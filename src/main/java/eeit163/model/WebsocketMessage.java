package eeit163.model;

import lombok.Data;

@Data
public class WebsocketMessage {
	private Integer id;
	private Integer target; // 0=all , others=id.
	private Integer type; //0=status , 1=message , 2=friendList
	private String message;
	public Integer getTarget() {
		return target;
	}
	public void setTarget(Integer target) {
		this.target = target;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}
