package org.gdiazm.finance.app.finance.category.repository;

import org.gdiazm.finance.app.finance.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    List<Category> findByUserIdAndIsActiveTrue(UUID userId);

    Optional<Category> findByIdAndUserId(UUID categoryId, UUID userId);

    boolean existsByIdAndUserIdAndIsDefaultTrue(UUID categoryId, UUID userId);

}
