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
import org.thuta.entities.interfaces.IPhoto;
import org.thuta.entities.interfaces.IPhotoPool;
import org.thuta.entities.interfaces.IProfilerBinder;
import org.thuta.entities.interfaces.ITeam;
import org.thuta.entities.interfaces.ITeamBinder;

import com.rits.cloning.Cloner;

@Entity
@Table(name = "team")
@TableGenerator(name = "TEAM_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "TEAM_GEN", allocationSize = 1)
@Access(value = AccessType.FIELD)
public class Team implements Serializable, ITeam {
	private static final long serialVersionUID = 1L;

	@Transient
	private long id;
	private String code;
	private String name;
	private String description;
	
	//Many End possessed by profiler user
	@ManyToOne(targetEntity = org.thuta.entities.ProfilerBinder.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "profiler_binder_id", referencedColumnName = "id")
	private IProfilerBinder profilerBinder;
	
	//One End possessed by this
	@OneToOne(targetEntity = org.thuta.entities.TeamBinder.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "team_binder_id", referencedColumnName = "id")
	private ITeamBinder teamBinder;
	
	public Team() {
		this(null);
	}
	
	public Team(ITeamBinder teamBinder) {
		this(null, teamBinder);
	}
	
	public Team(IProfilerBinder profilerBinder, 
			ITeamBinder teamBinder) {
		this(null, profilerBinder, teamBinder);
	}
	
	public Team(String description, IProfilerBinder profilerBinder, 
			ITeamBinder teamBinder) {
		this(null, description, profilerBinder, teamBinder);
	}
	
	public Team(String name, String description, IProfilerBinder profilerBinder, 
			ITeamBinder teamBinder) {
		this(null, name, description, profilerBinder, teamBinder);
	}
	
	public Team(String code, String name, String description, IProfilerBinder profilerBinder, 
			ITeamBinder teamBinder) {
		this(0, code, name, description, profilerBinder, teamBinder);
	}
	
	public Team(long id, String code, String name, String description, IProfilerBinder profilerBinder, 
			ITeamBinder teamBinder) {
		populateProperties(id, code, name, description, profilerBinder, teamBinder);
	}
	
	private void populateProperties(long id, String code, String name, String description, IProfilerBinder profilerBinder, 
			ITeamBinder teamBinder) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
		this.profilerBinder = profilerBinder;
		this.teamBinder = teamBinder;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TEAM_GEN")
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

	public IProfilerBinder getProfilerBinder() {
		return profilerBinder;
	}

	public void setProfilerBinder(IProfilerBinder profilerBinder) {
		this.profilerBinder = profilerBinder;
	}

	public ITeamBinder getTeamBinder() {
		return teamBinder;
	}

	public void setTeamBinder(ITeamBinder teamBinder) {
		this.teamBinder = teamBinder;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	public ITeam duplicate() {
		Cloner cloneMaker = new Cloner();
		ITeam clonedTeam = cloneMaker.deepClone(this);
		return clonedTeam;
	}
	
}
