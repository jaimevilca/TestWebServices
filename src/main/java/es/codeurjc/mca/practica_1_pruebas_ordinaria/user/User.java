package es.codeurjc.mca.practica_1_pruebas_ordinaria.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import es.codeurjc.mca.practica_1_pruebas_ordinaria.event.Event;
import es.codeurjc.mca.practica_1_pruebas_ordinaria.ticket.Ticket;

@Entity(name = "_user")
public class User {

	public static String ROLE_ORGANIZER = "ROLE_ORGANIZER";
	public static String ROLE_ADMIN = "ROLE_ADMIN";
	public static String ROLE_CUSTOMER = "ROLE_CUSTOMER";

	
	public interface BasicAtt {}
	public interface EventsAtt extends BasicAtt{}
	public interface TicketsAtt extends BasicAtt{}


	public User() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(BasicAtt.class)
	private Long id;
	
	@JsonView(BasicAtt.class)
	private String name;
	
	@JsonView(BasicAtt.class)
	@Column(unique = true)
	private String email;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@ElementCollection(fetch = FetchType.EAGER)
	@JsonView(BasicAtt.class)
	private List<String> roles;

	@OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
	@JsonView(EventsAtt.class)
	private List<Event> events;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	@JsonView(TicketsAtt.class)
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<Ticket> tickets;

	public User(String name, String email, String password, String... roles) {
		this.name = name;
		this.email = email;
		this.password = new BCryptPasswordEncoder().encode(password);
		this.roles = new ArrayList<>(Arrays.asList(roles));
		this.events = new ArrayList<>();
	}

	@Override
	public String toString(){
		return "User [id=" + id + ", name=" + this.getName() + ", email=" + this.getEmail() + "]";
	}

}