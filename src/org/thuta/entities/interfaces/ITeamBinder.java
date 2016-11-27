package org.thuta.entities.interfaces;

import java.util.ArrayList;
import java.util.List;

public interface ITeamBinder {
	public long getId();
	public void setId(long id);
	public List<IProfilerUser> getProfilerUserList();
	public void setProfilerUserList(List<IProfilerUser> profilerUserList);
	public void addProfilerUser(IProfilerUser profilerUser);
	public void removeProfilerUser(IProfilerUser profilerUser);
	public void appendProfilerUser(List<IProfilerUser> profilerUsers);
	public ITeamBinder duplicate();
}
