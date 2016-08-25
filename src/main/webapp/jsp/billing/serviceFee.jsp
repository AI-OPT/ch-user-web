<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="uedroot" value="${pageContext.request.contextPath}/template/default"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>保证金设置</title>
<%@include file="/inc/inc.jsp" %>
</head>
<body>
   <div class="content-wrapper-iframe" ><!--右侧灰色背景-->
     <!--框架标签结束-->
      <div class="row"><!--外围框架-->
     	<div class="col-lg-12"><!--删格化-->
             <div class="row"><!--内侧框架-->
	                 <div class="col-lg-12"><!--删格化-->
	                    <div class="main-box clearfix"><!--白色背景-->
					   	<!--查询结束-->      
	         			</div>
	                	</div>
              </div>
         </div>
     </div>	
     <!--框架标签结束-->
  		  <div class="row"><!--外围框架-->
            <div class="col-lg-12"><!--删格化-->
                <div class="row"><!--内侧框架-->
                    <div class="col-lg-12"><!--删格化-->
                        <div class="main-box clearfix"><!--白色背景-->
                        <!--标题-->
                            <header class="main-box-header clearfix">
                            <h2 class="pull-left">保证金设置</h2>
                            </header>
                        <!--标题结束-->   
                            <div class="main-box-body clearfix">
                            	<!--table表格-->
                                <div class="table-responsive clearfix">
								
								 <div class="form-label pl-40">
								 	<ul>
								 		<li>
								 			<p class="word">用户名:</p>
								 			<p>${userName }</p>
								 		</li>
								 		<li>
								 			<p class="word">企业名称:</p>
								 			<p>${shopName }</p>
								 		</li>
								 	</ul>
								 	<ul>
								 		<li><p class="word">当前结算设置:</p>
								 	</ul>
								 	<ul>
								 	<li><p class="word">保证金: ${deposit }元</p></li>
								 	</ul>
								 	<ul>
								 	<li><p class="word">固定金额服务费: ${deposit }</p>
								 	<p class="word">元/</p>
								 	<p>${depositCycle }</p></li>
								 	</ul>
								 	<ul>
								 	<li><p class="word">实时划扣服务费: ${ratio }%</p></li>
								 	</ul>
								 </div>
							</div>
                        </div>
                    </div>
                </div>
            </div>
            </div>
    </div>
  </div>   
</body>
</html>