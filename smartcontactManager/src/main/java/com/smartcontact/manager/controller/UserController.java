package com.smartcontact.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	@RequestMapping("/index")
	public String dashbord()
	{ System.out.println("user dashbord");
		
		return "normal/user_dashboard";
	}

}
