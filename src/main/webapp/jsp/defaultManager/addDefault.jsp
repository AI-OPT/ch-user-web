<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="uedroot" value="${pageContext.request.contextPath}/template/default" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>保证金</title>
<%@include file="/inc/inc.jsp"%>
<script src="${uedroot}/scripts/modular/frame.js"></script>
<link rel="stylesheet" type="text/css"
	href="${uedroot}/css/modular/modular.css" />
</head>
<body>
	<div class="row">
		<!--外围框架-->
		<div class="col-lg-12">
			<!--删格化-->
			<div class="row">
				<!--内侧框架-->
				<div class="col-lg-12">
					<!--删格化-->
					<div class="main-box clearfix">
						<!--白色背景-->
						<div class="form-label pl-40">
								<div class="form-label  bd-bottom">
							           	<ul>
							                <li  class="col-md-6">
							                    <p class="word">用户名:</p>
							                    <p>${userName }</p>
							                </li>
							                <li  class="col-md-6">
							                    <p class="word">企业名称:</p>
							                    <p>${custName}</p>
							                </li>  
							            </ul>  
							            <ul>
							                <li  class="col-md-6">
							                    <p class="word">保证金余额:</p>
							                    <p>${balance}</p>元
							                </li>
							            </ul>
							  	</div>
							  	</div>
							<div class="form-label">
							<ul>
								<li>
									<p class="word">新增违约记录</p>
								</li>
							</ul>
						</div>
						<!--查询结束-->
						<form:form id="defaultManagerForm" method="post">
							<div>
								<div class="form-label pl-40">
									<input type="hidden" id="userId" name="userId"
										value="${userId}" />
									<ul style="margin-bottom: 20px">
										<li>
											<p class="word" style="font-style:">
												<b class="red">*</b>违约原因:
											</p>
										</li>
										<li><textarea class="int-text textarea-large"
												id="defaultReason" name="defaultReason" cols="50" rows="4"></textarea></li>
										<li><label id="defaultReasonErrMsg"
											style="display: none;"><span class="ash"
												id="defaultReasonText">1-12位字符，可用数字及"."</span></label></li>
									</ul>
									<ul style="margin-bottom: 20px">
										<li>
											<p class="word" style="font-style:">
												<b class="red">*</b>扣款金额:
											</p>
										</li>
										<li><input type="text" class="int-text int-medium"
											id="amount" name="deductBalance" placeholder="请输入金额" />元</li>
										<li><label id="amountErrMsg" style="display: none;"><span
												class="ash" id="amountText">1-12位字符，可用数字及"."</span></label></li>
									</ul>
									<ul>
										<li class="form-btn"><input type="button"
											class="biu-btn  btn-primary btn-blue btn-medium ml-10"
											 id="saveDefaultManager" value="保存">
											<input type="button"  class="biu-btn  btn-primary btn-blue btn-medium ml-5"
											onclick="history.go(-1)"	value="返回">
											<input type="hidden" id="defaultReasonFlag" /> 
											<input
											type="hidden" id="amountFlag" /></li>
										
									</ul>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>

		</div>
	</div>

	<script type="text/javascript">
		var defaultPagerManager;
		var balance = ${balance};
		seajs.use([ 'app/jsp/billing/defaultManager' ], function(
				DefaultPagerManager) {
			defaultPagerManager = new DefaultPagerManager({
				element : document.body
			});
			defaultPagerManager.render();
		});
	</script>
</body>
</html>
