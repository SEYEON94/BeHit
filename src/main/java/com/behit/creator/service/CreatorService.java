package com.behit.creator.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.behit.creator.dao.CreatorDAO;
import com.behit.creator.dao.CreatorStatDAO;
import com.behit.creator.dto.ChannelDTO;
import com.behit.creator.dto.ChannelDataDTO;
import com.behit.creator.dto.CommCreDTO;
import com.behit.creator.dto.CreHistDTO;
import com.behit.creator.dto.CreatorDTO;
import com.behit.creator.dto.CreatorPermDTO;
import com.behit.creator.dto.CreatorRequestDTO;
import com.behit.creator.dto.SnsDTO;
import com.behit.employee.dto.EmployeeDTO;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.VideoListResponse;

@Service
public class CreatorService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${GOOGLE-KEY}") String secret_key;
	
	@Autowired CreatorDAO creatorDAO;
	@Autowired CreatorStatService creatorStatService;
	
	// 크리에이터 등록시 항목의 데이터 가져오기
	public ArrayList<CommCreDTO> getGenders() {
		logger.info("getGenders() 실행");
		return creatorDAO.getGenders();
	}

	public ArrayList<CommCreDTO> getCountries() {
		logger.info("getCountries() 실행");
		return creatorDAO.getCountries();
	}

	public ArrayList<String> getChCategory() {
		logger.info("getChCategory() 실행");
		return creatorDAO.getChCategory();
	}
	
	

	
	// 크리에이터 등록
	@Transactional
	public ArrayList<HashMap<String, Object>> creatorAdd(CreatorRequestDTO creatorRequestDTO, HttpSession session) throws Exception {
		logger.info("creatorAdd() 실행");
		
		EmployeeDTO loginInfo = (EmployeeDTO)session.getAttribute("loginInfo");
		logger.info("loginInfo = "+ loginInfo);
		
		ArrayList<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
		int cre_idx = 0;
		
		// Creator tbl insert
		CreatorDTO creatorDTO = creatorRequestDTO.getCreatorDTO();
		creatorDTO.setEmp_id_in(loginInfo.getEmp_id());
		creatorDTO.setEmp_id_up(loginInfo.getEmp_id());
		int creatorRow =0;
		creatorRow = creatorDAO.creatorInsert(creatorDTO);
		logger.info("creatorInsert result :: "+creatorRow);
		
		cre_idx = creatorDTO.getCre_idx();
		logger.info("cre_idx : "+cre_idx);
		
		// Channel tbl insert
		ArrayList<ChannelDTO> channelDTOs = creatorRequestDTO.getChannelDTOList();
		int channelRow = 0;
		String channelId = "";
		for(ChannelDTO channelDTO : channelDTOs) {
//			try {
				String videoUrl = channelDTO.getRep_video();
			    
				HashMap<String, String> channelInfo = getChannelInfo(videoUrl);
				if(channelInfo == null) {
					
				}
				channelId = channelInfo.get("channelId");
				channelDTO.setChannel_id(channelId);	// dto에 채널 id set
//			} catch (Exception e1) {
//				e1.printStackTrace();
//				logger.warn("영상링크가 없거나 잘못된 링크");
//				// 잘못되면 등록 취소 시키기 로직 구현
//			}
			// client에서 자동을 가져오지 않는 정보 직접 set
			channelDTO.setCre_idx(cre_idx);
			channelDTO.setEmp_id(loginInfo.getEmp_id());
			channelDTO.setEmp_id_up(loginInfo.getEmp_id());
			channelRow += creatorDAO.channelInsert(channelDTO);
			logger.info("channelInsert result :: "+channelRow);
			
			// 크리에이터 등록후  channel data  를 위한 객체
			HashMap<String, Object> channelParam = new HashMap<String, Object>();
			channelParam.put("cre_idx", channelDTO.getCre_idx());
			channelParam.put("channel_id", channelId);
			channelParam.put("rep_channel", channelDTO.getRep_channel());
			
			result.add(channelParam);
		}
		// Sns tbl insert
		ArrayList<SnsDTO> snsDTOs = creatorRequestDTO.getSnsDTOList();
		int snsRow = 0;
		for(SnsDTO snsDTO : snsDTOs) {
			// url에서 platform명 뽑아내기
			String url = snsDTO.getSns_url()
							.replace("http://", "")
							.replace("https://", "")
							.replace("www.", "");
			String[] parts = url.split("\\.");
			String snsCate = parts[0];
			
			logger.info("snsCate = "+snsCate);
			
			snsDTO.setSns_cate(snsCate);
			snsDTO.setCre_idx(cre_idx);
			snsDTO.setEmp_id(loginInfo.getEmp_id());
			snsDTO.setEmp_id_up(loginInfo.getEmp_id());
			snsRow += creatorDAO.snsInsert(snsDTO);
			logger.info("snsInsert result :: "+snsRow);
		}
		
		// CreatorHistory tbl insert
		ArrayList<CreHistDTO> creHistDTOs = creatorRequestDTO.getCreHistDTOList();
		int creHisRow = 0;
		for(CreHistDTO creHistDTO : creHistDTOs) {
			creHistDTO.setCre_idx(cre_idx);
			creHistDTO.setEmp_id(loginInfo.getEmp_id());
			creHistDTO.setEmp_id_up(loginInfo.getEmp_id());
			creHisRow += creatorDAO.creHisInsert(creHistDTO);
		}
		CreHistDTO creHistDTO = new CreHistDTO();
		for(int i=0; i<channelDTOs.size(); i++) {
			creHistDTO.setCre_his_title(channelDTOs.get(i).getChannel_name());
			creHistDTO.setCre_his_cate("채널시작");
			creHistDTO.setCre_his_content("유튜브 채널 생성");
			creHistDTO.setHistory_date(channelDTOs.get(i).getChannel_date());
			creHistDTO.setCre_idx(cre_idx);
			creHistDTO.setEmp_id(loginInfo.getEmp_id());
			creHistDTO.setEmp_id_up(loginInfo.getEmp_id());
			creHisRow += creatorDAO.creHisInsert(creHistDTO);
		}
		logger.info("creHisInsert result :: "+creHisRow);
		// 확인
		
		if(creatorRow > 0 && channelRow > 0 && snsRow > 0 && creHisRow > 0) {
			logger.info("creatorRow="+creatorRow);
			logger.info("channelRow="+channelRow);
			logger.info("snsRow="+snsRow);
			logger.info("creHisRow="+creHisRow);
		}else {
			logger.info("크리에이터 등록 에러");
		}
		
		return result;
	}
	
	
	// 채널정보 가져오기
	public HashMap<String,String> getChannelInfo(String videoURL) throws Exception {
		logger.info("채널 정보 가져오기 실행");
		logger.info("videoURL = "+videoURL);

		String videoId = videoURL.split("v=")[1];
		
	    YouTube youtube = new YouTube.Builder(
	            GoogleNetHttpTransport.newTrustedTransport(),
	            JacksonFactory.getDefaultInstance(),
	            null)
	            .setApplicationName("BeHit")
	            .build();
	    YouTube.Videos.List request = youtube.videos()
	            .list("snippet")
	            .setId(videoId)
	            .setKey(secret_key);
	    logger.info("youtube api getInfo request result : ",request);
	    VideoListResponse response = request.execute();
	    logger.info("youtube api getInfo response result: ",response);
	    // 채널 id를 가져올 수 없다면 
	    if(response == null) {
	    	return null; 
	    }
	    String channelId = response.getItems().get(0).getSnippet().getChannelId();
	    
	    YouTube.Channels.List channelRequest = youtube.channels()
	            .list("snippet")
	            .setId(channelId)
	            .setKey(secret_key);

	    ChannelListResponse channelResponse = channelRequest.execute();
	    String channelDate = channelResponse.getItems().get(0).getSnippet().getPublishedAt().toStringRfc3339();
	    String channelName = channelResponse.getItems().get(0).getSnippet().getTitle();

	    HashMap<String, String> channelInfo = new HashMap<String, String>();
	    channelInfo.put("channelId" , channelId);
	    channelInfo.put("channelUrl" , "https://www.youtube.com/channel/" + channelId);
	    channelInfo.put("channelDate", channelDate);
	    channelInfo.put("channelName", channelName);
	    
	    logger.info("Channel ID: " + channelId);

	    return channelInfo;
	}
	
	
	// 크리에이터 리스트
	
	// 전체 크리에이터 합계 정보
	public HashMap<String, Object> getTotalInfo() {
		logger.info("전체 정보 가져오기 실행");
		HashMap<String, Object> totalInfo = creatorDAO.getTotalInfo(); 
		if(totalInfo != null) {
			logger.info("전체 정보 가져오기 성공");
			logger.info("creatorAllList = "+totalInfo);
		}
		return totalInfo;
	}

	
	// 크리에이터 전체 리스트
	public ArrayList<HashMap<String, Object>> getAllList() {
		logger.info("전체 크리에이터 리스트 가져오기 실행");
		ArrayList<HashMap<String, Object>> allList = new ArrayList<HashMap<String,Object>>();
		try {
			allList = creatorDAO.getAllList();
			logger.info("전체 크리에이터 리스트 가져오기 성공");
			logger.info("allList = "+allList);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return allList;
	}

	// 매니저별 크리에이터 리스트
	public ArrayList<HashMap<String, Object>> getMyList(String loginId) {
		logger.info("나의 크리에이터 리스트 가져오기 실행");
		ArrayList<HashMap<String, Object>> myList = creatorDAO.getMyList(loginId);
		try {
			myList = creatorDAO.getMyList(loginId);
			logger.info("전체 크리에이터 리스트 가져오기 성공");
			logger.info("allList = "+myList);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return myList;
	}

	// 크리에이터 상세
	
	// 크리에이터 기본 정보
	public HashMap<String, Object> getCreator(int cre_idx) {
		logger.info("creator 가져오기 실행");
		return creatorDAO.getCreator(cre_idx);
	}
	// 크리에이터 채널 합계 정보
	public HashMap<String, Object> getChannelSum(int cre_idx) {
		logger.info("creator 채널 합계 정보 가져오기 실행");
		return creatorDAO.getChannelSum(cre_idx);
	}

	// 크리에이터 채널 정보
	public ArrayList<HashMap<String, Object>> getChannel(int cre_idx) {
		logger.info("channel 기본정보 가져오기 실행");
		ArrayList<HashMap<String, Object>> channelInfoList = creatorDAO.getChannel(cre_idx);
//		logger.info("channel 통계 정보 가져오기 실행");
//		logger.info("Channel ID 가져오기 실행");
//		ArrayList<String> channelIdList = creatorDAO.getChannelIdByCreIdx(cre_idx); 
		
		int idx = 0;
		for(HashMap<String, Object> channelItem : channelInfoList) {
			String channelId = (String) channelItem.get("channel_id");
			logger.info("channelIdList 하나씩 꺼내기 : "+channelId);
			Channel channel = creatorStatService.useYoutubeApi(channelId);
			
			HashMap<String, Object> channelInfo = channelInfoList.get(idx++);
			
			BigInteger subscriber = channel.getStatistics().getSubscriberCount();
	        BigInteger views = channel.getStatistics().getViewCount();
	        BigInteger contents = channel.getStatistics().getVideoCount();
	        
	        channelInfo.put("subscriber", subscriber);
	        channelInfo.put("views", views);
	        channelInfo.put("contents", contents);
	        
		}
		return channelInfoList;
	}
	
	// 크리에이터 활동이력
	public ArrayList<HashMap<String, Object>> getCreHistory(int cre_idx) {
		logger.info("활동이력 가져오기 실행");
		ArrayList<HashMap<String, Object>> creatorHistory = creatorDAO.getCreHistory(cre_idx);
		return creatorHistory;
	}
	
	// 크리에이터 SNS 정보
	public ArrayList<SnsDTO> getSns(int cre_idx) {
		logger.info("SNS 정보 가져오기 실행");
		ArrayList<SnsDTO> snsList = creatorDAO.getSns(cre_idx);
		return snsList;
	}
	
	// 대표 채널정보 탭
	
	// 그래프 데이터 가져오기
	public ArrayList<ChannelDataDTO> getChartData(String repChannelId) {
		logger.info("차트 데이터 가져오기 실행");
		ArrayList<ChannelDataDTO> channelDataList = creatorDAO.getChartData(repChannelId);
		return channelDataList;
	}

	// 열람자 추가
	public int addCreator_perm(String loginId, int cre_idx, List<Map<String, String>> emp_data) {
		List<String> empIds = new ArrayList<>();
		
		// emp_data에 있는 각 Map에서 emp_id를 추출하여 추가
				for (Map<String, String> empDataMap : emp_data) {
				    String empId = empDataMap.get("emp_id");
				    empIds.add(empId);
				}

			    // chat_pp에 empId를 반복하여 데이터 삽입
			    for (String empId : empIds) {
			    	creatorDAO.addCreator_perm(empId, cre_idx, loginId);
			    }
		return cre_idx;
	}

	public List<CreatorDTO> creatorPermList(int cre_idx) {
		return creatorDAO.creatorPermList(cre_idx);
	}

	public int delPerm(int cre_idx, String emp_id) {
		// TODO Auto-generated method stub
		return creatorDAO.delPerm(cre_idx, emp_id);
	}

	public boolean permChk(HashMap<String, Object> permChkParam) {
		logger.info("상세보기 권한 체크 실행");
		boolean permChk1 = false;
		boolean permChk2 = false;
		boolean permChk3 = false;
		
		permChk1 = creatorDAO.permChk1(permChkParam);
		if(!permChk1) {
			permChk2 = creatorDAO.permChk2(permChkParam);
		}
		if(!permChk2) {
			permChk3 = creatorDAO.permChk3(permChkParam);
		}
		if(permChk1 || permChk2 || permChk3) {
			return true;
		}
		return false;
	}

	// 크리에이터 리스트에 보여줄 권한
	public ArrayList<CreatorPermDTO> getCrePerm(String loginId) {
		logger.info("로그인 id 별 크리에이터 권한 리스트 요청");
		ArrayList<CreatorPermDTO> crePermList = creatorDAO.getCrePerm(loginId);
		return crePermList;
	}

	public boolean leaderChk(String loginId) {
		logger.info("팀장인지 권한 체크 요청");
		return creatorDAO.leaderChk(loginId);
	}

	public ArrayList<CreatorPermDTO> getCrePermForMng(String loginId) {
		logger.info("담당자인지 권한 체크 요청");
		return creatorDAO.getCrePermForMng(loginId);
	}

	public boolean permListChk(HashMap<String, Object> permChkParam) {
		logger.info("열람권한 리스트 체크 실행");
		boolean permChk1 = false;
		boolean permChk2 = false;
		
		permChk1 = creatorDAO.permChk1(permChkParam);
		if(!permChk1) {
			permChk2 = creatorDAO.permChk2(permChkParam);
		}
		
		if(permChk1 || permChk2) {
			return true;
		}
		return false;
	}

	
	
	

	
	


}
