define('app/jsp/crm/rankrule-edit', function (require, exports, module) {
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
    		"click #toEdit":"_toEdit",
    		"click #backup":"_backup"
        },
    	//重写父类
    	setup: function () {
    		RankRuleEditPager.superclass.setup.call(this);
    		this._init();
    	},
    	
    	_init:function(){
    		$.ajax({
    			type:"post",
    			processing: true,
    			url:_base+"/rank/getInitData",
    			dataType: "json",
    			data:{},
    	        success: function(data) {
    	        	if(data.responseHeader.resultCode='000000'){
    					//initView
    					var result=data.data.result;
    					//debugger;
    					var urlMap=data.data.urlMap;
    					var nameMap=data.data.nameMap;
    					var idpsMap=data.data.idpsMap;
    					var rank=data.data.rank;
    					var periodType=data.data.periodType;
    					var data=data.data.middleData;
    					$("#TBODY_VIEW").html();
    		    		var count = result.length;
    		    		var count_=count-1;
    		    		//第一行
    		    		var htmlOutput = "<tr><td><p class='f-14' style='font-weight:400;'>等级1:0 至 "+result[0].maxScore+"分</p></td>";
    		    		htmlOutput+="<td><p class='f-14' style='font-weight:400;'> "+result[0].rankName+"</p></td>";
    		    		htmlOutput+="<td><p><image src='"+urlMap[1]+"' height='80px' width='80px'/></p></td></tr>";
    		    		//最后一行
    		    		var htmlOutputEnd = "<tr><td><p class='f-14' style='font-weight:400;'>等级"+count+":"+result[count_].minScore+"分以上</p></td>";
    		    		htmlOutputEnd+="<td><p class='f-14' style='font-weight:400;'> "+result[count_].rankName+"</p></td>";
    		    		htmlOutputEnd+="<td><p><image src='"+urlMap[count]+"' height='80px' width='80px'/></p></td></tr>";
    		    		if(count>2){
    		    			var template = $.templates("#rankRuleViewImpl");
    		    			var middleOutput=template.render(data);
    		    			htmlOutput += middleOutput;
    		    		}
    		    		htmlOutput+=htmlOutputEnd;
    		            $("#TBODY_VIEW").html(htmlOutput);
    		            for(var t=2;t<count;t++)
    		            	$("#imgView"+t).attr("src",urlMap[t]);
    		            
    		            //initTable
    		            $("#TBODY_RANKRULE").html();
    		    		var count = result.length;
    		    		var count_ = count-1;
    		    		//第一行
    		    		var htmlOutput ="<tr><td class='text-l pl-10' style='white-space:nowrap'><p class='f-14' style='font-weight:400;'>等级1:<input type='text' value='0' class='int-text int-mini' id='min1' style='border: none;background:none;font-weight:400;' name='list[0].minScore' readonly='readonly'>至<input type='hidden' value='1' name='list[0].rank'><input class='int-text int-mini' name='list[0].maxScore' id='max1' value='"+result[0].maxScore+"' type='text' onblur='"+"pager._changeValue(1)' onfocus='"+"pager._rankView(1)' maxlength='12' onkeydown='return doit()' style=''>分<span id='rankInfo1' style='color:red;display:none'>1-12位整数</span><input type='text' id='rankMsg1' style='display:none;color:red'></p></td>";
    		            htmlOutput+="<td class='text-l pl-10' style='white-space:nowrap'><p class='f-14'><input class='int-text int-mini' name='list[0].rankName' type='text' value='"+result[0].rankName+"' id='name1' onblur='"+"pager._valideName(1)' onfocus='"+"pager._nameView(1);' maxlength='12' ><span id='nameInfo1' style='color:red;display:none'>1-12位字符</span><input type='text' style='display:none;color:red' id='nameMsg1'></p></td>";
    		            htmlOutput+="<td class='text-l pl-10' style='white-space:nowrap'><p class='f-14'><b class='red'>*</b>图片名称:<span class='btn-upload'><input class='int-text int-mini' disabled='disabled' type='text' value='"+nameMap[1]+"' id='picName1' style='border:none;background:none;font-weight:400;'><input type='hidden' name='list[0].rankLogo' id='rankLogo1' value='idpsMap[1]'><input type='hidden' name='rankName1' id='rankName1' value='nameMap[1]'>";
    		            htmlOutput+="&nbsp;&nbsp;<input type='button' class='btn-primary btn-default btn-medium' value='浏览文件'/>";
    		            htmlOutput+="<input type='file' class='int-file1' id='img1' name='img1' onchange=\""+"pager._imgName('1')\"/></span><span id='imgInfo1' style='color:red;'>gif/jpg/jpeg/png</span><input type='text' id='picErr1' style='display:none;color:red;font-size:14px'></p></td></tr>";
    		            //最后一行
    		            var htmlOutputEnd ="<tr><td class='text-l pl-10' style='white-space:nowrap'><p class='f-14' style='font-weight:400;'>等级"+count+":<input class='int-text int-mini' name='list["+count_+"].minScore' id='min"+count+"' type='text' value='"+result[count_].minScore+"' readonly='readonly' style='border: none;background:none;font-weight:400;' maxlength='12' onkeydown='return doit()' style=''>分以上</p><input type='hidden' value='999999999999999' name='maxScore' id='max"+count+"'><input type='hidden' value='"+count+"' name='list["+count_+"].rank' onblur='"+"changeValue("+count+")'></td>";
    		    		htmlOutputEnd+="<td class='text-l pl-10' style='white-space:nowrap'><p class='f-14'><input class='int-text int-mini' name='list["+count_+"].rankName' type='text' value='"+result[count_].rankName+"' id='name"+count+"' onblur=\""+"pager._valideName('"+count+"')\" onfocus=\""+"pager._nameView('"+count+"')\" maxlength='12'><span id='nameInfo"+count+"' style='color:red;display:none'>1-12位字符</span><input type='text' id='nameMsg"+count+"' style='display:none;color:red'></p></td>";
    		    		htmlOutputEnd+="<td class='text-l pl-10' style='white-space:nowrap'><p class='f-14'><b class='red'>*</b>图片名称:<span class='btn-upload'><input class='int-text int-mini' disabled='disabled' type='text' value='"+nameMap[count]+"' id='picName"+count+"' style='border:none;background:none;font-weight:400;'><input type='hidden' name='list["+count_+"].rankLogo' id='rankLogo"+count+"' value='idpsMap["+count+"]'><input type='hidden' name='rankName"+count+"' id='rankName"+count+"' value='nameMap["+count+"]'>";
    		    		htmlOutputEnd+="&nbsp;&nbsp;<input type='button' class='btn-primary btn-default btn-medium' value='浏览文件'/>";
    		    		htmlOutputEnd+="<input type='file' class='int-file1' id='img"+count+"' name='img"+count+"' onchange=\""+"pager._imgName("+count+")\"/></span><span id='imgInfo"+count+"' style='color:red;'>gif/jpg/jpeg/png</span><input type='text' id='picErr"+count+"' style='display:none;color:red;font-size:14px'></p></td></tr>";
    		    		if(count>2){
    		    			var template = $.templates("#rankRuleInitImpl");
    		    			var middleOutput=template.render(data);
    		    			htmlOutput += middleOutput;
    		    		}
    			    		htmlOutput+=htmlOutputEnd;
    			            $("#TBODY_RANKRULE").html(htmlOutput);
    			    		$("#rankRegion").val(count);
    						for(var t=2;t<count;t++){
    							$("#picName"+t).val(nameMap[t]);
    							$("#rankLogo"+t).val(idpsMap[t]);
    							$("#rankName"+t).val(nameMap[t]);
    		    		}
    				}
    	            },
    				error: function(error) {
    					var d = Dialog({
    	    				title : '提示',
    	    				content : '查询失败',
    	    				icon:'fail',
    	    				closeIconShow:false,
    	    				okValue : "确定",
    	    				ok : function() {
    	    					this.close;
    	    				}
    	    			});
    	    			d.show();
    				}
    				});
    	},
    	
    	_changeTable:function(){
    		$("#TBODY_RANKRULE").html();
    		var count = $("#rankRegion").val();
    		if(count==null||count=="")
    			count=result[result.length-1].rank;
    		var count_=count-1;
    		var htmlOutput ="<tr><td class='text-l pl-10' style='white-space:nowrap'><p class='f-14' style='font-weight:400;'>等级1:<input class='int-text int-mini' readonly='readonly' type='text' name='list[0].minScore' id='min1' value='0' style='border: none;background:none;font-weight:400;'><input type='hidden' name=list[0].rank value='1'>至<input class='int-text int-mini' name='list[0].maxScore' id='max1' type='text' onblur=\""+"pager._changeValue('1')"+"\" onfocus=\""+"pager._rankView('1')"+"\" maxlength='12' onkeydown='return doit()' style=''>分<span id='rankInfo1' style='color:red;display:none'>1-12位整数</span><input type='text' style='display:none;color:red' id='rankMsg1'></p></td>";
            htmlOutput+="<td class='text-l pl-10' style='white-space:nowrap'><p class='f-14'><input class='int-text int-mini' name='list[0].rankName' id='name1' type='text' onblur=\""+"pager._valideName('1')"+"\" onfocus=\""+"pager._nameView('1')"+"\" maxlength='12'><span id='nameInfo1' style='color:red;display:none'>1-12位字符</span><input type='text' id='nameMsg1' style='display:none;color:red'></p></td>";
            htmlOutput+="<td class='text-l pl-10' style='white-space:nowrap'><p class='f-14'><b class='red'>*</b>图片名称:<span class='btn-upload'><input class='int-text int-mini' disabled='disabled' id='picName1' style='border:none;background:none;font-weight:400;'><input type='hidden' name='list[0].rankLogo' id='rankLogo1'><input type='hidden' name='rankName1' id='rankName1'>";
            htmlOutput+="&nbsp;<input type='button' class='btn-primary btn-default btn-medium' value='浏览文件'/>";
            htmlOutput+="<input type='file' class='int-file1' id='img1' name='img1' onchange=\""+"pager._imgName('1')\"/></span><span id='imgInfo1' style='color:red;'>gif/jpg/jpeg/png</span><input type='hidden' id='idpsId1' name='list[0].idpsId'><input type='text' id='picErr1' style='display:none;color:red;font-size:14px'></p></td></tr>";
    		if(count>2){
    		var json = '{result:[';
    		for(var i=2;i<=count-1;i++){
    			json+='{index:'+i+'},';
    		}
    		json=json.substr(0,json.length-1);
    		json+=']}';
    		json = eval("(" + json + ")");
    		var template = $.templates("#rankRuleImpl");
    		//渲染模版
            htmlOutput += template.render(json);
    		}
    		htmlOutput+="<tr><td class='text-l pl-10' style='white-space:nowrap'><p class='f-14' style='font-weight:400;'>等级 "+count+":<input class='int-text int-mini' name='list["+count_+"].minScore' id='min"+count+"' type='text' value='0' readonly='readonly' id='min"+count+"' style='border: none;background:none;font-weight:400;' maxlength='12' onkeydown='return doit()'>分以上</p><p class='f-14' style='font-weight:400;'><input type='text' style='display:none;color:red' id='rankMsg"+count+"'></p><input type='hidden' value='999999999999999' name='maxScore' id='max"+count+"'><input type='hidden' value='"+count+"' name='list["+count_+"].rank'></td>";
            htmlOutput+="<td class='text-l pl-10' style='white-space:nowrap'><p class='f-14'><input class='int-text int-mini' name='list["+count_+"].rankName' id='name"+count+"' type='text' onblur=\""+"pager._valideName('"+count+"')\" onfocus=\""+"pager._nameView('"+count+"')\" maxlength='12'><span id='nameInfo"+count+"' style='color:red;display:none'>1-12位字符</span><input type='text' id='nameMsg"+count+"' style='display:none;color:red'></p></td>";
            htmlOutput+="<td class='text-l pl-10' style='white-space:nowrap'><p class='f-14'><b class='red'>*</b>图片名称:<span class='btn-upload'><input class='int-text int-mini' disabled='disabled' id='picName"+count+"' type='text' style='border: none;background:none;font-weight:400;'><input type='hidden' name='list["+count_+"].rankLogo' id='rankLogo"+count_+"'><input type='hidden' name='rankName"+count+"' id='rankName"+count+"'>";
            htmlOutput+="&nbsp;<input type='button' class='btn-primary btn-default btn-medium' value='浏览文件'/>";
            htmlOutput+="<input type='file' class='int-file1' id='img"+count+"' name='img"+count+"' onchange=\""+"pager._imgName('"+count+"')\"/></span><span id='imgInfo"+count+"' style='color:red;'>gif/jpg/jpeg/png</span><input type='hidden' id='idpsId"+count+"' name='list["+count_+"].idpsId'><input type='text' id='picErr"+count+"' style='display:none;color:red;font-size:14px'></p></td></tr>";
            $("#TBODY_RANKRULE").html(htmlOutput);
            $("#rankRegion").val(count);
    	},
    	
    	_toEdit:function(){
    		$("#view").hide();
    		$("#edit").show();
    	},
    	
    	_updateRankRule:function(){
    		var _this=this;
    		var count = $("#rankRegion").val();
    		if(count==null||count=="")
    			count=5;
    		//debugger;
    		$("#picFlag").val('1');
			$("#rankFlag").val('1');
			$("#nameFlag").val('1');
    		for(var i=1;i<=count;i++){
    			if(i<count)
    				this._valideValue(i);
    			this._valideName(i);
    			var pic = $("#rankName"+i).val();
    			if(pic==""||pic==null){
    				document.getElementById('imgInfo'+index).style.display="none";
    				$("#picErr"+i).val("(图片格式不能为空)");
		   			$("#picErr"+i).show();
		   			$("#rankLogo"+i).val("");
		   			$("#picFlag").val("0");
		   			return false;
    			}
    		}
    		var rankFlag = $("#rankFlag").val();
    		var picFlag = $("#picFlag").val();
    		var nameFlag = $("#nameFlag").val();
    		//debugger;
    		if(rankFlag!='0'&&nameFlag!='0'&&picFlag!='0')
    		$("#rankRule").submit();
    	},
    	
    	//获取上传图片名
    	_imgName:function(index) {
    		 var img = document.getElementById('img'+index).files;
    		 document.getElementById('imgInfo'+index).style.display="none";
    		 if(/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(img[0].name)){
    			 document.getElementById('imgInfo'+index).style.display="";
    			 $("#picName"+index).val(img[0].name)
    			 $("#rankName"+index).val(img[0].name)
				 $("#picErr"+index).hide();
    		 }
    		 else{
    			 $("#picErr"+index).val("(图片格式不对)");
    			 $("#picErr"+index).show();
    			 $("#picName"+index).val("");
    			 $("#rankName"+index).val("");
    			 $("#picFlag").val("0");
    		 }
    	},
    	
    	_backup:function(){
    		window.location.href=_base+"/rank/rankrule";
    	},
    	_rankView:function(index){
    		document.getElementById("rankMsg"+index).style.display="none";
    		document.getElementById("rankInfo"+index).style.display="";
    	},
    	_nameView:function(index){
    		document.getElementById("nameMsg"+index).style.display="none";
    		document.getElementById("nameInfo"+index).style.display="";
    	},
    	_changeValue:function(index){
    		document.getElementById('rankMsg'+index).style.display="none";
    		document.getElementById('rankInfo'+index).style.display="none";
    		var maxIndex = $("#max"+index).val();
    		var minIndex = $("#min"+index).val();
    		//debugger;
    		if(maxIndex==""||maxIndex==null)
    		{
    			$("#rankMsg"+index).val('(区间不能为空)');
    			document.getElementById('rankMsg'+index).style.display="";
    			$("#rankFlag").val('0');
    			return false;
    		}
    		var reg = /^[1-9]+[0-9]*]*$/;
    		if(!reg.test($("#max"+index).val())){
    			$("#rankMsg"+index).val('(请输入正整数)');
				$("#max"+index).val('');
    			document.getElementById('rankMsg'+index).style.display="";
    			$("#rankFlag").val('0');
    			return false;
    		}
    		//debugger;
    		if($("#max"+(parseInt(index)+1)).val()!=""){
    			document.getElementById('rankMsg'+index).style.display="none";
    			if(parseInt($("#max"+index).val())>=parseInt($("#max"+(parseInt(index)+1)).val())){
    				$("#rankMsg"+index).val('(等级区间错误)');
    				$("#max"+index).val('');
        			document.getElementById('rankMsg'+index).style.display="";
        			$("#rankFlag").val('0');
        			return false;
    				}
        		}
    		if(parseInt(maxIndex)<=parseInt(minIndex)){
    			$("#rankMsg"+index).val('(等级区间错误)');
    			document.getElementById('rankMsg'+index).style.display="";
    			$("#max"+index).val("");
    			$("#rankFlag").val('0');
    		}else{
    			$("#min"+(parseInt(index)+1)).val(maxIndex);
    		}
    	},
    	
    	_valideValue:function(index){
    		var maxIndex = $("#max"+index).val();
    		//debugger;
    		if($("#rankFlag").val()=='1'){
    		if(maxIndex==""||maxIndex==null)
    		{
    			$("#rankMsg"+index).val('(区间不能为空)');
    			document.getElementById('rankMsg'+index).style.display="";
    			$("#rankFlag").val('0');
    			return false;
    		}
    		}
    	},
    	
    	_valideName:function(index){
    		document.getElementById('nameMsg'+index).style.display='none';
    		document.getElementById('nameInfo'+index).style.display="none";
    		var name = $("#name"+index).val().replace(/(^\s*)|(\s*$)/g,"");
    		if(name==null||name==""){
    			$("#nameMsg"+index).val("(名称不能为空)");
    			$("#name"+index).val('');
				document.getElementById('nameMsg'+index).style.display="";
				$("#nameFlag").val('0');
				return false;
    		}
    	}
    	
    });
    
    module.exports = RankRuleEditPager
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