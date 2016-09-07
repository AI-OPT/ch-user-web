<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
                    		 <div class="form-label">
					           	<ul>
					                <li class="col-md-6" style="width: 40%">
					                    <p class="word" style="font-size: 20px;margin-left: 30px">用户名:</p>
					                    <p>${userName}</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word" style="font-size: 20px">企业名称:</p>
					                    <p>${custName}</p>
					                </li>  
					            </ul>
					            <ul>
					                <li  class="col-md-6" style="border-bottom:1px  solid #e7e7e7;padding-bottom: 10px;width: 98%;margin-left: 30px">
					                    <p class="word" style="font-size: 20px">合同管理</p>
					                </li>  
					            </ul>
				         	</div>
					 	<!--form-->
					 	<form:form id="contractInfo" method="post">
					 	 	<div class="form-label pl-40">
						    	<ul>
									<li>
									  <p class="word" style="font-style: "><b class="red">*</b>合同编号:</p>
									</li>
									<li>
									   <input type="text" class="int-text int-medium" placeholder="请输入账号" id="contractCode" name="contractCode" value="${contactInfo.contractCode }"/>
									   <input type="hidden" name="userId" value="${userId }"/>
									 </li>
									<li><label id="contractCodeErrMsg" style="display: none;"><span class="ash" id="contractCodeText">1-12位字符，可用数字及"."</span></label></li>
								</ul>
								
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>合同名称:</p>
									</li>
									<li><input type="text" class="int-text int-medium" placeholder="请输入名称" id="contractName" name="contractName" value="${contactInfo.contractName }"/></li>
									<li><label id="contractNameErrMsg" style="display: none;"><span class="ash" id="contractNameText">1-256个字符</span></label></li>
								</ul>

								<ul>
									<li>
										<p class="word"><b class="red">*</b>开始时间:</p>
										<p><input type="text" class="int-text int-medium" value="${startTime}" name="startTime" id="startTime">
											<span class="time"> <i class="fa  fa-calendar" ></i></span></p>
									</li>
									<li><label id="startTimeErrMsg" style="display: none;"><span class="ash" id="startTimeText">请输入时间</span></label></li>
									<li>
									  <p style="font-style:oblique; ">——</p>
									</li>
									<li>
										<p class=""><b class="red">*</b>结束时间:</p>
										<p><input type="text" class="int-text int-medium" value="${endTime}" name="endTime" id="endTime">
											<span class="time"> <i class="fa  fa-calendar" ></i></span></p>
									</li>
									<li><label id="endTimeTextErrMsg" style="display: none;"><span class="ash" id="endTimeTextShow">请输入时间</span></label></li>
								</ul>
								<ul>
									<li>
										<p class="word"><b class="red">*</b>扫描版合同:</p>
										<span class="btn-upload">
										   <input type="hidden" id="scanFileName" value="" name="list[0].infoName">
							               <input type="hidden" value="30" name="list[0].infoType">
							               <input type="hidden" value="30001" name="list[0].infoItem">
							               <input type="hidden" value="${userId}" name="list[0].userId"/>
							               <input type="hidden" value="changhong" name="list[0].tenantId"/>
							               
										   <input class="int-text int-large" value="${scanContractInfoName}" id="scanFileText" type="text">
										   <input type="button" id="scanContract" class="btn-default btn-medium" value="浏览文件"/>
										   <input type="file" name="scanFile" id="scanFile" onchange="uploadFile('scanFile','scanFileText','scanContractErrMsg','scanContractText','scanVersionContractFlag','ddsId1')"  class="int-file"/>
										</span>
									</li>
									<li><label id="scanContractErrMsg" style="display: none;"><span class="ash" id="scanContractText">扫描件不能为空</span></label></li>
								</ul>
								<ul>
									<li>
										<p class="word">电子版合同:</p>
										
										 <span class="btn-upload">
											 <input type="hidden" id="electronicFileName" value="" name="list[1].infoName">
								             <input type="hidden" id="electronicFileInfoType" value="30" name="list[1].infoType">
								             <input type="hidden" id="electronicFileInfoItem" value="30002" name="list[1].infoItem">
								             <input type="hidden" id="electronicFileUserId" value="${userId}" name="list[1].userId"/>
								             <input type="hidden" id="electronicFileTennatId" value="changhong" name="list[1].tenantId"/>
											 <input class="int-text int-large" id="electronicContractText" value="${electronicContractInfoName }" type="text">
											 <input type="button" id="electronicContract" class="btn-default btn-medium" value="浏览文件"/>
											 <input type="file" name="electronicFile" id="electronicFile" onchange="uploadFile('electronicFile','electronicContractText','electronicContractErrMsg','electronicContractText','electronicContractFlag','ddsId2')" class="int-file"/>
										</span>
									</li>
									<li><label id="electronicContractErrMsg" style="display: none;"><span class="ash" id="electronicContractText">扫描件不能为空</span></label></li>
								</ul>
								<ul>
									<li>
										<p style="margin-left: 25%">支持上传pdf、PNG、JPG及word格式的文件，大小20M以下</p>
										
									</li>
								</ul>
								<ul>
									<li class="form-btn" >
										<input type="button" class="biu-btn border-green btn-xlarge  radius" id="supplierSave"  style="margin-left: 60%"  value="保存">	
										<input type="hidden" id="contractCodeFlag" value="0"/>
										<input type="hidden" id="contractNameFlag" value="0"/>
										<input type="hidden" id="startTimeFlag" value="0"/>
										<input type="hidden" id="endTimeTextFlag" value="0"/>
										<input type="hidden" id="scanVersionContractFlag" value="0"/>
										<input type="hidden" id="electronicContractFlag" value="0"/>
										 <input type="hidden" id="ddsId1" name="list[0].attrValue">
										 <input type="hidden" id="ddsId2" name="list[1].attrValue">
										 
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
</script>

</body>
</html>
