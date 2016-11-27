package web.org.thuta.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thuta.services.interfaces.IProfilerUserOperationService;

import web.org.thuta.model.ajaxmodel.GeneralAjaxRespond;
import web.org.thuta.model.dto.HitDTO;
import web.org.thuta.model.dto.PhotoDTO;
import web.org.thuta.model.dto.PhotoPoolDTO;
import web.org.thuta.model.dto.ProfilerUserDTO;
import web.org.thuta.model.dto.UserDTO;
import web.org.thuta.security.interfaces.UserContext;

@Controller
@RequestMapping("/profilerUserView")
public class ProfilerUserController {
	
	public static final String url_Controller_Root = "/profilerUserView";
	public static final String url_Profiler_Public_Notice_Board_Page = "/publicNoticeBoard";
	public static final String url_Profiler_My_Room_Page = "/MyRoom";
	
	@Autowired
	@Qualifier("ProfilerUserOperationService")
	private IProfilerUserOperationService profilerUserOperationService;
	
	@Autowired
	@Qualifier("UserContextImpl")
	UserContext userContext;
	
	Logger logger = Logger.getLogger(ProfilerUserController.class);
	
	@RequestMapping(value = ProfilerUserController.url_Profiler_Public_Notice_Board_Page + "/publicWall.html", method = RequestMethod.GET)
	public String goDefaultRequest(HttpServletRequest request) {
		return "PublicNoticeBoard";
	}
	
	@RequestMapping(value = ProfilerUserController.url_Profiler_Public_Notice_Board_Page + "/ajaxPublicWall.html", 
			method = RequestMethod.GET, headers = {"Accept=application/json"})
	public @ResponseBody GeneralAjaxRespond<ProfilerUserDTO> goPublicWall(HttpServletRequest request, Model model) {
		
		GeneralAjaxRespond<ProfilerUserDTO> publicWallResponse = new GeneralAjaxRespond<ProfilerUserDTO>();
		List<ProfilerUserDTO> profilerUserDtoList = this.profilerUserOperationService.loadPublicWallProfilerUser();
		
		if(profilerUserDtoList != null && !profilerUserDtoList.isEmpty()) {
			publicWallResponse.setProfilerUserDtoList(profilerUserDtoList);
		}
		
		return publicWallResponse;
		
	}
	
	@RequestMapping(value = ProfilerUserController.url_Profiler_Public_Notice_Board_Page + "/photo/{photoDtoId}")
	@ResponseBody
	public byte[] getProfilerUserPhotoByPhotoId(@PathVariable long photoDtoId) {
		PhotoDTO photoDto = this.profilerUserOperationService.getPhotoDtoByPhotoDtoId(photoDtoId);
		logger.info("Checking getProfilerUserPhotoByPhotoId() : " + photoDto.getFile_name());
		return (photoDto != null) ? photoDto.getPhoto_content() : null;
	}
	
	@RequestMapping(value = ProfilerUserController.url_Profiler_My_Room_Page + "/myRoomSetting.htmlsss", method = RequestMethod.GET)
	public String goMyRoomForGet(ModelMap model) {
		
		UserDTO userDto = new UserDTO(this.userContext.getCurrentUser());
		ProfilerUserDTO profilerUserDto = this.profilerUserOperationService.findProfilerUserForEditMyRoom(userDto);
		logger.info("Checking session User : "+profilerUserDto);
		PhotoPoolDTO photoPoolDto = new PhotoPoolDTO();
		List<PhotoDTO> photoDtoList = new ArrayList<PhotoDTO>();
		photoDtoList.add(new PhotoDTO());
		photoPoolDto.setPhotoDtoList(photoDtoList);
		profilerUserDto.setPhotoPoolDto(photoPoolDto);
		model.put("profilerUserDto", profilerUserDto);
		return "MyRoom";
		
	}
	
