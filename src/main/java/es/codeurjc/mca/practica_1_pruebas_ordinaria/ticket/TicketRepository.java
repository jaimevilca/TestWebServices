package es.codeurjc.mca.practica_1_pruebas_ordinaria.ticket;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
}
