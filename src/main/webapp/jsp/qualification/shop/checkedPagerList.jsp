<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="uedroot" value="${pageContext.request.contextPath}/template/default"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>供应商已审核</title>
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
								<h5 class="pull-left">入驻商户已审核列表</h5>
								</header>
					 	<div class="main-box-body clearfix">
					 	<div class="order-list-table">
					           <ul>
						           <li><a href="javascript:void(0)" class="current">已通过</a></li>
						           <li><a href="javascript:void(0)">已拒绝</a></li>
					           </ul>                                        
					    </div>
					     	<div id="date1">
                                <div class="table-responsive clearfix">
                                    <table class="table table-hover table-border table-bordered">
                                        <thead>      
                                            <tr>
                                            	<th>用户名</th>
                                                <th>企业名称</th>
                                                <th>审核时间</th>
                                                <th>操作</th>
                                            </tr>
                                        </thead>
                                    <tbody id="TBODY_CHECKED_PASS">
                                    </tbody>
                                    </table>
                               </div>
                                 <!--分页-->
                                  <div class="paging">
                            		<ul id="pagination-ul-pass"></ul>
								</div>
								<!--分页结束-->
								<div id="showMessageDiv_pass" class="text-c"></div>
                                </div>
                                <div id="date2" style="display:none;">
                                <div class="table-responsive clearfix">
                                    <table class="table table-hover table-border table-bordered">
                                        <thead>      
                                            <tr>
                                            	<th>用户名</th>
                                                <th>企业名称</th>
                                                <th>审核时间</th>
                                                <th>操作</th>
                                            </tr>
                                        </thead>
                                    <tbody id="TBODY_CHECKED_REJECT">
                                    </tbody>
                                    </table>
                               </div>
                                 <!--分页-->
                                  <div class="paging">
                            		<ul id="pagination-ul-reject"></ul>
								</div>
								<!--分页结束-->
								<div id="showMessageDiv_reject" class="text-c"></div>
                            	<!--/table表格结束-->
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
    </div>
        <script type="text/javascript">
		var pager;
		(function () {
			seajs.use('app/jsp/qualification/checkedPagerList', function (CheckedPagerListPager) {
				pager = new CheckedPagerListPager({element: document.body});
				pager.render();
				pager._getInitList('2');
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
