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
                        <!--标题-->
                            <header class="main-box-header clearfix">
                            <h2 class="pull-left">保证金/服务费管理</h2>
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
                                                <th>经营品类</th>
                                                <th>违约操作</th>
                                            </tr>
                                     </thead>
                                    <tbody id="TBODY_BILLLIST">
                                    	 <tr>
                                        	<td>username_login1</td>
                                            <td>custName1</td>
                                            <td>品类信息1</td>
                                            <td  id="operation">
                                            	<a href="${_base}/defaultManager/addDefaultInfo?userId=1&userName='username_login1'&custName='custName1'">扣款</a>
                                            	<a href="${_base}/defaultManager/defaultHistoryPager?userId=1&userName='username_login1'&custName='custName1'">扣款历史</a>
                                            </td>
                                        </tr>
                                         <tr>
                                        	<td>username_login2</td>
                                            <td>custName2</td>
                                            <td>品类信息1</td>
                                            <td  id="operation">
                                            	<a href="${_base}/defaultManager/addDefaultInfo?userId=2&userName='username_login2'&custName='custName2'">扣款</a>
                                            	<a href="${_base}/contract/defaultManagerCycleDetail?userId=2&userName='username_login2'&custName='custName2'">扣款历史</a>
                                            </td>
                                        </tr>
                                         <tr>
                                        	<td>username_login1</td>
                                            <td>custName3</td>
                                            <td>品类信息1</td>
                                            <td  id="operation">
                                            	<a href="${_base}/defaultManager/addDefaultInfo?userId=3&userName='username_login3'&custName='custName3'">扣款</a>
                                            	<a href="${_base}/defaultManager/defaultManagerCycleDetail?userId=3&userName='username_login3'&custName='custName3'">扣款历史</a>
                                            </td>
                                        </tr>
                                    </tbody>
                                    </table>
                                    </div>
                                	<!--/table表格结束-->
					            	<!--分页-->
                                <div class="paging">
                            		<ul class="pagination">
									<li class="disabled"><a href="#"><i class="fa fa-chevron-left"></i></a></li>
									<li class="active"><a href="#">1</a></li>
									<li><a href="#">2</a></li>
									<li><a href="#">3</a></li>
									<li><a href="#">4</a></li>
									<li><a href="#">5</a></li>
									<li><a href="#"><i class="fa fa-chevron-right"></i></a></li>
								</ul>
								</div>
								<!--分页结束-->
                        </div>
                    </div>
                </div>
            </div>
            </div>
    </div>
  </div>   
</body>
</html>