package com.tickets.paraderos.repositories;

import com.tickets.paraderos.entities.Paradero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParaderoRepository extends JpaRepository<Paradero, Integer> {
    List<Paradero> findByEstatusOrderByIdAsc(Integer estatus);
    Optional<Paradero> findByIdAndEstatus(Integer id, Integer estatus);
}
