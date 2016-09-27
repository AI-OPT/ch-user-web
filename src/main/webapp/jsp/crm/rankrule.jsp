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
		<div class="row"><!--外围框架-->
            <div class="col-lg-12"><!--删格化-->
                <div class="row"><!--内侧框架-->
                    <div class="col-lg-12"><!--删格化-->
                        <div class="main-box clearfix"><!--白色背景-->
                        	<div class="main-box-body clearfix">	<!--padding20-->
							<!--标题-->
							<header class="main-box-header clearfix">
								<h4 class="pull-left">设置评级规则</h4>
							</header>
							<!--标题结束-->
							<form:form id="rankForm" method="post" enctype="multipart/form-data" action="${_base}/rank/saverule">
							<div class="form-label">
							<div class="form-label  bd-bottom">
				            <ul>
				                 <li class="col-lg-12">
				              		<p class="word">请选择评级周期:</p>
				              		<p><input type="radio" class="radio-2" name="periodType_" checked value="M"> <label for="radio-2">月</label></p>
									<p><input type="radio" class="radio-1" name="periodType_" value="Q"><label for="radio-1">季度</label></p> 
									<p><input type="radio" class="radio-1" name="periodType_" value="Y"> <label for="radio-1">年</label></p>
				                </li>
				                </ul>
				                <ul>
								<li class="col-lg-12">
									<p class="word">请选择店铺级数:</p>
									<p><select class="select select-mini" id="rankRegion" name="rankRegion">
									<option value="">请选择</option>
									<c:forEach var="i" begin="2" end="20">
									<option>${i}</option>
									</c:forEach>
									</select>
									<p>(2-20个等级之间)</p>
								</li>
								</ul>
								</div>
								</div>
							<div class="table-responsive clearfix">
		                		<table class="table table-hover table-border table-bordered">
										<thead>
											<tr>
												<th class="text-l pl-10"width="30%">店铺评分等级</th>
												<th class="text-l pl-10"width="30%">等级名称</th>
												<th class="text-l pl-10"width="40%">等级图片</th>
											</tr>
										</thead>
										<tbody id="TBODY_RANKRULE">
										
										</tbody>
									</table>
								</div>
					</form:form>
								</div>
									<div class="text-c">
									<input type="button" id="saveRule" class="biu-btn btn-primary btn-blue btn-medium ml-10" value="保存">
									<input type="hidden" id="rankFlag" value="">
									<input type="hidden" id="nameFlag" value="">
									<input type="hidden" id="picFlag" value="">
									</div>
								<!--/table表格结束-->
						</div>
					</div>
				</div>
			</div>
		</div>
		</div>
	
	<script type="text/javascript">
		var pager;
		(function() {
			seajs.use('app/jsp/crm/rankrule', function(RankRulePager) {
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
			<td class="text-l pl-10" style="white-space:nowrap"><p class='f-14' style='font-weight:400;'>等级 {{:index}}:<input class="int-text int-mini" name="list[{{:index-1}}].minScore" id="min{{:index}}" type="text" value="0" readonly="readonly" style="border: none;background:none;width:60px;font-weight:400;"><input type='hidden' name=list[{{:index-1}}].rank value='{{:index}}'>-<input class="int-text int-mini" name="list[{{:index-1}}].maxScore" type="text" id="max{{:index}}" onblur="pager._changeValue('{{:index}}')" maxlength="15" onkeydown="return doit()" style="width:60px;font-weight:400;">分<input type='text' style='display:none;color:red' id='rankMsg{{:index}}'></p></td>
			<td class="text-l pl-10" style="white-space:nowrap"><p class='f-14'><input class="int-text int-mini" name="list[{{:index-1}}].rankName" type="text" id="name{{:index}}" onblur="pager._valideName('{{:index}}')" maxlength='40'><input type='text' id='nameMsg{{:index}}' style='display:none;color:red'></p></td>
			<td class="text-l pl-10" style="white-space:nowrap"><span class="btn-upload"><p class='f-14'><b class="red">*</b>图片名称 :<input class="int-text int-mini" disabled="disabled" id="picName{{:index}}" type="text" style="border: none;background:none;width:60px;"><input type="hidden" name="list[{{:index-1}}].rankLogo" id="rankLogo{{:index}}"><input type="hidden" name="rankName{{:index}}" id="rankName{{:index}}">
				<input type="button" class="btn-primary btn-default btn-medium" value="浏览文件"/></p>
				<input type="file" class="int-file" id='img{{:index}}' name='img{{:index}}' onchange="pager._imgName('{{:index}}')"/></span>
				<input type='text' style='display:none;color:red;font-size:14px' id='picErr{{:index}}'></p></td>
		</tr>
	{{/for}}
</script>
</body>
</html>