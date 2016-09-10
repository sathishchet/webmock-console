var stompClient = null;

function connect() {
	var socket = new SockJS("/webmock-console/gs-guide-websocket");
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		console.log("connected: " + frame);
		stompClient.subscribe("/topic/relay", function(data) {
			if (data.body != null) {
				setData(JSON.parse(data.body));
			}
		});
	});
}

function setData(obj) {
	msg = '';
	if (obj.rand > 75) {
		msg = msg.concat('<div class="alert alert-success">');
	} else if (obj.rand > 50) {
		msg = msg.concat('<div class="alert alert-info">');
	} else if (obj.rand > 25) {
		msg = msg.concat('<div class="alert alert-warning">');
	} else {
		msg = msg.concat('<div class="alert alert-danger">');
	}
	msg = msg.concat('<strong>Random: ' + obj.rand + '</strong>. id: ' + obj.id
			+ ', Thread Name: ' + obj.name);
	msg = msg.concat('</a>');
	$(".list-group.relay").prepend(msg);
}
function disconnect() {
	if (stompClient != null) {
		stompClient.disconnect();
	}
	console.log("Disconnected..")
}
function packet() {
	$.ajax({
		url : "datasource/request/4",
		method : "GET",
	}).done(function(data) {
		console.log("Success!! " + data)
	});
}
$(function() {
	connect();
	$(".packet").click(function() {
		packet()
	});
	packet();

	$(".disconnect").click(function() {
		disconnect()
	});
})