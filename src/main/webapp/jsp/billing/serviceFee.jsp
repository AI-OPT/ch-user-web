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
								<h4 class="pull-left">查看详情</h4>
							</header>
							<!--标题结束-->
							<div class="main-box-body clearfix">
								<!--table表格-->
								<div class="table-responsive clearfix">
									<div class="form-label pl-40">
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
									<div class="form-label  bd-bottom">
										<ul>
											<li><p class="word">当前结算设置</p>
										</ul>
									</div>
									<div class="form-label">
										<ul>
											<li class="col-md-6"><p class="col-md-3">保证金</p>
												<p>${deposit }</p></li>
										</ul>
										<ul>
											<li class="col-md-6"><p class="col-md-3">固定金额服务费</p>
												<p>${rentFeeStr }</p></li>
										</ul>
										<ul>
											<li class="col-md-6"><p class="col-md-3">实时划扣服务费</p>
												<p>${ratioStr }</p></li>
										</ul>
										<ul>
											<li>
												<p class="word">&nbsp;</p>
												<p>
													<input type="button" onclick="backup();"
														class="biu-btn  btn-primary btn-blue btn-medium ml-5"
														value="返回">
												</p>
											</li>
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
	<script type="text/javascript">
	function backup(){
		window.location.href=_base+"/billing/billingpager";
	}
	</script>
</body>
</html>