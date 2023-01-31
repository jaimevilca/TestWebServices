package es.codeurjc.mca.practica_1_pruebas_ordinaria.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.codeurjc.mca.practica_1_pruebas_ordinaria.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EventRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EventRepository eventRepository;

    @Autowired
    ObjectMapper objectMapper;

    private Event eventPrueba1;
    private Event eventPrueba2;

    @MockBean
    private EventService eventService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() throws Exception {

        eventPrueba1 = new Event();
        eventPrueba1.setDescription("Event 1");
        eventPrueba1.setCreateDateTime(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        eventPrueba2 = new Event();
        eventPrueba2.setDescription("Event 2");
        eventPrueba1.setCreateDateTime(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getAllEventsTest() throws Exception {
        when(eventService.findAll()).thenReturn(Arrays.asList(eventPrueba1, eventPrueba2));

        mvc.perform(
                        get("/api/events/")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void postEventTest() throws Exception {

        Event createdEvent = new Event();
        createdEvent.setDescription("Event 100");
        createdEvent.setCreateDateTime(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        createdEvent.setName("Event name A");
        createdEvent.setPrice(100D);
        createdEvent.setMax_capacity(1000);
        createdEvent.setCurrent_capacity(500);

        User organizerUser = new User("Organizer User", "organizer@urjc.es", "pass", User.ROLE_ORGANIZER);

        createdEvent.setCreator(organizerUser);
        createdEvent.setImage("testing.jpg");


        when(eventService.createEvent(createdEvent)).thenReturn(createdEvent);

        mvc.perform(
                        post("/api/events/no-image/")
                                .content(objectMapper.writeValueAsString(createdEvent))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());
    }

}