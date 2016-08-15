define('app/jsp/score/scorelist', function (require, exports, module) {
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
    var ScoreListPager = Widget.extend({
    	
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5
    	},
    	//事件代理
    	events: {
    		//查询
            "click [id='BTN_QUERY']":"_queryPhoneBooks",
        },
    	//重写父类
    	setup: function () {
    		ScoreListPager.superclass.setup.call(this);
    		this._queryScoreList();
    	},
    	/**
    	 * 显示对话框
    	 */
    	_showDialog:function(id){
    		$('.eject-mask').fadeIn(100);
    		$('#'+id).slideDown(200);
    	},
    	
    	//获取供货商管理列表
    	_queryScoreList: function(){
    		var _this = this;
    		$("#pagination-ul").runnerPagination({
    			url: _base+"/score/getscorelist",
	 			method: "POST",
	 			dataType: "json",
	 			renderId:"TBODY_SCORELIST",
	            data : {
					tenantId: 'ch',
				},
	           	pageSize: ScoreListPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            callback: function(data){
	            	var count = data.count;
	              	if(data.result != null && data.result != 'undefined' && data.result.length>0){
	            		var template = $.templates("#scoreListImpl");
	                    var htmlOutput = template.render(data);
	                    $("TBODY_SCORELIST").html(htmlOutput);
	            	}
	            }
    		}); 
},
    	
    	/**
    	 * 编辑按钮事件
    	 */
    	_modifyTelData:function(telNo,telName,telMp){
    		$("#telName_"+telNo).html(
    				"<input id='telName_val_"+telNo+"' type='text' class='table-int-mini' value='"+telName+"' maxLength='24'>" +
    				"<div id='modify_name_error_"+telNo+"' class='ejecr-pos-border' style='display: none;'>" +
    				"<i class='icon-caret-up'></i></div>");
    		$("#telMp_"+telNo).html(
    				"<input id='telMp_val_"+telNo+"' type='text' class='table-int-mini' value='"+telMp+"' maxLength='11'><input type='button' class='mail-btn' value='保存' onclick=\"pager._saveModifyTelData('"+telNo+"')\">" +
    				"<div id='modify_mp_error_"+telNo+"' class='ejecr-pos-border' style='display: none;'>" +
					"<i class='icon-caret-up'></i></div>");
    	}
    	
    });
    
    module.exports = ScoreListPager
});