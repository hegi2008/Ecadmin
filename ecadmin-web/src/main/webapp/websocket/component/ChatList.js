var audio = document.getElementById("audio");
var FriendsBox = React.createClass({

	getInitialState: function() {
		return {
			friends: [],
			me: {},
			search: [], //好友搜索保存数组
			is: false, //是否是搜索操作
			display: {
				show: false,
				style: {
					"right": "-230px"
				},
				bootom: {
					"marginLeft": "-230px"
				}
			}
		};
	},
	componentDidMount: function() {
		var _this = this;
		$.ajax({
			"url": _this.props.url,
			"type": "POST",
			success: function(data) {
				_this.setState({
					friends: data.data,
					me: data.me
				});
			},
			error: function() {
				alert("网络错误");
			}
		});
		//添加更新好友列表监听
		chat.updateFriend.add(function(data, friend) {
			if (data.online) {
				var find = _.findIndex(_this.state.friends, function(item) {
					return item.id == data.id;
				});
				if (find == -1) {
					_this.state.friends.push(friend);
					_this.setState(null);
				}
			} else {
				_.remove(_this.state.friends, function(item) {
					return item.id == data.id;
				});
				_this.setState(null);
			}
		});
	},
	search: function(e) {
		var value = e.target.value;
		var friends = this.state.friends;
		if (value || value === 0) {
			var search = friends.map(function(item, key) {
				if (item.name.indexOf(value) != -1) {
					return item;
				}
			});
			this.setState({
				search: search,
				is: true
			});
		} else {
			this.setState({
				search: [],
				is: false
			});
		}
	},
	display: function() {
		var display = this.state.display.show ? {
			show: false,
			style: {
				"right": "-230px"
			},
			bootom: {
				"marginLeft": "-230px"
			}
		} : {
			show: true,
			style: {
				"right": "0"
			},
			bootom: {
				"marginLeft": "0"
			}
		};
		this.setState({
			display: display
		});
	},
	render: function() {
		var friends = this.state.is ? this.state.search : this.state.friends;
		return (
			<div className="friendsbox" style= {this.state.display.style}>
				<div className="searchtop">
					<i className="searchicon"></i>
					<input type="text" className="searchinput" onBlur={this.search}/>
				</div>
			
				<div className="tab">
					<span className="tabnow"><i className="tabfriend"></i></span>
					<span><i className="tabgroup"></i></span>
					<span style={{borderRight:'none'}}><i className="tablatechat"></i></span>
				</div>
			
				<ul className="list" style={{display:"block"}}>
					<li className="parentnode liston">
					{
						!this.state.is &&  <h5>
							<i></i>
							<span className="parentname">在线好友</span>
						    <em className="parentnum">({friends.length})</em>
						</h5>
					}
					<ul className="chatlist">
						<FriendList  friends={friends}/>
					</ul>
					</li>
				</ul>

			<ul className="bottom" style={this.state.display.bootom}>
				<li className="online">
					<i className="nowstate"></i><span id="onlinetex">在线</span>
					<div className="setonline" style={{display:"none"}}>
						<span><i></i>在线</span>
						<span className="setoffline"><i></i>隐身</span>
					</div>
				</li>
				<li className="mymsg" title="我的私信">
					<i></i>
					<a href="javascript:void" target="_blank"></a>
				</li>
				<li className="seter" title="设置">
					<i></i>
					<div></div>
				</li>
				<li className="hide" id="hide" onClick={this.display}><i></i></li>
			</ul>
			 <ChatBoxList me={this.state.me}/> 
		</div>
		);
	}
});
//好友列表项
var FriendList = React.createClass({
	propTypes: {
		friends: React.PropTypes.array
	},
	getDefaultProps: function() {
		return {
			friends: []
		};
	},
	render: function() {
		var friendsList = this.props.friends.map(function(items, key) {
			return (
				<FriendItem friendItem={items} />
			);
		});
		return (
			<div>{friendsList}</div>
		);
	}
});
//好友单项
var FriendItem = React.createClass({
	propTypes: {
		friendItem: React.PropTypes.object
	},
	getDefaultProps: function() {
		return {
			friendItem: {},
			tips: 0
		};
	},
	getInitialState: function() {
		return {
			tips: 0
		};
	},
	componentDidMount: function() {
		var items = this.props.friendItem;
		var _this = this;
		chat.messageCount.add(function(id) {
			if (id == items.id) {
				var tips = _this.state.tips + 1;
				_this.setState({
					tips: tips
				});
				audio.play();
			}
		});
	},
	friendChat: function() {
		if (this.state.tips > 0) {
			this.setState({
				tips: 0
			});
		}
		chat.cilckItem.dispatch(this.props.friendItem);
	},
	render: function() {
		var items = this.props.friendItem;
		return (
			<div>
				{
					items.name != undefined && items.face!= undefined && 
					<li onClick={this.friendChat} key={items.id}>
						<img src={path + items.face} className="face"></img>
						<span className="name">{items.name}</span>
						{
							this.state.tips != 0 && <i className='tips'>{this.state.tips}</i>
						}
					</li>
				}
			</div>
		);
	}
});

