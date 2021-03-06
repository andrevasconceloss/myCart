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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value= "/api/product")
@RequiredArgsConstructor
@Api("Product routes")
public class ProductController {

    private final ProductServiceImpl service;

    @PostMapping(produces = {"application/json"}, consumes = {"application/json"})
    @ResponseStatus(CREATED)
    @ApiOperation("Create new product")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public Product save(@RequestBody @Valid ProductDTO dto) {
        try{
            return service.save(dto);
        } catch (UniqueConstraintException e) {
            throw new ResponseStatusException(UNAUTHORIZED);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = {"application/json"})
    @ResponseStatus(OK)
    @ApiOperation("Update product")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public void update(@PathVariable Integer id, @RequestBody @Valid ProductDTO dto) {
        try{
            service.update(id, dto);
        } catch (UniqueConstraintException e) {
            throw new ResponseStatusException(UNAUTHORIZED);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Delete product")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public void delete(@PathVariable Integer id){
        try{
            service.delete(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = {"application/json"})
    @ResponseStatus(OK)
    @ApiOperation("Find product with filter")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<Product> find(Product filter) {
        try {
            return service.find(filter);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    @ResponseStatus(OK)
    @ApiOperation("Find product by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public Product find(@PathVariable("id") Integer id){
        try {
            return service.findById(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR);
        }
    }
}
