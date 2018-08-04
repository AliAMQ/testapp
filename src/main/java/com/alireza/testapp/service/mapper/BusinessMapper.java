package com.alireza.testapp.service.mapper;

import com.alireza.testapp.domain.*;
import com.alireza.testapp.service.dto.BusinessDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Business and its DTO BusinessDTO.
 */
@Mapper(componentModel = "spring", uses = {UserProfileMapper.class})
public interface BusinessMapper extends EntityMapper<BusinessDTO, Business> {

    @Mapping(source = "userProfile.id", target = "userProfileId")
    BusinessDTO toDto(Business business);

    @Mapping(source = "userProfileId", target = "userProfile")
    @Mapping(target = "reviews", ignore = true)
    Business toEntity(BusinessDTO businessDTO);

    default Business fromId(Long id) {
        if (id == null) {
            return null;
        }
        Business business = new Business();
        business.setId(id);
        return business;
    }
}
