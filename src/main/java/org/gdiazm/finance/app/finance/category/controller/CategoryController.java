package org.gdiazm.finance.app.finance.category.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gdiazm.finance.app.finance.category.dto.CategoryRequest;
import org.gdiazm.finance.app.finance.category.dto.CategoryResponse;
import org.gdiazm.finance.app.finance.category.dto.CategoryUpdateRequest;
import org.gdiazm.finance.app.finance.category.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> getCategories() {
        return categoryService.getCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategory(@PathVariable UUID id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest);
    }
    @PatchMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable UUID id, @Valid @RequestBody CategoryUpdateRequest request) {
        return categoryService.updateCategory(id,request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
    }

}

