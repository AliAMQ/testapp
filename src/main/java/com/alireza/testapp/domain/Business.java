package com.alireza.testapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.alireza.testapp.domain.enumeration.State;

/**
 * A Business.
 */
@Entity
@Table(name = "business")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Business implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Min(value = 1)
    @Max(value = 5)
    @Column(name = "rate")
    private Integer rate;

    @Column(name = "since")
    private LocalDate since;

    @Column(name = "jhi_link")
    private String link;

    @Column(name = "reservation")
    private Boolean reservation;

    @Column(name = "delivery")
    private Boolean delivery;

    @Column(name = "wifi")
    private Boolean wifi;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Lob
    @Column(name = "video")
    private byte[] video;

    @Column(name = "video_content_type")
    private String videoContentType;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "imagepath")
    private String imagepath;

    @Column(name = "videopath")
    private String videopath;

    @ManyToOne
    @JsonIgnoreProperties("businesses")
    private UserProfile userProfile;

    @OneToMany(mappedBy = "business")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Review> reviews = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Business title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Business description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public State getState() {
        return state;
    }

    public Business state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public Business address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public Business phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRate() {
        return rate;
    }

    public Business rate(Integer rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public LocalDate getSince() {
        return since;
    }

    public Business since(LocalDate since) {
        this.since = since;
        return this;
    }

    public void setSince(LocalDate since) {
        this.since = since;
    }

    public String getLink() {
        return link;
    }

    public Business link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean isReservation() {
        return reservation;
    }

    public Business reservation(Boolean reservation) {
        this.reservation = reservation;
        return this;
    }

    public void setReservation(Boolean reservation) {
        this.reservation = reservation;
    }

    public Boolean isDelivery() {
        return delivery;
    }

    public Business delivery(Boolean delivery) {
        this.delivery = delivery;
        return this;
    }

    public void setDelivery(Boolean delivery) {
        this.delivery = delivery;
    }

    public Boolean isWifi() {
        return wifi;
    }

    public Business wifi(Boolean wifi) {
        this.wifi = wifi;
        return this;
    }

    public void setWifi(Boolean wifi) {
        this.wifi = wifi;
    }

    public byte[] getImage() {
        return image;
    }

    public Business image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Business imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public byte[] getVideo() {
        return video;
    }

    public Business video(byte[] video) {
        this.video = video;
        return this;
    }

    public void setVideo(byte[] video) {
        this.video = video;
    }

    public String getVideoContentType() {
        return videoContentType;
    }

    public Business videoContentType(String videoContentType) {
        this.videoContentType = videoContentType;
        return this;
    }

    public void setVideoContentType(String videoContentType) {
        this.videoContentType = videoContentType;
    }

    public Boolean isPaid() {
        return paid;
    }

    public Business paid(Boolean paid) {
        this.paid = paid;
        return this;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public String getImagepath() {
        return imagepath;
    }

    public Business imagepath(String imagepath) {
        this.imagepath = imagepath;
        return this;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getVideopath() {
        return videopath;
    }

    public Business videopath(String videopath) {
        this.videopath = videopath;
        return this;
    }

    public void setVideopath(String videopath) {
        this.videopath = videopath;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public Business userProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
        return this;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public Business reviews(Set<Review> reviews) {
        this.reviews = reviews;
        return this;
    }

    public Business addReview(Review review) {
        this.reviews.add(review);
        review.setBusiness(this);
        return this;
    }

    public Business removeReview(Review review) {
        this.reviews.remove(review);
        review.setBusiness(null);
        return this;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Business business = (Business) o;
        if (business.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), business.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Business{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", state='" + getState() + "'" +
            ", address='" + getAddress() + "'" +
            ", phone='" + getPhone() + "'" +
            ", rate=" + getRate() +
            ", since='" + getSince() + "'" +
            ", link='" + getLink() + "'" +
            ", reservation='" + isReservation() + "'" +
            ", delivery='" + isDelivery() + "'" +
            ", wifi='" + isWifi() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", video='" + getVideo() + "'" +
            ", videoContentType='" + getVideoContentType() + "'" +
            ", paid='" + isPaid() + "'" +
            ", imagepath='" + getImagepath() + "'" +
            ", videopath='" + getVideopath() + "'" +
            "}";
    }
}
