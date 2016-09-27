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
<title>合同管理页面</title>
<%@include file="/inc/inc.jsp"%>
<script src="${uedroot}/scripts/modular/frame.js"></script>
<script
	src="${_base}/resources/spm_modules/app/jsp/qualification/jquery.lightbox-0.5.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${uedroot}/css/lightbox/jquery.lightbox-0.5.css" />
<link rel="stylesheet" type="text/css"
	href="${uedroot}/css/modular/modular.css" />
<script type="text/javascript">
	$(function() {
		$(".popup").lightBox();
	});
</script>
</head>
<body>
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
							<header class="main-box-header clearfix">
								<h4 class="pull-left">入驻商户资质审核</h4>
							</header>
							<!--标题结束-->
						<div class="form-label">
							<div class="form-label  bd-bottom">
								<ul>
									<li class="col-md-3">
										<p class="word">用户名:</p>
										<p>${userName }</p>
									</li>
									<li class="col-md-3">
										<p class="word">公司名称:</p>
										<p>${shopName }</p>
									</li>
									<li class="col-md-3">
										<p class="word">提交时间:</p>
										<p>${auditTime }</p>
									</li>
								</ul>
							</div>
						</div>
						<div class="form-label">
							<div class="form-label  bd-bottom">
								<ul>
									<li class="col-md-3"><p>企业介绍信息</p></li>
								</ul>
							</div>
							<ul>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>行业:
									</p>
									<p>${industryType }</p>
								</li>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>官网:
									</p>
									<p>${officialWebsite }</p>
								</li>
							</ul>
							<ul>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>公司人数:
									</p>
									<p>${companiesNumber }</p>
								</li>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>公司性质:
									</p>
									<p>${companyNature }</p>
								</li>
							</ul>
							<ul>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>通讯地址:
									</p>
									<p>${location }</p>
								</li>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>年营业额:
									</p>
									<p>${annualTurnover }</p>
								</li>
							</ul>
							<ul>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>占地面积:
									</p>
									<p>${areaCover }</p>
								</li>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>电话:
									</p>
									<p>${phone }</p>
								</li>
							</ul>
							<ul>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>传真:
									</p>
									<p>${fax }</p>
								</li>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>邮箱:
									</p>
									<p>${email }</p>
								</li>
							</ul>
							<div class="form-label  bd-bottom">
								<ul>
									<li class="col-md-3">企业执照</li>
								</ul>
							</div>
							<ul>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>企业名称:
									</p>
									<p>${shopName }</p>
								</li>
								<li class="col-md-6">
									<p class="word">
										<b class="red">*</b>企业注册地址:
									</p>
									<p>${businessAddress }</p>
								</li>
							</ul>
							<ul>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>营业执照号:
									</p>
									<p>${businessLicenseRegistrationNumber }</p>
								</li>
								</ul>
								<ul>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>营业执照副本:
									</p>
									<p>
										<a class="popup" title="点击看缩略图"
											href="http://img.blog.csdn.net/20160920155755921"><img
											src="http://img.blog.csdn.net/20160920155755921" width="80px"
											height="80px"></a>
									</p>
								</li>
							</ul>
							<ul>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>成立日期:
									</p>
									<p>${establishDate }</p>
								</li>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>注册资本:
									</p>
									<p>${registerCapital }</p>
								</li>
							</ul>
							<ul>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>经营范围:
									</p>
									<p>${businessScope }</p>
								</li>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>法人姓名:
									</p>
									<p>${legalRepresentative }</p>
								</li>
							</ul>
							<ul>
								<li class="col-md-6">
									<p class="word">
										<b class="red">*</b>法人身份证号:
									</p>
									<p>${idNumber }</p>
								</li>
							</ul>
							<ul>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>身份证复印件:
									</p>
									<p>
										<a class="popup" title="点击看缩略图"
											href="http://img.blog.csdn.net/20160920155755921"><img
											src="http://img.blog.csdn.net/20160920155755921" width="80px"
											height="80px"></a>
									</p>
								</li>
								<li class="col-md-3">
									<p>
										<a class="popup" title="点击看缩略图"
											href="http://img.blog.csdn.net/20160920155755921"><img
											src="http://img.blog.csdn.net/20160920155755921" width="80px"
											height="80px"></a>
									</p>
								</li>
							</ul>
							<div class="form-label  bd-bottom">
								<ul>
									<li class="col-md-3">税务登记证</li>
								</ul>
							</div>
							<ul>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>纳税人识别号:
									</p>
									<p>${taxpayerNumber }</p>
								</li>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>纳税人类型:
									</p>
									<p>${taxpayerType }</p>
								</li>
							</ul>
							<ul>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>纳税类型税码:
									</p>
									<p>${taxCode }</p>
								</li>
								</ul>
								<ul>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>税务登记证:
									</p>
									<p>
										<a class="popup" title="点击看缩略图"
											href="http://img.blog.csdn.net/20160920155755921"><img
											src="http://img.blog.csdn.net/20160920155755921" width="80px"
											height="80px"></a>
									</p>
								</li>
							</ul>
							<div class="form-label  bd-bottom">
								<ul>
									<li class="col-md-3">组织机构代码证</li>
								</ul>
							</div>
							<ul>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>组织机构代码证:
									</p>
									<p class="word">${organizationCode }</p>
								</li>
								</ul>
								<ul>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>代码证电子版:
									</p>
									<p>
										<a class="popup" title="点击看缩略图"
											href="http://img.blog.csdn.net/20160920155755921"><img
											src="http://img.blog.csdn.net/20160920155755921" width="80px"
											height="80px"></a>
									</p>
								</li>
							</ul>
							<div class="form-label  bd-bottom">
								<ul>
									<li class="col-md-3">银行开户许可证</li>
								</ul>
							</div>
							<ul>
								<li class="col-md-6">
									<p class="word">
										<b class="red">*</b>开户银行名称:
									</p>
									<p class="word">${bankName }</p>
								</li>
								</ul>
								<ul>
								<li class="col-md-6">
									<p class="word">
										<b class="red">*</b>公司银行账户:
									</p>
									<p class="word">${bankAccount }</p>
								</li>
							</ul>
							<ul>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>银行开户许可证:
									</p>
									<p>
										<a class="popup" title="点击看缩略图"
											href="http://img.blog.csdn.net/20160920155755921"><img
											src="http://img.blog.csdn.net/20160920155755921" width="80px"
											height="80px"></a>
									</p>
								</li>
							</ul>
							<div class="form-label  bd-bottom">
								<ul>
									<li class="col-md-3">供应商品信息</li>
								</ul>
							</div>
							<ul>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>供应商品类型:
									</p>
									<p>${brandNameType }</p>
								</li>
								</ul>
								<ul>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>品牌名称(中文):
									</p>
									<p>${brandNameCh }</p>
								</li>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>品牌名称(英文):
									</p>
									<p>${brandNameEn }</p>
								</li>
								</ul>
								<ul>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>商标注册证:
									</p>
									<p>
										<a class="popup" title="点击看缩略图"
											href="http://img.blog.csdn.net/20160920155755921"><img
											src="http://img.blog.csdn.net/20160920155755921" width="80px"
											height="80px"></a>
									</p>
								</li>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>行业资质证明:
									</p>
									<p>
										<a class="popup" title="点击看缩略图"
											href="http://img.blog.csdn.net/20160920155755921"><img
											src="http://img.blog.csdn.net/20160920155755921" width="80px"
											height="80px"></a>
									</p>
								</li>
								</ul>
								<ul>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>商品质检/检验报告:
									</p>
									<p>
										<a class="popup" title="点击看缩略图"
											href="http://img.blog.csdn.net/20160920155755921"><img
											src="http://img.blog.csdn.net/20160920155755921" width="80px"
											height="80px"></a>
									</p>
								</li>
								<li class="col-md-3">
									<p class="word">
										<b class="red">*</b>卫生/生产许可证:
									</p>
									<p>
										<a class="popup" title="点击看缩略图"
											href="http://img.blog.csdn.net/20160920155755921"><img
											src="http://img.blog.csdn.net/20160920155755921" width="80px"
											height="80px"></a>
									</p>
								</li>
							</ul>
							<div class="form-label  bd-bottom">
								<ul>
									<li class="col-md-3">店铺及经营信息</li>
								</ul>
							</div>
							<ul>
								<li class="col-md-3">
								<p class="word"><b class="red">*</b>期望店铺名称</p>
								<p>${wantShopName }</p>
								</li>
								<li class="col-md-3">
								<p class="word"><b class="red">*</b>可售商品数量</p>
								<p >${goodsNum }</p>
								</li>
								</ul>
								<ul>
								<li class="col-md-3">
								<p class="word"><b class="red">*</b>经营类型</p>
								<p>${busiType }</p>
								</li>
								<li class="col-md-3">
								<p class="word"><b class="red">*</b>有无电商经验</p>
								<p>${hasExperi }</p>
								</li>
								</ul>
								<ul>
								<li class="col-md-3">
								<p class="word"><b class="red">*</b>现有电商平台</p>
								<p>${ecommOwner }</p>
								</li>
							</ul>
							<ul>
								<li>
								<p class="word">店铺介绍</p>
								<p>${shopDesc }</p>
								</li>
							</ul>
							<ul>
								<li class="col-md-3">
									<p>
										<input type="button"
											class="biu-btn  btn-primary btn-blue btn-medium ml-10" value="通过"
											onclick="pager._passAudit('${userId}','${_base}/qualification/toNoCheckedShopPager');" style="margin-left: 60%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</p>
								</li>
								<li><p>
										<input type="button"
											class="biu-btn  btn-primary btn-blue btn-medium ml-10" value="拒绝"
											onclick="pager._rejectAudit('${userId}','${_base }/qualification/toNoCheckedShopPager');">
									</p></li>
							</ul>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
	<script type="text/javascript">
		var pager;
		(function() {
			seajs.use('app/jsp/qualification/auditeQualification', function(
					AuditeQualificationPager) {
				pager = new AuditeQualificationPager({
					element : document.body
				});
				pager.render();
			});
		})();
	</script>
</body>
</html>
