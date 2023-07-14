package eeit163.model;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eeit163.service.MemberService;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint("/ws/{id}")
public class Websocket {
	private Session session;
	private static CopyOnWriteArraySet<Websocket> websocketSet = new CopyOnWriteArraySet<>();
	private Integer id;

	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public static CopyOnWriteArraySet<Websocket> getWebsocketSet() {
		return websocketSet;
	}

	public static void setWebsocketSet(CopyOnWriteArraySet<Websocket> websocketSet) {
		Websocket.websocketSet = websocketSet;
	}

	@OnOpen
	public void onOpen(Session session, @PathParam("id") Integer id) {
		this.session = session;
		this.id = id;
		websocketSet.add(this);
		String str = "{\"id\":\"" +id + "\",\"target\":\"" + 0+ "\",\"onlineNumber\":\""+websocketSet.size()+ "\",\"type\":\"" + 0 + "\",\"message\":\""
				+ "online" + "\"}";
		for (Websocket ws : websocketSet) {
			ws.session.getAsyncRemote().sendText(str);
	    }
		System.out.println("websocket:" + websocketSet.size());
		System.out.println("id:" + id);
	}

	@OnClose
	public void onClose(Session session) {
		websocketSet.remove(this);
		String str = "{\"id\":\"" +id + "\",\"target\":\"" + 0+ "\",\"onlineNumber\":\""+websocketSet.size()+"\",\"type\":\"" + 0 + "\",\"message\":\""
				+ "offline" + "\"}";
		for (Websocket ws : websocketSet) {
			try {
				ws.session.getBasicRemote().sendText(str);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}

	@OnMessage
	public void onMessage(String message, @PathParam("id") Integer id) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			System.out.println("後端收到websocket:"+message);
			WebsocketMessage websocketMessage = objectMapper.readValue(message, WebsocketMessage.class);
			
			if (websocketMessage.getType() == 0) {
				for (Websocket ws : websocketSet) {
					if(ws.getId()==websocketMessage.getId()) {
						for (Websocket ws1 : websocketSet) {
							String str = "{\"id\":\"" +ws1.getId() + "\",\"type\":\"" + 0 + "\",\"message\":\""
								+ "online" + "\"}";
						ws.session.getBasicRemote().sendText(str);
						}
				    }
				}
			}
			
			if (websocketMessage.getType() == 1) {
				for (Websocket ws : websocketSet) {
					if(ws.getId()==websocketMessage.getTarget()) {
						ws.session.getAsyncRemote().sendText(message);
					}
				}
			}
			
			if (websocketMessage.getType() == 2) {
				for (Websocket ws : websocketSet) {
					if(ws.getId()==websocketMessage.getId()||ws.getId()==websocketMessage.getTarget())
					ws.session.getAsyncRemote().sendText(message);
				}
			}

			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("id:" + id);
		System.out.println("message:" + message);
	}

	@OnError
	public void onError(Session session, Throwable error) {
		error.printStackTrace();
	}

	public static int getOnlineCount() {
		return websocketSet.size();
	}

	public void sendMessage(String message) throws IOException {
		this.session.getAsyncRemote().sendText(message);
	}

	public static void sendInfo(String message, @PathParam("id") Integer id) throws IOException {

		for (Websocket ws : websocketSet) {
			try {
				if (id == null) {
				} else if (ws.getId() == id) {
					ws.sendMessage(message);
				}
			} catch (IOException e) {
				continue;
			}
		}
	}

}