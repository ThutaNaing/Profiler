package org.thuta.entities;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.thuta.entities.interfaces.IHit;
import org.thuta.entities.interfaces.IPhoto;
import org.thuta.entities.interfaces.IPhotoPool;
import org.thuta.entities.interfaces.IProfile;
import org.thuta.entities.interfaces.IProfilerUser;
import org.thuta.entities.interfaces.ITeam;

import com.rits.cloning.Cloner;

/**
 * @author Acer
 *
 */
@Entity
@Table(name = "hit")
@TableGenerator(name = "HIT_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "HIT_GEN", allocationSize = 1)
@Access(value = AccessType.FIELD)
public class Hit implements Serializable, IHit {
	private static final long serialVersionUID = 1L;
	
	@Transient
	private int id;
	private String code;
	private String name;
	private String description;
	
	@OneToOne(targetEntity=org.thuta.entities.Profile.class, fetch=FetchType.EAGER)
	@JoinColumn(name="profile_id",referencedColumnName="id")
	@JsonManagedReference
	private IProfile profile;
	
	@ManyToOne(targetEntity = org.thuta.entities.ProfilerUser.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "profiler_user_id", referencedColumnName = "id")
	private IProfilerUser profilerUser;
	
	@OneToOne(targetEntity=org.thuta.entities.Team.class,fetch=FetchType.EAGER)
	@JoinColumn(name="team_id",referencedColumnName="id")
	private ITeam team;
	
	private boolean publicHit;
	private boolean teamHit;
	
	public Hit () {
		this(false);
	}
	
	public Hit (boolean teamHit) {
		this(false, teamHit);
	}
	
	public Hit (boolean publicHit, boolean teamHit) {
		this(null, publicHit, teamHit);
	}
	
	public Hit (ITeam team, boolean publicHit, boolean teamHit) {
		this(null, team, publicHit, teamHit);
	}
	
	public Hit (IProfile profile
			, ITeam team, boolean publicHit, boolean teamHit) {
		this(null, profile, team, publicHit, teamHit);
	}
	
	public Hit (String description, IProfile profile
			, ITeam team, boolean publicHit, boolean teamHit) {
		this(null, description, profile, team, publicHit, teamHit);
	}
	
	public Hit (String name, String description, IProfile profile
			, ITeam team, boolean publicHit, boolean teamHit) {
		this(null, name, description, profile, team, publicHit, teamHit);
	}
	
	public Hit (String code, String name, String description, IProfile profile
			, ITeam team, boolean publicHit, boolean teamHit) {
		this(code, name, description, profile, null, team, publicHit, teamHit);
	}
	
	public Hit (String code, String name, String description, IProfile profile, IProfilerUser profilerUser
			, ITeam team, boolean publicHit, boolean teamHit) {
		this(0, code, name, description, profile, profilerUser, team, publicHit, teamHit);
	}
	
	public Hit (int id, String code, String name, String description, IProfile profile, IProfilerUser profilerUser
			, ITeam team, boolean publicHit, boolean teamHit) {
		populateProperties(id, code, name, description, profile, profilerUser, team, publicHit, teamHit);
	}
	
	private void populateProperties(int id, String code, String name, String description, IProfile profile
			, IProfilerUser profilerUser, ITeam team, boolean publicHit, boolean teamHit) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
		this.profile = profile;
		this.profilerUser = profilerUser;
		this.team = team;
		this.publicHit = publicHit;
		this.teamHit = teamHit;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "HIT_GEN")
	@Access(value = AccessType.PROPERTY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public IProfile getProfile() {
		return profile;
	}

	public void setProfile(IProfile profile) {
		this.profile = profile;
	}

	public IProfilerUser getProfilerUser() {
		return profilerUser;
	}

	public void setProfilerUser(IProfilerUser profilerUser) {
		this.profilerUser = profilerUser;
	}

	public ITeam getTeam() {
		return team;
	}

	public void setTeam(ITeam team) {
		this.team = team;
	}

	public boolean isPublicHit() {
		return publicHit;
	}

	public void setPublicHit(boolean publicHit) {
		this.publicHit = publicHit;
	}

	public boolean isTeamHit() {
		return teamHit;
	}

	public void setTeamHit(boolean teamHit) {
		this.teamHit = teamHit;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	public IHit duplicate() {
		Cloner cloneMaker = new Cloner();
		IHit clonedHit = cloneMaker.deepClone(this);
		return clonedHit;
	}
}
