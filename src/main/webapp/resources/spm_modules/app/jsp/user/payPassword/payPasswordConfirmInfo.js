define('app/jsp/user/payPassword/payPasswordConfirmInfo', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("artDialog/src/dialog"),
    Uploader = require('arale-upload/1.2.0/index'),
    AjaxController=require('opt-ajax/1.0.0/index');
    
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("treegrid/js/jquery.treegrid.min");
    require("treegrid/js/jquery.cookie");
    
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    
    //定义页面组件类
    var ConfirmInfoPager = Widget.extend({
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5,
    		USER_LEFT_MNU_ID: "left_mnu_security_set"
    	},
    	//事件代理
    	events: {
    		//key的格式: 事件+空格+对象选择器;value:事件方法
    		"keyup [id='passwordInput']":"_pwStrength",
    		"focus [id='passwordInput']":"_passShow",
    		"blur [id='passwordInput']":"_checkPasswordValue",
    		"click [id='sendVerify']":"_sendPhoneVerifyCode",
    		"blur [id='confirmationPassword']":"_passwordConfirmation",
    		"click [id='passwordNext']" :"_next",
    		"click [id='submitBtn']":"_submit"
        },
        init: function(){
        	_getImageRandomCode();
        },
    	//重写父类
    	setup: function () {
    		ConfirmInfoPager.superclass.setup.call(this);
    		activeUserLeftMenu(ConfirmInfoPager.USER_LEFT_MNU_ID);
    		this._renderAccountInfo();
    	},
    
    	//加载账户数据
    	_renderAccountInfo: function(){
			var _this = this;
		},
		
		//控制显示内容
		_controlMsgText: function(id,msg){
			var doc = document.getElementById(id+"");
			doc.innerText=msg;
		},
		//控制显隐属性 1:隐藏 2：显示
		_controlMsgAttr: function(id,flag){
			var doc = document.getElementById(id+"");
			if(flag == 1){
				doc.setAttribute("style","display:none");
			}else if(flag == 2){
				doc.setAttribute("style","display");
			}
		},
		_passShow: function(){
			$("#strength_L").hide();
			$("#strength_M").hide();
			$("#strength_H").hide();
    		$("#errorPawMsg").show();
    		$("#passwordImage").show();
    		$("#showPawMsg").show();
    		$("#showPawMsg").text("6-20个字符，可用字母、数字及符号的组合");
    		$("#passwordImage").attr("src",_base+"/resources/slpmall/images/icon-d.png");
    		
    	},
		//校验密码
    	_validServicePaw:function(){
    		$("#errorShowPM").attr("style","display:none");
    		$("#errorPawMsg").attr("style","display:none");
    		var password = $('#passwordInput').val();
    		if(password==""){
    			$('#passwordImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
    			$('#showPawMsg').text("请输入密码");
    			$("#showPawMsg").show();
    			$("#errorPawMsg").show();
				return false; 
    		}else if(/[\x01-\xFF]*/.test(password)){
    				if(/^\S*$/.test(password)){
    					if(/^[\x21-\x7E]{6,20}$/.test(password)){
    						$("#errorPawMsg").show();
    						$("#showPawMsg").hide();
    						$('#passwordImage').attr('src',_base+'/resources/slpmall/images/icon-b.png');
    						$('#errorPassFlag').val("1");
    						return true;
    					}else{
    						$("#errorPawMsg").show();
    						$("#passwordImage").show();
    						$('#showPawMsg').show();
    						$('#passwordImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
    						$('#showPawMsg').text("6-20个字符，可用字母、数字及符号的组合 ");
    		    			$('#errorPassFlag').val("0");
    						return false;
    					}
    				}else{
    					$("#errorPawMsg").show();
						$("#passwordImage").show();
						$('#showPawMsg').show();
						$('#passwordImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
    					$('#showPawMsg').text("不允许有空格 ");
            			$('#errorPassFlag').val("0");
        				return false;
    				}
    			}else{
    				$('#showPawMsg').text("支持数字、字母、符号组合 ");
        			$("#errorPawMsg").attr("style","display:");
        			$('#errorPassFlag').val("0");
    				return false;
    			}
    	},
		//判断输入密码的类型  
		_charMode:function(iN){
			if (iN>=48 && iN <=57) //数字  
				return 1;  
				if (iN>=65 && iN <=90) //大写  
					return 2;  
				if (iN>=97 && iN <=122) //小写  
					return 4;  
				else  
					return 8; 
		},
		//bitTotal函数  
		//计算密码模式  
		_bitTotal:function(num){
			var modes=0;  
			for (var i=0;i<4;i++){  
				if (num & 1) modes++;  
				num>>>=1;  
			}  
			return modes; 
		},
		//返回强度级别
		_checkStrong:function(sPW){
			if (sPW.length<=6)  
				return 0; //密码太短  
				var Modes=0;  
				for (var i=0;i<sPW.length;i++){  
					//密码模式  
					Modes|=this._charMode(sPW.charCodeAt(i));  
				}  
			return this._bitTotal(Modes); 
		},
		//显示密码强度
		_pwStrength:function(){
			var flag = this._validServicePaw();
			if(!flag) return;
			var pwd = $("#passwordInput").val();
			var S_level=this._checkStrong(pwd);  
			switch(S_level) {  
			case 0:  
				$("#strength_L").show();
				$("#strength_M").hide();
				$("#strength_H").hide();
				break; 
			case 1:  
				$("#strength_L").show();
				$("#strength_M").hide();
				$("#strength_H").hide(); 
				break;  
			case 2:  
				$("#strength_L").hide();
				$("#strength_M").show();
				$("#strength_H").hide(); 
				break;  
			default:  
				$("#strength_L").hide();
				$("#strength_M").hide();
				$("#strength_H").show(); 
			  }  
		},
		//密码校验
    	_passwordConfirmation:function(){
    		var inputPassword = $("#passwordInput").val();
    		if(inputPassword==""){
    			$("#errorPawMsg").show();
    			$("#showPawMsg").text("请输入密码");
    			return false;
    		}
    		var confirmationPassword = $("#confirmationPassword").val();
    		if(inputPassword!=""&&confirmationPassword==""){
    			$("#errorPasswordMsg").show();
    			$("#showPasswordMsg").show();
    			$("#confirmationPasswordImage").attr("src",_base+'/resources/slpmall/images/icon-a.png')
    			$("#showPasswordMsg").text("请输入确认密码");
    			return false;
    		}else{
    			$("#confirmationPasswordImage").attr("src",_base+'/resources/slpmall/images/icon-b.png')
    			$("#errorPasswordMsg").hide();
    			$("#showPasswordMsg").hide();
    		}
    		if(inputPassword!=confirmationPassword){
    			$("#confirmationPasswordImage").attr('src',_base+'/resources/slpmall/images/icon-a.png');
    			$("#showPasswordMsg").text("两次输入的密码不一致");
    			$("#errorPasswordMsg").show();
    			$("#showPasswordMsg").show();
    			$("#errorPassEqualsFlag").val("0");
    			return false;
    		}else{
    			$("#errorPasswordMsg").show();
    			$("#showPasswordMsg").hide();
    			$("#errorPassEqualsFlag").val("1");
    			$("#confirmationPasswordImage").attr('src',_base+'/resources/slpmall/images/icon-b.png');
    			return true;
    		}
    	},
    	_submit:function(){
    		
    		var passwordFlag = $("#passwordFlag").val();
    		if(passwordFlag=="1") {
    			return false;
    		}
    		
    		var validFlag = this._validServicePaw();
    		if(!validFlag) return;
    		var passConfirmFlag = this._passwordConfirmation();
    		if(!passConfirmFlag) return;
    		
    		var	param={
    				password:$("#passwordInput").val()
				   };
    		ajaxController.ajax({
				type : "POST",
				data : param,
				dataType: 'json',
				url :_base+"/user/payPassword/updatePayPasswordNew",
				processing: true,
				message : "正在处理中，请稍候...",
				success : function(data) {
					var resultCode = data.responseHeader.resultCode;
					if(resultCode=="000001"){
						$("#errorPawMsg").show();
						$("#showPawMsg").text("修改失败请重新输入支付密码");
					}else{
						if(resultCode=="000000"){
							$("#errorPawMsg").hide();
							$("#passwordImage").hide();
							var url = data.data;
							window.location.href = _base+url;
						}
					}
				},
				error : function(){
					alert("网络连接超时!");
				}
			
    		})
    	},
    	_sendPhoneVerifyCode:function(){
			var _this = this;
			$("#sendVerify").attr("disabled", true);
			var	param={
					userMp:$("#phone").html()
				   };
			ajaxController.ajax({
				type : "POST",
				data : param,
				dataType: 'json',
				url :_base+"/user/verify/sendPhoneVerify?confirmType=1",
				processing: true,
				message : "正在处理中，请稍候...",
				success : function(data) {
					var resultCode = data.responseHeader.resultCode;
					if(resultCode=="100000"){
						var url = data.data;
						window.location.href = _base+url;
					}else{
						if(resultCode=="000000"){
							var step = 59;
				            $('#sendVerify').val('重新发送60');
				            $("#sendVerify").attr("disabled", true);
				            var _res = setInterval(function(){
				                $("#sendVerify").attr("disabled", true);//设置disabled属性
				                $('#sendVerify').val(step+'s后重新发送');
				                step-=1;
				                if(step <= 0){
				                $("#sendVerify").removeAttr("disabled"); //移除disabled属性
				                $('#sendVerify').val('获取验证码');
				                clearInterval(_res);//清除setInterval
				                }
				            },1000);
						}else{
							$("#sendVerify").removeAttr("disabled");
						}
						if(resultCode=="100002"){
							$("#phoneVerifyCodeError").show();
							_this._controlMsgText("verifyCodeMsg",data.statusInfo);
							_this._controlMsgAttr("verifyCodeMsg",2);
			        	}else{
			        		$("#phoneVerifyCodeError").hide();
			        		_this._controlMsgText("verifyCodeMsg","");
			        		_this._controlMsgAttr("verifyCodeMsg",1);
			        	}
					}
				},
				failure : function(){
					$("#sendVerify").removeAttr("disabled"); //移除disabled属性
				},
				error : function(){
					alert("网络连接超时!");
				}
			});
		},
		_checkPhoneVerifyCode: function(){
			var verifyCode = jQuery.trim($("#phone").val());
			if(verifyCode == "" || verifyCode == null || verifyCode == undefined){
				$("#errorSmsMsg").show();
				this._controlMsgText("phoneText","请输入手机验证码");
				this._controlMsgAttr("phoneText",2);
				return false;
			}else{
				$("#errorSmsMsg").hide();
				this._controlMsgText("phoneText","");
				this._controlMsgAttr("phoneText",1);
				return true;
			}
		},
		_next:function(){
			var _this = this;
			var checkPhoneVerifyCode = this._checkPhoneVerifyCode();
			if(!checkPhoneVerifyCode){
    			return false;
    		}
			ajaxController.ajax({
				type : "POST",
				data : _this._getSafetyConfirmData(),
				dataType: 'json',
				url :_base+"/user/verify/confirmInfo?confirmType=1",
				processing: true,
				message : "正在处理中，请稍候...",
				success : function(data) {
					var status = data.responseHeader.resultCode;
					if(status == "000000"){
						window.location.href=_base+"/user/payPassword/setPayPassword"
					}else{
						var msg = data.statusInfo;
						//验证码
						if(status == "000003"||status == "000004"||status == "000007"){
							$("#phoneVerifyCodeError").show();
							_this._controlMsgText("showSmsMsg",msg);
							_this._controlMsgAttr("showSmsMsg",2);
						}else{
							$("#phoneVerifyCodeError").hide();
							_this._controlMsgText("showSmsMsg","");
							_this._controlMsgAttr("showSmsMsg",1);
						}
					}
				},
				error : function(){
					alert("网络连接超时，请重新修改登录密码");
				}
			});
		
		},
		_checkPasswordValue:function(){
			var _this=this;
			
			var flag = this._validServicePaw();
			if(!flag) return;
			var	param={
					password:$("#passwordInput").val()
				   };
			ajaxController.ajax({
				type : "POST",
				data : param,
				dataType: 'json',
				url :_base+"/user/payPassword/checkPasswordValue",
				processing: true,
				message : "正在处理中，请稍候...",
				success : function(data) {
					var status = data.responseHeader.resultCode;
					if(status == "000000"){
						$("#errorPawMsg").show();
		        		$("#showPawMsg").hide();
		        		$("#passwordFlag").val("0");
		        		//$('#errorPhoneFlag').val("1");
		        		$('#passwordImage').attr('src',_base+'/resources/slpmall/images/icon-b.png');
						//window.location.href=_base+"/user/payPassword/setPayPassword"
					}else{
						var msg = data.statusInfo;
						//验证码
						if(status == "10003"){
							$("#errorPawMsg").show();
							$("#passwordImage").show();
							$('#passwordImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
							_this._controlMsgText("showPawMsg",msg);
							_this._controlMsgAttr("showPawMsg",2);
							$("#passwordFlag").val("1");
							return false;
							
						}else{
							$("#errorPawMsg").hide();
							_this._controlMsgText("showPawMsg","");
							_this._controlMsgAttr("showPawMsg",1);
							$("#passwordFlag").val("0");
							return true;
						}
					}
				},
				error : function(){
					alert("网络连接超时，请重新修改支付密码");
				}
		});
		},
		//获取界面填写验证信息
		_getSafetyConfirmData:function(){
			return{
				"userMp":function () {
					return jQuery.trim($("#phone").html())
			    },
				"pictureVerifyCode":function () {
			        return jQuery.trim($("#pictureVerifyCode").val())
			    },
				"verifyCode":function () {
			        return jQuery.trim($("#phoneVerifyCode").val())
			    }
			}
		}
    });
    module.exports = ConfirmInfoPager
});
