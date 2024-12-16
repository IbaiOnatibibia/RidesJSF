package eredua.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import businessLogic.BLFacade;
import exceptions.UserAlreadyExistsException;

@ManagedBean(name = "Register")
@SessionScoped
public class RegisterBean {

	BLFacade facadeBL = FacadeBean.getBusinessLogic();
	private String email = "";
	private String password = "";
	private String name = "";
	private String confirmPassword = "";

	public RegisterBean() {

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

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String register() {
		if (!password.equals(confirmPassword)) {
			 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Pasahitza eta konfirmazioa ez dira berdinak", "Errorea.");
	            FacesContext.getCurrentInstance().addMessage(null, message);
	    }
		else {
		if(email!=null && !email.isEmpty() && password!=null && !password.isEmpty() && confirmPassword!=null && !confirmPassword.isEmpty()) {
			try {
				facadeBL.register(email, name, confirmPassword);
				return "home";
			} catch (UserAlreadyExistsException e) {
				 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erabiltzailea existitzen da.", "Errorea.");
		            FacesContext.getCurrentInstance().addMessage(null, message);

			}
		}else {	
			 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Utsune guzztiak bete behar dira", "Errorea.");
	            FacesContext.getCurrentInstance().addMessage(null, message);
		}
		}
	return "";
}

}
