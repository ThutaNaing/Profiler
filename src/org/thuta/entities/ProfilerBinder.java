package org.thuta.entities;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.thuta.entities.interfaces.IProfileType;
import org.thuta.entities.interfaces.IProfilerBinder;
import org.thuta.entities.interfaces.IProfilerUser;
import org.thuta.entities.interfaces.ITeam;
import org.thuta.entities.interfaces.ITeamBinder;

import com.rits.cloning.Cloner;

@Entity
@Table(name = "profiler_binder")
@TableGenerator(name = "PROFILER_BINDER_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "PROFILER_BINDER_GEN", allocationSize = 1)
@Access(value = AccessType.FIELD)
public class ProfilerBinder implements Serializable, IProfilerBinder {
	private static final long serialVersionUID = 1L;

	@Transient
	private long id;
	@OneToMany(targetEntity = org.thuta.entities.Team.class, fetch = FetchType.EAGER, mappedBy = "profilerBinder", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<ITeam> teamList;
	
	public ProfilerBinder() {
		this(new ArrayList<ITeam>());
	}
	
	public ProfilerBinder(List<ITeam> teamList) {
		this(0, teamList);
	}
	
	public ProfilerBinder(long id, List<ITeam> teamList) {
		populateProperties(id, teamList);
	}
	
	public void populateProperties(long id, List<ITeam> teamList) {
		this.id = id;
		this.teamList = teamList;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "PROFILER_BINDER_GEN")
	@Access(value = AccessType.PROPERTY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<ITeam> getTeamList() {
		return teamList;
	}

	public void setTeamList(List<ITeam> teamList) {
		this.teamList = teamList;
	}
	
	public void addTeam(ITeam team) {
		if (this.teamList == null) {
			this.teamList = new ArrayList<ITeam>();
		}
		this.teamList.add(team);
	}
	
	public void removeTeam(ITeam team) {
		if (this.teamList != null) {
			this.teamList.remove(team);
		}
	}
	
	public void appendTeam(List<ITeam> teams) {
		if (teams != null) {
			for (ITeam team : teams) {
				addTeam(team);
			}
		}
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	public IProfilerBinder duplicate() {
		Cloner cloneMaker = new Cloner();
		IProfilerBinder clonedProfilerBinder = cloneMaker.deepClone(this);
		return clonedProfilerBinder;
	}
	
}
