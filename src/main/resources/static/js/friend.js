function refreshSession() {
	let xhRefreshSession = new XMLHttpRequest();
	xhRefreshSession.onload = function() {
	}
	xhRefreshSession.open("POST", "/community/member/refreshLogin", true);
	xhRefreshSession.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhRefreshSession.send("id=" + myId.value);
}

let canvasContainer = document.querySelector(".canvas-container");

function toggleList() {
	canvasContainer.classList.toggle("active");
	if (canvasContainer.classList.contains("active")) {
		toggleButton.src = '/community/image/rectangle-list-solidA.png';
	} else {
		toggleButton.src = '/community/image/rectangle-list-solidB.png';
	}
}

let searchButton = document.getElementById("searchButton");
let searchResults = document.getElementById("searchResults");

function searchMember() {
	refreshSession();
	let xhrSearchMember = new XMLHttpRequest();
	xhrSearchMember.onload = function() {
		if (xhrSearchMember.responseText == "") { document.getElementById("searchResults").innerHTML = ``; }
		if (xhrSearchMember.responseText != "") {
			let resultMember = JSON.parse(xhrSearchMember.responseText);
			if (resultMember.relation == "new") {
				document.getElementById("searchResults").innerHTML = `
							<span>`+ resultMember.id + `</span>
							<span><img style="width: 50px" src="data:image/*;base64,`+ resultMember.photo + `" /></span>
							<span>`+ resultMember.username + `</span>
							<button onclick="beFriend(`+ resultMember.id + `)"><img style='height:25px;' src='/community/image/person-circle-plus-solid.png'></button>
		                    `;
			}
			else if (resultMember.relation == "invite") {
				document.getElementById("searchResults").innerHTML = `
							<span>`+ resultMember.id + `</span>
							<span><img style="width: 50px" src="data:image/*;base64,`+ resultMember.photo + `" /></span>
							<span>`+ resultMember.username + `</span>
							<button onclick="unFriend(`+ resultMember.id + `)"><img style='height:25px;' src='/community/image/circle-xmark-solid.png'></button>
		                    `;
			}
			else if (resultMember.relation == "invitation") {
				document.getElementById("searchResults").innerHTML = `
							<span>`+ resultMember.id + `</span>
							<span><img style="width: 50px" src="data:image/*;base64,`+ resultMember.photo + `" /></span>
							<span>`+ resultMember.username + `</span>
							<button onclick="beFriend(`+ resultMember.id + `)"><img style='height:25px;' src='/community/image/circle-check-solid.png'></button>
							<button onclick="unFriend(`+ resultMember.id + `)"><img style='height:25px;' src='/community/image/circle-xmark-solid.png'></button>
		                    `;
			}
			else if (resultMember.relation == "friend") {
				document.getElementById("searchResults").innerHTML = `
							<span>`+ resultMember.id + `</span>
							<span><img style="width: 50px" src="data:image/*;base64,`+ resultMember.photo + `" /></span>
							<span>`+ resultMember.username + `</span>
							<button onclick="unFriend(`+ resultMember.id + `)"><img style='height:25px;' src='/community/image/person-circle-minus-solid.png'></button>
		                    `;
			}

		}
	}
	xhrSearchMember.open("POST", "/community/member/ajax/searchMember", true);
	xhrSearchMember.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhrSearchMember.send("username=" + searchInput.value);
}

function beFriend(x) {
	let xhrBeFriend = new XMLHttpRequest();
	xhrBeFriend.onload = function() {
		refreshSession();
		let websocketMessage = {
			id: myId.value,
			target: x,
			type: 2
		};
		websocket.send(JSON.stringify(websocketMessage));
	}
	xhrBeFriend.open("POST", "/community/member/ajax/inviteOrAccept", true);
	xhrBeFriend.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhrBeFriend.send("id=" + x);
}

function unFriend(x) {
	let xhrUnFriend = new XMLHttpRequest();
	xhrUnFriend.onload = function() {
		refreshSession();
		let websocketMessage = {
			id: myId.value,
			target: x,
			type: 2
		};
		websocket.send(JSON.stringify(websocketMessage));
	}
	xhrUnFriend.open("POST", "/community/member/ajax/cancelOrReject", true);
	xhrUnFriend.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhrUnFriend.send("id=" + x);
}

function showInvited() {
	let xhrShowInvited = new XMLHttpRequest();
	xhrShowInvited.onload = function() {
		document.getElementById("invitedList").innerHTML = xhrShowInvited.responseText;
	}
	xhrShowInvited.open("POST", "/community/member/ajax/invited", true);
	xhrShowInvited.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhrShowInvited.send("id=" + myId.value);
}

