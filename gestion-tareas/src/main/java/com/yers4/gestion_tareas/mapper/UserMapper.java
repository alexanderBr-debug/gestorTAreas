package com.yers4.gestion_tareas.mapper;

import com.yers4.gestion_tareas.dto.UserRequestDto;
import com.yers4.gestion_tareas.dto.UserResponseDto;
import com.yers4.gestion_tareas.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



// Le decimos a MapStruct que esto será un Bean de Spring para poder inyectarlo
@Mapper(componentModel = "spring")
public interface UserMapper {

    // Ignoramos id, rol y password porque esos los manejas/codificas en el servicio
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "authorities",ignore = true)
    User toEntity(UserRequestDto dto);

    // Para el response, usualmente solo devuelves el token o datos básicos
    @Mapping (target = "acessToken",ignore = true)
    @Mapping (target = "refreshToken",ignore = true)
    UserResponseDto toDto(User entity);
}