var supplierValidFormManager = null;
function ready_supplierValidForm(){
	supplierValidFormManager =  new $.supplierValidFormManager();
	supplierValidFormManager.init(); 
}; 
(function($){
	$.supplierValidFormManager = function() {
		this.settings = $.extend(true,{},$.supplierValidFormManager.defaults);
	}
	$.extend($.supplierValidFormManager, {
		defaults: {
			validateController: new $.ValidateController()
		},
		prototype: {
			/*初始化方法*/
			init: function() {
				var _this = this;
				_this.bindEvents();
				$(":input").bind("focusout",function(){
					_this.focusoutEvent($(this).attr("name"));
				});
			},
			/*事件处理方法*/
			bindEvents: function(){
				var _this = this;
			},
			//表单失去焦点的校验 
			focusoutEvent: function(fieldName){
				/*单个字段级的校验*/
				var valiValueFrame = this.bindValidRules();
				this.settings.validateController.processField(valiValueFrame,fieldName);
			},
			/*
			* 执行数据校验
			* 如果返回true标识校验成功; 否则显示校验失败信息
			*/
			validate: function(){
				var _this=this;
				/*初始化框架级别校验对象*/
				var validateFrame= new $.ValidateChannelFrame(); 
				/*初始化标签页级的校验对象*/
				var validateTab = new $.ValidateTab("1","供应商"); 
				/*初始化form部件级别的校验对象*/
				var validateComponent = new $.ValidateComponent(_this.settings.FORM_ID,"DBForm"); 
				var valiValueFrame = this.bindValidRules();
				valiValueFrame.valid(); 
				validateComponent.addFormFields(valiValueFrame.getErrors()); 
				/*将部件级别校验数据存储到标签页级，并将标签页级校验对象封装到框架级别*/
				var componet = validateComponent.get();  
				validateTab.push(componet); 
				validateFrame.push(validateTab.get()); 
				/*执行框架级校验，并返回校验结果。如果校验不通过，弹出提示信息*/
				var pass=validateFrame.valid(); 
				if(pass)return true;
				validateFrame.info({
					title: "提示：校验不通过",
					width: "400",
					height: "300"
				});
			},
			/*清除校验结果*/
			clear:function(jqObj){
				if(jqObj.nextAll("[name='_message_span_']")){
					jqObj.nextAll("[name='_message_span_']").removeClass().text(" ");
				}
			},

			/**绑定校验规则*/
			bindValidRules: function(){
				var _this=this;
				var valiValueFrame = new $.ValidateValueFrame();
				var currYear = new Date().getFullYear();
				var currMonth = new Date().getMonth() + 1;
				

				//基本信息校验
				valiValueFrame.addRule({
					labelName: "供应商名称",
					fieldName: "supplier.supplierName",
					fieldType: "DBEdit",
					fieldValue: $('input[name="supplier.supplierName"]').val(),
					jqObj: $('input[name="supplier.supplierName"]'),
					fieldRules: {
						required :true,
						//inputFilter:true,
						remote: {//remote规则用来调用服务端Action代码,与服务端进行实时交互
							url: "supplierManager!ajaxValidateSuppliername.action",
							type: "post",
							data: {
								suppliername : GBK.encodeURI(GBK.encodeURI($('input[name="supplier.supplierName"]').val())) ,
								supplierid: function() { return $('input[name=id]').val(); },
								random: Math.random()
							}
					  	}
					}
				}).addRule({
					labelName: "供应商简称",
					fieldName: "supplier.supplierShortName",
					fieldType: "DBEdit",
					fieldValue: $('input[name="supplier.supplierShortName"]').val(),
					jqObj: $('input[name="supplier.supplierShortName"]'),
					fieldRules: {
						required :true,
						//inputFilter:true,
						cnlength:16,
						remote: {//remote规则用来调用服务端Action代码,与服务端进行实时交互
							url: "supplierManager!ajaxValidateSuppliershortname.action",
							type: "post",
							data: {
								suppliername : GBK.encodeURI(GBK.encodeURI($('input[name="supplier.supplierShortName"]').val())) ,
								supplierid: function() { return $('input[name=id]').val(); },
								random: Math.random()
							}
					  	}
					}
				}).addRule({
					labelName: "联系人",
					fieldName: "supplier.contactPerson",
					fieldType: "DBEdit",
					fieldValue: $('input[name="supplier.contactPerson"]').val(),
					jqObj: $('input[name="supplier.contactPerson"]'),
					fieldRules: {
						required :true						
					}
				})/* .addRule({
					labelName: "供应商代码",
					fieldName: "supplier.supplierCode",
					fieldType: "DBEdit",
					fieldValue: $('input[name="supplier.supplierCode"]').val(),
					jqObj: $('input[name="supplier.supplierCode"]'),
					fieldRules: { 
						required :true,
						inputFilter:true,
						cnlength:50,
						remote: {remote规则用来调用服务端Action代码,与服务端进行实时交互							url: "supplierManager!ajaxValidateSuppliercode.action",
							type: "post",
							data: {
								suppliercode : GBK.encodeURI($('input[name="supplier.supplierCode"]').val()) ,
								supplierid: function() { return $('input[name=id]').val(); },
								random: Math.random()
							}
					  	}
					}
				})*/.addRule({
					labelName: "备注",
					fieldName: "supplier.remark",
					fieldType: "DBEdit",
					fieldValue: $('input[name="supplier.remark"]').val(),
					jqObj: $('input[name="supplier.remark"]'),
					fieldRules: {
						cnlength:500						
					}
				})/* .addRule({
					labelName: "开户银行",
					fieldName: "supplier.bankName",
					fieldType: "DBEdit",
					fieldValue: $('input[name="supplier.bankName"]').val(),
					jqObj: $('input[name="supplier.bankName"]'),
					fieldRules: {
						required :true,
						inputFilter:true						
					}
				})*/.addRule({
					labelName: "银行账号",
					fieldName: "supplier.bankAccount",
					fieldType: "DBEdit",
					fieldValue: $('input[name="supplier.bankAccount"]').val(),
					jqObj: $('input[name="supplier.bankAccount"]'),
					fieldRules: {
						//required :true,
						integer :true						
					}
				}).addRule({
					labelName: "办公电话",
					fieldName: "supplier.officeTel",
					fieldType: "DBEdit",
					fieldValue: $('input[name="supplier.officeTel"]').val(),
					jqObj: $('input[name="supplier.officeTel"]'),
					fieldRules: {
						//required :true,
						fixphone :true						
					}
				}).addRule({
					labelName: "手机",
					fieldName: "supplier.cellPhone",
					fieldType: "DBEdit",
					fieldValue: $('input[name="supplier.cellPhone"]').val(),
					jqObj: $('input[name="supplier.cellPhone"]'),
					fieldRules: {
						//required :true,
						phonenumber :true						
					}
				}).addRule({
					labelName: "电子邮件",
					fieldName: "supplier.email",
					fieldType: "DBEdit",
					fieldValue: $('input[name="supplier.email"]').val(),
					jqObj: $('input[name="supplier.email"]'),
					fieldRules: {
						//required :true,
						email :true						
					}
				}).addRule({
					labelName: "邮编",
					fieldName: "supplier.postcode",
					fieldType: "DBEdit",
					fieldValue: $('input[name="supplier.postcode"]').val(),
					jqObj: $('input[name="supplier.postcode"]'),
					fieldRules: {
						//required :true,
						postcode :true						
					}
				});
				

				return valiValueFrame;
			}
		}
	});
})(jQuery);