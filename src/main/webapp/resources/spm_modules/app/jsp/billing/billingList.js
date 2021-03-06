define('app/jsp/billing/billingList', function (require, exports, module) {
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
    var BillingListPager = Widget.extend({
    	
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5
    	},
    	//事件代理
    	events: {
    		"change #rankRegion":"_initTable"
        },
    	//重写父类
    	setup: function () {
    		BillingListPager.superclass.setup.call(this);
    		this._getInitList();
    	},
    	
    	//获取已设置保证金列表
    	_getInitList: function(){
    		var _this = this;
    		$("#pagination-ul").runnerPagination({
    			url: _base+"/billing/getBillingList",
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	 			messageId:"showMessageDiv",
	 			renderId:"TBODY_BILLLIST",
	            data : {
	            	"companyType":"2"
				},
	           	pageSize: BillingListPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            callback: function(data){
	              	if(data.result != null && data.result != 'undefined' && data.result.length>0){
	            		var template = $.templates("#bailListImpl");
	                    var htmlOutput = template.render(data);
	                    $("#TBODY_BILLLIST").html(htmlOutput);
	            	}
	            }
    		}); 
    	},
    	
    	_getList:function(){
    	var _this = this;
		$("#pagination-ul").runnerPagination({
			url: _base+"/billing/getBillingList",
 			method: "POST",
 			dataType: "json",
 			processing: true,
 			messageId:"showMessageDiv",
 			renderId:"TBODY_BILLLIST",
            data : {
            	"username":$("#username").val().replace(/(^\s*)|(\s*$)/g,""),
				"companyName":$("#companyName").val().replace(/(^\s*)|(\s*$)/g,""),
            	"companyType":"2"
			},
           	pageSize: BillingListPager.DEFAULT_PAGE_SIZE,
           	visiblePages:5,
            message: "正在为您查询数据..",
            callback: function(data){
              	if(data.result != null && data.result != 'undefined' && data.result.length>0){
            		var template = $.templates("#bailListImpl");
                    var htmlOutput = template.render(data);
                    $("#TBODY_BILLLIST").html(htmlOutput);
            	}
            }
		});
    	},
    	_toMarginPage:function(userId,username,shopName){
    		var url =  _base+"/billing/marginsetting?userId="+userId+'&username='+escape(encodeURIComponent(username))+'&shopName='+escape(encodeURIComponent(shopName));
    		window.location.href=url;
    	},
    	_toServiceFeeSettingPage:function(userId,username,shopName){
    		var url = _base+"/billing/servicefeesetting?userId="+userId+'&username='+escape(encodeURIComponent(username))+'&shopName='+escape(encodeURIComponent(shopName));
    		window.location.href= url;
    	},
    	_toServiceFeePage:function(userId,username,shopName){
    		var url = _base+"/billing/servicefee?userId="+userId+'&username='+escape(encodeURIComponent(username))+'&shopName='+escape(encodeURIComponent(shopName));
    		window.location.href= url; 
    	}
    });
    
    module.exports = BillingListPager
});

