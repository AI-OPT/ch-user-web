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
<title>审核日志</title>
<%@include file="/inc/inc.jsp"%>
<script src="${uedroot}/scripts/modular/frame.js"></script>
<link rel="stylesheet" type="text/css"
	href="${uedroot}/css/modular/modular.css" />
</head>
<body>
	<div class="row">
		<!--内侧框架-->
		<div class="col-lg-12">
			<!--删格化-->
			<div class="main-box clearfix">
				<!--白色背景-->
				<div class="main-box-body clearfix">
					<header class="main-box-header clearfix">
						<h5 class="pull-left">入驻商户审核日志</h5>
					</header>
					<div class="form-label">
					<div id="dateDiv">
						<ul>
							<li class="col-md-6">
								<p class="word">开始时间</p>
								<p>
									<input class="int-text int-medium " readonly
										onClick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'#F{$dp.$D(\'orderTimeEnd\')}'})"
										id="orderTimeBegin" name="control_date"/>
									<span class="time"> <i class="fa  fa-calendar"></i></span>
								</p>
							</li>
							<li class="col-md-6">
								<p class="word">结束时间</p>
								<p>
									<input class="int-text int-medium " readonly
										onClick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'orderTimeBegin\')}'})"
										id="orderTimeEnd" name="control_date" /> <span class="time"><i
										class="fa  fa-calendar"></i></span>
								</p>
							</li>
						</ul>
						</div>
						<ul>
							<li class="col-md-6">
								<p class="word">审核人</p>
								<p>
									<input class="int-text int-medium" id="username" type="text"
										placeholder="请输入姓名">
								</p>
							</li>
							<li class="col-md-6">
								<p class="word">&nbsp;</p>
								<p>
									<input type="button"
										class="biu-btn  btn-primary btn-blue btn-medium ml-10"
										id="search" value="查  询" onclick="pager._search('1')">
								</p>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<!--删格化-->
		<div class="row">
			<!--内侧框架-->
			<div class="col-lg-12">
				<!--删格化-->
				<div class="main-box clearfix">
					<!--白色背景-->
					<div class="main-box-body clearfix">
						<table class="table table-hover table-border table-bordered">
							<thead>
								<tr>
									<th>用户名</th>
									<th>企业名称</th>
									<th>审核结果</th>
									<th>审核人</th>
									<th>审核时间</th>
									<th>审核原因</th>
								</tr>
							</thead>
							<tbody id="TBODY_AUDIT">
							</tbody>
						</table>
					</div>
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
	<script type="text/javascript">
		
	<%-- 展示日历 --%>
		$('#dateDiv').delegate('.fa-calendar', 'click', function() {
			var calInput = $(this).parent().prev();
			var timeId = calInput.attr('id');
			WdatePicker({
				el : timeId,
				readOnly : true
			});
		});
		var pager;
		(function() {
			seajs.use('app/jsp/auditlog/auditlog', function(AuditLogPager) {
				pager = new AuditLogPager({
					element : document.body
				});
				pager.render();
				pager._getAuditList('1');
			});
		})();
	</script>

	<script id="checkedImpl" type="text/x-jsrender">
{{for result ~pageSize=pageSize ~pageNo=pageNo}}
	<tr>
		<td>{{:userName}}</td>
		<td>{{:companyName}}</td>
		<td>{{:auditStatus}}</td>
		<td>{{:operName}}</td>
		<td>{{:auditTime}}</td>
		<td title="{{:auditDesc}}">{{:~subStr(10,auditDesc)}}</td>
	</tr>
{{/for}}
</script>
</body>
</html>
