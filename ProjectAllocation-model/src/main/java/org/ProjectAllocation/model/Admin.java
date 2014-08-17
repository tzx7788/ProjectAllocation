package org.ProjectAllocation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.json.JSONObject;

@Entity
public class Admin extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8478044573960478633L;

	public static final String TABLENAME = "Admin";
	public static final String COL_AID = "AID";
	public static final String COL_NAME = "NAME";
	public static final String COL_PASSWORD = "PASSWORD";
	public static final String COL_SESSION = "SESSION";

	private String aid;
	private String name;
	private String password;
	private String session;

	public Admin() {
		this.initialization("", "", "", null);
	}
	
	public Admin(String aid, String name) {
		this.initialization(aid, name, "", null);
	}

	private void initialization(String aid, String name, String password,
			String session) {
		this.aid = aid;
		this.name = name;
		this.password = password;
		this.session = session;
	}

	@Column( name = COL_AID )
	@Id
	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	@Column( name = COL_NAME )
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column( name = COL_PASSWORD )
	public String getPassword() {
		if ( password == null ) password = "";
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column( name = COL_SESSION, nullable = true )
	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public JSONObject toJSONObject() {
		JSONObject result = new JSONObject();
		result.put("aid", this.getAid());
		result.put("name", this.getName());
		return result;
	}

}
