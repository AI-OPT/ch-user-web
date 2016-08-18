define('app/jsp/rank/rankrule-edit', function (require, exports, module) {
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
    var RankRuleEditPager = Widget.extend({
    	
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5
    	},
    	//事件代理
    	events: {
    		"change #rankRegion":"_initTable",
    		"click #saveRule":"_saveRule",
        },
    	//重写父类
    	setup: function () {
    		RankRuleEditPager.superclass.setup.call(this);
    		this._initTable();
    	},
    	
    	_initTable:function(){
    		$("#TBODY_RANKRULE").html();
    		var index = document.getElementById("rankRegion").value;
    		if(index==null||index=="")
    			index=5;
    		var json = '[';
    		for(var i=1;i<=index;i++){
    			json+='{id:{index:'+i+'}},';
    		}
    		json=json.substr(0,json.length-1);
    		json+=']';
    		json = eval("(" + json + ")");
    		var template = $.templates("#rankRuleImpl");
            var htmlOutput = template.render(json);
            $("#TBODY_RANKRULE").html(htmlOutput);
    	},
    	
    	_saveRule:function(){
    		$.ajax({
    			type:"post",
    			url:_base+"/rank/saverank",
    			dataType: "json",
    			data:$("#rankRule").serialize(),
    	        success: function(data) {
    	        	if(data.responseHeader.resultCode='000000'){
    	        	var dialog = Dialog({
    					title : '提示',
    					content : "评价成功",
    					okValue : "确定",
    					ok : function() {
    						this.close;
    						window.location.href=_base+"/rank/rankrule";
    					}
    				});
    	        	dialog.show();
    	        	}
    	            },
    				error: function(error) {
    					alert("error:"+ error);
    				}
    				});
    	}
    	
    });
    
    module.exports = RankRuleEditPager
});