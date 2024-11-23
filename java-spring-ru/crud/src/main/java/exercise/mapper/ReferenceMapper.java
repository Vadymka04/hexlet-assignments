package exercise.mapper;

import exercise.model.Category;
import exercise.repository.CategoryRepository;
import exercise.repository.ProductRepository;
import jakarta.validation.ConstraintViolationException;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import exercise.model.BaseEntity;
import jakarta.persistence.EntityManager;

@Mapper(
        // Подключение JsonNullableMapper
        uses = {JsonNullableMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class ReferenceMapper {

    @Autowired
    private CategoryRepository categoryRepository;


    public Category mapCategory(Long categoryId) {
        if (categoryId == null) {
            return null;
        }

        var category = categoryRepository.findById(categoryId).orElseThrow(() -> new ConstraintViolationException("Category ID " + categoryId + " is invalid or does not exist", null));
        return category;
    }

}
