package org.thuta.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.thuta.entities.interfaces.IHit;
import org.thuta.entities.interfaces.IPhoto;
import org.thuta.entities.interfaces.IPhotoPool;
import org.thuta.entities.interfaces.IProfile;
import org.thuta.entities.interfaces.IProfileType;
import org.thuta.entities.interfaces.IProfilerBinder;
import org.thuta.entities.interfaces.IProfilerUser;
import org.thuta.entities.interfaces.ITeamBinder;
import org.thuta.entities.interfaces.IUser;

import com.rits.cloning.Cloner;

@Entity
@Table(name = "profiler_user")
@TableGenerator(name = "PROFILER_USER_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "PROFILER_USER_GEN", allocationSize = 1)
@Access(value = AccessType.FIELD)
public class ProfilerUser implements Serializable, IProfilerUser {
	private static final long serialVersionUID = 1L;
	
	@Transient
	private long id;
	private String code;
	private String name;
	private String description;
	private Date dateOfBirth;
	private String email;
	private String phno;
	private String address;
	private String websiteaddress;
	
	@OneToOne(targetEntity = org.thuta.entities.PhotoPool.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "photo_pool_id", referencedColumnName = "ID")
	private IPhotoPool photoPool;
	
	@OneToMany(targetEntity = org.thuta.entities.Hit.class, fetch = FetchType.EAGER, mappedBy = "profilerUser", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonBackReference
	private List<IHit> hitList;
	
	@OneToOne(targetEntity=org.thuta.entities.User.class,fetch=FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name="user_id",referencedColumnName="id")
	private IUser user;

	//Many End possessed by Team
	@ManyToOne(targetEntity = org.thuta.entities.TeamBinder.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "team_binder_id", referencedColumnName = "id")
	private ITeamBinder teamBinder;
	
	//One End possessed by this
	@OneToOne(targetEntity = org.thuta.entities.ProfilerBinder.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "profiler_binder_id", referencedColumnName = "id")
	private IProfilerBinder profilerBinder;
	
	private long publicNotification;
	private long teamNotification;
	
	public ProfilerUser() {
		this(null);
	}
	
	public ProfilerUser(IUser user) {
		this(null, user);
	}
	
	public ProfilerUser(String websiteaddress, IUser user) {
		this(null, websiteaddress, user);
	}
	
	public ProfilerUser(String address, String websiteaddress, IUser user) {
		this(null, address, websiteaddress, user);
	}
	
	public ProfilerUser(String phno, String address, String websiteaddress, IUser user) {
		this(null, phno, address, websiteaddress, user);
	}
	
	public ProfilerUser(String email, String phno, String address, String websiteaddress, IUser user) {
		this(null, email, phno, address, websiteaddress, user);
	}
	
	public ProfilerUser(Date dateOfBirth, String email, String phno, String address, String websiteaddress, IUser user) {
		this(null, dateOfBirth, email, phno, address, websiteaddress, user);
	}
	
	public ProfilerUser(String description, Date dateOfBirth, String email
			, String phno, String address, String websiteaddress, IUser user) {
		this(null, description, dateOfBirth, email, phno, address, websiteaddress, user);
	}

	public ProfilerUser(String name, String description, Date dateOfBirth, String email
			, String phno, String address, String websiteaddress, IUser user) {
		this(null, name, description, dateOfBirth, email, phno, address, websiteaddress, user);
	}
	
	public ProfilerUser(String code, String name, String description, Date dateOfBirth, String email
			, String phno, String address, String websiteaddress, IUser user) {
		this(code, name, description, dateOfBirth, email, phno, address, websiteaddress, user, null);
	}
	
	public ProfilerUser(String code, String name, String description, Date dateOfBirth, String email
			, String phno, String address, String websiteaddress, IUser user, IPhotoPool photoPool) {
		this(code, name, description, dateOfBirth, email, phno, address, websiteaddress, user, photoPool, null);
	}
	
	public ProfilerUser(String code, String name, String description, Date dateOfBirth, String email
			, String phno, String address, String websiteaddress, IUser user, IPhotoPool photoPool, List<IHit> hitList) {
		this(code, name, description, dateOfBirth, email, phno, address, websiteaddress, user, photoPool, hitList, null);
	}
	
	public ProfilerUser(String code, String name, String description, Date dateOfBirth, String email
			, String phno, String address, String websiteaddress, IUser user, IPhotoPool photoPool, List<IHit> hitList
			, ITeamBinder teamBinder) {
		this(code, name, description, dateOfBirth, email, phno, address, websiteaddress, user, photoPool, hitList
				, teamBinder, null);
	}
	
	public ProfilerUser(String code, String name, String description, Date dateOfBirth, String email
			, String phno, String address, String websiteaddress, IUser user, IPhotoPool photoPool, List<IHit> hitList
			, ITeamBinder teamBinder, IProfilerBinder profilerBinder) {
		this(code, name, description, dateOfBirth, email, phno, address, websiteaddress, user, photoPool, hitList
				, teamBinder, profilerBinder, 0);
	}
	
	public ProfilerUser(String code, String name, String description, Date dateOfBirth, String email
			, String phno, String address, String websiteaddress, IUser user, IPhotoPool photoPool, List<IHit> hitList
			, ITeamBinder teamBinder, IProfilerBinder profilerBinder,long publicNotification) {
		this(0, code, name, description, dateOfBirth, email, phno, address, websiteaddress, user, photoPool, hitList
				, teamBinder, profilerBinder, publicNotification, 0);
	}
	
	public ProfilerUser(long id, String code, String name, String description, Date dateOfBirth, String email
			, String phno, String address, String websiteaddress, IUser user, IPhotoPool photoPool, List<IHit> hitList
			, ITeamBinder teamBinder, IProfilerBinder profilerBinder,long publicNotification, long teamNotification) {
		
		populateProperties(id, code, name, description, dateOfBirth, email, phno, address, websiteaddress, user, photoPool, hitList
				, teamBinder, profilerBinder, publicNotification, teamNotification);
		
	}
	
	private void populateProperties(long id, String code, String name, String description, Date dateOfBirth, String email
			, String phno, String address, String websiteaddress, IUser user, IPhotoPool photoPool, List<IHit> hitList
			, ITeamBinder teamBinder, IProfilerBinder profilerBinder,long publicNotification, long teamNotification) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.phno = phno;
		this.address = address;
		this.websiteaddress = websiteaddress;
		this.user = user;
		this.photoPool = photoPool;
		this.hitList = hitList;
		this.teamBinder = teamBinder;
		this.profilerBinder = profilerBinder;
		this.publicNotification = publicNotification;
		this.teamNotification = teamNotification;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "PROFILER_USER_GEN")
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhno() {
		return phno;
	}

	public void setPhno(String phno) {
		this.phno = phno;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWebsiteaddress() {
		return websiteaddress;
	}

	public void setWebsiteaddress(String websiteaddress) {
		this.websiteaddress = websiteaddress;
	}

	public IUser getUser() {
		return user;
	}

	public void setUser(IUser user) {
		this.user = user;
	}
	
	public IPhotoPool getPhotoPool() {
		return photoPool;
	}

	public void setPhotoPool(IPhotoPool photoPool) {
		this.photoPool = photoPool;
	}

	public List<IHit> getHitList() {
		return hitList;
	}

	public void setHitList(List<IHit> hitList) {
		this.hitList = hitList;
	}

	public void addHit(IHit hit) {
		if (this.hitList == null) {
			this.hitList = new ArrayList<IHit>();
		}
		hit.setProfilerUser(this);
		this.hitList.add(hit);
	}
	
	public void removeHit(IHit hit) {
		if (this.hitList != null) {
			this.hitList.remove(hit);
		}
	}
	
	public void appendHit(List<IHit> hits) {
		if (hits != null) {
			for (IHit hit : hits) {
				addHit(hit);
			}
		}
	}
	
	public ITeamBinder getTeamBinder() {
		return teamBinder;
	}

	public void setTeamBinder(ITeamBinder teamBinder) {
		this.teamBinder = teamBinder;
	}

	public IProfilerBinder getProfilerBinder() {
		return profilerBinder;
	}

	public void setProfilerBinder(IProfilerBinder profilerBinder) {
		this.profilerBinder = profilerBinder;
	}

	public long getPublicNotification() {
		return publicNotification;
	}

	public void setPublicNotification(long publicNotification) {
		this.publicNotification = publicNotification;
	}

	public long getTeamNotification() {
		return teamNotification;
	}

	public void setTeamNotification(long teamNotification) {
		this.teamNotification = teamNotification;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	public IProfilerUser duplicate() {
		Cloner cloneMaker = new Cloner();
		IProfilerUser clonedProfileUser = cloneMaker.deepClone(this);
		return clonedProfileUser;
	}
	
}
