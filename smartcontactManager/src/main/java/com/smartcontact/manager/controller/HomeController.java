package com.smartcontact.manager.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartcontact.manager.dao.UserRepository;
import com.smartcontact.manager.enties.User;
import com.smartcontact.manager.helper.Message;

@Controller
public class HomeController {

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	

	@RequestMapping("/")
	public String home(Model model) {
		System.out.println("welcome to home method+++++++++++");
		model.addAttribute("title", "Home Smartcontact Manager");
		return "home";
	}

	@RequestMapping("/about")
	public String about(Model model) {
		System.out.println("welcome to about method+++++++++++");
		model.addAttribute("title", "About SmartContact Manager");
		return "about";
	}

	@RequestMapping("/singup")
	public String singnup(Model model) {
		System.out.println("singup method here");
		model.addAttribute("title", "Register SmartContact Manager");
		model.addAttribute("user", new User());

		return "singup";
	}

	// handler for registering user
	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") User user,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model , BindingResult bindingResult, HttpSession session) {
		try {
			if (!agreement) {
				System.out.println("you have not agreed the terms and condition");
				throw new Exception("you have not agreed the terms and condition");
			}
			 if(bindingResult.hasErrors())
			 { System.out.println("error"+bindingResult.toString());
				 model.addAttribute("user",user);
				 return "singup";
				 
			 }
			
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			System.out.println("agreement" + agreement);
			System.out.println("use" + user);
			User result = this.userRepository.save(user);
			model.addAttribute("user", new User());
			session.setAttribute("message", new Message("successfully register !!", "alert-success"));
	          return "singup";	

		} catch (Exception e) {
          e.printStackTrace();
          model.addAttribute("user",user);
          session.setAttribute("message", new Message("something went worng !!"+e.getMessage(), "alert-danger"));
          return "singup";	
	}

}
//hadler for cutom  login
	@GetMapping("/signin")
	 public String cutomLogin(Model model)
	 {  model.addAttribute("titke","Login Page");
		 return "Login";
	 }

}
