package org.thuta.entities.interfaces;

public interface IHit {
	public int getId();
	public void setId(int id);
	public String getCode();
	public void setCode(String code);
	public String getName();
	public void setName(String name);
	public String getDescription();
	public void setDescription(String description);
	public IProfile getProfile();
	public void setProfile(IProfile profile);
	public void setProfilerUser(IProfilerUser profilerUser);
	public IProfilerUser getProfilerUser();
	public ITeam getTeam();
	public void setTeam(ITeam team);
	public boolean isPublicHit();
	public void setPublicHit(boolean publicHit);
	public boolean isTeamHit();
	public void setTeamHit(boolean teamHit);
	public IHit duplicate();
}
