package exercise.mapper;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        // Подключение JsonNullableMapper
        uses = {JsonNullableMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ProductMapper {

    @Autowired
    private ReferenceMapper referenceMapper;

    public void update(ProductUpdateDTO dto, Product model) {
        if (dto.getTitle()!=null) model.setTitle(dto.getTitle().get());
        if (dto.getPrice()!=null) model.setPrice(dto.getPrice().get());
        if (dto.getCategoryId()!=null) {
            var category = referenceMapper.mapCategory(dto.getCategoryId().get());
            model.setCategory(category);
        }
    }

    public ProductDTO map(Product product) {
        var productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setTitle(product.getTitle());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setCategoryName(product.getCategory().getName());
        productDTO.setCreatedAt(product.getCreatedAt());
        productDTO.setUpdatedAt(product.getUpdatedAt());
        return productDTO;
    }

    public Product map(ProductCreateDTO productCreateDTO) {
        var category = referenceMapper.mapCategory(productCreateDTO.getCategoryId());
        var product = new Product();
        product.setTitle(productCreateDTO.getTitle());
        product.setPrice(productCreateDTO.getPrice());
        product.setCategory(category);
        return product;
    }


}