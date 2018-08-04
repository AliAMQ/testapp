package com.alireza.testapp.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;


import io.github.jhipster.service.filter.LocalDateFilter;



/**
 * Criteria class for the Review entity. This class is used in ReviewResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /reviews?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ReviewCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter title;

    private StringFilter description;

    private IntegerFilter like;

    private IntegerFilter dislike;

    private LocalDateFilter date;

    private StringFilter imagepath;

    private StringFilter videopath;

    private LongFilter userProfileId;

    private LongFilter businessId;

    public ReviewCriteria() {
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

    public IntegerFilter getLike() {
        return like;
    }

    public void setLike(IntegerFilter like) {
        this.like = like;
    }

    public IntegerFilter getDislike() {
        return dislike;
    }

    public void setDislike(IntegerFilter dislike) {
        this.dislike = dislike;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
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

    public LongFilter getBusinessId() {
        return businessId;
    }

    public void setBusinessId(LongFilter businessId) {
        this.businessId = businessId;
    }

    @Override
    public String toString() {
        return "ReviewCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (like != null ? "like=" + like + ", " : "") +
                (dislike != null ? "dislike=" + dislike + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (imagepath != null ? "imagepath=" + imagepath + ", " : "") +
                (videopath != null ? "videopath=" + videopath + ", " : "") +
                (userProfileId != null ? "userProfileId=" + userProfileId + ", " : "") +
                (businessId != null ? "businessId=" + businessId + ", " : "") +
            "}";
    }

}
