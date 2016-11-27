package org.thuta.dao;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.parser.Entity;

import org.hibernate.Query;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thuta.dao.interfaces.IHitDAO;
import org.thuta.dao.interfaces.IProfileDAO;
import org.thuta.dao.interfaces.IProfilerUserDAO;
import org.thuta.dao.interfaces.IUserDAO;
import org.thuta.entities.Hit;
import org.thuta.entities.Profile;
import org.thuta.entities.ProfilerUser;
import org.thuta.entities.TeamBinder;
import org.thuta.entities.User;
import org.thuta.entities.interfaces.IHit;
import org.thuta.entities.interfaces.IProfile;
import org.thuta.entities.interfaces.IProfilerUser;
import org.thuta.entities.interfaces.ITeamBinder;
import org.thuta.entities.interfaces.IUser;

@Repository("ProfileDAO")
@Transactional(propagation=Propagation.SUPPORTS)
public class ProfileDAO extends RootDAO implements IProfileDAO {
	private static int noOfRows;

	@Transactional(propagation=Propagation.REQUIRED)
	public void addProfile(IProfile profile) {
		currentSession().save(profile);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateProfile(IProfile profile) {
		currentSession().update(profile);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public IProfile findProfile(String id) {
		return (IProfile) currentSession().get(Profile.class, id);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public IProfile findProfile(Long id) {
		return (IProfile) currentSession().get(Profile.class, id);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<IProfile> findAllProfile() {
		List<IProfile> result = new ArrayList<IProfile>();
		Query q = currentSession().createQuery("from org.thuta.entities.Profile");
		noOfRows = q.list().size();
		result = q.list();
		return result;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void removeProfile(IProfile profile) {
		if(profile != null) {
			IProfile profileTemp = (IProfile) currentSession().get(Profile.class, profile.getId());
			currentSession().delete(profileTemp);
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<IProfile> findProfileByHit(IHit hit) {
		List<IProfile> result = new ArrayList<IProfile>();
		Query q = currentSession().createQuery("select hPro from Profile p, Hit h join h.profile hPro where hPro.id = p.id and h.id = :Id");
		q.setParameter("Id", hit.getId());
		noOfRows = q.list().size();
		result = q.list();
		return result;
	}
	
	public int getNoOfRows() {
		return noOfRows;
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
		IProfileDAO profileDao = (IProfileDAO) context.getBean("ProfileDAO");
		IHitDAO hitDao = (IHitDAO) context.getBean("HitDAO");
		IProfilerUserDAO profilerUserDao = (IProfilerUserDAO) context.getBean("ProfilerUserDAO");
		IUserDAO userDao = (IUserDAO) context.getBean("UserDAO");
		
//		IProfilerUser profilerUserEntity = new ProfilerUser("PUR-2", "Thinwai", "", "", "094637722", "SanChaung", "www.soul-eater.com", null, null, null);
//		IUser userEntity = new User("Thinwai", "thinwaipassword");
//		userEntity.setProfilerUser(profilerUserEntity);
//		userDao.addUser(userEntity);
		
		/*
		IUser userEntity = userDao.findUser(1);
		IProfilerUser profilerUserEntity = profilerUserDao.findProfilerUserByUser(userEntity);
		System.out.println("Profile Hitter Name is : " + profilerUserEntity.getName());
		
		IHit hitEntity = new Hit("HIT-1", "HottyGirlsHit", "HottyGirlsHit", null, null, null, true, false);
		IHit hitEntityTwice = new Hit("HIT-2", "ProgrammingHit", "ProgrammingHit", null, null, null, true, false);
		IProfile profileEntity = new Profile("PRO-1", "Hot Issue", "Today girls are so hot at Myanmar. Wanna assure my words? You can come and check yourself at Myanigone Township, Yangon", "", "", null, null, true);
		IProfile profileEntityTwice = new Profile("PRO-1", "Today Programmming", "Programming concert not only with OOP but also with other factors like(DI,AOP,Design Pattern etc). Unless you only know OOP concept and don't read well formed programming design pattern, you will be back to programmer of 1990 century.", "", "", null, null, true);
		hitEntity.setProfile(profileEntity);
		hitEntityTwice.setProfile(profileEntityTwice);
		
		profilerUserEntity.addHit(hitEntity);
		profilerUserEntity.addHit(hitEntityTwice);
		
		profilerUserDao.updateProfilerUser(profilerUserEntity);
		*/
		
		List<IProfile> profileList = new ArrayList<IProfile>();
		List<IHit> hitList = hitDao.findAllPublicHit();
		if(hitList != null && !hitList.isEmpty()) {
			
			for(IHit hit : hitList) {
				IProfile profileEntity = profileDao.findProfileByHit(hit).get(0);
				System.out.println("Profile Entity =====================> " + profileEntity.getTopic());
//				profileList.add(profileEntity);
			}
			
		}
		
		
	}
}
