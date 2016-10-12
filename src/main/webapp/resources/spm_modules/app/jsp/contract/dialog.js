define('app/jsp/contract/dialog', function (require, exports, module) {
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
    var DialogPager = Widget.extend({
    	
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
    		DialogPager.superclass.setup.call(this);
    	},
    	_dialog:function(title,content,icon,okValue,url){
    		var d =new Dialog({
    			title : title,
    			content : content,
    			icon:icon,
    			closeIconShow:false,
    			okValue : okValue,
    			ok : function() {
    				this.close;
    				window.location.href=url;
    			}
    		});
    		d.show();
    	}
    });
    
    module.exports = DialogPager
});
