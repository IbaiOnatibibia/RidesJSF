package eredua.bean;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import businessLogic.BLFacade;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

@ManagedBean(name = "createRide")
@RequestScoped
public class CreateRideBean {

	BLFacade facadeBL=FacadeBean.getBusinessLogic();
	private String nondik;
	private String nora;
	private Date data;
	private int eserlekuak;
	private int prezioa;
	
	public CreateRideBean() {
		
	}

	public String getNondik() {
		return nondik;
	}

	public void setNondik(String nondik) {
		this.nondik = nondik;
	}

	public String getNora() {
		return nora;
	}

	public void setNora(String nora) {
		this.nora = nora;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getEserlekuak() {
		return eserlekuak;
	}

	public void setEserlekuak(int eserlekuak) {
		this.eserlekuak = eserlekuak;
	}
	
	public String createRide() {
		
	    if (nondik != null && nora != null && data != null && eserlekuak > 0) {
	        try {
	            facadeBL.createRide(nondik, nora, data, eserlekuak, prezioa, "driver1@gmail.com");
	            FacesContext.getCurrentInstance().addMessage(null, 
	                new FacesMessage(FacesMessage.SEVERITY_INFO, "Bidaia sortu da", null));
	            return ""; 
	        } catch (RideMustBeLaterThanTodayException | RideAlreadyExistException e) {
	            FacesContext.getCurrentInstance().addMessage(null, 
	                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore bat gertatu da: " + e.getMessage(), null));
	            e.printStackTrace();
	        }
	    } else {
	        FacesContext.getCurrentInstance().addMessage(null, 
	            new FacesMessage(FacesMessage.SEVERITY_WARN, "Utsune guztiak bete behar dira.", null));
	    }
	    return null;
	}

	public int getPrezioa() {
		return prezioa;
	}

	public void setPrezioa(int prezioa) {
		this.prezioa = prezioa;
	}
}
