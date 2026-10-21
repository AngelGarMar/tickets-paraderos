package com.tickets.paraderos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class ParaderosRequest implements Serializable {
    @NotNull(message = "El campo origenId no debe estar vacío.")
    private Integer origenId;
    @NotNull(message = "El campo destinoId no debe estar vacío.")
    private Integer destinoId;
}
