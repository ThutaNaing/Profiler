package org.thuta.entities.interfaces;

import java.util.List;

public interface IRole {
	public long getId();
	public void setId(long id);
	public String getCode();
	public void setCode(String code);
	public String getDescription();
	public void setDescription(String description);
	public String getRoleName();
	public void setRoleName(String roleName);
	public List<IPrivilege> getPrivilegeList();
	public void setPrivilegeList(List<IPrivilege> privilegeList);
	public void addPrivilege(IPrivilege privilege);
	public void removePrivilege(IPrivilege privilege);
	public void appendPrivilege(List<IPrivilege> privilegeList);
}
