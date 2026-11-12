package com.rebanking.controller;

import com.rebanking.exception.ResourceNotFoundException;
import com.rebanking.model.Product;
import com.rebanking.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ProductController {

    private static final String CSRF_TOKEN_SESSION_ATTRIBUTE = "CSRF_TOKEN";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    Logger logger = LoggerFactory.getLogger(ProductController.class);


    @Autowired
    ProductRepository productRepository;

    private String getOrCreateCsrfToken(HttpSession session) {
        String csrfToken = (String) session.getAttribute(CSRF_TOKEN_SESSION_ATTRIBUTE);
        if (csrfToken == null) {
            byte[] tokenBytes = new byte[32];
            SECURE_RANDOM.nextBytes(tokenBytes);
            csrfToken = Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
            session.setAttribute(CSRF_TOKEN_SESSION_ATTRIBUTE, csrfToken);
        }
        return csrfToken;
    }

    private void validateCsrfToken(HttpSession session, String csrfToken) {
        String sessionToken = getOrCreateCsrfToken(session);
        if (csrfToken == null || !sessionToken.equals(csrfToken)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid CSRF token");
        }
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        logger.info("getting all products");
        return productRepository.findAll();
    }

    @PostMapping("/products")
    public Product createProduct(@RequestHeader("X-CSRF-TOKEN") String csrfToken,
                                 HttpSession session,
                                 @Valid @RequestBody Product product) {
        validateCsrfToken(session, csrfToken);
        logger.info(String.format("Saving product Title: %s ",product.getTitle()));
        return productRepository.save(product);
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable(value = "id") Long productId) {
        logger.info(String.format("Finding product id: %s ",productId));
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
    }

    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable(value = "id") Long productId,
                              @RequestHeader("X-CSRF-TOKEN") String csrfToken,
                              HttpSession session,
                              @Valid @RequestBody Product productDetails) {

        validateCsrfToken(session, csrfToken);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        product.setTitle(productDetails.getTitle());
        product.setDescription(productDetails.getDescription());

        Product updatedProduct = productRepository.save(product);
        logger.info(String.format("product Title: %s was updated", product.getTitle()));

        return updatedProduct;
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") Long productId,
                                           @RequestHeader("X-CSRF-TOKEN") String csrfToken,
                                           HttpSession session) {
        validateCsrfToken(session, csrfToken);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        productRepository.delete(product);
        logger.info(String.format("Product id:  %s deleted ", productId));
        return ResponseEntity.ok().build();
    }
}
