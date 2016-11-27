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
import org.thuta.entities.interfaces.IPhoto;
import org.thuta.entities.interfaces.IPhotoPool;

import com.rits.cloning.Cloner;

@Entity
@Table(name = "photo_pool")
@TableGenerator(name = "PHOTO_POOL_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "PHOTO_POOL_GEN", allocationSize = 1)
@Access(value = AccessType.FIELD)
public class PhotoPool implements Serializable, IPhotoPool{
	private static final long serialVersionUID = 1L;
	
	@Transient
	private long id;
	
	@OneToMany(targetEntity = org.thuta.entities.Photo.class, fetch = FetchType.EAGER, mappedBy = "photoPool", cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<IPhoto> photoList;
	
	public PhotoPool() {
		this(new ArrayList<IPhoto>());
	}
	
	public PhotoPool(List<IPhoto> photoList) {
		this(0, photoList);
	}
	
	public PhotoPool(long id, List<IPhoto> photoList) {
		populateProperties(id, photoList);
	}
	
	public void populateProperties(long id, List<IPhoto> photoList) {
		this.id = id;
		this.photoList = photoList;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "PHOTO_POOL_GEN")
	@Access(value = AccessType.PROPERTY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<IPhoto> getPhotoList() {
		return photoList;
	}

	public void setPhotoList(List<IPhoto> photoList) {
		this.photoList = photoList;
	}

	public void addPhoto(IPhoto photo) {
		if (this.photoList == null) {
			this.photoList = new ArrayList<IPhoto>();
		}
		photo.setPhotoPool(this);
		this.photoList.add(photo);
	}
	
	public void removePhoto(IPhoto photo) {
		if (this.photoList != null) {
			this.photoList.remove(photo);
		}
	}
	
	public void appendPhoto(List<IPhoto> photos) {
		if (photos != null) {
			for (IPhoto photo : photos) {
				addPhoto(photo);
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
	
	public IPhotoPool duplicate() {
		Cloner cloneMaker = new Cloner();
		IPhotoPool clonedPhotoPool = cloneMaker.deepClone(this);
		return clonedPhotoPool;
	}
}
