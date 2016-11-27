package org.thuta.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.thuta.entities.interfaces.IPrivilege;
import org.thuta.entities.interfaces.IProfileType;
import org.thuta.entities.interfaces.IProfilerUser;
import org.thuta.entities.interfaces.IRole;

import com.rits.cloning.Cloner;

@Entity
@Table(name = "role")
@TableGenerator(name = "ROLE_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "ROLE_GEN", allocationSize = 1)
@Access(value = AccessType.FIELD)
public class Role implements Serializable, IRole{
	private static final long serialVersionUID = 1L;
	
	@Transient
	private long id;
	private String code;
	private String description;
	@Column(name="role_name")
	private String roleName;
	
	@OneToMany(targetEntity = org.thuta.entities.Privilege.class, fetch = FetchType.EAGER)
	@JoinTable(name = "ROLE_PRIVILEGE_LINK", 
		joinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") }, 
		inverseJoinColumns = { @JoinColumn(name = "PRIVILEGE_ID", referencedColumnName = "ID") })
	@Fetch(value = FetchMode.SUBSELECT)
	private List<IPrivilege> privilegeList;
	
	public Role() {
		this(null);
	}
	
	public Role(List<IPrivilege> privilegeList) {
		this(null, privilegeList);
	}
	
	public Role(String roleName, List<IPrivilege> privilegeList) {
		this(null, roleName, privilegeList);
	}
	
	public Role(String description, String roleName, List<IPrivilege> privilegeList) {
		this(null, description, roleName, privilegeList);
	}
	
	public Role(String code, String description, String roleName, List<IPrivilege> privilegeList) {
		this(0, code, description, roleName, privilegeList);
	}
	
	public Role(long id, String code, String description, String roleName, List<IPrivilege> privilegeList) {
		populateProperties(id, code, description, roleName, privilegeList);
	}
	
	private void populateProperties(long id, String code, String description, String roleName, List<IPrivilege> privilegeList){
		this.id = id;
		this.code = code;
		this.description = description;
		this.roleName = roleName;
		this.privilegeList = privilegeList;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ROLE_GEN")
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<IPrivilege> getPrivilegeList() {
		return privilegeList;
	}

	public void setPrivilegeList(List<IPrivilege> privilegeList) {
		this.privilegeList = privilegeList;
	}

	public void addPrivilege(IPrivilege privilege) {
		if (this.privilegeList == null) {
			this.privilegeList = new ArrayList<IPrivilege>();
		}
		this.privilegeList.add(privilege);
	}

	public void removePrivilege(IPrivilege privilege) {
		if (this.privilegeList != null) {
			this.privilegeList.remove(privilege);
		}
	}

	public void appendPrivilege(List<IPrivilege> privilegeList) {
		if (privilegeList != null) {
			for (IPrivilege privilege : privilegeList) {
				addPrivilege(privilege);
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
	
	public IRole duplicate() {
		Cloner cloneMaker = new Cloner();
		IRole clonedRole = cloneMaker.deepClone(this);
		return clonedRole;
	}
	
	
}
