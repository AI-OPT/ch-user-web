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
									<li>${contactInfo.contractCode}</li>
								</ul>
								
						    	<ul>
									<li>
									  <p class="word"><b class="red">*</b>合同名称:</p>
									</li>
									<li>${contactInfo.contractName }</li>
								</ul>

								<ul>
									<li>
										<p class="word"><b class="red">*</b>开始时间:</p>
										<p>${startTime }</p>
									</li>
									<li>
									  <p style="font-style:oblique; ">——</p>
									</li>
									<li>
										<p class=""><b class="red">*</b>结束时间:</p>
										<p>${endTime}</p>
									</li>
								</ul>
								<ul>
									<li>
										<p class="word"><b class="red">*</b>扫描版合同:</p>
										<p>${scanContractInfoName }</p>
										<a class="biu-btn btn-auto btn-green" href="${_base}/contract/download/${scanContractInfoName}?fileName=${scanContractInfoName}&attrValue=${scanContractAttrValue}"> <i class="icon-download-alt"> </i>下载 </a>
									</li>
								</ul>
								<ul>
									<li>
										<p class="word">电子版合同:</p>
										<p>${electronicContractInfoName}</p>
										<a class="biu-btn btn-auto btn-green" href="${_base}/contract/download/${electronicContractInfoName}?fileName=${electronicContractInfoName}&attrValue=${electronicContractAttrValue}"> <i class="icon-download-alt"> </i>下载 </a>
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
</body>
</html>
