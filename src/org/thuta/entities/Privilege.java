package org.thuta.entities;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.thuta.entities.interfaces.IPrivilege;
import com.rits.cloning.Cloner;

@Entity
@Table(name = "privilege")
@TableGenerator(name = "PRIVILEGE_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "PRIVILEGE_GEN", allocationSize = 1)
@Access(value = AccessType.FIELD)
public class Privilege implements Serializable, IPrivilege{
	private static final long serialVersionUID = 1L;
	
	@Transient
	private long id;
	private String code;
	private String description;
	@Column(name="privilege_name")
	private String privilegeName;
	
	public Privilege() {
		this(null);
	}
	
	public Privilege(String privilegeName) {
		this(null, privilegeName);
	}
	
	public Privilege(String description, String privilegeName) {
		this(null, description, privilegeName);
	}
	
	public Privilege(String code, String description, String privilegeName) {
		this(0, code, description, privilegeName);
	}
	
	public Privilege(long id, String code, String description, String privilegeName) {
		populateProperties(id, code, description, privilegeName);
	}
	
	private void populateProperties(long id, String code, String description, String privilegeName){
		this.id = id;
		this.code = code;
		this.description = description;
		this.privilegeName = privilegeName;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "PRIVILEGE_GEN")
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

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	public IPrivilege duplicate() {
		Cloner cloneMaker = new Cloner();
		IPrivilege clonedPrivilege = cloneMaker.deepClone(this);
		return clonedPrivilege;
	}
	
	
}
