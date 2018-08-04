package com.alireza.testapp.service;

import com.alireza.testapp.service.dto.BusinessDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Business.
 */
public interface BusinessService {

    /**
     * Save a business.
     *
     * @param businessDTO the entity to save
     * @return the persisted entity
     */
    BusinessDTO save(BusinessDTO businessDTO);

    /**
     * Get all the businesses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BusinessDTO> findAll(Pageable pageable);


    /**
     * Get the "id" business.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BusinessDTO> findOne(Long id);

    /**
     * Delete the "id" business.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
