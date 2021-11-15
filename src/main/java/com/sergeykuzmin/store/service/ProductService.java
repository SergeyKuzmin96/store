package com.sergeykuzmin.store.service;

import com.sergeykuzmin.store.dto.ProductDto;
import com.sergeykuzmin.store.models.Category;
import com.sergeykuzmin.store.models.Product;
import com.sergeykuzmin.store.models.UserComment;
import com.sergeykuzmin.store.repository.ProductRepository;
import com.sergeykuzmin.store.repository.UserCommentRepository;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository repository;

    @Autowired
    UserCommentRepository commentRepo;


    public Product addComment(UserComment comment, Long id) {
        Product product = findById(id);

        product.addUserComment(comment);
        comment.setProduct(product);
        commentRepo.save(comment);

        return product;
    }

    public Product findById(Long id) {

        return repository.findProductById(id);
    }

    public List<Product> findAllProducts() {
        return repository.findAll();
    }


    public List<Product> findProductsByCurrency(String currency, List<String> categories) {

        List<Product> filtredlist = filterByCategories(categories);
        modificationByCurrency(filtredlist, currency);
        return filtredlist;

    }

    public boolean purchase(Long id, Integer countOfPurchase) {

        Product product = findById(id);
        if (countOfPurchase == null) {
            countOfPurchase = 1;
        }
        int count = product.getCountInStock();
        if (count > 0 && count >= countOfPurchase) {
            product.setCountInStock(count - countOfPurchase);
            repository.save(product);
            return true;
        }
        return false;

    }


    public List<Product> filterByCategories(List<String> categories) {
        List<Product> list = findAllProducts();
        if (categories != null) {
            List<Product> result = new ArrayList<>();

            String[] what = categories.stream()
                    .map(String::toLowerCase)
                    .toArray(String[]::new);

            for (Product product : list) {

                String[] where = product.getCategories().stream()
                        .map(Category::getCategoryName)
                        .map(String::toLowerCase)
                        .toArray(String[]::new);

                if (filter(where, what)) {
                    result.add(product);
                }
            }
            return result;
        }
        return list;

    }

    private boolean filter(String[] where, String[] what) {
        boolean result = false;
        for (String s : what) {
            if (ArrayUtils.contains(where, s)) {
                result = true;
            } else {
                result = false;
                break;
            }
        }
        return result;

    }


    private void modificationByCurrency(List<Product> products, String currency) {

        final double priceModification;
        final String currencyModification;

        if (currency.equalsIgnoreCase("usd") || currency.equalsIgnoreCase("eur")) {
            if (currency.equalsIgnoreCase("usd")) {
                priceModification = 71.81;
                currencyModification = "USD";
            } else {
                priceModification = 82.22;
                currencyModification = "EUR";
            }
            for (Product product : products) {
                product.setPrice(new BigDecimal(product.getPrice().doubleValue() / priceModification).setScale(2, RoundingMode.UP));
                product.setCurrency(currencyModification);
            }

        }
    }

    public int countOfProducts(){
        return repository.findCountOfProducts();
    }
}
