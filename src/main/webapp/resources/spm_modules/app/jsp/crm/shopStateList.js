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
    		this._getInitList();
    	},
    	
    	//获取企业管理列表
    	_getInitList: function(){
    		var _this = this;
    		$("#info").html("");
    		$("#pagination-ul").runnerPagination({
    			url: _base+"/status/getList",
	 			method: "POST",
	 			processing: true,
	 			messageId:"showMessageDiv",
	 			dataType: "json",
	 			renderId:"TBODY_SHOPSTATE",
	            data : {
	            	"companyType":"2"
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
	                    	if(result[i].state=='0'){
	                    		//debugger;
	                    		$("#freeze_"+result[i].userId).show();
	                    		$("#thraw_"+result[i].userId).hide();
	                    		$("#cancel_"+result[i].userId).show();
	                    		$("#recovery_"+result[i].userId).hide();
	                    	}else if(result[i].state=='1'){
	                    		$("#freeze_"+result[i].userId).hide();
	                    		$("#thraw_"+result[i].userId).show();
	                    		$("#cancel_"+result[i].userId).show();
	                    		$("#recovery_"+result[i].userId).hide();
	                    	}else if(result[i].state=='2'){
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
				closeIconShow:false,
				okValue : "确定",
				ok : function() {
					this.close;
					$.ajax({
		    			type:"post",
		    			url:_base+"/status/updateStatus",
		    			dataType: "json",
		    			data:{
		    				"companyState":"1",
		    				"companyId":userId
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
				},
				cancelValue: '取消',
			    cancel: true //为true等价于function(){}
			});
			d.show();
    	},
    	_toThraw:function(userId){
    		var d = Dialog({
				title : '提示',
				content : '确定要解冻此账户?',
				icon:'warning',
				okValue : "确定",
				closeIconShow:false,
				ok : function() {
					this.close;
					$.ajax({
		    			type:"post",
		    			url:_base+"/status/updateStatus",
		    			dataType: "json",
		    			data:{
		    				"companyState":"0",
		    				"companyId":userId
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
				},
				cancelValue: '取消',
			    cancel: true //为true等价于function(){}
			});
			d.show();
    	},
    	_toCancel:function(userId){
    		var d = Dialog({
				title : '提示',
				content : '确定要注销此账户?',
				icon:'warning',
				closeIconShow:false,
				okValue : "确定",
				ok : function() {
					this.close;
					$.ajax({
		    			type:"post",
		    			url:_base+"/status/updateStatus",
		    			dataType: "json",
		    			data:{
		    				"companyState":"2",
		    				"companyId":userId
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
				},
				cancelValue: '取消',
			    cancel: true //为true等价于function(){}
			});
			d.show();
    	},
    	_toRecovery:function(userId){
    		var d = Dialog({
				title : '提示',
				content : '确定要恢复此账户吗?',
				icon:'warning',
				closeIconShow:false,
				okValue : "确定",
				ok : function() {
					this.close;
					$.ajax({
		    			type:"post",
		    			url:_base+"/status/updateStatus",
		    			dataType: "json",
		    			data:{
		    				"companyState":"0",
		    				"companyId":userId
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
				},
				cancelValue: '取消',
			    cancel: true //为true等价于function(){}
			});
			d.show();
    	},
    	
    	_getList:function(){
    		var _this = this;
    		$("#pagination-ul").runnerPagination({
    			url: _base+"/status/getList",
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	 			messageId:"showMessageDiv",
	 			renderId:"TBODY_SHOPSTATE",
	            data : {
	            	"username":$("#username").val().replace(/(^\s*)|(\s*$)/g,""),
					"companyName":$("#companyName").val().replace(/(^\s*)|(\s*$)/g,""),
					"companyType":"2"
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
	                    	if(result[i].state=='0'){
	                    		//debugger;
	                    		$("#freeze_"+result[i].userId).show();
	                    		$("#thraw_"+result[i].userId).hide();
	                    		$("#cancel_"+result[i].userId).show();
	                    		$("#recovery_"+result[i].userId).hide();
	                    	}else if(result[i].state=='1'){
	                    		$("#freeze_"+result[i].userId).hide();
	                    		$("#thraw_"+result[i].userId).show();
	                    		$("#cancel_"+result[i].userId).show();
	                    		$("#recovery_"+result[i].userId).hide();
	                    	}else if(result[i].state=='2'){
	                    		$("#freeze_"+result[i].userId).hide();
	                    		$("#thraw_"+result[i].userId).hide();
	                    		$("#cancel_"+result[i].userId).hide();
	                    		$("#recovery_"+result[i].userId).show();
	                    	}
	                    }
	            	}
	            }
    		}); 
    	}
    	
    });
    
    module.exports = shopStateListPager
});