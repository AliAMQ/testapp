package com.alireza.testapp.service.mapper;

import com.alireza.testapp.domain.*;
import com.alireza.testapp.service.dto.ReviewDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Review and its DTO ReviewDTO.
 */
@Mapper(componentModel = "spring", uses = {UserProfileMapper.class, BusinessMapper.class})
public interface ReviewMapper extends EntityMapper<ReviewDTO, Review> {

    @Mapping(source = "userProfile.id", target = "userProfileId")
    @Mapping(source = "business.id", target = "businessId")
    ReviewDTO toDto(Review review);

    @Mapping(source = "userProfileId", target = "userProfile")
    @Mapping(source = "businessId", target = "business")
    Review toEntity(ReviewDTO reviewDTO);

    default Review fromId(Long id) {
        if (id == null) {
            return null;
        }
        Review review = new Review();
        review.setId(id);
        return review;
    }
}
