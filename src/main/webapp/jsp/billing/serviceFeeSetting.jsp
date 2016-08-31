<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="uedroot" value="${pageContext.request.contextPath}/template/default"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>服务费设置</title>
<%@include file="/inc/inc.jsp" %>
<style type="text/css">
* {
	font-size: 14px;
}
label.error {
	color: Red;
	font-size: 13px;
	margin-left: 5px;
	padding-left: 16px;
}
</style>
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
                            <h2 class="pull-left">服务费设置</h2>
                            </header>
                        <!--标题结束-->   
                            <div class="main-box-body clearfix">
                            	<!--table表格-->
                                <div class="table-responsive clearfix">
								
								 <div class="form-label pl-40">
								 	<ul>
								 		<li>
								 			<p class="word">用户名:</p>
								 			<p>${userName }</p>
								 		</li>
								 		<li>
								 			<p class="word">企业名称:</p>
								 			<p>${shopName }</p>
								 		</li>
								 	</ul>
								 	<ul>
								 		<li><p class="word">当前结算设置:</p>
								 	</ul>
								 	<ul>
								 		<li>
								 			<p class="word">固定金额服务费</p>
								 			<p class="word">${rentFeeStr }</p>
								 		</li>
								 	</ul>
								 	<ul>
								 		<li>
								 			<p class="word">实时划扣服务费</p>
								 			<p class="word">${ratioStr }</p>
								 		</li>
								 	</ul>
								 <form id="serviceFee">
								 <div class="mt-20 skin-minimal">
									  <div class="radio-box">
									  		<p class="word">固定金额服务费:  
										    <input type="radio" class="radio-1" value="0" name="needPayRent" onclick="pager._change('needPayRent','payRent');" checked>
										    <label for="radio-1">需缴纳</label>
										    <input type="radio" class="radio-1" value="1" name="needPayRent" onclick="pager._change('needPayRent','payRent');">
										    <label for="radio-1">无需缴纳</label>
										    </p>
									  </div>
									  <div id="payRent" style="display:">
									  	<p><input type="text" class="int-text int-mini" id="rentFee" name="rentFee" />元/
									  	<select class="select select-mini" id="rentCycleType" name="rentCycleType">
									  	<option value="Y">年</option>
									  	<option value="Q">季度</option>
									  	<option value="M">月</option>
									  	</select>
									  	</p>
									  </div>
								</div>
									  <div class="radio-box">
									  <p class="word">实时划扣服务费: 
										    <input type="radio" class="radio-1" value="0" name="needPayCycle" checked onclick="pager._change('needPayCycle','payCycle')">
										    <label for="radio-1">需缴纳</label>
										    <input type="radio" class="radio-1" value="1" name="needPayCycle" onclick="pager._change('needPayCycle','payCycle')">
										    <label for="radio-1">无需缴纳</label>
									  </div>
									   <div id="payCycle">
									  	<p><input type="text" class="int-text int-mini" name="ratio" id="ratio"/>% * 订单金额
									  	<input type="hidden" id="userId" name="userId" value="${userId }"/>
									  	</p>
										</div>
									</form>
									<div>
										<ul>
								 		<li>
								 			<input type="button" id="saveSetting" style="margin-left:50px;" class="biu-btn btn-blue btn-xlarge  radius" value="保存">
								 		</li>
								 		</ul>
								 	</div>
								 	
								 </div>
							</div>
                        </div>
                    </div>
                </div>
            </div>
            </div>
            </div>
    </div>
    
	<script type="text/javascript">
		var pager;
		(function() {
			seajs.use('app/jsp/billing/serviceFeeSetting', function(ServiceFeeSettingPager) {
				pager = new ServiceFeeSettingPager({
					element : document.body
				});
				pager.render() ;
			});
		})();
	</script>
</body>
</html>