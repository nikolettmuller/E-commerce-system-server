package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.mapper;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.RegisterRequest;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RegisterMapper {


    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()))")
    User map(RegisterRequest registerRequest);

}
