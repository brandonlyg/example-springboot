package example.springboot.data.redis.repositories;

import org.springframework.data.repository.CrudRepository;

import example.springboot.entities.UserSession;

public interface SessionRepository extends CrudRepository<UserSession, String>{

}
