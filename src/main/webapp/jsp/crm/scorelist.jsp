<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="uedroot" value="${pageContext.request.contextPath}/template/default"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>评价管理</title>
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
                        <!--标题-->
                            <header class="main-box-header clearfix">
                            <h2 class="pull-left">评价管理</h2>
                            </header>
                        <!--标题结束-->   
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
								 			<p><input type="button" class="btn-default btn-blue btn-mini" value="查询" /></p>
								 		</li>
								 	</ul>
								 </div>

                                    <table class="table table-border table-bordered">
                                        <thead>
                                            <tr>
                                                <th>用户名</th>
                                                <th>企业名称</th>
                                                <th>综合得分</th>
                                                <th>操作</th>
                                            </tr>
                                        </thead>
                                    <tbody id="TBODY_SCORELIST">
                                    </tbody>
                                    </table>
                                </div>
                                	<!--/table表格结束-->
                                	
					            <!--分页-->          
						          <div style="text-align: center">
									 <ul id="pagination-ul"></ul>
								  </div>
								<!--分页-->
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
	seajs.use('app/jsp/crm/scorelist', function (ScoreListPager) {
		pager = new ScoreListPager({element: document.body});
		pager.render();
	});
})();
</script>

<script id="scoreListImpl" type="text/x-jsrender">
	{{for result ~pageSize=pageSize ~pageNo=pageNo}}
		<tr>
			<td id='userId{{:userId}}'>{{:userName}}</td>
			<td>{{:groupName}}</td>
			<td>{{:totalScore}}</td>
			<td><a href="javascript:pager._toScorePage('{{:userId}}')" class="click">评价</a></td>
		</tr>
	{{/for}}
</script>

</body>
</html>