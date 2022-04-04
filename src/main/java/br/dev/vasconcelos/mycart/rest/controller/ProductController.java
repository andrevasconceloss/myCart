package br.dev.vasconcelos.mycart.rest.controller;

import br.dev.vasconcelos.mycart.domain.entity.Product;
import br.dev.vasconcelos.mycart.exception.NotFoundException;
import br.dev.vasconcelos.mycart.exception.UniqueConstraintException;
import br.dev.vasconcelos.mycart.rest.dto.ProductDTO;
import br.dev.vasconcelos.mycart.service.impl.ProductServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import static org.springframework.http.HttpStatus.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value= "/api/product")
@RequiredArgsConstructor
@Api("Product routes")
public class ProductController {

    private final ProductServiceImpl productService;

    @PostMapping(produces = {"application/json"}, consumes = {"application/json"})
    @ResponseStatus(CREATED)
    @ApiOperation("Create new product")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public Product save(@RequestBody ProductDTO dto) {
        try{
            Product product = productService.save(dto);
            return product;
        } catch (UniqueConstraintException e) {
            throw new ResponseStatusException(UNAUTHORIZED);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR);
        }
    }
}
