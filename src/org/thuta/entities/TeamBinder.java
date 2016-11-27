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
import org.thuta.entities.interfaces.IProfilerUser;
import org.thuta.entities.interfaces.ITeam;
import org.thuta.entities.interfaces.ITeamBinder;

import com.rits.cloning.Cloner;

@Entity
@Table(name = "team_binder")
@TableGenerator(name = "TEAM_BINDER_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "TEAM_BINDER_GEN", allocationSize = 1)
@Access(value = AccessType.FIELD)
public class TeamBinder implements Serializable, ITeamBinder {
	private static final long serialVersionUID = 1L;
	
	@Transient
	private long id;
	@OneToMany(targetEntity = org.thuta.entities.ProfilerUser.class, fetch = FetchType.EAGER, mappedBy = "teamBinder", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<IProfilerUser> profilerUserList;
	
	public TeamBinder() {
		this(new ArrayList<IProfilerUser>());
	}
	
	public TeamBinder(List<IProfilerUser> profilerUserList) {
		this(0, profilerUserList);
	}
	
	public TeamBinder(long id, List<IProfilerUser> profilerUserList) {
		populateProperties(id, profilerUserList);
	}
	
	public void populateProperties(long id, List<IProfilerUser> profilerUserList) {
		this.id = id;
		this.profilerUserList = profilerUserList;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TEAM_BINDER_GEN")
	@Access(value = AccessType.PROPERTY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<IProfilerUser> getProfilerUserList() {
		return profilerUserList;
	}

	public void setProfilerUserList(List<IProfilerUser> profilerUserList) {
		this.profilerUserList = profilerUserList;
	}
	
	public void addProfilerUser(IProfilerUser profilerUser) {
		if (this.profilerUserList == null) {
			this.profilerUserList = new ArrayList<IProfilerUser>();
		}
		this.profilerUserList.add(profilerUser);
	}
	
	public void removeProfilerUser(IProfilerUser profilerUser) {
		if (this.profilerUserList != null) {
			this.profilerUserList.remove(profilerUser);
		}
	}
	
	public void appendProfilerUser(List<IProfilerUser> profilerUsers) {
		if (profilerUsers != null) {
			for (IProfilerUser user : profilerUsers) {
				addProfilerUser(user);
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
	
	public ITeamBinder duplicate() {
		Cloner cloneMaker = new Cloner();
		ITeamBinder clonedTeamBinder = cloneMaker.deepClone(this);
		return clonedTeamBinder;
	}

}
