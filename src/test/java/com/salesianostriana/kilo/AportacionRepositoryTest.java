package com.salesianostriana.kilo;

import com.salesianostriana.kilo.dtos.aportaciones.AportacionesReponseDTO;
import com.salesianostriana.kilo.entities.Aportacion;
import com.salesianostriana.kilo.entities.Clase;
import com.salesianostriana.kilo.entities.DetalleAportacion;
import com.salesianostriana.kilo.repositories.AportacionRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Sql(value = {"classpath:import-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class AportacionRepositoryTest {
    @Autowired
    EntityManager em;

    @Autowired
    AportacionRepository repository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"))
            .withUsername("user")
            .withPassword("user")
            .withDatabaseName("test");



    @Test
    void getAllAportaciones(){
        assertEquals(3, repository.getAllAportaciones().size());
        List<AportacionesReponseDTO> expected = List.of(
                new AportacionesReponseDTO(2L, LocalDate.of(2022, 12,13), "1 DAM", 10),
                new AportacionesReponseDTO(3L, LocalDate.of(2022, 12,13), "2 DAM", 12),
                new AportacionesReponseDTO(1L, LocalDate.of(2022, 12,13), "1 DAM", 8)
        );
        assertEquals(expected.stream().mapToDouble(AportacionesReponseDTO::getCantidadTotalKg).sum(), repository.getAllAportaciones().stream().mapToDouble(AportacionesReponseDTO::getCantidadTotalKg).sum());
        assertEquals(expected.stream().map(AportacionesReponseDTO::getId).toList(), repository.getAllAportaciones().stream().map(AportacionesReponseDTO::getId).toList());
        assertEquals(expected.get(0).getFecha(), repository.getAllAportaciones().get(0).getFecha());

    }
}
