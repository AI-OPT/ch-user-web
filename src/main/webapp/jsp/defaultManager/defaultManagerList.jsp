<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="uedroot" value="${pageContext.request.contextPath}/template/default"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>违约管理</title>
<%@include file="/inc/inc.jsp" %>
</head>
<body>
   <div class="content-wrapper-iframe" ><!--右侧灰色背景-->
     <!--框架标签结束-->
      <div class="row"><!--外围框架-->
     	<div class="col-lg-12"><!--删格化-->
             <div class="row"><!--内侧框架-->
	                 <div class="col-lg-12"><!--删格化-->
	                    <div class="main-box clearfix"><!--白色背景-->
					   	<!--查询结束-->      
	         			</div>
	                	</div>
              </div>
         </div>
     </div>	
     <!--框架标签结束-->
  		  <div class="row"><!--外围框架-->
            <div class="col-lg-12"><!--删格化-->
                <div class="row"><!--内侧框架-->
                    <div class="col-lg-12"><!--删格化-->
                        <div class="main-box clearfix"><!--白色背景-->
                            <div class="main-box-body clearfix">
                            	<!--table表格-->
                                <div class="table-responsive clearfix">
								
								 <div class="form-label pl-40">
								 	<ul>
								 		<li>
								 			<p class="word">用户名:</p>
								 			<p><input type="text" class="int-text int-medium"/></p>
								 		</li>
								 		<li>
								 			<p class="word">企业名称:</p>
								 			<p><input type="text" class="int-text int-medium"/></p>
								 			<p><input type="button" class="btn-default btn-blue btn-mini" onclick="pager._getList();" value="查询" /></p>
								 		</li>
								 	</ul>
								 </div>

                                    <table class="table table-border table-bordered">
                                    <thead>
                                            <tr>
                                                <th>用户名</th>
                                                <th>企业名称</th>
                                                <th>经营品类</th>
                                                <th>违约操作</th>
                                            </tr>
                                     </thead>
                                     <tbody id="TBODY_DEFAULTLIST">
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
  </div>   
<script type="text/javascript">
(function () {
	seajs.use('app/jsp/billing/defaultManagerList', function (DefaultManagerListPager) {
		pager = new DefaultManagerListPager({element: document.body});
		pager.render();
	});
})();
</script>
<script id="defaultManagerImpl" type="text/x-jsrender">
{{for result ~pageSize=pageSize ~pageNo=pageNo}}
	<tr>
		<td>{{:userName}}</td>
		<td>{{:custName}}</td>
		<td>{{:BusinessCategory}}</td>
		<td>
			<a href="${_base}/defaultManager/addDefaultInfo?userId={{:userId}}&userName={{:userName}}&custName={{:custName}}">扣款</a>
            <a href="${_base}/defaultManager/defaultHistoryPager?userId={{:userId}}&userName={{:userName}}&custName={{:custName}}">扣款历史</a>
		</td>
	</tr>
{{/for}}
</script>
</body>
</html>