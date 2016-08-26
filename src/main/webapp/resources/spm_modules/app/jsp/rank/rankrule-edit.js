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
    require("bootstrap/dist/js/bootstrap.min");
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
    		"change #rankRegion":"_changeTable",
    		"click #updateRule":"_updateRankRule",
    		"click #toEdit":"_toEdit"
        },
    	//重写父类
    	setup: function () {
    		RankRuleEditPager.superclass.setup.call(this);
    		this._initView();
    		this._initTable();
    	},
    	
    	_initView:function(){
    		$("#TBODY_VIEW").html();
    		var template = $.templates("#rankRuleViewImpl");
    		//var resultView = result;
    		var resultView = JSON.parse(JSON.stringify(eval(result)));
    		var count = resultView.length;
    		var count_=count-1;
    		//第一行
    		var htmlOutput = "<tr><td><p class='f-14' style='font-weight:400;'>等级1 :  0 - "+resultView[0].maxFee+"元</p></td>";
    		htmlOutput+="<td><p class='f-14' style='font-weight:400;'>等级名称:  "+resultView[0].rankName+"</p></td>";
    		htmlOutput+="<td><p class='f-14' style='font-weight:400;'>图片名称:  "+nameMap[1]+"<image src='"+urlMap[1]+"' height='80' width='100'/></p></td></tr>";
    		//最后一行
    		var htmlOutputEnd = "<tr><td><p class='f-14' style='font-weight:400;'>等级"+count+" :  "+resultView[count_].minFee+"元以上</p></td>";
    		htmlOutputEnd+="<td><p class='f-14' style='font-weight:400;'>等级名称:  "+resultView[count_].rankName+"</p></td>";
    		htmlOutputEnd+="<td><p class='f-14' style='font-weight:400;'>图片名称:  "+nameMap[count]+"<image src='"+urlMap[count]+"' height='80' width='100'/></p></td></tr>";
    		delete resultView[0];
    		delete resultView[count_];
    		if(count>2)
            htmlOutput += template.render(resultView);
    		htmlOutput+=htmlOutputEnd;
            $("#TBODY_VIEW").html(htmlOutput);
            for(var t=2;t<count;t++){
            	document.getElementById("imgView"+t).src=urlMap[t];
            	document.getElementById("custName"+t).innerHTML =nameMap[t];
            }
    	},
    	
    	_initTable:function(){
    		$("#TBODY_RANKRULE").html();
    		var template = $.templates("#rankRuleInitImpl");
    		var count = result.length;
    		var count_ = count-1;
    		//第一行
    		var htmlOutput ="<tr><td><p class='f-14' style='font-weight:400;'>等级 1: 0 - <input type='hidden' name='minFee' id='min1' value='0'><input type='hidden' value='1' name='list[0].rank'><input class='int-text int-mini' name='list[0].maxFee' id='max1' value='"+result[0].maxFee+"' type='text' onblur='"+"pager._changeValue(1)'>元</p></td>";
            htmlOutput+="<td><p class='f-14'>等级名称 :  <input class='int-text int-small' name='list[0].rankName' type='text' value='"+result[0].rankName+"'></p></td>";
            htmlOutput+="<td><p class='f-14'>图片名称 :  <input class='int-text int-small' name='list[0].rankLogo' type='text' value='"+nameMap[1]+"' id='custName1'>&nbsp;&nbsp;&nbsp;<span class='btn-upload'>";
            htmlOutput+="<input type='button' class='btn-default btn-medium' value='浏览文件'/>";
            htmlOutput+="<input type='file' class='int-file' id='img1' name='img1' onchange=\""+"pager._imgName('1')\"/></span></p></td></tr>";
            //最后一行
            var htmlOutputEnd ="<tr><td><p class='f-14' style='font-weight:400;'>等级 "+count+" :  <input class='int-text int-mini' name='list["+count_+"].minFee' id='min"+count+"' type='text' value='"+result[count_].minFee+"' readonly='readonly'> 元以上</p><input type='hidden' value='999999999999999' name='maxFee'><input type='hidden' value='"+count+"' id='max"+count+"' name='list["+count_+"].rank' onblur='"+"changeValue("+count+")'></td>";
    		htmlOutputEnd+="<td><p class='f-14'>等级名称 :  <input class='int-text int-small' name='list["+count_+"].rankName' type='text' value='"+result[count_].rankName+"'></p></td>";
    		htmlOutputEnd+="<td><p class='f-14'>图片名称 :  <input class='int-text int-small' name='list["+count_+"].rankLogo' type='text' value='"+nameMap[count]+"' id='custName"+count+"'>&nbsp;&nbsp;&nbsp;<span class='btn-upload'>";
    		htmlOutputEnd+="<input type='button' class='btn-default btn-medium' value='浏览文件'/>";
    		htmlOutputEnd+="<input type='file' class='int-file' id='img"+count+"' name='img"+count+"' onchange=\""+"pager._imgName("+count+")\"/></span></p></td></tr>";
            delete result[0];
            delete result[count_];
    		if(count>2)
				htmlOutput += template.render(result);
	    		htmlOutput+=htmlOutputEnd;
	            $("#TBODY_RANKRULE").html(htmlOutput);
	    		$("#rankRegion").val(rank);
				for(var t=2;t<count;t++){
					document.getElementById("custTab"+t).value =nameMap[t];	
    		}
    	},
    	
    	_changeTable:function(){
    		$("#TBODY_RANKRULE").html();
    		var count = document.getElementById("rankRegion").value;
    		if(count==null||count=="")
    			count=result[result.length-1].rank;
    		//I am drunk
    		var count_=count-1;
    		var htmlOutput ="<tr><td><p class='f-14' style='font-weight:400;'>等级 1: 0 - <input type='hidden' name='minFee' id='min1' value='0'><input type='hidden' value='1' name='list[0].rank'><input class='int-text int-mini' name='list[0].maxFee' type='text' id='max1' onblur='"+"pager._changeValue(1)'>元</p></td>";
             htmlOutput+="<td><p class='f-14'>等级名称 :  <input class='int-text int-small' name='list[0].rankName' type='text'></p></td>";
             htmlOutput+="<td><p class='f-14'>图片名称 :  <input class='int-text int-small' name='list[0].rankLogo' type='text' id='rankLogo1' readonly='readonly'>&nbsp;&nbsp;&nbsp;<span class='btn-upload'>";
             htmlOutput+="<input type='button' class='btn-default btn-medium' value='浏览文件'/>";
             htmlOutput+="<input type='file' class='int-file' id='img1' name='img1' onchange=\""+"pager._imgName('1')\"/></span></p></td></tr>";
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
            htmlOutput+="<tr><td><p class='f-14' style='font-weight:400;'>等级 "+count+" :  <input class='int-text int-mini' name='list["+count_+"].minFee' type='text' id=min"+count+" readonly='readonly'> 元以上</p><input type='hidden' value='999999999999999' name='maxFee'><input type='hidden' value='"+count+"' name='list["+count_+"].rank'></td>";
            htmlOutput+="<td><p class='f-14'>等级名称 :  <input class='int-text int-small' name='list["+count_+"].rankName' type='text'></p></td>";
            htmlOutput+="<td><p class='f-14'>图片名称 :  <input class='int-text int-small' name='list["+count_+"].rankLogo' type='text' id='custName"+count+"' readonly='readonly'>&nbsp;&nbsp;&nbsp;<span class='btn-upload'>";
            htmlOutput+="<input type='button' class='btn-default btn-medium' value='浏览文件'/>";
            htmlOutput+="<input type='file' class='int-file' id='img"+count+"' name='img"+count+"' onchange=\""+"pager._imgName("+count+")\"/></span></p></td></tr>";
            $("#TBODY_RANKRULE").html(htmlOutput);
            $("#rankRegion").val(count);
    	},
    	
    	_toEdit:function(){
    		$("#view").hide();
    		$("#edit").show();
    	},
    	
    	_updateRankRule:function(){
    		$("#rankRule").submit();
    	},
    	
    	//获取上传图片名
    	_imgName:function(index) {
    		 var img = document.getElementById('img'+index).files;
    		document.getElementById('custName'+index).value=img[0].name;
    	},
    	
    	_changeValue:function(index){
    		var maxIndex = document.getElementById('max'+index).value;
    		var minIndex = document.getElementById('min'+index).value;
    		if(maxIndex<=minIndex){
    			$("#dialogContent").text('等级区间不正确');
    			$("#sureModal").modal();
	        	document.getElementById('max'+index).value="";
    		}else{
    		document.getElementById('min'+(parseInt(index)+1)).value=maxIndex;
    		}
    	}
    	
    });
    
    module.exports = RankRuleEditPager
});
