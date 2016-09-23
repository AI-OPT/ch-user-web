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
    		"blur [id='endTime']":"_checkEndTimeText",
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
			$("#contractNameText").text('1-64位字符');
    	},
    	_checkContractCodeValue:function(){
			var contractCode = $("#contractCode").val();
			if(contractCode==""||contractCode==null){
				$("#contractCodeErrMsg").show();
				$("#contractCodeText").show();
				$("#contractCodeText").text('1-64位字符');
	    		$("#contractCodeFlag").val("0");
			}else{
				
				var	param={
						contractCode:$("#contractCode").val(),
    					userId:userId,
    					tenantId:"changhong",
    					contractType:$("#contractType").val()
    				   };
        		ajaxController.ajax({
    			        type: "post",
    			        processing: false,
    			        url: _base+"/contract/checkContractCode",
    			        dataType: "json",
    			        data: param,
    			        message: "正在加载数据..",
    			        success: function (data) {
    			         if(data.responseHeader.resultCode=="100005"){
    			        	    $("#contractCodeErrMsg").show();
    			        	 	$("#contractCodeText").show();
    			        	 	$("#contractCodeText").text('合同编号已注册');
					    		$("#contractCodeFlag").val("0");
    							return false;
    			        	}else if(data.responseHeader.resultCode=="000000"){
    			        		if(contractCode.length>=1&&contractCode.length<=64){
    			        			if(contractCode.length>=1&&contractCode.length<=64){
    			    					if(contractCode.match(/^\s+$/)){
    			    						$('#contractCodeErrMsg').show();
    			    	    				$("#contractCodeText").show();
    			    	        			$('#contractCodeText').text('必填项');
    			    	        			$("#contractCodeFlag").val("0");
    			    					}else{
    			    						$('#contractCodeErrMsg').hide();
    			    	    				$("#contractCodeFlag").val("1");
    			    					}
    			        				
    			    				}else{
    			    					$('#contractCodeErrMsg').show();
    			        				$("#contractCodeText").show();
    			            			$('#contractCodeText').text('1-64位字符');
    			            			$("#contractCodeFlag").val("0");
    			    				}
    			    				
    							}else{
    								$('#contractCodeErrMsg').show();
			        				$("#contractCodeText").show();
			            			$('#contractCodeText').text('1-64位字符');
			            			$("#contractCodeFlag").val("0");
    							}
    			        	}
    			        	
    			        },
    			        error: function(XMLHttpRequest, textStatus, errorThrown) {
    						 alert(XMLHttpRequest.status);
    						 alert(XMLHttpRequest.readyState);
    						 alert(textStatus);
    						}
    			        
    			    }); 
				
			}
		},
		_checkContractNameValue:function(){
			var contractName = $("#contractName").val();
			if(contractName==""||contractName==null){
				$("#contractNameErrMsg").show();
				$("#contractNameText").show();
				$("#contractNameText").text('1-64位字符');
	    		$("#contractNameFlag").val("0");
			}else{
				
				var	param={
						contractName:$("#contractName").val(),
    					userId:userId,
    					tenantId:"changhong",
    					contractType:$("#contractType").val()
    				   };
        		ajaxController.ajax({
    			        type: "post",
    			        processing: false,
    			        url: _base+"/contract/checkContractName",
    			        dataType: "json",
    			        data: param,
    			        message: "正在加载数据..",
    			        success: function (data) {
    			         if(data.responseHeader.resultCode=="100005"){
    			        	    $("#contractNameErrMsg").show();
    			        	 	$("#contractNameText").show();
    			        	 	$("#contractNameText").text('合同名称已注册');
					    		$("#contractNameFlag").val("0");
    							return false;
    			        	}else if(data.responseHeader.resultCode=="000000"){
    			        		if(contractName.length>=1&&contractName.length<=64){
    								if(contractName.match(/^\s+$/)){
    									$("#contractNameErrMsg").show();
    									$("#contractNameText").show();
    									$("#contractNameText").text('必填项');
    						    		$("#contractNameFlag").val("0");
    								}else{
    									$('#contractNameErrMsg').hide();
    				    				$("#contractNameFlag").val("1");
    								}
    							}else{
    								$('#contractNameErrMsg').show();
    			    				$("#contractNameText").show();
    			        			$('#contractNameText').text('1-64位字符');
    			        			$("#contractNameFlag").val("0");
    			        			
    							}
    			        	}
    			        },
    			        error: function(XMLHttpRequest, textStatus, errorThrown) {
    						 alert(XMLHttpRequest.status);
    						 alert(XMLHttpRequest.readyState);
    						 alert(textStatus);
    					}
    			        
    			    }); 
				
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
		_checkFileText:function(){
			var scanFileInput = $("#scanFileText").val();
			var electronicContractText = $("#electronicContractText").val();
			
			if(scanFileInput==""||scanFileInput==null){
				$("#scanContractErrMsg").show();
				$("#scanContractText").show();
				$("#scanContractText").text('请上传合同扫描件');
	    		$("#scanVersionContractFlag").val("0");
			}else{
				if(scanFileInput!=null&&scanFileInput!=""){
					if(!/\.(PDF|PNG|JPG|DOC|pdf|png|jpg|doc|docx)$/.test(scanFileInput)){
						$("#scanContractErrMsg").show();
						$("#scanContractText").show();
						$("#scanContractText").text('文件格式不对，只允许上传pdf、png、jpg、doc、docx');
						$("#scanVersionContractFlag").val("0");
					}else{
						var reg = /^[\u4e00-\u9fa5a-zA-Z0-9\.]{1,20}$/;
						if(scanFileInput.match(reg)){
							$("#scanContractErrMsg").hide();
							$("#scanContractErrMsg").hide();
							$("#scanVersionContractFlag").val("1");
						}else{
							$("#scanContractErrMsg").show();
							$("#scanContractText").show();
							$("#scanContractText").text('1-20个字符，允许使用汉字、字母、数字');
							$("#scanVersionContractFlag").val("0");
						}
					}
					
				}
			}
			
			if(electronicContractText!=null&&electronicContractText!=""){
				if(!/\.(PDF|PNG|JPG|DOC|pdf|png|jpg|doc|docx)$/.test(electronicContractText)){
					$("#electronicContractErrMsg").show();
					$("#electronicContractFileText").show();
					$("#electronicContractFileText").text('文件格式不对，只允许上传pdf、png、jpg、doc、docx');
					$("#electronicContractFlag").val("0");
				}else{
					var reg = /^[\u4e00-\u9fa5a-zA-Z0-9\.]{1,20}$/;
					if(electronicContractText.match(reg)){
						$("#electronicContractErrMsg").hide();
						$("#electronicContractFileText").hide();
						$("#electronicContractFlag").val("1");
					}else{
						$("#electronicContractErrMsg").show();
						$("#electronicContractFileText").show();
						$("#electronicContractFileText").text('1-20个字符，允许使用汉字、字母、数字');
						$("#electronicContractFlag").val("0");
					}
				}
				
			}else{
				$("#electronicContractErrMsg").hide();
				$("#electronicContractFileText").hide();
			}
		},
		_checkFileValue:function(fileId,fileValue,errMsg,contractText,contractFlag){
			if(fileValue.length>20){
				$("#"+errMsg).show();
				$("#"+contractText).show();
				$("#"+contractText).text('1-20个字符，允许使用汉字、字母、数字');
				$("#"+contractFlag).val("0");
			}else{
				var reg = /^[\u4e00-\u9fa5a-zA-Z0-9\.]{1,20}$/;
				if(subString.match(reg)){
					$("#"+errMsg).hide();
					$("#"+contractText).hide();
					$("#"+contractFlag).val("1");
				}else{
					$("#"+errMsg).show();
					$("#"+contractText).show();
					$("#"+contractText).text('1-20个字符，允许使用汉字、字母、数字');
					$("#"+contractFlag).val("0");
				}
			}
		},
		_checkEndTimeText:function(){
			var endTime = $("#endTime").val();
			if(endTime==""||endTime==null){
				$("#endTimeTextErrMsg").show();
				$("#endTimeTextShow").show();
				$("#endTimeTextShow").text('请输入时间');
	    		$("#endTimeTextFlag").val("0");
			}
			
			if(endTime!=""&&endTime!=null){
				var startTime = $("#startTime").val();
				var endTime = $("#endTime").val();
				var scanVersionContractFlag = $("#scanVersionContractFlag").val();
				var  startTimeStr=startTime.toString();
				startTimeStr =  startTimeStr.replace(/-/g,"/");
				var oDate1 = new Date(startTimeStr).getTime();
				var  endTimeStr=endTime.toString();
				endTimeStr =  endTimeStr.replace(/-/g,"/");
				var oDate2 = new Date(endTimeStr).getTime();
				if(oDate1>oDate2){
					$("#endTimeTextErrMsg").show();
					$("#endTimeTextShow").show();
					$("#endTimeTextShow").text('开始时间不能大于结束时间');
		    		$("#endTimeTextFlag").val("0");
				}else{
					$("#endTimeTextErrMsg").hide();
					$("#endTimeTextShow").hide();
					$("#endTimeTextFlag").val("1");
				}
			}
		},
		_checkFileFormat:function(){
			var electronicFileTest = $("#electronicContractText").val();
			var scanFileText = $("#scanFileText").val();
			if(electronicFileTest!=null&&electronicFileTest!=""){
				if(!/\.(PDF|PNG|JPG|DOC|pdf|png|jpg|doc|docx)$/.test(electronicFileTest)){
					$("#electronicContractErrMsg").show();
					$("#electronicContractFileText").show();
					$("#electronicContractFileText").text('文件格式不对，只允许上传pdf、png、jpg、doc、docx');
					$("#electronicContractFlag").val("0");
				}else{
					$("#electronicContractErrMsg").hide();
					$("#electronicContractFileText").hide();
					$("#electronicContractFlag").val("1");
				}
			}else{
				$("#electronicContractErrMsg").hide();
				$("#electronicContractFileText").hide();
			}
			if(scanFileText!=null&&scanFileText!=""){
				if(!/\.(PDF|PNG|JPG|DOC|pdf|png|jpg|doc|docx)$/.test(scanFileText)){
					$("#scanContractErrMsg").show();
					$("#scanContractText").show();
					$("#scanContractText").text('文件格式不对，只允许上传pdf、png、jpg、doc、docx');
					$("#scanVersionContractFlag").val("0");
				}
			}else{
				$("#scanContractErrMsg").show();
				$("#scanContractText").show();
				$("#scanContractText").text('合同附件不能为空');
				$("#scanVersionContractFlag").val("0");
			}
		},
		_saveSupplierContract:function(){
			this._checkContractCodeValue();
			this._checkContractNameValue();
			this._checkStartTime();
			this._checkEndTimeText();
			this._checkFileText();
			//this._checkFileFormat();
			var contractCodeFlag = $("#contractCodeFlag").val();
			var contractNameFlag = $("#contractNameFlag").val();
			var startTimeFlag = $("#startTimeFlag").val();
			var endTimeFlag  = $("#endTimeTextFlag").val();
			var scanVersionContractFlag = $("#scanVersionContractFlag").val();
			var electronicContractFlag = $("#electronicContractFlag").val();
			if(contractCodeFlag!="0"&&contractNameFlag!="0"&&startTimeFlag!="0"&&endTimeFlag!="0"&&scanVersionContractFlag!="0"){
				var electronicFileTest = $("#electronicContractText").val();
				if(electronicFileTest!=null&&electronicFileTest!=""&&electronicContractFlag=="0"){
					return;
				}
				var userName = $("#userName").val();
				var custName = $("#custName").val();
				
				$("#scanFileName").val($("#scanFileText").val());
				if($("#electronicContractText").val()!=""&&$("#electronicContractText").val()!=null){
					$("#electronicFileName").attr("name","list[1].infoName");
					$("#electronicFileName").val($("#electronicContractText").val());
				}
				
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
			        		window.location.href=_base+"/contract/contractSupplierDetailPager?userId="+userId+"&userName="+escape(encodeURIComponent(userName))+"&custName="+escape(encodeURIComponent(custName));
			        	}
			          },
					error: function(error) {
							alert("error:"+ error);
						}
					});
			}
		},
		_saveShopContract:function(){
			this._checkContractCodeValue();
			this._checkContractNameValue();
			this._checkStartTime();
			this._checkEndTimeText();
			this._checkFileText();
			this._checkFileFormat();
			var contractCodeFlag = $("#contractCodeFlag").val();
			var contractNameFlag = $("#contractNameFlag").val();
			
			
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			
			var scanVersionContractFlag = $("#scanVersionContractFlag").val();
			
			var  startTimeStr=startTime.toString();
			startTimeStr =  startTimeStr.replace(/-/g,"/");
			var oDate1 = new Date(startTimeStr).getTime();
			
			var  endTimeStr=endTime.toString();
			endTimeStr =  endTimeStr.replace(/-/g,"/");
			var oDate2 = new Date(endTimeStr).getTime();
			if(oDate1>oDate2){
				$("#endTimeTextErrMsg").show();
				$("#endTimeTextShow").show();
				$("#endTimeTextShow").text('开始时间不能大于结束时间');
	    		$("#endTimeTextFlag").val("0");
			}else{
				$("#endTimeTextErrMsg").hide();
				$("#endTimeTextShow").hide();
				$("#endTimeTextFlag").val("1");
			}
			
			var startTimeFlag = $("#startTimeFlag").val();
			var endTimeFlag = $("#endTimeTextFlag").val();
			var scanVersionContractFlag = $("#scanVersionContractFlag").val();
			if(contractCodeFlag!="0"&&contractNameFlag!="0"&&startTimeFlag!="0"&&endTimeFlag!="0"&&scanVersionContractFlag!="0"){
				
				var userName = $("#userName").val();
				var custName = $("#custName").val();
				
				$("#scanFileName").val($("#scanFileText").val());
				
				if($("#electronicContractText").val()!=""&&$("#electronicContractText").val()!=null){
					$("#electronicFileName").attr("name","list[1].infoName");
					$("#electronicFileName").val($("#electronicContractText").val());
				}
				
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
			        		window.location.href=_base+"/contract/contractShopDetailPager?userId="+userId+"&userName="+decodeURL(Server.URLEncode(Request.Form(userName)))+"&custName="+decodeURL(Server.URLEncode(Request.Form(custName)));;
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

function uploadFile(fileId,inputText,errMsg,contractText,contractFlag,ddsId){
	var contractFile = $("#"+fileId).val();
	var subString = contractFile.substring(contractFile.lastIndexOf("\\")+1,contractFile.length);
	$("#"+inputText).val(subString);
	/**
	 * 校验字符数
	 */
	var reg = /^[\u4e00-\u9fa5a-zA-Z0-9\.]{1,20}$/;
	if(subString.match(reg)){
		$("#"+errMsg).hide();
		$("#"+contractText).hide();
		$("#"+contractFlag).val("1");
	}else{
		$("#"+errMsg).show();
		$("#"+contractText).show();
		$("#"+contractText).text('1-20个字符，允许使用汉字、字母、数字');
		$("#"+contractFlag).val("0");
		return;
	}
	$("#"+inputText).val(subString);
	var fileTest = $("#"+inputText).val();
	if(fileTest==""){
		$("#"+errMsg).show();
		$("#"+contractText).show();
		$("#"+contractText).text('合同附件不能为空');
		$("#"+contractFlag).val("0");
		return false;
	}else if(!/\.(PDF|PNG|JPG|DOC|pdf|png|jpg|doc|docx)$/.test(fileTest)){
		$("#"+errMsg).show();
		$("#"+contractText).show();
		$("#"+contractText).text('文件格式不对，只允许上传pdf、png、jpg、doc、docx');
		$("#"+contractFlag).val("0");
		return false;
	}else if(document.getElementById(fileId).files[0].size>20*1024*1024){
		$("#"+errMsg).show();
		$("#"+contractText).show();
		$("#"+contractText).text('文档太大，不能超过20M');
		$("#"+contractFlag).val("0");
		return false;
	}else{
		$("#"+errMsg).hide();
		$("#"+contractText).hide();
		$("#"+contractFlag).val("1");
	}
	 $.ajaxFileUpload({  
         url:_base+"/contract/uploadFile?contractFileId="+fileId,  
         secureuri:false,  
         fileElementId:fileId,//file标签的id  
         dataType: 'text',//返回数据的类型  
         data:{fileId:fileId},//一同上传的数据  
         success: function (data, status) {
        	var dataJson = $.parseJSON(data);  
        	if(dataJson.isTrue==true){
        		$("#"+ddsId).val(dataJson.dssId);
        		$("#"+contractFlag).val("1");
        	 }else{
        		 alert("上传失败");
        		 $("#"+contractFlag).val("0");
        	 }
         },
         error: function (data, status, e) {  
             alert(e);  
         }
     });
}