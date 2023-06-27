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
	private String onlineStatus;

	@Autowired
	private MemberService mDao;

	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
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
		System.out.println("websocket:" + websocketSet.size());
		System.out.println("id:" + id);
	}

	@OnClose
	public void onClose(Session session) {
		websocketSet.remove(this);
		System.out.println("websocket:" + websocketSet.size());
	}

	@OnMessage
	public void onMessage(String message, @PathParam("id") Integer id) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			System.out.println("後端收到websocket:"+message);
			WebsocketMessage websocketMessage = objectMapper.readValue(message, WebsocketMessage.class);
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
