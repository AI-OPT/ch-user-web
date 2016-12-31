<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="uedroot" value="${pageContext.request.contextPath}/template/default"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
					 	<!--form-->
					 	<form:form id="contractInfo" method="post" enctype="multipart/form-data" action="${_base}/contract/addSupplierContractInfo">
					 	 	<div class="form-label pl-40">
						    	<ul>
									<li>
									  <p class="word" style="font-style: "><b class="red">*</b>合同编号</p>
									</li>
									<li>
									   <input type="text" class="int-text int-medium" placeholder="请输入合同编号" id="contractCode" name="contractCode" value="${contactInfo.contractCode }"/>
									   <input type="hidden" name="userId" value="${userId }"/>
									 </li>
									<li><label id="contractCodeErrMsg" style="display: none;"><span class="ash" id="contractCodeText">1-12位字符，可用数字及"."</span></label></li>
								</ul>
								
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>合同名称</p>
									</li>
									<li><input type="text" class="int-text int-medium" placeholder="请输入合同名称" id="contractName" name="contractName" value="${contactInfo.contractName }"/></li>
									<li><label id="contractNameErrMsg" style="display: none;"><span class="ash" id="contractNameText">1-256个字符</span></label></li>
								</ul>

								<ul>
									<li>
										<p class="word"><b class="red">*</b>开始时间</p>
										<p><input type="text" class="int-text int-medium" readonly="readonly" value="${startTime}" name="startTime" id="startTime">
											<span class="time"> <i class="fa  fa-calendar" ></i></span></p>
									</li>
									<li><label id="startTimeErrMsg" style="display: none;"><span class="ash" id="startTimeText">请输入时间</span></label></li>
									<li>
									  <p style="font-style:oblique; ">——</p>
									</li>
									<li>
										<p class=""><b class="red">*</b>结束时间</p>
										<p><input type="text" class="int-text int-medium" readonly="readonly" value="${endTime}" name="endTime" id="endTime">
											<span class="time"> <i class="fa  fa-calendar" ></i></span></p>
									</li>
									<li><label id="endTimeTextErrMsg" style="display: none;"><span class="ash" id="endTimeTextShow">请输入时间</span></label></li>
								</ul>
								 <ul>
									<li>
										<p class="word">合同金额</p>
										<p><input type="text" class="int-text int-medium" placeholder="请输入合同金额" id="contractAmount" name="contractAmount" value="${contactInfo.contractAmount}"/>元</p>
									</li>
									<li><label id="contractAmountErrMsg" style="display: none;"><span class="ash" id="contractAmountText"></span></label></li>
								</ul>
								<ul>
									<li>
										<p class="word">合同备注</p>
										<p></p>
									</li>
									<li><textarea class="int-text textarea-large"
												id="contractRemark" name="remark" cols="50" rows="3" >${contactInfo.remark}</textarea></li>
									<li><label id="remarkErrMsg" style="display: none;"><span class="ash" id="remarkText"></span></label></li>
								</ul>
								<ul>
									<li>
										<p class="word"><b class="red">*</b>扫描版合同</p>
										<span class="btn-upload">
										   <input type="hidden" id="scanFileName" value="" name="list[0].infoName">
							               <input type="hidden" value="30" name="list[0].infoType">
							               <input type="hidden" value="30001" name="list[0].infoItem">
							               <input type="hidden" value="${userId}" name="list[0].userId"/>
							               <input type="hidden" value="changhong" name="list[0].tenantId"/>
							               
										   <input class="int-text int-large" value="${scanContractInfoName}" id="scanFileText" type="text">
										   <input type="button" id="scanContract" class="btn-primary btn-medium" value="浏览文件"/>
										   <input type="file" name="scanFile" id="scanFile" onchange="uploadScanFile('scanFile','scanFileText','scanContractErrMsg','scanContractText','scanVersionContractFlag','ddsId1')"  class="int-file"/>
										   <input type="button"
											class="biu-btn  btn-primary btn-blue btn-medium ml-10"
											value="删除" id="scanFileButtonId" style="cursor: default;"/>
										</span>
									</li>
									<li><label id="scanContractErrMsg" style="display: none;"><span class="ash" id="scanContractText"></span></label></li>
								</ul>
								<ul>
									<li>
										<p class="word">电子版合同</p>
										
										 <span class="btn-upload">
											 <input type="hidden" id="electronicFileName" value="" name="list[1].infoName">
								             <input type="hidden" id="electronicFileInfoType" value="30" name="list[1].infoType">
								             <input type="hidden" id="electronicFileInfoItem" value="30002" name="list[1].infoItem">
								             <input type="hidden" id="electronicFileUserId" value="${userId}" name="list[1].userId"/>
								             <input type="hidden" id="electronicFileTennatId" value="changhong" name="list[1].tenantId"/>
											 <input class="int-text int-large" id="electronicContractText" value="${electronicContractInfoName }" type="text">
											 <input type="button" id="electronicContract" class="btn-primary btn-medium" value="浏览文件"/>
											 <input type="file" name="electronicFile" id="electronicFile" onchange="uploadEleFile('electronicFile','electronicContractText','electronicContractErrMsg','electronicContractFileText','electronicContractFlag','ddsId2')" class="int-file"/>
											 <input type="button"
												class="biu-btn  btn-primary btn-blue btn-medium ml-10"
												value="删除" id="electronicFileButtonId" style="cursor: default;"/>
										</span>
									</li>
									<li><label id="electronicContractErrMsg" style="display: none;"><span class="ash" id="electronicContractFileText"></span></label></li>
								</ul>
								<ul>
									<li>
										<p style="margin-left: 25%">支持上传pdf、PNG、JPG及word格式的文件</p>
										
									</li>
								</ul>
								<ul>
									<li>
									<p class="word">&nbsp;</p>
									<p>
										<input type="button" class="biu-btn btn-primary btn-blue btn-medium ml-10" id="supplierSave"    value="保存">	
										</p><p>
										<input type="button"  class="biu-btn  btn-primary btn-blue btn-medium ml-5"
										onclick="backup();"	value="返回">
										</p>
										<input type="hidden" id="contractCodeFlag" value="0"/>
										<input type="hidden" id="contractNameFlag" value="0"/>
										<input type="hidden" id="startTimeFlag" value="0"/>
										<input type="hidden" id="endTimeTextFlag" value="0"/>
										<input type="hidden" id="scanVersionContractFlag" value="0"/>
										<input type="hidden" id="electronicContractFlag" value="0"/>
										<input type="hidden" id="contractType" value="1">
										<input type="hidden" id="contractAmountFlag">
										<input type="hidden" id="contractRemarkFlag">
									</li>
								</ul>
                             </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            
            </div>
    </div>
    

<script type="text/javascript">
	var contractPager;
	var userId = "${contactInfo.userId}";
	
	var scanContractInfoExtId = "${scanContractInfoExtId}";
	var scanAttrValue = "${scanContractAttrValue }";
	
	var electronicInfoExtId = "${electronicInfoExtId}";
	var electronicScanAttrValue = "${electronicContractAttrValue }";
	
	(function() { 
		<%-- 展示日历 --%>
		$('#contractInfo').delegate('.fa-calendar','click',function(){
			var calInput = $(this).parent().prev();
			var timeId = calInput.attr('id');
			console.log("click calendar "+timeId);
			WdatePicker({el:timeId,readOnly:true});
		});
		seajs.use(['app/jsp/contract/contract'], function(ContractPager) {
			    contractPager = new ContractPager({
				element : document.body
			});
			contractPager.render();
		});
	})();  
	function backup(){
		window.location.href=_base+"/contract/contractSupplierPager";
	}
</script>
</body>
</html>
