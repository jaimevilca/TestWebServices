package es.codeurjc.mca.practica_1_pruebas_ordinaria.event;

import es.codeurjc.mca.practica_1_pruebas_ordinaria.ticket.TicketRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestAPITest {

    @LocalServerPort
    int port;


    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://localhost:" + port;
    }

    @Test
    public void createEventLikeOrganizerUser() {

        given()
                .multiPart("multiparImage", loadFileResource("img.png"))
                .formParam("name", "Obra de teatro")
                .formParam("description", "Obra ofrecido por ...")
                .formParam("date", "2020-11-22T19:00:00+0000")
                .formParam("price", 19.99)
                .formParam("max_capacity", 10)
                .auth()
                .basic("Patxi", "pass")
                .when()
                .post("/api/events/").then()
                .assertThat().statusCode(201)
                .assertThat().body("name", equalTo("Obra de teatro"))
                .assertThat().body("description", equalTo("Obra ofrecido por ..."));

    }

    @Test
    public void createTicketAndCheckMaxCapacity() {

        given()

                .auth()
                .basic("Michel", "pass")
                .queryParam("eventId", 4)

                .when()
                .post("/api/tickets/").then()

                .assertThat().statusCode(201)
                .assertThat().body("id", notNullValue())
                .assertThat().body("event.current_capacity", equalTo(1))
              ;

    }


    @Test
    public void removeTicketAndCheckCapacity() {
        this.createTicketAndCheckMaxCapacity();

        given()

                .auth()
                .basic("Michel", "pass")

                .when()
                .delete("/api/tickets/5").then()

                .assertThat().statusCode(200)
        ;
        given()

                .auth()
                .basic("Michel", "pass")
                .pathParam("eventId", 4)

                .when()
                .get("/api/events/{eventId}").then()

                .assertThat().statusCode(200)
                .assertThat().body("current_capacity", equalTo(0))
        ;

    }


    @Test
    public void notRegistredUserCreateNewCustomer() {

        given()

                .contentType("application/json")
                .queryParam("type", "Customer")
                .body("{\"name\":\"test\",\"email\":\"test@test.com\",\"password\":\"pass\" }")


                .when()
                .post("/api/users/").then()

                .assertThat().statusCode(201)
                .assertThat().body("id", notNullValue())

        ;

    }

    @Test
    public void removeUserByAdminUserType() {

        given()

                .auth()
                .basic("admin", "pass")

                .when()
                .delete("/api/users/{id}",2).then()//No pasa el delete

                .assertThat().statusCode(204)
        ;


    }

    private File loadFileResource(String resourceName) {

        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(resourceName).getFile());
    }
}
