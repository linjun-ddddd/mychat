
//长连接函数
var isRefresh = '0';
function longPoll(){
	
	//监听刷新、关闭事件
	window.onbeforeunload=function (){ 
		isRefresh='1';
		
		$.ajax({
	    	type : "POST",  //提交方式 
	        url: "${pageContext.request.contextPath}/main/chat/longPoll!destory",
	        //data: {"timed": new Date().getTime()},
	        dataType: "json",
	        timeout: 1000*60*5,
	        error: function (XMLHttpRequest, textStatus, errorThrown) {
	        },
	        success: function (result) {
	   		}
	    });
		
	}
	
	//发起长连接
	$.ajax({
    	type : "POST",  //提交方式 
        url: "",
        //data: {"timed": new Date().getTime()},
        dataType: "json",
        timeout: 1000*60*5,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
			console.log("fail-"+textStatus);
			console.log('textStatus-'+isRefresh);
          	//longPolling(); // 递归调用
          	if (isRefresh=='0'){//刷新为'1'
          		setTimeout('longPolling()',2000);
          	}
          	//send message of abort
        },
        success: function (result) {
        	var json = eval(result);
        	console.log("success-"+json.status+":"+json.data);
        	if (json.status=='1'){
        	}
        	//结束消息的话就不发送长连接了
        	if (isRefresh=='0'){//刷新或关闭，就不发长连接
	        	longPolling();
        	}
   		}
    });
}
//init function
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

//panel click
var prePanel='';
function friendListClick(){
	if (prePanel=='1'){
		closePanel(true);
		prePanel='';
	}else{
		changeOptionContent($(".friendList_content"));
		prePanel='1';
	}
}
function messageBoxClick(){
	if (prePanel=='2'){
		closePanel(true);
		prePanel='';
	}else{
		changeOptionContent($(".messageBox_content"));
		prePanel='2';
	}
}
function searchUserClick(){
	if (prePanel=='3'){
		closePanel(true);
		prePanel='';
	}else{
		changeOptionContent($(".searchUser_content"));
		prePanel='3';
	}
}
function otherItemClick(){
	changeOptionContent($(".otherItem_content"));
}
function changeOptionContent(obj){
	$(".background").fadeIn();
	closePanel(false);
	$(obj).animate({left:"0rem"},300,function(){

	});
}
function closePanel(isFadeOut){
	$(".option_content").each(function(index,element){
		if ($(element).css("left")!="0rem"){
			$(element).animate({left:"35rem"},200,function(){
				
			});
		}
	});
	if (isFadeOut){
		$(".background").fadeOut();
	}
}
//profile click
var isSiderOpen =false;
var isSiderAnimating=false;
function profileClick(){
	if( isSiderAnimating ) return false;
	isSiderAnimating = true;
	if (isSiderOpen){
		$(".left_sider").animate({left:"-22rem"},360,function(){
			isSiderOpen = false;
			isSiderAnimating=false;
		});
	}else{
		$(".left_sider").animate({left:"0rem"},360,function(){
			isSiderOpen = true;
			isSiderAnimating=false;
		});
	}
	
	
}


///// click ///////
//发送聊天消息
function sendMessage(){
	
}
//查找好友
function searchUser(page){
	var searchName=$("input[name='searchName']");
	$.ajax({  
        type : "POST",  //提交方式  
        url : contextPath+"/search/search.do",//路径  
        data : {
            "username" : searchName.val(),
            "page" : page
        },//数据，这里使用的是Json格式进行传输  
        dataType : "json",
        success : function(result) {//返回数据根据结果进行相应的处理 
        	var json = eval(result);
      		console.log("json status:"+json.status);
            if ( json.status=='1' ) {
            	updateSearchList(json);
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
function updateSearchList(json){
	//更新列表
	var friendList=$(".searchUser_content .list-group");
	var friendHtml=''
	var data=json.data;
	for (var i=0;i<data.length;i++){
		var curData = data[i];
		friendHtml+='<li class="list-group-item">';
		friendHtml+='<img src="'+contextPath+'/resource/images/chat/upload/'+curData.icon+'" class="usericon">';
		friendHtml+='<div class="username">'+curData.name+'</div>';
		friendHtml+='<div><span class="mysign">'+curData.mysign+'</span>';
		friendHtml+='<img class=\'icon\' src="'+contextPath+'/resource/images/chat/icon/6.png" onclick="addFriend(this)" userid=\''+curData.id+'\'>';
		if (curData.sex=='1'){//男生
			friendHtml+='<img class=\'icon\' src="'+contextPath+'/resource/images/chat/icon/male.png"></div></li>';
		}else{//0为女生
			friendHtml+='<img class=\'icon\' src="'+contextPath+'/resource/images/chat/icon/female.png"></div></li>';
		}
	}
	friendList.html(friendHtml);
	//更新翻页按钮
	var curPage = parseInt(json.curPage);
	var maxPage = parseInt(json.maxPage);
	var pageNav = $(".searchUser_content .pagination");
	buildPageNav(curPage,maxPage,pageNav);
  
}
//添加好友
function addFriend(obj){
	var userid = $(obj).attr("userid");
	$.ajax({  
        type : "POST",  //提交方式  
        url : contextPath+"/search/addFriend.do",//路径  
        data : {
            "userid" : userid
        },//数据，这里使用的是Json格式进行传输  
        dataType : "json",
        success : function(result) {//返回数据根据结果进行相应的处理 
        	var json = eval(result);
      		console.log("json:"+json);
        	console.log("json.data:"+json.data);
            if ( json.status=='1' ) {
            	
            } else {  
                //alert("查询失败。。");
            	errorAlert("fail!!");
            }  
        },
        error : function(XMLHttpRequest, textStatus, errorThrown){ 
        	errorAlert('error:'+textStatus+" | "+errorThrown);
        }
    }); 
}
//同意申请
function agreeRequest(obj){
	
}
//拒绝申请
function rejectRequest(obj){
	
}
//退出登录
function loginOut(obj){
	$.ajax({  
        type : "POST",  //提交方式  
        url : contextPath+"/loginOut.action",//路径  
        data : {
        },//数据，这里使用的是Json格式进行传输  
        dataType : "json",
        success : function(result) {//返回数据根据结果进行相应的处理 
        	var json = eval(result);
      		console.log("json:"+json);
        	console.log("json.data:"+json.data);
            if ( json.status=='1' ) {
            	window.location=contextPath+"/login.html";
            } else {  
                //alert("查询失败。。");
            	errorAlert("fail!!");
            }  
        },
        error : function(XMLHttpRequest, textStatus, errorThrown){ 
        	errorAlert('error:'+textStatus+" | "+errorThrown);
        }
    }); 
}
//////
initAccordian();
//longPoll();