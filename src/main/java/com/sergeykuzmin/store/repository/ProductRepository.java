package com.sergeykuzmin.store.repository;

import com.sergeykuzmin.store.models.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Override
    List<Product> findAll();

    Product findProductById(Long id);

    @Query(value = "SELECT COUNT(p) FROM Product p")
    int findCountOfProducts();


}
