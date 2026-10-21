package com.tickets.paraderos.services;

import com.tickets.paraderos.dto.ParaderosRequest;
import com.tickets.paraderos.models.ResponseService;
import jakarta.servlet.http.HttpServletRequest;

public interface ParaderoService {
    ResponseService getListParaderos();
    ResponseService getParadero(Integer idParadero);
    ResponseService existParaderos(ParaderosRequest request, HttpServletRequest servletRequest);
}
