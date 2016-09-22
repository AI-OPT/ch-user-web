<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="uedroot" value="${pageContext.request.contextPath}/template/default"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>供应商状态管理</title>
<%@include file="/inc/inc.jsp" %>
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
								<h5 class="pull-left">供货商状态列表</h5>
								</header>
                                    <table class="table table-border table-bordered">
                                        <thead>
                                            <tr>
                                                <th>用户名</th>
                                                <th>企业名称</th>
                                                <th>状态</th>
                                                <th>操作</th>
                                            </tr>
                                        </thead>
                                    <tbody id="TBODY_SUPLLIER">
                                    </tbody>
                                    </table>
                                </div>
                                	<!--/table表格结束-->
					            <!--分页-->          
								<div id="showMessageDiv"></div>
						          <div style="text-align: center">
									 <ul id="pagination-ul"></ul>
								  </div>
								<!--分页-->
                        </div>
                    </div>
                </div>
            </div>
            </div>
<script type="text/javascript">
var pager;
(function () {
	seajs.use('app/jsp/crm/supplierStateList', function (SupplierStateListPager) {
		pager = new SupplierStateListPager({element: document.body});
		pager.render();
	});
})();
</script>

<script id="scoreListImpl" type="text/x-jsrender">
{{for result ~pageSize=pageSize ~pageNo=pageNo}}
		<tr>
			<td id='userId{{:userId}}'>{{:userName}}</td>
			<td>{{:groupName}}</td>
			<td>{{:stateValue}}</td>
			<td><a class＝"btn-primary" href="javascript:void(0)" id="freeze_{{:userId}}" style="display:none;" onclick="pager._toFreeze('{{:userId}}')">冻结</a>
				<a class＝"btn-primary" href="javascript:void(0)" id="thraw_{{:userId}}" style="display:none;" onclick="pager._toThraw('{{:userId}}')">解冻</a>
				<a class＝"btn-primary" href="javascript:void(0)" id="cancel_{{:userId}}" style="display:none;" onclick="pager._toCancel('{{:userId}}')">注销</a>
				<a class＝"btn-primary" href="javascript:void(0)" id="recovery_{{:userId}}" style="display:none;" onclick="pager._toRecovery('{{:userId}}')">恢复</a></td>
		</tr>
	{{/for}}
</script>

</body>
</html>