//global variable
var contextPath = '/mychat-core';
//sweet alert
document.write("<script language=javascript src='/mychat-core/resource/js/common/sweet-alert.min.js'></script>");
function successAlert(title,content){
	swal(title,content, "success");
}
function errorAlert(title,content){
	swal(title,content, "error");
}
function successAlert(content){
	swal("success",content, "success");
}
function errorAlert(title,content){
	swal("error",content, "error");
}
//翻页
function buildPageNav(curPage,maxPage,pageNav){
	var pageNavHtml = '';
	//前一页
	if (curPage=='1'){
		pageNavHtml+='<li> <a href="#" aria-label="Previous" onclick="searchUser(1)"><span aria-hidden="true" >&laquo;</span></a></li>';
	}else{
		pageNavHtml+='<li> <a href="#" aria-label="Previous" onclick="searchUser('+(curPage-1)+')"><span aria-hidden="true" >&laquo;</span></a></li>';
	}
	//更新页标签
	var pageCount=0;
	var pageTabHtml ='';
	for (var i=curPage-2;i<=maxPage;i++){
		if (i<=0) continue;
		pageCount++;
		if (pageCount>5) break;
		if (i==curPage){
			pageTabHtml+='<li  class="active"><a href="#" onclick="searchUser('+i+')">'+i+'</a></li>';
		}else{
			pageTabHtml+=' <li><a href="#" onclick="searchUser('+i+')">'+i+'</a></li>';
		}
		
	}
	//页数不够要单独处理
	var i=curPage-3;
	while (pageCount<5&&i>0){
		pageTabHtml=' <li><a href="#" onclick="searchUser('+i+')">'+i+'</a></li>'+pageTabHtml;
		i--;
		pageCount++;
	}
	pageNavHtml+=pageTabHtml;
	//下一页
	if (curPage==maxPage){
		pageNavHtml+='<li> <a href="#" aria-label="Next" onclick="searchUser('+maxPage+')"><span aria-hidden="true" >&raquo;</span></a></li>';
	}else{
		pageNavHtml+='<li> <a href="#" aria-label="Next" onclick="searchUser('+(curPage+1)+')"><span aria-hidden="true" >&raquo;</span></a></li>';
	}
	pageNav.html(pageNavHtml);
}