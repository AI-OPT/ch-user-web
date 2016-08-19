<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="uedroot" value="${pageContext.request.contextPath}/template/default"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>合同管理页面</title>
<%@include file="/inc/inc.jsp" %>
<script src="${uedroot}/scripts/modular/frame.js"></script>  
<link rel="stylesheet" type="text/css" href="${uedroot}/css/modular/modular.css"/>
</head>
<body>

<div id="theme-wrapper">
    <header class="navbar" id="header-navbar">
        <div class="container">
            <a href="index.html" id="logo" class="navbar-brand"><img  src="../img/logo.png" alt="" class="normal-logo logo-white"/></a>
        <div class="clearfix">
        <!--操作菜单-->
            <button class="navbar-toggle" data-target=".navbar-ex1-collapse" data-toggle="collapse" type="button">
            <span class="sr-only"></span>
            <span class="fa fa-bars"></span>
            </button>
         <!--隐藏菜单icon-->
            <div class="nav-no-collapse navbar-left pull-left hidden-sm hidden-xs">
                <ul class="nav navbar-nav pull-left">
               	 <li><a class="btn" id="make-small-nav"><i class="fa fa-bars"></i></a></li>
                </ul>   
            </div>
        <!--/隐藏菜单icon结束-->
        <!--右侧导航-->
            <div class="nav-no-collapse pull-right" id="header-nav">
            <ul class="nav navbar-nav pull-right">
       
         <!--颜色设置-->     
      	 <li class="dropdown hidden-xs notifications-list">
                 <a class="btn dropdown-toggle" data-toggle="dropdown"><i class="fa fa-cog"></i></a>
		 <ul class="dropdown-menu color-pifu" id="skin-colors" >
               	 	<li>
		            <a class="skin-changer" data-skin="theme-whbl" data-toggle="tooltip" title="蓝色" style="background-color: #3498db;height:20px;width:10px;padding:0 10px;float:left">
		            </a>
		            </li>
		            <li>
		            <a class="skin-changer" data-skin="theme-white" data-toggle="tooltip" title="绿色" style="background-color: #2ecc71;height:20px;width:10px;padding:0 10px;float:left">
		            </a>
		            </li>
		             <li>
		            <a class="skin-changer" data-skin="theme-amethyst" data-toggle="tooltip" title="紫色" style="background-color: #9b59b6;height:20px;width:10px;padding:0 10px;float:left">
		            </a>
		            </li>
		            <!--
		             <li>
		            <a class="skin-changer" data-skin="" data-toggle="tooltip" title="black" style="background-color: #34495e;height:20px;width:10px;padding:0 10px;float:left">
		            </a>
		            </li>
		            <li>
		            <a class="skin-changer blue-gradient" data-skin="theme-blue-gradient" data-toggle="tooltip" title="Gradient"  style="background:#3498db;height:20px;width:10px;padding:0 10px;float:left">
		            </a>
		            </li>
		            <li>
		            <a class="skin-changer" data-skin="theme-turquoise" data-toggle="tooltip" title="Green Sea" style="background-color: #1abc9c;height:20px;width:10px;padding:0 10px;float:left">
		            </a>
		            </li>
		           
		            <li>
		            <a class="skin-changer" data-skin="theme-blue" data-toggle="tooltip" title="Blue" style="background-color: #2980b9;height:20px;width:10px;padding:0 10px;float:left">
		            </a>
		            </li>
		            <li>
		            <a class="skin-changer" data-skin="theme-red" data-toggle="tooltip" title="Red" style="background-color: #e74c3c;height:20px;width:10px;padding:0 10px;float:left">
		            </a>
		            </li>
		            -->
                </ul>
      	 </li>
         <!--/颜色设置结束-->  
           <li class="dropdown hidden-xs"><a class="btn dropdown-toggle" data-toggle="dropdown"><i class="fa fa-info-circle"></i></a></li>
         <!--用户信息-->
            <li class="dropdown profile-dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                <img src="../img/robert-300.jpg" alt=""/>
                <span class="hidden-xs">熊熊熊二</span> <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="#"><i class="fa fa-user"></i>个人中心</a></li>
                    <li><a href="#"><i class="fa fa-key"></i>修改密码</a></li>
                    <li><a href="#"><i class="fa fa-cog"></i>系统设置</a></li>
                </ul>
            </li>
         <!--/用户信息结束-->
         <!--退出icon-->    
            <li class="hidden-xxs"><a class="btn"><i class="fa fa-power-off"></i></a></li>
         <!--/退出icon-->       
            </ul>
            </div>
            </div>
        </div>
    </header>
