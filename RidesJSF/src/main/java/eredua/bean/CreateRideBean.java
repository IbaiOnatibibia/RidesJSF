package eredua.bean;

import java.sql.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import businessLogic.BLFacade;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

@ManagedBean(name = "CreateRide")
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
	
	public String  createRide() {
		if(nondik!=null && nora!=null && data!=null && eserlekuak>0) {
			try {
				facadeBL.createRide(nondik, nora, data, eserlekuak, prezioa,"driver1@gmail.com");
				System.out.println("Viaje creado");
			} catch (RideMustBeLaterThanTodayException | RideAlreadyExistException e) {
				
				e.printStackTrace();
				System.out.println("Error al intenetar crear el viaje");
			}
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
