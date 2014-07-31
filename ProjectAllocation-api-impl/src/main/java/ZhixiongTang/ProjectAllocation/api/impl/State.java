package ZhixiongTang.ProjectAllocation.api.impl;

import org.ProjectAllocation.model.JSONInterface;
import org.json.JSONArray;
import org.json.JSONObject;

public class State extends JSONObject {


	public State(JSONInterface data) {
		super();
		if (data.getClass() != Error.class) {
			this.put("status", "success");
		} else {
			this.put("status", "fail");
		}
		this.initWithJSONObject(data.toJSONObject());
	}
	
	public State(JSONObject data) {
		this.put("status", "success");
		this.initWithJSONObject(data);
	}
	
	public State(JSONArray data) {
		this.put("status", "success");
		this.initWithJSONArray(data);
	}
	
	private void initWithJSONObject(JSONObject obj) {
		this.put("data",obj);
	}

	private void initWithJSONArray(JSONArray array) {
		this.put("data",array);
	}
}
