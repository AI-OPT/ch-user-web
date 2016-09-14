define('app/jsp/crm/shopStateList', function (require, exports, module) {
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
    var shopStateListPager = Widget.extend({
    	
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
    		shopStateListPager.superclass.setup.call(this);
    		this._queryGroupStateList();
    	},
    	
    	//获取企业管理列表
    	_queryGroupStateList: function(){
    		var _this = this;
    		$("#pagination-ul").runnerPagination({
    			url: _base+"/crm/getGroupStateList",
	 			method: "POST",
	 			dataType: "json",
	 			renderId:"TBODY_SHOPSTATE",
	            data : {
					tenantId: 'changhong',
					userType:'10'
				},
	           	pageSize: shopStateListPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            callback: function(data){
	              	if(data.result != null && data.result != 'undefined' && data.result.length>0){
	            		var template = $.templates("#shopStateListImpl");
	                    var htmlOutput = template.render(data);
	                    $("#TBODY_SHOPSTATE").html(htmlOutput);
	                    var result=data.result;
	                    for(var i=0;i<result.length;i++){
	                    	if(result[i].state=='10'){
	                    		//debugger;
	                    		$("#freeze_"+result[i].userId).show();
	                    		$("#thraw_"+result[i].userId).hide();
	                    		$("#cancel_"+result[i].userId).show();
	                    		$("#recovery_"+result[i].userId).hide();
	                    	}else if(result[i].state=='11'){
	                    		$("#freeze_"+result[i].userId).hide();
	                    		$("#thraw_"+result[i].userId).show();
	                    		$("#cancel_"+result[i].userId).show();
	                    		$("#recovery_"+result[i].userId).hide();
	                    	}else if(result[i].state=='12'){
	                    		$("#freeze_"+result[i].userId).hide();
	                    		$("#thraw_"+result[i].userId).hide();
	                    		$("#cancel_"+result[i].userId).hide();
	                    		$("#recovery_"+result[i].userId).show();
	                    	}
	                    }
	            	}
	            }
    		}); 
    	},

    	_toFreeze:function(userId){
    		var d = Dialog({
				title : '提示',
				content : '确定要冻结此账户?',
				icon:'warning',
				okValue : "确定",
				ok : function() {
					this.close;
					$.ajax({
		    			type:"post",
		    			url:_base+"/status/changeShopStatus",
		    			dataType: "json",
		    			data:{},
		    	        success: function(data) {
		    	        	if(data.responseHeader.resultCode='000000'){
		    	        		var d = Dialog({
		    	    				title : '提示',
		    	    				content : '保存成功',
		    	    				icon:'success',
		    	    				okValue : "确定",
		    	    				ok : function() {
		    	    					this.close;
		    	    					window.location.href=_base+"/billing/billingpager";
		    	    				}
		    	    			});
		    	    			d.show();
		    	        	}
		    	            },
		    				error: function(error) {
		    					var d = Dialog({
		    	    				title : '提示',
		    	    				content : '保存失败',
		    	    				icon:'fail',
		    	    				okValue : "确定",
		    	    				ok : function() {
		    	    					this.close;
		    	    					window.location.href=_base+"/billing/billingpager";
		    	    				}
		    	    			});
		    	    			d.show();
		    				}
		    				});
				}
			});
			d.show();
    	},
    	_toThraw:function(userId){
    		var d = Dialog({
				title : '提示',
				content : '确定要解冻此账户?',
				icon:'warning',
				okValue : "确定",
				ok : function() {
					this.close;
				}
			});
			d.show();
    	},
    	_toCancel:function(userId){
    		var d = Dialog({
				title : '提示',
				content : '确定要注销此账户?',
				icon:'warning',
				okValue : "确定",
				ok : function() {
					this.close;
				}
			});
			d.show();
    	},
    	_toRecovery:function(userId){
    		var d = Dialog({
				title : '提示',
				content : '确定要恢复此账户?',
				icon:'warning',
				okValue : "确定",
				ok : function() {
					this.close;
				}
			});
			d.show();
    	}
    	
    });
    
    module.exports = shopStateListPager
});