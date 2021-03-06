package com.seanlindev.springframework.controllers;

import com.seanlindev.springframework.api.dto.ProductDto;
import com.seanlindev.springframework.api.request.ProductDetailsRequestModel;
import com.seanlindev.springframework.api.response.ProductResponse;
import com.seanlindev.springframework.services.ProductService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation(value = "Creat a new product service end point",
                  notes = "Provide product details in request body to create a new product")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value= "Bearer JWT Token", paramType = "hearder")
    })
    @PostMapping()
    public ProductResponse createProduct(@RequestBody ProductDetailsRequestModel productDetailsRequestModel) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        ProductDto productDto = modelMapper.map(productDetailsRequestModel, ProductDto.class);
        ProductDto createdProductDto = productService.createProduct(productDto);
        return modelMapper.map(createdProductDto, ProductResponse.class);
    }

    @ApiOperation(value = "Get a product details service end point",
                  notes = "Specify product public id in URL path to get a product info")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value= "Bearer JWT Token", paramType = "hearder")
    })
    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable String id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        ProductDto productDto = productService.getProductByProductId(id);
        return modelMapper.map(productDto, ProductResponse.class);
    }

    @ApiOperation(value = "Get the product list service end point",
                  notes = "This API supports page and limit as parameter to get product list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value= "Bearer JWT Token", paramType = "hearder")
    })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ProductResponse> getProducts(@RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "limit", defaultValue = "25") int limit) {
        return productService.getProducts(page, limit).stream()
                .map(productDto -> {
                    ModelMapper modelMapper = new ModelMapper();
                    ProductResponse productResponse = modelMapper.map(productDto, ProductResponse.class);
                    return productResponse;
                }).collect(Collectors.toList());
    }
}
