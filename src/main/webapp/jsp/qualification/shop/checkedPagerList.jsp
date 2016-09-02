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
					                    <p><input name="control_date" class="int-text int-medium " type="text"/>
					                    </p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">企业名称</p>
					                    <p><input type="text" class="int-text int-medium"></p>
					                </li>  
					            </ul>
					            <ul>
					                <li class="width-xlag">
					                <p class="word">&nbsp;</p>
					                <p><input type="button" class="biu-btn  btn-primary btn-blue btn-medium ml-10" value="查  询"></p>
					                </li>
					            </ul>
					         </div>
					   	<!--查询结束-->   
					 	<!--table表格-->
					 	<div class="main-box-body clearfix">
							<div class="order-list-table">
					           <ul>
						           <li><a href="#" class="current">已通过</a></li>
						           <li><a href="#">被拒绝</a></li>
					           </ul>                                        
					     	</div>
					     	<!--选项卡第一个-->
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
                                    <tbody>
                                        <tr>
                                        	<td>xionger</td>
                                            <td>企业营业执照注册名称</td>
                                            <td>2016-07-11</td>
                                            <td  id="operation"><a href="${_base}/qualification/toShopDetailPager">查看</a></td>
                                        </tr>
                                        <tr>
                                        	<td>xionger</td>
                                            <td>企业营业执照注册名称</td>
                                            <td>2016-07-11</td>
                                            <td  id="operation"><a href="${_base}/qualification/toShopDetailPager">查看</a></td>
                                        </tr>
                                         <tr>
                                        	<td>xionger</td>
                                            <td>企业营业执照注册名称</td>
                                            <td>2016-07-11</td>
                                            <td  id="operation"><a href="${_base}/qualification/toShopDetailPager">查看</a></td>
                                        </tr>
                                       
                                    </tbody>
                                    </table>
                               </div>
                                </div>
                            <!--选项卡第一个-->
                            <div id="date2" style="display:none;">
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
                                    <tbody>
                                        <tr>
                                        	<td>xionger</td>
                                            <td>企业营业执照注册名称1</td>
                                            <td>2016-07-11</td>
                                            <td  id="operation"><a href="#">查看</a></td>
                                        </tr>
                                        <tr>
                                        	<td>xionger</td>
                                            <td>企业营业执照注册名称</td>
                                            <td>2016-07-11</td>
                                            <td  id="operation"><a href="#">查看</a></td>
                                        </tr>
                                         <tr>
                                        	<td>xionger</td>
                                            <td>企业营业执照注册名称</td>
                                            <td>2016-07-11</td>
                                            <td  id="operation"><a href="#">查看</a></td>
                                        </tr>
                                       
                                    </tbody>
                                    </table>
                               </div>
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
    
    <!--底部-->
    <footer id="footer-bar" class="row">
   		 <p id="footer-copyright" class="col-xs-12">亚信</p>
    </footer>
   <!--/底部结束-->
</body>
</html>
