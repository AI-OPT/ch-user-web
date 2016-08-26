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
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var MarginSettingPager = Widget.extend({
    	
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5
    	},
    	//事件代理
    	events: {
    		"click #saveSetting":"_saveSetting",
    		"blur #deposit":"_checkDeposit"
        },
    	//重写父类
    	setup: function () {
    		MarginSettingPager.superclass.setup.call(this);
    	},
    	
    	_saveSetting:function(){
    	var deposit=$("#deposit").val();
    	$.ajax({
			type:"post",
			url:_base+"/billing/savemarginsetting",
			dataType: "json",
			data:{"deposit":deposit,
				 "userId":userId },
	        success: function(data) {
	        	if(data.responseHeader.resultCode='000000'){
	        		$('#dialogContent').text('保存成功');
	    			$('#sureModal').modal({backdrop:"static",show:true});
	        	}
	            },
				error: function(error) {
					alert("error:"+ error);
				}
				});
    	},
    	_jump:function(){
    		window.location.href=_base+"/billing/billingpager";
    	},
    	_checkDeposit:function(){
    		var deposit = $("#deposit").val();
    		var pattern = "^[1-9]\d{15}$";
    		if(!deposit.match(pattern)){
    			$('#dialogContent2').text('数据类型不对');
    			$('#sureModal2').modal();
    			$('#deposit').val("");
    		}
    	}
    	
    });
    
    module.exports = MarginSettingPager
});