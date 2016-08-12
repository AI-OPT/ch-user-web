define(
		'app/jsp/user/changePhone/change-phone',
		function(require, exports, module) {
			'use strict';
			var $ = require('jquery'), Validator = require('arale-validator/0.10.2/index'), Calendar = require('arale-calendar/1.1.2/index'), Widget = require('arale-widget/1.2.0/widget'), Dialog = require("artDialog/src/dialog"), AjaxController = require('opt-ajax/1.0.0/index');

			// 实例化AJAX控制处理对象
			var ajaxController = new AjaxController();

			// 定义页面组件类
			var ChangePhonePager = Widget.extend({
				// 属性，使用时由类的构造函数传入
				attrs : {},
				Statics: {
		    		DEFAULT_PAGE_SIZE: 5,
		    		USER_LEFT_MNU_ID: "left_mnu_security_set"
		    	},
				// 事件代理
				events : {
				// key的格式: 事件+空格+对象选择器;value:事件方法
				// "click [id='randomImg']":"_refrashVitentify",
				},
				init : function() {
					//_hideErroText();
				},
				// 重写父类
				setup : function() {
					ChangePhonePager.superclass.setup.call(this);
					activeUserLeftMenu(ChangePhonePager.USER_LEFT_MNU_ID);
					//this._hideErroText();
					this._bindHandle();
				},
				//_hideInfo : function() {},
				// 带下划线的方法，约定为内部私有方法
				_bindHandle : function() {
					$("#password").on("blur", this._checkPasswordEmpty);
					$("#password").on("focus", this._hidePassword);
					$("#newPhone").on("blur", this._validServiceNewPho);
					$("#newPhone").on("focus", this._hideNewPhone);
					$("#validateCode").on("blur", this._checkValidateCodeEmpty);
					$("#validateCode").on("focus", this._hideValidateCode);
					$("#phoneCode").on("blur", this._checkPhoneCodeEmpty);
					$("#phoneCode").on("focus", this._hidePhoneCode);
					$("#PHONE_IDENTIFY1").on("click", this._getPhoneVitentify1);
					$("#PHONE_IDENTIFY2").on("click", this._getPhoneVitentify2);
					$("#CHECKPAYPASSWORD").on("click", this._validatePayPassword);
					$("#next").on("click", this._next);
					$("#submit").on("click", this._submit);
					},
				//检查验证码是否为空
				_checkValidateCodeEmpty : function(){
					$("#validateCodeErrMsg").attr("style", "display:none");
					var validateCode = $('#validateCode').val();
					if(validateCode==""){
						$("#validateCodeErrMsgShow").text("验证码不能为空");
						$("#validateCodeErrMsg").show();
						$("#validateCodeEmptyFlag").val("0");
					}
				},
				//检查验证码是否为空
				_checkPhoneCodeEmpty : function(){
					$("#newPhoneCodeErrMsg").attr("style", "display:none");
					var phoneCode = $('#phoneCode').val();
					if(phoneCode==""){
						$("#newPhoneCodeErrMsgShow").text("手机验证码不能为空");
						$("#newPhoneCodeErrMsg").show();
						$("#phoneCodeFlag").val("0");
					}
				},
				//检查支付密码是否为空
				_checkPasswordEmpty : function(){
					$("#passwordErrMsg").attr("style", "display:none");
					var password = $('#password').val();
					if(password==""){
						$("#passwordErrMsgShow").text("支付密码不能为空");
						$("#passwordErrMsg").show();
						$("#passwordEmptyFlag").val("0");
					}
				},
				//隐藏新手机号错误提示
				_hideNewPhone : function(){
					$("#newPhoneErrFlag").val("1");
					$("#newPhoneErrMsg").attr("style", "display:none");
					$("#newPhoneCodeErrMsg").attr("style", "display:none");
				},
				//隐藏验证码错误提示
				_hideValidateCode : function(){
					$("#validateCodeErrMsg").attr("style", "display:none");
				},
				//隐藏验证码错误提示
				_hidePhoneCode : function(){
					$("#phoneCodeFlag").val("1");
					$("#phoneCodeErrMsg").attr("style", "display:none");
				},
				//隐藏密码错误提示
				_hidePassword : function(){
					$("#passwordErrMsg").attr("style", "display:none");
				},

				
				//通过支付密码验证账户
				__validatePayPassword : function(){
					$("#validateCodeFlag").val("1");
					$("#validateCodeErrMsg").attr("style", "display:none");
					var validateCode = $('#validateCode').val();
					if(validateCode==""){
						$("#validateCodeErrMsgShow").text("验证码不能为空");
						$("#validateCodeErrMsg").show();
						$("#validateCodeEmptyFlag").val("0");
						return false;
					}
					
					var	param={
						userMp:$("#phone").val(),
						phoneVerifyCode:$("#validateCode").val()
    				   };
					ajaxController.ajax({
				        type: "post",
				        processing: false,
				        url: _base+"/reg/checkPhoneVerifyCode",
				        dataType: "json",
				        data: param,
				        message: "正在加载数据..",
				        success: function (data) {
				         if(data.responseHeader.resultCode=="000007"){
				        		$('#validateCodeErrMsgShow').text("手机与发送短信手机不一致");
								$("#validateCodeErrMsg").attr("style","display:");
								$('#validateCodeFlag').val("0");
								return false;
				        	}else if(data.responseHeader.resultCode=="000004"){
				        		$('#validateCodeErrMsgShow').text("验证码已失效");
				        		$("#validateCodeErrMsg").attr("style","display:");
								$('#validateCodeFlag').val("0");
								return false;
				        	}else if(data.responseHeader.resultCode=="000003"){
				        		$('#validateCodeErrMsgShow').text("短信验证码错误");
								$("#validateCodeErrMsg").attr("style","display:");
								$('#validateCodeFlag').val("0");
								return false;
				        	}else if(data.responseHeader.resultCode=="000000"){
				        		$('#validateCodeFlag').val("1");
								$("#validateCodeErrMsg").attr("style","display:none");
				        	}
				        	
				        },
				        error: function(XMLHttpRequest, textStatus, errorThrown) {
							 alert(XMLHttpRequest.status);
							 alert(XMLHttpRequest.readyState);
							 alert(textStatus);
							}
		    			        
		    			    }); 
					
				},
				
				// 点击下一步用户信息显示
				_next : function() {
					$("#validateCodeFlag").val("1");
					$("#validateCodeErrMsg").attr("style", "display:none");
					var validateCode = $('#validateCode').val();
					if(validateCode==""){
						$("#validateCodeErrMsgShow").text("验证码不能为空");
						$("#validateCodeErrMsg").show();
						$("#validateCodeEmptyFlag").val("0");
					}
					if($("#validateCodeFlag").val()!='0'){
					var	param={
							userMp:$("#phone").val(),
							verifyCode:$("#validateCode").val(),
							confirmType:"1"
	    				   };
						ajaxController.ajax({
					        type: "post",
					        processing: false,
					        url: _base+"/user/verify/confirmInfo",
					        dataType: "json",
					        data: param,
					        message: "正在加载数据..",
					        success: function (data) {
					        	if(data.responseHeader.resultCode=="000003"){
					        	 	$("#validateCodeErrMsg").show();
					        		$('#validateCodeErrMsgShow').text("短信验证码错误");
									$('#validateCodeFlag').val("0");
									return false;
					        	}else if(data.responseHeader.resultCode=="000004"){
					        		$("#validateCodeErrMsg").show();
					        		$('#validateCodeErrMsgShow').text("短信验证码已失效");
									$('#validateCodeFlag').val("0");
									return false;
					        	}else if(data.responseHeader.resultCode=="000007"){
					        		$("#validateCodeErrMsg").show();
					        		$('#validateCodeErrMsgShow').text("手机与发送短信手机不一致");
									$('#validateCodeFlag').val("0");
									return false;
					        	}else{
					        		$("#changePhoneBorder2").removeClass()
									.addClass("yellow-border");
									$("#changePhoneYuan2").removeClass().addClass(
									"yellow-yuan");
									$("#changePhoneWord2").removeClass().addClass(
									"yellow-word");
									$("#change-phone1").hide();
									$("#change-phone2").show();
									$("#change-phone3").hide();
					        		$('#validateCodeFlag').val("1");
					        	}
					        	
					        	
					        },
					        error: function(XMLHttpRequest, textStatus, errorThrown) {
								 alert(XMLHttpRequest.status);
								 alert(XMLHttpRequest.readyState);
								 alert(textStatus);
								}
			    			        
			    			    }); 
					}
					},

				// 点击下一步用户信息显示
				_submit : function() {
					$("#phoneCodeFlag").val("1");
					$("#newPhoneErrFlag").val("1");
					
					//校验手机号是否为空
					var phone = $('#newPhone').val();
					if (phone == "") {
						$("#newPhoneErrMsg").attr("style", "display:");
						$('#newPhoneErrMsg').attr('src',
								_base + '/theme/slp/images/icon-a.png');
						$('#newPhoneErrMsgShow').text("手机号不能为空");
						$("#newPhoneErrMsg").show();
						$('#newPhoneErrFlag').val("0");
						return false;
					}
					
					//校验验证码是否为空
					$("#newPhoneCodeErrMsg").attr("style", "display:none");
					var phoneCode = $('#phoneCode').val();
					if(phoneCode==""){
						$("#newPhoneCodeErrMsgShow").text("手机验证码不能为空");
						$("#newPhoneCodeErrMsg").show();
						$("#phoneCodeFlag").val("0");
						return false;
					}
					var phoneCodeFlag = $("#phoneCodeFlag").val();
					var newPhoneErrFlag = $("#newPhoneErrFlag").val();
					if(phoneCodeFlag!='0'&&newPhoneErrFlag!='0'){
					var	param={
							userMp:$("#newPhone").val(),
							verifyCode:$("#phoneCode").val(),
							confirmType:"1"
	    				   };
						ajaxController.ajax({
					        type: "post",
					        processing: false,
					        async:false,
					        url: _base+"/user/verify/confirmInfo",
					        dataType: "json",
					        data: param,
					        message: "正在加载数据..",
					        success: function (data) {
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
					        	}else{
					        		$("#newPhoneCodeErrMsg").hide();
					        		$('#phoneCodeFlag').val("1");
					        	}
					         
					        },
					        error: function(XMLHttpRequest, textStatus, errorThrown) {
								 alert(XMLHttpRequest.status);
								 alert(XMLHttpRequest.readyState);
								 alert(textStatus);
								}
			    			    }); 
					var phoneCodeFlag = $("#phoneCodeFlag").val();
					if(phoneCodeFlag!=0){
					var param = {
							userMp : $("#newPhone").val()
					};
					ajaxController.ajax({
						type : "post",
						processing : false,
						async: false, 
						url : _base + "/user/phone/updatePhone",
						dataType : "json",
						data : param,
						message : "正在加载数据..",
						success : function(data) {
							if(data.responseHeader.resultCode=='11112'){
							  	var userMp = $("#newPhone").val();
			                 	var phoneStr = userMp.substring(0,3)+"****"+userMp.substr(7,4);
								$("#changePhoneBorder3").removeClass()
								.addClass("yellow-border");
								$("#changePhoneYuan3").removeClass().addClass(
								"yellow-yuan");
								$("#changePhoneWord3").removeClass().addClass(
								"yellow-word");
								$("#phoneStr").html(phoneStr);
								$("#change-phone1").hide();
								$("#change-phone2").hide();
								$("#change-phone3").show();
							}
						},
						error : function(XMLHttpRequest,
								textStatus, errorThrown) {
							alert(XMLHttpRequest.status);
							alert(XMLHttpRequest.readyState);
							alert(textStatus);
						}
					});
				}
					}
				},
				
	
				// 获取绑定手机短信验证码
				_getPhoneVitentify1 : function() {
					$("#phoneCodeErrMsg").attr("style", "display:none");
					$("#PHONE_IDENTIFY1").attr("disabled", true);
						
						var param = {
							userMp : $("#phone").val()
						};
						ajaxController.ajax({
							type : "post",
							processing : false,
							url : _base + "/user/verify/sendPhoneVerify?confirmType=1&functionType=0",
							dataType : "json",
							data : param,
							message : "正在加载数据..",
							success : function(data) {
								var resultCode = data.responseHeader.resultCode;
								if(resultCode=="100000"){
									var url = data.data;
									window.location.href = _base+url;
								}else{
									if(resultCode=="000000"){
										var step = 59;
							            $('#PHONE_IDENTIFY1').val('重新发送60');
							            $("#PHONE_IDENTIFY1").attr("disabled", true);
							            var _res = setInterval(function(){
							                $("#PHONE_IDENTIFY1").attr("disabled", true);//设置disabled属性
							                $('#PHONE_IDENTIFY1').val(step+'s后重新发送');
							                step-=1;
							                if(step <= 0){
							                $("#PHONE_IDENTIFY1").removeAttr("disabled"); //移除disabled属性
							                $('#PHONE_IDENTIFY1').val('获取验证码');
							                clearInterval(_res);//清除setInterval
							                }
							            },1000);
									}else{
										$("#PHONE_IDENTIFY1").removeAttr("disabled");
									}
									if(resultCode=="100002"){
										var msg = data.statusInfo;
										$('#validateCodeErrMsgShow').text(msg);
										$("#validateCodeErrMsg").show();
										return false;
						        	}else{
						        		$("#validateCodeErrMsg").hide();
						        	}
								}
							},
								error : function(XMLHttpRequest,
										textStatus, errorThrown) {
									alert(XMLHttpRequest.status);
									alert(XMLHttpRequest.readyState);
									alert(textStatus);
									}

								});
				},
				// 获取新手机短信验证码
				_getPhoneVitentify2 : function() {
					$("#phoneCodeErrMsg").attr("style", "display:none");
					$("#PHONE_IDENTIFY2").attr("disabled", true);
					
					var phoneFlag = $('#newPhoneErrFlag').val();
					if (phoneFlag != "0") {
						var step = 60;
						$('#PHONE_IDENTIFY2').val('重新发送60');
						$("#PHONE_IDENTIFY2").attr("disabled", true);
						var _res = setInterval(function() {
							$("#PHONE_IDENTIFY2").attr("disabled", true);// 设置disabled属性
							$('#PHONE_IDENTIFY2').val(step + 's后重新发送');
							step -= 1;
							if (step <= 0) {
								$("#PHONE_IDENTIFY2")
										.removeAttr("disabled"); // 移除disabled属性
								$('#PHONE_IDENTIFY2').val(
										'获取验证码');
								clearInterval(_res);// 清除setInterval
								}
							}, 1000);
						var param = {
							userMp : $("#newPhone").val()
						};
						ajaxController.ajax({
							type : "post",
							processing : false,
							url : _base + "/user/verify/sendPhoneVerify?confirmType=1&functionType=1",
							dataType : "json",
							data : param,
							message : "正在加载数据..",
							success : function(data) {
								if (data.responseHeader.resultCode == "9999") {
									$('#newPhoneCodeErrMsg').text("1分钟后可重复发送 ");
									$("#newPhoneCodeErrMsg").attr("style","display:");
									$("#newPhoneCodeFlag").val("0");
									return false;
								} else if (data.responseHeader.resultCode == "100002") {
									var msg = data.statusInfo;
									$('#newPhoneCodeErrMsg').text(msg);
									$("#newPhoneCodeErrMsg").attr("style","display:");
									return false;
								}
							},
								error : function(XMLHttpRequest,
										textStatus, errorThrown) {
									alert(XMLHttpRequest.status);
									alert(XMLHttpRequest.readyState);
									alert(textStatus);
									}
								});
					}
				},
				
			// 校验新手机
			_validServiceNewPho : function() {
				$("#newPhoneErrMsg").attr("style", "display:none");
				$("#PHONE_IDENTIFY2").attr("disabled", false);
				var phone = $('#newPhone').val();
				if (phone == "") {
					$("#newPhoneErrMsg").attr("style", "display:");
					$('#newPhoneErrMsg').attr('src',_base + '/theme/slp/images/icon-a.png');
					$('#newPhoneErrMsgShow').text("手机号不能为空");
					$("#newPhoneErrMsg").show();
					$('#newPhoneErrFlag').val("0");
					return false;
				} else if (!(/^0?1[3|4|5|8][0-9]\d{8}$/.test(phone))) {
					$("#newPhoneErrMsg").attr("style", "display:");
					$('#newPhoneErrMsg').attr('src',
							_base + '/theme/slp/images/icon-a.png');
					$('#newPhoneErrMsgShow').text("请输入正确有效的手机号码");
					$("#newPhoneErrMsg").show();
					$('#newPhoneErrFlag').val("0");
					return false;
				} 
				var param = {
						userMp : $("#newPhone").val()
					};
					ajaxController.ajax({
						type : "post",
						processing : false,
						url : _base + "/user/phone/validatePhone",
						dataType : "json",
						data : param,
						message : "正在加载数据..",
						success : function(data) {
							var resultCode=data.responseHeader.resultCode;
							if(resultCode=="10003"){
								$("#newPhoneErrMsg").attr("style", "display:");
								$('#newPhoneErrMsg').attr('src',_base + '/theme/slp/images/icon-a.png');
								$('#newPhoneErrMsgShow').text("手机号已注册");
								$("#newPhoneErrMsg").show();
								$('#newPhoneErrFlag').val("0");
								return false;
							}
						},
							error : function(XMLHttpRequest,textStatus, errorThrown) {
								alert(XMLHttpRequest.status);
								alert(XMLHttpRequest.readyState);
								alert(textStatus);
								}

							});
			}
			
		});
			module.exports = ChangePhonePager
		});
