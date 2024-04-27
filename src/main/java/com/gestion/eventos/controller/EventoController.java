package com.gestion.eventos.controller;

import com.gestion.eventos.entities.Evento;
import com.gestion.eventos.services.abstract_services.IEventoServices;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/evento")
@AllArgsConstructor
public class EventoController {
    //Inyección de dependencias
    //Haremos inyeccion de la interfaz IEventoServices
    @Autowired
    private final IEventoServices objIEventoServices;

    @PostMapping
    /*    @RequestBody permite que un método de controlador tome un objeto automáticamente deserializado del cuerpo de una solicitud HTTP, utilizando convertidores de mensajes HTTP.*/
    /*@RequestBody en Spring MVC es una herramienta poderosa para recibir datos estructurados en el cuerpo de una solicitud HTTP y mapearlos directamente a objetos Java correspondientes, facilitando así la manipulación de datos en tus controladores.*/
    public ResponseEntity<String> crear(@RequestBody Evento objEvento){
        // Guardar el objeto que nos llega en la solicitud POST en la base de datos
        int capacidad = objEvento.getCapacidad();
        LocalDate fecha = objEvento.getFecha();

        if (capacidad < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (fecha.isBefore(LocalDate.now())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        objIEventoServices.guardar(objEvento);
        // Retornar una respuesta indicando que el objeto ha sido creado satisfactoriamente
        return ResponseEntity.status(HttpStatus.CREATED).body("Objeto creado satisfactoriamente");
    }


    @GetMapping
    public ResponseEntity<List<Evento>>listarEventos(){
        List<Evento> listaDeEventos = this.objIEventoServices.listarEventos();
        return ResponseEntity.ok(listaDeEventos);
    }


    //Se puede dejar el getMapping sin especificar pero en este caso que haremos uso de un extracto del http
    @GetMapping(path = "/{id}")
    public ResponseEntity<Evento> BuscarPorId(@PathVariable String id){
        Evento objEvento = this.objIEventoServices.findById(id);
        return ResponseEntity.ok(objEvento);
    }

    @GetMapping(path = "/{page}/{size}")
    public ResponseEntity<Page<Evento>>saddsa(@PathVariable int page, @PathVariable int size){
        Page pages = this.objIEventoServices.paginar(page - 1, size);
        return ResponseEntity.ok(pages);
    }


    @PutMapping(path = "/{id}")

    //Proceso editar, el me pasará la parte del json que será modifica
    //La obtención del id se debe hacer por medio del path para temas de seguridad


    public ResponseEntity<Evento> editarEvento(@RequestBody Evento objEvento, @PathVariable String id){
        /* Es una buena práctica establecer el ID del objeto recibido en el controlador antes de pasarlo al servicio para mantener la coherencia y facilitar la comprensión del flujo de datos en la aplicación. */
        objEvento.setId(id);
        Evento evento =  objIEventoServices.actualizar(objEvento);
        return ResponseEntity.ok(evento);
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> eliminarEvento(@PathVariable String id){
        Boolean validacion = this.objIEventoServices.eliminar(id);
        if (validacion == true){
            return ResponseEntity.ok("Eliminado correctamente");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El evento con el ID " + id + " no ha sido encontrado");
        }
    }



}
