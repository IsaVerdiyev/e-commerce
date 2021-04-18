package ibar.task.ecommerce.demo.controllers;

import ibar.task.ecommerce.demo.models.Product;
import ibar.task.ecommerce.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    ProductService productService;

    @PostMapping
    public ResponseEntity<Object> addProduct(@Valid @RequestBody Product product){
        Product addedProduct = productService.addProduct(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

}
