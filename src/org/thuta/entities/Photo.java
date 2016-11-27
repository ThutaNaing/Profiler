package org.thuta.entities;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.thuta.entities.interfaces.IPhoto;
import org.thuta.entities.interfaces.IPhotoPool;
import org.thuta.entities.interfaces.IProfilerUser;
import org.thuta.entities.interfaces.IUser;

import com.rits.cloning.Cloner;

@Entity
@Table(name = "photo")
@TableGenerator(name = "PHOTO_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "PHOTO_GEN", allocationSize = 1)
@Access(value = AccessType.FIELD)
public class Photo implements Serializable, IPhoto {
	private static final long serialVersionUID = 1L;
	
	@Transient
	private long id;
	private String code;
	private String file_name;
	private String file_path;
	@Lob
	private byte[] photo_content;
	
	@ManyToOne(targetEntity = org.thuta.entities.PhotoPool.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "photo_pool_id", referencedColumnName = "id")
	private IPhotoPool photoPool;
	
	public Photo() {
		this(null);
	}
	
	public Photo(byte[] photo_content) {
		this(null, null, photo_content, null);
	}
	
	public Photo(byte[] photo_content, IPhotoPool photoPool) {
		this(null, null, photo_content, photoPool);
	}
	
	public Photo(String file_name, String file_path, IPhotoPool photoPool) {
		this(file_name, file_path, null, photoPool);
	}
	
	public Photo(String file_name, String file_path, byte[] photo_content, IPhotoPool photoPool) {
		this(null, file_name, file_path, photo_content, photoPool);
	}
	
	public Photo(String code, String file_name, String file_path, byte[] photo_content, IPhotoPool photoPool) {
		this(0, code, file_name, file_path, photo_content, photoPool);
	}
	
	public Photo(int id, String code, String file_name, String file_path, byte[] photo_content, IPhotoPool photoPool) {
		populateProperties(id, code, file_name, file_path, photo_content, photoPool);
	}
	
	private void populateProperties(long id, String code, String file_name, String file_path, byte[] photo_content, IPhotoPool photoPool){
		this.id = id;
		this.code = code;
		this.file_name = file_name;
		this.file_path = file_path;
		this.photo_content = photo_content;
		this.photoPool = photoPool;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "PHOTO_GEN")
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

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public byte[] getPhoto_content() {
		return photo_content;
	}

	public void setPhoto_content(byte[] photo_content) {
		this.photo_content = photo_content;
	}
	
	public IPhotoPool getPhotoPool() {
		return photoPool;
	}

	public void setPhotoPool(IPhotoPool photoPool) {
		this.photoPool = photoPool;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	public IPhoto duplicate() {
		Cloner cloneMaker = new Cloner();
		IPhoto clonedPhoto = cloneMaker.deepClone(this);
		return clonedPhoto;
	}
	
}
