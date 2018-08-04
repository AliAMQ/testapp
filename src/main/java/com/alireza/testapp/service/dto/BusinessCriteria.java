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
 * Criteria class for the Business entity. This class is used in BusinessResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /businesses?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BusinessCriteria implements Serializable {
    /**
     * Class for filtering State
     */
    public static class StateFilter extends Filter<State> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter title;

    private StringFilter description;

    private StateFilter state;

    private StringFilter address;

    private StringFilter phone;

    private IntegerFilter rate;

    private LocalDateFilter since;

    private StringFilter link;

    private BooleanFilter reservation;

    private BooleanFilter delivery;

    private BooleanFilter wifi;

    private BooleanFilter paid;

    private StringFilter imagepath;

    private StringFilter videopath;

    private LongFilter userProfileId;

    private LongFilter reviewId;

    public BusinessCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StateFilter getState() {
        return state;
    }

    public void setState(StateFilter state) {
        this.state = state;
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

    public IntegerFilter getRate() {
        return rate;
    }

    public void setRate(IntegerFilter rate) {
        this.rate = rate;
    }

    public LocalDateFilter getSince() {
        return since;
    }

    public void setSince(LocalDateFilter since) {
        this.since = since;
    }

    public StringFilter getLink() {
        return link;
    }

    public void setLink(StringFilter link) {
        this.link = link;
    }

    public BooleanFilter getReservation() {
        return reservation;
    }

    public void setReservation(BooleanFilter reservation) {
        this.reservation = reservation;
    }

    public BooleanFilter getDelivery() {
        return delivery;
    }

    public void setDelivery(BooleanFilter delivery) {
        this.delivery = delivery;
    }

    public BooleanFilter getWifi() {
        return wifi;
    }

    public void setWifi(BooleanFilter wifi) {
        this.wifi = wifi;
    }

    public BooleanFilter getPaid() {
        return paid;
    }

    public void setPaid(BooleanFilter paid) {
        this.paid = paid;
    }

    public StringFilter getImagepath() {
        return imagepath;
    }

    public void setImagepath(StringFilter imagepath) {
        this.imagepath = imagepath;
    }

    public StringFilter getVideopath() {
        return videopath;
    }

    public void setVideopath(StringFilter videopath) {
        this.videopath = videopath;
    }

    public LongFilter getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(LongFilter userProfileId) {
        this.userProfileId = userProfileId;
    }

    public LongFilter getReviewId() {
        return reviewId;
    }

    public void setReviewId(LongFilter reviewId) {
        this.reviewId = reviewId;
    }

    @Override
    public String toString() {
        return "BusinessCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (state != null ? "state=" + state + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (rate != null ? "rate=" + rate + ", " : "") +
                (since != null ? "since=" + since + ", " : "") +
                (link != null ? "link=" + link + ", " : "") +
                (reservation != null ? "reservation=" + reservation + ", " : "") +
                (delivery != null ? "delivery=" + delivery + ", " : "") +
                (wifi != null ? "wifi=" + wifi + ", " : "") +
                (paid != null ? "paid=" + paid + ", " : "") +
                (imagepath != null ? "imagepath=" + imagepath + ", " : "") +
                (videopath != null ? "videopath=" + videopath + ", " : "") +
                (userProfileId != null ? "userProfileId=" + userProfileId + ", " : "") +
                (reviewId != null ? "reviewId=" + reviewId + ", " : "") +
            "}";
    }

}
