package example.springboot.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import example.springboot.data.redis.annotation.ExcludeField;

@RedisHash("session")
public class UserSession {
	@Id 
	private String id;
	private String aesKey;
	private String userId;
	
	@ExcludeField
	private String status;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAesKey() {
		return aesKey;
	}
	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
}
