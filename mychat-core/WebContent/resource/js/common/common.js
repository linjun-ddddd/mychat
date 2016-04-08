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