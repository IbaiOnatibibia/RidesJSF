package dataAccess;

import java.io.File;

import java.net.NoRouteToHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import configuration.UtilDate;
import domain.Ride;
import domain.User;
import eredua.bean.HibernateUtil;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.UserAlreadyExistsException;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess {

	public DataAccess() {

			initializeDB();

	}

	/**
	 * This is the data access method that initializes the database with some events
	 * and questions. This method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		try {

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 1;
				year += 1;
			}

			// Create drivers
			User user1 = new User("driver1@gmail.com", "Aitor Fernandez", "1");
			User user2 = new User("driver2@gmail.com", "Ane Gaztañaga", "1");
			User user3 = new User("driver3@gmail.com", "Test driver", "1");

			// Create rides
			user1.addRide("Donostia", "Bilbo", UtilDate.newDate(year, month, 15), 4, 7);
			user2.addRide("Donostia", "Gazteiz", UtilDate.newDate(year, month, 6), 4, 8);
			user3.addRide("Bilbo", "Donostia", UtilDate.newDate(year, month, 25), 4, 4);

			user1.addRide("Donostia", "Iruña", UtilDate.newDate(year, month, 7), 4, 8);

			user1.addRide("Donostia", "Bilbo", UtilDate.newDate(year, month, 15), 3, 3);
			user2.addRide("Bilbo", "Donostia", UtilDate.newDate(year, month, 25), 2, 5);
			user2.addRide("Eibar", "Gasteiz", UtilDate.newDate(year, month, 6), 2, 5);

			user3.addRide("Bilbo", "Donostia", UtilDate.newDate(year, month, 14), 1, 3);

			session.persist(user1);
			session.persist(user2);
			session.persist(user3);

			session.getTransaction().commit();
			System.out.println("Db initialized");
		} catch (Exception e) {
			e.printStackTrace();
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
		}
	}

	/**
	 * This method returns all the cities where rides depart
	 * 
	 * @return collection of cities
	 */
	public List<String> getDepartCities() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<String> cities = session.createQuery("SELECT DISTINCT r.origin FROM Ride r ORDER BY r.origin").list();
		session.getTransaction().commit();
		return cities;

	}

	/**
	 * This method returns all the arrival destinations, from all rides that depart
	 * from a given city
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	public List<String> getArrivalCities(String origin) {

		System.out.println("ArrivaleCities en sartua");
		List<String> arrivingCities = new ArrayList<String>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			Query query = session.createQuery(
					"SELECT DISTINCT r.destination FROM Ride r WHERE r.origin=:origin ORDER BY r.destination");
			query.setParameter("origin", origin);
			arrivingCities = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		System.out.println(arrivingCities);
		return arrivingCities;

	}

	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from        the origin location of a ride
	 * @param to          the destination location of a ride
	 * @param date        the date of the ride
	 * @param nPlaces     available seats
	 * @param driverEmail to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today
	 * @throws RideAlreadyExistException         if the same ride already exists for
	 *                                           the driver
	 */
	public Ride createRide(String origin, String destination, Date date, int nPlaces, float price, String driverEmail)
			throws RideAlreadyExistException, RideMustBeLaterThanTodayException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		System.out.println(">> DataAccess: createRide=> from= " + origin + " to= " + destination + " driver="
				+ driverEmail + " date " + date);
		try {
			if (new Date().compareTo(date) > 0) {
				throw new RideMustBeLaterThanTodayException();
			}

			User user = (User) session.get(User.class, driverEmail);
			if (user.doesRideExists(origin, destination, date)) {
				session.getTransaction().commit();
				throw new RideAlreadyExistException();
			}
			Ride ride = user.addRide(origin, destination, date, nPlaces, price);
			// next instruction can be obviated
			session.persist(user);
			session.getTransaction().commit();

			return ride;
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			session.getTransaction().rollback();
			return null;
		}

	}

	/**
	 * This method retrieves the rides from two locations on a given date
	 * 
	 * @param from the origin location of a ride
	 * @param to   the destination location of a ride
	 * @param date the date of the ride
	 * @return collection of rides
	 */
	public List<Ride> getRides(String origin, String destination, Date date) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		System.out.println(">> DataAccess: getRides=> from= " + origin + " to= " + destination + " date " + date);

		List<Ride> res = new ArrayList<>();
		Query query = session.createQuery("FROM Ride  WHERE origin= :origin AND destination= :destination AND date= :date");
		System.out.println(query);
		query.setParameter("origin", origin);
		query.setParameter("destination", destination);
		query.setParameter("date", date);
		List<Ride> rides = query.list();
		for (Ride ride : rides) {
			res.add(ride);
		}
		session.getTransaction().commit();
		return res;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param from the origin location of a ride
	 * @param to   the destination location of a ride
	 * @param date of the month for which days with rides want to be retrieved
	 * @return collection of rides
	 */
	public List<Date> getThisMonthDatesWithRides(String origin, String destination, Date date) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		System.out.println(">> DataAccess: getEventsMonth");
		List<Date> res = new ArrayList<>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		Query query = session.createQuery(
				"SELECT DISTINCT r.date FROM Ride r WHERE r.origin=?1 AND r.destination=?2 AND r.date BETWEEN ?3 and ?4");

		query.setParameter(1, origin);
		query.setParameter(2, destination);
		query.setParameter(3, firstDayMonthDate);
		query.setParameter(4, lastDayMonthDate);
		List<Date> dates = query.list();
		for (Date d : dates) {
			res.add(d);
		}
		session.getTransaction().commit();
		return res;
	}

	public User LogIn(String email, String password) {
		User u = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		try {
			session.beginTransaction();
			Query query = session.createQuery(" FROM User WHERE email = :email AND pasahitza = :password");
			query.setParameter("email", email);
			query.setParameter("password", password);
			u = (User) query.uniqueResult();
			session.getTransaction().commit();
		} catch (NoResultException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}

		return u;
	}

	public boolean register(String email, String name, String password) throws UserAlreadyExistsException {
	    boolean userExists = false;
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    
	    try {
	        session.beginTransaction();
	        
	        Query query = session.createQuery("FROM User u WHERE u.email = :email");
	        query.setParameter("email", email);
	        
	        userExists = query.list().isEmpty();
	        
	        if (userExists) {
	              List<Ride> rides = new ArrayList<>();
	              User user = new User(email, name, password, rides);
	              session.persist(user);	            
	        } else {
	        	session.getTransaction().rollback();
	            throw new UserAlreadyExistsException();
	        }

	        session.getTransaction().commit();
	    }catch(	Exception e){
	            session.getTransaction().rollback();
	        e.printStackTrace();
	    }

	    return userExists;
	}

}
