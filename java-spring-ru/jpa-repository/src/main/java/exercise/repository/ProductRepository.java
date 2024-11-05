package exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import exercise.model.Product;

import org.springframework.data.domain.Sort;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//    @Query("SELECT e FROM Product e WHERE e.name LIKE %:name%")
    List<Product> findAllByPriceLessThanEqualAndPriceGreaterThanEqual(Integer minPrice, Integer maxPrice);
}
