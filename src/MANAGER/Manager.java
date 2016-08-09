package MANAGER;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

import DTO.City;
import DTO.Country;
import DTO.Department;
import DTO.User;
import REQUEST.Request;

@RequestScoped
@ManagedBean(name = "manageBean")
@ViewScoped
public class Manager implements Serializable {

	private static final long serialVersionUID = 1L;
	User user = new User();
	Country country = new Country();
	Department department = new Department();
	City city = new City();
	List<Country> countries = new ArrayList<Country>();
	List<Department> departments = new ArrayList<Department>();
	List<City> cities = new ArrayList<City>();
	List<Object> objects = new ArrayList<Object>();
	Request request = new Request();
	

	/**
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(Country country) {
		this.country = country;
	}
	
	
	/**
	 * @return the department
	 */
	public Department getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(Department department) {
		this.department = department;
	}

	/**
	 * @return the city
	 */
	public City getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(City city) {
		this.city = city;
	}

	/**
	 * @return the countries
	 */
	public List<Country> getCountries() {
		return countries;
	}

	/**
	 * @param countries
	 *            the countries to set
	 */
	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}

	/**
	 * @return the departments
	 */
	public List<Department> getDepartments() {
		return departments;
	}

	/**
	 * @param departments
	 *            the departments to set
	 */
	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	/**
	 * @return the cities
	 */
	public List<City> getCities() {
		return cities;
	}

	/**
	 * @param cities
	 *            the cities to set
	 */
	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	// comprobacion de usuario valido.
	public void logIn(User user) throws IOException {

		Integer respt = request.callLogIn(user);

		if (respt == 200) {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", user);
			FacesContext.getCurrentInstance().getExternalContext().redirect("viewAll.xhtml");
		} else
			FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
	}

	@PostConstruct
	public void init() {
		objects = request.getListData();

		JSONArray data1 = (JSONArray) objects.get(0);
		for (int x = 0; x < data1.length(); x++) {
			JSONObject dataCou = data1.getJSONObject(x);

			Country country = new Country();
			country.setName(dataCou.getString("name"));
			country.setId(dataCou.getInt("id"));

			countries.add(country);
		}
		JSONArray data2 = (JSONArray) objects.get(1);
		for (int x = 0; x < data2.length(); x++) {
			JSONObject dataDep = data2.getJSONObject(x);

			Department department = new Department();

			department.setName(dataDep.getString("name"));
			department.setId(dataDep.getInt("id"));
			department.getCountry().setId(dataDep.getJSONObject("country").getInt("id"));

			departments.add(department);
		}
		JSONArray data3 = (JSONArray) objects.get(2);
		for (int x = 0; x < data3.length(); x++) {
			JSONObject dataCit = data3.getJSONObject(x);

			City city = new City();

			city.setName(dataCit.getString("name"));
			city.setId(dataCit.getInt("id"));
			city.getDepartment().setId(dataCit.getJSONObject("department").getInt("id"));

			cities.add(city);
		}

	}

	public void checkSession() {
		try {
			User us = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
			if (us == null) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void createCountry(Country country){
		try {
			if(country.getName() != null)
			{
				request.createCountry(country);
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("OK","El pais "+country.getName() +" ha sido agregado correctamente."));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void createDepartment(Department department){
		try {
			if(department.getName() != null)
			{
				request.createDepartment(department);
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("OK","El departamento "+department.getName() +" ha sido agregado correctamente."));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void createCity(City city){
		try {
			if(city.getName() != null)
			{
				request.createCity(city);
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("OK","La ciudad "+city.getName() +" ha sido agregado correctamente."));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void createUser(User user){
		int response = 0;
		try {
			if(user.getName() != null)
			{
				response = request.createUser(user);
			}
			
			if (response == 201)
				FacesContext.getCurrentInstance().getExternalContext().redirect("viewAll.xhtml");
			else
				FacesContext.getCurrentInstance().getExternalContext().redirect("users.xhtml");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void sendToRegister() throws IOException
	{
		FacesContext.getCurrentInstance().getExternalContext().redirect("users.xhtml");
	}

}
