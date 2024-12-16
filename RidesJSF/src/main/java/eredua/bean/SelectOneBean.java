package eredua.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import businessLogic.BLFacade;
import domain.Ride;

@ManagedBean(name = "selectOne")
@SessionScoped
public class SelectOneBean {

BLFacade facadeBL = FacadeBean.getBusinessLogic();
private String user ="";
private List<String> userList = new ArrayList<>();
private List<Ride> rideList = new ArrayList<>();


public SelectOneBean() {
	
}

public String getUser() {
	return user;
}

public void setUser(String user) {
	this.user = user;
}

public List<String> getUserList() {
	return facadeBL.getUsers();
}

public void setUserList(List<String> userList) {
	this.userList = userList;
}

public List<Ride> getRideList() {
	if(user!=null && !user.isEmpty()) {
		return facadeBL.getRides(user);
	}
	return rideList;
}

public void setRideList(List<Ride> rideList) {
	this.rideList = rideList;
}





}
