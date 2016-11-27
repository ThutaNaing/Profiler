package org.thuta.dao.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thuta.entities.TeamBinder;
import org.thuta.entities.interfaces.ITeamBinder;

public interface ITeamBinderDAO {
	public void addTeamBinder(ITeamBinder teamBinder) ;
	public void updateTeamBinder(ITeamBinder teamBinder);
	public ITeamBinder findTeamBinder(String id);
	public List<ITeamBinder> findAllTeamBinder();
	public void removeTeamBinder(ITeamBinder teamBinder);
	public int getNoOfRows();
}
