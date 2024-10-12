package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.mapper;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.dto.UserResponse;
import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserResponse map(User user);
}
