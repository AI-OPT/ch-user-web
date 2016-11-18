define('app/jsp/billing/defaultManager', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("optDialog/src/dialog"),
    Paging = require('paging/0.0.1/paging-debug'),
    Uploader = require('arale-upload/1.2.0/index'),
    AjaxController = require('opt-ajax/1.0.0/index'),
    Calendar = require('arale-calendar/1.1.2/index');
    
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("bootstrap-paginator/bootstrap-paginator.min");
    require("app/util/jsviews-ext");
    require("opt-paging/aiopt.pagination");
    require("twbs-pagination/jquery.twbsPagination.min");
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var DefaultPagerManager = Widget.extend({
    	
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5
    	},
    	//事件代理
    	events: {
    		"blur [id='defaultReason']":"_checkDefaultReason",
    		"blur [id='amount']":"_checkAmount",
    		"click [id='saveDefaultManager']":"_saveDefaultManager"
        },
    	//重写父类
    	setup: function () {
    		DefaultPagerManager.superclass.setup.call(this);
    	},
    	_checkDefaultReason:function(){
    		var defaultReason = $("#defaultReason").val();
    		if(defaultReason==""||defaultReason==null){
    			$("#defaultReasonErrMsg").show();
    			$("#defaultReasonText").show();
    			$("#defaultReasonText").text("请输入违款原因");
    			$("#defaultReasonFlag").val("0");
    		}else{
    			if(defaultReason.length>=1&&defaultReason.length<=128){
    				if(defaultReason.match(/^\s+$/)){
    					$("#defaultReasonErrMsg").show();
    	    			$("#defaultReasonText").show();
    	    			$("#defaultReasonText").text("必填项");
    	    			$("#defaultReasonFlag").val("0");
					}else{
	    				$("#defaultReasonErrMsg").hide();
	        			$("#defaultReasonText").hide();
	        			$("#defaultReasonFlag").val("1");
        			}
    			}else{
    				$("#defaultReasonErrMsg").show();
        			$("#defaultReasonText").show();;
        			$("#defaultReasonText").text("1-128位字符");
        			$("#defaultReasonFlag").val("0");
    			}
    		}
    	},
    	_checkAmount:function(){
    		var amount = $.trim($("#amount").val());
    		if(amount==""||amount==null){
    			$("#amountErrMsg").show();
    			$("#amountText").show();
    			$("#amountText").text("请输入扣款金额");
    			$("#amountFlag").val("0");
    		}else{
    			var reg = /^(\d{1,12}|\d{1,10}\.\d{1,2})$/;
				if(amount.match(reg)){
					if(amount<=balance){
						$("#amountErrMsg").hide();
	        			$("#amountText").hide();
	        			$("#amountFlag").val("1");
					}else{
						$("#amountErrMsg").show();
	        			$("#amountText").show();
	        			$("#amountText").text("输入的扣款金额不能大于保证金余额");
	        			$("#amountFlag").val("0");
					}
    				
    			}else{
    				$("#amountErrMsg").show();
        			$("#amountText").show();
    				if(amount.length>12){
    					$("#amountText").text("不能超过12个字符");
    				}else{
    					$("#amountText").text("请输入大于0的数字，最多有两位小数");
    				}
        			$("#amountFlag").val("0");
    			}
    		}
    		if(parseFloat(amount)==0){
    			$("#amountErrMsg").show();
    			$("#amountText").text("请输入大于0的数字，最多有两位小数");
    			$("#amountText").show();
    			$("#amountFlag").val("0");
    		}
    	},
    	_dialog:function(title,content,icon,okValue,url){
    		var d =new Dialog({
    			title : title,
    			content : content,
    			icon:icon,
    			closeIconShow:false,
    			okValue : okValue,
    			ok : function() {
    				this.close;
    				window.location.href=url;
    			}
    		});
    		d.show();
    	},
    	_saveDefaultManager:function(){
    		this._checkDefaultReason();
    		this._checkAmount();
    		var defaultReasonFlag = $("#defaultReasonFlag").val();
    		var amountFlag = $("#amountFlag").val();
    		if(defaultReasonFlag!="0"&&amountFlag!="0"){
    			$.ajax({
    				type:"post",
    				url:_base+"/defaultManager/saveDefaultInfo",
    				dataType: "json",
    				data:$("#defaultManagerForm").serialize(),
    				beforeSend:{
    					$("#back").attr("disabled","disabled");
    				},
    		        success: function(data) {
    		        	if(data.responseHeader.resultCode=="111111"){
    		        		var d =new Dialog({
    		        			title : "提示",
    		        			content : "扣款失败",
    		        			icon:"fail",
    		        			closeIconShow:false,
    		        			okValue : "确定",
    		        			ok : function() {
    		        				this.close;
    		        			}
    		        		});
    		        		d.show();
    		        		return false;
    		        	}else if(data.responseHeader.resultCode=="000000"){
    		        		var d =new Dialog({
    		        			title : "提示",
    		        			content : "扣款成功",
    		        			icon:"success",
    		        			closeIconShow:false,
    		        			okValue : "确定",
    		        			ok : function() {
    		        				this.close;
    		        				window.location.href= _base+"/defaultManager/defaultHistoryPager?userId="+userId+"&userName="+escape(encodeURIComponent(userName))+"&custName="+escape(encodeURIComponent(custName));
    		        			}
    		        		});
    		        		d.show();
    		        	}
    		          },
    				error: function(error) {
    					var d =new Dialog({
		        			title : "提示",
		        			content : "请求超时,请重试",
		        			icon:"fail",
		        			closeIconShow:false,
		        			okValue : "确定",
		        			ok : function() {
		        				this.close;
		        			}
		        		});
		        		d.show();
    				}
    				});
    		}
    });
    
    module.exports = DefaultPagerManager
});

