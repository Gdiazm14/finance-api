package org.gdiazm.finance.app.finance.user.mapper;

import org.gdiazm.finance.app.finance.user.dto.UserResponse;
import org.gdiazm.finance.app.finance.user.dto.UserUpdatedRequest;
import org.gdiazm.finance.app.finance.user.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)

    void updateEntityFromRequest(UserUpdatedRequest request, @MappingTarget User user);


}