	@RequestMapping(value = ProfilerUserController.url_Profiler_My_Room_Page + "/myRoomSetting.ttnsss", method = RequestMethod.POST)
	public String retrunMyRoomForPost(@Valid @ModelAttribute("profilerUserDto") ProfilerUserDTO profilerUserDto, BindingResult bindingResult, ModelMap model) {
		if(bindingResult.hasErrors()) {
			System.out.println("It has error:" + bindingResult.getAllErrors());
			return "MyRoom";
		}
		if(profilerUserDto != null) {
			
			UserDTO userDto = new UserDTO(this.userContext.getCurrentUser());
			logger.debug("Checking of PhotoPoolDTO : " + profilerUserDto.getPhotoPoolDto());
			logger.debug("Checking of PhotoPoolDTO's PhotoDto List size : " + profilerUserDto.getPhotoPoolDto().getPhotoDtoList().size());
			logger.debug("Checking of Current User Details: " + userDto);
			ProfilerUserDTO existingProfilerUserDto = this.profilerUserOperationService.findProfilerUserForEditMyRoom(userDto);
			logger.debug("Checking of existingProfilerUserDto: " + existingProfilerUserDto);
			if(existingProfilerUserDto != null) {
				
				
				existingProfilerUserDto.setUserDto(userDto);
				existingProfilerUserDto.setName(profilerUserDto.getName());
				logger.info("Checking for profilerUserDto.getPhotoPoolDto() =========================>" + profilerUserDto.getPhotoPoolDto());
				logger.info("Checking for profilerUserDto.getPhotoPoolDto().getPhotoDtoList() =========================>" + profilerUserDto.getPhotoPoolDto().getPhotoDtoList().size());
				for(PhotoDTO photo : profilerUserDto.getPhotoPoolDto().getPhotoDtoList()) {
					logger.info("Checking for profilerUserDto.getPhotoPoolDto().getPhotoDtoList().getPhoto() =========================>" + photo);
				}
				existingProfilerUserDto.setPhotoPoolDto(profilerUserDto.getPhotoPoolDto());
				existingProfilerUserDto.setEmail(profilerUserDto.getEmail());
				existingProfilerUserDto.setPhno(profilerUserDto.getPhno());
				existingProfilerUserDto.setWebsiteaddress(profilerUserDto.getWebsiteaddress());
				existingProfilerUserDto.setAddress(profilerUserDto.getAddress());
				this.profilerUserOperationService.updateToProfilerUserForEditMyRoom(existingProfilerUserDto);
				model.put("success", "Your save changes are success.");
				
			}
			
		}
		return "redirect:" + ProfilerUserController.url_Controller_Root +ProfilerUserController.url_Profiler_Public_Notice_Board_Page + "/publicWall.html";
	}
	
	@RequestMapping(value = ProfilerUserController.url_Profiler_My_Room_Page + "/myRoomSetting.html", method = RequestMethod.GET)
	public String goMyRoomForGetTVersion(ModelMap model) {
		
		UserDTO userDto = new UserDTO(this.userContext.getCurrentUser());
		userDto = this.profilerUserOperationService.findUserDtoById(userDto);
		logger.info("Checking session persisted User info : "+userDto);
		
		if(userDto != null && userDto.getProfilerUserDto() != null && userDto.getProfilerUserDto().getPhotoPoolDto() != null) {
			PhotoPoolDTO photoPoolDto = userDto.getProfilerUserDto().getPhotoPoolDto();
			if(photoPoolDto == null && photoPoolDto.getPhotoDtoList() == null && photoPoolDto.getPhotoDtoList().isEmpty()) {
				photoPoolDto = new PhotoPoolDTO();
				List<PhotoDTO> photoDtoList = new ArrayList<PhotoDTO>();
				photoDtoList.add(new PhotoDTO());
				photoPoolDto.setPhotoDtoList(photoDtoList);
				userDto.getProfilerUserDto().setPhotoPoolDto(photoPoolDto);
			}
		}
		model.put("userDto", userDto);
		return "MyRoom";
		
	}
	
