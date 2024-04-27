package com.gestion.eventos.services;

import com.gestion.eventos.entities.Evento;
import com.gestion.eventos.repository.EventoRepository;
import com.gestion.eventos.services.abstract_services.IEventoServices;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


//Implementamos la interfaz que creamos para EventoServices, hasta acá sigue un flujo común, por ende, tomamos a todo_ lo relacionado por service como un flujo lógico
@Service
@AllArgsConstructor
public class EventoServices implements IEventoServices {
    //Una vez aplicamos los términos del "contrato" (Usamos los métodos de la interfaz)
       //-- Procedemos a hacer una inyección de dependencia de la clase interfaz, EventoRepository

    @Autowired
    private final EventoRepository objEventoRepository;

    @Override
    public Evento guardar(Evento objEvento) {
        return this.objEventoRepository.save(objEvento);
    }
    @Override
    public List<Evento> listarEventos() {
        return this.objEventoRepository.findAll();
    }
    //El findById Es ùtil para tema de edit y delete
    @Override
    public Evento findById(String id) {
        return this.objEventoRepository.findById(id).orElseThrow();
    }
    @Override
    public Evento actualizar(Evento objEvento) {return this.objEventoRepository.save(objEvento);}
    @Override
    public boolean eliminar(String id) {
        Evento eventoExistente = objEventoRepository.findById(id).orElse(null);
        if (eventoExistente != null){
            objEventoRepository.delete(eventoExistente);
            return true;
        }
        return  false;
    }

    public Page<Evento> paginar(int page, int size){
        /* Validar que la página no sea menor a 0 */
        if (page < 0) {
            page = 0;
        }

        Pageable objPage = PageRequest.of(page, size);
        return this.objEventoRepository.findAll(objPage);
    };


}
