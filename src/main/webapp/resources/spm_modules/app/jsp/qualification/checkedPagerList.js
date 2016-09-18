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
    		this._getInitList();
    	},
    	
    	_updateAudit:function(auditCode){
    		var d = Dialog({
				title : '提示',
				content : '审核通过此资质信息吗？',
				icon:'warning',
				okValue : "确定",
				ok : function() {
					this.close;
					$.ajax({
		    			type:"post",
		    			url:_base+"/status/updateAudit",
		    			dataType: "json",
		    			data:{
		    				"openId":'1',
		    				"auditStat":"auditCode",
		    				"companyId":'ac_ew23'
		    			},
		    	        success: function(data) {
		    	        	if(data.responseHeader.resultCode='000000'){
		    	        		var d = Dialog({
		    	    				title : '提示',
		    	    				content : '保存成功',
		    	    				icon:'success',
		    	    				okValue : "确定",
		    	    				ok : function() {
		    	    					this.close;
		    	    					window.location.href=_base+"/crm/shopStatePager";
		    	    				}
		    	    			});
		    	    			d.show();
		    	        	}else
		    	        		var d = Dialog({
		    	    				title : '提示',
		    	    				content : '保存失败',
		    	    				icon:'fail',
		    	    				okValue : "确定",
		    	    				ok : function() {
		    	    					this.close;
		    	    					window.location.href=_base+"/crm/shopStatePager";
		    	    				}
		    	    			});
		    	    			d.show();
		    	            },
		    				error: function(error) {
		    					var d = Dialog({
		    	    				title : '提示',
		    	    				content : '网络错误:'+JSON.stringify(error),
		    	    				icon:'fail',
		    	    				okValue : "确定",
		    	    				ok : function() {
		    	    					this.close;
		    	    					window.location.href=_base+"/crm/shopStatePager";
		    	    				}
		    	    			});
		    	    			d.show();
		    				}
		    				});
				}
			});
			d.show();
    	},

    	_getInitList:function(){
    		var _this = this;
    		$("#pagination-ul").runnerPagination({
    			url: _base+"/qualification/getCheckedList",
	 			method: "POST",
	 			dataType: "json",
	 			renderId:"TBODY_CHECKED",
	            data : {
					"tenantId": 'changhong',
				},
	           	pageSize: checkedPagerListPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            callback: function(data){
	              	if(data.result != null && data.result != 'undefined' && data.result.length>0){
	            		var template = $.templates("#checkedImpl");
	                    var htmlOutput = template.render(data);
	                    $("#TBODY_CHECKED").html(htmlOutput);
	            	}
	            }
    		}); 
    	},
    	
		_toViewPage:function(userId){
			window.location.href = _base+'/qualification/toViewPage?userId='+userId;
		},
		
		_getList:function(){
    		var _this = this;
    		$("#pagination-ul").runnerPagination({
    			url: _base+"/qualification/getCheckedList",
	 			method: "POST",
	 			dataType: "json",
	 			renderId:"TBODY_CHECKED",
	            data : {
					"tenantId": 'changhong',
				},
	           	pageSize: checkedPagerListPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            callback: function(data){
	              	if(data.result != null && data.result != 'undefined' && data.result.length>0){
	            		var template = $.templates("#checkedImpl");
	                    var htmlOutput = template.render(data);
	                    $("#TBODY_CHECKED").html(htmlOutput);
	            	}
	            }
    		}); 
    	}
    	
    });
    
    module.exports = checkedPagerListPager
});