<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="uedroot" value="${pageContext.request.contextPath}/template/default"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>结算周期详情</title>
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
						                <li  class="col-md-6" style="font-weight:bold;border-bottom:1px  solid #e7e7e7;padding-bottom: 10px;width: 98%;">
						                    <p class="word" style="font-size: 16px">查看结算周期</p>
						                </li>  
						            </ul>
					           	<ul>
					                <li class="col-md-6" style="width: 40%">
					                    <p class="word" style="font-size: 16px;margin-left: 30px">用户名:</p>
					                    <p>${userName}</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word" style="font-size: 16px">企业名称:</p>
					                    <p>${custName}</p>
					                </li>  
					            </ul>
					            
					            <ul>
					                <li class="col-md-6" style="width: 40%">
					                    <p class="word" style="font-size: 18px;margin-left: 30px">当前结算周期:</p>
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
				         	</div>
                        </div>
                    </div>
                </div>
            
            </div>
    </div>
</body>
</html>
