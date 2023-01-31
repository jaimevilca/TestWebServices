package es.codeurjc.mca.practica_1_pruebas_ordinaria.ticket;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonView;

import org.hibernate.annotations.CreationTimestamp;

import es.codeurjc.mca.practica_1_pruebas_ordinaria.event.Event;
import es.codeurjc.mca.practica_1_pruebas_ordinaria.user.User;

@Entity
public class Ticket {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public interface BasicAtt {}

    public interface CustomerAtt{}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(BasicAtt.class)
    private Long id;

    @JsonView(BasicAtt.class)
    private Double purchasePrice;

    @CreationTimestamp
    @JsonView(BasicAtt.class)
    private LocalDateTime createDateTime;

    @ManyToOne
    @JsonView(CustomerAtt.class)
    private User customer;

    @ManyToOne
    @JsonView(BasicAtt.class)
    private Event event;

    public Ticket() {
    }

    public Ticket(User customer, Event event) {
        this.customer = customer;
        this.event = event;
        this.purchasePrice = event.getPrice();
    }
 
}
