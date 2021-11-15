package com.sergeykuzmin.store.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Validated
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "Name of product should not be empty")
    @Size(min = 4, max = 80, message = "Name of product should be between 4 and 80 characters")
    @Column(name = "product_name")
    private String productName;

    @NotEmpty(message = "Description should not be empty")
    @Size(min = 4, max = 255, message = "Description should be between 4 and 255 characters")
    @Column(name = "description")
    private String description;


    @DecimalMin(value = "0.0", inclusive = false, message = "Price be greater than 0")
    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "currency")
    private String currency = "RUB";

    @Min(value = 0, message = "Count be greater than 0")
    @Column(name = "count_in_stock")
    private Integer countInStock;

    @Column(name = "rating")
    private Double ratingAvg;

    @JsonBackReference
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY, mappedBy = "productsList")
    private List<Category> categories;

    @JsonBackReference
//    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "product")
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "product")
    private List<UserComment> comments;

    public void addCategory(Category category) {
        if (categories == null) {

            categories = new ArrayList<>();
        }
        categories.add(category);
    }

    public void addUserComment(UserComment comment) {
        if (comments == null) {

            comments = new ArrayList<>();
        }
        comments.add(comment);
        comment.setProduct(this);
        setRatingAvg();
    }


    public Product() {
    }

    public Product(@NotEmpty(message = "Name of product should not be empty") @Size(min = 4, max = 80, message = "Name of product should be between 4 and 80 characters") String productName, @NotEmpty(message = "Description should not be empty") @Size(min = 4, max = 255, message = "Description should be between 4 and 255 characters") String description, @DecimalMin(value = "0.0", inclusive = false, message = "Price be greater than 0") BigDecimal price, @Min(value = 0, message = "Count be greater than 0") Integer countInStock) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.countInStock = countInStock;
        this.ratingAvg = 0.00;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getCountInStock() {
        return countInStock;
    }

    public void setCountInStock(Integer countInStock) {
        this.countInStock = countInStock;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<UserComment> getComments() {
        return comments;
    }

    public void setComments(List<UserComment> comments) {
        this.comments = comments;
    }

    public Double getRatingAvg() {
        return ratingAvg;
    }

    public void setRatingAvg() {
        double sum = 0;
        int i = 0;
        for (UserComment comment : comments) {
            sum += comment.getRating();
            ++i;
        }
        this.ratingAvg = BigDecimal.valueOf(sum / i).setScale(2, RoundingMode.DOWN).doubleValue();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(productName, product.productName) &&
                Objects.equals(description, product.description) &&
                Objects.equals(price, product.price) &&
                Objects.equals(countInStock, product.countInStock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, description, price, countInStock);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", countInStock=" + countInStock +
                '}';
    }
}
