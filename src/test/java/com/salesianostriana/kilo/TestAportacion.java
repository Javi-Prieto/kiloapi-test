package com.salesianostriana.kilo;

import com.salesianostriana.kilo.entities.DetalleAportacion;
import com.salesianostriana.kilo.entities.KilosDisponibles;
import com.salesianostriana.kilo.entities.TipoAlimento;
import com.salesianostriana.kilo.repositories.TipoAlimentoRepository;
import com.salesianostriana.kilo.services.AportacionService;
import com.salesianostriana.kilo.services.TipoAlimentoSaveService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestAportacion {


   @Mock
    TipoAlimentoRepository tipoAlimentoRepository;

    @InjectMocks
    AportacionService aportacionService;

    @Test
    void cambiarKilosDetalles( ){

        DetalleAportacion detalleAportacion = DetalleAportacion.builder()
                .aportacion(null)
                .detalleAportacionPK(null)
                .cantidadKg(12)
                .build();

        KilosDisponibles kilosDisponibles = KilosDisponibles.builder()
                .id(1L)
                .cantidadDisponible(5.0)
                .build();

        TipoAlimento tipoAlimento = TipoAlimento.builder()
                .id(1L)
                .detalleAportaciones(List.of(detalleAportacion))
                .kilosDisponibles(kilosDisponibles)
                .nombre("arroz")
                .build();

        Mockito.when(tipoAlimentoRepository.save(detalleAportacion.getTipoAlimento())).thenReturn(tipoAlimento);



    }

}
