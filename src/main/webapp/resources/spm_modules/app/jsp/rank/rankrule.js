define('app/jsp/rank/rankrule', function (require, exports, module) {
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
    var RankRulePager = Widget.extend({
    	
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
    		RankRulePager.superclass.setup.call(this);
    		this._initTable();
    	},
    	
    	_initTable:function(){
    		$("#TBODY_RANKRULE").html();
    		var count = document.getElementById("rankRegion").value;
    		if(count==null||count=="")
    			count=5;
    		//I am drunk
    		var count_=count-1;
    		var htmlOutput ="<tr><td><p class='f-14' style='font-weight:400;padding-right:89px;'>等级 1: 0 - <input type='hidden' name='minFee' value='0'><input type='hidden' value='1' name='list[0].rank'><input class='int-text int-mini' name='list[0].maxFee' type='text'>元</p></td>";
             htmlOutput+="<td><p class='f-14'>等级名称 :  <input class='int-text int-small' name='list[0].rankName' type='text'></p></td>";
             htmlOutput+="<td><p class='f-14'>图片名称 :  <input class='int-text int-small' name='list[0].rankLogo' type='text'>&nbsp;&nbsp;&nbsp;<span class='btn-upload'>";
             htmlOutput+="<input type='button' class='btn-default btn-medium' value='浏览文件'/>";
             htmlOutput+="<input type='file' class='int-file'/></span></p></td></tr>";
    		if(count>2){
    		var json = '[';
    		for(var i=2;i<=count-1;i++){
    			json+='{id:{index:'+i+'}},';
    		}
    		json=json.substr(0,json.length-1);
    		json+=']';
    		json = eval("(" + json + ")");
    		var template = $.templates("#rankRuleImpl");
    		//渲染模版
            htmlOutput += template.render(json);
    		}
            htmlOutput+="<tr><td><p class='f-14' style='font-weight:400;padding-right:80px;'>等级 "+count+" :  <input class='int-text int-mini' name='list["+count_+"].minFee' type='text'> 元以上</p><input type='hidden' value='999999999999999' name='maxFee'><input type='hidden' value='1' name='list["+count+"].rank'></td>";
            htmlOutput+="<td><p class='f-14'>等级名称 :  <input class='int-text int-small' name='list["+count_+"].rankName' type='text'></p></td>";
            htmlOutput+="<td><p class='f-14'>图片名称 :  <input class='int-text int-small' name='list["+count_+"].rankLogo' type='text'>&nbsp;&nbsp;&nbsp;<span class='btn-upload'>";
            htmlOutput+="<input type='button' class='btn-default btn-medium' value='浏览文件'/>";
            htmlOutput+="<input type='file' class='int-file'/></span></p></td></tr>";
            $("#TBODY_RANKRULE").html(htmlOutput);
    	},
    	
    	_saveRule:function(){
    		$.ajax({
    			type:"post",
    			url:_base+"/rank/saverule",
    			dataType: "json",
    			data:$("#rankForm").serialize(),
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
    
    module.exports = RankRulePager
});