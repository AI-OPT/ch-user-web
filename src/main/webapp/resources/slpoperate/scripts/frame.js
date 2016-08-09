$(function(){
$(".wrapper-left ul li li").click(function () {
                $(".wrapper-left ul li li").each(function () {
                    $(this).removeClass("current");
                });
                $(this).removeClass("active");
            });
});

//导航右侧信息弹出
$(function(){
$(".msg").click(function () {
   $(".msg-cnt").animate({right:'0'},200);
});
});

$(function(){
$(".p a").click(function () {
   $(".msg-cnt").animate({right:'-280px'},200);
});
});
//导航右侧信息弹出结束

//table切换
$(function(){
$(".pst-table ul li a").click(function () {
                $(".pst-table ul li a").each(function () {
                    $(this).removeClass("current");
                });
                $(this).addClass("current");
            });
$('.pst-table ul li a').click(function(){
  var index=$('.pst-table ul li a').index(this);
      if(index==0){
     $('#date1').show();
  $('#date2').hide();
   }
   if(index==1){
     $('#date2').show();
  $('#date1').hide();
   }
  }); 
});
//table切换结束

//点击展开
$(document).ready(function(){
  $(".form-label ul li .sos a").click(function () {
	  $(".open ").slideToggle(100);
	  $(".nav-form ").toggleClass("reorder remove");
	  });
});
//点击结束


			
//点击展开
$(function () {
    var st = 100;
    $('.group-ly a').click(function () {
		$(this).toggleClass("imga imgb");
		$(this).parent().parent().parent().parent().children('.group-list').slideToggle();
    });
});

//点击结束			

//已选中关闭
$(function(){
$(".nav-form .close1").click(function () {
	$(this).parent('.choice').hide();
	});
	});
	
//已选中关闭
$(function(){
$(".right-topnav .dclose").click(function () {
	$(".right-topnav").hide(300)
	});
	});
	
//table 点击展开
$(function () {
    $(".nav-tplist-table-none table tr .click a").click(function () {
		$(this).children('i').toggleClass("icon-minus icon-plus");
		$(this).parent().parent().parent().parent().parent().parent().parent().children('.zhank').slideToggle(100);
    });
});	
	
$(function () {
$(".order-list-table  li:last").css("border-right","none");
});

//商品管理table切换
$(function(){
$(".order-list-table ul li a").click(function () {
                $(".order-list-table ul li a").each(function () {
                    $(this).removeClass("current");
                });
                $(this).addClass("current");
            });
$('.order-list-table ul li a').click(function(){
  var index=$('.order-list-table ul li a').index(this);
      if(index==0){
     $('#date1').show();
  	$('#date2').hide();
  	$('#date3').hide();
  	$('#date4').hide();
  	$('#date5').hide();
  	$('#date6').hide();
  	
   }
   if(index==1){
     $('#date2').show();
  	 $('#date1').hide();
  	 $('#date3').hide();
  	 $('#date4').hide();
  	 $('#date5').hide();
  	 $('#date6').hide();
   }
   if(index==2){
     $('#date3').show();
  	 $('#date2').hide();
  	 $('#date1').hide();
  	 $('#date4').hide();
  	 $('#date5').hide();
  	 $('#date6').hide();   	
   }
   if(index==3){
     $('#date4').show();
  	 $('#date1').hide();
  	 $('#date2').hide();
  	 $('#date3').hide();
  	 $('#date5').hide();
  	 $('#date6').hide();   	
   }
    if(index==4){
     $('#date5').show();
  	 $('#date1').hide();
  	 $('#date2').hide();
  	 $('#date3').hide();
  	 $('#date4').hide();
  	 $('#date6').hide();   	
   }
    if(index==5){
     $('#date6').show();
  	 $('#date1').hide();
  	 $('#date2').hide();
  	 $('#date3').hide();
  	 $('#date4').hide();
  	 $('#date5').hide();   	
   }
  }); 
});
//table切换结束

//商品管理拒绝详情 弹出
jQuery(document).ready(function($) {
	$('.details').click(function(){
	$('.eject-mask').fadeIn(100);
	$('.eject-samll').slideDown(200);
	})
	$('.eject-samll-title .img').click(function(){
	$('.eject-mask').fadeOut(100);
	$('.eject-samll').slideUp(150);
	})
	$('.eject-samll-confirm .close-btn').click(function(){
	$('.eject-mask').fadeOut(100);
	$('.eject-samll').slideUp(150);
	})
})
//小弹出框450 商品管理拒绝详情 弹出
jQuery(document).ready(function($) {
	$('.first').click(function(){
	$('.eject-mask').fadeIn(100);
	$('.eject-samll-first').slideDown(200);
	})
	$('.eject-samll-title .img').click(function(){
	$('.eject-mask').fadeOut(100);
	$('.eject-samll-first').slideUp(150);
	})
})
//小弹出框450 生成虚拟库弹出
jQuery(document).ready(function($) {
	$('#small-eject1').click(function(){
	$('#eject-mask').fadeIn(100);
	$('#eject-samll').slideDown(200);
	})
	$('.eject-samll-title .img').click(function(){
	$('#eject-mask').fadeOut(100);
	$('#eject-samll').slideUp(150);
	})
})



//小弹出框450 生成虚拟库弹出
jQuery(document).ready(function($) {
	$('#small-eject2').click(function(){
	$('#eject-mask').fadeIn(100);
	$('#eject-samll-1').slideDown(200);
	})
	$('.eject-samll-title .img').click(function(){
	$('#eject-mask').fadeOut(100);
	$('#eject-samll-1').slideUp(150);
	})
})

