define('app/jsp/user/qualification/agent-supplier-enterprise', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("artDialog/src/dialog"),
    Uploader = require('arale-upload/1.2.0/index'),
    AjaxController=require('opt-ajax/1.0.0/index'),
    Calendar = require('arale-calendar/1.1.2/index');
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("treegrid/js/jquery.treegrid.min");
    require("treegrid/js/jquery.cookie");
    require("app/jsp/user/qualification/ajaxfileupload");
    
    require("app/util/jsviews-ext");
    require("opt-paging/aiopt.pagination");
    require("twbs-pagination/jquery.twbsPagination.min");
    require("app/jsp/user/qualification/baseinfo");
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    
    //定义页面组件类
    var EnterprisePager = Widget.extend({
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5,
    		USER_LEFT_MNU_ID: "left_mnu_qualification_identify"
    	}, 
    	//事件代理
    	events: {
    		//key的格式: 事件+空格+对象选择器;value:事件方法
    		//注册日期
    		"blur [id='establishTime']":"_checkEstablishTime",
    		//注册资本
    		"focus [id='capital']":"_showCapitalTip",
    		"blur [id='capital']":"_checkCapitalValue",
    		//经营范围
    		"focus [id='scope']" :"_showScopeTip",
    		"blur [id='scope']" :"_checkScopeValue",
    		//法人姓名
    		"focus [id='corporationName']" :"_showCorporationNameTip",
    		"blur [id='corporationName']" :"_checkCorporationNameValue",
    		//法人身份证号码
    		"focus [id='idNumber']":"_showIdNumberTip",
    		"blur [id='idNumber']":"_checkIdNumber",
    		//纳税人识别号
    		"focus [id='identifyNumber']":"_showIdentifyNumberTip",
    		"blur [id='identifyNumber']":"_checkIdentifyNumberValue",
    		//纳税人类型
    		"change [id='taxpayerType']":"_checkTaxpayerTypeValue",
    		//纳税人代码
    		"change [id='taxCode']":"_checkTaxCodeValue",
    		//组织机构代码
    		"focus [id='organizationCode']":"_showOrganizationCodeTip",
    		"blur [id='organizationCode']":"_checkOrganizationCodeValue",
    		//开户银行名称
    		"focus [id='bankName']":"_showBankNameTip",
    		"blur [id='bankName']":"_checkBankNameValue",
    		//银行支行名称
    		"focus [id='subbranchName']":"_showSubbranchNameTip",
    		"blur [id='subbranchName']":"_checkSubbranchNameValue",
    		//银行账户
    		"focus [id='bankAccount']":"_showBankAccountTip",
    		"blur [id='bankAccount']":"_checkBankAccountValue",
    		//供应商品类型
    		"change [id='supplyGoods']":"_checkSupplyGoodsValue",
    		//品牌名称中文
    		"focus [id='brandNameC']":"_showBrandNameCTip",
    		"blur [id='brandNameC']":"_checkBrandNameCValue",
    		//品牌名称英文
    		"focus [id='brandNameE']":"_showBrandNameETip",
    		"blur [id='brandNameE']":"_checkBrandNameValue",
    		
    		"click [id='submit']":"_submit",
    		
        },
        init: function(){
        	
        },
    	//重写父类
    	setup: function () {
    		$("#provinceCode").val("0");
			$("#cityCode").val("0");
			$("#countyCode").val("0");
    		EnterprisePager.superclass.setup.call(this);
    		activeUserLeftMenu(EnterprisePager.USER_LEFT_MNU_ID);
    		this._bindCalendar();
    	},
    	_showCapitalTip:function(){
    		$("#capitalErrMsg").show();
			$("#capitalText").show();
			$("#capitalText").text('1-12位字符，可用数字及"."');
    		$('#capitalImage').attr('src',_base+'/resources/slpmall/images/icon-d.png');
    	},
    	_showScopeTip:function(){
    		$("#scopeErrMsg").show();
			$("#scopeText").show();
			$("#scopeText").text('4-300个字符');
    		$('#scopeImage').attr('src',_base+'/resources/slpmall/images/icon-d.png');
    	},
    	_showCorporationNameTip:function(){
    		$("#corporationNameErrMsg").show();
			$("#corporationNameText").show();
			$("#corporationNameText").text('4-24个字符，可用汉字或英语字母');
    		$('#corporationNameImage').attr('src',_base+'/resources/slpmall/images/icon-d.png');
    	},
    	_showIdNumberTip:function(){
			$("#idNumberErrMsg").show();
			$("#idNumberText").show();
			$("#idNumberImage").show();
			$("#idNumberText").text('18位数字');
    		$('#idNumberImage').attr('src',_base+'/resources/slpmall/images/icon-d.png');
		},
		_showIdentifyNumberTip:function(){
			$("#identifyNumberErrMsg").show();
			$("#identifyNumberText").show();
			$("#identifyNumberImage").show();
			$("#identifyNumberText").text('4-20位字符，可用数字及字母');
    		$('#identifyNumberImage').attr('src',_base+'/resources/slpmall/images/icon-d.png');
		},
		_showBankNameTip:function(){
			$("#bankNameErrMsg").show();
			$("#bankNameText").show();
			$("#bankNameImage").show();
			$("#bankNameText").text('4-20位字符');
    		$('#bankNameImage').attr('src',_base+'/resources/slpmall/images/icon-d.png');
		},
		_showSubbranchNameTip:function(){
			$("#subbranchNameErrMsg").show();
			$("#subbranchNameText").show();
			$("#subbranchNameImage").show();
			$("#subbranchNameText").text('4-20位字符');
    		$('#subbranchNameImage').attr('src',_base+'/resources/slpmall/images/icon-d.png');
		},
		_showBankAccountTip:function(){
			$("#bankAccountErrMsg").show();
			$("#bankAccountText").show();
			$("#bankAccountImage").show();
			$("#bankAccountText").text('4-20位字符');
    		$('#bankAccountImage').attr('src',_base+'/resources/slpmall/images/icon-d.png');
		},
		_showOrganizationCodeTip:function(){
			$("#organizationCodeErrMsg").show();
			$("#organizationCodeText").show();
			$("#organizationCodeImage").show();
			$("#organizationCodeText").text('4-50个字符，可用数字、字母、“-”');
    		$('#organizationCodeImage').attr('src',_base+'/resources/slpmall/images/icon-d.png');
		},
		_showBrandNameCTip:function(){
			$("#brandNameCErrMsg").show();
			$("#brandNameCText").show();
			$("#brandNameCImage").show();
			$("#brandNameCText").text('2-20个汉字');
    		$('#brandNameCImage').attr('src',_base+'/resources/slpmall/images/icon-d.png');
		},
		_showBrandNameETip:function(){
			$("#brandNameEErrMsg").show();
			$("#brandNameEText").show();
			$("#brandNameEImage").show();
			$("#brandNameEText").text('2-40个字符');
    		$('#brandNameEImage').attr('src',_base+'/resources/slpmall/images/icon-d.png');
		},
    	_bindCalendar:function(){
    		var beginCalendar = new Calendar({trigger: '#establishTimeId',output:"#establishTime"});
		},
		_changeEstablishTimeDate:function(){
			var sysDataStr = this._getSysDate();
			var establishTime = $("#establishTime").val();
			var beginCalendar = new Calendar({trigger: '#establishTimeId',output:"#establishTime"});
			if(establishTime == null || establishTime == "" || establishTime == undefined){
				beginCalendar.range([null,null]);
			}else{
				beginCalendar.range([null,endDate]);
			}
			
		},
		_getSysDate:function(){
			var sysDate = new Date();
  			var year = sysDate.getFullYear();    //获取完整的年份(4位,1970-????)
  			var month = sysDate.getMonth()+1;       //获取当前月份(0-11,0代表1月)
  			var day = sysDate.getDate();
  			if(month<10){
  				month = "0"+month;
  			}
  			if(day<10){
  				day = "0"+day;
  			}
  			return year+"-"+month+"-"+day;
		},
		_checkCapitalValue:function(){
			var capital = $("#capital").val();
			if(capital==""||capital==null){
				$("#capitalErrMsg").show();
				$("#capitalText").show();
				$("#capitalText").text('1-12位字符，可用数字及"."');
	    		$('#capitalImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
	    		$("#capitalFlag").val("0");
			}else{
				var reg = /^[\d+(\.\d+)|d+]{1,12}$/;
				if(capital.match(reg)){
					$('#capitalErrMsg').show();
    				$('#capitalText').hide();
    				$('#capitalImage').attr('src',_base+'/resources/slpmall/images/icon-b.png');
    				$("#capitalFlag").val("1");
				}else{
					$('#capitalErrMsg').show();
    				$("#capitalImage").show();
    				$("#capitalText").show();
        			$('#capitalText').text('1-12位字符，可用数字及"."');
        			$('#capitalImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
        			$("#capitalFlag").val("0");
				}
			}
		},
		_checkScopeValue:function(){
			var scope = $("#scope").val();
			if(scope==null||scope==""){
				$("#scopeErrMsg").show();
				$("#scopeText").show();
				$("#scopeText").text('4-300个字符');
	    		$('#scopeImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
	    		$("#scopeFlag").val("0");
			}else{
				if(scope.length<4||scope.length>300){
					$("#scopeErrMsg").show();
					$("#scopeText").show();
					$("#scopeText").text('4-300个字符');
		    		$('#scopeImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
		    		$("#scopeFlag").val("0");
				}else{
					$('#scopeErrMsg').show();
    				$('#scopeText').hide();
    				$('#scopeImage').attr('src',_base+'/resources/slpmall/images/icon-b.png');
    				$("#scopeFlag").val("1");
				}
			}
		},
		_checkCorporationNameValue:function(){
			var corporationName = $("#corporationName").val();
			if(corporationName==null||corporationName==""){
				$("#corporationNameErrMsg").show();
				$("#corporationNameText").show();
				$("#corporationNameText").text('4-24个字符，可用汉字或英语字母');
	    		$('#corporationNameImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
	    		$("#corporationNameFlag").val("0");
			}else{
				var reg = /^[\u4e00-\u9fa5a-zA-Z]{4,24}$/;
    			if(corporationName.match(reg)){
    				$('#corporationNameErrMsg').show();
    				$('#corporationNameText').hide();
    				$('#corporationNameImage').attr('src',_base+'/resources/slpmall/images/icon-b.png');
    				$("#corporationNameFlag").val("1");
    			}else{
    				$('#corporationNameErrMsg').show();
    				$("#corporationNameImage").show();
        			$('#corporationNameText').text("4-24个字符，可用汉字或英语字母");
        			$('#corporationNameImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
    			}
	    		
			}
		},
		_checkIdNumber:function(){
			var idNumber = $("#idNumber").val();
			if(idNumber==null|idNumber==""){
				$('#idNumberErrMsg').show();
				$("#idNumberImage").show();
    			$('#idNumberText').text("请填写正确的身份证号");
    			$('#idNumberImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
    			$("#idNumberFlag").val("0");
			}else{
				var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
				if(!reg.test(idNumber)){
					$('#idNumberErrMsg').show();
    				$("#idNumberImage").show();
    				$("#realNameText").show();
        			$('#idNumberText').text("18位数字");
        			$('#idNumberImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
        			$("#idNumberFlag").val("0");
				}else{
					$('#idNumberErrMsg').show();
    				$('#idNumberText').hide();
    				$('#idNumberImage').attr('src',_base+'/resources/slpmall/images/icon-b.png');
    				$("#idNumberFlag").val("1");
				}
			}
		},
		_checkIdentifyNumberValue:function(){
			var identifyNumber = $("#identifyNumber").val();
			if(identifyNumber==null|identifyNumber==""){
				$('#identifyNumberErrMsg').show();
				$("#identifyNumberImage").show();
    			$('#identifyNumberText').text("4-20位字符，可用数字及字母");
    			$('#identifyNumberImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
    			$("#identifyNumberFlag").val("0");
			}else{
				var reg =/^[a-zA-Z0-9]{4,20}$/;
				if(!identifyNumber.match(reg)){
					$('#identifyNumberErrMsg').show();
    				$("#identifyNumberImage").show();
    				$("#identifyNumberText").show();
        			$('#identifyNumberText').text("4-20位字符，可用数字及字母");
        			$('#identifyNumberImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
        			$("#identifyNumberFlag").val("0");
				}else{
					$('#identifyNumberErrMsg').show();
    				$('#identifyNumberText').hide();
    				$('#identifyNumberImage').attr('src',_base+'/resources/slpmall/images/icon-b.png');
    				$("#identifyNumberFlag").val("1");
				}
			}
		},
		_checkTaxpayerTypeValue:function(){
			var taxpayerType = $("#taxpayerType").val();
			if(taxpayerType=="0"){
				$("#taxpayerTypeErrMsg").show();
				$("#taxpayerTypeFlag").val("0");
			}else{
				$("#taxpayerTypeErrMsg").hide();
				$("#taxpayerTypeFlag").val("1");
			}
		},
		_checkTaxpayerTypeValue:function(){
			var taxpayerType = $("#taxpayerType").val();
			if(taxpayerType=="0"){
				$("#taxpayerTypeErrMsg").show();
				$("#taxpayerTypeFlag").val("0");
			}else{
				$("#taxpayerTypeErrMsg").hide();
				$("#taxpayerTypeFlag").val("1");
			}
		},
		_checkTaxCodeValue:function(){
			var taxCode = $("#taxCode").val();
			if(taxCode=="0"){
				$("#taxCodeErMsg").show();
				$("#taxCodeFlag").val("0");
			}else{
				$("#taxCodeErMsg").hide();
				$("#taxCodeFlag").val("1");
			}
		},
		_checkBankNameValue:function(){
			var bankName = $("#bankName").val();
			if(bankName==null|bankName==""){
				$('#bankNameErrMsg').show();
				$("#bankNameImage").show();
    			$('#bankNameText').text("请输入开户银行名称");
    			$('#bankNameImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
    			$("#bankNameFlag").val("0");
			}else{
				if(bankName.length<4||bankName.length>20){
					$('#bankNameErrMsg').show();
    				$("#bankNameImage").show();
    				$("#bankNameText").show();
        			$('#bankNameText').text("4-20位字符");
        			$('#bankNameImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
        			$("#bankNameFlag").val("0");
				}else{
					$('#bankNameErrMsg').show();
    				$('#bankNameText').hide();
    				$('#bankNameImage').attr('src',_base+'/resources/slpmall/images/icon-b.png');
    				$("#bankNameFlag").val("1");
				}
			}
		},
		_checkSubbranchNameValue:function(){
			var subbranchName = $("#subbranchName").val();
			if(subbranchName==null||subbranchName==""){
				$('#subbranchNameErrMsg').show();
				$("#subbranchNameImage").show();
    			$('#subbranchNameText').text("请输入开户银行名称");
    			$('#subbranchNameImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
    			$("#subbranchNameFlag").val("0");
			}else{
				if(subbranchName.length<4||subbranchName.length>20){
					$('#subbranchNameErrMsg').show();
    				$("#subbranchNameImage").show();
    				$("#subbranchNameText").show();
        			$('#subbranchNameText').text("4-20位字符");
        			$('#subbranchNameImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
        			$("#subbranchNameFlag").val("0");
				}else{
					$('#subbranchNameErrMsg').show();
    				$('#subbranchNameText').hide();
    				$('#subbranchNameImage').attr('src',_base+'/resources/slpmall/images/icon-b.png');
    				$("#subbranchNameFlag").val("1");
				}
			}
		},
		_checkBankAccountValue:function(){
			var bankAccount = $("#bankAccount").val();
			if(bankAccount==null|bankAccount==""){
				$('#bankAccountErrMsg').show();
				$("#bankAccountImage").show();
    			$('#bankAccountText').text("请输入银行名称");
    			$('#bankAccountImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
    			$("#bankAccountFlag").val("0");
			}else{
				if(bankAccount.length<4||bankAccount.length>20){
					$('#bankAccountErrMsg').show();
    				$("#bankAccountImage").show();
    				$("#bankAccountText").show();
        			$('#bankAccountText').text("4-20位字符");
        			$('#bankAccountImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
        			$("#bankAccountFlag").val("0");
				}else{
					$('#bankAccountErrMsg').show();
    				$('#bankAccountText').hide();
    				$('#bankAccountImage').attr('src',_base+'/resources/slpmall/images/icon-b.png');
    				$("#bankAccountFlag").val("1");
				}
			}
		},
		_checkEstablishTime:function(){
			var establishTime = $("#establishTime").val();
			if(establishTime==null||establishTime==""){
				$('#establishTimeErrorMsg').show();
				$("#establishTimeImage").show();
				$("#establishTimeText").show();
    			$('#establishTimeText').text("请输入日期");
    			$('#establishTimeImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
    			$("#establishTimeFlag").val("0");
			}else{
				$('#establishTimeErrorMsg').show();
				$('#establishTimeText').hide();
				$('#establishTimeImage').attr('src',_base+'/resources/slpmall/images/icon-b.png');
				$("#establishTimeFlag").val("1");
			}
		},
		_checkOrganizationCodeValue:function(){
			var organizationCode = $("#organizationCode").val();
			if(organizationCode==null|organizationCode==""){
				$('#organizationCodeErrMsg').show();
				$("#organizationCodeImage").show();
    			$('#organizationCodeText').text("请输入组织机构代码");
    			$('#organizationCodeImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
    			$("#organizationCodeFlag").val("0");
			}else{
				var reg = /^[a-zA-Z0-9\-]{4,50}$/;
				if(organizationCode.match(reg)){
					$('#organizationCodeErrMsg').show();
    				$('#organizationCodeText').hide();
    				$('#organizationCodeImage').attr('src',_base+'/resources/slpmall/images/icon-b.png');
    				$("#organizationCodeFlag").val("1");
				}else{
					$('#organizationCodeErrMsg').show();
    				$("#organizationCodeImage").show();
    				$("#organizationCodeText").show();
        			$('#organizationCodeText').text("4-20位字符");
        			$('#organizationCodeImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
        			$("#organizationCodeFlag").val("0");
				}
				
			}
		
		},
		_checkSupplyGoodsValue:function(){
			var supplyGoods = $("#supplyGoods").val();
			if(supplyGoods=="0"||supplyGoods==null){
				$("#supplyGoodsFlag").val("0");
				$("#supplyGoodsErrMsg").show();
			}else{
				$("#supplyGoodsFlag").val("1");
				$("#supplyGoodsErrMsg").hide();
			}
		},
		_checkBrandNameCValue:function(){
			var brandNameC = $("#brandNameC").val();
			if(brandNameC!=""){
				var reg = /^[\u4e00-\u9fa5]{2,20}$/;
				if(brandNameC.match(reg)){
					$('#brandNameCErrMsg').show();
    				$('#brandNameCText').hide();
    				$('#brandNameCImage').attr('src',_base+'/resources/slpmall/images/icon-b.png');
				}else{
					$('#brandNameCErrMsg').show();
    				$("#brandNameCmage").show();
    				$("#brandNameCText").show();
        			$('#brandNameCText').text("4-20位字符");
        			$('#brandNameCImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
				}
			}else{
				$('#brandNameCErrMsg').hide();
				$('#brandNameCText').hide();
				$('#brandNameCImage').hide();
			}
		},
		_checkBrandNameValue:function(){
			var brandNameE = $("#brandNameE").val();
			if(brandNameE!=""){
				if(brandNameE.length>2&&brandNameE.length<40){
					$('#brandNameEErrMsg').show();
    				$('#brandNameEText').hide();
    				$('#brandNameEImage').attr('src',_base+'/resources/slpmall/images/icon-b.png');
				}else{
					$('#brandNameEErrMsg').show();
    				$("#brandNameEImage").show();
    				$("#brandNameEText").show();
        			$('#brandNameEText').text("4-20位字符");
        			$('#brandNameEImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
				}
			}else{
				$('#brandNameEErrMsg').hide();
				$('#brandNameEText').hide();
				$('#brandNameEImage').hide();
			}
		},
    });
    
    module.exports = EnterprisePager
});

function toSave(){
	 $.ajax({
		type:"post",
		url:_base+"/user/qualification/saveEnterprise",
		dataType: "json",
		data:$("#agentEnterprise").serialize(),
        success: function(data) {
        	if(data.responseHeader.resultCode=="000003"){
        	 	$("#newPhoneCodeErrMsg").show();
        		$('#newPhoneCodeErrMsgShow').text("短信验证码错误");
				$('#phoneCodeFlag').val("0");
				return false;
        	}else if(data.responseHeader.resultCode=="000004"){
        		$("#newPhoneCodeErrMsg").show();
        		$('#newPhoneCodeErrMsgShow').text("短信验证码已失效");
				$('#phoneCodeFlag').val("0");
				return false;
        	}else if(data.responseHeader.resultCode=="000007"){
        		$("#newPhoneCodeErrMsg").show();
        		$('#newPhoneCodeErrMsgShow').text("手机与发送短信手机不一致");
				$('#phoneCodeFlag').val("0");
				return false;
        	}else if(data.responseHeader.resultCode=="111111"){
        		alert("失败了");
        		return false;
        	}else if(data.responseHeader.resultCode=="000000"){
        		window.location.href=_base+"/user/qualification/toEnterprisePage";
        	}
            },
			error: function(error) {
				alert("error:"+ error);
			}
		});
}