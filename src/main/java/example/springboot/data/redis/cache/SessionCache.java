package example.springboot.data.redis.cache;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.data.redis.hash.BeanUtilsHashMapper;
import org.springframework.data.redis.hash.DecoratingStringHashMapper;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import example.springboot.entities.UserSession;



@Component
public class SessionCache {
	private static Logger log = LoggerFactory.getLogger(SessionCache.class);
	//@Resource(name="redisTemplate")
	//@Qualifier("redisTemplate")
	//@Autowired private RedisTemplate<byte[], byte[]> redis;
	
	//@Resource(name="redisTemplate")
	//@Autowired
	//HashOperations<String, String, String> hopts;
	
	@Autowired StringRedisTemplate redis;
	
	//private HashMapper<Object, byte[], byte[]> mapper = new ObjectHashMapper();
	//BeanUtilsHashMapper<Session> mapper = new BeanUtilsHashMapper<Session>();
	private HashMapper<Object, String, Object> mapper = new Jackson2HashMapper(true);
	
	public void save(UserSession session){
		/*
		ObjectMapper jmapper = new ObjectMapper();
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", session.getId());
		map.put("aesKey", session.getAesKey());
		map.put("userId", session.getUserId());
		
		redis.opsForHash().putAll("session:"+session.getId(), map);*/
		
		//ObjectMapper mapper = new ObjectMapper();
		//mapper.reader
		
		//Map<byte[], byte[]> map = (new DecoratingStringHashMapper(mapper)).toHash(session);
		Map<String, Object> map = mapper.toHash(session); //OK
		//map.remove("@class");
		redis.opsForHash().putAll(session.getId(), map);
		
		HashOperations<String, String, Object> opts = redis.opsForHash();
		Map<String, Object> res = opts.entries(session.getId());
		
		UserSession objRes = (UserSession)mapper.fromHash(res);
		
		ObjectMapper jmapper = new ObjectMapper();
		String json = "";

		try {
			json = jmapper.writeValueAsString(objRes);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("read data: {}", json);
	}
	
}
