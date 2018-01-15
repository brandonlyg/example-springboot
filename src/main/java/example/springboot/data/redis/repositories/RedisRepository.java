package example.springboot.data.redis.repositories;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import example.springboot.data.redis.annotation.ExcludeField;

import org.springframework.data.annotation.Id;

public class RedisRepository<T> {
	@Qualifier("redisTemplate")
	@Autowired private RedisTemplate<String, byte[]> redisTpl;
	
	private HashMapper<Object, byte[], byte[]> mapper = new ObjectHashMapper();
	private HashOperations<String, byte[], byte[]> hopts;
	
	private String keyPre;
	private String idField;
	private String[] excludeFields;
	
	public RedisRepository(){
		RedisSerializer<String> stringSerializer = new StringRedisSerializer();
		redisTpl.setKeySerializer(stringSerializer);
		redisTpl.setValueSerializer(stringSerializer);
		redisTpl.setHashKeySerializer(stringSerializer);
		redisTpl.setHashValueSerializer(stringSerializer);
		
		hopts = redisTpl.opsForHash();
		
		keyPre = "";
		Class<?> pt = getParameterizedType();
		if(null == pt){
			return;
		}
		RedisHash anno = pt.getAnnotation(RedisHash.class);
		if(null != anno){
			keyPre = anno.value();
		}
		
		Field[] fields = pt.getDeclaredFields();
		ArrayList<String> exfs = new ArrayList<String>();
		for(Field field : fields){
			Id annoId = field.getDeclaredAnnotation(Id.class);
			if(null != annoId){
				idField = field.getName();
				continue;
			}
			ExcludeField annoExf = field.getDeclaredAnnotation(ExcludeField.class);
			if(null != annoExf){
				exfs.add(field.getName());
			}
		}
		
		excludeFields = exfs.toArray(new String[0]);
	}
	
	public void save(T obj){
		Map<byte[], byte[]> map = mapper.toHash(obj);
		
		for(String fn : excludeFields){
			map.remove(fn.getBytes());
		}
		
		String key = getKey(new String(map.get(idField.getBytes())));
		hopts.putAll(key, map);
	}
	
	protected String getKey(String k){
		String key = "" + k;
		if(keyPre != ""){
			key = keyPre + ":" + key;
		}
		
		return key;
	}
	
	protected Class<?> getParameterizedType(){
		Type t = getClass().getGenericSuperclass();
		if(!(t instanceof ParameterizedType)){
			return null;
		}
		ParameterizedType pt = (ParameterizedType)t;
		return (Class<?>)pt.getActualTypeArguments()[0];
		
	}
}
