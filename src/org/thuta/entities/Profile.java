package org.thuta.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.thuta.entities.interfaces.IPhotoPool;
import org.thuta.entities.interfaces.IProfile;
import org.thuta.entities.interfaces.IProfileType;

import com.rits.cloning.Cloner;

@Entity
@Table(name = "profile")
@TableGenerator(name = "PROFILE_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "PROFILE_GEN", allocationSize = 1)
@Access(value = AccessType.FIELD)
public class Profile implements Serializable, IProfile{
	private static final long serialVersionUID = 1L;
	
	@Transient
	private long id;
	private String code;
	private String topic;
	@Column(name="topic_content")
	private String topicContent;
	private String name;
	private String description;
	
	@OneToOne(targetEntity = org.thuta.entities.PhotoPool.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "photo_pool_id", referencedColumnName = "ID")
	private IPhotoPool photoPool;
	
	@OneToMany(targetEntity = org.thuta.entities.ProfileType.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "PROFILE_PROFILE_TYPE_LINK", 
		joinColumns = { @JoinColumn(name = "PROFILE_ID", referencedColumnName = "ID") }, 
		inverseJoinColumns = { @JoinColumn(name = "PROFILE_TYPE_ID", referencedColumnName = "ID") })
	@Fetch(value = FetchMode.SUBSELECT)
	private List<IProfileType> profileTypeList;
	private boolean publicwall = true;
	
	public Profile() {
		this(new ArrayList<IProfileType>(), true);
	}
	
	public Profile(List<IProfileType> profileTypeList, boolean publicwall) {
		this(null, profileTypeList, publicwall);
	}
	
	public Profile(IPhotoPool photoPool, List<IProfileType> profileTypeList, boolean publicwall) {
		this(null, photoPool, profileTypeList, publicwall);
	}
	
	public Profile(String description, IPhotoPool photoPool, List<IProfileType> profileTypeList, boolean publicwall) {
		this(null, description, photoPool, profileTypeList, publicwall);
	}
	
	public Profile(String name, String description
			, IPhotoPool photoPool, List<IProfileType> profileTypeList, boolean publicwall) {
		this(null, name, description, photoPool, profileTypeList, publicwall);
	}
	
	public Profile(String topicContent, String name, String description
			, IPhotoPool photoPool, List<IProfileType> profileTypeList, boolean publicwall) {
		this(null, topicContent, name, description, photoPool, profileTypeList, publicwall);
	}
	
	public Profile(String topic, String topicContent, String name, String description
			, IPhotoPool photoPool, List<IProfileType> profileTypeList, boolean publicwall) {
		this(null, topic, topicContent, name, description, photoPool, profileTypeList, publicwall);
	}
	
	public Profile(String code, String topic, String topicContent, String name, String description
			, IPhotoPool photoPool, List<IProfileType> profileTypeList, boolean publicwall) {
		this(0, code, topic, topicContent, name, description, photoPool, profileTypeList, publicwall);
	}
	
	public Profile(long id, String code, String topic, String topicContent, String name, String description
			, IPhotoPool photoPool, List<IProfileType> profileTypeList, boolean publicwall) {
		populateProperties(id, code, topic, topicContent, name, description, photoPool, profileTypeList, publicwall);
	}
	
	private void populateProperties(long id, String code, String topic, String topicContent, String name, String description
			, IPhotoPool photoPool, List<IProfileType> profileTypeList, boolean publicwall) {
		this.id = id;
		this.code = code;
		this.topic = topic;
		this.topicContent = topicContent;
		this.name = name;
		this.description = description;
		this.photoPool = photoPool;
		this.profileTypeList = profileTypeList;
		this.publicwall = publicwall;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "PROFILE_GEN")
	@Access(value = AccessType.PROPERTY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTopicContent() {
		return topicContent;
	}

	public void setTopicContent(String topicContent) {
		this.topicContent = topicContent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public IPhotoPool getPhotoPool() {
		return photoPool;
	}

	public void setPhotoPool(IPhotoPool photoPool) {
		this.photoPool = photoPool;
	}

	public List<IProfileType> getProfileTypeList() {
		return profileTypeList;
	}

	public void setProfileTypeList(List<IProfileType> profileTypeList) {
		this.profileTypeList = profileTypeList;
	}
	
	public void addProfileType(IProfileType profileType) {
		if (this.profileTypeList == null) {
			this.profileTypeList = new ArrayList<IProfileType>();
		}
		this.profileTypeList.add(profileType);
	}
	
	public void removeProfileType(IProfileType profileType) {
		if (this.profileTypeList != null) {
			this.profileTypeList.remove(profileType);
		}
	}
	
	public void appendProfileType(List<IProfileType> profileTypes) {
		if (profileTypes != null) {
			for (IProfileType profileType : profileTypes) {
				addProfileType(profileType);
			}
		}
	}
	
	public boolean isPublicwall() {
		return publicwall;
	}

	public void setPublicwall(boolean publicwall) {
		this.publicwall = publicwall;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	public IProfile duplicate() {
		Cloner cloneMaker = new Cloner();
		IProfile clonedProfileType = cloneMaker.deepClone(this);
		return clonedProfileType;
	}
}
