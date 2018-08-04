package com.alireza.testapp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.alireza.testapp.domain.Business;
import com.alireza.testapp.domain.*; // for static metamodels
import com.alireza.testapp.repository.BusinessRepository;
import com.alireza.testapp.service.dto.BusinessCriteria;

import com.alireza.testapp.service.dto.BusinessDTO;
import com.alireza.testapp.service.mapper.BusinessMapper;

/**
 * Service for executing complex queries for Business entities in the database.
 * The main input is a {@link BusinessCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BusinessDTO} or a {@link Page} of {@link BusinessDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BusinessQueryService extends QueryService<Business> {

    private final Logger log = LoggerFactory.getLogger(BusinessQueryService.class);

    private final BusinessRepository businessRepository;

    private final BusinessMapper businessMapper;

    public BusinessQueryService(BusinessRepository businessRepository, BusinessMapper businessMapper) {
        this.businessRepository = businessRepository;
        this.businessMapper = businessMapper;
    }

    /**
     * Return a {@link List} of {@link BusinessDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BusinessDTO> findByCriteria(BusinessCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Business> specification = createSpecification(criteria);
        return businessMapper.toDto(businessRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BusinessDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BusinessDTO> findByCriteria(BusinessCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Business> specification = createSpecification(criteria);
        return businessRepository.findAll(specification, page)
            .map(businessMapper::toDto);
    }

    /**
     * Function to convert BusinessCriteria to a {@link Specification}
     */
    private Specification<Business> createSpecification(BusinessCriteria criteria) {
        Specification<Business> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Business_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Business_.title));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Business_.description));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildSpecification(criteria.getState(), Business_.state));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Business_.address));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Business_.phone));
            }
            if (criteria.getRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRate(), Business_.rate));
            }
            if (criteria.getSince() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSince(), Business_.since));
            }
            if (criteria.getLink() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLink(), Business_.link));
            }
            if (criteria.getReservation() != null) {
                specification = specification.and(buildSpecification(criteria.getReservation(), Business_.reservation));
            }
            if (criteria.getDelivery() != null) {
                specification = specification.and(buildSpecification(criteria.getDelivery(), Business_.delivery));
            }
            if (criteria.getWifi() != null) {
                specification = specification.and(buildSpecification(criteria.getWifi(), Business_.wifi));
            }
            if (criteria.getPaid() != null) {
                specification = specification.and(buildSpecification(criteria.getPaid(), Business_.paid));
            }
            if (criteria.getImagepath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImagepath(), Business_.imagepath));
            }
            if (criteria.getVideopath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVideopath(), Business_.videopath));
            }
            if (criteria.getUserProfileId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserProfileId(), Business_.userProfile, UserProfile_.id));
            }
            if (criteria.getReviewId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getReviewId(), Business_.reviews, Review_.id));
            }
        }
        return specification;
    }

}
