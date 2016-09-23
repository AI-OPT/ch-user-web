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
<title>服务费设置</title>
<%@include file="/inc/inc.jsp"%>
<style type="text/css">
* {
	font-size: 14px;
}

label.error {
	color: Red;
	font-size: 13px;
	margin-left: 5px;
	padding-left: 16px;
}
</style>
</head>
<body>
	<div class="content-wrapper-iframe">
		<!--右侧灰色背景-->
		<!--框架标签结束-->
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
							<!--标题-->
							<header class="main-box-header clearfix">
								<h4 class="pull-left">服务费设置</h4>
							</header>
							<!--标题结束-->
							<div class="form-label">
								<ul>
									<li class="col-md-3">
										<p class="word">用户名:</p>
										<p>${userName }</p>
									</li>
									<li class="col-md-3">
										<p class="word">企业名称:</p>
										<p>${shopName }</p>
									</li>
								</ul>
							</div>
							<div class="form-label">
							<div class="nav-tplist-title bd-bottom pb-10">
								<ul>
									<li>当前结算设置</li>
								</ul>
							</div>
							<ul>
								<li class="col-md-6">
									<p class="word">固定金额服务费:</p>
									<p>${rentFeeStr }</p>
								</li>
								<li class="col-md-6">
									<p class="word">实时划扣服务费</p>
									<p>${ratioStr }</p>
								</li>
							</ul>
							</div>
							<!--table表格-->
							<form id="serviceFee">
								<div class="form-label">
								<div class="nav-tplist-title bd-bottom pb-10">
									<ul>
										<li>服务费设置</li>
									</ul>
								</div>
								<ul>
									<li class="col-lg-12">
										<p class="word">固定金额服务费:</p>
										<p>
											<input type="radio" value="0" name="needPayRent"
												onclick="pager._change('needPayRent','payRent');" checked>
										</p>
										<p>需缴纳</p>
										<p>
											<input type="radio" value="1" name="needPayRent"
												onclick="pager._change('needPayRent','payRent');">
										</p>
										<p>无需缴纳</p>
									</li>
								</ul>
								<div id="payRent" style="display:">
									<ul>
										<li class="col-lg-12">
											<p class="word">&nbsp;</p>
											<p>
												<input type="text" class="int-text int-mini" id="rentFee"
													name="rentFee" maxlength="15" onkeydown="return doit()">
											</p>
											<p>元/</p>
											<p>
												<select class="select select-mini" id="rentCycleType"
													name="rentCycleType">
													<option value="Y">年</option>
													<option value="Q">季度</option>
													<option value="M">月</option>
												</select>
											</p>
										</li>
									</ul>
								</div>
						</div>
						<div class="form-label pl-40">
							<ul>
								<li class="col-lg-12">
									<p class="word">实时划扣服务费:</p>
									<p>
										<input type="radio" value="0" name="needPayCycle" checked
											onclick="pager._change('needPayCycle','payCycle')">需缴纳
									</p>
									<p>
										<input type="radio" value="1" name="needPayCycle"
											onclick="pager._change('needPayCycle','payCycle')">无需缴纳
									</p>
								</li>
							</ul>
							<div id="payCycle" style="display:">
								<ul>
									<li class="col-lg-12">
										<p class="word">&nbsp;</p>
										<p>
											<input type="text" class="int-text int-mini" name="ratio"
												id="ratio" /> % * 订单金额
										</p> <input type="hidden" id="userId" name="userId"
										value="${userId }" />
									</li>
								</ul>
							</div>
							<ul>
								<li>
									<p class="word">&nbsp;</p>
									<p>
										<input type="button" id="saveSetting"
											class="biu-btn  btn-primary btn-blue btn-medium ml-10"
											value="保存">
									</p>
								</li>
							</ul>
							</div>
						</form>
						</div>
						<div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		var pager;
		(function() {
			seajs.use('app/jsp/billing/serviceFeeSetting', function(
					ServiceFeeSettingPager) {
				pager = new ServiceFeeSettingPager({
					element : document.body
				});
				pager.render();
			});
		})();
	</script>
</body>
</html>