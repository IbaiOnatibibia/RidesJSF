package domain;

import java.io.*;


import java.util.Date;

import javax.persistence.*;



@Entity
public class Ride {
	@Id 
	@GeneratedValue
	private Integer rideNumber;
	private String origin;
	private String destination;
	private int nPlaces;
	private Date date;
	private float price;
	@ManyToOne(targetEntity=User.class , fetch = FetchType.LAZY)
	private User user;  
	
	public Ride(){
	}
	
	public Ride(Integer rideNumber, String origin, String destination, Date date, int nPlaces, float price, User user) {
		super();
		this.rideNumber = rideNumber;
		this.origin = origin;
		this.destination = destination;
		this.nPlaces = nPlaces;
		this.date=date;
		this.price=price;
		this.user = user;
	}

	

	public Ride(String origin, String destination,  Date date, int nPlaces, float price, User user) {
		super();
		this.origin = origin;
		this.destination = destination;
		this.nPlaces = nPlaces;
		this.date=date;
		this.price=price;
		this.user = user;
	}
	
	/**
	 * Get the  number of the ride
	 * 
	 * @return the ride number
	 */
	public Integer getRideNumber() {
		return rideNumber;
	}

	
	/**
	 * Set the ride number to a ride
	 * 
	 * @param ride Number to be set	 */
	
	public void setRideNumber(Integer rideNumber) {
		this.rideNumber = rideNumber;
	}


	/**
	 * Get the origin  of the ride
	 * 
	 * @return the origin location
	 */

	public String getOrigin() {
		return origin;
	}


	/**
	 * Set the origin of the ride
	 * 
	 * @param origin to be set
	 */	
	
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * Get the destination  of the ride
	 * 
	 * @return the destination location
	 */

	public String getDestination() {
		return destination;
	}


	/**
	 * Set the origin of the ride
	 * 
	 * @param destination to be set
	 */	
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * Get the free places of the ride
	 * 
	 * @return the available places
	 */
	
	/**
	 * Get the date  of the ride
	 * 
	 * @return the ride date 
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * Set the date of the ride
	 * 
	 * @param date to be set
	 */	
	public void setDate(Date date) {
		this.date = date;
	}

	
	public float getnPlaces() {
		return nPlaces;
	}

	/**
	 * Set the free places of the ride
	 * 
	 * @param  nPlaces places to be set
	 */

	public void setBetMinimum(int nPlaces) {
		this.nPlaces = nPlaces;
	}

	/**
	 * Get the driver associated to the ride
	 * 
	 * @return the associated driver
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Set the driver associated to the ride
	 * 
	 * @param driver to associate to the ride
	 */
	public void setUser(User user) {
		this.user = user;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}



	public String toString(){
		return rideNumber+";"+";"+origin+";"+destination+";"+date;  
	}




	
}
