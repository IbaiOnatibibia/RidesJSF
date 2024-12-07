package eredua.bean;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.AjaxBehaviorEvent;

import businessLogic.BLFacade;
import domain.Ride;

@ManagedBean(name = "queryRides")
@RequestScoped
public class QueryRidesBean {

	BLFacade facadeBL = FacadeBean.getBusinessLogic();
	private List<String> abiapuntuaList = new ArrayList<>();
	private String nondik="";
	private String nora = "";
	private List<String> helmugaList =new ArrayList<>();;
	private Date data = new Date();
	private List<Ride> rides= new ArrayList<>();;

	public QueryRidesBean() {
		
	}

	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}

	public List<Ride> getRides() {
		return rides;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
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

	public List<String> getAbiapuntuaList() {
		return  facadeBL.getDepartCities();
	}

	public void setAbiapuntuaList(List<String> abiapuntuaList) {
		this.abiapuntuaList = abiapuntuaList;
	}

	public List<String> getHelmugaList() {
		if(nondik!=null && !nondik.isEmpty()) {
			return facadeBL.getDestinationCities(nondik);
		}
		return new ArrayList<>();
	}


	public void setHelmugaList(List<String> helmugaList) {
		this.helmugaList = helmugaList;
	}

	public String bilatu() {
		System.out.println("abe");
		if (nondik != null && nora != null && data != null) {
			rides = facadeBL.getRides(nondik, nora, data);
			return "";
		} else {
			rides = new ArrayList<>();
			return "";
		}
	}

}
