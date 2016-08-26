<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="uedroot" value="${pageContext.request.contextPath}/template/default"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>保证金</title>
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
							<div class="form-label" style="margin-bottom: 20px ">
					           	<ul>
					                <li class="col-md-6" style="width: 40%">
					                    <p class="word" style="font-size: 20px;margin-left: 30px">用户名:</p>
					                    <p>${userName }</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word" style="font-size: 20px">企业名称:</p>
					                    <p>${custName}</p>
					                </li>  
					            </ul>
					            
					            <ul>
					                <li class="col-md-6" style="width: 40%">
					                    <p class="word" style="font-size: 20px;margin-left: 30px">保证金余额:</p>
					                    <p>2000.00元</p>
					                </li>
					            </ul>
				         	</div>
				         	<div class="form-label">
				         		 <ul>
					                <li  class="col-md-6" style="border-bottom:1px  solid #e7e7e7;padding-bottom: 10px;width: 98%;margin-left: 30px">
					                    <p class="word" style="font-size: 20px">新增违约记录</p>
					                </li>  
					            </ul>
				         	</div>
					   	<!--查询结束-->   
					   	<form id="defaultManagerForm" method="post">
					 	 <div>	
					 	 	<div class="form-label pl-40" >
					 	 	   <input type="hidden" id="userId" name="userId" value="${userId}"/>
						    	<ul style="margin-bottom: 20px">
									<li>
									  <p class="word" style="font-style: "><b class="red">*</b>违约原因:</p>
									</li>
									<li ><textarea class="int-text textarea-large" id="defaultReason" name="defaultReason" cols="50" rows="4"></textarea></li>
									<li><label id="defaultReasonErrMsg" style="display: none;"><span class="ash" id="defaultReasonText">1-12位字符，可用数字及"."</span></label></li>
								</ul>
								<ul style="margin-bottom: 20px">
									<li>
									  <p class="word" style="font-style: "><b class="red">*</b>扣款金额:</p>
									</li>
									<li><input type="text" class="int-text int-medium" id="amount" name="deductBalance" placeholder="请输入金额" />元</li>
									<li><label id="amountErrMsg" style="display: none;"><span class="ash" id="amountText">1-12位字符，可用数字及"."</span></label></li>
								</ul>
							    <ul>
									<li class="form-btn" >
										<input type="button" class="biu-btn border-green btn-xlarge  radius" style="margin-left: 55%;" id="saveDefaultManager" value="保存">	
										<input type="hidden" id="defaultReasonFlag"/>
										<input type="hidden" id="amountFlag"/>
									</li>
								</ul>
							</div>
					   	 </div>
					   	 </form>
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
   var defaultPagerManager;
   seajs.use(['app/jsp/billing/defaultManager'], function(DefaultPagerManager) {
	    defaultPagerManager = new DefaultPagerManager({
		element : document.body
	});
	    defaultPagerManager.render();
});
   </script>
</body>
</html>
