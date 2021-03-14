 jQuery(document).ready(function() {

    "use strict";

    // Init Theme Core    
    Core.init();

    // Init Demo JS     
    Demo.init();


    /* Init datepicker
    ------------------------------------------------------------------ */
    $("#datepicker1").datepicker({
      changeMonth: true,
      changeYear: true,
      showButtonPanel: false,
      prevText: '<i class="fa fa-chevron-left"></i>',
      nextText: '<i class="fa fa-chevron-right"></i>',
      onClose: function() {
        $("#datepicker1").trigger('blur');
      },
      beforeShow: function(input, inst) {
        var newclass = 'admin-form';
        var themeClass = $(this).parents('.admin-form').attr('class');
        var smartpikr = inst.dpDiv.parent();
        if (!smartpikr.hasClass(themeClass)) {
          inst.dpDiv.wrap('<div class="' + themeClass + '"></div>');
        }
      }
    });


    // Cache several DOM elements
    var pageHeader = $('.content-header').find('b');
    var adminForm = $('.admin-form');
    var options = adminForm.find('.option');
    var switches = adminForm.find('.switch');
    var buttons = adminForm.find('.button');
    var Panel = adminForm.find('.panel');

    // Form Skin Switcher
    $('#skin-switcher a').on('click', function() {
      var btnData = $(this).data('form-skin');

      $('#skin-switcher a').removeClass('item-active');
      $(this).addClass('item-active')

      adminForm.each(function(i, e) {
        var skins = 'theme-primary theme-info theme-success theme-warning theme-danger theme-alert theme-system theme-dark'
        var panelSkins = 'panel-primary panel-info panel-success panel-warning panel-danger panel-alert panel-system panel-dark'
        $(e).removeClass(skins).addClass('theme-' + btnData);
        Panel.removeClass(panelSkins).addClass('panel-' + btnData);
        pageHeader.removeClass().addClass('text-' + btnData);
      });

      $(options).each(function(i, e) {
        if ($(e).hasClass('block')) {
          $(e).removeClass().addClass('block mt15 option option-' + btnData);
        } else {
          $(e).removeClass().addClass('option option-' + btnData);
        }
      });

      // var sliders = $('.ui-timepicker-div', adminForm).find('.ui-slider');
      $('body').find('.ui-slider').each(function(i, e) {
        $(e).addClass('slider-primary');
      });

      $(switches).each(function(i, ele) {
        if ($(ele).hasClass('switch-round')) {
          if ($(ele).hasClass('block')) {
            $(ele).removeClass().addClass('block mt15 switch switch-round switch-' + btnData);
          } else {
            $(ele).removeClass().addClass('switch switch-round switch-' + btnData);
          }
        } else {
          if ($(ele).hasClass('block')) {
            $(ele).removeClass().addClass('block mt15 switch switch-' + btnData);
          } else {
            $(ele).removeClass().addClass('switch switch-' + btnData);
          }
        }

      });
      buttons.removeClass().addClass('button btn-' + btnData);
    });

    setTimeout(function() {
      adminForm.addClass('theme-primary');
      Panel.addClass('panel-primary');
      pageHeader.addClass('text-primary');

      $(options).each(function(i, e) {
        if ($(e).hasClass('block')) {
          $(e).removeClass().addClass('block mt15 option option-primary');
        } else {
          $(e).removeClass().addClass('option option-primary');
        }
      });

      // var sliders = $('.ui-timepicker-div', adminForm).find('.ui-slider');
      $('body').find('.ui-slider').each(function(i, e) {
        $(e).addClass('slider-primary');
      });

      $(switches).each(function(i, ele) {
        if ($(ele).hasClass('switch-round')) {
          if ($(ele).hasClass('block')) {
            $(ele).removeClass().addClass('block mt15 switch switch-round switch-primary');
          } else {
            $(ele).removeClass().addClass('switch switch-round switch-primary');
          }
        } else {
          if ($(ele).hasClass('block')) {
            $(ele).removeClass().addClass('block mt15 switch switch-primary');
          } else {
            $(ele).removeClass().addClass('switch switch-primary');
          }
        }
      });
      buttons.removeClass().addClass('button btn-primary');
    }, 800);



  });
 
 function doSome(url){
	 var mymessage=confirm("确定吗？"+url);
	 alert(mymessage);
	 if(mymessage==true){
		 window.location.href=url;
	 }
 }
 
 function removeSome(url){
	 url+="?";
	 var checked = $("#message-table").find("input:checkbox:checked");
	 for (var i = 0; i < checked.length; i++) {
		 var sn = checked[i].parentElement.parentElement.nextElementSibling.innerHTML;
		 url+=("sn="+sn);
		 if(i!=checked.length-1)url+="&";
	 }
	 if(checked.length>0){
		 var mymessage=confirm("确定吗？"+url);
		 alert(mymessage);
		    if(mymessage==true){
		    	window.location.href=url;
		    }
	 }else{
		 alert("没有选择数据！");
	 }
 }
 
 function removeSomePost(url,tokenName,token){
	 var checked = $("#message-table").find("input:checkbox:checked");
	 var removeId="";
	 for (var i = 0; i < checked.length; i++) {
		 var id = checked[i].parentElement.parentElement.nextElementSibling.innerHTML;
		 if(i!=0){
			 removeId+=",";
		 }
		 removeId+=id;
	 }
	 if(checked.length>0){
		 var mymessage=confirm("确定吗？"+removeId);
		 alert(mymessage);
		    if(mymessage==true){
		    	var params={}
		    	params['receiptsId']=removeId;
		    	params[tokenName]=token;
		   	 httpPost(url,params)
		    }
	 }else{
		 alert("没有选择数据！");
	 }
 }

 function countTime() {
	var sTime = $("#aa").val();
	//先通过ajax去后台请求数据，成功后携带数据进行跳转
	$.ajax({
		url : "{{ route('time') }}",
		type : "POST",
		dataType : 'json',
		data : {
			'sTime' : sTime,
			'_token' : "{{ csrf_token() }}"
		},
		success : function(data) {
			console.log(data);
			if (data.success) {
				var params = {
					'time' : data.sTime,
					'_token' : "{{ csrf_token() }}"
				}
				httpPost("test1", params); //test1为跳转的路由地址，params为携带的参数
			}
		}
	})
}

function httpPost(URL, PARAMS) {
	var temp = document.createElement("form");
	temp.action = URL;
	temp.method = "post";
	temp.style.display = "none";
	for ( var x in PARAMS) {
		var opt = document.createElement("textarea");
		opt.name = x;
		opt.value = PARAMS[x];
		temp.appendChild(opt);
	}
	document.body.appendChild(temp);
	temp.submit();
	return temp;
}
