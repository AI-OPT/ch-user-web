<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="uedroot" value="${pageContext.request.contextPath}/template/default"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>合同管理页面</title>
<%@include file="/inc/inc.jsp" %>
<script src="${uedroot}/scripts/modular/frame.js"></script>  
<script src="${_base}/resources/spm_modules/app/jsp/qualification/jquery.lightbox-0.5.min.js"></script>  
<link rel="stylesheet" type="text/css" href="${uedroot}/css/lightbox/jquery.lightbox-0.5.css"/>
<link rel="stylesheet" type="text/css" href="${uedroot}/css/modular/modular.css"/>
<script type="text/javascript">
	$(function() {
		$(".popup").lightBox();
    });
</script>
</head>
<body>
 		  <div class="row" ><!--外围框架-->
            <div class="col-lg-12"><!--删格化-->
                <div class="row"><!--内侧框架-->
                    <div class="col-lg-12"><!--删格化-->
                         <div class="main-box clearfix"><!--白色背景-->   
                         	<!--标题-->
							<header class="main-box-header clearfix">
								<h4 class="pull-left">资质审核</h4>
							</header>
							<!--标题结束-->
						<div class="form-label">
							<div class="form-label  bd-bottom">
					           	<ul>
					                <li  class="col-md-6">
					                    <p class="word">用户名:</p>
					                    <p>${userName }</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">公司名称:</p>
					                    <p>${shopName }</p>
					                </li>  
					            </ul>  
					               	<ul>
					                <li  class="col-md-6">
					                    <p class="word">提交时间:</p>
					                    <p>${auditTime }</p>
					                </li>
					                <li  class="col-md-6">
					                    <p class="word">提交类型:</p>
					                    <p>无</p>
					                </li>  
					            </ul>  
					            </div>
					            </div>
							<div class="form-label">
							<ul>
								<li><p>企业介绍信息</p>
								</li>
							</ul>
							</div>
							<div class="form-label">
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>行业:</p>
									</li>
									<li>${industryType }</li>
								</ul>
						    	<ul>
									<li>
									  <p class="word"><b class="red">*</b>官网:</p>
									</li>
									<li>${officialWebsite }</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>公司人数:</p>
									</li>
									<li>${companiesNumber }</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>公司性质:</p>
									</li>
									<li>${companyNature }</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>通讯地址:</p>
									</li>
									<li>${companyNature }</li>
								</ul>															
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>年营业额:</p>
									</li>
									<li>${annualTurnover }</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>占地面积:</p>
									</li>
									<li>${areaCover }</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>电话:</p>
									</li>
									<li>${phone }</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>传真:</p>
									</li>
									<li>${fax }</li>
								</ul>	
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>邮箱:</p>
									</li>
									<li>${email }</li>
								</ul>		
								<ul>
									<li>
									  <label style="display: inline-block;font-size: 18px">企业执照</label><span style="display: inline-block;border-bottom: 1px solid #000;width: 900px;margin-left: 10px;margin-bottom: 5px" ></span>
									</li>
								</ul>	 
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>企业名称:</p>
									</li>
									<li>${shopName }</li>
								</ul>	
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>企业注册地址:</p>
									</li>
									<li>${businessAddress }</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>营业执照号:</p>
									</li>
									<li>${businessLicenseRegistrationNumber }</li>
								</ul>	
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>营业执照副本:</p>
									</li>
									<li><a class="popup" title="点击看缩略图" href="http://img.blog.csdn.net/20160920155755921"><img src="http://img.blog.csdn.net/20160920155755921" width="80px" height="80px"></a></li>
								</ul> 	
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>成立日期:</p>
									</li>
									<li>${establishDate }</li>
								</ul>	
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>注册资本:</p>
									</li>
									<li>${registerCapital }</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>经营范围:</p>
									</li>
									<li>${businessScope }
								    </li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>法人姓名:</p>
									</li>
									<li>${legalRepresentative }</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>法人身份证号:</p>
									</li>
									<li>${idNumber }</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>身份证复印件:</p>
									</li>
									<li><a class="popup" title="点击看缩略图" href="http://img.blog.csdn.net/20160920155755921"><img src="http://img.blog.csdn.net/20160920155755921" width="80px" height="80px"></a></li>
									<li><a class="popup" title="点击看缩略图" href="http://img.blog.csdn.net/20160920155755921"><img src="http://img.blog.csdn.net/20160920155755921" width="80px" height="80px"></a></li>
								</ul> 
								<ul>
									<li>
									  <label style="display: inline-block;font-size: 18px">税务登记证</label><span style="display: inline-block;border-bottom: 1px solid #000;width: 900px;margin-left: 10px;margin-bottom: 5px" ></span>
									</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>纳税人识别号:</p>
									</li>
									<li>${taxpayerNumber }</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>纳税人类型:</p>
									</li>
									<li>${taxpayerType }</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>纳税类型税码:</p>
									</li>
									<li>${taxCode }</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>税务登记证:</p>
									</li>
									<li><a class="popup" title="点击看缩略图" href="http://img.blog.csdn.net/20160920155755921"><img src="http://img.blog.csdn.net/20160920155755921" width="80px" height="80px"></a></li>
								</ul>
								<ul>
									<li>
									  <label style="display: inline-block;font-size: 18px">组织机构代码证</label><span style="display: inline-block;border-bottom: 1px solid #000;width: 900px;margin-left: 10px;margin-bottom: 5px" ></span>
									</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>组织机构代码证:</p>
									</li>
									<li> <p class="word" style="font-size: 16px">${organizationCode }</p></li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>代码证电子版:</p>
									</li>
									<li><a class="popup" title="点击看缩略图" href="http://img.blog.csdn.net/20160920155755921"><img src="http://img.blog.csdn.net/20160920155755921" width="80px" height="80px"></a></li>
								</ul>
								<ul>
									<li>
									  <label style="display: inline-block;font-size: 18px">银行开户许可证</label><span style="display: inline-block;border-bottom: 1px solid #000;width: 900px;margin-left: 10px;margin-bottom: 5px" ></span>
									</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>开户银行名称:</p>
									</li>
									<li> <p class="word" style="font-size: 16px">${bankName }</p></li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>公司银行账户:</p>
									</li>
									<li> <p class="word" style="font-size: 16px">${bankAccount }</p></li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>银行开户许可证:</p>
									</li>
									<li><a class="popup" title="点击看缩略图" href="http://img.blog.csdn.net/20160920155755921"><img src="http://img.blog.csdn.net/20160920155755921" width="80px" height="80px"></a></li>
								</ul>
								<ul>
									<li>
									  <label style="display: inline-block;font-size: 18px">供应商品信息</label><span style="display: inline-block;border-bottom: 1px solid #000;width: 900px;margin-left: 10px;margin-bottom: 5px" ></span>
									</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>供应商品类型:</p>
									</li>
									<li> <p class="word" style="font-size: 16px">${brandNameType }</p></li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>品牌名称(中文):</p>
									</li>
									<li> <p class="word" style="font-size: 16px">${brandNameCh }</p></li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>品牌名称(英文):</p>
									</li>
									<li> <p class="word" style="font-size: 16px">${brandNameEn }</p></li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>商标注册证:</p>
									</li>
									<li><a class="popup" title="点击看缩略图" href="http://img.blog.csdn.net/20160920155755921"><img src="http://img.blog.csdn.net/20160920155755921" width="80px" height="80px"></a></li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>行业资质证明:</p>
									</li>
									<li><a class="popup" title="点击看缩略图" href="http://img.blog.csdn.net/20160920155755921"><img src="http://img.blog.csdn.net/20160920155755921" width="80px" height="80px"></a></li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>商品质检/检验报告:</p>
									</li>
									<li><a class="popup" title="点击看缩略图" href="http://img.blog.csdn.net/20160920155755921"><img src="http://img.blog.csdn.net/20160920155755921" width="80px" height="80px"></a></li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>卫生/生产许可证:</p>
									</li>
									<li><a class="popup" title="点击看缩略图" href="http://img.blog.csdn.net/20160920155755921"><img src="http://img.blog.csdn.net/20160920155755921" width="80px" height="80px"></a></li>
								</ul>
								<ul>
									<li>
									  <input type="button" class="biu-btn  btn-primary btn-blue btn-medium ml-10 btn-green" value="通过" onclick="pager._passAudit('${userId}');" style="margin-left: 60%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</li>
									
									<li> 
									   <input type="button" class="biu-btn  btn-primary btn-blue btn-medium ml-10 btn-red" value="拒绝" onclick="pager._rejectAudit('${userId}');">
									</li>
								</ul>
                             </div>
                        </div>
                    </div>
                </div>
            
            </div>
    </div>
  <script type="text/javascript">
var pager;
(function () {
	seajs.use('app/jsp/qualification/auditeQualification', function (AuditeQualificationPager) {
		pager = new AuditeQualificationPager({element: document.body});
		pager.render();
	});
})();
</script> 
</body>
</html>
