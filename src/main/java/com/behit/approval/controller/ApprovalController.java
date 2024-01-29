package com.behit.approval.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.behit.approval.dto.ApprovalDTO;
import com.behit.approval.service.ApprovalService;
import com.behit.creator.dto.CreatorDTO;
import com.behit.employee.dto.EmployeeDTO;
import com.behit.project.dto.ProjectFileDTO;
import com.behit.util.dto.UtilDTO;

@Controller
public class ApprovalController {
	// 변경사항
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ApprovalService service;

	// 작성 기안 이동
	@GetMapping(value="/approval/approval_write.go/{form}")
	public ModelAndView approval_write_go(@PathVariable String form,HttpSession session) {
		EmployeeDTO loginInfo = (EmployeeDTO) session.getAttribute("loginInfo");
		String emp_id = loginInfo.getEmp_id();
		String login_name = loginInfo.getEmp_name();

		return service.approval_write_go(form,emp_id,login_name);
	}
	
	// 시간비교
	@GetMapping(value="/approval/compare_time")
	@ResponseBody
	public HashMap<String, Object> compare_time(ApprovalDTO dto,HttpSession session){
		EmployeeDTO loginInfo = (EmployeeDTO) session.getAttribute("loginInfo");
		String emp_id = loginInfo.getEmp_id();
		return service.compare_time(emp_id,dto);
	}
	
	// 조직도
	@GetMapping(value="/org_selected")
	@ResponseBody
	public HashMap<String, Object> org_selected(@RequestParam String emp_id){
		logger.info("선택된 emp_id :"+emp_id);
		return service.org_selected(emp_id);
	}
	
	// 상신
	@PostMapping(value="/approval/approval_write.do")
	public String approval_write(HttpSession session, ApprovalDTO dto, MultipartFile[] files) {
		EmployeeDTO loginInfo = (EmployeeDTO) session.getAttribute("loginInfo");
		
		logger.info(dto.getApv_cnt());

		return service.approval_write(loginInfo,dto,files);
	}
	
	// 임시저장
	@PostMapping(value="/approval/temporaryApproval_write.do")
	public String temporaryApproval_write(HttpSession session, ApprovalDTO dto, MultipartFile[] files) {
		EmployeeDTO loginInfo = (EmployeeDTO) session.getAttribute("loginInfo");
		
		return service.approval_write(loginInfo,dto,files);
	}
	
	
	// 리스트 상세보기
	@GetMapping(value="/approval/getApproval_detail.go/{apv_idx}")
	public ModelAndView getApproval_detail(@PathVariable String apv_idx,HttpSession session) {
		EmployeeDTO loginInfo = (EmployeeDTO) session.getAttribute("loginInfo");
		String emp_id = loginInfo.getEmp_id();
		String apv_type = "detail";
		return service.getApproval_detail(apv_idx,emp_id,apv_type);
	}
	
	// 결재
	@PostMapping(value="/approval/getApproval_detail.do")
	public String getApproval_detail_do(ApprovalDTO dto) {
		
		return service.getApproval_detail_do(dto);
	}
	
	// 임시저장 재상신
	@PostMapping(value="/approval/approval_update.do")
	public String approval_update(HttpSession session, ApprovalDTO dto, MultipartFile[] files) {
		EmployeeDTO loginInfo = (EmployeeDTO) session.getAttribute("loginInfo");
		
		return service.approval_update(loginInfo,dto,files);
	}
	
	// 임시저장 상세
	@GetMapping(value="/approval/temporaryApproval_detail.go/{apv_idx}")
	public ModelAndView temporaryApproval_detail(@PathVariable String apv_idx,HttpSession session) {
		EmployeeDTO loginInfo = (EmployeeDTO) session.getAttribute("loginInfo");
		String emp_id = loginInfo.getEmp_id();
		String apv_type = "detail";
		return service.getApproval_detail(apv_idx,emp_id,apv_type);
	}
	
	// 결재 요청받은 리스트
	@GetMapping(value="/approval/getApproval_list.do")
	@ResponseBody
	public List<ApprovalDTO> getApproval_list(HttpSession session) {
		EmployeeDTO loginInfo = (EmployeeDTO) session.getAttribute("loginInfo");
		String emp_id = loginInfo.getEmp_id();
		
		return service.getApproval_list(emp_id);
	}
	
	// 결재 완료한 리스트
	@GetMapping(value="/approval/compApproval_list.do")
	@ResponseBody
	public List<ApprovalDTO> compApproval_list(HttpSession session) {
		EmployeeDTO loginInfo = (EmployeeDTO) session.getAttribute("loginInfo");
		String emp_id = loginInfo.getEmp_id();
		
		return service.compApproval_list(emp_id);
	}
	
	// 결재 요청한 리스트
	@GetMapping(value="/approval/requestApproval_list.do")
	@ResponseBody
	public List<ApprovalDTO> requestApproval_list(HttpSession session) {
		EmployeeDTO loginInfo = (EmployeeDTO) session.getAttribute("loginInfo");
		String emp_id = loginInfo.getEmp_id();

		return service.requestApproval_list(emp_id);
	}
	
	// 결재 완료된 리스트
	@GetMapping(value="/approval/finishApproval_list.do")
	@ResponseBody
	public List<ApprovalDTO> finishApproval_list(HttpSession session) {
		EmployeeDTO loginInfo = (EmployeeDTO) session.getAttribute("loginInfo");
		String emp_id = loginInfo.getEmp_id();

		return service.finishApproval_list(emp_id);
	}
	
