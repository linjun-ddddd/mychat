//好友列表相关
function initAccordian(){
	 var Accordion = function (el, multiple) {
        this.el = el || {};
        this.multiple = multiple || false;
        var links = this.el.find('.link');
        links.on('click', {
            el: this.el,
            multiple: this.multiple
        }, this.dropdown);
    };
    Accordion.prototype.dropdown = function (e) {
        var $el = e.data.el;
        $this = $(this), $next = $this.next();
        $next.slideToggle();
        $this.parent().toggleClass('open');
        if (!e.data.multiple) {
            $el.find('.submenu').not($next).slideUp().parent().removeClass('open');
        }
        ;
    };
    var accordion = new Accordion($('#accordion'), false);
}
function friendListClick(){
	if (prePanel=='1'){
		closePanel(true);
	}else{
		changeOptionContent($(".friendList_content"));
		//获取朋友列表
		getFriendList();
		
		prePanel='1';
	}
}
function getFriendList(){
	$.ajax({  
        type : "POST",  //提交方式  
        url : contextPath+"/friend/list.do",//路径  
        data : {
        },//数据，这里使用的是Json格式进行传输  
        dataType : "json",
        success : function(result) {//返回数据根据结果进行相应的处理 
        	var json = eval(result);
      		console.log("json status:"+json.status);
            if ( json.status=='1' ) {
            	updateFriendList(json);
            } else {  
                //alert("查询失败。。");
            	errorAlert("fail!!");
            }  
        },
        error : function(XMLHttpRequest, textStatus, errorThrown){ 
			console.log('error:'+textStatus+" | "+errorThrown);
			errorAlert("fail!!");
        }
    });
}
function updateFriendList(json){
	var friendList = $(".friendList_content .default .submenu");
	var data =json.data;
	var friendListHtml='';
	for (var i=0;i<data.length;i++){
		var curData = data[i];
		friendListHtml+='<li onclick="friendClick('+curData.id+')" class="friendItem"><img src="'+
			contextPath+'/resource/images/chat/upload/'+
			curData.icon+'" class="usericon contact__photo">';
		friendListHtml+='<div class="username textEllipsis">'+curData.name+'</div>';
		friendListHtml+='<div><span class="mysign">'+curData.mysign+'</span>';
		if (curData.sex=='1'){
			friendListHtml+='<img class="icon" src="'+contextPath+'/resource/images/chat/icon/male.png">';
		}else {
			friendListHtml+='<img class="icon" src="'+contextPath+'/resource/images/chat/icon/female.png">';
		}
		friendListHtml+='</div></li>'; 
	}
	friendList.html(friendListHtml);
	//更新好友总数
	$(".friendAmount").text("("+data.length+")");
}
/////执行的代码//////
initAccordian();