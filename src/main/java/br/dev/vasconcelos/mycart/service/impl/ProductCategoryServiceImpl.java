package br.dev.vasconcelos.mycart.service.impl;

import br.dev.vasconcelos.mycart.domain.entity.ProductCategory;
import br.dev.vasconcelos.mycart.domain.repository.ProductCategoryRepository;
import br.dev.vasconcelos.mycart.exception.NotFoundException;
import br.dev.vasconcelos.mycart.rest.dto.ProductCategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Service
public class ProductCategoryServiceImpl {

    @Autowired
    private ProductCategoryRepository repository;

    @Transactional
    public ProductCategory save(@Valid ProductCategoryDTO dto) {
        if (dto.getParentId() != null) {
            repository.findById(dto.getParentId())
                    .orElseThrow(() -> new NotFoundException("Parent category not found."));
        }

        return repository.save(
                ProductCategory.builder()
                        .description(dto.getDescription())
                        .parentId(dto.getParentId())
                        .active(true)
                        .build()
        );
    }

    @Transactional
    public void update(Integer id, @Valid ProductCategoryDTO dto) {
        if (dto.getParentId() != null) {
            repository.findById(dto.getParentId())
                    .orElseThrow(() -> new NotFoundException("Parent category not found."));
        }

        repository
                .findById(id)
                .map(productCategory -> {
                    repository.save(
                            ProductCategory.builder()
                                    .description(dto.getDescription())
                                    .parentId(dto.getParentId())
                                    .active(dto.isActive())
                                    .build()
                    );
                    return null;
                }).orElseThrow(()-> new NotFoundException("Parent category not found."));
    }

    @Transactional
    public void delete(Integer id) {
        repository
                .findById(id)
                .map(productCategory -> {
                    repository.delete(productCategory);
                    return null;
                }).orElseThrow(()-> new NotFoundException("Parent category not found."));
    }

    public List<ProductCategory> find(ProductCategory filter) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filter, matcher);

        return repository.findAll(example);
    }

    public ProductCategory findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(()-> new NotFoundException("Parent category not found."));
    }
}
