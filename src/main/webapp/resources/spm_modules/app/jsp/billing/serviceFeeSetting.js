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
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var ServiceFeeSettingPager = Widget.extend({
    	
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5
    	},
    	//事件代理
    	events: {
    		"click #saveSetting":"_saveSetting",
    		"blur #rentFee":"_checkRentFee",
    		"blur #ratio":"_checkRatio"
        },
    	//重写父类
    	setup: function () {
    		ServiceFeeSettingPager.superclass.setup.call(this);
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
    			if(divId=='rentPay'){
    				$("#rentFee").attr("disabled",false);
    				$("#rentCycleType").attr("disabled",false);
    			}else if(divId=='payCycle')
    				$("#ratio").attr("disabled",false);
    		}else if(radioValue==1){
    			document.getElementById(divId).style.display="none";
    			if(divId=='rentPay'){
    				$("#rentFee").attr("disabled",true);
    				$("#rentCycleType").attr("disabled",true);
    			}else if(divId=='payCycle')
    				$("#ratio").attr("disabled",true);
    	}
    	},
    	
    	_saveSetting:function(){
    		pager._checkRentFee();
    		pager._checkRatio();
    		if($("#rentFeeFlag").val()=='0'||$("#ratioFlag").val()=='0')
    			return false;
    		else{
        	$.ajax({
    			type:"post",
    			url:_base+"/billing/saveservicesetting",
    			dataType: "json",
    			data:$("#serviceFee").serialize(),
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
    		}
    	},
    	_jump:function(){
    		window.location.href=_base+"/billing/billingpager"
    	},
    	
    	_checkRentFee:function(){
    		var rentFee=$("#rentFee").val();
    		if(rentFee==null||rentFee==""){
    			$('#dialogContent2').text('输入数据不能为空');
    			$('#sureModal2').modal({backdrop:"static",show:true});
    			$('#rentFeeFlag').val("0");
    			return false;
    		}
    		var pattern = /^[1-9]\d{0,14}$/;
    		if(!rentFee.match(pattern)){
    			$('#dialogContent2').text('输入数据有误');
    			$('#sureModal2').modal({backdrop:"static",show:true});
    			$('#rentFeeFlag').val("0");
    			$('#rentFee').val("");
    			return false;
    		}
    		$('#rentFeeFlag').val("1");
    	},
    	_checkRatio:function(){
    		var ratio=$("#ratio").val();
    		if(ratio==null||ratio==""){
    			$('#dialogContent2').text('输入数据不能为空');
    			$('#sureModal2').modal({backdrop:"static",show:true});
    			$('#ratioFlag').val("0");
    			return false;
    		}
    		var pattern = /^([1-9][0-9]?|100)$/;
    		if(!ratio.match(pattern)){
    			$('#dialogContent2').text('输入数据有误');
    			$('#sureModal2').modal({backdrop:"static",show:true});
    			$('#ratio').val("");
    			$('#ratioFlag').val("0");
    			return false;
    		}
    		$('#ratioFlag').val("1");
    	}
    	
    });
    
    module.exports = ServiceFeeSettingPager
});

