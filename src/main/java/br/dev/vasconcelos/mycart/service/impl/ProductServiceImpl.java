package br.dev.vasconcelos.mycart.service.impl;

import br.dev.vasconcelos.mycart.domain.entity.Product;
import br.dev.vasconcelos.mycart.domain.repository.ProductRepository;
import br.dev.vasconcelos.mycart.exception.NotFoundException;
import br.dev.vasconcelos.mycart.rest.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
public class ProductServiceImpl {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Product save(ProductDTO dto) throws NotFoundException {
        Product product = repository.save(dto.getEan(), dto.getDescription(), dto.getPrice(), dto.getImage());
        return product;
    }
}
