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
<title>入驻商户评级规则</title>
<%@include file="/inc/inc.jsp"%>
</head>
<body>
	<div class="content-wrapper-iframe">
		<!--右侧灰色背景-->
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
								<!--标题-->
								<header class="main-box-header clearfix">
									<h5 class="pull-left">设置商户评级规则</h5>
								</header>
								<!--标题结束-->
								<!--table表格-->
								<div class="form-label" id="view" style="display:">
									<div class="form-label  bd-bottom">
										<ul>
											<li class="col-md-6">
												<p class="word">评级周期:</p>
												<p>${periodType }</p>
											</li>
											<li class="col-md-6">
												<p class="word">店铺级数:</p>
												<p>${rank }</p>
											</li>
										</ul>
									</div>
											<div class="right pr-30">
									<ul>
										<li>
												<input type="button" id="toEdit"
													class="biu-btn  btn-primary btn-blue btn-medium ml-5"
													value="修改">
										</li>
									</ul>
											</div>
									<table class="table table-border table-bordered">
										<thead>
											<tr>
												<th>店铺评分等级</th>
												<th>等级名称</th>
												<th>等级图片</th>
											</tr>
										</thead>
										<tbody id="TBODY_VIEW">
										</tbody>
									</table>
									<!--/table表格结束-->
								</div>
								<div id="edit" style="display: none;">
										<form:form method="post" id="rankRule"
											enctype="multipart/form-data"
											action="${_base}/rank/updaterule">
											<div class="form-label  bd-bottom">
												<ul>
													<c:choose>
														<c:when test="${periodType=='月'}">
															<li><p class="word">请设置评级周期:</p>
																<p>
																	<input type="radio" class="radio-2" name="periodType_"
																		value="M" checked> <label for="radio-2">月</label>
																</p>
																<p>
																	<input type="radio" class="radio-1" name="periodType_"
																		value="Q"> <label for="radio-1">季度</label>
																</p>
																<p>
																	<input type="radio" class="radio-1" name="periodType_"
																		value="Y"> <label for="radio-1">年</label>
																</p></li>
														</c:when>
														<c:when test="${periodType=='季度'}">
															<li><p class="word">请设置评级周期:</p>
																<p>
																	<input type="radio" class="radio-2" name="periodType_"
																		value="M"> <label for="radio-2">月</label>
																</p>
																<p>
																	<input type="radio" class="radio-1" name="periodType_"
																		value="Q" checked><label for="radio-1">季度</label>
																</p>
																<p>
																	<input type="radio" class="radio-1" name="periodType_"
																		value="Y"> <label for="radio-1">年</label>
																</p></li>
														</c:when>
														<c:otherwise>
															<li><p class="word">请设置评级周期:</p>
																<p>
																	<input type="radio" class="radio-2" name="periodType_"
																		value="M"> <label for="radio-2">月</label>
																</p>
																<p>
																	<input type="radio" class="radio-1" name="periodType_"
																		value="Q"> <label for="radio-1">季度</label>
																</p>
																<p>
																	<input type="radio" class="radio-1" name="periodType_"
																		value="Y" checked> <label for="radio-1">年</label>
																</p>
														</c:otherwise>
													</c:choose>
												</ul>
												<ul>
													<li>
														<p class="word">请设置评级数:</p>
														<p>
															<select class="select select-mini" id="rankRegion">
																<c:forEach var="i" begin="2" end="20">
																	<option>${i}</option>
																</c:forEach>
															</select>
														</p>
														<p>(2-20个等级之间)</p>
													</li>
												</ul>
												</div>
											<div class="table-responsive clearfix">
		                						<table class="table table-hover table-border table-bordered">
												<thead>
													<tr>
														<th class="text-l pl-10" width="30%"><b class="red">*</b>店铺评分等级</th>
														<th class="text-l pl-10" width="30%"><b class="red">*</b>等级名称</th>
														<th class="text-l pl-10" width="40%"><b class="red">*</b>等级图片</th>
													</tr>
												</thead>
												<tbody id="TBODY_RANKRULE">

												</tbody>
											</table>
											</div>
											<!--/table表格结束-->
										</form:form>
									<div class="row">
									 <p class="center mt-30">
										<input type="button" id="updateRule"
											class="biu-btn btn-primary btn-blue btn-medium ml-10"
											value="保存">
										<input type="button" id="backup"
											class="biu-btn btn-primary btn-blue btn-medium ml-10"
											value="返回"></p>
											 <input type="hidden" id="rankFlag"
											value=""> <input type="hidden" id="nameFlag" value="">
										<input type="hidden" id="picFlag" value="">
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
		var pager;
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
			<td><p class="f-14" style="font-weight:400;">等级{{:rank}}:{{:minScore}} 至 {{:maxScore}}分</p></td>
			<td><p class="f-14">{{:rankName}}</p></td>
			<td><p><image id='imgView{{:rank}}' src="" height="80px" width="80px"/></p></td>
		</tr>
	{{/for}}
