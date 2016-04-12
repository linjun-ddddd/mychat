////全局变量/////
//当前聊天用户id
var curChatUserId = '';
//保存当前聊天记录
var chatMap = new Object();
//右侧panel记录
var prePanel='';

//长连接相关
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
          		setTimeout('longPoll()',1000*20);
          	}
          	//send message of abort
        },
        success: function (result) {
        	var json = eval(result);
        	console.log("success-"+json.status);
        	if (json.status=='1'){
        		var msg=eval(json.data);
        		switch (msg.type){
        		case CHAT_MESSAGE_TYPE:
        			packetLeftChat(msg);
        			//如果当前选中该用户，更新聊天信息
        			if (msg.fromuserid!=curChatUserId){
        				flashFriendList(json.fromuser);
        			}else{
        				//滚动到底部
        				var messageContent = $(".chat .chat__messages");
        				var scrollHeight = messageContent[0].scrollHeight;
        				messageContent.scrollTop(scrollHeight);
        			}
        			break;
        		
        		case ADD_FRIEND_MESSAGE_TYPE :
        			flashOptionPanelItem(ADD_FRIEND_MESSAGE_TYPE);
        			break
        		
        		case UPDATE_FRIEND_LIST_MESSAGE_TYPE:
        			insertHeadLastChatList(json.fromuser);
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
//右侧边栏相关
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
//发送聊天消息
function sendMessage(sendBtn){
	var friendId = $(sendBtn).attr("friendid");
	$.ajax({  
        type : "POST",  //提交方式  
        url : contextPath+"/chat/send.do",//路径  
        data : {
            "toUserId" : friendId,
            "msgData" : $(".chat .chat__input").val()
        },//数据，这里使用的是Json格式进行传输  
        dataType : "json",
        success : function(result) {//返回数据根据结果进行相应的处理 
        	var json = eval(result);
      		console.log("json status:"+json.status);
            if ( json.status=='1' ) {
            	updateChat(json.message);
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
function updateChat(json){
	var chatMsg = $(".chat .chat__input").val();
	 $(".chat .chat__input").val('');
	 packetRightChat(json);
	//滚动到底部
	var messageContent = $(".chat .chat__messages");
	var scrollHeight = messageContent[0].scrollHeight;
	messageContent.scrollTop(scrollHeight);
}
function packetRightChat(chatMsg){
	var html = '<div class="chat__msgRow">';
	html+='<div class="chat__message notMine">'+chatMsg.data+'</div></div>';
	$(".chat .chat__messages").append(html);
	//console.log(chatMsg.toUserId);
	if (chatMap[chatMsg.toUserId]==null){
		chatMap[chatMsg.toUserId]='';
	}
	chatMap[chatMsg.toUserId]+=html;
}
function packetLeftChat(chatMsg){
	var html = '<div class="chat__msgRow">';
	html+='<div class="chat__message mine">'+chatMsg.data+'</div></div>';
	//如果当前选中该用户，更新聊天信息
	if (curChatUserId==chatMsg.fromuserid){
		$(".chat .chat__messages").append(html);
	}
	//console.log(chatMsg.fromuserid);
	if (chatMap[chatMsg.fromuserid]==null){
		chatMap[chatMsg.fromuserid]='';
	}
	chatMap[chatMsg.fromuserid]+=html;
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
//回车发送消息
function sendMessageKeyDown(event){
	console.log(event.keyCode);
	  if (event.keyCode==13) {  //回车键的键值为13
		  sendMessage($(".chat .send_btn"));
	  }
}

//引入其他js
document.write("<script type='text/javascript' src='"+contextPath+"/resource/js/chat/sidebarContent.js'></script>");
document.write("<script type='text/javascript' src='"+contextPath+"/resource/js/chat/friendListPanel.js'></script>");
document.write("<script type='text/javascript' src='"+contextPath+"/resource/js/chat/messageBoxPanel.js'></script>");
document.write("<script type='text/javascript' src='"+contextPath+"/resource/js/chat/searchUserPanel.js'></script>");
document.write("<script type='text/javascript' src='"+contextPath+"/resource/js/chat/otherItemPanel.js'></script>");

/////执行的代码//////
longPoll();