package domain;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
	@Id
	private String email;
	private String name;
	private String pasahitza;
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<Ride> rides=new ArrayList<Ride>();
	
	public User() {
		this.email = "";
		this.name = "";
		this.pasahitza = "";
	}
	
	public User(String email, String name, String password) {
		this.email = email;
		this.name = name;
		this.pasahitza = password;
	}
	
	public User(String email, String name, String password, List<Ride> rides) {
		this.email = email;
		this.name = name;
		this.pasahitza = password;
		this.rides=rides;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasahitza() {
		return pasahitza;
	}

	public void setPasahitza(String pasahitza) {
		this.pasahitza = pasahitza;
	}
	
	
	public List<Ride> getRides() {
		return rides;
	}

	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (!email.equals(other.email))
			return false;
		return true;
	}
	public String toString(){
		return this.getEmail()+";"+this.getName()+rides;
	}
	
	/**
	 * This method creates a bet with a question, minimum bet ammount and percentual profit
	 * 
	 * @param question to be added to the event
	 * @param betMinimum of that question
	 * @return Bet
	 */
	public Ride addRide(String origin, String destination, Date date, int nPlaces, float price)  {
        Ride ride=new Ride(origin,destination,date,nPlaces,price, this);
        rides.add(ride);
        return ride;
	}

	/**
	 * This method checks if the ride already exists for that driver
	 * 
	 * @param from the origin location 
	 * @param to the destination location 
	 * @param date the date of the ride 
	 * @return true if the ride exists and false in other case
	 */
	public boolean doesRideExists(String origin, String destination, Date date)  {	
		for (Ride r:rides)
			if ( (java.util.Objects.equals(r.getOrigin(),origin)) && (java.util.Objects.equals(r.getDestination(),destination)) && (java.util.Objects.equals(r.getDate(),date)) )
			 return true;
		
		return false;
	}
		

	public Ride removeRide(String origin, String destination, Date date) {
		boolean found=false;
		int index=0;
		Ride r=null;
		while (!found && index<=rides.size()) {
			r=rides.get(++index);
			if ( (java.util.Objects.equals(r.getOrigin(),origin)) && (java.util.Objects.equals(r.getDestination(),destination)) && (java.util.Objects.equals(r.getDate(),date)) )
			found=true;
		}
			
		if (found) {
			rides.remove(index);
			return r;
		} else return null;
	}
}
