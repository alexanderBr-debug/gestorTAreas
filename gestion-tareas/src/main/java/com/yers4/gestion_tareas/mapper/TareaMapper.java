package com.yers4.gestion_tareas.mapper;

import com.yers4.gestion_tareas.dto.TareaRequestDto;
import com.yers4.gestion_tareas.dto.TareaResponseDto;
import com.yers4.gestion_tareas.model.Tarea;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TareaMapper {

    // Mapeamos de Request a Entidad. Ignoramos lo que asignaremos a mano (como el usuario)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "completada", constant = "false") 
    @Mapping(target = "user", ignore = true)
    Tarea toEntity(TareaRequestDto dto);

    // Mapeamos de Entidad a Response. 
    // Le decimos de dónde sacar el nameUser navegando por la relación: tarea -> user -> name
    @Mapping(target = "nameUser", source = "user.name")
    TareaResponseDto toDto(Tarea entity);

    // ¡MapStruct genera los ciclos "for" automáticamente con esta simple línea!
    List<TareaResponseDto> toDtoList(List<Tarea> tareas);
}
