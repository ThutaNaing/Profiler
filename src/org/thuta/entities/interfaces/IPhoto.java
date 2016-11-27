package org.thuta.entities.interfaces;

public interface IPhoto {
	public long getId();
	public void setId(long id);
	public String getCode();
	public void setCode(String code);
	public String getFile_name();
	public void setFile_name(String file_name);
	public String getFile_path();
	public void setFile_path(String file_path);
	public byte[] getPhoto_content();
	public void setPhoto_content(byte[] photo_content);
	public IPhotoPool getPhotoPool();
	public void setPhotoPool(IPhotoPool photoPool);
	public IPhoto duplicate();
}
