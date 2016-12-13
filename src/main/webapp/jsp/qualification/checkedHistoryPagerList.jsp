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
<title>审核历史</title>
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
						<!--查询条件-->
						<div class="form-label">
							<ul>
								<li class="col-md-6">
									<p class="word">用户名:</p>
									<p>
										${userName }
									</p>
								</li>
								<li class="col-md-6">
									<p class="word">企业名称:</p>
									<p>
										${custName }
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
								<h5 class="pull-left">审核历史列表</h5>
							</header>
							<div class="main-box-body clearfix">
								<div id="date1">
									<div class="table-responsive clearfix">
										<table class="table table-hover table-border table-bordered">
											<thead>
												<tr>
													<th>审核结果</th>
													<th>审核人</th>
													<th>审核时间</th>
													<th>审核原因</th>
												</tr>
											</thead>
											<tbody id="TBODY_CHECKED">
											</tbody>
										</table>
									</div>
									<!--/table表格结束-->
									<!--分页-->
									<div class="paging">
										<ul id="pagination-ul"></ul>
									</div>
									<!--分页结束-->
									<div id="showMessageDiv" class="text-c"></div>
                                	</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	<script type="text/javascript">
		var pager;
		var userId = '${userId}';
		(function() {
			seajs.use('app/jsp/qualification/checkedHistoryPagerList', function(
					CheckedHistoryPagerListPager) {
				pager = new CheckedHistoryPagerListPager({
					element : document.body
				});
				pager.render();
				pager._toViewHistoryPage(userId);
			});
		})();
	</script>

	<script id="checkedImpl" type="text/x-jsrender">
{{for result ~pageSize=pageSize ~pageNo=pageNo}}
	<tr>
		<td>{{:auditStatus}}</td>
		<td>{{:operName}}</td>
		<td>{{:auditTime}}</td>
		<td>{{:auditDesc}}</td>
	</tr>
{{/for}}
</script>
</body>
</html>
