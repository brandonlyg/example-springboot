package example.springboot.web.mvc;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import example.springboot.data.redis.SessionMgr;
import example.springboot.data.redis.cache.SessionCache;
import example.springboot.entities.User;
import example.springboot.entities.UserSession;


@RestController
@RequestMapping("/rest")
public class RestfulController {
	
	@Autowired private SessionMgr sessionMgr;
	@Autowired private SessionCache sessionCache;
	
	@GetMapping("/get")
	public Response get(){
		
		User u = new User();
		u.setUserId("11111");
		u.setTimeStamp(new Date());
		u.setInfo("this is dev");
		
		Response res = new Response();
		res.setRetCode(0);
		res.setData(u);

		return res;
	}
	
	@GetMapping("/createSession")
	public Response createSession(){
		UserSession sion = new UserSession();
		sion.setId(UUID.randomUUID().toString());
		sion.setUserId("aaaaaaaa");
		sion.setAesKey("123456789");
		
		sessionMgr.add(sion);
		
		Response res = new Response();
		res.setRetCode(0);
		res.setData(sion);

		return res;
	}
	
	@GetMapping("/v1/createSession")
	public Response createSessionV1(){
		UserSession sion = new UserSession();
		sion.setId(UUID.randomUUID().toString());
		sion.setUserId("bbbbbbb");
		sion.setAesKey("123456789");
		
		sessionCache.save(sion);
		
		Response res = new Response();
		res.setRetCode(0);
		res.setData(sion);

		return res;
	}
}
