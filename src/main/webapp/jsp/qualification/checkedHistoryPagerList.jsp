<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="uedroot" value="${pageContext.request.contextPath}/template/default"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>审核历史</title>
<%@include file="/inc/inc.jsp" %>
<script src="${uedroot}/scripts/modular/frame.js"></script>  
<link rel="stylesheet" type="text/css" href="${uedroot}/css/modular/modular.css"/>
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
					   	 </div>
                        </div>
                    </div>
                </div>
                </div>
            </div>
        <script type="text/javascript">
		var pager;
		(function () {
			seajs.use('app/jsp/qualification/checkedListPagerList', function (CheckedHistoryPagerListPager) {
				pager = new CheckedHistoryPagerListPager({element: document.body});
				pager.render();
			});
		})();
	</script>
	
	<script id="checkedImpl" type="text/x-jsrender">
{{for result ~pageSize=pageSize ~pageNo=pageNo}}
	<tr>
		<td>{{:userName}}</td>
		<td>{{:custName}}</td>
		<td>{{:auditTime}}</td>
		<td>
            <a class＝"btn-primary" href="javascript:void(0)" onclick="pager._toViewShopPage('{{:userId}}','{{:userName}}');">查看</a>
		</td>
	</tr>
{{/for}}
</script>
</body>
</html>
