package org.thuta.entities;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.thuta.entities.interfaces.IProfileType;
import org.thuta.entities.interfaces.IProfilerUser;
import org.thuta.entities.interfaces.IUser;

import com.rits.cloning.Cloner;

@Entity
@Table(name = "profile_type")
@TableGenerator(name = "PROFILE_TYPE_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "PROFILE_TYPE_GEN", allocationSize = 1)
@Access(value = AccessType.FIELD)
public class ProfileType implements IProfileType, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Transient
	private long id;
	private String code;
	private String name;
	private String description;
	
	public ProfileType() {
		this(null);
	}
	
	public ProfileType(String name) {
		this(null, name);
	}
	
	public ProfileType(String code, String name) {
		this(0, code, name);
	}
	
	public ProfileType(long id, String code, String name) {
		this(id, code, name, null);
	}
	
	public ProfileType(long id, String code, String name, String description) {
		populateProperties(id, code, name, description);
	}
	
	private void populateProperties(long id, String code, String name, String description) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "PROFILE_TYPE_GEN")
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
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	public IProfileType duplicate() {
		Cloner cloneMaker = new Cloner();
		IProfileType clonedProfileType = cloneMaker.deepClone(this);
		return clonedProfileType;
	}
	
}
