package com.tickets.paraderos.controllers;

import com.tickets.paraderos.constants.Constants;
import com.tickets.paraderos.dto.ParaderosRequest;
import com.tickets.paraderos.models.ResponseService;
import com.tickets.paraderos.services.ParaderoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets/paraderos")
@Slf4j
public class ParaderoController {
    @Autowired
    private ParaderoService service;

    @GetMapping
    public ResponseEntity<ResponseService> getAllParaderos() {
        ResponseService response = service.getListParaderos();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{idParadero}")
    public ResponseEntity<ResponseService> getParadero(@PathVariable Integer idParadero) {
        ResponseService response = service.getParadero(idParadero);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseService> existParaderos(@RequestBody @Valid ParaderosRequest request, BindingResult result, HttpServletRequest servletRequest) {
        if (result.hasFieldErrors()) return validation(result);
        ResponseService response = service.existParaderos(request, servletRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private ResponseEntity<ResponseService> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });
        return new ResponseEntity<>(new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.MISSED_INFORMATION, errors), HttpStatus.OK);
    }
}
