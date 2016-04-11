
//长连接函数
var isRefresh = '0';
function longPoll(){
	
	//监听刷新、关闭事件
	window.onbeforeunload=function (){ 
		isRefresh='1';
		
		$.ajax({
	    	type : "POST",  //提交方式 
	        url: contextPath+"/longPoll/destory.do",
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
        url: contextPath+"/longPoll.do",
        //data: {"timed": new Date().getTime()},
        dataType: "json",
        timeout: 1000*60*5,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
			console.log("fail-"+textStatus);
			console.log('textStatus-'+isRefresh);
          	//longPolling(); // 递归调用
          	if (isRefresh=='0'){//刷新为'1'
          		setTimeout('longPoll()',2000);
          	}
          	//send message of abort
        },
        success: function (result) {
        	var json = eval(result);
        	console.log("success-"+json.status+":"+json.data);
        	if (json.status=='1'){
        		var msg=eval(json.data);
        		switch (msg.type){
        		case CHAT_MESSAGE_TYPE:
        			break;
        		
        		case ADD_FRIEND_MESSAGE_TYPE :
        			console.log("ADD_FRIEND_MESSAGE_TYPE:"+ADD_FRIEND_MESSAGE_TYPE);
        			flashOptionPanelItem(ADD_FRIEND_MESSAGE_TYPE);
        			break
        		
        		case UPDATE_FRIEND_LIST_MESSAGE_TYPE:
        			break;
        		}
        	}
        	//结束消息的话就不发送长连接了
        	if (isRefresh=='0'){//刷新或关闭，就不发长连接
	        	longPoll();
        	}
   		}
    });
}
var timerMap = new Object();
function flashOptionPanelItem(type){
	switch (type){
	case ADD_FRIEND_MESSAGE_TYPE:
		var obj = $(".option_panel .messageBox");
		var timer = setInterval(function(){
			$(obj).find("img").animate({marginLeft:"1.5rem"},400 )
				.animate({marginLeft:"0rem"},400);
		},2000);
		obj.css({"background":"rgb(226, 93, 93)"});
		break;
	}
	timerMap[type]=timer;
}
function clearFlashOptionPanel(type){
	clearInterval(timerMap[type]);
	switch (type){
		case ADD_FRIEND_MESSAGE_TYPE:
		var obj = $(".option_panel .messageBox");
		obj.css({"background":""});
		break;
	}
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
	}else{
		changeOptionContent($(".friendList_content"));
		prePanel='1';
	}
}
function messageBoxClick(){
	if (prePanel=='2'){
		closePanel(true);
	}else{
		//取消闪烁
		clearFlashOptionPanel(ADD_FRIEND_MESSAGE_TYPE);
		//更新消息
		getMsgByPage(1);
		//打开消息盒子
		changeOptionContent($(".messageBox_content"));
		prePanel='2';
	}
}
function getMsgByPage(page){
	$.ajax({  
        type : "POST",  //提交方式  
        url : contextPath+"/msgBox/getMessage.do",//路径  
        data : {
            "page" : page
        },//数据，这里使用的是Json格式进行传输  
        dataType : "json",
        success : function(result) {//返回数据根据结果进行相应的处理 
        	var json = eval(result);
      		console.log("json status:"+json.status);
            if ( json.status=='1' ) {
            	updateMsgBox(json);
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
function updateMsgBox(json){
	var data=json.data;
	var messageBox = $(".messageBox_content .msgContainer");
	var messageListHtml='';
	for (var i=0;i<data.length;i++){
		var curData=data[i];
		var nodeHtml='';
		//console.log("curData.type:"+curData.data);
		//console.log("ADD_FRIEND_MESSAGE_TYPE:"+ADD_FRIEND_MESSAGE_TYPE);
		switch (curData.type){
		case ADD_FRIEND_MESSAGE_TYPE:
			nodeHtml+='<div class="alert alert-info" role="alert">';
			nodeHtml+='<div class="message">'+curData.data+'</div>';
			nodeHtml+='<div class="time_msg">'+curData.sendDate+'</div>';
			nodeHtml+='<div class="button_wrap">';
			if (curData.status==MESSAGE_UNDEAL_STATUS){
				nodeHtml+='<button type="button" class="btn btn-success" onclick="agreeRequest(this)" msgId="'+curData.id+'" fromUserId="'+curData.fromUserId+'">同意</button>&nbsp;';
				nodeHtml+='<button type="button" class="btn btn-danger" onclick="rejectRequest(this)" msgId="'+curData.id+'" fromUserId="'+curData.fromUserId+'">拒绝</button>';
			}else if (curData.status==MESSAGE_DEAL_AGREE_STATUS){
				nodeHtml+='<button type="button" class="btn btn-success">已同意</button>';
			}else if (curData.status==MESSAGE_DEAL_REJECT_STATUS){
		   		nodeHtml+='<button type="button" class="btn btn-danger">已拒绝</button>';
			}
			nodeHtml+='</div></div>';
			break;
		}
		messageListHtml+=nodeHtml;
	}
	messageBox.html(messageListHtml);
	//翻页按钮
	var curPage = parseInt(json.curPage);
	var maxPage = parseInt(json.maxPage);
	var pageNav = $(".messageBox_content .pagination");
	var func = "getMsgByPage";
	buildPageNav(curPage,maxPage,pageNav,func);
}
//
function searchUserClick(){
	if (prePanel=='3'){
		closePanel(true);
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
	prePanel='';
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
	var func = "searchUser";
	buildPageNav(curPage,maxPage,pageNav,func);
  
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
            	successAlert("请求发送成功!");
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
	$.ajax({  
        type : "POST",  //提交方式  
        url : contextPath+"/msgBox/agreeMessage.do",//路径  
        data : {
        	"messageId":$(obj).attr("msgid"),
        	"fromUserId":$(obj).attr("fromUserId")
        },//数据，这里使用的是Json格式进行传输  
        dataType : "json",
        success : function(result) {//返回数据根据结果进行相应的处理 
        	var json = eval(result);
        	console.log("json.status:"+json.status);
            if ( json.status=='1' ) {
            	updateMsg(obj,true);
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
//拒绝申请
function rejectRequest(obj){
	$.ajax({  
        type : "POST",  //提交方式  
        url : contextPath+"/msgBox/rejectMessage.do",//路径  
        data : {
        	"messageId":$(obj).attr("msgid"),
        	"fromUserId":$(obj).attr("fromUserId")
        },//数据，这里使用的是Json格式进行传输  
        dataType : "json",
        success : function(result) {//返回数据根据结果进行相应的处理 
        	var json = eval(result);
        	console.log("json.status:"+json.status);
            if ( json.status=='1' ) {
            	updateMsg(obj,false);
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
function updateMsg(obj,isAgree){
	if (isAgree){
		$(obj).parent().html('<button type="button" class="btn btn-success">已同意</button>');
	}else{
		$(obj).parent().html('<button type="button" class="btn btn-danger">已拒绝</button>');
	}
	
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
            	window.location=contextPath+"/login.action";
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
longPoll();