<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<table class="table-header">
			<tbody>
				<tr>
					<td class="table-subject" colspan="2">연차신청서</td>
				</tr>
				<tr>
					<td class="table-header-left">
						<table class="table-header-left-table">
							<tbody>
								<tr>
									<td class="table-header-left-table-left">기안자</td>
									<td class="table-header-left-table-right name">김민지</td>
								</tr>
								<tr>
									<td class="table-header-left-table-left">부서</td>
									<td class="table-header-left-table-right dept">인사</td>
								</tr>
								<tr>
									<td class="table-header-left-table-left">기안일</td>
									<td class="table-header-left-table-right date">2023-12-22</td>
								</tr>
								<tr>
									<td class="table-header-left-table-left">문서번호</td>
									<td class="table-header-left-table-right number"></td>
								</tr>
							</tbody>
						</table>
					</td>
					<td class="table-header-right">
						<div class="apv-sign">
							<table class="apv-sign-table">
								<tbody>
									<tr>
										<th>승인</th>
										
										<!-- 결재선 추가 -->
										<td class="apv-sign-table-right apv-sign1">
											<table>
												<tbody>
														
															<tr>
																<td>
																	<span class="apv-sign-line-dept">대리</span>
																</td>
															</tr>
															
															<tr>
																<td>
																	<span class="apv-sign-line-name">이유빈</span>
																</td>
															</tr>
															
															<tr class="last">
																<td>
																	<span class="apv-sign-line-date">2023-12-12</span>
																</td>
															</tr>
															
														</tbody>
													</table>
												</td>
												
												<td class="apv-sign-table-right">
											<table>
												<tbody>
														
															<tr>
																<td>
																	<span class="apv-sign-line-dept">대리</span>
																</td>
															</tr>
															
															<tr>
																<td>
																	<span class="apv-sign-line-name">이유빈</span>
																</td>
															</tr>
															
															<tr class="last">
																<td>
																	<span class="apv-sign-line-date">2023-12-12</span>
																</td>
															</tr>
															
														</tbody>
													</table>
												</td>
												
											</tr>
										</tbody>
									</table>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				<table class="table-content">
					<tbody>
						<tr>
							<td class="table-content-left">연차 종류</td>
							<td class="table-content-right">
								<select id="selectpickerBasic" class="selectpicker w-20 form-vac-time" data-style="btn-default">
                   <option>종일</option>
                   <option>시간</option>
						    </select>
							</td>
						</tr>
						<tr>
							<td class="table-content-left">연차 기간</td>
							<td class="table-content-right">
								<input type="text" class="form-control form-vac-time-start" placeholder="YYYY-MM-DD" id="flatpickr-date-before" />
								<span>~</span>
								<input type="text" class="form-control form-vac-time-end" placeholder="YYYY-MM-DD" id="flatpickr-date-after" />
								<span class="vac-time">시간</span>
							</td>
						</tr>
						<tr>
							<td class="table-content-left">연차 사유</td>
							<td class="table-content-right reason">
								<div>
						      <div id="snow-toolbar">
						        <span class="ql-formats">
						          <select class="ql-font"></select>
						          <select class="ql-size"></select>
						        </span>
						        <span class="ql-formats">
						          <button class="ql-bold"></button>
						          <button class="ql-italic"></button>
						          <button class="ql-underline"></button>
						          <button class="ql-strike"></button>
						        </span>
						        <span class="ql-formats">
						          <select class="ql-color"></select>
						          <select class="ql-background"></select>
						        </span>
						        <span class="ql-formats">
						          <button class="ql-script" value="sub"></button>
						          <button class="ql-script" value="super"></button>
						        </span>
						        <span class="ql-formats">
						          <button class="ql-header" value="1"></button>
						          <button class="ql-header" value="2"></button>
						          <button class="ql-blockquote"></button>
						          <button class="ql-code-block"></button>
						        </span>
						      </div>
						      <div id="snow-editor">
	                </div>
								</div>
	  					</td>
	  				</tr>
	  			</tbody>
	  		</table>