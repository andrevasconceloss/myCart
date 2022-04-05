package br.dev.vasconcelos.mycart.service.impl;

import br.dev.vasconcelos.mycart.domain.entity.Product;
import br.dev.vasconcelos.mycart.domain.repository.ProductRepository;
import br.dev.vasconcelos.mycart.exception.NotFoundException;
import br.dev.vasconcelos.mycart.rest.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl {

    @Autowired
    private ProductRepository repository;

    @Transactional
    public Product save(ProductDTO dto) {
        return repository.save(
                Product.builder()
                        .ean(dto.getEan())
                        .name(dto.getName())
                        .price(dto.getPrice())
                        .image(dto.getImage())
                        .categoryId(dto.getCategoryId())
                        .active(dto.isActive())
                        .build()
        );
    }

    @Transactional
    public void update(Integer id, ProductDTO dto) {
        repository
                .findById(id)
                .map(product -> {
                    repository.save(
                            Product.builder()
                                    .ean(dto.getEan())
                                    .name(dto.getName())
                                    .price(dto.getPrice())
                                    .image(dto.getImage())
                                    .categoryId(dto.getCategoryId())
                                    .active(dto.isActive())
                                    .build()
                    );
                    return null;
                }).orElseThrow(()-> new NotFoundException("Product not found."));
    }

    @Transactional
    public void delete(Integer id) {
        repository
                .findById(id)
                .map(product -> {
                    repository.delete(product);
                    return null;
                }).orElseThrow(()-> new NotFoundException("Product not found."));
    }

    public List<Product> find(Product filter) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filter, matcher);

        return repository.findAll(example);
    }

    public Product findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(()-> new NotFoundException("Product not found."));
    }
}
