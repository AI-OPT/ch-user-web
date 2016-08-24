define('app/jsp/contract/contract', function (require, exports, module) {
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
    require("my97DatePicker/WdatePicker");
    require("opt-paging/aiopt.pagination");
    require("twbs-pagination/jquery.twbsPagination.min");
    require("app/jsp/contract/ajaxfileupload");
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var ContractInfoPager = Widget.extend({
    	
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5
    	},
    	//事件代理
    	events: {
    		//合同编号
    		"focus [id='contractCode']":"_showContractCodeTip",
    		"blur [id='contractCode']":"_checkContractCodeValue",
    		
    		"focus [id='contractName']":"_showContractNameTip",
    		"blur [id='contractName']":"_checkContractNameValue",
    		
    		"click [id='supplierSave']":"_saveSupplierContract",
    		"click [id='shopSave']":"_saveShopContract",
    		
    		/*"click [id='scanContract']":"_uploadFile",
    		
    		"change [id='scanFile']":"_uploadFile",*/
    		
    		
        },
    	//重写父类
    	setup: function () {
    		ContractInfoPager.superclass.setup.call(this);
    	},
    	_showContractCodeTip:function(){
    		$("#contractCodeErrMsg").show();
			$("#contractCodeText").show();
			$("#contractCodeText").text('1-64位字符');
    	},
    	_showContractNameTip:function(){
    		$("#contractNameErrMsg").show();
			$("#contractNameText").show();
			$("#contractNameText").text('1-256位字符');
    	},
    	_checkContractCodeValue:function(){
			var contractCode = $("#contractCode").val();
			if(contractCode==""||contractCode==null){
				$("#contractCodeErrMsg").show();
				$("#contractCodeText").show();
				$("#contractCodeText").text('1-64位字符');
	    		$("#contractCodeFlag").val("0");
			}else{
				if(contractCode.length>=1&&contractCode.length<=64){
    				$('#contractCodeErrMsg').hide();
    				$("#contractCodeFlag").val("1");
				}else{
					$('#contractCodeErrMsg').show();
    				$("#contractCodeText").show();
        			$('#contractCodeText').text('1-64位字符');
        			$("#contractCodeFlag").val("0");
				}
			}
		},
		_checkContractNameValue:function(){
			var contractName = $("#contractName").val();
			if(contractName==""||contractName==null){
				$("#contractNameErrMsg").show();
				$("#contractNameText").show();
				$("#contractNameText").text('1-256位字符');
	    		$("#contractNameFlag").val("0");
			}else{
				if(contractName.length>=1&&contractName.length<=256){
    				$('#contractNameErrMsg').hide();
    				$("#contractNameFlag").val("1");
				}else{
					$('#contractNameErrMsg').show();
    				$("#contractNameText").show();
        			$('#contractNameText').text('1-64位字符');
        			$("#contractNameFlag").val("0");
				}
			}
		},
		_checkStartTime:function(){
			var startTime = $("#startTime").val();
			if(startTime==""||startTime==null){
				$("#startTimeErrMsg").show();
				$("#startTimeText").show();
				$("#startTimeText").text('请输入时间');
	    		$("#startTimeFlag").val("0");
			}else{
				$("#startTimeErrMsg").hide();
				$("#startTimeText").hide();
				$("#startTimeFlag").val("1");
			}
		},
		_checkEndTime:function(){
			var endTime = $("#endTime").val();
			if(endTime==""||endTime==null){
				$("#endTimeErrMsg").show();
				$("#endTimeText").show();
				$("#endTimeText").text('请输入时间');
	    		$("#endTimeFlag").val("0");
			}else{
				$("#endTimeErrMsg").hide();
				$("#endTimeText").hide();
				$("#endTimeFlag").val("1");
			}
		},
		_saveSupplierContract:function(){
			this._checkContractCodeValue();
			this._checkContractNameValue();
			this._checkStartTime();
			this._checkEndTime();
			var contractCodeFlag = $("#contractCodeFlag").val();
			var contractNameFlag = $("#contractNameFlag").val();
			
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			var  startTimeStr=startTime.toString();
			startTimeStr =  startTimeStr.replace(/-/g,"/");
			var oDate1 = new Date(startTimeStr).getTime();
			
			var  endTimeStr=endTime.toString();
			endTimeStr =  endTimeStr.replace(/-/g,"/");
			var oDate2 = new Date(endTimeStr).getTime();
			
			if(oDate1>oDate2){
				$("#endTimeErrMsg").show();
				$("#endTimeText").show();
				$("#endTimeText").text('开始时间不能大于结束时间');
	    		$("#endTimeFlag").val("0");
			}else{
				$("#endTimeErrMsg").hide();
				$("#endTimeText").hide();
				$("#endTimeFlag").val("1");
			}
			var startTimeFlag = $("#startTimeFlag").val();
			var endTimeFlag  = $("#endTimeFlag").val();
			if(contractCodeFlag!="0"&&contractNameFlag!="0"&&startTimeFlag!="0"&&endTimeFlag!="0"){
				$.ajax({
					type:"post",
					url:_base+"/contract/addSupplierContractInfo",
					dataType: "json",
					data:$("#contractInfo").serialize(),
			        success: function(data) {
			        	if(data.responseHeader.resultCode=="111111"){
			        		alert("失败了");
			        		return false;
			        	}else if(data.responseHeader.resultCode=="000000"){
			        		window.location.href=_base+"/contract/contractSupplierDetailPager?userId="+userId;
			        	}
			          },
					error: function(error) {
							alert("error:"+ error);
						}
					});
			}
		},
		_uploadFile:function(){
			var scanContractFile = $("#scanFile").val();
			$("#scanFileTest").val(scanContractFile);
			var scanFileTest = $("#scanFileTest").val();
			if(scanFileTest==""){
				$("#scanContractErrMsg").show();
				$("#scanContractText").show();
				$("#scanContractText").text('扫描件合同不能为空');
				$("#scanVersionContractFlag").val("0");
				return false;
			}else if(!/\.(PDF|PNG|JPG|DOC|pdf|png|jpg|doc)$/.test(scanFileTest)){
				$("#scanContractErrMsg").show();
				$("#scanContractText").show();
				$("#scanContractText").text('文件格式不对，只允许上传pdf、png、jpg、word');
				$("#scanVersionContractFlag").val("0");
				return false;
			}else if(scanFileTest.size>=(20.05*1024*1024)-1){
				$("#scanContractErrMsg").show();
				$("#scanContractText").show();
				$("#scanContractText").text('文档太大，不能超过20M');
				$("#scanVersionContractFlag").val("0");
				return false;
			}
			 $.ajaxFileUpload({  
		         url:_base+"/contract/uploadFile",  
		         secureuri:false,  
		         fileElementId:"scanFileTest",//file标签的id  
		         dataType: 'json',//返回数据的类型  
		         data:{scanFile:scanFileTest},//一同上传的数据  
		         success: function (data, status) {
		        	if(data.isTrue==true){
		        		alert("dddd");
		        		//document.getElementById(ddsId1).value=data.idpsId;
		        		//$("#picFlag").val("1");
		        	 }
		         },
		         error: function (data, status, e) {  
		             alert(e);  
		         }
		     }); 
		},
		_saveShopContract:function(){
			this._checkContractCodeValue();
			this._checkContractNameValue();
			this._checkStartTime();
			this._checkEndTime();
			var contractCodeFlag = $("#contractCodeFlag").val();
			var contractNameFlag = $("#contractNameFlag").val();
			
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			
			var  startTimeStr=startTime.toString();
			startTimeStr =  startTimeStr.replace(/-/g,"/");
			var oDate1 = new Date(startTimeStr).getTime();
			
			var  endTimeStr=endTime.toString();
			endTimeStr =  endTimeStr.replace(/-/g,"/");
			var oDate2 = new Date(endTimeStr).getTime();
			if(oDate1>oDate2){
				$("#endTimeErrMsg").show();
				$("#endTimeText").show();
				$("#endTimeText").text('开始时间不能大于结束时间');
	    		$("#endTimeFlag").val("0");
			}else{
				$("#endTimeErrMsg").hide();
				$("#endTimeText").hide();
				$("#endTimeFlag").val("1");
			}
			
			var startTimeFlag = $("#startTimeFlag").val();
			var endTimeFlag = $("#endTimeFlag").val();
			
			if(contractCodeFlag!="0"&&contractNameFlag!="0"&&startTimeFlag!="0"&&endTimeFlag!="0"){
				$.ajax({
					type:"post",
					url:_base+"/contract/addShopContractInfo",
					dataType: "json",
					data:$("#contractInfo").serialize(),
			        success: function(data) {
			        	if(data.responseHeader.resultCode=="111111"){
			        		alert("失败了");
			        		return false;
			        	}else if(data.responseHeader.resultCode=="000000"){
			        		window.location.href=_base+"/contract/contractShopDetailPager?userId="+userId;
			        	}
			          },
					error: function(error) {
							alert("error:"+ error);
						}
					});
			}
		}
		
    });
    module.exports = ContractInfoPager
});

