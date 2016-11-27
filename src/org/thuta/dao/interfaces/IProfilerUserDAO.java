package org.thuta.dao.interfaces;

import java.util.List;

import org.thuta.entities.interfaces.IProfilerUser;
import org.thuta.entities.interfaces.IUser;

public interface IProfilerUserDAO {
	public void addProfilerUser(IProfilerUser profilerUser);
	public void updateProfilerUser(IProfilerUser profilerUser);
	public IProfilerUser findProfilerUser(long id);
	public List<IProfilerUser> findAllProfilerUser();
	public List<String> findAllProfilerUserNames();
	public List<IProfilerUser> findProfilerUserByCode(String code);
	public List<IProfilerUser> findProfilerUserOrderByUsage();
	public List<Object[]> findProfilerUserGroupByUsage();
	public int updateProfilerUseByexecuteUpdate();
	public IProfilerUser findProfilerUserByUser(IUser userEntity);
	public int getNoOfRows();
}
