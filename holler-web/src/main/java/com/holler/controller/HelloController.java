package com.holler.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.holler.bean.TestDTO;
import com.holler.bean.UserJobDTO;
import com.holler.holler_dao.entity.User;
import com.holler.holler_service.UserService;


@Controller
public class HelloController {
	
	@Autowired
	UserService userService;
	
/*	@Autowired
	HibernateTestService hibernateTestService;*/
	
	@RequestMapping("/index")
	public ModelAndView handleeequest(Model model, HttpServletRequest request){
		
		HttpSession session = request.getSession(false);
		ModelAndView mv = null;
		User user = null;
		if(session == null){
			mv = setDefaultLoginAction();
		}else{
			user = (User)session.getAttribute("user");
			if(user != null){
				mv = new ModelAndView("helloworld");
				mv.addObject("message", "Hello Friend ");
				mv.addObject("name", user.getName());
			}else{
				mv = setDefaultLoginAction();
			}
		}
		return mv;
	}
	
	@RequestMapping(value="/welcome", method=RequestMethod.POST)
	public ModelAndView welcomeUser(@ModelAttribute("user")User user, Model model, HttpServletRequest request){
		
		ModelAndView mv = null;
		HttpSession session = request.getSession(false);
		if(session == null){
			mv = setDefaultLoginAction();
		}else{
			boolean isValidUser = userService.authenticateUser(user.getEmail(), user.getPassword());
			if(isValidUser){
				session = request.getSession();
				session.setAttribute("user", user);
				mv = new ModelAndView("helloworld");
				mv.addObject("message", "Hello Friend ");
				mv.addObject("name", user.getName());
				//hibernateTestService.saveUsingEntityManager();
			}else{
				mv = setDefaultLoginAction();
			}
		}
		return mv;
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.POST)
	public ModelAndView logoutUser(Model model, HttpServletRequest request){
		ModelAndView mv = null;
		HttpSession session = request.getSession(false);
		if(session != null){
            session.invalidate();
        }
		mv = setDefaultLoginAction();
		return mv;
	}
	
	@RequestMapping(value="/getUserJobs", method=RequestMethod.POST)
	public @ResponseBody UserJobDTO getUserJobs(@RequestParam("userId")int userId, HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session == null){
			return null;
		}else{
			User user = (User)session.getAttribute("user");
			UserJobDTO userJobDTO = userService.getUserJobs(user, userId);
			return userJobDTO;
		}
	}
	
	@RequestMapping(value="/getJsonInRequest", method=RequestMethod.POST)
	public @ResponseBody UserJobDTO getJsonInRequest(@RequestBody TestDTO testDTO, HttpServletRequest request){
		
		User user = null;
		HttpSession session = request.getSession(false);
		if(session != null){
			session = request.getSession();
			user = (User)session.getAttribute("user");
		}else{
			return null;
		}
		
		UserJobDTO userJobDTO = userService.getUserJobs(user, 1);
		return userJobDTO;
	}
	
	private ModelAndView setDefaultLoginAction(){
		ModelAndView mv = new ModelAndView("login");
		User user = new User();
		mv.addObject("user", user);
		return mv;
	}

}
