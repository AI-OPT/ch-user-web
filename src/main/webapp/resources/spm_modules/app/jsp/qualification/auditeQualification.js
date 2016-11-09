define('app/jsp/qualification/auditeQualification', function (require, exports, module) {
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
    require("bootstrap/dist/js/bootstrap");
    require("app/util/jsviews-ext");
    require("opt-paging/aiopt.pagination");
    require("twbs-pagination/jquery.twbsPagination.min");
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var auditeQualificationPager = Widget.extend({
    	
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5
    	},
    	//事件代理
    	events: {
    		"click #scoreListForm":"_getScoreList",
    		"blur #reason":"_checkReason",
        },
    	//重写父类
    	setup: function () {
    		auditeQualificationPager.superclass.setup.call(this);
    	},
    	
    	_backup:function(url){
    		window.location.href=url;
    	},
    	
    	_passAudit:function(userId,url){
    		this._checkReason();
    		if($("#reasonFlag").val()=='0'){
    			return false;
    		}
    		var d = Dialog({
				title : '提示',
				content : '审核通过此资质信息吗？',
				icon:'warning',
				closeIconShow:false,
				okValue : "确定",
				ok : function() {
					this.close;
					$.ajax({
		    			type:"post",
		    			url:_base+"/qualification/updateAudit",
		    			dataType: "json",
		    			data:{
		    				"auditState":"2",
		    				"companyId":userId,
		    				"reason":$("#reason").val()
		    			},
		    	        success: function(data) {
		    	        	if(data.responseHeader.resultCode=='000000'){
		    	        		var d = Dialog({
		    	    				title : '提示',
		    	    				content : '保存成功',
		    	    				icon:'success',
		    	    				closeIconShow:false,
		    	    				okValue : "确定",
		    	    				ok : function() {
		    	    					this.close;
		    	    					window.location.href=url+'?id='+Math.random();
		    	    				}
		    	    			});
		    	    			d.show();
		    	        	}else
		    	        		var d = Dialog({
		    	    				title : '提示',
		    	    				content : '保存失败',
		    	    				icon:'fail',
		    	    				closeIconShow:false,
		    	    				okValue : "确定",
		    	    				ok : function() {
		    	    					this.close;
		    	    					window.location.href=url;
		    	    				}
		    	    			});
		    	    			d.show();
		    	            },
		    				error: function(error) {
		    					var d = Dialog({
		    	    				title : '提示',
		    	    				content : '网络错误:'+JSON.stringify(error),
		    	    				icon:'fail',
		    	    				closeIconShow:false,
		    	    				okValue : "确定",
		    	    				ok : function() {
		    	    					this.close;
		    	    					window.location.href=url;
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
		_checkReason:function(){
			var remark = $("#reason").val();
			if(remark.length==0){
				$("#reasonErrMsg").show();
				$("#reasonText").show();;
				$("#reasonText").text("审核原因不能为空");
				$("#reasonFlag").val("0");
				return;
			}else if(remark.length>0&&remark.length<=256){
				$("#reasonErrMsg").hide();
				$("#reasonText").hide();
				$("#reasonText").val("")
				$("#reasonFlag").val("1");
				
			}else{
				$("#reasonErrMsg").show();
				$("#reasonText").show();;
				$("#reasonText").text("1-256位字符");
				$("#reasonFlag").val("0");
				return;
			}
		},
		
    	_rejectAudit:function(userId,url){
    		this._checkReason();
    		if($("#reasonFlag").val()=='0'){
    			return;
    		}
    		var d = Dialog({
				title : '提示',
				content : '审核拒绝此资质信息吗？',
				icon:'warning',
				closeIconShow:false,
				okValue : "确定",
				ok : function() {
					this.close;
					$.ajax({
		    			type:"post",
		    			url:_base+"/qualification/updateAudit",
		    			dataType: "json",
		    			data:{
		    				"auditState":'3',
		    				"companyId":userId,
		    				"reason":$("#reason").val()
		    			},
		    	        success: function(data) {
		    	        	if(data.responseHeader.resultCode=='000000'){
		    	        		var d = Dialog({
		    	    				title : '提示',
		    	    				content : '保存成功',
		    	    				icon:'success',
		    	    				closeIconShow:false,
		    	    				okValue : "确定",
		    	    				ok : function() {
		    	    					this.close;
		    	    					window.location.href=url;
		    	    				}
		    	    			});
		    	    			d.show();
		    	        	}else
		    	        		var d = Dialog({
		    	    				title : '提示',
		    	    				content : '保存失败',
		    	    				icon:'fail',
		    	    				closeIconShow:false,
		    	    				okValue : "确定",
		    	    				ok : function() {
		    	    					this.close;
		    	    					window.location.href=url;
		    	    				}
		    	    			});
		    	    			d.show();
		    	            },
		    				error: function(error) {
		    					var d = Dialog({
		    	    				title : '提示',
		    	    				content : '网络错误:'+JSON.stringify(error),
		    	    				icon:'fail',
		    	    				closeIconShow:false,
		    	    				okValue : "确定",
		    	    				ok : function() {
		    	    					this.close;
		    	    					window.location.href=url;
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
    	}
    	
    });
    
    module.exports = auditeQualificationPager
});