var ChatBoxList = React.createClass({

	getInitialState: function() {
		return {
			chatPersions: [], //所有聊天人员数组
			front: -1, //需要显示聊天窗口的人员id
			message: {}
		};
	},
	componentDidMount: function() {
		var _this = this;
		var front = null;
		//触发项目点击
		chat.cilckItem.add(function(item) {
			if (_.findIndex(_this.state.chatPersions, function(o) {
					return o.id == item.id;
				}) == -1) {
				_this.state.chatPersions.push(item);
				front = item.id;
			} else {
				front = _this.state.front == item.id ? -1 : item.id;
			}
			//触发重新渲染
			_this.setState({
				front: front
			});
		});
		//触发关闭窗口
		chat.closeWindow.add(function(front) {
			_this.setState({
				front: front
			});
		});
		//触发消息更新
		chat.message.add(function(id, msg) {
			var message = _this.state.message;
			var _id = id.toString();
			//检查是否有该用户的消息数组
			if (!message[_id]) {
				message[_id] = [];
			}
			message[_id].push(msg);
			//检查用户聊天窗口是否打开
			if (id == _this.state.front) {
				//重新render界面
				_this.setState(null);
			} else {
				//用户未打开消息窗口,消息提示
				chat.messageCount.dispatch(id);
			}
		});
	},
	render: function() {
		var _this = this;
		return (
			<div>
				{
					this.state.chatPersions.map(function(items,key){
						if(items.id == _this.state.front){
							return <ChatBox  message= {_this.state.message[items.id.toString()]} me={_this.props.me} person={items} key={items.id} display={true}/>;
						}else{
							return <ChatBox  message= {_this.state.message[items.id.toString()]} me={_this.props.me} person={items} key={items.id} display={false}/>;
						}
					})
				}
			</div>
		);
	}
});

var ChatBox = React.createClass({
	propTypes: {
		person: React.PropTypes.object
	},
	getDefaultProps: function() {
		return {
			display: false,
			person: {},
			message: []
		};
	},
	componentDidMount: function() {
		var _this = this;
		var box = this.refs.chatBox.getDOMNode();
		$(box).slimScroll({
			width: 'auto', //可滚动区域宽度
			height: '100%', //可滚动区域高度
			size: '5px', //组件宽度
			color: '#000', //滚动条颜色
			position: 'right', //组件位置：left/right
			distance: '0px', //组件与侧边之间的距离
			start: 'top', //默认滚动位置：top/bottom
			opacity: .4, //滚动条透明度
			alwaysVisible: false, //是否 始终显示组件
			disableFadeOut: true, //是否 鼠标经过可滚动区域时显示组件，离开时隐藏组件
		});
		chat.sroll.add(function() {
			box.scrollTop = box.scrollHeight + 50;
		});
		//回车键监听
		$(window).on("keydown", function(e) {
			if (e.keyCode == 13) {
				_this.sendMessage();
				e.preventDefault();
			}
		});
	},
	closeWindow: function() {
		chat.closeWindow.dispatch(-1);
	},
	sendMessage: function() {
		var msg = {},
			now = new Date();
		msg.content = this.refs.msg.getDOMNode().value;
		msg.time = this.format(now.getHours()) + ':' + this.format(now.getMinutes()) + ':' + this.format(now.getSeconds());
		msg.id = this.props.me.id;
		msg.to = this.props.person.id;
		//清空输入框
		this.refs.msg.getDOMNode().value = '';
		//触发发送消息
		chat.message.dispatch(this.props.person.id, msg);
		chat.sendMessage.dispatch(msg);
	},
	format: function(time) {
		return time < 10 ? '0' + time : time;
	},
	render: function() {
		var show = this.props.display == false ? {
			'display': 'none'
		} : {
			'display': 'block'
		};
		return (
			<div className="chatbox" style={show}>
			<h6>
				<span className="move" style={{"cursor": "move"}}></span>
				<a href="javascript:void" className="chatface" target="_blank"><img src={path + this.props.person.face}></img></a>
				<a href="javascript:void" className="chatname" target="_blank">{this.props.person.name}</a>
				<span className="rightbtn">
					<i className="setmin"></i>
					<i className="close" onClick={this.closeWindow}></i>
				</span>
			</h6>
			<div className="chat">
				<div className="chatarea">
					<ul className="chatview chatthis" ref="chatBox">
						<Mssage box= {this.box} message= {this.props.message} me = {this.props.me} person={this.props.person}/>
					</ul>
				</div>
				<div className="tool">
					<i className="addface"></i>
					<a href="javascript:void"><i className="addimage" title="上传图片"></i></a>
					<a href="javascript:void"><i className="addfile" title="上传附件"></i></a>
					<a href="javascript:void" className="searchchatlog"><i></i>聊天记录</a>
				</div>
				<textarea className="write" ref="msg"></textarea>
				<div className="send">
					<div className="sendbtn" onClick={this.sendMessage}>发送<span className="enter"><em class="zero1"></em></span></div>			
					<div className="sendtype">
					</div>
				</div>
			</div>
			</div>
		);
	}
});

var Mssage = React.createClass({

	componentDidUpdate: function() {
		//滚动条事件
		chat.sroll.dispatch();
	},
	render: function() {
		var me = this.props.me;
		var person = this.props.person;
		var code = this.props.message.map(function(item, key) {
			if (item.id == me.id) {
				return (
					<li className="chateme">
						<div className="chatuser">
							<span className="chattime">{item.time}</span>
							<span className="username">我</span>
							<img src={path + me.face}></img>
						</div>
						<div className="chatsay">
								{item.content}
							<em className="zero"></em>
						</div>
					</li>
				);
			} else {
				return (
					<li className="clearfix">
					   	<div className="chatuser">
							<img src={path + person.face} alt=""></img>
							<span className="username">{person.name}</span>
							<span className="chattime">{item.time}</span>
						</div>
						<div className="chatsay">
								{item.content}
							<em className="zero"></em>
						</div>
					</li>
				);
			}
		});
		return (
			<div>{code}</div>
		);
	}
});