//消息盒子相关
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