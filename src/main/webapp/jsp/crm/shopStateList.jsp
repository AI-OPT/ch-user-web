<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="uedroot" value="${pageContext.request.contextPath}/template/default"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>店铺状态管理</title>
<%@include file="/inc/inc.jsp" %>
<script type="text/javascript">  
    function getResult(data){  
        alert("through jsonp,receive data from other domain : "+data);  
    }  
</script>
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
                            <h2 class="pull-left">状态管理</h2>
                            </header>
                        <!--标题结束-->   
                            <div class="main-box-body clearfix">
                            	<!--table表格-->
                                <div class="table-responsive clearfix">
								<div class="form-label">
					           	<ul>
					                <li class="col-md-6">
					                    <p class="word">用户名</p>
					                    <p><input name="control_date" class="int-text int-medium " id="username " type="text">
					                    </p>
					                </li>
					                <li class="col-md-6">
					                    <p class="word">企业名称</p>
					                    <p><input class="int-text int-medium" type="text" id="companyName"></p>
					                    <p><input class="biu-btn  btn-primary btn-blue btn-medium ml-10" value="查  询" type="button" onclick="pager._getList();"></p>
					            </ul>
					         </div>

                                    <table class="table table-border table-bordered">
                                        <thead>
                                            <tr>
                                                <th>用户名</th>
                                                <th>企业名称</th>
                                                <th>状态</th>
                                                <th>操作</th>
                                            </tr>
                                        </thead>
                                    <tbody id="TBODY_SHOPSTATE">
                                    </tbody>
                                    </table>
                                </div>
                                <div id="info" class="text-c"></div>
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
	seajs.use('app/jsp/crm/shopStateList', function (ShopStateListPager) {
		pager = new ShopStateListPager({element: document.body});
		pager.render();
	});
})();
</script>

<script id="shopStateListImpl" type="text/x-jsrender">
	{{for result ~pageSize=pageSize ~pageNo=pageNo}}
		<tr>
			<td id='userId{{:userId}}'>{{:userName}}</td>
			<td>{{:groupName}}</td>
			<td>{{:stateValue}}</td>
			<td><a href="javascript:void(0)" id="freeze_{{:userId}}" style="display:none;" onclick="pager._toFreeze('{{:userId}}')">冻结</a>
				<a href="javascript:void(0)" id="thraw_{{:userId}}" style="display:none;" onclick="pager._toThraw('{{:userId}}')">解冻</a>
				<a href="javascript:void(0)" id="cancel_{{:userId}}" style="display:none;" onclick="pager._toCancel('{{:userId}}')">注销</a>
				<a href="javascript:void(0)" id="recovery_{{:userId}}" style="display:none;" onclick="pager._toRecovery('{{:userId}}')">恢复</a></td>
		</tr>
	{{/for}}
</script>

</body>
</html>