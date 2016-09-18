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
<link rel="stylesheet" type="text/css" href="${uedroot}/css/modular/modular.css"/>
</head>
<body>

  		  <div class="row" ><!--外围框架-->
            <div class="col-lg-12"><!--删格化-->
                <div class="row"><!--内侧框架-->
                    <div class="col-lg-12"><!--删格化-->
                         <div class="main-box clearfix"><!--白色背景-->   
                         	<!--查询条件-->
	                    		 <div class="form-label">
						            <ul>
						                <li  class="col-md-6" style="border-bottom:1px  solid #e7e7e7;padding-bottom: 10px;width: 98%;margin-left: 30px">
						                    <p class="word" style="font-size: 20px">资质审核</p>
						                </li>  
						            </ul>
					         </div>
					   	<!--查询结束-->   
					 	<!--table表格-->
					 	 	<div class="form-label pl-40" style="height: 100%;">
					 	 			<ul style="background:#e7e7e7 ">
						                <li class="col-md-6" style="width: 25%">
						                    <p class="word" style="font-size: 20px;">用户名:</p>
						                    <p>aaa</p>
						                </li>
						                <li  class="col-md-6" style="width: 25%">
						                    <p class="word" style="font-size: 20px">企业名称:</p>
						                    <p>bbb</p>
						                </li> 
						                <li class="col-md-6" style="width: 25%">
						                    <p class="word" style="font-size: 20px;">提交时间:</p>
						                    <p>aaa</p>
						                </li>
						                <li  class="col-md-6" style="width: 25%">
						                    <p class="word" style="font-size: 20px">提交类型:</p>
						                    <p>bbb</p>
						                </li>   
						            </ul>
						            
					 	 		<ul>
									<li>
									  <label style="display: inline-block;font-size: 18px">企业介绍信息</label><span style="display: inline-block;border-bottom: 1px solid #000;width: 900px;margin-left: 10px;margin-bottom: 5px" ></span>
									</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>行业:</p>
									</li>
									<li>制造者</li>
								</ul>
						    	<ul>
									<li>
									  <p class="word"><b class="red">*</b>官网:</p>
									</li>
									<li>www.home.com</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>公司人数:</p>
									</li>
									<li>1-50人</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>公司性质:</p>
									</li>
									<li>民营企业</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>通讯地址:</p>
									</li>
									<li>北京市 北京  朝阳区  大悦城9楼905</li>
								</ul>															
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>年营业额:</p>
									</li>
									<li>300万元</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>占地面积:</p>
									</li>
									<li>2200  平方米</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>电话:</p>
									</li>
									<li>010-86754632</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>传真:</p>
									</li>
									<li>010-86754639</li>
								</ul>	
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>邮箱:</p>
									</li>
									<li>010-86754639</li>
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
									<li>北京华夏科技贸易有限公司</li>
								</ul>	
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>企业注册地址:</p>
									</li>
									<li>北京市海淀区中关村北路21号昆泰大厦A座302</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>营业执照号:</p>
									</li>
									<li>23233489898000</li>
								</ul>	
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>营业执照副本:</p>
									</li>
									<li> <p class="img"><img src="../images/fom-t.png"></p></li>
								</ul> 	
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>成立日期:</p>
									</li>
									<li>2011年10月7日</li>
								</ul>	
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>注册资本:</p>
									</li>
									<li>40万元</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>经营范围:</p>
									</li>
									<li>五金交电，日用百货、针纺织品、洗涤用品、化妆品
								    </li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>法人姓名:</p>
									</li>
									<li>孙大伟</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>法人身份证号:</p>
									</li>
									<li>13331177069787878</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>身份证复印件:</p>
									</li>
									<li> 
									  <p class="img"><img src="../images/fom-t.png"></p>
									</li>
									<li> 
									  <p class="img"><img src="../images/fom-t.png"></p>
									</li>
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
									<li>1x5656767343X</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>纳税人类型:</p>
									</li>
									<li>一般纳税人</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>纳税类型税码:</p>
									</li>
									<li>6%</li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>税务登记证:</p>
									</li>
									<li> <p class="img"><img src="../images/fom-t.png"></p></li>
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
									<li> <p class="word" style="font-size: 16px">1x5656767343X</p></li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>代码证电子版:</p>
									</li>
									<li> <p class="img"><img src="../images/fom-t.png"></p></li>
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
									<li> <p class="word" style="font-size: 16px">中国银行</p></li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>公司银行账户:</p>
									</li>
									<li> <p class="word" style="font-size: 16px">62213545478788778</p></li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>银行开户许可证:</p>
									</li>
									<li> <p class="img"><img src="../images/fom-t.png"></p></li>
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
									<li> <p class="word" style="font-size: 16px">家纺纺织</p></li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>品牌名称(中文):</p>
									</li>
									<li> <p class="word" style="font-size: 16px">温馨家纺</p></li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>品牌名称(英文):</p>
									</li>
									<li> <p class="word" style="font-size: 16px">Sweet texttile</p></li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>商标注册证:</p>
									</li>
									<li> <p class="img"><img src="../images/fom-t.png"></p></li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>行业资质证明:</p>
									</li>
									<li> <p class="img"><img src="../images/fom-t.png"></p></li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>商品质检/检验报告:</p>
									</li>
									<li> <p class="img"><img src="../images/fom-t.png"></p></li>
								</ul>
								<ul>
									<li>
									  <p class="word"><b class="red">*</b>卫生/生产许可证:</p>
									</li>
									<li> <p class="img"><img src="../images/fom-t.png"></p></li>
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
			seajs.use('app/jsp/qualification/checkedDetail', function (CheckedDetailPager) {
				pager = new CheckedDetailPager({element: document.body});
				pager.render();
			});
		})();
</script>
</body>
</html>
