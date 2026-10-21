package com.tickets.paraderos.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "paraderos")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Paradero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    @Size(min = 1, max = 70)
    private String nombre;
    private Integer estatus;
    private Timestamp creado;
    private Timestamp modificado;
}
