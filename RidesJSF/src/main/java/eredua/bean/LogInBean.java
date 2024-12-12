package eredua.bean;

import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import businessLogic.BLFacade;
import domain.User;

@ManagedBean(name = "logIn")
@SessionScoped
public class LogInBean {

	BLFacade facadeBL = FacadeBean.getBusinessLogic();
	private String email="";
	private String password= "";
	public LogInBean() {
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String login() {
	    if (email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
	        User u = facadeBL.LogIn(email, password);
	        if (u == null) {
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
	                "Errorea", "Erabiltzaile edo pasahitza okerra."));
	        } else {
	            return "Mainpage";
	        }
	    } else {
	        FacesContext.getCurrentInstance().addMessage(null,
	            new FacesMessage(FacesMessage.SEVERITY_WARN, 
	            "Abisua", "Utsune guztiak bete behar dira."));
	    }
	    return "";
	}
}
