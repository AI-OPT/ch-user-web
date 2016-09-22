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
<title>保证金/服务费管理</title>
<%@include file="/inc/inc.jsp"%>
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
						<!--查询条件-->
						<div class="form-label">
							<ul>
								<li class="col-md-6">
									<p class="word">用户名</p>
									<p>
										<input name="control_date" class="int-text int-medium "
											id="username" type="text" />
									</p>
								</li>
								<li class="col-md-6">
									<p class="word">企业名称</p>
									<p>
										<input type="text" class="int-text int-medium"
											id="companyName">
									</p>
									<p>
										<input type="button"
											class="biu-btn  btn-primary btn-blue btn-medium ml-10"
											value="查询" onclick="pager._getList();" />
									</p>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--table表格-->
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
						<div class="main-box-body clearfix">
						<header class="main-box-header clearfix">
								<h5 class="pull-left">保证金/服务费列表</h5>
								</header>
									<table class="table table-border table-bordered">
										<thead>
											<tr>
												<th>用户名</th>
												<th>企业名称</th>
												<th>经营品类</th>
												<th>应缴保证金</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody id="TBODY_BILLLIST">
										</tbody>
									</table>
								</div>
								<div id="showMessageDiv" class="text-c"></div>
								<!--/table表格结束-->
							</div>
							<!--分页-->
							<div style="text-align: center">
								<ul id="pagination-ul"></ul>
							</div>
							<!--分页-->
						</div>
					</div>
				</div>
			</div>
	<script type="text/javascript">
		var pager;
		(function() {
			seajs.use('app/jsp/billing/billingList',
					function(BillingListPager) {
						pager = new BillingListPager({
							element : document.body
						});
						pager.render();
					});
		})();
	</script>
	<script id="bailListImpl" type="text/x-jsrender">
	{{for result ~pageSize=pageSize ~pageNo=pageNo}}
		<tr>
			<td id={{:userId}}>{{:userName}}</td>
			<td>{{:shopName}}</td>
			<td id={{:busiType}}>{{:busiType}}</td>
			<td id={{:deposit}}>{{:deposit}}</td>
			<td><a class＝"btn-primary" href="javascript:void(0)" onclick="pager._toMarginPage('{{:userId}}','{{:userName}}')">保证金设置</a>
				<a class＝"btn-primary" href="javascript:void(0)" onclick="pager._toServiceFeeSettingPage('{{:userId}}','{{:userName}}')">服务费设置</a>
				<a class＝"btn-primary" href="javascript:void(0)" onclick="pager._toServiceFeePage('{{:userId}}','{{:userName}}')">查看详情</a></td>
		</tr>
	{{/for}}
</script>

</body>
</html>