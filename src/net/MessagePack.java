package net;

import java.util.HashMap;

public class MessagePack implements java.io.Serializable {
	private String action = "";
	private HashMap<String, Object> objectArrayList;

	public MessagePack(String action) {
		// TODO Auto-generated constructor stub
		this.action = action;
		objectArrayList = new HashMap<String, Object>();
	}

	public String getAction() {
		return action;
	}

	public void setAction(String act) {
		action = act;
	}

	public void addData(String key, Object o) {
		objectArrayList.put(key, o);
	}

	public HashMap<String, Object> getObjectHashMap() {
		return objectArrayList;
	}

}
