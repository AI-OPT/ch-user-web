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
					                    <p><input name="control_date" class="int-text int-medium " type="text"/>
					                    </p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">企业名称</p>
					                    <p><input type="text" class="int-text int-medium"></p>
					                    <p><input type="button" class="btn-default btn-blue btn-mini" value="查询" /></p>
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
                                            	<th>用户名</th>
                                                <th>企业名称</th>
                                                <th>合同状态</th>
                                                <th>操作</th>
                                            </tr>
                                        </thead>
                                    <tbody>
                                        <tr>
                                        	<td>店铺test1</td>
                                            <td>test1营业执照</td>
                                            <td>未上传</td>
                                            <td  id="operation"><a href="${_base}/contract/contractShopManagerPager?userId=1">管理</a><a href="${_base}/contract/contractShopDetailPager?userId=1">查看</a></td>
                                        </tr>
                                        <tr>
                                        	<td>店铺test2</td>
                                            <td>test2营业执照</td>
                                            <td>未上传</td>
                                            <td  id="operation"><a href="${_base}/contract/contractShopManagerPager?userId=2">管理</a><a href="${_base}/contract/contractShopDetailPager?userId=2">查看</a></td>
                                        </tr>
                                         <tr>
                                        	<td>店铺test2</td>
                                            <td>test3营业执照</td>
                                            <td>未上传</td>
                                            <td  id="operation"><a href="${_base}/contract/contractShopManagerPager?userId=3">管理</a><a href="${_base}/contract/contractShopDetailPager?userId=3">查看</a></td>
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
    </div>
    </div>
</div>
</div>
</body>
</html>
