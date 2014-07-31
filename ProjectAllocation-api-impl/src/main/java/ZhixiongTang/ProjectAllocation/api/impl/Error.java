package ZhixiongTang.ProjectAllocation.api.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JSONString;

public class Error implements JSONString {
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

	public String toJSONString() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("msg",msg);
		return JSONObject.fromObject(result).toString();
	}
	
}
