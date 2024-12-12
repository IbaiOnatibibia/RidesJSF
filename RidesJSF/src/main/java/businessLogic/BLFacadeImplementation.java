package businessLogic;

import java.util.Date;


import java.util.List;
import java.util.ResourceBundle;

import dataAccess.DataAccess;
import domain.Ride;
import domain.User;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.UserAlreadyExistsException;
import exceptions.RideAlreadyExistException;

/**
 * It implements the business logic as a web service.
 */
public class BLFacadeImplementation implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation() {
		System.out.println("Creating BLFacadeImplementation instance");

		dbManager = new DataAccess();

	}

	public BLFacadeImplementation(DataAccess da) {

		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		dbManager = da;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<String> getDepartCities() {

		List<String> departLocations = dbManager.getDepartCities();

		return departLocations;

	}

	/**
	 * {@inheritDoc}
	 */
	public List<String> getDestinationCities(String origin) {

		List<String> targetCities = dbManager.getArrivalCities(origin);

		return targetCities;
	}

	/**
	 * {@inheritDoc}
	 */

	public Ride createRide(String origin, String destination, Date date, int nPlaces, float price, String driverEmail)
			throws RideMustBeLaterThanTodayException, RideAlreadyExistException {

		Ride ride = dbManager.createRide(origin, destination, date, nPlaces, price, driverEmail);
		return ride;
	};

	/**
	 * {@inheritDoc}
	 */

	public List<Ride> getRides(String origin, String destination, Date date) {
		List<Ride> rides = dbManager.getRides(origin, destination, date);
		return rides;
	}

	/**
	 * {@inheritDoc}
	 */

	public List<Date> getThisMonthDatesWithRides(String origin, String destination, Date date) {
		List<Date> dates = dbManager.getThisMonthDatesWithRides(origin, destination, date);
		return dates;
	}

	public void close() {
		DataAccess dB4oManager = new DataAccess();

	}

	/**
	 * {@inheritDoc}
	 */

	public void initializeBD() {
		dbManager.initializeDB();
	}

	public User LogIn(String email, String password) {
		User u = dbManager.LogIn(email, password);
		return u;
	}
	
	public boolean register(String email, String name, String password) throws UserAlreadyExistsException {
		boolean b = dbManager.register(email, name, password);
		return b;
	}
	
}
