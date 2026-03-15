package org.gdiazm.finance.app.finance.category.mapper;

import org.gdiazm.finance.app.finance.category.dto.CategoryRequest;
import org.gdiazm.finance.app.finance.category.dto.CategoryResponse;
import org.gdiazm.finance.app.finance.category.dto.CategoryUpdateRequest;
import org.gdiazm.finance.app.finance.category.entity.Category;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isDefault", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Category toEntity(CategoryRequest request);

    CategoryResponse toResponse(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isDefault", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(CategoryUpdateRequest request, @MappingTarget Category category);
}
