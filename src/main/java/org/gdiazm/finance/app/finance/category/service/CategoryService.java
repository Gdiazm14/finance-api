package org.gdiazm.finance.app.finance.category.service;

import org.gdiazm.finance.app.finance.category.dto.CategoryRequest;
import org.gdiazm.finance.app.finance.category.dto.CategoryResponse;
import org.gdiazm.finance.app.finance.category.dto.CategoryUpdateRequest;
import org.gdiazm.finance.app.finance.category.entity.Category;
import org.gdiazm.finance.app.finance.user.entity.User;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryResponse> getCategories();

    CategoryResponse getCategoryById(UUID categoryId);

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse updateCategory(UUID categoryId, CategoryUpdateRequest request);

    void deleteCategory(UUID categoryId);

    void setDefaultCategory(User user);
}
