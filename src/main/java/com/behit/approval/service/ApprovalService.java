package com.behit.approval.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.behit.approval.dao.ApprovalDAO;
import com.behit.approval.dto.ApprovalDTO;
import com.behit.creator.dto.CreatorDTO;
import com.behit.employee.dto.EmployeeDTO;
import com.behit.employee.dto.VacationDTO;
import com.behit.employee.dto.WorkDTO;
import com.behit.project.dto.ProjectFileDTO;
import com.behit.util.dto.UtilDTO;
import com.behit.util.service.UtilService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ApprovalService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// private String root = "/usr/local/tomcat/upload/";	
	
	private String root = "C:/upload/";
	
	@Autowired
	ApprovalDAO dao;
	
	// 기안 작성
	public ModelAndView approval_write_go(String form,String emp_id, String login_name) {
		
		ModelAndView mav = new ModelAndView("/approval/approval_write");
		ApprovalDTO dto = dao.approval_write_go(emp_id);
		
		// ArrayList<WorkDTO> workDTO = dao.commute_history(emp_id);

		mav.addObject("form",form);
		mav.addObject("login_name",login_name);
		mav.addObject("dto",dto);
		mav.addObject("form_type","write");
		// mav.addObject("workDTO" ,workDTO);
		
		return mav;
	}

	
	// 조직도
	public HashMap<String, Object> org_selected(String emp_id) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		ApprovalDTO dto = dao.approval_write_go(emp_id);
		
		map.put("dept_name",dto.getDept_name());
		map.put("position_name",dto.getPosition_name());
		map.put("emp_name",dto.getEmp_name());
		map.put("grade_name",dto.getGrade_name());
		
		return map;
	}

	
	
	public String approval_write(EmployeeDTO loginInfo, ApprovalDTO dto, MultipartFile[] files) {
		
		
		
		
		String emp_id = loginInfo.getEmp_id();
		String total_name = dto.getTotal_name();
		String apv_code = dto.getApv_code();
		String apv_vac_type = dto.getApv_vac_type();
		
		logger.info("!!왜?"+emp_id);
		
		dto.setEmp_id(emp_id);
		
		if(apv_code.equals("BFVC") || apv_code.equals("AFVC")) {
			
			if(apv_code.equals("BFVC")) {
				dto.setApv_subject("연차 신청서");
			}else {
				dto.setApv_subject("사후 연차 신청서");
			}
			
			dto.setApv_time(dto.getApv_time().replace(",", ""));
			
			if(apv_vac_type.equals("종일")) {
				dto.setApv_start_day(dto.getApv_start_day().replace(",", ""));
			}else if(apv_vac_type.equals("시간")) {
				setVacationTime(dto);
				dto.setApv_start_day("");
			}
			
			dao.approval_write_vac(dto);

		}else if(apv_code.equals("BSPN")) {
			dao.approval_write_biz(dto);
		}
		
		// 제너레이트 키로 apv_idx 값 받기
		int apv_idx = dto.getApv_idx();
		
		logger.info("!!dto.getTotal_name()"+dto.getTotal_name());
		
		if (dto.getTotal_name() != null && !dto.getTotal_name().isEmpty()) {
		    // 값이 비어있지 않은 경우
			apv_line(apv_idx,total_name,emp_id);
			
			ApprovalDTO apv_dto = dao.getApproval_detail(apv_idx);
			
			logger.info("값 찍어보기 "+apv_dto.getApv_stmt());
			
			if(!apv_dto.getApv_stmt().equals("임시저장")) {
				String apv_subject = apv_dto.getApv_subject();
				if (apv_subject.length() > 20) {
				    apv_subject = apv_subject.substring(0, 20) + "...";
				    apv_dto.setApv_subject(apv_subject);
				}
				dao.alarm(apv_dto);
			}
			
		} else {
		    // 값이 비어있는 경우
		    logger.info("TotalName is empty or null");
		    // 또는 다른 로직 수행
		}
	
		
		HashMap<String, Object> file = new HashMap<String, Object>();
		
		file.put("file_kind", 3);
		file.put("login_id", emp_id);
		file.put("apv_idx", apv_idx);
		
		for (MultipartFile multipartFile : files) {
			try {
				upload(multipartFile,file);
				Thread.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return "approval/approval_main";
	}
	


	public String approval_update(EmployeeDTO loginInfo, ApprovalDTO dto, MultipartFile[] files) {
		
		int apv_idx = dto.getApv_idx();
		String apv_vac_type = dto.getApv_vac_type();
		String apv_code = dto.getApv_code();
		String total_name = dto.getTotal_name();
		String emp_id = loginInfo.getEmp_id();
		
		logger.info("!!total_name :"+total_name);
		
		
		if(apv_code.equals("BFVC") || apv_code.equals("AFVC")) {
			
			if(apv_code.equals("BFVC")) {
				dto.setApv_subject("연차 신청서");
			}else {
				dto.setApv_subject("사후 연차 신청서");
			}
			
			dto.setApv_time(dto.getApv_time().replace(",", ""));
		
			if(apv_vac_type.equals("종일")) {
				dto.setApv_start_day(dto.getApv_start_day().replace(",", ""));
			}else if(apv_vac_type.equals("시간")) {
				setVacationTime(dto);
				dto.setApv_start_time(dto.getApv_start_time().replace(",", ""));
				dto.setApv_end_time(dto.getApv_end_time().replace(",", ""));
				
				setVacationTime(dto);
				dto.setApv_start_day("");
			}
		
		}
		
		dao.approval_update(dto);
		
		ApprovalDTO apv_dto = dao.getApproval_detail(apv_idx);
		
		logger.info("값 찍어보기 "+apv_dto.getApv_stmt());
		
		
		if (dto.getTotal_name() != null && !dto.getTotal_name().isEmpty()) {
		    // 값이 비어있지 않은 경우
			
			logger.info("값이 찍히나요?");
			logger.info("!!total_name :"+total_name);
			apv_line(apv_idx,total_name,emp_id);
			
			ApprovalDTO aaa = dao.getApproval_detail(apv_idx);
			
			if(!aaa.getApv_stmt().equals("임시저장")) {
				String apv_subject = aaa.getApv_subject();
				if (apv_subject.length() > 20) {
					apv_subject = apv_subject.substring(0, 20) + "...";
					apv_dto.setApv_subject(apv_subject);
				}
				
				dao.alarm(aaa);
			}
			
			
		}
		
		// 만약 지울 파일이 있으면
		if(dto.getApv_file() != null && !dto.getApv_file().isEmpty()) {
			
			ObjectMapper objectMapper = new ObjectMapper();
			List<String> delUpfileList = null;
			try {
				delUpfileList = objectMapper.readValue(dto.getApv_file(), new TypeReference<List<String>>() {});
				if(delUpfileList != null) { // 파일 삭제
					for (String delF : delUpfileList) {
						logger.info("삭제할 파일: {}", delF);
						int file_idx = Integer.parseInt(delF);
						dao.fileDel(file_idx);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		HashMap<String, Object> file = new HashMap<String, Object>();
		
		file.put("file_kind", 3);
		file.put("login_id", emp_id);
		file.put("apv_idx", apv_idx);
		
		for (MultipartFile multipartFile : files) {
			try {
				upload(multipartFile,file);
				Thread.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			
		return "approval/temporaryApproval_list";
	}
	
	// 파일 업로드 메서드
	private void upload(MultipartFile uploadFile, HashMap<String, Object> file) {
		String ori_file_name = uploadFile.getOriginalFilename(); // 파일명 추출
		String ext = ori_file_name.substring(ori_file_name.lastIndexOf(".")); // 확장자 추출
		String new_file_name = System.currentTimeMillis()+ext; // 새 파일명 생성 + 확장자
		
		String sub = "approval/";
		String file_kind_idx = String.valueOf(file.get("apv_idx"));
		
		byte[] bytes;
		
		try {
			bytes = uploadFile.getBytes();
			Path path = Paths.get(root+sub+new_file_name);
			Files.write(path, bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		file.put("file_kind_idx", file_kind_idx);
		file.put("ori_file_name", ori_file_name);
		file.put("new_file_name", new_file_name);
		
		dao.upload(file);
		
	}
	
	

	// apv_line 테이블에 데이터 insert 메서드
	private void apv_line(int apv_idx, String total_name, String emp_id_in) {
		
		dao.apv_line_del(apv_idx);
		
		String pattern = "\\[\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\"(?:,\"(.*?)\")?\\]";
		
		// 정규 표현식에 따라 패턴 찾기
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(total_name);

        // 결과를 담을 리스트
        List<List<String>> resultList = new ArrayList<>();

        while (matcher.find()) {
            List<String> item = new ArrayList<>();
            
            // 그룹의 개수만큼 반복하여 리스트에 추가
            for (int i = 1; i <= matcher.groupCount(); i++) {
                item.add(matcher.group(i));
            }

            // 리스트를 문자열로 변환하여 결과 리스트에 추가
            resultList.add(item);
        }
        
        // apv 의 apv_approver 에 첫번째 결재자 추가
        List<String> item = resultList.get(0);
        dao.apv_approver(apv_idx,item.get(3));

        // 결과 출력 apv_line 테이블에 모든 결재자 데이터 insert 메서드
        for (int i = 0; i < resultList.size(); i++) {
        	
        	ApprovalDTO dto = new ApprovalDTO();
        	
            List<String> items = resultList.get(i);
            // 리스트에 적어도 4개의 요소가 있는지 확인
            if (items.size() >= 4) {
            	
            	dto.setApv_idx(apv_idx);
            	dto.setEmp_id(items.get(3));
            	dto.setApv_line(i+1);
            	dto.setEmp_id_in(emp_id_in);
            	
                dao.apv_line(dto);
                
            } else {
                System.out.println("리스트에 충분한 요소가 없습니다.");
            }
        }
		
	}

	// 종일, 시간에 따라 dto 에 담긴 값 달리 하는 메서드
	private void setVacationTime(ApprovalDTO dto) {
		
			SimpleDateFormat inputFormat = new SimpleDateFormat("h:mma", Locale.ENGLISH); // 7:00am 을
			SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm:ss"); // 07:00:00 로
			
			try {
				java.util.Date date1 = inputFormat.parse(dto.getApv_start_time());
				java.util.Date date2 = inputFormat.parse(dto.getApv_end_time());
				
				String outputTime1 = outputFormat.format(date1);
	            String outputTime2 = outputFormat.format(date2);
	            
	            String combinedDateTime1 = dto.getApv_start_day() + " " + outputTime1;
	            String combinedDateTime2 = dto.getApv_start_day() + " " + outputTime2;
	            
	            dto.setApv_start_time(combinedDateTime1);
	            dto.setApv_end_time(combinedDateTime2);
			
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
	}
	

	public ModelAndView getApproval_detail(String apv_idx, String emp_id, String apv_type) {
		
			
		ApprovalDTO apv = dao.getApproval_detail(Integer.parseInt(apv_idx)); // apv 에 대한 정보 
		
		ModelAndView mav = new ModelAndView("approval/getApproval_detail");
		
		if(apv_type.equals("detail")) {
			mav = new ModelAndView("approval/getApproval_detail");
		}else if(apv_type.equals("pdf")) {
			
			if(apv.getApv_code().equals("BSPN")) {
				mav = new ModelAndView("approval/approval_pdf_biz");
			}else if(apv.getApv_code().equals("BFVC") || apv.getApv_code().equals("AFVC")) {
				mav = new ModelAndView("approval/approval_pdf_vac");
			}
				
		}
		
		
		ApprovalDTO dto = dao.approval_write_go(apv.getEmp_id()); // 상신자 정보
		
		ApprovalDTO apv_history_reason = dao.apv_history_reason(apv_idx); // 결재사유
		
		if(apv_history_reason != null) {
			apv.setApv_history_stmt(apv_history_reason.getApv_history_stmt());
			apv.setApv_history_reason(apv_history_reason.getApv_history_reason());
		}
		
		List<ApprovalDTO> apv_line = dao.getApv_line(apv_idx); // 결재자 아이디, 결재순서
		List<ApprovalDTO> apv_line_info = new ArrayList<ApprovalDTO>();
		
		for (ApprovalDTO approvalDTO : apv_line) { // 결재선 정보
			String emp_idx = approvalDTO.getEmp_id();
			ApprovalDTO line_info = dao.approval_write_go(emp_idx); 
			line_info.setApv_line(approvalDTO.getApv_line());
			
			// 결재 여부
			ApprovalDTO apv_history = dao.apv_line_stmt(apv_idx,emp_idx);
			
			if(apv_history != null) {
				line_info.setApv_history_date(apv_history.getApv_history_date());
				line_info.setApv_history_stmt(apv_history.getApv_history_stmt());
				line_info.setApv_history_reason(apv_history.getApv_history_reason());
			}
			apv_line_info.add(line_info);
		}
		apv.setApv_idx(Integer.parseInt(apv_idx));
		
		if(apv.getApv_stmt().equals("임시저장")) {
			
			mav = new ModelAndView("approval/temporaryApproval_detail");
			
			ArrayList<UtilDTO> detailFile = dao.approval_file(apv_idx);
			
			mav.addObject("form_type","update");
			
			if(detailFile != null) {
				mav.addObject("detailFile", detailFile);
			}
			
			if(apv.getApv_approver() == null) {
				mav.addObject("temporaryTotalName","null");
			}
			
		}else {
			mav.addObject("form_type","detail");
		}
		
		if(apv.getApv_stmt().equals("완료")) {
			List<ApprovalDTO> apvPermList=dao.apvPermList(Integer.parseInt(apv_idx)); 
			
			if(apvPermList != null) {
				mav.addObject("apvPermList", apvPermList);
			}
			
		}
		
		mav.addObject("emp_id",emp_id);
		mav.addObject("apv",apv);
		mav.addObject("dto",dto);
		mav.addObject("apv_line_info",apv_line_info);
		
		return mav;
		
	}


	public String getApproval_detail_do(ApprovalDTO dto) {
		
		dao.getApproval_detail_do(dto); // 결재 히스토리에 기록 insert
		
		String apv_stmt = "";
		int apv_idx = dto.getApv_idx();      
		String apv_approver = dao.apv_line_sel(apv_idx,dto.getApv_approver()); // 다음 순서
		
		if(dto.getApv_history_stmt().equals("결재")) {
			
			if(apv_approver != null) {
				// 다음 결재자 업데이트
				dao.apv_approver(apv_idx, apv_approver);
				
				ApprovalDTO apv_dto = dao.getApproval_detail(apv_idx);
				String apv_subject = apv_dto.getApv_subject();
				if (apv_subject.length() > 20) {
				    apv_subject = apv_subject.substring(0, 20) + "...";
				    apv_dto.setApv_subject(apv_subject);
				}
				dao.alarm(apv_dto);
				
			}else{
				// 다음 결재자 없으면 완료 처리
				apv_stmt = "완료";
				
				String apv_code = dto.getApv_code(); //연차인지 사업기안인지
				String format_apv_idx = formatNumber(apv_idx); // 4를 004 로 변경
				String apv_history_date = apv_history_date(dao.apv_history_date(apv_idx)); // 2024-01-10 -> 20240110 으로 변경 
				String apv_num = apv_code + "-" + apv_history_date + "-" + format_apv_idx ;
				
				dao.apv_update(apv_idx,apv_stmt); // 문서 상태 완료로 표시
				dao.apv_update_num(apv_idx,apv_num); // 문서 번호 표시
				
				if(apv_code.equals("BFVC") || apv_code.equals("AFVC")) {
					// 완료 처리 후 연차 승인 위해 vacation_histroy 테이블에 값 입력 
					ApprovalDTO apv_dto =dao.getApproval_detail(apv_idx);
					// vacation_histroy_insert 함수
					vacation_history_insert(apv_dto);
				}
				
			}
			
		}else if(dto.getApv_history_stmt().equals("반려")){
			apv_stmt = "반려";
			dao.apv_update(apv_idx,apv_stmt);
		}
		
			return "approval/getApproval_list";
	}

	// vacation_history 에 insert 할 내용
	private void vacation_history_insert(ApprovalDTO apv_dto) {
		
		ApprovalDTO vac_dto = new ApprovalDTO(); // vacation_history 에 넣을 DTO
		
		// 공통
		vac_dto.setEmp_id(apv_dto.getEmp_id());
		vac_dto.setDoc_code(apv_dto.getApv_code());
		vac_dto.setVaca_reason("연차 신청");
		vac_dto.setEmp_id_in(apv_dto.getApv_approver());
		
		// 종일인 경우
		if(apv_dto.getApv_vac_type().equals("종일")) {
			
			try {
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            java.util.Date startDate = dateFormat.parse(apv_dto.getApv_start_day());
	            java.util.Date endDate = dateFormat.parse(apv_dto.getApv_end_day());

	            Calendar calendar = Calendar.getInstance();
	            calendar.setTime(startDate);

	            while (!calendar.getTime().after(endDate)) {
	                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

	                // 주말(토요일 또는 일요일)이 아닌 경우에만 출력
	                if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
	                	
	                    String vaca_date = dateFormat.format(calendar.getTime());
	                    String vaca_start = vaca_date + " 09:00:00";
	    				String vaca_end_time = vaca_date + " 18:00:00";
	    				
	    				vac_dto.setVaca_date(vaca_date);
	    				vac_dto.setVaca_start(vaca_start);
	    				vac_dto.setVaca_end_time(vaca_end_time);
	    				vac_dto.setUse_hour(8);
	                }
	                // 다음 날짜로 이동
	                calendar.add(Calendar.DAY_OF_MONTH, 1);
	                
	                dao.vacation_histroy(vac_dto);
	                
	            }
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
			
		}else if(apv_dto.getApv_vac_type().equals("시간")) {
			
			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
	        
	        try {
	        	java.util.Date date1 = inputFormat.parse(apv_dto.getApv_start_time());
	            String vaca_date = outputFormat.format(date1);
	            
	            vac_dto.setVaca_date(vaca_date);
				vac_dto.setVaca_start(apv_dto.getApv_start_time());
				vac_dto.setVaca_end_time(apv_dto.getApv_end_time());
				vac_dto.setUse_hour(Integer.parseInt(apv_dto.getApv_time()));
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        dao.vacation_histroy(vac_dto);
		}
		
		
	}



	// 4 -> 004 변경
	private String formatNumber(int apv_idx) {
		// %03d는 3자리 숫자로 형식화하되, 필요할 경우 0으로 채우는 형식 지정자입니다.
        return String.format("%03d", apv_idx);
	}
	// 2024-01-10 -> 20240110 으로 변경 
	private String apv_history_date(String apv_history_date) {
        // 입력된 문자열 형식 지정
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 출력할 문자열 형식 지정
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyyMMdd");
		String outputDateString = "";
		
        try {
            // 문자열을 Date 객체로 파싱
        	java.util.Date date = inputFormat.parse(apv_history_date);
            // 출력 형식으로 포맷팅
            outputDateString = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDateString;
    }
	
		
	public List<ApprovalDTO> getApproval_list(String emp_id) {
		return dao.getApproval_list(emp_id);
	}

	public List<ApprovalDTO> requestApproval_list(String emp_id) {
		return dao.requestApproval_list(emp_id);
	}
	
	public List<ApprovalDTO> compApproval_list(String emp_id) {
		return dao.compApproval_list(emp_id);
	}

	public List<ApprovalDTO> finishApproval_list(String emp_id) {
		return dao.finishApproval_list(emp_id);
	}

	public List<ApprovalDTO> rejectedApproval_list(String emp_id) {
		return dao.rejectedApproval_list(emp_id);
	}

	public List<ApprovalDTO> temporaryApproval_list(String emp_id) {
		return dao.temporaryApproval_list(emp_id);
	}
	
	public List<ApprovalDTO> viewApproval_list(String emp_id) {
		return dao.viewApproval_list(emp_id);
	}



	public HashMap<String, Object> CompApproval_ViewerPlus(EmployeeDTO loginInfo, ApprovalDTO dto) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		int apv_idx = dto.getApv_idx();
		String total_name = dto.getTotal_name();
		String emp_id_in = loginInfo.getEmp_id();
		
		apv_perm(apv_idx,total_name,emp_id_in);
		
		
		return map;
	}
	
	private void apv_perm(int apv_idx, String total_name, String emp_id_in) {
		
		String pattern = "\\[\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\"(?:,\"(.*?)\")?\\]";
		
		// 정규 표현식에 따라 패턴 찾기
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(total_name);

        // 결과를 담을 리스트
        List<List<String>> resultList = new ArrayList<>();

        while (matcher.find()) {
            List<String> item = new ArrayList<>();
            
            // 그룹의 개수만큼 반복하여 리스트에 추가
            for (int i = 1; i <= matcher.groupCount(); i++) {
                item.add(matcher.group(i));
            }

            // 리스트를 문자열로 변환하여 결과 리스트에 추가
            resultList.add(item);
        }
        
        // apv 의 apv_approver 에 첫번째 결재자 추가
        List<String> item = resultList.get(0);

        // 결과 출력 apv_line 테이블에 모든 결재자 데이터 insert 메서드
        for (int i = 0; i < resultList.size(); i++) {
        	
        	ApprovalDTO dto = new ApprovalDTO();
        	
            List<String> items = resultList.get(i);
            // 리스트에 적어도 4개의 요소가 있는지 확인
            if (items.size() >= 4) {
            	
            	dto.setApv_idx(apv_idx);
            	dto.setEmp_id(items.get(3));
            	dto.setEmp_id_in(emp_id_in);
            	
                dao.apv_perm(dto);
                
            } else {
                System.out.println("리스트에 충분한 요소가 없습니다.");
            }
        }
		
	}

	public HashMap<String, Object> apv_cancel(ApprovalDTO dto) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		dao.apv_cancel(dto.getApv_stmt(),dto.getApv_idx());
		
		map.put("msg","상신취소가 완료되었습니다.");
		
		return map;
	}

	public HashMap<String, Object> compare_time(String emp_id, ApprovalDTO dto) {
		
		HashMap<String, Object> result = new HashMap<>();
		
	    SimpleDateFormat inputFormat = new SimpleDateFormat("h:mma", Locale.ENGLISH); // 7:00am을
	    SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm:ss"); // 07:00:00로
	    SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    
	    String apv_start_time = "";
	    String apv_end_time = "";
	    
	    if (dto.getApv_start_day() != null && !dto.getApv_start_day().isEmpty()) {

	        try {
	        	if (dto.getApv_start_time() != null && !dto.getApv_start_time().isEmpty()) {
	        	    apv_start_time = dto.getApv_start_time();
	        	    java.util.Date date1 = inputFormat.parse(apv_start_time);
	        	    apv_start_time = outputFormat.format(date1);
	        	    apv_start_time = dto.getApv_start_day() + " " + apv_start_time;
	        	}

	        	if (dto.getApv_end_time() != null && !dto.getApv_end_time().isEmpty()) {
	        	    apv_end_time = dto.getApv_end_time();
	        	    java.util.Date date2 = inputFormat.parse(apv_end_time);
	        	    apv_end_time = outputFormat.format(date2);
	        	    apv_end_time = dto.getApv_start_day() + " " + apv_end_time;
	        	}
	        } catch (Exception e) {
	            // 파싱 예외 처리
	            e.printStackTrace();
	            return null;
	        }

	        WorkDTO workDTO = dao.commute_history(dto.getApv_start_day(), emp_id);
	        
	        String work_start_time = "";
	        String work_end_time = "";
	        
	        if(workDTO != null) {
	        	work_start_time = workDTO.getWork_start_time();
	        	work_end_time = workDTO.getWork_end_time();
	        }else {
	        	work_start_time = dto.getApv_start_day() + " 09:00:00";
	        	work_end_time = dto.getApv_start_day() + " 18:00:00";
	        }



	        try {
	        // apv_start_time과 work_start_time 비교
	        	java.util.Date workStartDate = Format.parse(work_start_time);
	        	java.util.Date workEndDate = Format.parse(work_end_time);
	        	
			    if (!apv_start_time.isEmpty() && !work_start_time.isEmpty()) {
			    	
			            java.util.Date apvStartDate = Format.parse(apv_start_time);
		
			            if (apvStartDate.before(workStartDate) || apvStartDate.after(workEndDate)) {
			                // apv_start_time의 시간이 더 빠릅니다.
			            	result.put("msg", "출근하실 시간은 "+work_start_time+" 입니다. 확인 후 다시 입력해주세요.");
			            	result.put("status", "error");
			            } else {
			            	result.put("status", "success");
			            }
			    }
			    
			    if (!apv_end_time.isEmpty() && !work_end_time.isEmpty()) {
			    	
	                java.util.Date apvEndDate = Format.parse(apv_end_time);

	                if (apvEndDate.after(workEndDate) || apvEndDate.before(workStartDate)) {
	                    // apv_end_time의 시간이 더 느립니다.
	                	result.put("msg", "퇴근하실 시간은 "+work_end_time+" 입니다. 확인 후 다시 입력해주세요.");
	                	result.put("status", "error");
	                } else {
	                	result.put("status", "success");
	                }
			    }
			    
	        } catch (Exception e) {
	                // 비교 예외 처리
	                e.printStackTrace();
	                result.put("status", "error");
	                return null;
	        }
	    }
	    
	    // 필요한 경우 메시지를 포함한 결과를 반환할 수 있습니다.
	    return result;
		
	}



	public ArrayList<UtilDTO> approval_file(String apv_idx) {
		return dao.approval_file(apv_idx);
	}

	public HashMap<String, Object> temporary_apv_del(String apv_idx) {
		
	HashMap<String, Object> map = new HashMap<String, Object>();
				
	dao.temporary_apv_del(apv_idx);
				
	map.put("msg","문서가 삭제되었습니다.");
	
	return map;
	}



	public int addApv_perm(String loginId, int apv_idx, List<Map<String, String>> emp_data) {
		
		List<String> empIds = new ArrayList<>();
		
		// emp_data에 있는 각 Map에서 emp_id를 추출하여 추가
		for (Map<String, String> empDataMap : emp_data) {
		    String empId = empDataMap.get("emp_id");
		    empIds.add(empId);
		}
	
		// chat_pp에 empId를 반복하여 데이터 삽입
		for (String empId : empIds) {
			dao.addApv_perm(apv_idx,empId, loginId);
		}
		
		return apv_idx;
		
	}


	public List<ApprovalDTO> apvPermList(int apv_idx) {
		return dao.apvPermList(apv_idx);
	}



	public int delApvPerm(int apv_idx, String emp_id) {
		return dao.delApvPerm(apv_idx,emp_id);
	}





	
}
