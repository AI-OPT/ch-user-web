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
<title>评价供货商</title>
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
									<span class="word">供货商用户名：${supplier_name }</span> <span
										class="word">公司名称：${company_name }</span>
								</p>
							</div>
							<div class="main-box-body clearfix">
								<!--table表格-->
								<div class="table-responsive clearfix">
									<div class="form-label pl-40">
										<form id="scorePage">
											<c:forEach items="${scoreKpiList}" var="ctScoreKpiVo"
												varStatus="status">
												<ul>
													<li class="word" style="font-weight: bold;">${status.count}.${ctScoreKpiVo.kpiName }</li>
													<li><input type="text" class="int-text int-medium"
														placeholder="请输入评分" name='${status.count }'
														id='${status.count }' /></li>
													<li style="color: red">&nbsp;&nbsp;&nbsp;${ctScoreKpiVo.minScore }-${ctScoreKpiVo.maxScore }</li>
												</ul>
												<ul>
													<li>注:${ctScoreKpiVo.kpiDesc }</li>
												</ul>
											</c:forEach>
										</form>
										<ul>
											<li style="margin-left: 70px"><input type="button"
												id="submitScore" class="biu-btn btn-blue btn-xlarge  radius"
												value="提交评价"></li>
											<li><input type="hidden" id="scoreFlag" /></li>
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
			seajs.use('app/jsp/score/scorepage', function(ScorePagePager) {
				pager = new ScorePagePager({
					element : document.body
				});
				pager.render();
			});
		})();
	</script>
</body>
</html>