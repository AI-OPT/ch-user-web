define('app/jsp/qualification/checkedPagerList', function (require, exports, module) {
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
    require("bootstrap/js/modal")
    require("app/util/jsviews-ext");
    
    require("opt-paging/aiopt.pagination");
    require("twbs-pagination/jquery.twbsPagination.min");
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var checkedPagerListPager = Widget.extend({
    	
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
    		checkedPagerListPager.superclass.setup.call(this);
    	},

    	_getInitList:function(companyType){
    		var _this = this;
    		$("#pagination-ul-pass").runnerPagination({
    			url: _base+"/qualification/getCheckedList",
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	 			messageId:"showMessageDiv_pass",
	 			renderId:"TBODY_CHECKED_PASS",
	            data : {
	            	"companyType":companyType,
	            	"auditState":'1',
				},
	           	pageSize: checkedPagerListPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            callback: function(data){
	              	if(data.result != null && data.result != 'undefined' && data.result.length>0){
	            		var template = $.templates("#checkedImpl");
	                    var htmlOutput = template.render(data);
	                    $("#TBODY_CHECKED_PASS").html(htmlOutput);
	            	}
	            }
    		}); 
    		$("#pagination-ul-reject").runnerPagination({
    			url: _base+"/qualification/getCheckedList",
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	 			messageId:"showMessageDiv_reject",
	 			renderId:"TBODY_CHECKED_REJECT",
	            data : {
	            	"companyType":companyType,
	            	"auditState":'2',
				},
	           	pageSize: checkedPagerListPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            callback: function(data){
	              	if(data.result != null && data.result != 'undefined' && data.result.length>0){
	            		var template = $.templates("#checkedImpl");
	                    var htmlOutput = template.render(data);
	                    $("#TBODY_CHECKED_REJECT").html(htmlOutput);
	            	}
	            }
    		}); 
    	},
    	
		_toViewShopPage:function(userId,username){
			window.location.href  = _base+'/qualification/toShopDetailPager?userId='+userId+'&username='+escape(encodeURIComponent(username));
			
		},
		_toAuditShopPage:function(userId,username){
			window.location.href  = _base+'/qualification/toShopCheckPager?userId='+userId+'&username='+escape(encodeURIComponent(username));
		},
		_toShopAuditLogPager:function(userId,username,custname){
			window.location.href = _base+'/qualification/toShopAuditLogPager?userId='+userId+"&username="+escape(encodeURIComponent(username))+"&custname="+escape(encodeURIComponent(custname));
		},
		
		_toViewSupplierPage:function(userId,username){
			window.location.href  = _base+'/qualification/toSuplierDetailPager?userId='+userId+'&username='+escape(encodeURIComponent(username));
			
		},
		_toSuplierAuditLogPager:function(userId,username,custname){
			window.location.href =  _base+'/qualification/toSuplierAuditLogPager?userId='+userId+"&username="+escape(encodeURIComponent(username))+"&custname="+escape(encodeURIComponent(custname));
		},
		
		_toAuditSupplierPage:function(userId,username){
			window.location.href  = _base+'/qualification/toAuditSupplierPage?userId='+userId+'&username='+escape(encodeURIComponent(username));
			
		},
		
		_getList:function(companyType){
    		var _this = this;
    		$("#info_pass").html("");
    		$("#info_reject").html("");
    		$("#pagination-ul-pass").runnerPagination({
    			url: _base+"/qualification/getCheckedList",
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	 			messageId:"showMessageDiv_pass",
	 			renderId:"TBODY_CHECKED_PASS",
	            data : {
	            	"username":$("#username").val().replace(/(^\s*)|(\s*$)/g,""),
					"companyName":$("#companyName").val().replace(/(^\s*)|(\s*$)/g,""),
	            	"companyType":companyType,
	            	"auditState":'1',
				},
	           	pageSize: checkedPagerListPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            callback: function(data){
	              	if(data.result != null && data.result != 'undefined' && data.result.length>0){
	            		var template = $.templates("#checkedImpl");
	                    var htmlOutput = template.render(data);
	                    $("#TBODY_CHECKED_PASS").html(htmlOutput);
	            	}
	            }
    		}); 
    		$("#pagination-ul-reject").runnerPagination({
    			url: _base+"/qualification/getCheckedList",
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	 			messageId:"showMessageDiv_reject",
	 			renderId:"TBODY_CHECKED_REJECT",
	            data : {
	            	"username":$("#username").val().replace(/(^\s*)|(\s*$)/g,""),
					"companyName":$("#companyName").val().replace(/(^\s*)|(\s*$)/g,""),
	            	"companyType":companyType,
	            	"auditState":'2',
				},
	           	pageSize: checkedPagerListPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            callback: function(data){
	              	if(data.result != null && data.result != 'undefined' && data.result.length>0){
	            		var template = $.templates("#checkedImpl");
	                    var htmlOutput = template.render(data);
	                    $("#TBODY_CHECKED_REJECT").html(htmlOutput);
	            	}
	            }
    		});
    	}
    	
    });
    
    module.exports = checkedPagerListPager
});