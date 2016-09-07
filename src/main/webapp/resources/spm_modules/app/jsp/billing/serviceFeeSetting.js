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
    					min:0,
    					max:999999999999999,
    					pattern:/^\+?(0|[1-9][0-9]*)$/
    					},
    				ratio: {
		    			required:true,
		    			pattern:/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/,
		    			min:0,
		    			max:100,
    		}
    			},
    			messages: {
    				rentFee: {
    					required:"服务费不能为空",
    					digits: "只能输入数字",
    					min:"最小值为{0}",
    					max:"最大值为{0}",
    					pattern:"数据格式不对"
    					},
    				ratio: {
    					required:"服务费不能为空",
    					pattern: "数据格式不对,支持小数点后2位",
    					min:"最小值为{0}",
    					max:"最大值为{0}",
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
    	
    	_saveSetting:function(){
    		var _this= this;
			//父类目
			var catArr = [];
			var hasError = false;
			var formValidator=_this._initValidate();
			formValidator.form();
			if(!$("#serviceFee").valid()){
				Dialog({
					title : '提示',
					content : '验证不通过',
					okValue : "确定",
					ok : function() {
						this.close;
					}
				}).showModal();
				return;
			}
			//debugger;
        	$.ajax({
    			type:"post",
    			url:_base+"/billing/saveservicesetting",
    			dataType: "json",
    			data:$("#serviceFee").serialize(),
    	        success: function(data) {
    	        	if(data.responseHeader.resultCode='000000'){
    	        		Dialog({
    						title : '提示',
    						content : "保存成功",
    						okValue : "确定",
    						ok : function() {
    							window.location.href=_base+"/billing/billingpager"
    							this.close;
    						}
    					}).showModal();
    	        	}
    	            },
    				error: function(error) {
    					Dialog({
    						title : '提示',
    						content : error,
    						okValue : "确定",
    						ok : function() {
    							this.close;
    						}
    					}).showModal();
    				}
    				});
    	},
    	_jump:function(){
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
