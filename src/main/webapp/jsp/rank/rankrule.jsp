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
<title>设置评级规则</title>
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
							<!--查询结束-->
						</div>
					</div>
				</div>
			</div>
		</div>
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
								<h2 class="pull-left">设置评级规则</h2>
							</header>
							<!--标题结束-->
							<div class="main-box-body clearfix">
								<!--table表格-->
								<div class="table-responsive clearfix">

									<div class="radio-box">
										<span>请选择评级周期:</span> <input type="radio" class="radio-2"
											name="demo-radio1" checked> <label for="radio-2">月</label>
										<input type="radio" class="radio-1" name="demo-radio1">
										<label for="radio-1">季度</label> <input type="radio"
											class="radio-1" name="demo-radio1"> <label
											for="radio-1">年</label>
									</div>
									<div>
										<span>请选择评级周期:</span>
										<select class="select select-small">
										<c:forEach var="i" begin="2" end="20">
										<option>${i}</option>
										</c:forEach>
										</select>
										<span>(2-20个等级之间)</span>
									</div>
									<table class="table table-border table-bordered">
										<thead>
											<tr>
												<th>店铺佣金等级</th>
												<th>等级名称</th>
												<th>等级图片</th>
											</tr>
										</thead>
										<tbody id="TBODY_RANKRULE">
										<tr>
										<td><span>等级1: </span><span>0</span><span>-</span><input class="int-text int-small" type="text"><span>元</span></td>
										<td><span>等级名称: </span><input class="int-text int-small" type="text"></td>
										<td><span>图片名称: </span><input class="int-text int-small" type="text">&nbsp;&nbsp;&nbsp;<span class="btn-upload">
											<input type="button" class="btn-default btn-medium" value="浏览文件"/>
											<input type="file" class="int-file"/>
										</span>
										<a href="#">删除</a></td>
										</tr>
										</tbody>
									</table>
								<!--/table表格结束-->
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
		(function() {
			seajs.use('app/jsp/rank/rankrule', function(RankRulePager) {
				pager = new RankRulePager({
					element : document.body
				});
				pager.render();
			});
		})();
	</script>

<script id="rankRuleImpl" type="text/x-jsrender">
	{{for id}}
		<tr>
			<td>{{:id}}</td>
			<td>{{:id}}</td>
			<td>{{:id}}</td>
		</tr>
	{{/for}}
</script>
<!-- 
<td><span>等级+'{{:id}}'+0-<input class="int-text int-small" type="text"><span>元</span></td>
			<td><span>等级名称: </span><input class="int-text int-small" type="text"></td>
			<td><span>图片名称: </span><input class="int-text int-small" type="text">&nbsp;&nbsp;&nbsp;<span class="btn-upload">
				<input type="button" class="btn-default btn-medium" value="浏览文件"/>
				<input type="file" class="int-file"/></span></td> -->
</body>
</html>