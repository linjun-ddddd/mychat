# mychat
# About
  本案例是一个基于web的实时通讯demo，类似WebQQ等实时IM。<br/>
  用户通过浏览器，发送消息给对方，而对方可以在登录的情况下，实时接收到用户发过来的消息。<br/>
  消息之间的传递格式是JSON。<br/>
  大致的架构图如下。<br/>
  ![](https://github.com/lj654548718/mychat/blob/master/mychat-core/WebContent/resource/images/github/architecture.png)
# 展示
[ 主界面(聊天窗口) ]<br/>
用户之间互相发送消息<br/>
发送消息会通过ajax向后台发送一个请求，后台把请求放进消息队列。<br/>
接收消息则依赖前端的长连接进行实时获取。长连接的实现原理大致是用ajax的超时机制来实现。<br/>
![](https://github.com/lj654548718/mychat/blob/master/mychat-core/WebContent/resource/images/github/chat.png)

[ 搜索好友列表 ]<br/>
在输入框输入用户姓名，点击搜索即可把相关用户查找出来。<br/>
![](https://github.com/lj654548718/mychat/blob/master/mychat-core/WebContent/resource/images/github/friendlist.png)

[ 消息盒子 列表 ]<br/>
消息盒子对消息进行集中的处理，类似qq的消息盒子功能。盒子里面提供消息的查看、同意和拒绝操作。<br/>
![](https://github.com/lj654548718/mychat/blob/master/mychat-core/WebContent/resource/images/github/messageBox.png)
