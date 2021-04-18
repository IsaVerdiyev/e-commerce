package ibar.task.ecommerce.demo.controllers;

import ibar.task.ecommerce.demo.models.Product;
import ibar.task.ecommerce.demo.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

@Controller
@RequestMapping("/products")
public class ProductsController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProductService productService;

    @PostMapping
    public ResponseEntity<Object> addProduct(@Valid @RequestBody Product product){
        Product addedProduct = productService.addProduct(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Object> getProducts(@RequestParam Map<String, String> params){
        List<Product> productList = productService.getProducts(params);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

}
