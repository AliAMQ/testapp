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

import com.alireza.testapp.domain.Review;
import com.alireza.testapp.domain.*; // for static metamodels
import com.alireza.testapp.repository.ReviewRepository;
import com.alireza.testapp.service.dto.ReviewCriteria;

import com.alireza.testapp.service.dto.ReviewDTO;
import com.alireza.testapp.service.mapper.ReviewMapper;

/**
 * Service for executing complex queries for Review entities in the database.
 * The main input is a {@link ReviewCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReviewDTO} or a {@link Page} of {@link ReviewDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReviewQueryService extends QueryService<Review> {

    private final Logger log = LoggerFactory.getLogger(ReviewQueryService.class);

    private final ReviewRepository reviewRepository;

    private final ReviewMapper reviewMapper;

    public ReviewQueryService(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

    /**
     * Return a {@link List} of {@link ReviewDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReviewDTO> findByCriteria(ReviewCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Review> specification = createSpecification(criteria);
        return reviewMapper.toDto(reviewRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReviewDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReviewDTO> findByCriteria(ReviewCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Review> specification = createSpecification(criteria);
        return reviewRepository.findAll(specification, page)
            .map(reviewMapper::toDto);
    }

    /**
     * Function to convert ReviewCriteria to a {@link Specification}
     */
    private Specification<Review> createSpecification(ReviewCriteria criteria) {
        Specification<Review> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Review_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Review_.title));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Review_.description));
            }
            if (criteria.getLike() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLike(), Review_.like));
            }
            if (criteria.getDislike() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDislike(), Review_.dislike));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Review_.date));
            }
            if (criteria.getImagepath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImagepath(), Review_.imagepath));
            }
            if (criteria.getVideopath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVideopath(), Review_.videopath));
            }
            if (criteria.getUserProfileId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserProfileId(), Review_.userProfile, UserProfile_.id));
            }
            if (criteria.getBusinessId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getBusinessId(), Review_.business, Business_.id));
            }
        }
        return specification;
    }

}
