package ZhixiongTang.ProjectAllocation.api.impl;

import org.ProjectAllocation.model.JSONInterface;
import org.json.JSONObject;


public class Error implements JSONInterface {
	protected String msg;

	public Error(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public JSONObject toJSONObject() {
		JSONObject result = new JSONObject();
		result.put("msg",msg);
		return result;
	}
	
}
