<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="uedroot"
	value="${pageContext.request.contextPath}/template/default" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>操作失败</title>
<%@include file="/inc/inc.jsp"%>
</head>
<body>
	<div class="content-wrapper-iframe">
		<!--右侧灰色背景-->
		<!--框架标签结束-->
		<div class="row">
			<!--外围框架-->
			<div class="col-lg-12">
				<!--删格化-->
				<div class="row">
					<!--内侧框架-->
					<div class="col-lg-12">
						<!--删格化-->
						<div class="main-box clearfix">
							<!--白色背景-->
							<!--标题-->
							<!--标题结束-->
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		(function() {
			seajs.use('app/jsp/contract/dialog', function(DialogPager) {
				pager = new DialogPager({
					element : document.body
				});
				pager.render();
				pager._dialog('提示', '保存成功', 'success', '确定', _base+'/contract/contractShopPager');
			});
		})();
	</script>
</body>
</html>