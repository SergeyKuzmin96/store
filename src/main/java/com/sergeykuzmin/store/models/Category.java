package com.sergeykuzmin.store.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Validated
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "Name of category should not be empty")
    @Size(min = 4, max = 80, message = "Name of category should be between 4 and 80 characters")
    @Column(name = "category_name")
    private String categoryName;

    @JsonIgnore
    @ManyToMany(cascade =  CascadeType.REFRESH)
    @JoinTable(
            name = "products_category",
                joinColumns = @JoinColumn(name = "category_id"),
                inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> productsList;

    public void addProduct(Product product){
        if(productsList == null){
            productsList = new ArrayList<>();
        }
        productsList.add(product);
    }

    public Category() {
    }

    public Category(@NotEmpty(message = "Name of category should not be empty") @Size(min = 4, max = 80, message = "Name of category should be between 4 and 80 characters") String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Product> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Product> productsList) {
        this.productsList = productsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) &&
                Objects.equals(categoryName, category.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryName);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
