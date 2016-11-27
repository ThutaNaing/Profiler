package org.thuta.entities.interfaces;

import java.util.ArrayList;
import java.util.List;

public interface IProfilerBinder {
	public long getId();
	public void setId(long id);
	public List<ITeam> getTeamList();
	public void setTeamList(List<ITeam> teamList);
	public void addTeam(ITeam team);
	public void removeTeam(ITeam team);
	public void appendTeam(List<ITeam> teams);
	public IProfilerBinder duplicate();
}
