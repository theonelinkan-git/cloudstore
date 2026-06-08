package se.jensen.linkan.productservice.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import se.jensen.linkan.productservice.dto.Product;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    private final RestTemplate restTemplate;

    public ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Product> getProducts() {

        String url = "https://yahyatesting-env.eba-sarnymwd.eu-north-1.elasticbeanstalk.com/products";

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Product[]> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, Product[].class
        );

        return Arrays.asList(response.getBody());
    }

    public Product getProductById(Long id) {

        String url = "https://yahyatesting-env.eba-sarnymwd.eu-north-1.elasticbeanstalk.com/products/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Product> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, Product.class
            );
            return response.getBody();
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Product service unavailable");
        }
    }
}