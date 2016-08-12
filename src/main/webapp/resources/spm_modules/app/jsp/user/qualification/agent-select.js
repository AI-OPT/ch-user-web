define('app/jsp/user/qualification/agent-select',
		function(require, exports, module) {
			'use strict';
			var $ = require('jquery'), 
			Validator = require('arale-validator/0.10.2/index'),
			Calendar = require('arale-calendar/1.1.2/index'), 
			Widget = require('arale-widget/1.2.0/widget'), 
			Dialog = require("artDialog/src/dialog"), 
			AjaxController = require('opt-ajax/1.0.0/index');
			// 实例化AJAX控制处理对象
			var ajaxController = new AjaxController();

			// 定义页面组件类
			var AgengSelectPager = Widget.extend({
				// 属性，使用时由类的构造函数传入
				attrs : {},
				Statics: {
		    		DEFAULT_PAGE_SIZE: 5,
		    		USER_LEFT_MNU_ID: "left_mnu_qualification_identify"
		    	},
				// 事件代理
				events : {
				// key的格式: 事件+空格+对象选择器;value:事件方法
				// "click [id='randomImg']":"_refrashVitentify",
				},
				init : function() {
					
				},
				// 重写父类
				setup : function() {
					AgengSelectPager.superclass.setup.call(this);
					//activeUserLeftMenu(AgengSelectPager.USER_LEFT_MNU_ID);
					//this._hideErroText();
					this._bindHandle();
				},
				//_hideInfo : function() {},
				// 带下划线的方法，约定为内部私有方法
				_bindHandle : function() {
					$("#toIdentity").on("click", this._toIdentity);
					},
				_toIdentity : function(){
					var personal = $("#personal");
					var enterprise = $("#enterprise")
					if(personal.hasClass("current")&&!enterprise.hasClass("current")){
						window.location.href = _base+"/user/qualification/toAgentPersonalPage";
					}
					if(!personal.hasClass("current")&&enterprise.hasClass("current")){
						window.location.href = _base+"/user/qualification/toAgentEnterprisePage";
					}
				}
			
		});
			module.exports = AgengSelectPager
		});

	function addPersonalClass(){
		$("#personal").removeClass("current").addClass("current");
		$("#enterprise").removeClass("current");
	}
	
	function addEnterpriseClass(){
		$("#personal").removeClass("current");
		$("#enterprise").removeClass("current").addClass("current");
	}
