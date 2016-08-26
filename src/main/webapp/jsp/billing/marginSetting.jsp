<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="uedroot" value="${pageContext.request.contextPath}/template/default"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>保证金设置</title>
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
                            <h2 class="pull-left">保证金设置</h2>
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
								 		<li><p class="word">当前保证金:</p>
								 		<p>${deposit }</p></li>
								 	</ul>
								 	<form>
								 	<ul>
								 	<li>
								 	<p class="word">保证金:</p>
								 	<p class="word"><input type="text" class="int-text int-mini"  placeholder="" name="deposit" id="deposit"/></p>
								 	<p class="word">元(一次性收取)</p>
								 	</li>
								 	</ul>
								 	<ul>
								 	<li>
								 	<input type="hidden" value="${userId }" name="userId">
								 		<input type="button" id="saveSetting" style="margin-left:110px;" class="biu-btn btn-blue btn-xlarge  radius" value="保存">
								 	</li>
								 	</ul>
								 	</form>
								 </div>
							</div>
                        </div>
                    </div>
                </div>
            </div>
            </div>
    </div>
  </div>   
  
  <!-- 模态框（Modal） 开始 -->
	<div class="modal fade" id="sureModal" tabindex="-1" role="dialog" aria-labelledby="stopSureModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 400px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">提示</h4>
				</div>
				<div class="modal-body" id="dialogContent">
					
				</div>
				<div class="modal-footer">
					<button type="button" onclick="pager._jump();" class="biu-btn  btn-primary btn-blue btn-medium ml-10"
						data-dismiss="modal">确认</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	<!-- 模态框（Modal） 结束 -->
  
    <!-- 模态框（Modal） 开始 -->
	<div class="modal fade" id="sureModal2" tabindex="-1" role="dialog" aria-labelledby="stopSureModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 400px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">提示</h4>
				</div>
				<div class="modal-body" id="dialogContent2">
					
				</div>
				<div class="modal-footer">
					<button type="button" class="biu-btn  btn-primary btn-blue btn-medium ml-10"
						data-dismiss="modal">确认</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	<!-- 模态框（Modal） 结束 -->
  
	<script type="text/javascript">
		var pager;
		var userId=${userId};
		(function() {
			seajs.use('app/jsp/billing/marginSetting', function(MarginSettingPager) {
				pager = new MarginSettingPager({
					element : document.body
				});
				pager.render();
			});
		})();
	</script>
</body>
</html>