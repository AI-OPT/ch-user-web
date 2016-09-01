<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="uedroot" value="${pageContext.request.contextPath}/template/default"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>历史评价明细</title>
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
                            <h2 class="pull-left">历史评价明细</h2>
                            </header>
                        <!--标题结束-->   
                            <div class="main-box-body clearfix">
                            	<!--table表格-->
                                <div class="table-responsive clearfix">
								    <div class="form-label pl-40">
								    	<ul>
								    		<li>
								    			<p class="word">供货商用户名:${supplier_name }</p>
								    		</li>
								    		<li>
								    			<p class="word">公司名称:${company_name }</p>
								    		</li>
								    		<li>
								    			<p class="word">综合评分:${total_score }</p>
								    		</li>
								    	</ul>
								    </div>
                                    <table class="table table-hover table-border table-bordered">
                                        <thead>
                                            <tr>
                                                <th>评价人</th>
                                                <th>评价时间</th>
                                                <th>总分</th>
                                                <th>商品质量(40)</th>
                                                <th>交付时间(20)</th>
                                                <th>商品价格(20)</th>
                                                <th>综合服务(20)</th>
                                            </tr>
                                        </thead>
                                    <tbody>
                                        <tr>
                                            <td>biu</td>
                                            <td>分公司</td>
                                            <td>全国</td>
                                            <td>总部</td>
                                            <td>北京</td>
                                            <td>北京</td>
                                            <td></td>
                                        </tr>
                                         <tr>
                                            <td>biu</td>
                                            <td>分公司</td>
                                            <td>全国</td>
                                            <td>总部</td>
                                            <td>北京</td>
                                            <td>北京</td>
                                            <td></td>
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
<script type="text/javascript">
var pager;
var scoreLogParams = $.parseJSON('${scoreLogParams}');
(function () {
	seajs.use('app/jsp/crm/scorelog', function (ScoreLogPager) {
		pager = new ScoreLogPager({element: document.body});
		pager.render();
	});
})();
</script>
</body>
</html>