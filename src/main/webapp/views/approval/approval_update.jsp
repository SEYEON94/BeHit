<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html
  lang="en"
  class="light-style layout-navbar-fixed layout-menu-fixed layout-compact"
  dir="ltr"
  data-theme="theme-default"
  data-assets-path="../../assets/"
  data-template="vertical-menu-template">
  <head>
    <meta charset="utf-8" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />

	<title>BeHit</title>
	
    <meta name="description" content="" />

    <!-- Favicon -->
    <link rel="icon" type="image/x-icon" href="../../assets/img/favicon/favicon.ico" />
    
    <!-- bootstrap 아이콘 -->
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
		<!-- pretendard 폰트 -->
		<link rel="stylesheet" type="text/css" href='https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css'>

    <!-- Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Public+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&display=swap"
      rel="stylesheet" />

    <!-- Icons -->
    <link rel="stylesheet" href="../../assets/vendor/fonts/boxicons.css" />
    <link rel="stylesheet" href="../../assets/vendor/fonts/fontawesome.css" />
    <link rel="stylesheet" href="../../assets/vendor/fonts/flag-icons.css" />

    <!-- Core CSS -->
    <link rel="stylesheet" href="../../assets/vendor/css/rtl/core.css" class="template-customizer-core-css" />
    <link rel="stylesheet" href="../../assets/vendor/css/rtl/theme-default.css" class="template-customizer-theme-css" />
    <link rel="stylesheet" href="../../assets/css/demo.css" />

    <!-- Vendors CSS -->
    <link rel="stylesheet" href="../../assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.css" />
    <link rel="stylesheet" href="../../assets/vendor/libs/typeahead-js/typeahead.css" />
    <link rel="stylesheet" href="../../assets/vendor/libs/select2/select2.css" />
    <link rel="stylesheet" href="../../assets/vendor/libs/flatpickr/flatpickr.css" />
		<link rel="stylesheet" href="../../assets/vendor/libs/datatables-bs5/datatables.bootstrap5.css" />
    <link rel="stylesheet" href="../../assets/vendor/libs/datatables-responsive-bs5/responsive.bootstrap5.css" />
    <link rel="stylesheet" href="../../assets/vendor/libs/datatables-checkboxes-jquery/datatables.checkboxes.css" />
    <link rel="stylesheet" href="../../assets/vendor/libs/datatables-buttons-bs5/buttons.bootstrap5.css" />
    <link rel="stylesheet" href="../../assets/vendor/libs/bootstrap-select/bootstrap-select.css" />
    <link rel="stylesheet" href="../../assets/vendor/libs/quill/typography.css" />
    <link rel="stylesheet" href="../../assets/vendor/libs/quill/katex.css" />
    <link rel="stylesheet" href="../../assets/vendor/libs/quill/editor.css" />
    <link rel="stylesheet" href="../../assets/vendor/libs/sweetalert2/sweetalert2.css" />
    
    
		<!-- Row Group CSS -->
    <link rel="stylesheet" href="../../assets/vendor/libs/datatables-rowgroup-bs5/rowgroup.bootstrap5.css" />
    <!-- Form Validation -->
    <link rel="stylesheet" href="../../assets/vendor/libs/@form-validation/umd/styles/index.min.css" />
	
    <!-- Page CSS -->

    <!-- Helpers -->
    <script src="../../assets/vendor/js/helpers.js"></script>
    <!--! Template customizer & Theme config files MUST be included after core stylesheets and helpers.js in the <head> section -->
    <!--? Template customizer: To hide customizer set displayCustomizer value false in config.js.  -->
    <!--? Config:  Mandatory theme config file contain global vars & default theme options, Set your preferred theme option in this file.  -->
    <script src="../../assets/js/config.js"></script>
    
    <!-- custom CSS -->
    <style>
    
    button{
			background: inherit;
			border:none;
			box-shadow:none;
			border-radius:0;
			padding:0;
			overflow:visible;
			cursor:pointer;
			}
   
    .col-lg-4{
    	width:20%;
    }
    
    .col-lg-8{
     width:80%;
    }
    
    .mb-1{
    	margin-bottom:0.1rem !important;
    }
    
    h5{
    	font-family:pretendard;
    	font-weight:600;
    }
    
    h4{
    	font-family:pretendard;
    	font-weight:600;
    }
    
    .apv-home{
    	color:#c20000;
    	margin-bottom:2rem;
    }
    
    .apv-title{
    	margin-top:0.125rem;
    } 
   
    .apv-menu{
    	margin-top: 1rem;
    }
    
    .align-middle{
    	font-family:pretendard;
    	font-weight:500;
    }
    
    .apv-form-menu{
    	display:flex;
    	justify-content: flex-end;
    }
    
    .apv-form-menu .apv-form-menu-cnt{
    	margin-left:1rem;
    	margin-bottom:1rem;
    }
    
    .card{
    	margin-bottom:3rem;
    	display:flex;
    }
    
    .btn.btn-secondary{
    	margin-top:1rem;
   		font-family:pretendard;
    	margin-bottom:0.625rem;
    }
    
    
    /*휴가신청서*/
    
    .apv-form-vac{
    	margin: 1rem;
    	display:flex;
    	justify-content: center;
    }
    
    .apv-form-vac .table-header,
    .apv-form-vac .table-content{
    	width: 800px;
    	color: black;
    	background: white;
    	font-size: 12px;
    }
    
    .apv-form-vac .table-subject{
    	width: 300px;
	    padding: 3px !important;
	    border: 0px solid black;
	    height: 90px !important;
	    font-size: 22px;
	    font-weight: 600;
	    text-align: center;
	    vertical-align: middle;
    }
    
    .apv-form-vac .table-header-left{
    	width:250px;
    }
    
    .apv-form-vac .table-header-left-table{
	    border: 1px solid black;
	    font-size: 12px;
	    border-collapse: collapse !important;
    }
    
    .apv-form-vac .table-header-left-table-left{
    	width: 100px;
	    height: 22px;
	    vertical-align: middle;
	    border: 1px solid black;
	    text-align: center;
	    font-weight: bold;
	    background: rgb(221, 221, 221);
	    padding: 3px !important;
    }
    
    .apv-form-vac .table-header-left-table-right{
    	width: 150px;
	    height: 22px;
	    vertical-align: middle;
	    border: 1px solid black;
	    text-align: left;
	    padding: 3px !important;
    }
    
    .apv-form-vac .table-header-right{
			width: 500px;
	    text-align: right;
	    padding: 0px!important;
	    border: 0!important;
	    vertical-align: top !important;
    }
    
    .apv-form-vac .apv-sign{
    	display: inline-block;
    	margin-right: -1px;
    }
    
    .apv-form-vac .apv-sign-table{
    	border:1px solid black;
	    border-collapse: collapse;
    }
    
    .apv-form-vac .apv-sign-table tbody tr th{
    	width: 15px;
	    padding: 6px;
	    text-align: center;
	    word-break: break-word;
	    white-space: normal;
	    background: rgb(221, 221, 221);
    }
    
    .apv-form-vac .apv-sign-table tbody tr td{
			padding: 0!important;
    }
    
    .apv-form-vac .apv-sign-table-right tbody tr{
    	border-bottom:1px solid black;
    	border-left:1px solid black;
	    border-collapse: collapse;
    }
    
    .apv-form-vac .apv-sign-table-right tbody tr.last{
    	border-bottom:0;
    }
    
    
    .apv-form-vac .apv-sign-line{
    	height: 26px;
	    min-height: 26px;
    }
    
    .apv-form-vac .apv-sign-line-dept,
    .apv-form-vac .apv-sign-line-name,
    .apv-form-vac .apv-sign-line-date
     {
    	display: block;
	    text-align: center;
	    overflow: hidden;
	    text-overflow: ellipsis;
	    white-space: nowrap;
	    width: 70px;
	    font-size: 12px;
	    padding: 2px 4px;
    }
    
    .apv-form-vac .apv-sign-line-name{
    	height: 74px;
    	text-align: center;
    }
    
    /*table-content*/
    
    .apv-form-vac .table-content-left{
    	background: rgb(221, 221, 221);
	    padding: 5px;
	    border: 1px solid black;
	    height: 25px;
	    text-align: center;
	    color: rgb(0, 0, 0);
	    font-size: 14px;
	    font-weight: bold;
	    vertical-align: middle;
    }
    
    .apv-form-vac .table-content-right{
    	padding: 3px;
	    border: 1px solid black;
	    width: 700px;
	    height: 38px;
	    text-align: left;
	    color: rgb(0, 0, 0);
	    font-size: 12px;
	    vertical-align: middle;
	    background: rgb(255, 255, 255);
    }
    
    .apv-form-vac .table-content-right.reason{
	    height: 282px;
    }
    
    .table-content-right .form-control{
    	display: inline-block;
    	width: 30%;
    }
    
    .table-upload{
    	width: 100%;
    	margin-top:4rem;
    }
    
    .table-upload-left{
    	height: 26px;
	    width: 100px;
	    text-align: center;
	    color:black;
    }
    
    .table-upload-right .form-control{
    	display: inline-block;
    	width: 100%;
    }
    
    .apv-form-button{
    	display:flex;
    	justify-content: flex-end;
    	margin: 1rem 0;
    	font-family:pretendard;
    }
    
    .apv-form-button button[type="submit"]{
    	margin-right: 5.125rem;
    }
    
    .apv-form-button button[type="reset"]{
    	margin-right:1rem;
    }
    
    .text-truncate{
    	font-family:pretendard;
    }
    
    
    


    </style>
    
    
  </head>

  <body>
    <!-- Layout wrapper -->
    <div class="layout-wrapper layout-content-navbar">
      <div class="layout-container">
        <!-- Menu -->

        <aside id="layout-menu" class="layout-menu menu-vertical menu bg-menu-theme">
          <div class="app-brand demo">
            <a href="../home.go" class="app-brand-link">
              <span class="app-brand-logo demo">
              	<img src="../../assets/img/branding/logo.png" class="logo_beHit" width="96px"/>
              </span>
            </a>

            <a href="javascript:void(0);" class="layout-menu-toggle menu-link text-large ms-auto">
              <i class="bx bx-chevron-left bx-sm align-middle"></i>
            </a>
          </div>

          <div class="menu-inner-shadow"></div>

          <ul class="menu-inner py-1">
            <!-- Dashboards -->
            <li class="menu-item">
              <a href="../home.go" class="menu-link">
                <i class="menu-icon tf-icons bx bx-home-circle"></i>
                <div class="text-truncate" data-i18n="홈">홈</div>
              </a>
            </li>
            
            <li class="menu-item active">
              <a href="../approval/approval_main.go" class="menu-link">
                <i class="menu-icon tf-icons bx bx-food-menu"></i>
                <div class="text-truncate" data-i18n="전자 결재">전자 결재</div>
              </a>
            </li>
            
            <li class="menu-item">
              <a href="../creators/creator_list_all.go" class="menu-link">
                 <i class="menu-icon tf-icons bx bx-slideshow"></i>
                <div class="text-truncate" data-i18n="크리에이터">크리에이터</div>
              </a>
            </li>
            
            <li class="menu-item">
              <a href="../project/project_main.go" class="menu-link">
                 <i class="menu-icon tf-icons bx bx-customize"></i>
                <div class="text-truncate" data-i18n="프로젝트">프로젝트</div>
              </a>
            </li>
            
            <li class="menu-item">
              <a href="../calendar/calendar.go" class="menu-link">
                 <i class="menu-icon tf-icons bx bx-calendar"></i>
                <div class="text-truncate" data-i18n="캘린더">캘린더</div>
              </a>
            </li>
            
            <li class="menu-item">
              <a href="../reserve/reserveRoom_list.go" class="menu-link">
                 <i class="menu-icon tf-icons bx bx-time-five"></i>
                <div class="text-truncate" data-i18n="예약">예약</div>
              </a>
            </li>
            
            <li class="menu-item">
              <a href="javascript:void(0);" class="menu-link menu-toggle">
                <i class="menu-icon tf-icons bx bx-pie-chart-alt-2"></i>
                <div class="text-truncate" data-i18n="근태관리">근태관리</div>
              </a>

              <ul class="menu-sub">
              	<li class="menu-item">
                  <a href="../myHr/mhr_timeline.go" class="menu-link">
                    <div class="text-truncate" data-i18n="내 근태관리">내 근태관리</div>
                  </a>
                </li>
                <li class="menu-item">
                  <a href="../myHr/mhr_vacation.go" class="menu-link">
                    <div class="text-truncate" data-i18n="내 연차내역">내 연차내역</div>
                  </a>
                </li>
              </ul>
            </li>
            
            <li class="menu-item">
              <a href="../chat/messenger.go" class="menu-link">
                 <i class="menu-icon tf-icons bx bx-chat"></i>
                <div class="text-truncate" data-i18n="메신저">메신저</div>
              </a>
            </li>
            
            <li class="menu-item">
              <a href="javascript:void(0);" class="menu-link menu-toggle">
                <i class="menu-icon tf-icons bx bx-user"></i>
                <div class="text-truncate" data-i18n="인사 관리">인사 관리</div>
              </a>
              <ul class="menu-sub">
                <li class="menu-item">
                  <a href="../employee/employee_list.go" class="menu-link">
                    <div class="text-truncate" data-i18n="직원 관리">직원 관리</div>
                  </a>
                </li>
                <li class="menu-item">
                  <a href="javascript:void(0);" class="menu-link menu-toggle">
                    <div class="text-truncate" data-i18n="근태 관리">근태 관리</div>
                  </a>
                  <ul class="menu-sub">
                    <li class="menu-item">
                      <a href="../employee/workHour_list.go" class="menu-link">
                        <div class="text-truncate" data-i18n="근태 현황">근태 현황</div>
                      </a>
                    </li>
                    <li class="menu-item">
                      <a href="../employee/vacation_list.go" class="menu-link">
                        <div class="text-truncate" data-i18n="연차 관리">연차 관리</div>
                      </a>
                    </li>
                  </ul>
                </li>
              </ul>
            </li>
          </ul>
        </aside>
        <!-- / Menu -->

        <!-- Layout container -->
        <div class="layout-page">
        
        <!-- Navbar -->
          <nav
            class="layout-navbar container-xxl navbar navbar-expand-xl navbar-detached align-items-center bg-navbar-theme"
            id="layout-navbar">
            <div class="layout-menu-toggle navbar-nav align-items-xl-center me-3 me-xl-0 d-xl-none">
              <a class="nav-item nav-link px-0 me-xl-4" href="javascript:void(0)">
                <i class="bx bx-menu bx-sm"></i>
              </a>
            </div>

            <div class="navbar-nav-right d-flex align-items-center" id="navbar-collapse">
              <!-- Search -->
              <div class="navbar-nav align-items-center">
              </div>
              <!-- /Search -->

              <ul class="navbar-nav flex-row align-items-center ms-auto">
                <!-- Language -->
                <li class="nav-item dropdown-language dropdown me-2 me-xl-0">
                </li>
                <!-- /Language -->

                <!-- Quick links  -->
                <li class="nav-item dropdown-shortcuts navbar-dropdown dropdown me-2 me-xl-0">
                </li>
                <!-- Quick links -->

                <!-- Style Switcher -->
                <li class="nav-item dropdown-style-switcher dropdown me-2 me-xl-0">
                </li>
                <!-- / Style Switcher-->

                <!-- Notification -->
                <li class="nav-item dropdown-notifications navbar-dropdown dropdown me-3 me-xl-1">
                  <a
                    class="nav-link dropdown-toggle hide-arrow"
                    href="javascript:void(0);"
                    data-bs-toggle="dropdown"
                    data-bs-auto-close="outside"
                    aria-expanded="false">
                    <i class="bx bx-bell bx-sm"></i>
                    <span class="badge bg-danger rounded-pill badge-notifications">5</span>
                  </a>
                  <ul class="dropdown-menu dropdown-menu-end py-0">
                  
                    <li class="dropdown-menu-header border-bottom">
                      <div class="dropdown-header d-flex align-items-center py-3">
                        <h5 class="text-body mb-0 me-auto">알림</h5>
                        <a
                          href="javascript:void(0)"
                          class="dropdown-notifications-all text-body"
                          data-bs-toggle="tooltip"
                          data-bs-placement="top"
                          title="Mark all as read"
                          ><i class="bx fs-4 bx-envelope-open"></i
                        ></a>
                      </div>
                    </li>
                    
                    <li class="dropdown-notifications-list scrollable-container">
                      <ul class="list-group list-group-flush">
                        <li class="list-group-item list-group-item-action dropdown-notifications-item">
                          <div class="d-flex">
                            <div class="flex-shrink-0 me-3">
                              <div class="avatar">
                                <img src="../../assets/img/avatars/1.png" alt class="w-px-40 h-auto rounded-circle" />
                              </div>
                            </div>
                            <div class="flex-grow-1">
                              <h6 class="mb-1">윤예성님이 메세지를 보냈습니다 메세지 확인해보세요~~~~~~~~</h6>
                              <small class="text-muted">11:00</small>
                            </div>
                            <div class="flex-shrink-0 dropdown-notifications-actions">
                              <a href="javascript:void(0)" class="dropdown-notifications-read"
                                ><span class="badge badge-dot"></span
                              ></a>
                              <a href="javascript:void(0)" class="dropdown-notifications-archive"
                                ><span class="bx bx-x"></span
                              ></a>
                            </div>
                          </div>
                        </li>
                      </ul>
                    </li>
                    
                    <li class="dropdown-menu-footer border-top p-3">
                      <button class="btn btn-primary text-uppercase w-100">알림 전체 삭제</button>
                    </li>
                    
                  </ul>
                </li>
                <!--/ Notification -->
                <!-- User -->
                <li class="nav-item navbar-dropdown dropdown-user dropdown">
                  <a class="nav-link dropdown-toggle hide-arrow" href="javascript:void(0);" data-bs-toggle="dropdown">
                    <div class="avatar avatar-online">
                      <img src="../../assets/img/avatars/1.png" alt class="w-px-40 h-auto rounded-circle" />
                    </div>
                  </a>
                  <ul class="dropdown-menu dropdown-menu-end">
                    <li>
                      <a class="dropdown-item" href="#">
                        <div class="d-flex">
                          <div class="flex-shrink-0 me-3">
                            <div class="avatar avatar-online">
                              <img src="../../assets/img/avatars/1.png" alt class="w-px-40 h-auto rounded-circle" />
                            </div>
                          </div>
                          <div class="flex-grow-1">
                            <span class="fw-medium d-block">John Doe</span>
                            <small class="text-muted">Admin</small>
                          </div>
                        </div>
                      </a>
                    </li>
                    <li>
                      <div class="dropdown-divider"></div>
                    </li>
                    <li>
                      <a class="dropdown-item" href="auth-login-cover.go" target="_blank">
                        <i class="bx bx-power-off me-2"></i>
                        <span class="align-middle">Log Out</span>
                      </a>
                    </li>
                  </ul>
                </li>
                <!--/ User -->
              </ul>
            </div>
          </nav>

          <!-- / Navbar -->

          <!-- Content wrapper -->
          <div class="content-wrapper">
            <!-- Content -->

            <div class="container-xxl flex-grow-1 container-p-y">
              <!-- <h4 class="py-3 mb-4">전자 결재</h4> -->

              <div class="row g-4">
                <!-- Navigation -->
                <div class="col-12 col-lg-4">
                  <div class="d-flex justify-content-between flex-column mb-3 mb-md-0">
                    <ul class="nav nav-align-left nav-pills flex-column">
                    
                    	<li class="nav-item mb-1">
                    		<h4 class="apv-title">
                    			전자 결재
                    		</h4>
                    	</li>
                    	
                    	<li class="nav-item mb-1">
                    		<button type="button" class="btn btn-secondary">새 결재 작성</button>
                    	</li>
                    
                    	<li class="nav-item mb-1">
                    		<h5 class="apv-menu">
                    			<i class="menu-icon tf-icons bx bx-food-menu"></i>
                    			결재하기
                    		</h5>
                    	</li>
                      <li class="nav-item mb-1">
                        <a class="nav-link" href="getApproval_list.go">
                          <span class="align-middle">결재 요청 받은 문서</span>
                        </a>
                      </li>
                      <li class="nav-item mb-1">
                        <a class="nav-link" href="compApproval_list.go">
                          <span class="align-middle">결재 완료한 문서</span>
                        </a>
                      </li>
                      <li class="nav-item mb-1">
                        <a class="nav-link" href="viewApproval_list.go">
                          <span class="align-middle">열람 가능한 문서</span>
                        </a>
                      </li>
                      <li class="nav-item mb-1">
                    		<h5 class="apv-menu">
                    			<i class="menu-icon tf-icons bx bx-food-menu"></i>
                    			상신한 결재
                    		</h5>
                    	</li>
                      <li class="nav-item mb-1">
                        <a class="nav-link" href="requestApproval_list.go">
                          <span class="align-middle">결재 요청한 문서</span>
                        </a>
                      </li>
                      <li class="nav-item mb-1">
                        <a class="nav-link" href="finishApproval_list.go">
                          <span class="align-middle">결재 완료된 문서</span>
                        </a>
                      </li>
                      <li class="nav-item mb-1">
                        <a class="nav-link" href="temporaryApproval_list.go">
                          <span class="align-middle">임시 저장된 문서</span>
                        </a>
                      </li>
                      <li class="nav-item mb-1">
                        <a class="nav-link" href="rejectedApproval_list.go">
                          <span class="align-middle">반려된 문서</span>
                        </a>
                      </li>
                    </ul>
                  </div>
                </div>
                <!-- /Navigation -->

                <!-- Options -->
                <div class="col-12 col-lg-8 pt-4 pt-lg-0">
                	<h4 class="apv-home">휴가 신청서 작성</h4>
                	<div class="apv-form-menu">
                		<span class="text-truncate">
                			<button type="button" id="confirm-text" class="apv-form-menu-cnt"><i class='bx bx-plus'></i> 결재선 추가</button>
                		</span>
                		<span class="text-truncate">
                			<button type="button" id="confirm-text" class="apv-form-menu-cnt"><i class='bx bxs-file-blank'></i> 미리보기</button>
                		</span>
                		<span class="text-truncate">
                			<button type="button" id="confirm-text" class="apv-form-menu-cnt"><i class='bx bxs-archive-in'></i> 임시 저장</button>
                		</span>
                	</div>
                  <div class="tab-content p-0">
                    <!-- Store Details Tab -->
                    <div class="tab-pane fade show active" id="store_details" role="tabpanel">
        
				              <div class="card">
				              	<div class="apv-form-vac">
					              	<form>
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
					              									<td class="table-header-left-table-right">김민지</td>
					              								</tr>
					              								<tr>
					              									<td class="table-header-left-table-left">부서</td>
					              									<td class="table-header-left-table-right">인사팀</td>
					              								</tr>
					              								<tr>
					              									<td class="table-header-left-table-left">기안일</td>
					              									<td class="table-header-left-table-right">2023-12-22</td>
					              								</tr>
					              								<tr>
					              									<td class="table-header-left-table-left">문서번호</td>
					              									<td class="table-header-left-table-right"></td>
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
					              						<select id="selectpickerBasic" class="selectpicker w-20" data-style="btn-default">
					                            <option>종일</option>
					                            <option>시간</option>
					                          </select>
					              					</td>
					              				</tr>
					              				<tr>
					              					<td class="table-content-left">연차 기간</td>
					              					<td class="table-content-right">
					              						<input type="text" class="form-control" placeholder="YYYY-MM-DD" id="flatpickr-date-before" />
					              						<span>~</span>
					              						<input type="text" class="form-control" placeholder="YYYY-MM-DD" id="flatpickr-date-after" />
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
					              		
					              		<table class="table-upload">
					              			<tbody>
					              				<tr>
					              					<td class="table-upload-left">파일 첨부</td>
					              					<td class="table-upload-right">
					              						<input class="form-control" type="file" id="formFile" />
					              					</td>
					              				</tr>
					              			</tbody>
					              		</table>
					              		
					              	</form>
				                </div>
				                
				                <div class="pt-4 apv-form-button">
			                    <button type="reset" class="btn btn-label-secondary">취소</button>
			                    <button type="submit" class="btn btn-primary">상신</button>
			                  </div>
				                
				              </div>


                    </div>
                  </div>
                </div>
                <!-- /Options-->
              </div>
            </div>
            <!-- / Content -->
            
            
            
            <div class="content-backdrop fade"></div>
          </div>
          <!-- Content wrapper -->
        </div>
        <!-- / Layout page -->
      </div>

      <!-- Overlay -->
      <div class="layout-overlay layout-menu-toggle"></div>

      <!-- Drag Target Area To SlideIn Menu On Small Screens -->
      <div class="drag-target"></div>
    </div>
    <!-- / Layout wrapper -->

    <!-- Core JS -->
    <!-- build:js assets/vendor/js/core.js -->

    <script src="../../assets/vendor/libs/jquery/jquery.js"></script>
    <script src="../../assets/vendor/libs/popper/popper.js"></script>
    <script src="../../assets/vendor/js/bootstrap.js"></script>
    <script src="../../assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.js"></script>
    <script src="../../assets/vendor/libs/hammer/hammer.js"></script>
    <script src="../../assets/vendor/libs/i18n/i18n.js"></script>
    <script src="../../assets/vendor/libs/typeahead-js/typeahead.js"></script>
    <script src="../../assets/vendor/js/menu.js"></script>

    <!-- endbuild -->

    <!-- Vendors JS -->
    <script src="../../assets/vendor/libs/select2/select2.js"></script>
    <script src="../../assets/vendor/libs/cleavejs/cleave.js"></script>
    <script src="../../assets/vendor/libs/cleavejs/cleave-phone.js"></script>
    <script src="../../assets/vendor/libs/datatables-bs5/datatables-bootstrap5.js"></script>
    <script src="../../assets/vendor/libs/bootstrap-select/bootstrap-select.js"></script>
    <script src="../../assets/vendor/libs/bootstrap-datepicker/bootstrap-datepicker.js"></script>
    <script src="../../assets/vendor/libs/bootstrap-daterangepicker/bootstrap-daterangepicker.js"></script>
    <script src="../../assets/vendor/libs/jquery-timepicker/jquery-timepicker.js"></script>
    <script src="../../assets/vendor/libs/pickr/pickr.js"></script>
    <script src="../../assets/vendor/libs/quill/katex.js"></script>
    <script src="../../assets/vendor/libs/quill/quill.js"></script>
    <script src="../../assets/vendor/libs/sweetalert2/sweetalert2.js"></script>
    
    <!-- Flat Picker -->
    <script src="../../assets/vendor/libs/moment/moment.js"></script>
    <script src="../../assets/vendor/libs/flatpickr/flatpickr.js"></script>
    
    <!-- Form Validation -->
    <script src="../../assets/vendor/libs/@form-validation/umd/bundle/popular.min.js"></script>
    <script src="../../assets/vendor/libs/@form-validation/umd/plugin-bootstrap5/index.min.js"></script>
    <script src="../../assets/vendor/libs/@form-validation/umd/plugin-auto-focus/index.min.js"></script>

    <!-- Main JS -->
    <script src="../../assets/js/main.js"></script>

    <!-- Page JS -->
    <script src="../../assets/js/app-ecommerce-settings.js"></script>
    <script src="../../assets/js/forms-editors.js"></script>
    
    <!-- custom JS -->
    <script>
    
    // sweetAlert
    
