package org.gdiazm.finance.app.finance.category.service;

import lombok.RequiredArgsConstructor;
import org.gdiazm.finance.app.finance.category.dto.CategoryRequest;
import org.gdiazm.finance.app.finance.category.dto.CategoryResponse;
import org.gdiazm.finance.app.finance.category.dto.CategoryUpdateRequest;
import org.gdiazm.finance.app.finance.category.entity.Category;
import org.gdiazm.finance.app.finance.category.mapper.CategoryMapper;
import org.gdiazm.finance.app.finance.category.repository.CategoryRepository;
import org.gdiazm.finance.app.finance.common.exception.BusinessException;
import org.gdiazm.finance.app.finance.common.exception.ResourceNotFoundException;
import org.gdiazm.finance.app.finance.security.SecurityUtils;
import org.gdiazm.finance.app.finance.user.entity.User;
import org.gdiazm.finance.app.finance.user.repository.UserRepository;
import org.mapstruct.control.MappingControl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CategoryMapper categoryMapper;

    public static final List<String[]> DEFAULT_CATEGORIES = List.of(
            new String[]{"Food", "#FF6B6B"},
            new String[]{"Transport", "#4ECDC4"},
            new String[]{"Rent", "#45B7D1"},
            new String[]{"Health", "#96CEB4"},
            new String[]{"Entertainment", "#FFEAA7"},
            new String[]{"Education", "#DDA0DD"},
            new String[]{"Clothes", "#F0E68C"},
            new String[]{"Future", "#98FB98"}
    );

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategories() {
        return categoryRepository.findByUserIdAndIsActiveTrue(SecurityUtils.getCurrentUserId())
                .stream()
                .map(categoryMapper::toResponse)
                .toList();

    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(UUID categoryId) {
        return categoryMapper.toResponse(findCategoryForCurrentUser(categoryId));
    }

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        User user = userRepository.getReferenceById(SecurityUtils.getCurrentUserId());

        Category category = categoryMapper.toEntity(request);
        category.setUser(user);
        category.setIsDefault(false);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(UUID categoryId, CategoryUpdateRequest request) {
        Category category = findCategoryForCurrentUser(categoryId);
        categoryMapper.updateEntityFromRequest(request, category);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void deleteCategory(UUID categoryId) {
        Category category = findCategoryForCurrentUser(categoryId);
        if(Boolean.TRUE.equals(category.getIsDefault())) {
            throw new BusinessException("Default category cannot be deleted");
        }

        // TODO: validar que no tenga transacciones cuando se implemente ese módulo
        // if (transactionRepository.existsByCategoryId(categoryId)) {
        //     throw new BusinessException("Category has transactions and cannot be deleted");
        // }
        category.setIsActive(false);
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void setDefaultCategory(User user) {
    List<Category> defaults =   DEFAULT_CATEGORIES.stream()
            .map(data ->{
                Category category = new Category();
                category.setName(data[0]);
                category.setColor(data[1]);
                category.setBudgetAmount(BigDecimal.ZERO);
                category.setIsDefault(true);
                category.setIsActive(true);
                category.setUser(user);
                return category;
            })
            .toList();

    categoryRepository.saveAll(defaults);
    }

    private Category findCategoryForCurrentUser(UUID categoryId){
        return categoryRepository.findByIdAndUserId(categoryId, SecurityUtils.getCurrentUserId())
                .orElseThrow(()-> new ResourceNotFoundException("Category not found"));
    }
}
