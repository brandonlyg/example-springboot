package example.springboot.data.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import example.springboot.data.redis.repositories.SessionRepository;
import example.springboot.entities.UserSession;

@Component
public class SessionMgr {
	
	@Autowired
	private SessionRepository  sessionRepo;
	
	
	public void add(UserSession sion){
		sessionRepo.save(sion);
	}
}