function showInvitation() {
	let xhrShowInvitation = new XMLHttpRequest();
	xhrShowInvitation.onload = function() {
		document.getElementById("invitationList").innerHTML = xhrShowInvitation.responseText;
	}
	xhrShowInvitation.open("POST", "/community/member/ajax/invitation", true);
	xhrShowInvitation.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhrShowInvitation.send("id=" + myId.value);
}

function showFriend() {
	let xhrShowFriend = new XMLHttpRequest();
	xhrShowFriend.onload = function() {
		document.getElementById("friendList").innerHTML = xhrShowFriend.responseText;
		unReadList();
		let websocketMessage = {
			id: myId.value,
			type: 0
		};
		websocket.send(JSON.stringify(websocketMessage));
	}
	xhrShowFriend.open("POST", "/community/member/ajax/friendList", true);
	xhrShowFriend.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhrShowFriend.send("id=" + myId.value);
}






let tabButtons = document.querySelectorAll(".tab-button");
let tabContents = document.querySelectorAll(".tab-content");

function switchTab(event) {
	let tabId = event.target.id.replace("tabButton", "tabContent");

	// 隱藏所有分頁內容
	tabContents.forEach(function(content) {
		content.classList.remove("active");
	});

	// 顯示選中的分頁內容
	let selectedTab = document.getElementById(tabId);
	selectedTab.classList.add("active");
}

tabButtons.forEach(function(button) {
	button.addEventListener("click", switchTab);
});



let websocket;

if (myId.value != "" && myId.value != null) {
	websocket = new WebSocket("ws://localhost:8080/community/ws/" + myId.value);
	websocket.onmessage = function(event) {
		let wm = JSON.parse(event.data);
		if (wm.type == 0) {
			if (wm.onlineNumber != "" && wm.onlineNumber != null && wm.onlineNumber != 0) { document.getElementById("onlineNumber").innerText = "線上人數：" + wm.onlineNumber; }
			if (wm.message == "online") {
				if (document.getElementById("targetOnlineStatus_" + wm.id) != null) {
					document.getElementById("targetOnlineStatus_" + wm.id).innerText = wm.message;
					document.getElementById("targetOnlineStatus_" + wm.id).style.color = 'green';
				}
			} else {
				if (document.getElementById("targetOnlineStatus_" + wm.id) != null) {
					document.getElementById("targetOnlineStatus_" + wm.id).innerText = wm.message;
					document.getElementById("targetOnlineStatus_" + wm.id).style.color = 'red';
				}
			}
		}
		if (wm.type == 2) { showInvited(); showInvitation(); showFriend(); searchMember(); }
		if (wm.type == 1) {
			unRead(wm.id);
			if (wm.id == document.getElementById("reading").value) {
				let xhrOpenChat = new XMLHttpRequest();
				xhrOpenChat.onload = function() {
					let chatList = JSON.parse(xhrOpenChat.responseText);
					for (let i = 0; i < chatList.length; i++) {
						if (chatList[i].sender == myId.value) { appendMessage(chatList[i].type, chatList[i].content, chatList[i].creationtime, "self"); }
						if (chatList[i].sender == wm.id) { appendMessage(chatList[i].type, chatList[i].content, chatList[i].creationtime, "other"); }
					}
					read(wm.id);
				}
				xhrOpenChat.open("POST", "/community/message/showChat", true);
				xhrOpenChat.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
				xhrOpenChat.send("target=" + wm.id);
			}
		}
	}

	window.onbeforeunload = function() {
		websocket.close();
	}

}




let chatPopup = document.getElementById("chatPopup");
let chatMessages = document.getElementById("chatMessages");
let messageInput = document.getElementById("messageInput");
let sendButton = document.getElementById("sendButton");

function openChat(x) {
	chatMessages.innerHTML = "";
	targetPhoto.value = document.getElementById("targetPhoto_" + x).src;
	targetName.innerText = document.getElementById("targetName_" + x).innerText;
	targetId.value = x;
	if (document.getElementById("reading").value != x) {
		if (document.getElementById("reading").value == 0) { chatPopup.classList.toggle("open"); }
		document.getElementById("reading").value = x;
	}
	else if (document.getElementById("reading").value == x) { document.getElementById("reading").value = 0; chatPopup.classList.toggle("open"); }
	document.getElementById("target").value = x;
	let xhrOpenChat = new XMLHttpRequest();
	xhrOpenChat.onload = function() {
		let chatList = JSON.parse(xhrOpenChat.responseText);
		for (let i = 0; i < chatList.length; i++) {
			if (chatList[i].sender == myId.value) { appendMessage(chatList[i].type, chatList[i].content, chatList[i].creationtime, "self"); }
			if (chatList[i].sender == x) { appendMessage(chatList[i].type, chatList[i].content, chatList[i].creationtime, "other"); }
		}
		read(x);
	}
	xhrOpenChat.open("POST", "/community/message/showChat", true);
	xhrOpenChat.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhrOpenChat.send("target=" + x);
}

