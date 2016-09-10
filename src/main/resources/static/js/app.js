var stompClient = null;

function connect() {
	var socket = new SockJS("/webmock-console/gs-guide-websocket");
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		console.log("connected: " + frame);
		stompClient.subscribe("/topic/greeting", function(data) {
			if (data.body != null) {
				setData(JSON.parse(data.body).datasource);
			}
		});
	});
}

function setData(obj) {
	$(".list-group").empty();
	for ( var key in obj) {
		if (obj.hasOwnProperty(key)) {
			var val = obj[key];
			msg = '<a href="#" class="well list-group-item">';
			msg = msg.concat('<h4 class="list-group-item-heading">' + val.name
					+ '</h4>');
			msg = msg.concat('<p class="list-group-item-text">url : ' + val.url
					+ '</p>');
			msg = msg.concat('<p class="list-group-item-text">username : '
					+ val.username + '</p>');
			msg = msg.concat('<p class="list-group-item-text">password : '
					+ val.password + '</p>');
			msg = msg.concat('<p class="list-group-item-text">driver : '
					+ val.driver + '</p>');
			msg = msg.concat('</a>');
			$(".list-group").append(msg);
		}
	}
}
function disconnect() {
	if (stompClient != null) {
		stompClient.disconnect();
	}
	console.log("Disconnected..")
}
$(function() {
	$.ajax({
		url : "datasource/fetch",
		method : "GET",
		dataType : "json"
	}).done(function(data) {
		setData(data.datasource);
	});
	 connect();
	// $(".register").click(function() {
	// connect()
	// });
	$(".disconnect").click(function() {
		disconnect()
	});
})