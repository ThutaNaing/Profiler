package org.thuta.dao.interfaces;

import java.util.List;

import org.thuta.entities.interfaces.IProfilerBinder;

public interface IProfilerBinderDAO {
	public void addProfilerBinder(IProfilerBinder profilerBinder);
	public void updateProfilerBinder(IProfilerBinder profilerBinder);
	public IProfilerBinder findProfilerBinder(String id);
	public List<IProfilerBinder> findAllProfilerBinder();
	public void removeProfilerBinder(IProfilerBinder profilerBinder);
	public int getNoOfRows();
}
