package com.sjk.controller.tools;

public class JsonObject {
	
	private String status;// error success
	private String eMsg;
	private String sMsg;
	private Object data;
	private String path;
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String geteMsg() {
		return eMsg;
	}
	public void seteMsg(String eMsg) {
		this.eMsg = eMsg;
	}
	public String getsMsg() {
		return sMsg;
	}
	public void setsMsg(String sMsg) {
		this.sMsg = sMsg;
	}
}
