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
                         	  <div class="form-label pl-40">
							<div class="form-label  bd-bottom">
								<ul>
									<li class="col-md-6">
										<p class="word">用户名:</p>
										<p>${userName }</p>
										<input type="hidden" value="${userName}" id="userName"/>
									</li>
									<li class="col-md-6">
										<p class="word">企业名称:</p>
										<p>${custName}</p>
										<input type="hidden" value="${custName}" id="custName"/>
									</li>
								</ul>
                          		</div>
                          		</div>
                          		<div class="form-label">
							<ul>
								<li class="col-md-6">
								<p class="word">合同管理</p>
								</li>
							</ul>
						</div>
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
									  <p class="word" style="font-style: ">合同金额:</p>
									</li>
									<li>${contactInfo.contractAmount}元</li>
								</ul>
								
						    	<ul>
									<li>
									  <p class="word">合同备注:</p>
									</li>
									<li>${contactInfo.remark }</li>
								</ul>
								
								<ul>
									<li>
										<p class="word"><b class="red">*</b>扫描版合同:</p>
										<p>${scanContractInfoName }</p>
										<c:if test="${scanContractInfoName!=''&&scanContractInfoName!=null}">
											<a class="biu-btn btn-auto btn-green" href="${_base}/contract/download?fileName=${scanDownLoadName}&attrValue=${scanContractAttrValue}"> <i class="icon-download-alt"> </i>下载 </a>
										</c:if>
									</li>
								</ul>
								<ul>
									<li>
										<p class="word">电子版合同:</p>
										<p>${electronicContractInfoName}</p>
										<c:if test="${electronicContractInfoName!=''&&electronicContractInfoName!=null}">
											<a class="biu-btn btn-auto btn-green" href="${_base}/contract/download?fileName=${electronicDownLoadName}&attrValue=${electronicContractAttrValue}"> <i class="icon-download-alt"> </i>下载 </a>
										</c:if>
									</li>
								</ul>
																							
													 	 	
                             </div>
                             <div>
                             	<ul>
                             		<li>
                             			<input type="button" style="margin-left: 20%" class="biu-btn  btn-primary btn-blue btn-medium ml-5"
										onclick="history.go(-1)"	value="返回">
                             		</li>
                             	</ul>
                             </div>
                        </div>
                    </div>
                </div>
            
            </div>
    </div>
    
</body>
</html>
