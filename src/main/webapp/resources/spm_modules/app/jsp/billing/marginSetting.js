define('app/jsp/billing/marginSetting', function (require, exports, module) {
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
    require("bootstrap/dist/js/bootstrap.min");
    require("app/util/jsviews-ext");
    
    require("opt-paging/aiopt.pagination");
    require("twbs-pagination/jquery.twbsPagination.min");
    
	require("jquery-validation/1.15.1/jquery.validate");
	require("app/util/aiopt-validate-ext");
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var marginSettingPager = Widget.extend({
    	
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5
    	},
    	//事件代理
    	events: {
    		"click #saveSetting":"_saveSetting",
        },
    	//重写父类
    	setup: function () {
    		marginSettingPager.superclass.setup.call(this);
    		var formValidator=this._initValidate();
    		$("input[type='text']").bind("focusout",function(){
				formValidator.element(this);
			});
    	},
    	
    	//初始化校验器
    	_initValidate:function(){
    		var formValidator=$("#depositForm").validate({
    			rules: {
    				depositBalance: {
    					required:true,
    					digits:true,
    					maxlength:12,
    					min:1,
    					max:999999999999999,
    					pattern:/^\+?(0|[1-9][0-9]*)$/
    					}
    			},
    			messages: {
    				depositBalance: {
    					required:"保证金不能为空",
    					digits:"请输入大于0的数字",
    					maxlength:"最多输入12位",
    					min:"最小值为{0}",
    					max:"最大值为{0}",
    					pattern:"请输入大于0的数字"
    					}
    			},
    			errorPlacement: function (error, element) {
    				if (element.hasClass("input-group")) {
    					error.appendTo(element.parent());
                  }
                  else
                	  error.appendTo(element.parent());
    			}
    		});
    		
    		return formValidator;
    	},
    	
    	_saveSetting:function(){
    		var _this= this;
			//父类目
			var catArr = [];
			var hasError = false;
			var formValidator=_this._initValidate();
			formValidator.form();
			if(!$("#depositForm").valid()){
				return;
			}
			$.ajax({
			type:"post",
			processing: true,
			url:_base+"/billing/savemarginsetting",
			dataType: "json",
			data:$("#depositForm").serialize(),
	        success: function(data) {
	        	alert(JSON.stringify(data));
	        	if(data.responseHeader.resultCode=='000000'){
	        		var d = Dialog({
	    				title : '提示',
	    				content : '保存成功',
	    				icon:'success',
	    				closeIconShow:false,
	    				okValue : "确定",
	    				ok : function() {
	    					this.close;
	    					window.location.href=_base+"/billing/billingpager";
	    				}
	    			});
	    			d.show();
	        	}else{
	        	var d = Dialog({
    				title : '提示',
    				content : '保存失败',
    				icon:'fail',
    				closeIconShow:false,
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
	    				closeIconShow:false,
	    				okValue : "确定",
	    				ok : function() {
	    					this.close;
	    					window.location.href=_base+"/billing/billingpager";
	    				}
	    			});
	    			d.show();
				}
				});
    	},
    	_hideInfo:function(){
    		$("#depositInfo").hide();
    	},
    	_viewInfo:function(){
    		$("#depositBalance-error").hide();
    		$("#depositInfo").show();
    	}
    });
    
    module.exports = marginSettingPager
});

function getEvent() {
    if (document.all) {
        return window.event; //for ie
    }
    func = getEvent.caller;
    while (func != null) {
        var arg0 = func.arguments[0];
        if (arg0) {
            if ((arg0.constructor == Event || arg0.constructor == MouseEvent) || (typeof (arg0) == "object" && arg0.preventDefault && arg0.stopPropagation)) {
                return arg0;
            }
        }
        func = func.caller;
    }
    return null;
}
function doit(){
    var ev = getEvent();
    if((ev.keyCode>=48&&ev.keyCode<=57)||ev.keyCode==8) 
    	return true;
    return false;
}

function backup(){
	window.location.href=_base+"/billing/billingpager";
}