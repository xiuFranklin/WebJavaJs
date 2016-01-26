package sy.test;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import sy.modle.User;
import sy.service.UserServiceI;

import com.alibaba.fastjson.JSON;

//spring-test  junit测试
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/spring.xml","classpath:spring/spring-mybatis.xml"})
public class TestMybatis {
	private static final Logger logger = Logger.getLogger(TestMybatis.class);
	
//junit测试
//	@Test
//	public void test1() {
//		ApplicationContext ac=new ClassPathXmlApplicationContext(new String[]{"spring.xml","spring-mybatis.xml"});
//		UserServiceI userService=(UserServiceI) ac.getBean("userService");
//		User u = userService.getUserById("1");
//		System.out.println(u.getName());
//	}
	
	
	/*//junit测试
	ApplicationContext ac;
	UserServiceI userService;
	@Before
	public void before(){
		
		ac=new ClassPathXmlApplicationContext(new String[]{"spring.xml","spring-mybatis.xml"});
		userService=(UserServiceI) ac.getBean("userService");
	}
	
	@Test
	public void test1() {
		User u = userService.getUserById("1");
		System.out.println(u.getName());
	}
	*/
	private UserServiceI userService;

	public UserServiceI getUserService() {
		return userService;
	}
	@Autowired
	public void setUserService(UserServiceI userService) {
		this.userService = userService;
	}
	
	@Test
	public void test1() {
		User u = userService.getUserById("1");
		logger.info(JSON.toJSONString(u));
	}
	
	
	@Test
	public void test2() {
		List<User> list = userService.getAll();
		logger.info(JSON.toJSONStringWithDateFormat(list,"yyyy-MM-dd HH:mm:ss"));
	}
	
	@Test
	public void test3() {
		List<User> list = userService.getAllUser();
		logger.info(JSON.toJSONStringWithDateFormat(list,"yyyy-MM-dd HH:mm:ss"));
	}
}
