define(
		'app/jsp/user/qualification/agent-personal',
		function(require, exports, module) {
			'use strict';
			var $ = require('jquery'), 
			Widget = require('arale-widget/1.2.0/widget'), 
			AjaxController = require('opt-ajax/1.0.0/index');
			require('app/jsp/user/qualification/birthday')
			// 实例化AJAX控制处理对象
			var ajaxController = new AjaxController();

			// 定义页面组件类
			var AgentPersonalPager = Widget.extend({
				// 属性，使用时由类的构造函数传入
				attrs : {},
				// 事件代理
				events : {
				// key的格式: 事件+空格+对象选择器;value:事件方法
				//真实姓名校验
				"blur [id='custName']":"_checkcustName",
	    		"focus [id='custName']":"_showcustNameTip",
	    		//身份证校验
	    		"focus [id='idNumber']":"_showIdNumberTip",
	    		"blur [id='idNumber']":"_checkIdNumber",
	    		//学历校验
	    		"change [id='custEducation']":"_checkcustEducation",
	    		"blur [id='countyCode']":"_checkContactAddress",
	    		//生日校验
	    		"blur [id='dd']":"_checkBithday",
	    		//收入校验
	    		"change [id='inCome']":"_checkInCome",
				},
				init : function() {
				},
				// 重写父类
				setup : function() {
					$("#provinceCode").val("0");
					$("#cityCode").val("0");
					$("#countyCode").val("0");
					AgentPersonalPager.superclass.setup.call(this);
					birth.init('yy_mm_dd');
				},
				_showcustNameTip:function(){
					$("#custNameErrMsg").show();
					$("#custNameText").show();
					$("#custNameText").text('4-24个字符，可用汉字或英语字母');
		    		$('#custNameImage').attr('src',_base+'/resources/slpmall/images/icon-d.png');
				},
				_showIdNumberTip:function(){
					$("#idNumberErrMsg").show();
					$("#idNumberText").show();
					$("#idNumberImage").show();
					$("#idNumberText").text('18位数字');
		    		$('#idNumberImage').attr('src',_base+'/resources/slpmall/images/icon-d.png');
				},
				_checkcustName:function(){
					var name = $("#custName").val();
					var reg = /^[\u4e00-\u9fa5a-zA-Z]{4,24}$/;
					if(name==null||name==""){
						$('#custNameErrMsg').show();
	    				$("#custNameImage").show();
	        			$('#custNameText').text("4-24个字符，可用汉字或英语字母");
	        			$('#custNameImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
	        			$("#custNameFlag").val("0");
					}else{
						if(name.match(reg)){
		    				$('#custNameErrMsg').show();
		    				$('#custNameText').hide();
		    				$('#custNameImage').attr('src',_base+'/resources/slpmall/images/icon-b.png');
		    				$("#custNameFlag").val("1");
		    			}else{
		    				$('#custNameErrMsg').show();
		    				$("#custNameImage").show();
		        			$('#custNameText').text("4-24个字符，可用汉字或英语字母");
		        			$('#custNameImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
		        			$("#custNameFlag").val("0");
		    			}
					}
				},
				_checkcustEducation:function(){
					var custEducationVal = $("#custEducation").val();
					if(custEducationVal=="0"){
						$("#custEducationErrMsg").show();
						$("#custEducationFlag").val("0");
					}else{
						$("#custEducationErrMsg").hide();
						$("#custEducationFlag").val("1");
					}
				},
				_checkContactAddress:function(){
					//校验联系地址
					var princeCode = $("#provinceCode").val();
					var cityCode = $("#cityCode").val();
					var countyCode = $("#countyCode").val();
					if(princeCode=="0"||princeCode==null||cityCode=="0"||cityCode==null||countyCode=="0"||countyCode==null){
						$("#registerAddrErrMsg").show();
						$("#provinceCodeFlag").val("0");
					}else{
						$("#registerAddrErrMsg").hide();
						$("#provinceCodeFlag").val("1");
					}
				},
				_checkInCome:function(){
					var income = $("#inCome").val();
					if(income=="0"){
						$("#idComeErrMsg").show();
						$("#inComeFlag").val("0");
					}else{
						$("#idComeErrMsg").hide();
						$("#inComeFlag").val("1");
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
		    				$("#custNameText").show();
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
				_checkBithday:function(){
					//校验生日
					var year = $("#yy_mm_dd").val();
					var mm = $("#mm").val();
					var dd = $("#dd").val();
					if(year=="0"||mm=="0"||dd=="0"){
						$("#bithdayErrMsg").show();
						$("#bithdayFlag").val("0");
					}else{
						$("#bithdayErrMsg").hide();
						$("#bithdayFlag").val("1");
					}
				}
		});
			module.exports = AgentPersonalPager
		});

