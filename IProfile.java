package org.thuta.entities.interfaces;

import java.util.ArrayList;
import java.util.List;

public interface IProfile {
	public long getId();
	public void setId(long id);
	public String getCode();
	public void setCode(String code);
	public String getTopic();
	public void setTopic(String topic);
	public String getTopicContent();
	public void setTopicContent(String topicContent);
	public String getName();
	public void setName(String name);
	public String getDescription();
	public void setDescription(String description);
	public IPhotoPool getPhotoPool();
	public void setPhotoPool(IPhotoPool photoPool);
	public List<IProfileType> getProfileTypeList();
	public void setProfileTypeList(List<IProfileType> profileTypeList);
	public void addProfileType(IProfileType profileType);
	public void removeProfileType(IProfileType profileType);
	public void appendProfileType(List<IProfileType> profileTypes);
	public boolean isPublicwall();
	public void setPublicwall(boolean publicwall);
	public IProfile duplicate();
}
