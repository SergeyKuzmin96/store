package com.sergeykuzmin.store.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;
@Validated
@Entity
@Table(name = "user_comment")
public class UserComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @NotEmpty(message = "Name of user should not be empty")
    @Size(min = 4, max = 80, message = "Name of user should be between 4 and 80 characters")
    @Column(name = "user_name")
    private String userName;

    @NotEmpty(message = "Comment should not be empty")
    @Size(min = 1, max = 255, message = "Comment should be between 1 and 255 characters")
    @Column(name = "comment")
    private String comment;

    @DecimalMin(value = "0.0", message = "Rating should be between 0.0 and 5.0")
    @DecimalMax(value = "5.0", message = "Rating should be between 0.0 and 5.0" )
    @Column(name = "rating")
    private Double rating;

    @JsonIgnore
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.EAGER,optional=false)
//    @ManyToOne(cascade = { CascadeType.MERGE})
    @JoinColumn(name = "product_id")
    private Product product;

    public UserComment() {
    }

    public UserComment(@NotEmpty(message = "Name of user should not be empty") @Size(min = 4, max = 80, message = "Name of user should be between 4 and 80 characters")
                               String userName,
                       @NotEmpty(message = "Comment should not be empty") @Size(min = 1, max = 255, message = "Comment should be between 1 and 255 characters")
            String comment,
                       @DecimalMin(value = "0.0", message = "Rating should be between 0.0 and 5.0") @DecimalMax(value = "5.0", message = "Rating should be between 0.0 and 5.0")
            Double rating) {
        this.userName = userName;
        this.comment = comment;
        this.rating = rating;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserComment that = (UserComment) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(rating, that.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, comment, rating);
    }

    @Override
    public String toString() {
        return "UserComment{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                '}';
    }
//    @ManyToMany
//    @JoinTable(
//            name = "products_comment",
//            joinColumns = @JoinColumn(name = "comment_id"),
//            inverseJoinColumns = @JoinColumn(name = "product_id")
//    )
//    List<Product> products = new ArrayList<>();
//
//    public void addProduct(Product product){
//        products.add(product);
//    }
}

