//左侧常用联系人相关
function insertHeadLastChatList(user){
	var addHtml = '';
	addHtml+='<div class="contact contactNewMessage" onclick="friendClick('+user.id+')" userid="'+user.id+'">';
	addHtml+='<img src="'+contextPath+'/resource/images/chat/upload/'+user.icon+'" alt="" class="contact__photo" />';
	addHtml+='<span class="contact__name textEllipsis">'+user.name+'</span>';
	addHtml+='<span class="contact__status online"></span></div>';
	
	var friendList = $(".sidebar-content .contact_list");
	var preHtml = friendList.html();
	friendList.html(addHtml+preHtml);
}
function flashFriendList(user){
	var targetItem = null;
	var contactList =$(".sidebar-content .contact_list");
	contactList.children(".contact").each(function(index,element){
		var userid=$(element).attr("userid");
		if (userid==user.id){
			targetItem=$(element);
		}
	});
	//删除当前节点
	if (targetItem!=null){
		targetItem.remove();
	}
	//在头部插入新节点
	insertHeadLastChatList(user);
}
//把用户移到最顶
function moveUserHead(userid){
	var contactList =$(".sidebar-content .contact_list");
	contactList.children(".contact").each(function(index,element){
		var _userid=$(element).attr("userid");
		if (userid==_userid){
			targetItem=$(element);
		}
	});
	//删除当前节点,并上移
	if (targetItem!=null){
		var cloneItem=targetItem.clone();
		targetItem.remove();
		var friendList = $(".sidebar-content .contact_list");
		var html = '<div class="contact contactActive" onclick="friendClick('+userid+')" userid="'+userid+'">';
		html+=cloneItem.html();
		html+='</div>';
		friendList.html(html+friendList.html());
	}
}
//常用联系人搜索
var preKeyWord = "";
function filterFriendList(obj){
	var keyWord = $(obj).val().toLowerCase();
	if (preKeyWord==keyWord) return;
	preKeyWord=keyWord;
	var contactList =$(".sidebar-content .contact_list");
	contactList.children(".contact").each(function(index,element){
		var name=$(element).find(".contact__name").text().toLowerCase();
		//console.log(name);
		if (name.indexOf(keyWord)==-1){
			$(element).css({"display":"none"});
		}else{
			$(element).css({"display":"flex"});
		}
	});
}