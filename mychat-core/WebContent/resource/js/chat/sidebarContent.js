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