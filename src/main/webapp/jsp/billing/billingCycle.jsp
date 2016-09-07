<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="uedroot" value="${pageContext.request.contextPath}/template/default"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>结算周期设置</title>
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
							<div class="form-label" style="margin-bottom: 20px; ">
					           	<ul style="background: #e7e7e7;margin-top: 10px;width: 1060px">
					                <li class="col-md-6" style="width: 40%">
					                    <p class="word" style="font-size: 16px;margin-left: 30px">用户名:</p>
					                    <p>${userName }</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word" style="font-size: 16px">企业名称:</p>
					                    <p>${custName}</p>
					                </li>  
					                 <li class="col-md-6" style="width: 40%">
					                    <p class="word" style="font-size: 16px;margin-left: 30px">当前结算周期:</p>
					                    <p>
					                     <c:if test="${shopInfo.periodType=='M' }">
					                     	月（自然月）
					                     </c:if>
					                     <c:if test="${shopInfo.periodType=='Q'}">
					                     	季度（自然季度）
					                     </c:if><c:if test="${shopInfo.periodType=='D'}">
					                     	T+1（日结）
					                     </c:if><c:if test="${shopInfo.periodType=='R'}">
					                     	实时
					                     </c:if>
					                     </p>
					                </li>
					            </ul>
					            
					            <ul style="background: #e7e7e7">
					               
					            </ul>
				         	</div>
				         	<div class="form-label">
				         		 <ul>
					                <li  class="col-md-6" style="border-bottom:1px  solid #e7e7e7;padding-bottom: 10px;width: 98%;margin-left: 30px">
					                    <p class="word" style="font-size: 16px">结算周期设置</p>
					                </li>  
					            </ul>
				         	</div>
					   	<!--查询结束-->   
					 	 <div>	
					 	 	<div class="form-label pl-40" >
						    	<ul style="margin-bottom: 20px">
									<li>
									 <input type="hidden" id="userId" value="${userId }"/>
									 <input type="hidden" id="userName" value="${userName }"/>
									 <input type="hidden" id="custName" value="${custName }"/>
									  <p class="word" style="font-style: "><b class="red">*</b>结算周期:</p>
									  <input type="radio" class="radio-1" id="billingCycle" value="M" name="periodType" checked="checked">
									  <label for="radio-1">月（自然月）</label>
									  <input type="radio" class="radio-1" id="billingCycle" value="Q" name="periodType">
									  <label for="radio-1">季度（自然季度）</label>	
									  <input type="radio" class="radio-1" value="D" id="billingCycle" name="periodType">
									  <label for="radio-1">T+1（日结）</label>
									  <input type="radio" class="radio-1" value="R" id="billingCycle" name="periodType">
									  <label for="radio-1">实时</label>	
									</li>
									
								</ul>
								
							    <ul>
									<li class="form-btn" >
										<input type="button" class="biu-btn border-green btn-xlarge  radius" style="margin-left: 55%;" id="saveShopInfo" onclick="saveShopInfo()" value="保存">	
									</li>
								</ul>
							</div>
					   	 </div>
                        </div>
                    </div>
                </div>
            
            </div>
    </div>
</body>
<script type="text/javascript">
	function saveShopInfo(){
		var userId = $("#userId").val();
		var userName = $("#userName").val();
		var custName = $("#custName").val();
		var billingCycle = $('input:radio:checked').val();
		window.location.href=_base+"/billing/saveCycleSetting?userId="+userId+"&periodType="+billingCycle+"&userName="+userName+"&custName="+custName;
		
	}
</script>
</html>
