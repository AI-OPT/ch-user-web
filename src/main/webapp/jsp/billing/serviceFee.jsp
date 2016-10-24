<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="uedroot"
	value="${pageContext.request.contextPath}/template/default" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>保证金设置</title>
<%@include file="/inc/inc.jsp"%>
</head>
<body>
	<div class="content-wrapper-iframe">
		<!--右侧灰色背景-->
		<!--框架标签结束-->
			<div class="row"><!--外围框架-->
            <div class="col-lg-12"><!--删格化-->
                <div class="row"><!--内侧框架-->
                    <div class="col-lg-12"><!--删格化-->
                        <div class="main-box clearfix"><!--白色背景-->
                        	<div class="main-box-body clearfix">	<!--padding20-->
				       	<header class="main-box-header clearfix">
                            <h4 class="pull-left">查看详情</h5>
                        </header>
       				   	<div class="form-label">
				           	<ul>
					             <li class="col-md-6">
					                <p class="word">用户名：</p>
					                <p>${userName }</p> 
					              </li>
					              <li class="col-md-6">
					                <p class="word">企业名称：</p>
					                <p>${shopName }</p> 
					              </li> 
				            </ul>  
       				   	</div>
       				   	<!--标题带下划线-->
						<div class="nav-tplist-title bd-bottom pb-10">
				                  <ul>
				                    <li>当前结算设置:</li>
				                  </ul>
				       </div>
				       <!--标题带下划线结束-->
                      	<div class="form-label">
				           	<ul>
					             <li class="col-md-12">
					                <p class="word">保证金：</p>
					                <p>${deposit }</p> 
					              </li>
					              <li class="col-md-12">
					                <p class="word">固定金额服务费：</p>
					                <p>${rentFeeStr }</p> 
					              </li>
					               <li class="col-md-12">
					                <p class="word">实时划扣服务费：</p>
					                <p>${ratioStr }</p> 
					              </li>
					            </ul>       
       				   	</div>
						</div>	
                        </div>
                </div>
              </div> 
            </div>
    </div>
    <!--底部-->
	<script type="text/javascript">
	function backup(){
		window.location.href=_base+"/billing/billingpager";
	}
	</script>
</body>
</html>