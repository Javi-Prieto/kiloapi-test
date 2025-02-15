package com.salesianostriana.kilo.controllers;


import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.dtos.tipo_alimento.TipoAlimentoDTO;
import com.salesianostriana.kilo.entities.TipoAlimento;
import com.salesianostriana.kilo.services.TipoAlimentoService;
import com.salesianostriana.kilo.views.View;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("tipoAlimento")
@RequiredArgsConstructor
@Tag(name = "Tipo de alimento", description = "Este es el controlador de los tipos de alimento")
public class TipoAlimentoController {

    private final TipoAlimentoService tipoAlimentoService;

    @Operation(summary = "Obtiene todos los tipos de alimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Se han encontrado tipos de alimento",
            content = {
                    @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = TipoAlimentoDTO.class)),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "id": 2,
                                                    "nombre": "Pasta"
                                                },
                                                {
                                                    "id": 3,
                                                    "nombre": "Chocolate"
                                                }
                                            ]
                                            """
                            )
                    })
            }),
            @ApiResponse(responseCode = "404",
            description = "No se han encontrado tipos de alimentos",
            content = @Content)
    })
    @JsonView(View.TipoAlimentoView.AllTipoAlimentoView.class)
    @GetMapping("/")
    public ResponseEntity<List<TipoAlimentoDTO>> getAllTipoAlimento() {
        List<TipoAlimento> lista = tipoAlimentoService.findAll();


        if(lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            List<TipoAlimentoDTO> resultado = lista.stream()
                    .map(ta -> TipoAlimentoDTO.of(ta))
                    .toList();
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        }
    }

    @Operation(summary = "Obtiene un tipo de alimento en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Se ha encontrado el tipo de alimento",
            content = {
                    @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TipoAlimentoDTO.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "id": 4,
                                                "nombre": "Pasta",
                                                "kilosDisponibles": 10.0
                                            }
                                            """
                            )
                    })
            }),
            @ApiResponse(responseCode = "404",
            description = "No se ha encontrado el tipo de alimento por el ID",
            content = @Content)
    })
    @JsonView(View.TipoAlimentoView.TipoAlimentoByIdView.class)
    @GetMapping("/{id}")
    public ResponseEntity<TipoAlimentoDTO> getTipoAlimentoById(
            @Parameter(
                    description = "ID del tipo de alimento a buscar",
                    schema = @Schema(implementation = Long.class),
                    name = "id",
                    required = true
            )
            @PathVariable Long id) {
        Optional<TipoAlimento> resultado = tipoAlimentoService.findById(id);
        if(resultado.isPresent()) {
            return ResponseEntity.of(resultado.map(TipoAlimentoDTO::of));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @Operation(summary = "Crea un tipo de alimento")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
    description = "Cuerpo de la petición",
    content = {
            @Content(mediaType = "application/json",
            schema = @Schema(implementation = TipoAlimentoDTO.class),
            examples = {
                    @ExampleObject(
                            value = """
                                    {
                                        "nombre": "Atún en aceite de oliva"
                                    }
                                    """
                    )
            })
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
            description = "Se ha creado el tipo de alimento",
            content = {
                    @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TipoAlimentoDTO.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "id": 9,
                                                "nombre": "Atún en aceite de oliva",
                                                "kilosDisponibles": 0.0
                                            }
                                            """
                            )
                    })
            }),
            @ApiResponse(responseCode = "400",
            description = "Los datos del tipo de alimento son incorrectos",
            content = @Content)
    })
    @JsonView(View.TipoAlimentoView.TipoAlimentoByIdView.class)
    @PostMapping("/")
    public ResponseEntity<TipoAlimentoDTO> createTipoAlimento(@JsonView(View.TipoAlimentoView.TipoAlimentoRequest.class) @RequestBody TipoAlimentoDTO dto) {
        TipoAlimento creado = tipoAlimentoService.createTipoAlimento(dto);
        if(creado.getNombre() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {

            return ResponseEntity.status(HttpStatus.CREATED).body(TipoAlimentoDTO.of(creado));
        }
    }
    @Operation(summary = "Edita un tipo de alimento en base a su ID")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
    description = "Cuerpo de la petición",
    content = {
            @Content(mediaType = "application/json",
            schema = @Schema(implementation = TipoAlimentoDTO.class),
            examples = {
                    @ExampleObject(
                            value = """
                                    {
                                        "nombre": "Arroz"
                                    }
                                    """
                    )
            })
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Se ha editado el tipo de alimento",
            content = {
                    @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TipoAlimentoDTO.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "id": 4,
                                                "nombre": "Arroz",
                                                "kilosDisponibles": 60.0
                                            }
                                            """
                            )
                    })
            }),
            @ApiResponse(responseCode = "400",
            description = "Los datos del tipo de alimento no son correctos o no encuentra el tipo de alimento",
            content = @Content)
    })
    @JsonView(View.TipoAlimentoView.TipoAlimentoByIdView.class)
    @PutMapping("/{id}")
    public ResponseEntity<TipoAlimentoDTO> editTipoAlimento(
            @Parameter(
                    description = "ID del tipo de alimento a editar",
                    schema = @Schema(implementation = Long.class),
                    name = "id",
                    required = true
            )
            @PathVariable Long id, @JsonView(View.TipoAlimentoView.TipoAlimentoRequest.class) @RequestBody TipoAlimentoDTO dto) {
        Optional<TipoAlimento> editado = tipoAlimentoService.editTipoAlimento(id, dto);
        return editado.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(TipoAlimentoDTO.of(editado.get())) :
                                        ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Operation(summary = "Borra un tipo de alimento en base a su ID")
    @ApiResponse(responseCode = "204",
    description = "Se ha borrado el tipo de alimento",
    content = @Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTipoAlimento(
            @Parameter(
                    description = "ID del tipo de alimento a borrar",
                    schema = @Schema(implementation = Long.class),
                    name = "id",
                    required = true
            )
            @PathVariable Long id) {
        tipoAlimentoService.deleteTipoAlimento(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
