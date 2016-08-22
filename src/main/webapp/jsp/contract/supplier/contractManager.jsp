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
					 	<!--form-->
					 	<form:form id="contractInfo" method="post">
					 	 	<div class="form-label pl-40">
						    	<ul>
									<li>
									  <p class="word" style="font-style: "><b class="red">*</b>合同编号:</p>
									</li>
									<li>
									   <input type="text" class="int-text int-medium" placeholder="请输入账号" id="contractCode" name="contractCode" value="${contactInfo.contractCode }"/>
									   <input type="hidden" name="userId" value="${contactInfo.userId }"/>
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
										<p><input type="text" class="int-text int-medium" name="startTime" id="startTime">
											<span class="time"> <i class="fa  fa-calendar" ></i></span></p>
									</li>
									<li><label id="startTimeErrMsg" style="display: none;"><span class="ash" id="startTimeText">请输入时间</span></label></li>
									<li>
									  <p style="font-style:oblique; ">——</p>
									</li>
									<li>
										<p class=""><b class="red">*</b>结束时间:</p>
										<p><input type="text" class="int-text int-medium" name="endTime" id="endTime">
											<span class="time"> <i class="fa  fa-calendar" ></i></span></p>
									</li>
									<li><label id="endTimeErrMsg" style="display: none;"><span class="ash" id="endTimeText">请输入时间</span></label></li>
								</ul>
								<ul>
									<li>
										<p class="word"><b class="red">*</b>扫描版合同:</p>
										<span class="btn-upload">
										 <input class="int-text int-large" type="text">
										 <input type="button" class="btn-default btn-medium" value="浏览文件"/>
										 <input type="file" class="int-file"/>
										</span>

									</li>
								</ul>
								<ul>
									<li>
										<p class="word"><b class="red">*</b>电子版合同:</p>
										<span class="btn-upload">
										 <input class="int-text int-large" type="text">
										 <input type="button" class="btn-default btn-medium" value="浏览文件"/>
										 <input type="file" class="int-file"/>
										</span>
									</li>
								</ul>
								<ul>
									<li>
										<p style="margin-left: 25%">支持上传pdf、PNG、JPG及word格式的文件</p>
										
									</li>
								</ul>
								<ul>
									<li class="form-btn" >
										<input type="button" class="biu-btn border-green btn-xlarge  radius" id="supplierSave"  style="margin-left: 60%"  value="保存">	
										<input type="hidden" id="contractCodeFlag" value="0"/>
										<input type="hidden" id="contractNameFlag" value="0"/>
										<input type="hidden" id="startTimeFlag" value="0"/>
										<input type="hidden" id="endTimeFlag" value="0"/>
										<input type="hidden" id="scanVersionContractFlag" value="0"/>
									</li>
								</ul>
                             </div>
                            </form:form>
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
