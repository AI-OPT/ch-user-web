define('app/jsp/score/scorepage', function (require, exports, module) {
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
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var ScorePagePager = Widget.extend({
    	
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5
    	},
    	//事件代理
    	events: {
    		"click #submitScore":"_submitScore",
    		"blur #1":"_validate1",
    		"blur #2":"_validate2",
    		"blur #3":"_validate3",
    		"blur #4":"_validate4"
        },
    	//重写父类
    	setup: function () {
    		ScorePagePager.superclass.setup.call(this);
    	},
    	
    	//校验输入数字0到50
    	_validate1:function(){
    		var score = document.getElementById(1).value;
    		if(score==null||score==""){
    			$('#dialogContent').text('评价分数不能为空');
    			$('#sureModal').modal();
    		}else{
    		//var	reg = /^[0-9]{1}$|^[0-4][0-9]$|^50$/;
    		if(score>50||score<0){
    			$('#dialogContent').text('评价分数区间不对');
    			$('#sureModal').modal();
    			document.getElementById(1).value='';
    		}
    		}
    	},
    	
		//校验输入数字0到10
		_validate2:function(){
			var score = document.getElementById(2).value;
			if(score==null||score==""){
				$('#dialogContent').text('评价分数不能为空');
    			$('#sureModal').modal();
			}else{
				//var	reg = /^(0?\d|10)$/;
				if(score>10||score<0){
					$('#dialogContent').text('评价分数区间不对');
	    			$('#sureModal').modal();
	    			document.getElementById(2).value='';
				}
			}
		},

    	//校验输入数字0到20
    	_validate3:function(){
    		var score = document.getElementById(3).value;
    		if(score==null||score==""){
    			$('#dialogContent').text('评价分数不能为空');
    			$('#sureModal').modal();
    		}else{
    			if(score>20||score<0){
    				$('#dialogContent').text('评价分数区间不对');
	    			$('#sureModal').modal();
	    			document.getElementById(3).value='';
    			}
    		}
    	},
    	//校验输入数字0到20
    	_validate4:function(){
    		var score = document.getElementById(4).value;
    		if(score==null||score==""){
    			$('#dialogContent').text('评价分数不能为空');
    			$('#sureModal').modal();
    		}else{
    			if(score>20||score<0){
    				$('#dialogContent').text('评价分数区间不对');
	    			$('#sureModal').modal();
	    			document.getElementById(4).value='';
    			}
    		}
    	},
    	
    	//提交供货商评价
    	_submitScore: function(){
    		$("#scoreFlag").val("1");
    		for(var i=1;i<=4;i++){
    		var score = document.getElementById(i).value;
    		if(score==null||score=="")
    			$("#scoreFlag").val("0");
    	}
    	if($("#scoreFlag").val()!="0"){
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
	        		$('#dialogContent').text('评价成功');
	    			$('#sureModal').modal();
	        	}
	            },
				error: function(error) {
					alert("error:"+ error);
				}
				});
	           }
    	}
    });
    
    module.exports = ScorePagePager
});