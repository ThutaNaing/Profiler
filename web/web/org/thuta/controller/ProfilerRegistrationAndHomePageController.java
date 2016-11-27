package web.org.thuta.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thuta.dao.interfaces.IUserDAO;
import org.thuta.services.interfaces.IProfilerUserOperationService;

import web.org.thuta.model.dto.UserDTO;
import web.org.thuta.security.interfaces.UserContext;

@Controller
@RequestMapping("/profilerHome")
public class ProfilerRegistrationAndHomePageController {
	
	public static final String url_Controller_Root = "/profilerHome";
	public static final String url_Profiler_Home_Page = "/home";
	public static final String url_Profiler_Login_Page = "/login/form";
	public static final String url_Profiler_Registration_Page = "/registration/form";
	
	@Autowired
	@Qualifier("UserDAO")
	private IUserDAO userDAO;
	
	@Autowired
	@Qualifier("ProfilerUserOperationService")
	private IProfilerUserOperationService profilerUserOperationService;
	
	@Autowired
	@Qualifier("UserContextImpl")
	UserContext userContext;
	
	@RequestMapping({"/", url_Profiler_Home_Page})
	public String showHomePage(ModelMap model) {
		return "ProfilerHomePage";
	}
	
	@RequestMapping(value = url_Profiler_Login_Page, method = RequestMethod.GET)
	public String showLoginPage(ModelMap model) {
		return "ProfilerLogin";
	}
	
	@RequestMapping(value = url_Profiler_Registration_Page, method = RequestMethod.GET)
	public String showProfilerRegistrationPage(Model model) {
		UserDTO userDto = new UserDTO();
		model.addAttribute("userDto",userDto);
		return "ProfilerRegistration";
	}
	
	@RequestMapping(value = url_Profiler_Registration_Page, method = RequestMethod.POST)
	public String submitProfilerRegistrationPage(@Valid @ModelAttribute("userDto") UserDTO userDto, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "ProfilerRegistration";
		}
		if(userDto != null) {
			this.profilerUserOperationService.signUpUserAndRegisterProfilerUserAccount(userDto);
			this.userContext.loginAutomatic(userDto);
			System.out.println("Login User Testing===================================> "+ this.userContext.getCurrentUser().getUsername());
		}
		return "redirect:" + ProfilerUserController.url_Controller_Root + ProfilerUserController.url_Profiler_Public_Notice_Board_Page;
	}
	
	/**************************
	 * AJAX style login pop up*
	 **************************/
	/*
	@RequestMapping(value = "/userLogin", method = RequestMethod.GET)
	public @ResponseBody User showUserLoginPage(HttpServletRequest request) {
		User respond = new User();
		return respond;
	}
	
	@RequestMapping(value = "/userLogin", method = RequestMethod.POST)
	public void handleUserLoginPage(HttpServletRequest request, HttpServletResponse response) {
		String userJson = "";
		User user = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			if(br != null) {
				userJson = br.readLine();
			}
			ObjectMapper mapper = new ObjectMapper();
			user = mapper.readValue(userJson, User.class);
			System.out.println("The result from json object is " + user.getUsername());
			System.out.println("The result from json object is " + user.getPassword());
//			response.sendRedirect("/profiler/profilerUserView/publicNoticeBoard");
		} catch (Exception e) {
			e.printStackTrace();
		}
//		return "redirect:/profilerHome/publicNoticeBoard/PublicNoticeBoard";
//		return "PublicNoticeBoard";
	}
	*/
}
