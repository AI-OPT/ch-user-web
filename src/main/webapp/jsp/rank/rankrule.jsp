<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
									<form:form id="rankForm" method="post" enctype="multipart/form-data" action="${_base}/rank/saverule">
									<div class="radio-box">
										<span>请选择评级周期:</span> <input type="radio" class="radio-2"
											name="periodType_" checked value="M"> <label for="radio-2">月</label>
										<input type="radio" class="radio-1" name="periodType_" value="Q">
										<label for="radio-1">季度</label> <input type="radio"
											class="radio-1" name="periodType_" value="Y"> <label
											for="radio-1">年</label>
									</div>
									<div>
										<span>请选择店铺级数:</span>
										<select class="select select-small" id="rankRegion" name="rankRegion">
										<option value="">请选择</option>
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
										
										</tbody>
									</table>
									<div class="text-c">
									<input type="button" id="saveRule" class="biu-btn btn-blue btn-xlarge  radius" value="保存">
									<input type="hidden" id="picFlag" value="">
									</div>
									</form:form>
								<!--/table表格结束-->
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
		<!-- 模态框（Modal） 开始 -->
	<div class="modal fade" id="sureModal" tabindex="-1" role="dialog" aria-labelledby="stopSureModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 400px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">提示</h4>
				</div>
				<div class="modal-body" id="dialogContent">
					
				</div>
				<div class="modal-footer">
					<button type="button" class="biu-btn  btn-primary btn-blue btn-medium ml-10"
						data-dismiss="modal">确认</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	<!-- 模态框（Modal） 结束 -->
	
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
			<td><p class="f-14" style="font-weight:400;">等级 {{:index}}:  <input type='hidden' value='{{:index}}' name='list[{{:index-1}}].rank'><input class="int-text int-mini" name="list[{{:index-1}}].minFee" id="min{{:index}}" type="text" value="0" readonly="readonly"> - <input class="int-text int-mini" name="list[{{:index-1}}].maxFee" type="text" id="max{{:index}}" onblur="pager._changeValue('{{:index}}')">元</p></td>
			<td><p class="f-14">等级名称 :  <input class="int-text int-small" name="list[{{:index-1}}].rankName" type="text"></p></td>
			<td><p class="f-14">图片名称 :  <input class="int-text int-small" name="list[{{:index-1}}].rankLogo" id="rankLogo{{:index}}" readonly="readonly" type="text">&nbsp;&nbsp;&nbsp;<span class="btn-upload">
				<input type="button" class="btn-default btn-medium" value="浏览文件"/>
				<input type="file" class="int-file" id='img{{:index}}' name='img{{:index}}' onchange="pager._imgName('{{:index}}')"/></span></p></td>
		</tr>
	{{/for}}
</script>
</body>
</html>