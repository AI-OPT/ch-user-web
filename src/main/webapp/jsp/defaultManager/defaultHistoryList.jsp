<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="uedroot" value="${pageContext.request.contextPath}/template/default"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>保证金及费用设置</title>
<%@include file="/inc/inc.jsp" %>
<script src="${uedroot}/scripts/modular/frame.js"></script>  
<link rel="stylesheet" type="text/css" href="${uedroot}/css/modular/modular.css"/>
</head>
<body>
  		  <div class="row"><!--外围框架-->
            <div class="col-lg-12"><!--删格化-->
                <div class="row"><!--内侧框架-->
                    <div class="col-lg-12"><!--删格化-->
                        <div class="main-box clearfix"><!--白色背景-->   
							 	<!--查询条件-->
	                    		 <div class="form-label">
					           	<ul>
					                <li class="col-md-6">
					                    <p class="word">用户名</p>
					                    <p>${userName}</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">企业名称</p>
					                    <p>${custName}</p>
					                </li>  
					            </ul>
					         </div>
					   	<!--查询结束-->   
					 	<!--table表格-->
					 	 <div class="main-box-body clearfix">
					     	<div id="date1">
                                <div class="table-responsive clearfix">
                                    <table class="table table-hover table-border table-bordered">
                                        <thead>      
                                            <tr>
                                            	<th>违约时间</th>
                                                <th>违约原因</th>
                                                <th>扣款金额(元）</th>
                                                <th>操作人</th>
                                            </tr>
                                        </thead>
                                      	<tbody id="TBODY_DEFAULTLIST">
                                     	</tbody>
                                     </table>
                               </div>
                                </div>
                            <!--/table表格结束-->
                                <!--分页-->
                                <div id="showMessageDiv" class="text-c"></div>
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
    
    <!--底部-->
    <footer id="footer-bar" class="row">
   		 <p id="footer-copyright" class="col-xs-12">亚信</p>
    </footer>
   <!--/底部结束-->
   
   
<script type="text/javascript">
var pager;
var userId='${userId}';
var loginNameInfo='${userName}';
var custNameInfo='${custName}';
(function () {
	seajs.use('app/jsp/billing/defaultHistoryList', function (DefaultHistoryPager) {
		pager = new DefaultHistoryPager({element: document.body});
		pager.render();
	});
})();
</script>
<script id="defaultListImpl" type="text/x-jsrender">
{{for result ~pageSize=pageSize ~pageNo=pageNo}}
	<tr>
		<td>{{:~timesToFmatter(deductDate)}}</td>
		<td>{{:defaultReason}}</td>
		<td>{{:deductBalance}}</td>
		<td>{{:operName}}</td>
	</tr>
{{/for}}
</script>
</body>
</html>
