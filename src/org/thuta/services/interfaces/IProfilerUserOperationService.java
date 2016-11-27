package org.thuta.services.interfaces;

import java.util.List;

import web.org.thuta.model.dto.HitDTO;
import web.org.thuta.model.dto.PhotoDTO;
import web.org.thuta.model.dto.ProfileDTO;
import web.org.thuta.model.dto.ProfilerUserDTO;
import web.org.thuta.model.dto.UserDTO;

public interface IProfilerUserOperationService {
	public void signUpUserAndRegisterProfilerUserAccount(UserDTO userDto);
	public UserDTO findUserDtoById(UserDTO userDto);
	public ProfilerUserDTO findProfilerUserDtoById(long profilerUserId);
	public List<HitDTO> loadPublicWallHit();
	public PhotoDTO getPhotoDtoByPhotoDtoId(long id);
	public List<ProfilerUserDTO> loadPublicWallProfilerUser();
	public ProfilerUserDTO findProfilerUserForEditMyRoom(UserDTO userDto);
	public void updateToProfilerUserForEditMyRoom(ProfilerUserDTO profilerUserDto);
	public void updateUserForEditMyRoom(UserDTO userDto);
}
