package com.tickets.paraderos.services.impl;

import com.tickets.paraderos.constants.Constants;
import com.tickets.paraderos.dto.ParaderoResponse;
import com.tickets.paraderos.dto.ParaderosRequest;
import com.tickets.paraderos.entities.Paradero;
import com.tickets.paraderos.models.ResponseService;
import com.tickets.paraderos.repositories.ParaderoRepository;
import com.tickets.paraderos.services.ParaderoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ParaderoServiceImpl implements ParaderoService {
    @Autowired
    private ParaderoRepository repository;

    @Override
    @Transactional(readOnly = true)
    public ResponseService getListParaderos() {
        log.info("se obtienen todos los paraderos");
        List<Paradero> paraderos = repository.findByEstatusOrderByIdAsc(1);
        List<ParaderoResponse> paraderosResponse = new ArrayList<>();
        paraderos.forEach(paradero -> {
            paraderosResponse.add(new ParaderoResponse(paradero));
        });
        log.info("se regresa la lista con {} elementos", paraderos.size());
        return new ResponseService(Constants.RESPONSE_TYPE_SUCCESS, Constants.PARADEROS_OBTAINED, paraderosResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseService getParadero(Integer idParadero) {
        log.info("se revisa que el idParadero exista");
        Optional<Paradero> paradero = repository.findById(idParadero);
        if (paradero.isEmpty()) {
            log.info("no existe");
            return new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.PARADERO_UNKNOWN, null);
        }
        log.info("se revisa que esté habilitado");
        if (paradero.get().getEstatus() == 0) {
            log.info("se encuentra inhabilitado");
            return new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.PARADERO_UNKNOWN, null);
        }
        ParaderoResponse paraderoResponse = new ParaderoResponse(paradero.get());
        log.info("se regresa el paradero");
        return new ResponseService(Constants.RESPONSE_TYPE_SUCCESS, Constants.PARADERO_OBTAINED, paraderoResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseService existParaderos(ParaderosRequest request, HttpServletRequest servletRequest) {
        log.info("petición atendida desde el puerto {}", servletRequest.getServerPort());
        log.info("se revisa que el paradero de origen exista");
        Optional<Paradero> origenOptional = repository.findByIdAndEstatus(request.getOrigenId(), 1);
        if (origenOptional.isEmpty()) {
            log.info("no existe");
            return new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.PARADERO_UNKNOWN, null);
        }
        log.info("se revisa que el paradero de destino exista");
        Optional<Paradero> destinoOptional = repository.findByIdAndEstatus(request.getDestinoId(), 1);
        if (destinoOptional.isEmpty()) {
            log.info("no existe");
            return new ResponseService(Constants.RESPONSE_TYPE_ERROR, Constants.PARADERO_UNKNOWN, null);
        }
        return new ResponseService(Constants.RESPONSE_TYPE_SUCCESS, Constants.PARADEROS_OBTAINED, origenOptional.get().getNombre() + ";" + destinoOptional.get().getNombre());
    }
}
