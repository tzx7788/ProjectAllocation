package ZhixiongTang.ProjectAllocation.api.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JSONString;

public class State implements JSONString {

	String data;
	String status;

	public State(String data) {
		if (data.getClass().equals(Error.class)) {
			status = "success";
		} else {
			status = "fail";
		}
		this.data = data;
	}

	public String toJSONString() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("status", status);
		result.put("data", data);
		return JSONObject.fromObject(result).toString();
	}

}
