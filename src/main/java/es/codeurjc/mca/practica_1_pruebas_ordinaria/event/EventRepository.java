package es.codeurjc.mca.practica_1_pruebas_ordinaria.event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    
}