function unReadList() {
	let xhrFriendIdList = new XMLHttpRequest();
	xhrFriendIdList.onload = function() {
		let list = JSON.parse(xhrFriendIdList.responseText);
		for (let i = 0; i < list.length; i++) {
			unRead(list[i]);
		}
	}
	xhrFriendIdList.open("POST", "/community/message/friendIdList", true);
	xhrFriendIdList.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhrFriendIdList.send("id=" + myId.value);
}

function unRead(x) {
	let xhrUnRead = new XMLHttpRequest();
	xhrUnRead.onload = function() {
		if (xhrUnRead.responseText == 0) { document.getElementById("readChat_" + x).innerText = ""; }
		else { document.getElementById("readChat_" + x).innerText = xhrUnRead.responseText; }
	}
	xhrUnRead.open("POST", "/community/message/unRead", true);
	xhrUnRead.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhrUnRead.send("id=" + x);
}

function read(x) {
	let xhrRead = new XMLHttpRequest();
	xhrRead.onload = function() {
		document.getElementById("readChat_" + x).innerText = "";
	}
	xhrRead.open("POST", "/community/message/read", true);
	xhrRead.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhrRead.send("id=" + x);
}

sendButton.addEventListener("click", function() {
	if (messageInput.value.trim() !== "") {
		sendForm();
	}
});

function sendForm() {
	let form = document.getElementById("form1");
	let formdata = new FormData(form);
	let xhrForm = new XMLHttpRequest();
	xhrForm.onload = function() {
		let websocketMessage = {
			id: myId.value,
			target: targetId.value,
			type: 1
		};
		websocket.send(JSON.stringify(websocketMessage));
		appendMessage(0, messageInput.value.trim(), xhrForm.responseText, "self");
		messageInput.value = "";
		messageInput.focus();
		part.value = "";
	}
	xhrForm.open("POST", "/community/message/addMessage", true);
	xhrForm.send(formdata);
}

function sendImg() {
	let form = document.getElementById("form1");
	let formdata = new FormData(form);
	let xhrForm = new XMLHttpRequest();
	let base64String = "";
	let file = formdata.get('part');
	let reader = new FileReader();
	reader.onload = function(e) {
		base64String = e.target.result;
	};
	reader.readAsDataURL(file);
	xhrForm.onload = function() {
		let websocketMessage = {
			id: myId.value,
			target: targetId.value,
			type: 1
		};
		websocket.send(JSON.stringify(websocketMessage));
		appendMessage(1, base64String, xhrForm.responseText, "self");
		messageInput.value = "";
		messageInput.focus();
		part.value = "";
	}
	xhrForm.open("POST", "/community/message/addMessage", true);
	xhrForm.send(formdata);
}

function appendMessage(type, content, time, sender) {
	let memberPhoto = document.createElement("img");
	memberPhoto.style.width = '50px';
	memberPhoto.style.height = '50px';
	let spanElement = document.createElement("span");
	let nameElement = document.createElement("span");
	nameElement.style.fontSize = "16px";
	nameElement.style.lineHeight = "50px";
	nameElement.style.textAlign = "right";
	let messageTime = document.createElement("span");
	messageTime.innerText = time;
	messageTime.style.fontSize = '12px';
	messageTime.style.verticalAlign = 'bottom';
	if (sender == "self"&&photo!=null) { memberPhoto.src = photo.src; nameElement.innerText = "  " + myName.innerText + " :"; }
	if (sender == "other") { memberPhoto.src = targetPhoto.value; nameElement.innerText = "  " + targetName.innerText + " :"; }
	spanElement.appendChild(nameElement);
	spanElement.appendChild(messageTime);
	let messageContainer = document.createElement("div");
	messageContainer.className = "chat-message " + sender;
	let messageContent;
	if (type == 0 || type == 2) {
		messageContent = document.createElement("div");
		messageContent.className = "content";
		messageContent.innerHTML = content;
	}
	if (type == 1 || type == 3) {
		messageContent = document.createElement("img");
		messageContent.className = "content";
		messageContent.src = content;
	}
	messageContainer.appendChild(memberPhoto);
	messageContainer.appendChild(spanElement);
	messageContainer.appendChild(document.createElement('br'));
	messageContainer.appendChild(messageContent);
	chatMessages.appendChild(messageContainer);
	chatMessages.scrollTop = chatMessages.scrollHeight;
}