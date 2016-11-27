package org.thuta.entities.interfaces;

public interface ITeam {
	public long getId();
	public void setId(long id);
	public String getCode();
	public void setCode(String code);
	public String getName();
	public void setName(String name);
	public String getDescription();
	public void setDescription(String description);
	public IProfilerBinder getProfilerBinder();
	public void setProfilerBinder(IProfilerBinder profilerBinder);
	public ITeamBinder getTeamBinder();
	public void setTeamBinder(ITeamBinder teamBinder);
	public ITeam duplicate();
}