//小弹出框450 生成虚拟库弹出
jQuery(document).ready(function($) {
	$('#small-eject3').click(function(){
	$('#eject-mask').fadeIn(100);
	$('#eject-samll-2').slideDown(200);
	})
	$('.eject-samll-title .img').click(function(){
	$('#eject-mask').fadeOut(100);
	$('#eject-samll-2').slideUp(150);
	})
})

//小弹出框450 生成虚拟库弹出
jQuery(document).ready(function($) {
	$('#small-eject4').click(function(){
	$('#eject-mask').fadeIn(100);
	$('#eject-samll-3').slideDown(200);
	})
	$('.eject-samll-title .img').click(function(){
	$('#eject-mask').fadeOut(100);
	$('#eject-samll-3').slideUp(150);
	})
})

//中弹出框600 通讯录详情 批量导入弹出
jQuery(document).ready(function($) {
	$('.upload').click(function(){
	$('.eject-mask').fadeIn(100);
	$('.eject-medium').slideDown(200);
	})
	$('.eject-medium-title .img').click(function(){
	$('.eject-mask').fadeOut(100);
	$('.eject-medium').slideUp(150);
	})
	$('.pst-bttton .cancel').click(function(){
	$('.eject-mask').fadeOut(100);
	$('.eject-medium').slideUp(150);
	})
})	
//中弹出框600 虚拟库存 查看详情
jQuery(document).ready(function($) {
	$('#medium-eject1').click(function(){
	$('#eject-mask').fadeIn(100);
	$('#eject-medium').slideDown(200);
	})
	$('.eject-medium-title .img').click(function(){
	$('#eject-mask').fadeOut(100);
	$('#eject-medium').slideUp(150);
	})
})
//大弹出框800  商品编辑 添加弹出弹出
jQuery(document).ready(function($) {
	$('.modify').click(function(){
	$('.eject-mask').fadeIn(100);
	$('.eject-large').slideDown(200);
	})
	$('.eject-large-title .img').click(function(){
	$('.eject-mask').fadeOut(100);
	$('.eject-large').slideUp(150);
	})
})

//大弹出框800  选择地市弹出
jQuery(document).ready(function($) {
	$('.city').click(function(){
	$('.eject-mask').fadeIn(100);
	$('.eject-large2').slideDown(200);
	})
	$('.eject-large-title .img').click(function(){
	$('.eject-mask').fadeOut(100);
	$('.eject-large2').slideUp(150);
	})
})
//弹出框带图标 删除弹出	
jQuery(document).ready(function($) {
	$('.eject-icon').click(function(){
	$('.eject-mask').fadeIn(100);
	$('#small1').slideDown(200);
	})
	$('.eject-samll-title .img').click(function(){
	$('.eject-mask').fadeOut(100);
	$('#small1').slideUp(150);
	})
	$('.eject-samll-confirm .close-btn').click(function(){
	$('.eject-mask').fadeOut(100);
	$('#small1').slideUp(150);
	})
})

//提示
jQuery(document).ready(function($) {
	$('.trash-close').click(function(){
	$('.eject-mask').fadeIn(100);
	$('#small2').slideDown(200);
	})
	$('.eject-samll-title .img').click(function(){
	$('.eject-mask').fadeOut(100);
	$('#small2').slideUp(150);
	})
	$('.eject-samll-confirm .close-btn').click(function(){
	$('.eject-mask').fadeOut(100);
	$('#small2').slideUp(150);
	})
})

//提示
jQuery(document).ready(function($) {
	$('.add').click(function(){
	$('.eject-mask').fadeIn(100);
	$('#small3').slideDown(200);
	})
	$('.eject-samll-title .img').click(function(){
	$('.eject-mask').fadeOut(100);
	$('#small3').slideUp(150);
	})
	$('.eject-samll-confirm .close-btn').click(function(){
	$('.eject-mask').fadeOut(100);
	$('#small3').slideUp(150);
	})
})

//搜索 结果弹出框
jQuery(document).ready(function($) {
	$('.eject-query').click(function(){
	$('.eject-mask').fadeIn(100);
	$('#large2').slideDown(200);
	})
	$('.eject-large-title .img').click(function(){
	$('.eject-mask').fadeOut(100);
	$('#large2').slideUp(150);
	})
})

//查询信息 结果弹出框
jQuery(document).ready(function($) {
	$('.eject-paging').click(function(){
	$('.eject-mask').fadeIn(100);
	$('#large1').slideDown(200);
	})
	$('.eject-large-title .img').click(function(){
	$('.eject-mask').fadeOut(100);
	$('#large1').slideUp(150);
	})
})	

//商品编辑展开更多 点击展开
$(function () {
    $(".cit-width .zk").click(function () {
		$(this).children('i').toggleClass("icon-angle-down  icon-angle-up");
		$(this).parents().children('.open').slideToggle(100);
    });
});	
//商品编辑上传图片弹出框 点击展开
$(function () {
    $(".int-zk").click(function () {
		$(this).parents().children('.mouse-open').slideToggle(100);
    });
});	
$(function () {
    $(".newly-build").click(function () {
		$(this).parents().children('.newly-build-onclick').slideToggle(100);
    });
});	
/**商品编辑 其他设置 全国 部分 table**/
$(function(){
$(".radioa").click(function () {
	$('#check1').show();
	$('#check2').hide();
});
});

$(function(){
$(".radiob").click(function () {
	$('#check2').show();
	$('#check1').hide();
});
});

/**商品编辑 其他设置 全国 部分 table**/
$(function(){
$(".radioc").click(function () {
	$('#check3').show();
	$('#check4').hide();
});
});

$(function(){
$(".radiod").click(function () {
	$('#check4').show();
	$('#check3').hide();
});
});

//搜索已选中关闭
$(function(){
$(".form-label .icon-remove-sign").click(function () {
	$(this).parent('.img').hide();
	});
	});  