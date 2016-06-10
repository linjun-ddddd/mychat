//查找好友相关
function searchUserClick(){
	if (prePanel=='3'){
		closePanel(true);
	}else{
		changeOptionContent($(".searchUser_content"));
		prePanel='3';
	}
}
//查找好友
function searchUser(page){
	//var searchName=$(".searchUserNameInput");
	var searchName=$("input[name='searchUserNameInput']");
	console.log(searchName.val());
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