	@RequestMapping(value = ProfilerUserController.url_Profiler_My_Room_Page + "/myRoomSetting.ttn", method = RequestMethod.POST)
	public String retrunMyRoomForPostTVersion(@Valid @ModelAttribute("userDto") UserDTO userDto, BindingResult bindingResult, ModelMap model) {
		if(bindingResult.hasErrors() && userDto.getPassword() != null) {
			System.out.println("It has error:" + bindingResult.getAllErrors());
			return "MyRoom";
		}
		if(userDto != null) {
			
			UserDTO sessionUserDto = new UserDTO(this.userContext.getCurrentUser());
			logger.debug("Checking of PhotoPoolDTO : " + userDto.getProfilerUserDto().getPhotoPoolDto());
			logger.debug("Checking of PhotoPoolDTO's PhotoDto List size : " + userDto.getProfilerUserDto().getPhotoPoolDto().getPhotoDtoList().size());
			logger.debug("Checking of Current User Details: " + userDto);
			UserDTO existingUserDto = this.profilerUserOperationService.findUserDtoById(sessionUserDto);
			logger.debug("Checking of existingUserDto: " + existingUserDto);
			if(existingUserDto != null) {
				
				existingUserDto.setProfilerUserDto(userDto.getProfilerUserDto());
				this.profilerUserOperationService.updateUserForEditMyRoom(existingUserDto);
				model.put("success", "Your save changes are success.");
				
			}
			
		}
		return "redirect:" + ProfilerUserController.url_Controller_Root +ProfilerUserController.url_Profiler_Public_Notice_Board_Page + "/publicWall.html";
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
		IProfilerUserOperationService test = (IProfilerUserOperationService) context.getBean("ProfilerUserOperationService");
		
//		PublicWallRespond publicWallResponse = new PublicWallRespond();
		List<HitDTO> hitDtoList = test.loadPublicWallHit();
		List<ProfilerUserDTO> profilerUserDtoList = test.loadPublicWallProfilerUser();
		
		ObjectMapper mapper = new ObjectMapper();
		try{
//		System.out.println("Json Mapping Testing"+ mapper.writeValueAsString(profilerUserDtoList));
		System.out.println("Json Mapping Testing"+ mapper.writeValueAsString(hitDtoList));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
//		if(hitDtoList != null && !hitDtoList.isEmpty()) {
//			for(HitDTO hitDto : hitDtoList) {
//				
//				if(hitDto != null) {
//					ProfilerUserDTO profilerUserDto = test.findProfilerUserDtoById(hitDto.getProfilerUserId());
//					hitDto.setProfilerUserDto(profilerUserDto);
//					System.out.println("This is profilerUserDto Name "+ profilerUserDto.getName());
//				}
//				
//			}
//			publicWallResponse.appendHitToDtoList(hitDtoList);
//		}
//		System.out.println(publicWallResponse.getHitDtoList().size());
	}
//	
//	@RequestMapping(value="/test", method = RequestMethod.GET, headers = {"Accept=application/json"})
//	public @ResponseBody ProfilerUserRespond getAll(HttpServletRequest request) {
//		List<IProfilerUser> profilerUserList = new ArrayList<IProfilerUser>();
//		profilerUserList = this.profilerUserDAO.findAllProfilerUser();
//		ProfilerUserRespond response = new ProfilerUserRespond();
//		response.setTtn(profilerUserList);
//		int count = this.profilerUserDAO.getNoOfRows();
//		int total = 10;
//		response.setTotal(total);
//		response.setRecords(count);
//		response.setPage(1);
//		return response;
//	}
//	
//	@RequestMapping(value="/demon", method = RequestMethod.GET)
//	public String startDemon(HttpServletRequest request) {
//		return "demon";
//	}

}
