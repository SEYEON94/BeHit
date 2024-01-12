package com.behit.creator.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.behit.creator.dto.ChannelDataDTO;
import com.behit.creator.dto.CommCreDTO;
import com.behit.creator.dto.CreatorDTO;
import com.behit.creator.dto.CreatorRequestDTO;
import com.behit.creator.dto.SnsDTO;
import com.behit.creator.service.CreatorService;
import com.behit.employee.dto.EmployeeDTO;
import com.behit.util.service.UtilService;


@RestController
public class CreatorController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired CreatorService creatorService;
	@Autowired CreatorStatController creatorStatController;
	@Autowired UtilService utilService;
	
	// 크리에이터 리스트 페이지 이동 
	@GetMapping(value = "creators/creatorList.go")
	public ModelAndView cratorListGo(ModelAndView mav, HttpSession session) {
		logger.info("크리에이터 리스트 페이지로 이동 요청");
		
		EmployeeDTO loginInfo = (EmployeeDTO)session.getAttribute("loginInfo");
		boolean deptCheck = loginInfo.getEmp_dept_idx() == 6   ? true : false;
		logger.info("로그인 회원의 부서 코드 : " + deptCheck);
		
		mav.addObject("deptCheck", deptCheck);
		mav.setViewName("creators/creator_list_all");
		return mav;
	}
	
	// 크리에이터 등록 페이지 이동
	@GetMapping(value = "creators/creatorAdd.go")
	public ModelAndView creatorAddGo(ModelAndView mav, HttpSession session) {
		logger.info("크리에이터 등록 페이지로 이동 요청");
		
		EmployeeDTO loginInfo = (EmployeeDTO)session.getAttribute("loginInfo");
		boolean deptCheck = loginInfo.getEmp_dept_idx() == 6   ? true : false;
		if(deptCheck) {
			ArrayList<CommCreDTO> genders = creatorService.getGenders();
			ArrayList<CommCreDTO> countries = creatorService.getCountries();
			logger.info("genders : " + genders.toString());
			logger.info("countries : " + countries.toString());
			mav.addObject("genders", genders);
			mav.addObject("countries", countries);
			mav.setViewName("creators/creator_add");
		}else {
			mav.addObject("warningMsg", "접근권한이 없습니다.");
			mav.setViewName("home");
		}
		
		return mav;
	}
	
	// 크리에이터 수정 페이지 이동
	@GetMapping(value = "/creators/creatorUpdate.go")
	public ModelAndView creatorUpdateGo(@RequestParam String cre_idx, ModelAndView mav,HttpSession session) {
		logger.info("크리에이터 수정 페이지로 이동 :: cre_idx="+cre_idx);
		
		EmployeeDTO loginInfo = (EmployeeDTO)session.getAttribute("loginInfo");
		boolean deptCheck = loginInfo.getEmp_dept_idx() == 6   ? true : false;
		if(deptCheck) {
			int creIdx = Integer.parseInt(cre_idx);
			
			ArrayList<CommCreDTO> genders = creatorService.getGenders();
			ArrayList<CommCreDTO> countries = creatorService.getCountries();
			
			HashMap<String, Object> creatorInfo = creatorService.getCreator(creIdx);
			ArrayList<HashMap<String, Object>> channelInfoList = creatorService.getChannel(creIdx);
			ArrayList<HashMap<String, Object>> creatorHistory = creatorService.getCreHistory(creIdx); 
			ArrayList<SnsDTO> snsList = creatorService.getSns(creIdx); 
			
			for(HashMap<String, Object>channelInfo : channelInfoList  ) {
				logger.info("rep_channel value check = "+channelInfo.get("rep_channel"));
			}
			mav.addObject("genders", genders);
			mav.addObject("countries", countries);
			
			mav.addObject("creatorInfo", creatorInfo);
			mav.addObject("channelInfoList", channelInfoList);
			mav.addObject("creatorHistory", creatorHistory);
			mav.addObject("snsList", snsList);
			
			mav.setViewName("creators/creator_update");
		}else {
			mav.addObject("warningMsg", "접근권한이 없습니다.");
			mav.setViewName("home");
		}
		return mav;
	}
	

	
	@PostMapping(value = "/creatorAdd.ajax.do", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> creatorAddDO(@RequestBody CreatorRequestDTO creatorRequestDTO, HttpSession session) {
		logger.info("크리에이터 등록 요청 || prarms = {}", creatorRequestDTO);
		int creidx = 0;
		try {
			ArrayList<HashMap<String, Object>> result = creatorService.creatorAdd(creatorRequestDTO, session);
			for(HashMap<String, Object> param : result) {
				
				logger.info("등록 요청 결과 channel_id: "+ param.get("channel_id"));
				logger.info("등록 요청 결과 cre_idx "+ param.get("cre_idx"));
				logger.info("등록 요청 결과 rep_channel: "+ param.get("rep_channel"));
				creidx = (int) param.get("cre_idx");
				
				creatorStatController.saveChannelDataOne(param);
			}
			
			// creatorAdd()가 정상적으로 수행되었을때
			return new ResponseEntity<>(creidx,HttpStatus.OK);
		} catch (Exception e) {
			logger.info("creatorAdd()에서 오류 발생");
			// 그렇지 않을때
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
	
	// 크리에이터 프로필 사진 업로드
	@PostMapping("/creatorImgUpload.ajax")
    public ResponseEntity<String> creatorImgUpload( HttpSession session,
    		@RequestParam("file") MultipartFile file, Integer cre_idx) {
        logger.info("Received file: " + file);
        HashMap<String, Object > params = new HashMap<String, Object>();
        
        EmployeeDTO loginInfo = (EmployeeDTO) session.getAttribute("loginInfo");
        String loginId = loginInfo.getEmp_id();
        
        if (cre_idx != null) {
            String cre_idx_str = cre_idx.toString();
            logger.info("cre_idx: " + cre_idx_str);
            params.put("cre_idx", cre_idx_str);
        }else {
        	logger.warn("cre_idx IS NULL");
        }
        
        params.put("login_id", loginId);
        params.put("file_kind", 6);
        
        try {
			utilService.upload(file, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // 파일 처리 성공 시 응답
        return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
    }

	
	
	// 크리에이터 리스트 
	@GetMapping(value = "/getCreatorlist")
	public HashMap<String, Object> getCreatorList(HttpSession session) {
		logger.info("크리에이터 리스트 가져오기");
		
		EmployeeDTO loginInfo = (EmployeeDTO)session.getAttribute("loginInfo");
		String loginId = loginInfo.getEmp_id();
		
		HashMap<String, Object> totalInfo = creatorService.getTotalInfo();
		ArrayList<HashMap<String, Object>> allList = creatorService.getAllList();
		ArrayList<HashMap<String,Object>> myList = creatorService.getMyList(loginId);
		
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("totalInfo", totalInfo);
		result.put("allList", allList);
		result.put("myList", myList);
		return result;
	}
	
	// 크리에이터 상세보기 
	@GetMapping(value = "creators/creatorDetail.go")
	public ModelAndView creatorDetailGo(@RequestParam int cre_idx ,ModelAndView mav, HttpSession session) {
		logger.info("크리에이터 상세 페이지 요청 || cre_idx = "+cre_idx);
		
		EmployeeDTO loginInfo = (EmployeeDTO)session.getAttribute("loginInfo");
		boolean deptCheck = loginInfo.getEmp_dept_idx() == 6   ? true : false;
		
		HashMap<String, Object> creatorInfo = creatorService.getCreator(cre_idx);
		ArrayList<HashMap<String, Object>> channelInfoList = creatorService.getChannel(cre_idx);
		ArrayList<HashMap<String, Object>> creatorHistory = creatorService.getCreHistory(cre_idx); 
		ArrayList<SnsDTO> snsList = creatorService.getSns(cre_idx); 
		List<CreatorDTO> creatorPermList=creatorService.creatorPermList(cre_idx); 
		
		mav.addObject("creatorPermList", creatorPermList);
		mav.addObject("creatorInfo", creatorInfo);
		mav.addObject("channelInfoList", channelInfoList);
		mav.addObject("creatorHistory", creatorHistory);
		mav.addObject("snsList", snsList);
		
		mav.addObject("deptCheck", deptCheck);
		
		mav.setViewName("creators/creator_detail");
		return mav;
	}
	
	// 채널 차트 데이터 가져오기
	@GetMapping(value = "/getChartData.ajax")
	public HashMap<String, Object> getChartData(@RequestParam String repChannelId){
		logger.info("차트 데이터 요청");
		ArrayList<ChannelDataDTO> channelDataList = creatorService.getChartData(repChannelId);
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("channelDataList", channelDataList);
		return result;
	}
	
	// API 활용하여 영상 링크로 채널 정보 가져오기
		@PostMapping(value = "/getChInfo.ajax.do")
		public HashMap<String, Object> getChInfo(@RequestParam String videoURL){
			logger.info("videoURL = "+videoURL);
			HashMap<String, Object> result = new HashMap<String, Object>();
			try {
				HashMap<String, String> chInfo = creatorService.getChannelInfo(videoURL);
				result.put("chInfo", chInfo);
			} catch (Exception e) {
				result.put("msg","영상의 주소를 확인해주세요");
				e.printStackTrace();
			}
			return result;
		}
		
		// 열람 권한 추가
		@RequestMapping(value = "/addCreator_perm", method = RequestMethod.POST)
		@ResponseBody
		public HashMap<String, Object> addCreator_perm(HttpSession session,
				@RequestBody CreatorDTO requestData) {

				logger.info("cre_idx: " + requestData.getCre_idx());
			    logger.info("selectedNodes: " + requestData.getSelectedNodes());
			    int cre_idx=requestData.getCre_idx();
			    List<Map<String, String>> emp_data=requestData.getSelectedNodes();
				HashMap<String, Object> result = new HashMap<String, Object>();
				EmployeeDTO empdto=(EmployeeDTO) session.getAttribute("loginInfo");
				String loginId=empdto.getEmp_id();
				int idx=creatorService.addCreator_perm(loginId, cre_idx, emp_data);

				result.put("idx", idx);
				return result;
			}
		
		
		@RequestMapping(value = "/delPerm", method = RequestMethod.POST)
		@ResponseBody
		public HashMap<String, Object> delPerm(HttpSession session,
				@RequestBody CreatorDTO delPerm) {
			 int cre_idx = delPerm.getCre_idx();
			 String emp_id =delPerm.getEmp_id();
			
				logger.info("cre_idx: " + cre_idx);
			    logger.info("emp_id"+emp_id);

				HashMap<String, Object> result = new HashMap<String, Object>();
				EmployeeDTO empdto=(EmployeeDTO) session.getAttribute("loginInfo");
				String loginId=empdto.getEmp_id();
				int idx=creatorService.delPerm(cre_idx, emp_id);

				result.put("idx", idx);
				return result;
			}
		
	/*
		@GetMapping(value="/creatorPermList")
		public String creatorPermList(HttpSession session, Model model) {
			logger.info("");
			return "creator_detail";
		}
	*/
	// 채널 카테고리 항목 데이터 가져오기  보류
//	@GetMapping(value = "/getChCate.ajax.do")
//	public HashMap<String, Object> getChCateDo() {
//		logger.info("채널카테고리 데이터 요청");
//
//		ArrayList<String> chCategory = creatorService.getChCategory();
//		logger.info("chCategory : " + chCategory.size());
//
//		HashMap<String, Object> result = new HashMap<String, Object>();
//		result.put("chCategory", chCategory);
//
//		return result;
//	}
	
	


}
