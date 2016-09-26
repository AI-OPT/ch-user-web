define('app/jsp/qualification/noCheckedPagerList', function (require, exports, module) {
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
    var noCheckedPagerListPager = Widget.extend({
    	
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5
    	},
    	//事件代理
    	events: {
    		"click #scoreListForm":"_getScoreList"
        },
    	//重写父类
    	setup: function () {
    		noCheckedPagerListPager.superclass.setup.call(this);
    	},

    	_getInitList:function(companyType){
    		var _this = this;
    		$("#pagination-ul").runnerPagination({
    			url: _base+"/qualification/getUncheckList",
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	 			renderId:"UN_CHECKED",
	 			messageId:"showMessageDiv",
	            data : {
	            	"companyType":companyType,
	            	"auditState":'1',
				},
	           	pageSize: noCheckedPagerListPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            callback: function(data){
	              	if(data.result != null && data.result != 'undefined' && data.result.length>0){
	            		var template = $.templates("#unCheckedImpl");
	                    var htmlOutput = template.render(data);
	                    $("#UN_CHECKED").html(htmlOutput);
	            	}
	            }
    		}); 
    	},
    	
    	_toAuditShopPage:function(userId,username){
			window.location.href = _base+'/qualification/toShopCheckPager?userId='+userId+'&username='+username;
			
		},
		_toAuditSupplierPage:function(userId,username){
			window.location.href = _base+'/qualification/toSuplierCheckPager?userId='+userId+'&username='+username;
			
		},
		
		_getList:function(companyType){
    		var _this = this;
    		$("#pagination-ul").runnerPagination({
    			url: _base+"/qualification/getUncheckList",
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	 			messageId:"showMessageDiv",
	 			renderId:"UN_CHECKED",
	            data : {
	            	"username":$("#username").val().replace(/(^\s*)|(\s*$)/g,""),
					"companyName":$("#companyName").val().replace(/(^\s*)|(\s*$)/g,""),
					"companyType":companyType,
					"auditState":'1',
				},
	           	pageSize: noCheckedPagerListPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            callback: function(data){
	              	if(data.result != null && data.result != 'undefined' && data.result.length>0){
	            		var template = $.templates("#unCheckedImpl");
	                    var htmlOutput = template.render(data);
	                    $("#UN_CHECKED").html(htmlOutput);
	            	}
	            }
    		}); 
    	}
    	
    });
    
    module.exports = noCheckedPagerListPager
});