function uploadFile(fileId,inputText,errMsg,contractText,contractFlag){
	var contractFile = $(fileId).val();
	$("#"+inputText).val(contractFile);
	var fileTest = $("#"+inputText).val();
	if(fileTest==""){
		$("#"+errMsg).show();
		$("#"+contractText).show();
		$("#"+contractText).text('扫描件合同不能为空');
		$("#"+contractFlag).val("0");
		return false;
	}else if(!/\.(PDF|PNG|JPG|DOC|pdf|png|jpg|doc)$/.test(scanFileTest)){
		$("#"+errMsg).show();
		$("#"+contractText).show();
		$("#"+contractText).text('文件格式不对，只允许上传pdf、png、jpg、word');
		$("#"+contractFlag).val("0");
		return false;
	}else if(fileTest.size>=(20.05*1024*1024)-1){
		$("#"+errMsg).show();
		$("#"+contractText).show();
		$("#"+contractText).text('文档太大，不能超过20M');
		$("#"+contractFlag).val("0");
		return false;
	}
	 $.ajaxFileUpload({  
         url:_base+"/contract/uploadFile",  
         secureuri:false,  
         fileElementId:fileId,//file标签的id  
         dataType: 'json',//返回数据的类型  
         data:{"contractFileId":fileId},//一同上传的数据  
         success: function (data, status) {
        	if(data.isTrue==true){
        		$("#"+contractFlag).val("1");
        	 }else{
        		 alert("上传失败");
        		 $("#"+contractFlag).val("1");
        	 }
         },
         error: function (data, status, e) {  
             alert(e);  
         }
     }); 
}