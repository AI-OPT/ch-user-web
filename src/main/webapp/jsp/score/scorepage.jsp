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
<title>历史评价明细</title>
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
								<h2 class="pull-left">评价供货商</h2>
							</header>
							<!--标题结束-->
							<div style="padding-left: 40px">
								<p>
									<span class="word">供货商用户名：${supplier_name }</span>
									<span class="word">公司名称：${company_name }</span>
								</p>								
							</div>	
							<div class="main-box-body clearfix">
								<!--table表格-->
								<div class="table-responsive clearfix">
									<div class="form-label pl-40">
									<%-- 	<ul>
											<li class="word">1.${scoreKpiList[0].ctScoreKpiVo.kpiName }</li>
											<li><input type="text" class="int-text int-medium" placeholder="请输入评分" /></li>
											<li>${scoreKpiList[0].ctScoreKpiVo.minScore }-${scoreKpiList[0].ctScoreKpiVo.maxScore }</li><br/>
										</ul> --%>
										
										
										<c:forEach items="${scoreKpiList}" var="ctScoreKpiVo" >
										<ul>
											<li class="word" style="font-weight:bold;">1.${ctScoreKpiVo.kpiName }</li>
											<li><input type="text" class="int-text int-medium" placeholder="请输入评分" /></li>
											<li style="color:red">&nbsp;&nbsp;&nbsp;${ctScoreKpiVo.minScore }-${ctScoreKpiVo.maxScore }</li>
										</ul>
										<ul>
											<li>注:${ctScoreKpiVo.kpiDesc }</li>
										</ul>
									     </c:forEach> 
									     <ul>
									     	<li style="margin-left:70px"><input type="button" class="biu-btn btn-blue btn-xlarge  radius" value="提交评价"></li>
									     </ul>
										<%-- <ul>
											<li>注:${scoreKpiList[0].ctScoreKpiVo.kpiDesc }</li>
										</ul>
										
										<ul>
											<li class="word">2.${scoreKpiList[1].ctScoreKpiVo.kpiName }</li>
											<li><input type="text" class="int-text int-medium" placeholder="请输入评分" /></li>
											<li>${scoreKpiList[1].ctScoreKpiVo.minScore }-${scoreKpiList[1].ctScoreKpiVo.maxScore }</li><br/>
										</ul>
										
										<ul>
											<li>注:${scoreKpiList[1].ctScoreKpiVo.kpiDesc }</li>
										</ul>
										<ul>
											<li class="word">3.${scoreKpiList[2].ctScoreKpiVo.kpiName }</li>
											<li><input type="text" class="int-text int-medium" placeholder="请输入评分" /></li>
											<li>${scoreKpiList[2].ctScoreKpiVo.minScore }-${scoreKpiList[2].ctScoreKpiVo.maxScore }</li><br/>
										</ul>
										<ul>
											<li>注:${scoreKpiList[2].ctScoreKpiVo.kpiDesc }</li>
										</ul>
										
										<ul>
											<li class="word">4.${scoreKpiList[3].ctScoreKpiVo.kpiName }</li>
											<li><input type="text" class="int-text int-medium" placeholder="请输入评分" /></li>
											<li>${scoreKpiList[3].ctScoreKpiVo.minScore }-${scoreKpiList[3].ctScoreKpiVo.maxScore }</li><br/>
										</ul>
										<ul>
											<li>注:${scoreKpiList[3].ctScoreKpiVo.kpiDesc }</li>
										</ul>
										<ul>
											<li><input type="button" class="biu-btn btn-blue btn-xlarge  radius" value="首要"></li>
										</ul> --%>
										</div>
							</div>
						</div>
					</div>
				</div>
				</div>
				</div>
				<script type="text/javascript">
					var pager;
					var scorePageParams = $.parseJSON('${scorePageParams}');
					(function() {
						seajs.use('app/jsp/score/scorepage', function(
								ScorePagePager) {
							pager = new ScorePagePager({
								element : document.body
							});
							pager.render();
						});
					})();
				</script>
</body>
</html>