package REQUEST;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import DTO.City;
import DTO.Country;
import DTO.Department;
import DTO.User;

public class Request implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Integer callLogIn(User user) {

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(UriBuilder.fromUri("http://localhost:3000/wsMATRIX").path("api").path("login").build());
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("username", user.getName());
		formData.add("pass", user.getPass());

		ClientResponse response = service.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class, formData);

		return response.getStatus();

	}

	public List<Object> getListData() {
		List<Object> info = new ArrayList<Object>();
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(UriBuilder.fromUri("http://localhost:3000/wsMATRIX").build());
		// getting JSON data
		System.out.println(service.path("api").path("alldata").accept(MediaType.APPLICATION_JSON).get(String.class));
		JSONArray json = new JSONArray(service.path("api").path("alldata").accept(MediaType.APPLICATION_JSON).get(String.class));

		for (int i = 0; i < json.length(); i++) {
			JSONArray data = json.getJSONArray(i);
			info.add(data);

		}
		return info;
	
		 
	}
	
	public int createCountry(Country country){
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		ClientResponse response = null;
		try {
			WebResource resource = client.resource(UriBuilder.fromUri("http://localhost:3000/wsMATRIX").path("api").path("Countries").build());
			MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
			formData.add("nameCountry", String.valueOf(country.getName()));
			
			// ws preparado para recibir solo @FormParam usa
			// APPLICATION_FORM_URLENCODED_TYPE

			 response = resource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class, formData);
			// JSONObject objO = response.getType(MediaType.APPLICATION_JSON);
			System.out.println(response);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return response.getStatus();
		
	}
	public int createDepartment(Department department){
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		ClientResponse response = null;
		try {
			WebResource resource = client.resource(UriBuilder.fromUri("http://localhost:3000/wsMATRIX").path("api").path("departments").build());
			MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
			formData.add("nameDep", String.valueOf(department.getName()));
			formData.add("countryId", String.valueOf(department.getCountry().getId()));
			
			// ws preparado para recibir solo @FormParam usa
			// APPLICATION_FORM_URLENCODED_TYPE

			 response = resource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class, formData);
			// JSONObject objO = response.getType(MediaType.APPLICATION_JSON);
			System.out.println(response);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return response.getStatus();
		
	}
	public int createCity(City city){
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		ClientResponse response = null;
		try {
			WebResource resource = client.resource(UriBuilder.fromUri("http://localhost:3000/wsMATRIX").path("api").path("cities").build());
			MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
			formData.add("nameCity", String.valueOf(city.getName()));
			formData.add("DepartmentId", String.valueOf(city.getDepartment().getId()));
			
			// ws preparado para recibir solo @FormParam usa
			// APPLICATION_FORM_URLENCODED_TYPE

			 response = resource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class, formData);
			// JSONObject objO = response.getType(MediaType.APPLICATION_JSON);
			System.out.println(response);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return response.getStatus();
		
	}
	
	public int createUser(User user){
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		ClientResponse response = null;
		try {
			WebResource resource = client.resource(UriBuilder.fromUri("http://localhost:3000/wsMATRIX").path("api").path("users").build());
			MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
			formData.add("username", String.valueOf(user.getName()));
			formData.add("pass", String.valueOf(user.getPass()));
			
			// ws preparado para recibir solo @FormParam usa
			// APPLICATION_FORM_URLENCODED_TYPE

			 response = resource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class, formData);
			// JSONObject objO = response.getType(MediaType.APPLICATION_JSON);
			System.out.println(response);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return response.getStatus();
		
	}

}
