define('app/jsp/rank/rankpagesuc', function (require, exports, module) {
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
    
    // 实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    // 定义页面组件类
    var RankPagePager = Widget.extend({
    	
    	// 属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5
    	},
    	// 事件代理
    	events: {
        },
    	// 重写父类
    	setup: function () {
    		RankPagePager.superclass.setup.call(this);
    		this._init();
    	},
    	
    	_init:function(){
    		var dialog = Dialog({
    			title : '提示',
    			content : "操作成功",
    			okValue : "确定",
    			ok : function() {
    				this.close;
    				window.location.href=_base+"/rank/rankrule";
    			}
    		});
    		dialog.show();
    	}
    	
    });
    
    module.exports = RankPagePager
});