(function () {
  const confirmText = document.querySelector('#confirm-text');
    
    if (confirmText) {
        confirmText.onclick = function () {
          Swal.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Yes, delete it!',
            customClass: {
              confirmButton: 'btn btn-primary me-3',
              cancelButton: 'btn btn-label-secondary'
            },
            buttonsStyling: false
          }).then(function (result) {
            if (result.value) {
              Swal.fire({
                icon: 'success',
                title: 'Deleted!',
                text: 'Your file has been deleted.',
                customClass: {
                  confirmButton: 'btn btn-success'
                }
              });
            }
          });
        };
      }
})();
    
    
    // 날짜 input
    (function () {
    	  // Flat Picker
    	  // --------------------------------------------------------------------
    	  const flatpickrDateBefore = document.querySelector('#flatpickr-date-before');
    	  const flatpickrDateAfter = document.querySelector('#flatpickr-date-after');

    	  // Date
    	  if (flatpickrDateBefore) {
    	    flatpickrDateBefore.flatpickr({
    	      monthSelectorType: 'static'
    	    });
    	  }
    	  
    	  if (flatpickrDateAfter) {
      	    flatpickrDateAfter.flatpickr({
      	      monthSelectorType: 'static'
      	    });
      	  }
    	  
    })();
    
    
    </script>
  </body>
</html>
