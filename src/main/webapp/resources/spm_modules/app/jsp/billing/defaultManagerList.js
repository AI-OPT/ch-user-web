define('app/jsp/billing/defaultManagerList', function (require, exports, module) {
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
    var DefaultManagerListPager = Widget.extend({
    	
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5
    	},
    	//事件代理
    	events: {
        },
    	//重写父类
    	setup: function () {
    		DefaultManagerListPager.superclass.setup.call(this);
    		this._queryDefaultManagerList();
    	},
    	
    	//获取违约历史列表
    	_queryDefaultManagerList: function(){
    		var _this = this;
    		$("#pagination-ul").runnerPagination({
    			url: _base+"/defaultManager/getList",
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	 			messageId:"showMessageDiv",
	 			renderId:"TBODY_DEFAULTLIST",
	            data : {
	            	"companyType":"2"
				},
	           	pageSize: DefaultManagerListPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            callback: function(data){
	              	if(data.result != null && data.result != 'undefined' && data.result.length>0){
	            		var template = $.templates("#defaultManagerImpl");
	                    var htmlOutput = template.render(data);
	                    $("#TBODY_DEFAULTLIST").html(htmlOutput);
	            	}
	            }
    		}); 
    	},
    	_toDefaultCutPayment:function(userId,userName,custName){
    		window.location.href = _base+"/defaultManager/addDefaultInfo?userId="+userId+"&userName="+escape(encodeURIComponent(userName))+"&custName="+escape(encodeURIComponent(custName));
    	},
    	_toDefaultHistory:function(userId,userName,custName){
    		window.location.href = _base+"/defaultManager/defaultHistoryPager?userId="+userId+"&userName="+escape(encodeURIComponent(userName))+"&custName="+escape(encodeURIComponent(custName));
    	},
    	_getList:function(){
    		var _this = this;
    		$("#pagination-ul").runnerPagination({
    			url: _base+"/defaultManager/getList",
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	 			messageId:"showMessageDiv",
	 			renderId:"TBODY_DEFAULTLIST",
	            data : {
	            	"username":$("#username").val().replace(/(^\s*)|(\s*$)/g,""),
					"companyName":$("#companyName").val().replace(/(^\s*)|(\s*$)/g,""),
	            	"companyType":"2"
				},
	           	pageSize: DefaultManagerListPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            callback: function(data){
	              	if(data.result != null && data.result != 'undefined' && data.result.length>0){
	            		var template = $.templates("#defaultManagerImpl");
	                    var htmlOutput = template.render(data);
	                    $("#TBODY_DEFAULTLIST").html(htmlOutput);
	            	}
	            }
    		}); 
    	}

    	
    });
    
    module.exports = DefaultManagerListPager
});