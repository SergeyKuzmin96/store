package com.sergeykuzmin.store.controller;

import com.sergeykuzmin.store.models.Product;
import com.sergeykuzmin.store.models.UserComment;
import com.sergeykuzmin.store.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService service;


    @PostMapping("/products/{id}")
    @Operation(
            summary = "Добавление комментария",
            description = "Данный запрос добавит к выбранному продукту комментарий"
    )
    public Product addCommentToProduct(@PathVariable @Parameter(description = "Идентификатор товара") Long id,
                                      @RequestBody @Parameter(description = "Коментарий пользователя") @Valid UserComment userComment) {

          return   service.addComment(userComment, id);

    }


    @GetMapping("/productsByCategory")
    @Operation(
            summary = "Получение списка продуктов ",
            description = "Данный запрос вернет список продутктов в соответствии с выбранными категориями"
    )
    public List<Product> findProducts(@RequestParam(value = "category",required = false) @Parameter(description = "Категории") List<String> category) {
        return service.filterByCategories(category);

    }


    @GetMapping("/productsByCategory/{currency}/")
    @Operation(
            summary = "Получение списка продуктов(CUR)",
            description = "Данный запрос вернет список продутктов в соответствии с выбранными категориями, а так же предоставит цены на товары в соответствии с указанной валютой"
    )
    public List<Product> findByCurrency(@PathVariable @Parameter(description = "Валюта") String currency,
                                        @RequestParam(value = "category",required = false) @Parameter(description = "Категории") List<String> category) {
        return service.findProductsByCurrency(currency, category);

    }


    @Operation(
            summary = "Покупка продукта",
            description = "Данный запрос произведет изъятия единицы товара из склада в указанном количествею. При отсутсвии инициализации количества покупаемого товара будет куплен один товар)"
    )
    @PutMapping("/products/{id}/")
    public String purchaseProduct(@PathVariable @Parameter(description = "Идентификатор товара") Long id,
                                   @RequestParam(value = "count",required = false) @Parameter(description = "Количество товара к покупке") Integer count){

        if(id > 0 && id <= service.countOfProducts()){

            boolean result = service.purchase(id, count);
            if(result){ return "Покупка прошла успешно";

            }else {return "На складе не достаточно товара для покупки";}
        }
        return "Товар с таким идентификатором отсутствует";


    }
}
