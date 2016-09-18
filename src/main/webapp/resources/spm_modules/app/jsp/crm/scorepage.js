define('app/jsp/crm/scorepage', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("optDialog/src/dialog"),
    AjaxController = require('opt-ajax/1.0.0/index'),
    Calendar = require('arale-calendar/1.1.2/index');
    
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("bootstrap-paginator/bootstrap-paginator.min");
    require("bootstrap/dist/js/bootstrap.min");
    require("app/util/jsviews-ext");
    
	require("jquery-validation/1.15.1/jquery.validate");
	require("app/util/aiopt-validate-ext");
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var scorePagePager = Widget.extend({
    	
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5
    	},
    	//事件代理
    	events: {
    		"click #submitScore":"_submitScore"
        },
    	//重写父类
    	setup: function () {
    		scorePagePager.superclass.setup.call(this);
    		var formValidator=this._initValidate();
			$("input[type='text']").bind("focusout",function(){
				formValidator.element(this);
			});
    	},
    	
    	//初始化校验器
    	_initValidate:function(){
    		var formValidator=$("#scorePage").validate({
    			rules: {
    				1: {
    					required:true,
    					digits:true,
    					min:0,
    					max:50,
    					pattern:/^(0|[1-9]\d{0,9})$/
    					},
					2: {
    					required:true,
    					digits:true,
    					min:0,
    					max:10,
    					pattern:/^(0|[1-9]\d{0,9})$/
    					},
					3: {
    					required:true,
    					digits:true,
    					min:0,
    					max:20,
    					pattern:/^(0|[1-9]\d{0,9})$/
    					},
					4: {
    					required:true,
    					digits:true,
    					min:0,
    					max:20,
    					pattern:/^(0|[1-9]\d{0,9})$/
    					}
    			},
    			messages: {
    				1: {
    					required:"评分不能为空",
    					digits: "只能输入数字",
    					min:"最小值为{0}",
    					max:"最大值为{0}",
    					pattern:'格式不对'
    					},
    				2: {
    					required:"评分不能为空",
    					digits: "只能输入数字",
    					min:"最小值为{0}",
    					max:"最大值为{0}",
    					pattern:'格式不对'
    					},
        			
        			3: {
    					required:"评分不能为空",
    					digits: "只能输入数字",
    					min:"最小值为{0}",
    					max:"最大值为{0}",
    					pattern:'格式不对'
    					},
            		4: {
    					required:"评分不能为空",
    					digits: "只能输入数字",
    					min:"最小值为{0}",
    					max:"最大值为{0}",
    					pattern:'格式不对'
    					}
    			},
    			errorPlacement: function (error, element) {
                	  error.appendTo(element.parent().next());
    			}
    		});
    		
    		return formValidator;
    	},
    	
    	//提交供货商评价
    	_submitScore: function(){
    		var _this= this;
			//父类目
			var catArr = [];
			var hasError = false;
			var formValidator=_this._initValidate();
			formValidator.form();
			if(!$("#scorePage").valid()){
			var d = Dialog({
					title : '提示',
					content : '验证不通过',
					icon:'fail',
					okValue : "确定",
					ok : function() {
						this.close;
					}
				});
				d.show();
				return;
			}
    	   $.ajax({
			type:"post",
			url:_base+"/score/savescore",
			dataType: "json",
			data:$("#scorePage").serialize(),
			beforeSend:function(){
				document.getElementById("submitScore").disabled=true;
			},
	        success: function(data) {
	        	if(data.responseHeader.resultCode='000000'){
	        		window.location.href=_base+"/score/scorelist";
	        	}
	            },
				error: function(error) {
					var d = Dialog({
						title : '提示',
						content : '错误信息:'+error,
						icon:'fail',
						okValue : "确定",
						ok : function() {
							this.close;
							window.location.href=_base+"/score/scorelist";
						}
					});
					d.show();
				}
				});
    	}
    });
    
    module.exports = scorePagePager
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
