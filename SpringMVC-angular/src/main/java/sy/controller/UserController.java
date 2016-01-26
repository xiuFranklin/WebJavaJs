package sy.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import sy.jdbc.UserJdbc;
import sy.modle.User;
import sy.service.UserServiceI;

@Controller
@RequestMapping("/userController")
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	@Resource
	private UserJdbc UserJdbc;//jdbc
	
	private UserServiceI userService;

	public UserServiceI getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserServiceI userService) {
		this.userService = userService;
	}
	//http://localhost:8080/testmybatis/userController/showUser1.do?id=1
	@RequestMapping("/showUser1")
	public String shouUser1(String id,HttpServletRequest request){
		User u = userService.getUserById(id);
		request.setAttribute("user", u);
		System.out.println("------"+id+"-----");
		return "showUser";
	}
	//http://localhost:8080/testmybatis/userController/showUser/1,2.do
	@RequestMapping("/showUser/{id},{name}")
	public String shouUser(@PathVariable String id,@PathVariable String name,HttpServletRequest request){
		User u = userService.getUserById(id);
		request.setAttribute("user", u);
		logger.info(JSON.toJSONString(u));
		return "showUser";
	}
	//http://localhost:8080/SpringMVC-angular/userController/showUser2.json?id=1
	//http://localhost:8080/SpringMVC-angular/userController/showUser2.xml?id=1
	//http://localhost:8080/SpringMVC-angular/userController/showUser2.do?id=1
	@RequestMapping(value={"shouUser2"},method=RequestMethod.GET)
	public ModelAndView shouUser2(String id,HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		User u = userService.getUserById(id);
		mav.addObject("user", u);
		logger.info(mav.toString());
		return mav;
	}
}
