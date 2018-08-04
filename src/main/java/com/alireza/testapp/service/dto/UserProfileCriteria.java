package com.alireza.testapp.service.dto;

import java.io.Serializable;
import com.alireza.testapp.domain.enumeration.State;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;


import io.github.jhipster.service.filter.LocalDateFilter;



/**
 * Criteria class for the UserProfile entity. This class is used in UserProfileResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /user-profiles?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserProfileCriteria implements Serializable {
    /**
     * Class for filtering State
     */
    public static class StateFilter extends Filter<State> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StateFilter state;

    private StringFilter city;

    private StringFilter address;

    private StringFilter phone;

    private LocalDateFilter since;

    private BooleanFilter owner;

    private StringFilter imagepath;

    private LongFilter userId;

    private LongFilter businessId;

    private LongFilter reviewId;

    public UserProfileCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StateFilter getState() {
        return state;
    }

    public void setState(StateFilter state) {
        this.state = state;
    }

    public StringFilter getCity() {
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public LocalDateFilter getSince() {
        return since;
    }

    public void setSince(LocalDateFilter since) {
        this.since = since;
    }

    public BooleanFilter getOwner() {
        return owner;
    }

    public void setOwner(BooleanFilter owner) {
        this.owner = owner;
    }

    public StringFilter getImagepath() {
        return imagepath;
    }

    public void setImagepath(StringFilter imagepath) {
        this.imagepath = imagepath;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getBusinessId() {
        return businessId;
    }

    public void setBusinessId(LongFilter businessId) {
        this.businessId = businessId;
    }

    public LongFilter getReviewId() {
        return reviewId;
    }

    public void setReviewId(LongFilter reviewId) {
        this.reviewId = reviewId;
    }

    @Override
    public String toString() {
        return "UserProfileCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (state != null ? "state=" + state + ", " : "") +
                (city != null ? "city=" + city + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (since != null ? "since=" + since + ", " : "") +
                (owner != null ? "owner=" + owner + ", " : "") +
                (imagepath != null ? "imagepath=" + imagepath + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (businessId != null ? "businessId=" + businessId + ", " : "") +
                (reviewId != null ? "reviewId=" + reviewId + ", " : "") +
            "}";
    }

}
