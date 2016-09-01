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
								<div id="view" style="display:">
								<div class="table-responsive clearfix">
									<div class="radio-box">
										<span>评级周期:</span><span>${ periodType}</span> 
									</div>
									<div>
										<span>店铺级数:</span>
										<span>${rank }</span>
									</div>
									<div class="text-r"><input type="button" id="toEdit" class="biu-btn btn-blue btn-small radius" value="修改">
									</div>
									<table class="table table-border table-bordered">
										<thead>
											<tr>
												<th>店铺佣金等级</th>
												<th>等级名称</th>
												<th>等级图片</th>
											</tr>
										</thead>
										<tbody id="TBODY_VIEW">
										
										</tbody>
									</table>
								<!--/table表格结束-->
								</div>
								</div>
								
								<div id="edit" class="table-responsive clearfix" style="display:none;">
								<form:form method="post" id="rankRule" enctype="multipart/form-data" action="${_base}/rank/updaterule">
									<div class="radio-box">
										<c:choose>
										<c:when test="${periodType=='月'}">
										<span>请选择评级周期:</span> <input type="radio" class="radio-2"
											name="periodType_" value="M" checked> <label for="radio-2">月</label>
										<input type="radio" class="radio-1" name="periodType_" value="Q">
										<label for="radio-1">季度</label> <input type="radio"
											class="radio-1" name="periodType_" value="Y"> <label
											for="radio-1">年</label>
											 </c:when>
											<c:when test="${periodType=='季度'}">
											    <span>请选择评级周期:</span> <input type="radio" class="radio-2"
											name="periodType_" value="M"> <label for="radio-2">月</label>
										<input type="radio" class="radio-1" name="periodType_" value="Q" checked>
										<label for="radio-1">季度</label> <input type="radio"
											class="radio-1" name="periodType_" value="Y"> <label
											for="radio-1">年</label>
											 </c:when>
											 <c:otherwise>
											    <span>请选择评级周期:</span> <input type="radio" class="radio-2"
											name="periodType_" value="M"> <label for="radio-2">月</label>
										<input type="radio" class="radio-1" name="periodType_" value="Q">
										<label for="radio-1">季度</label> <input type="radio"
											class="radio-1" name="periodType_" value="Y" checked> <label
											for="radio-1">年</label>
											 </c:otherwise>
											</c:choose>
									</div>
									<div>
										<span>请选择评级周期:</span>
										<select class="select select-small" id="rankRegion">
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
									<input type="button" id="updateRule" class="biu-btn btn-blue btn-xlarge  radius" value="保存">
									<input type="hidden" id="rankFlag" value="">
									<input type="hidden" id="nameFlag" value="">
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
	<script type="text/javascript">
		var pager;
		var result=${result};
		var urlMap=${urlMap};
		var nameMap=${nameMap};
		var rank=${rank};
		var periodType='${periodType}';
		(function() {
			seajs.use('app/jsp/crm/rankrule-edit', function(RankRuleEditPager) {
				pager = new RankRuleEditPager({
					element : document.body
				});
				pager.render();
			});
		})();
	</script>

<script id="rankRuleViewImpl" type="text/x-jsrender">
	{{for}}
		<tr>
			<td><p class="f-14" style="font-weight:400;">等级{{:rank}}:  {{:minScore}} - {{:maxScore}}元</p></td>
			<td><p class="f-14" style="font-weight:400;">等级名称:  {{:rankName}}</p></td>
			<td><p class="f-14" style="font-weight:400;">图片名称:  <span id="rankLogoView{{:rank}}"></span><image id='imgView{{:rank}}' src="" height="80" width="100"/></p></td>
		</tr>
	{{/for}}
</script>
<script id="rankRuleInitImpl" type="text/x-jsrender">
	{{for}}
		<tr>
			<td><p class="f-14" style="font-weight:400;">等级 {{:rank}}:  <input type='hidden' value='{{:rank}}' name='list[{{:rank-1}}].rank'><input class="int-text int-mini" name="list[{{:rank-1}}].minScore" value="{{:minScore}}" id="min{{:rank}}" type="text" readonly="readonly" style='border:1px;border-bottom-style:none;border-top-style:none;border-left-style:none;border-right-style:none;'> - <input class="int-text int-mini" name="list[{{:rank-1}}].maxScore" value="{{:maxScore}}" type="text" id="max{{:rank}}" onblur="pager._changeValue('{{:rank}}')">元<input type='text' style='display:none;color:red' id='rankMsg{{:rank}}'></p></td>
			<td><p class="f-14">等级名称 :  <input class="int-text int-small" name="list[{{:rank-1}}].rankName" value="{{:rankName}}" id="name{{:rank}}" type="text" onblur="pager._valideName({{:rank}})"><input type='text' id='nameMsg{{:rank}}' style='display:none;color:red'></p></td>
			<td><p class="f-14">图片名称 :  <input class="int-text int-small" name="list[{{:rank-1}}].rankLogo" id="rankLogo{{:rank}}" value="" type="text" readonly="readonly" style='border:1px;border-bottom-style:none;border-top-style:none;border-left-style:none;border-right-style:none;'>&nbsp;&nbsp;&nbsp;<span class="btn-upload">
				<input type="button" class="btn-default btn-medium" value="浏览文件"/>
				<input type="file" class="int-file" id='img{{:rank}}' name='img{{:rank}}' onchange="pager._imgName('{{:rank}}')"/></span></p></td>
		</tr>
	{{/for}}
</script>
<script id="rankRuleImpl" type="text/x-jsrender">
	{{for id}}
		<tr>
			<td><p class="f-14" style="font-weight:400;">等级 {{:index}}:  <input class="int-text int-mini" name="list[{{:index-1}}].minScore" id="min{{:index}}" type="text" value="0" readonly="readonly" style='border:1px;border-bottom-style:none;border-top-style:none;border-left-style:none;border-right-style:none;'><input type='hidden' name=list[{{:index-1}}].rank value='{{:index}}'> - <input class="int-text int-mini" name="list[{{:index-1}}].maxScore" type="text" id="max{{:index}}" onblur="pager._changeValue('{{:index}}')">元<input type='text' style='display:none;color:red' id='rankMsg{{:index}}'></p></td>
			<td><p class="f-14">等级名称 :  <input class="int-text int-small" name="list[{{:index-1}}].rankName" type="text" id="name{{:index}}" onblur="pager._valideName('{{:index}}')"><input type='text' id='nameMsg{{:index}}' style='display:none;color:red'></p></td>
			<td><p class="f-14">图片名称 :  <input class="int-text int-small" name="list[{{:index-1}}].rankLogo" id="rankLogo{{:index}}" readonly="readonly" type="text" style="border:1px;border-bottom-style:none;border-top-style:none;border-left-style:none;border-right-style:none;">&nbsp;&nbsp;&nbsp;<span class="btn-upload">
				<input type="button" class="btn-default btn-medium" value="浏览文件"/>
				<input type="file" class="int-file" id='img{{:index}}' name='img{{:index}}' onchange="pager._imgName('{{:index}}')"/></span></p></td>
		</tr>
	{{/for}}
</script>
</body>
</html>