<!--头部-->
<div id="page-wrapper" class="container">
    <!---->
    <div class="row">
    <!--左侧菜单-->
    <div id="nav-col">
    <section id="col-left" class="col-left-nano">
        <div id="col-left-inner" class="col-left-nano-content">
            <div class="collapse navbar-collapse navbar-ex1-collapse" id="sidebar-nav">
                <ul class="nav nav-pills nav-stacked">
                	<li class="active"><a href="#"><i class="fa fa-home"></i><span>系统控制台</span><!--<span class="label label-info label-circle pull-right">28</span>--></a></li>
                    <li>
                        <a href="#" class="dropdown-toggle">
	                        <i class="fa fa-sitemap"></i><span>资质审核</span>
	                        <i class="fa fa-chevron-circle-right drop-icon"></i>
                        </a>
                       <!--二级菜单-->
                        <ul class="submenu">
                            <li>
                            	<a href="${_base}/demo/demopage" target="mainFrame">待审核</a>
                            </li>
                            <li><a href="#">已审核</a></li>
                        </ul>
                        <!--二级菜单结束-->
                    </li>
                     <li>
                        <a href="#" class="dropdown-toggle">
	                        <i class="fa fa-sitemap"></i><span>CRM管理</span>
	                        <i class="fa fa-chevron-circle-right drop-icon"></i>
                        </a>
                       <!--二级菜单-->
                        <ul class="submenu">
                            <li>
                            	<a href="${_base}/crm/supplierStatePager" target="mainFrame">供货商状态管理</a>
                            </li>
                            <li><a href="${_base}/crm/shopStatePager" target="mainFrame">入驻商户状态管理</a></li>
                            <li><a href="#">入驻商户评级规则设置</a></li>
                        </ul>
                        <!--二级菜单结束-->
                    </li>
                <li>
                    <a href="#" class="dropdown-toggle">
                    <i class="fa fa-inbox"></i>
                    <span>合同管理</span>
                    <i class="fa fa-chevron-circle-right drop-icon"></i>
                    </a>
                    <!--二级菜单-->
                    <ul class="submenu">
                        <li><a href="#">供货商合同</a></li>
                        <li><a href="#">入驻商户合同</a></li>
                    </ul>
                    <!--二级菜单结束-->
                </li>
                <li>
                    <a href="#" class="dropdown-toggle">
                    <i class="fa fa-usd"></i>
                    <span>结算管理</span><i class="fa fa-chevron-circle-right drop-icon"></i>
                    </a>
                <!--二级菜单-->    
                    <ul class="submenu">
                        <li><a href="${_base}/billing/billingPager">保证金/服务费设置</a></li>
                        <li><a href="#">计算周期设置</a></li>
                        <li><a href="#">违约管理</a></li>
                    </ul>
                <!--二级菜单结束-->
                </li>
                
                </ul>
            </div>
        </div>
    </section>
    </div>
    <!--/左侧菜单结束-->
    
    <div id="content-wrapper"><!--右侧灰色背景-->
    <!--右侧-->
     <div class="row"><!--外围框架-->
     	<div class="col-lg-12"><!--删格化-->
             <div class="row"><!--内侧框架-->
	                 <div class="col-lg-12"><!--删格化-->
	                    <div class="main-box clearfix"><!--白色背景-->
	                    		<div class="notice col-lg-12">
	                    			<p class="gongg"><A href="#">［公告］:</A></p>
           						 <div  id="elem">
						            <ul id="elem1">
						                <li><A href="#">公告位置！比如说系统维护，哪些功能在什么时间段可能不可用之类的，针对后台</A></li>
						                <li><A href="#">公告位置！比如说系统维护，哪些功能在什么时间段可能不可用之类的，针对后台</A></li>
						                <li><A href="#">公告位置！比如说系统维护，哪些功能在什么时间段可能不可用之类的，针对后台</A></li>
						                <li><A href="#">公告位置！比如说系统维护，哪些功能在什么时间段可能不可用之类的，针对后台</A></li>
						            </ul>
						            <ul id="elem2">
						            </ul>
						            </div>
            						 <p class="dclose"><A href="#"><i class="icon-remove"></i></A></p>
	                    		</div>
	         			</div>
	                	</div>
              </div>
         </div>
     </div>	
			
  		  <div class="row"><!--外围框架-->
            <div class="col-lg-12"><!--删格化-->
                <div class="row"><!--内侧框架-->
                    <div class="col-lg-12"><!--删格化-->
                         <div class="main-box clearfix"><!--白色背景-->   
                         	<!--查询条件-->
	                    		 <div class="form-label">
						           	<ul>
						                <li class="col-md-6" style="width: 40%">
						                    <p class="word" style="font-size: 20px;margin-left: 30px">用户名:</p>
						                    <p>aaa</p>
						                </li>
						                <li  class="col-md-6">
						                    <p class="word" style="font-size: 20px">企业名称:</p>
						                    <p>bbb</p>
						                </li>  
						            </ul>
						            <ul>
						                <li  class="col-md-6" style="border-bottom:1px  solid #e7e7e7;padding-bottom: 10px;width: 98%;margin-left: 30px">
						                    <p class="word" style="font-size: 20px">合同管理</p>
						                </li>  
						            </ul>
					         </div>
					   	<!--查询结束-->   
					 	<!--table表格-->
					 	 	<div class="form-label pl-40">
						    	<ul>
									<li>
									  <p class="word" style="font-style: "><b class="red">*</b>合同编号:</p>
									</li>
									<li>aaaa</li>
								</ul>
								
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>合同名称:</p>
									</li>
									<li>bbb</li>
								</ul>

								<ul>
									<li>
										<p class="word"><b class="red">*</b>开始时间:</p>
										<p>2016-8-12 16:33</p>
									</li>
									<li>
									  <p style="font-style:oblique; ">——</p>
									</li>
									<li>
										<p class=""><b class="red">*</b>结束时间:</p>
										<p>2016-8-14 16:33</p>
									</li>
								</ul>
								<ul>
									<li>
										<p class="word"><b class="red">*</b>扫描版合同:</p>
										<p>扫描合同.pdf</p>
										<a class="biu-btn btn-auto btn-green"> <i class="icon-download-alt"> </i>下载 </a>
									</li>
								</ul>
								<ul>
									<li>
										<p class="word"><b class="red">*</b>电子版合同:</p>
										<p>电子合同.pdf</p>
										<a class="biu-btn btn-auto btn-green"> <i class="icon-download-alt"> </i>下载 </a>
									</li>
								</ul>
																							
													 	 	
                             </div>
                        </div>
                    </div>
                </div>
            
            </div>
    </div>
    
    <!--底部-->
    <footer id="footer-bar" class="row">
   		 <p id="footer-copyright" class="col-xs-12">亚信</p>
    </footer>
   <!--/底部结束-->
    </div>
    </div>
</div>
</div>
</body>
</html>
