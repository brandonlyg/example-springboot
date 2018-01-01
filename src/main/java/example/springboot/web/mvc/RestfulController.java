package example.springboot.web.mvc;

import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/rest")
public class RestfulController {
	
	@GetMapping("/get")
	public Response get(){
		Response res = new Response();
		res.setCmd(1001);
		res.setUserId("aaaaaa");
		res.setTimeStamp(new Date());
		res.setInfo("it's dev branch");

		return res;
	}
}
