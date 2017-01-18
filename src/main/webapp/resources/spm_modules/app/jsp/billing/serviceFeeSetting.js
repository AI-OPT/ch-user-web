define('app/jsp/billing/serviceFeeSetting', function (require, exports, module) {
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
    var serviceFeeSettingPager = Widget.extend({
    	
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5
    	},
    	//事件代理
    	events: {
    		"click #saveSetting":"_saveSetting",
    		"blur #ratio":"_checkRatio",
        },
    	//重写父类
    	setup: function () {
    		serviceFeeSettingPager.superclass.setup.call(this);
    		var formValidator=this._initValidate();
    		$("input[type='text']").bind("focusout",function(){
				formValidator.element(this);
			});
    	},
    	
    	//初始化校验器
    	_initValidate:function(){
    		var formValidator=$("#serviceFee").validate({
    			rules: {
    				rentFee: {
    					required:true,
    					digits:true,
    					maxlength:12,
    					min:1,
    					max:999999999999999,
    					pattern:/^\+?(0|[1-9][0-9]*)$/
    					}
    			},
    			messages: {
    				rentFee: {
    					required:"服务费不能为空",
    					digits: "请输入大于0的数字",
    					maxlength:"最多输入12位",
    					min:"请输入大于0的数字，最多输入12位",
    					max:"最大值为{0}",
    					pattern:"请输入大于0的数字，最多输入12位"
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
    	
    	_change:function(value,divId){
    		var radios = document.getElementsByName(value);
    		var radioValue="";
    		for(var i=0;i<radios.length;i++)
            {
                if(radios[i].checked==true)
                {
                	radioValue=radios[i].value;
                }
            }
    		if(radioValue==0){
    			//debugger;
    			document.getElementById(divId).style.display="";
    			if(divId=='payRent'){
    				$("#rentFee").attr("disabled",false);
    				$("#rentCycleType").attr("disabled",false);
    			}else if(divId=='payCycle')
    				$("#ratio").attr("disabled",false);
    		}else if(radioValue==1){
    			document.getElementById(divId).style.display="none";
    			if(divId=='payRent'){
    				$("#rentFee").attr("disabled",true);
    				$("#rentCycleType").attr("disabled",true);
    			}else if(divId=='payCycle')
    				$("#ratio").attr("disabled",true);
    	}
    	},
    	
    	_checkRatio:function(){
    		$("#ratioInfo").hide();
    		if($("#ratio").val()==null){
    			$("#ratioInfo").val("服务费不能为空");
    			$("#ratioInfo").show();
				return;
    		}
    		if(parseFloat($("#ratio").val())==0){
				$("#ratioInfo").show();
				return;
			}
    		if(parseFloat($("#ratio").val())>100){
    			$("#ratioInfo").val("最大值为100");
    			$("#ratioInfo").show();
    			return;
    		}
    		var reg = /^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/;
    		if(reg.test($("#ratio").val())!=true){
    			$("#ratioInfo").val("请输入大于0的数字，最多有两位小数");
    			$("#ratioInfo").show();
    			return;
    		}
    	},
    	
    	_saveSetting:function(){
    		var _this= this;
    		
			//父类目
			var catArr = [];
			var hasError = false;
			var formValidator=_this._initValidate();
			formValidator.form();
			if(!$("#serviceFee").valid()){
				return;
			}
			this._checkRatio();
			//debugger;
        	$.ajax({
    			type:"post",
    			url:_base+"/billing/saveservicesetting",
    			dataType: "json",
    			data:$("#serviceFee").serialize(),
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
    	    					window.location.href=_base+"/billing/billingpager";
    	    				}
    	    			});
    	    			d.show();
    	        	}else{
    	        		var d = Dialog({
    	    				title : '提示',
    	    				content : '保存失败',
    	    				icon:'fail',
    	    				okValue : "确定",
    	    				closeIconShow:false,
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
    	    				closeIconShow:false,
    	    				ok : function() {
    	    					this.close;
    	    					window.location.href=_base+"/billing/billingpager";
    	    				}
    	    			});
    	    			d.show();
    				}
    				});
    	},
    	_hideInfo:function(id){
    		$("#"+id).hide();
    	},
    	_viewInfo:function(id,errorId){
    		$("#"+errorId).hide();
    		$("#"+id).show();
    	}
    	
    });
    
    module.exports = serviceFeeSettingPager
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
    if((ev.keyCode>=48&&ev.keyCode<=57)||ev.keyCode==8||ev.keyCode==110) 
    	return true;
    return false;
}

function backup(){
	window.location.href=_base+"/billing/billingpager";
}
