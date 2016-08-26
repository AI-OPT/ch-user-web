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
    			if(defaultReason.length>=1&&defaultReason.length<=512){
    				$("#defaultReasonErrMsg").hide();
        			$("#defaultReasonText").hide();
        			$("#defaultReasonFlag").val("1");
    			}else{
    				$("#defaultReasonErrMsg").show();
        			$("#defaultReasonText").show();;
        			$("#defaultReasonText").text("请输入违款原因");
        			$("#defaultReasonFlag").val("0");
    			}
    		}
    	},
    	_checkAmount:function(){
    		var amount = $("#amount").val();
    		if(amount==""||amount==null){
    			$("#amountErrMsg").show();
    			$("#amountText").show();
    			$("#amountText").text("请输入扣款金额");
    			$("#amountFlag").val("0");
    		}else{
    			var reg = /^(\d{1,15}|\d{1,12}\.\d{1,2})$/;
				if(amount.match(reg)){
    				$("#amountErrMsg").hide();
        			$("#amountText").hide();
        			$("#amountFlag").val("1");
    			}else{
    				$("#amountErrMsg").show();
        			$("#amountText").show();;
        			$("#amountText").text("请输入数字，最多有两位小数");
        			$("#amountFlag").val("0");
    			}
    		}
    	},
    	_saveDefaultManager:function(){
    		this._checkDefaultReason();
    		this._checkAmount();
    		var defaultReasonFlag = $("#defaultReasonFlag").val();
    		var amountFlag = $("#amountFlag").val();
    		if(defaultReasonFlag!="0"&&amountFlag!="0"){
    			$.ajax({
    				type:"post",
    				url:_base+"/billing/saveDefaultInfo",
    				dataType: "json",
    				data:$("#defaultManagerForm").serialize(),
    		        success: function(data) {
    		        	if(data.responseHeader.resultCode=="111111"){
    		        		alert("失败了");
    		        		return false;
    		        	}else if(data.responseHeader.resultCode=="000000"){
    		        		window.location.href= _base+"/billing/defaultManagerPager";
    		        	}
    		          },
    				error: function(error) {
    						alert("error:"+ error);
    					}
    				});
    		}
    	}
    });
    
    module.exports = DefaultPagerManager
});