</script>
	<script id="rankRuleInitImpl" type="text/x-jsrender">
	{{for}}
		<tr>
			<td class="text-l pl-10" style="white-space:nowrap"><p class="f-14" style="font-weight:400;">等级{{:rank}}:<input type='hidden' value='{{:rank}}' name='list[{{:rank-1}}].rank'><input class="int-text int-mini" name="list[{{:rank-1}}].minScore" value="{{:minScore}}" id="min{{:rank}}" type="text" readonly="readonly" style='border:none;background:none;font-weight:400;'>至<input class="int-text int-mini" name="list[{{:rank-1}}].maxScore" value="{{:maxScore}}" type="text" id="max{{:rank}}" onblur="pager._changeValue('{{:rank}}')" onfocus="pager._rankView('{{:rank}}')" maxlength="12" onkeydown="return doit()" style="">分<span id='rankInfo{{:rank}}' style='color:red;display:none'>1-12位整数</span><input type='text' style='display:none;color:red' id='rankMsg{{:rank}}'></p></td>
			<td class="text-l pl-10" style="white-space:nowrap"><p class="f-14"><input class="int-text int-mini" name="list[{{:rank-1}}].rankName" value="{{:rankName}}" id="name{{:rank}}" type="text" onblur="pager._valideName({{:rank}})" onfocus="pager._nameView('{{:rank}}')" maxlength='12'><span id='nameInfo{{:rank}}' style='color:red;display:none'>1-12位字符</span><input type='text' id='nameMsg{{:rank}}' style='display:none;color:red'></p></td>
			<td class="text-l pl-10" style="white-space:nowrap"><p class="f-14">图片名称:
				<span class="btn-upload">
				<input class="int-text int-mini" id="picName{{:rank}}" value="" type="text" disabeld="disabled" style="border:none;background:none;"><input type="hidden" id=rankLogo{{:rank}} name="list[{{:rank-1}}].rankLogo"><input type="hidden" id=rankName{{:rank}} name="rankName{{:rank}}">
				<input type="button" class="btn-primary btn-default btn-medium" value="浏览文件"/>
				<input type="file" class="int-file1" id='img{{:rank}}' name='img{{:rank}}' onchange="pager._imgName('{{:rank}}')"/>
				</span>
				<span id='imgInfo{{:rank}}' style='color:red;'>gif/jpg/jpeg/png</span><input type='text' id='picErr{{:rank}}' style='display:none;color:red;font-size:14px'></p>
			</td>
		</tr>
	{{/for}}
</script>
	<script id="rankRuleImpl" type="text/x-jsrender">
	{{for result}}
		<tr>
			<td class="text-l pl-10" style="white-space:nowrap"><p class='f-14' style='font-weight:400;'>等级{{:index}}:<input class="int-text int-mini" name="list[{{:index-1}}].minScore" id="min{{:index}}" type="text" value="0" readonly="readonly" style="border: none;background:none;font-weight:400;" onchange="pager._changeValue('{{:rank}}')"><input type='hidden' name=list[{{:index-1}}].rank value='{{:index}}'>至<input class="int-text int-mini" name="list[{{:index-1}}].maxScore" type="text" id="max{{:index}}" onblur="pager._changeValue('{{:index}}')" onfocus="pager._rankView('{{:index}}')" maxlength="12" onkeydown="return doit()" style="font-weight:400;">分<span id='rankInfo{{:index}}' style='color:red;display:none'>1-12位整数</span><input type='text' style='display:none;color:red' id='rankMsg{{:index}}'></p></td>
			<td class="text-l pl-10" style="white-space:nowrap"><p class='f-14'><input class="int-text int-mini" name="list[{{:index-1}}].rankName" type="text" id="name{{:index}}" onblur="pager._valideName('{{:index}}')" onfocus="pager._nameView('{{:index}}')" maxlength='12'><span id='nameInfo{{:index}}' style='color:red;display:none'>1-12位字符</span><input type='text' id='nameMsg{{:index}}' style='display:none;color:red'></p></td>
			<td class="text-l pl-10" style="white-space:nowrap"><p class='f-14'>图片名称:<span class="btn-upload"><input class="int-text int-mini" id="picName{{:index}}" disabled="disabled" type="text" style="border:none;background:none;"><input type="hidden" id="list[{{:index-1}}].rankLogo" name="rankLogo{{:index}}"><input type="hidden" id=rankName{{:index}} name="rankName{{:index}}">
				<input type="button" class="btn-primary btn-default btn-medium" value="浏览文件"/>
				<input type="file" class="int-file1" id='img{{:index}}' name='img{{:index}}' onchange="pager._imgName('{{:index}}')"/>
				<input type='text' style='display:none;color:red;font-size:14px' id='picErr{{:index}}'></span>
				<span id='imgInfo{{:index}}' style='color:red;'>gif/jpg/jpeg/png</span>
			</p></td>
		</tr>
	{{/for}}
</script>
</body>
</html>