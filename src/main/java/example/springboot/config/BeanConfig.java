package example.springboot.config;

import java.net.UnknownHostException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class BeanConfig {
	
	@Bean
	public RedisTemplate<String, byte[]> redisTemplate(RedisConnectionFactory redisConnectionFactory) 
			throws UnknownHostException {
		RedisTemplate<String, byte[]> template = new RedisTemplate<String, byte[]>();
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}
}
