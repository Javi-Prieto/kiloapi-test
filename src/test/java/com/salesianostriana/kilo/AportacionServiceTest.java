package com.salesianostriana.kilo;

import com.salesianostriana.kilo.entities.*;
import com.salesianostriana.kilo.repositories.AportacionRepository;
import com.salesianostriana.kilo.services.AportacionService;
import com.salesianostriana.kilo.services.TipoAlimentoSaveService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AportacionServiceTest {

    @Mock
    AportacionRepository aportacionRepository;
    @Mock
    TipoAlimentoSaveService tipoAlimentoSaveService;

    @InjectMocks
    AportacionService aportacionService;

    public static Stream<Arguments> getData(){
        return Stream.of(
                Arguments.arguments(Aportacion.builder()
                        .clase(Clase.builder().build())
                        .detalleAportaciones(List.of(DetalleAportacion.builder().cantidadKg(0).tipoAlimento(
                                TipoAlimento.builder()
                                        .kilosDisponibles(KilosDisponibles.builder().cantidadDisponible(10d).build())
                                        .build()
                        ).build()))
                        .id(1L)
                        .build()),
                Arguments.arguments(Aportacion.builder()
                        .clase(Clase.builder().build())
                        .detalleAportaciones(List.of())
                        .id(1L)
                        .build()),
                Arguments.arguments(Aportacion.builder()
                        .clase(Clase.builder().build())
                        .detalleAportaciones(null)
                        .id(1L)
                        .build()),
                null
        );
    }

    @ParameterizedTest
    @MethodSource("getData")
    void deleteDetalleAportacion(Aportacion a){
        if (a == null){
            Throwable throwable = assertThrows(NullPointerException.class ,() -> aportacionService.borrarDetallesAportacion(a));
            assertEquals("Cannot invoke \"com.salesianostriana.kilo.entities.Aportacion.getDetalleAportaciones()\" because \"a\" is null",throwable.getMessage() );
        }
        TipoAlimento tipo = TipoAlimento.builder().build();
        DetalleAportacion detalle = DetalleAportacion.builder().tipoAlimento(tipo).build();


        if(a.getDetalleAportaciones().isEmpty()){
            verify(tipoAlimentoSaveService, never()).save(detalle.getTipoAlimento());

        } else{
            when(aportacionRepository.findDetallesBorrables()).thenReturn(a.getDetalleAportaciones());
            verify(tipoAlimentoSaveService, atLeast(1)).save(detalle.getTipoAlimento());
        }

        verify(aportacionRepository, atMost(1)).save(a);
    }

}
