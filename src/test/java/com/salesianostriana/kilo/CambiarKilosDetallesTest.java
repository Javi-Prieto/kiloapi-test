package com.salesianostriana.kilo;

import com.salesianostriana.kilo.entities.Aportacion;
import com.salesianostriana.kilo.entities.DetalleAportacion;
import com.salesianostriana.kilo.entities.KilosDisponibles;
import com.salesianostriana.kilo.entities.TipoAlimento;
import com.salesianostriana.kilo.repositories.AportacionRepository;
import com.salesianostriana.kilo.services.AportacionService;
import com.salesianostriana.kilo.services.ClaseService;
import com.salesianostriana.kilo.services.TipoAlimentoSaveService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = KiloApplication.class)
public class CambiarKilosDetallesTest {

    @Mock
    private AportacionRepository aportacionRepositoryMock;

    @Mock
    private TipoAlimentoSaveService tipoAlimentoSaveServiceMock;

    @Mock
    private ClaseService claseServiceMock;

    @InjectMocks
    private AportacionService aportacionService;

    @Test
    public void testCambiarKilosDetalle() {
        // Datos de prueba
        DetalleAportacion detalle = new DetalleAportacion();
        detalle.setCantidadKg(5.0);

        TipoAlimento tipoAlimento = new TipoAlimento();
        KilosDisponibles kilosDisponibles = new KilosDisponibles();
        kilosDisponibles.setCantidadDisponible(100.0);
        tipoAlimento.setKilosDisponibles(kilosDisponibles);
        detalle.setTipoAlimento(tipoAlimento);

        double kgNuevos = 10.0;


        TipoAlimento tipoAlimentoSimulado = new TipoAlimento();
        Aportacion aportacionSimulada = new Aportacion();


        


        Optional<Aportacion> resultado = aportacionService.cambiarKilosDetalle(detalle, kgNuevos);


        assertTrue(resultado.isPresent());

    }
}
