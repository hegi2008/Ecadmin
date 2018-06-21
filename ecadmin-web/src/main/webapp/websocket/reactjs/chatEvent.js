var chat = {
	updateFriend: new signals.Signal(),
	//聊天窗口增加消息
	message: new signals.Signal(),
	cilckItem: new signals.Signal(),
	closeWindow: new signals.Signal(),
	//通过websocket发送消息
	sendMessage: new signals.Signal(),
	//更新消息提示数量
	messageCount: new signals.Signal(),
	//滚动条滚动到底部
	sroll: new signals.Signal(),
	//好友查找
	search: new signals.Signal()
};
var path = "http://192.168.10.161:8096/";