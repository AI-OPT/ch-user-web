define('util/slp_dialog', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    //定义页面组件类
    var SLPDialog = function (options, ok, cancel) {
    	var _this = this;
        // 确定按钮文本
        var okValue= 'ok';
        // 取消按钮文本
        var cancelValue= 'cancel';
        //标题
        var title = '提示';
        //提示信息
        var message = '';
        //按钮组
        var button = null;
        
    	title = options.title?option.title:this.title;
    	message = options.message?option.message:this.message;
    	// 按钮组
        if (!$.isArray(options.button)) {
            options.button = [];
        }
    	if(options.ok!== undefined && typeof options.ok=='function'){
			options.ok = ok;
		}
    	if (options.ok) {
            options.button.push({
                id: 'ok',
                value: options.okValue,
                callback: options.ok,
                autofocus: true
            });
        }
    	if(options.cancel!== undefined && typeof options.cancel=='function'){
			options.cancel= cancel;
		}
    	if (options.cancel) {
            options.button.push({
                id: 'cancel',
                value: options.cancelValue,
                callback: options.cancel,
                display: options.cancelDisplay
            });
        }
    };
    return SLPDialog;
});

