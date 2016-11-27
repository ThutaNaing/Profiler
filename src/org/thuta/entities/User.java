package org.thuta.entities;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.thuta.entities.interfaces.IProfilerUser;
import org.thuta.entities.interfaces.IUser;

import com.rits.cloning.Cloner;

@Entity
@Table(name = "user")
@TableGenerator(name = "USER_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "USER_GEN", allocationSize = 1)
@Access(value = AccessType.FIELD)
public class User implements Serializable, IUser, UserDetails{
	private static final long serialVersionUID = 1L;
	
	@Transient
	private long id;
	private String code;
	private String description;
	private String username;
	private String password;
	
	@OneToOne(targetEntity=org.thuta.entities.ProfilerUser.class,mappedBy="user",fetch=FetchType.EAGER,cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name="profiler_user_id",referencedColumnName="id")
	private IProfilerUser profilerUser;
	
	public User() {
		this(null);
	}
	
	public User(IProfilerUser profilerUser) {
		this(null, profilerUser);
	}
	
	public User(String password, IProfilerUser profilerUser) {
		this(null, password, profilerUser);
	}
	
	public User(String username, String password) {
		this(username, password, null);
	}
	
	public User(String username, String password, IProfilerUser profilerUser) {
		this(null, username, password, profilerUser);
	}
	
	public User(String description, String username, String password, IProfilerUser profilerUser) {
		this(null, description, username, password, profilerUser);
	}
	
	public User(String code, String description, String username, String password, IProfilerUser profilerUser) {
		this(0,code, description, username, password, profilerUser);
	}
	
	public User(long id, String code, String description, String username, String password, IProfilerUser profilerUser) {
		populateProperties(id, code, description, username, password, profilerUser);
	}
	
	private void populateProperties(long id, String code, String description, String username, String password, IProfilerUser profilerUser){
		this.id = id;
		this.code = code;
		this.description = description;
		this.username = username;
		this.password = password;
		this.profilerUser = profilerUser;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "USER_GEN")
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public IProfilerUser getProfilerUser() {
		return profilerUser;
	}

	public void setProfilerUser(IProfilerUser profilerUser) {
		this.profilerUser = profilerUser;
		if(profilerUser != null) {
			profilerUser.setUser(this);
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
	
	public IUser duplicate() {
		Cloner cloneMaker = new Cloner();
		IUser clonedUser = cloneMaker.deepClone(this);
		return clonedUser;
	}

	
	/**
	 * Spring UserDetails implementation methods
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
}