	// 반려된 리스트
	@GetMapping(value="/approval/rejectedApproval_list.do")
	@ResponseBody
	public List<ApprovalDTO> rejectedApproval_list(HttpSession session) {
		EmployeeDTO loginInfo = (EmployeeDTO) session.getAttribute("loginInfo");
		String emp_id = loginInfo.getEmp_id();

		return service.rejectedApproval_list(emp_id);
	}
	
	// 임시저장된 리스트
	@GetMapping(value="/approval/temporaryApproval_list.do")
	@ResponseBody
	public List<ApprovalDTO> temporaryApproval_list(HttpSession session) {
		EmployeeDTO loginInfo = (EmployeeDTO) session.getAttribute("loginInfo");
		String emp_id = loginInfo.getEmp_id();

		return service.temporaryApproval_list(emp_id);
	}
	
	// 열람자 리스트
	@GetMapping(value="/approval/viewApproval_list.do")
	@ResponseBody
	public List<ApprovalDTO> viewApproval_list(HttpSession session) {
		EmployeeDTO loginInfo = (EmployeeDTO) session.getAttribute("loginInfo");
		String emp_id = loginInfo.getEmp_id();

		return service.viewApproval_list(emp_id);
	}
	
	// 열람자 추가
//	@PostMapping(value="/approval/CompApproval_ViewerPlus")
//	@ResponseBody
//	public HashMap<String, Object> CompApproval_ViewerPlus(ApprovalDTO dto,HttpSession session) {
//		EmployeeDTO loginInfo = (EmployeeDTO) session.getAttribute("loginInfo");
//		
//		logger.info("total_name 값 : "+ dto.getTotal_name());
//		logger.info("apv_idx 값 : "+ dto.getApv_idx());
//		
//		return service.CompApproval_ViewerPlus(loginInfo,dto);
//		
//	}
	
	// 열람자 추가
	@RequestMapping(value = "/approval/CompApproval_ViewerPlus", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> addApv_perm(HttpSession session, @RequestBody ApprovalDTO requestData) {

		logger.info("cre_idx: " + requestData.getApv_idx());
		logger.info("selectedNodes: " + requestData.getSelectedNodes());
		int apv_idx=requestData.getApv_idx();
		List<Map<String, String>> emp_data=requestData.getSelectedNodes();
		HashMap<String, Object> result = new HashMap<String, Object>();
		EmployeeDTO empdto=(EmployeeDTO) session.getAttribute("loginInfo");
		String loginId=empdto.getEmp_id();
		int idx=service.addApv_perm(loginId, apv_idx, emp_data);
		List<ApprovalDTO> apvPermList=service.apvPermList(apv_idx); 
		
		result.put("apvPermList", apvPermList);
		result.put("idx", idx);
		return result;
	}
	
	// 열람자 삭제
	@RequestMapping(value = "/approval/delApvPerm", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> delApvPerm(HttpSession session,
			@RequestBody ApprovalDTO delPerm) {
		int apv_idx = delPerm.getApv_idx();
		String emp_id =delPerm.getEmp_id();
	
		logger.info("cre_idx: " + apv_idx);
	    logger.info("emp_id"+emp_id);
	
		HashMap<String, Object> result = new HashMap<String, Object>();
		EmployeeDTO empdto=(EmployeeDTO) session.getAttribute("loginInfo");
		String loginId=empdto.getEmp_id();
		int idx=service.delApvPerm(apv_idx, emp_id);
		List<ApprovalDTO> apvPermList=service.apvPermList(apv_idx); 
		
		result.put("apvPermList", apvPermList);			
		result.put("idx", idx);
		return result;
	}
	
	
	
	// 상신 취소
	@PostMapping(value="/approval/apv_cancel")
	@ResponseBody
	public HashMap<String, Object> apv_cancel(ApprovalDTO dto,HttpSession session) {
		EmployeeDTO loginInfo = (EmployeeDTO) session.getAttribute("loginInfo");
		
		logger.info("apv_stmt 값 : "+ dto.getApv_stmt());
		logger.info("apv_idx 값 : "+ dto.getApv_idx());
		
		return service.apv_cancel(dto);

	}
	
	//pdf 미리보기
	@GetMapping(value="/approval/approval_pdf.go")
	@ResponseBody
	public ModelAndView approval_pdf(@RequestParam String apv_idx, @RequestParam String apv_type, HttpSession session) {
		EmployeeDTO loginInfo = (EmployeeDTO) session.getAttribute("loginInfo");
		String emp_id = loginInfo.getEmp_id();
		return service.getApproval_detail(apv_idx,emp_id,apv_type);

	}
	
	// 임시저장 삭제
	@PostMapping(value="/approval/temporary_apv_del")
	@ResponseBody
	public HashMap<String, Object> temporary_apv_del(@RequestParam String apv_idx) {
		
		return service.temporary_apv_del(apv_idx);

	}
	
	// 페이지 이동
	@GetMapping(value="/approval/{page}.go")
	public String pageMove(@PathVariable String page) {
		return "/approval/"+page;
	}
	
	// 결재 파일
	
	@GetMapping(value="/approval/approval_file.do")
	@ResponseBody
	public Map<String, Object> projAllFile(@RequestParam String apv_idx){
		ArrayList<UtilDTO> list = service.approval_file(apv_idx);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		return map;
	}
	


}
