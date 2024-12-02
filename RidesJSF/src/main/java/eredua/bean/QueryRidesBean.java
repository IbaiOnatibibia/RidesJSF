package eredua.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import businessLogic.BLFacade;
import domain.Ride;

@ManagedBean(name = "QueryRides")
@RequestScoped
public class QueryRidesBean {

	BLFacade facadeBL = FacadeBean.getBusinessLogic();
	private String nondik;
	private List<String> AbiapuntuaList;
	private String nora;
	private List<String> HelmugaList;
	private Date data;
	private List<Ride> rides;

	public QueryRidesBean() {
		AbiapuntuaList = facadeBL.getDepartCities();
		HelmugaList = new ArrayList<>();
		rides= new ArrayList<>();
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
        return this.AbiapuntuaList;
    }
   
	public void setAbiapuntuaList(List<String> abiapuntuaList) {
		this.AbiapuntuaList = abiapuntuaList;
	}
	

    public List<String> getHelmugaList() {
        if (nondik == null || nondik.isEmpty()) 
            return new ArrayList<>(); 
            
        return facadeBL.getDestinationCities(nondik);
    }

    public void setHelmugaList(List<String> helmugaList) {
        this.HelmugaList = helmugaList;
    }
    	

	public String bilatu() {
		if (nondik != null && nora != null && data != null) {	
            rides = facadeBL.getRides(nondik, nora, data);
            return null;
        } else {
            rides = new ArrayList<>(); 
            return null;
        }
	}

}
