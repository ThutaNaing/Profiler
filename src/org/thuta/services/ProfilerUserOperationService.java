package org.thuta.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thuta.dao.interfaces.IHitDAO;
import org.thuta.dao.interfaces.IPhotoDAO;
import org.thuta.dao.interfaces.IPhotoPoolDAO;
import org.thuta.dao.interfaces.IProfileDAO;
import org.thuta.dao.interfaces.IProfilerUserDAO;
import org.thuta.dao.interfaces.IUserDAO;
import org.thuta.entities.interfaces.IHit;
import org.thuta.entities.interfaces.IPhoto;
import org.thuta.entities.interfaces.IProfile;
import org.thuta.entities.interfaces.IProfilerUser;
import org.thuta.entities.interfaces.IUser;
import org.thuta.services.interfaces.IProfilerUserOperationService;

import web.org.thuta.model.dto.HitDTO;
import web.org.thuta.model.dto.PhotoDTO;
import web.org.thuta.model.dto.PhotoPoolDTO;
import web.org.thuta.model.dto.ProfileDTO;
import web.org.thuta.model.dto.ProfilerUserDTO;
import web.org.thuta.model.dto.UserDTO;

@Service("ProfilerUserOperationService")
@Transactional(propagation = Propagation.REQUIRED)
public class ProfilerUserOperationService implements IProfilerUserOperationService {
	
	@Autowired
	@Qualifier("UserDAO")
	private IUserDAO userDAO;
	
	@Autowired
	@Qualifier("ProfilerUserDAO")
	private IProfilerUserDAO profilerUserDao;
	
	@Autowired
	@Qualifier("HitDAO")
	private IHitDAO hitDao;
	
	@Autowired
	@Qualifier("ProfileDAO")
	private IProfileDAO profileDao;
	
	@Autowired
	@Qualifier("PhotoDAO")
	private IPhotoDAO photoDao;
	
	public ProfilerUserOperationService() {}
	
	//[START]UserDTO Operation
	public void signUpUserAndRegisterProfilerUserAccount(UserDTO userDto) {
		if(userDto != null) {
			userDAO.addUser(userDto.mappingToEntity());
		}
	}
	
	public UserDTO findUserDtoById(UserDTO userDto) {
		UserDTO persistedUserDto = null;
		IUser userEntity = userDAO.findUser(userDto.getId());
		if(userEntity != null) {
			persistedUserDto = new UserDTO(userEntity);
		}
		return persistedUserDto;
	}
	
	public void updateUserForEditMyRoom(UserDTO userDto) {
		if(userDto != null) {
			userDAO.updateUser(userDto.mappingToEntity());
		}
	}
	//[END]UserDTO Operation
	
	public ProfilerUserDTO findProfilerUserDtoById(long profilerUserId) {
		ProfilerUserDTO profilerUserDto = null;
		IProfilerUser profilerUserEntity = this.profilerUserDao.findProfilerUser(profilerUserId);
		if(profilerUserEntity != null) {
			profilerUserDto = new ProfilerUserDTO(profilerUserEntity);
		}
		return profilerUserDto;
	}
	
	public List<HitDTO> loadPublicWallHit() {
		List<HitDTO> hitDtoList = new ArrayList<HitDTO>();
		List<IHit> hitList = this.hitDao.findAllPublicHit();
		if(hitList != null && !hitList.isEmpty()) {
			
			for(IHit hit : hitList) {
				hitDtoList.add(new HitDTO(hit));
			}
			
		}
		return hitDtoList;
	}
	
	public List<ProfilerUserDTO> loadPublicWallProfilerUser() {
		List<ProfilerUserDTO> profilerUserDtoList = new ArrayList<ProfilerUserDTO>();
		List<IProfilerUser> profilerUserList = this.profilerUserDao.findAllProfilerUser();
		if(profilerUserList != null && !profilerUserList.isEmpty()) {
			
			for(IProfilerUser profilerUserEntity : profilerUserList) {
				profilerUserDtoList.add(new ProfilerUserDTO(profilerUserEntity));
				System.out.println("Profiler User Entity List size : " + profilerUserEntity.getHitList().size());
			}
			
		}
		return profilerUserDtoList;
	}
	
	public PhotoDTO getPhotoDtoByPhotoDtoId(long id) {
		PhotoDTO photoDto = null;
		if(id > 0) {
			IPhoto photoEntity = this.photoDao.findPhoto(id);
			if(photoEntity != null)
				photoDto = new PhotoDTO(photoEntity);
		}
		return photoDto;		
	}
	
	public ProfilerUserDTO findProfilerUserForEditMyRoom(UserDTO userDto) {
		ProfilerUserDTO profilerUserDto = null;
		if(userDto != null) {
			IUser userEntity = this.userDAO.findUser(userDto.getId());
			IProfilerUser profilerUserEntity = userEntity.getProfilerUser();
			if(profilerUserEntity != null)
				profilerUserDto = new ProfilerUserDTO(profilerUserEntity);
		}
		return profilerUserDto;
	}
	
	public void updateToProfilerUserForEditMyRoom(ProfilerUserDTO profilerUserDto) {
		if(profilerUserDto != null) {
			this.profilerUserDao.updateProfilerUser(profilerUserDto.mappingToEntity());
		}
	}
	
	public static void main(String[] args) {
//		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
//		IProfilerUserOperationService profilerUserOperationService = (IProfilerUserOperationService) context.getBean("ProfilerUserOperationService");
//		ProfilerUserDTO profilerUserDto = profilerUserOperationService.findProfilerUserDtoById(1);
//		
//		PhotoPoolDTO photoPoolDto = new PhotoPoolDTO();
//		List<PhotoDTO> photoDtoList = new ArrayList<PhotoDTO>();
//		photoDtoList.add(new PhotoDTO());
//		photoPoolDto.setPhotoDtoList(photoDtoList);
//		profilerUserDto.setPhotoPoolDto(photoPoolDto);
//		
//		profilerUserOperationService.updateToProfilerUserForEditMyRoom(profilerUserDto);
		int b=1012121231;
		float shh= b;
		float result = b + shh;
		System.out.println("Result :"+ result);
	}
	
//	public List<ProfileDTO> loadPublicWall() {
//	List<ProfileDTO> profileDtoList = new ArrayList<ProfileDTO>();
//	List<IHit> hitList = this.hitDao.findAllPublicHit();
//	if(hitList != null && !hitList.isEmpty()) {
//		
//		for(IHit hit : hitList) {
//			IProfile profileEntity = this.profileDao.findProfileByHit(hit).get(0);
//			profileDtoList.add(new ProfileDTO(profileEntity));
//		}
//		
//	}
//	return profileDtoList;
//}
}
