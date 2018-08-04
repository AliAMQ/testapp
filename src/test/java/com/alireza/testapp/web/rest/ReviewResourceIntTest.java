package com.alireza.testapp.web.rest;

import com.alireza.testapp.TestappApp;

import com.alireza.testapp.domain.Review;
import com.alireza.testapp.domain.UserProfile;
import com.alireza.testapp.domain.Business;
import com.alireza.testapp.repository.ReviewRepository;
import com.alireza.testapp.service.ReviewService;
import com.alireza.testapp.service.dto.ReviewDTO;
import com.alireza.testapp.service.mapper.ReviewMapper;
import com.alireza.testapp.web.rest.errors.ExceptionTranslator;
import com.alireza.testapp.service.dto.ReviewCriteria;
import com.alireza.testapp.service.ReviewQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.alireza.testapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ReviewResource REST controller.
 *
 * @see ReviewResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestappApp.class)
public class ReviewResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_LIKE = 1;
    private static final Integer UPDATED_LIKE = 2;

    private static final Integer DEFAULT_DISLIKE = 1;
    private static final Integer UPDATED_DISLIKE = 2;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_VIDEO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_VIDEO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_VIDEO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_VIDEO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_IMAGEPATH = "AAAAAAAAAA";
    private static final String UPDATED_IMAGEPATH = "BBBBBBBBBB";

    private static final String DEFAULT_VIDEOPATH = "AAAAAAAAAA";
    private static final String UPDATED_VIDEOPATH = "BBBBBBBBBB";

    @Autowired
    private ReviewRepository reviewRepository;


    @Autowired
    private ReviewMapper reviewMapper;
    

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewQueryService reviewQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReviewMockMvc;

    private Review review;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReviewResource reviewResource = new ReviewResource(reviewService, reviewQueryService);
        this.restReviewMockMvc = MockMvcBuilders.standaloneSetup(reviewResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Review createEntity(EntityManager em) {
        Review review = new Review()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .like(DEFAULT_LIKE)
            .dislike(DEFAULT_DISLIKE)
            .date(DEFAULT_DATE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .video(DEFAULT_VIDEO)
            .videoContentType(DEFAULT_VIDEO_CONTENT_TYPE)
            .imagepath(DEFAULT_IMAGEPATH)
            .videopath(DEFAULT_VIDEOPATH);
        return review;
    }

    @Before
    public void initTest() {
        review = createEntity(em);
    }

    @Test
    @Transactional
    public void createReview() throws Exception {
        int databaseSizeBeforeCreate = reviewRepository.findAll().size();

        // Create the Review
        ReviewDTO reviewDTO = reviewMapper.toDto(review);
        restReviewMockMvc.perform(post("/api/reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewDTO)))
            .andExpect(status().isCreated());

        // Validate the Review in the database
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(databaseSizeBeforeCreate + 1);
        Review testReview = reviewList.get(reviewList.size() - 1);
        assertThat(testReview.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testReview.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testReview.getLike()).isEqualTo(DEFAULT_LIKE);
        assertThat(testReview.getDislike()).isEqualTo(DEFAULT_DISLIKE);
        assertThat(testReview.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testReview.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testReview.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testReview.getVideo()).isEqualTo(DEFAULT_VIDEO);
        assertThat(testReview.getVideoContentType()).isEqualTo(DEFAULT_VIDEO_CONTENT_TYPE);
        assertThat(testReview.getImagepath()).isEqualTo(DEFAULT_IMAGEPATH);
        assertThat(testReview.getVideopath()).isEqualTo(DEFAULT_VIDEOPATH);
    }

    @Test
    @Transactional
    public void createReviewWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reviewRepository.findAll().size();

        // Create the Review with an existing ID
        review.setId(1L);
        ReviewDTO reviewDTO = reviewMapper.toDto(review);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReviewMockMvc.perform(post("/api/reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Review in the database
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewRepository.findAll().size();
        // set the field null
        review.setTitle(null);

        // Create the Review, which fails.
        ReviewDTO reviewDTO = reviewMapper.toDto(review);

        restReviewMockMvc.perform(post("/api/reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewDTO)))
            .andExpect(status().isBadRequest());

        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewRepository.findAll().size();
        // set the field null
        review.setDescription(null);

        // Create the Review, which fails.
        ReviewDTO reviewDTO = reviewMapper.toDto(review);

        restReviewMockMvc.perform(post("/api/reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewDTO)))
            .andExpect(status().isBadRequest());

        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReviews() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList
        restReviewMockMvc.perform(get("/api/reviews?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(review.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].like").value(hasItem(DEFAULT_LIKE)))
            .andExpect(jsonPath("$.[*].dislike").value(hasItem(DEFAULT_DISLIKE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].videoContentType").value(hasItem(DEFAULT_VIDEO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO))))
            .andExpect(jsonPath("$.[*].imagepath").value(hasItem(DEFAULT_IMAGEPATH.toString())))
            .andExpect(jsonPath("$.[*].videopath").value(hasItem(DEFAULT_VIDEOPATH.toString())));
    }
    

    @Test
    @Transactional
    public void getReview() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get the review
        restReviewMockMvc.perform(get("/api/reviews/{id}", review.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(review.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.like").value(DEFAULT_LIKE))
            .andExpect(jsonPath("$.dislike").value(DEFAULT_DISLIKE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.videoContentType").value(DEFAULT_VIDEO_CONTENT_TYPE))
            .andExpect(jsonPath("$.video").value(Base64Utils.encodeToString(DEFAULT_VIDEO)))
            .andExpect(jsonPath("$.imagepath").value(DEFAULT_IMAGEPATH.toString()))
            .andExpect(jsonPath("$.videopath").value(DEFAULT_VIDEOPATH.toString()));
    }

    @Test
    @Transactional
    public void getAllReviewsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where title equals to DEFAULT_TITLE
        defaultReviewShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the reviewList where title equals to UPDATED_TITLE
        defaultReviewShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllReviewsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultReviewShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the reviewList where title equals to UPDATED_TITLE
        defaultReviewShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllReviewsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where title is not null
        defaultReviewShouldBeFound("title.specified=true");

        // Get all the reviewList where title is null
        defaultReviewShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    public void getAllReviewsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where description equals to DEFAULT_DESCRIPTION
        defaultReviewShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the reviewList where description equals to UPDATED_DESCRIPTION
        defaultReviewShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReviewsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultReviewShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the reviewList where description equals to UPDATED_DESCRIPTION
        defaultReviewShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReviewsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where description is not null
        defaultReviewShouldBeFound("description.specified=true");

        // Get all the reviewList where description is null
        defaultReviewShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllReviewsByLikeIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where like equals to DEFAULT_LIKE
        defaultReviewShouldBeFound("like.equals=" + DEFAULT_LIKE);

        // Get all the reviewList where like equals to UPDATED_LIKE
        defaultReviewShouldNotBeFound("like.equals=" + UPDATED_LIKE);
    }

    @Test
    @Transactional
    public void getAllReviewsByLikeIsInShouldWork() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where like in DEFAULT_LIKE or UPDATED_LIKE
        defaultReviewShouldBeFound("like.in=" + DEFAULT_LIKE + "," + UPDATED_LIKE);

        // Get all the reviewList where like equals to UPDATED_LIKE
        defaultReviewShouldNotBeFound("like.in=" + UPDATED_LIKE);
    }

    @Test
    @Transactional
    public void getAllReviewsByLikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where like is not null
        defaultReviewShouldBeFound("like.specified=true");

        // Get all the reviewList where like is null
        defaultReviewShouldNotBeFound("like.specified=false");
    }

    @Test
    @Transactional
    public void getAllReviewsByLikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where like greater than or equals to DEFAULT_LIKE
        defaultReviewShouldBeFound("like.greaterOrEqualThan=" + DEFAULT_LIKE);

        // Get all the reviewList where like greater than or equals to UPDATED_LIKE
        defaultReviewShouldNotBeFound("like.greaterOrEqualThan=" + UPDATED_LIKE);
    }

    @Test
    @Transactional
    public void getAllReviewsByLikeIsLessThanSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where like less than or equals to DEFAULT_LIKE
        defaultReviewShouldNotBeFound("like.lessThan=" + DEFAULT_LIKE);

        // Get all the reviewList where like less than or equals to UPDATED_LIKE
        defaultReviewShouldBeFound("like.lessThan=" + UPDATED_LIKE);
    }


    @Test
    @Transactional
    public void getAllReviewsByDislikeIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where dislike equals to DEFAULT_DISLIKE
        defaultReviewShouldBeFound("dislike.equals=" + DEFAULT_DISLIKE);

        // Get all the reviewList where dislike equals to UPDATED_DISLIKE
        defaultReviewShouldNotBeFound("dislike.equals=" + UPDATED_DISLIKE);
    }

    @Test
    @Transactional
    public void getAllReviewsByDislikeIsInShouldWork() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where dislike in DEFAULT_DISLIKE or UPDATED_DISLIKE
        defaultReviewShouldBeFound("dislike.in=" + DEFAULT_DISLIKE + "," + UPDATED_DISLIKE);

        // Get all the reviewList where dislike equals to UPDATED_DISLIKE
        defaultReviewShouldNotBeFound("dislike.in=" + UPDATED_DISLIKE);
    }

    @Test
    @Transactional
    public void getAllReviewsByDislikeIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where dislike is not null
        defaultReviewShouldBeFound("dislike.specified=true");

        // Get all the reviewList where dislike is null
        defaultReviewShouldNotBeFound("dislike.specified=false");
    }

    @Test
    @Transactional
    public void getAllReviewsByDislikeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where dislike greater than or equals to DEFAULT_DISLIKE
        defaultReviewShouldBeFound("dislike.greaterOrEqualThan=" + DEFAULT_DISLIKE);

        // Get all the reviewList where dislike greater than or equals to UPDATED_DISLIKE
        defaultReviewShouldNotBeFound("dislike.greaterOrEqualThan=" + UPDATED_DISLIKE);
    }

    @Test
    @Transactional
    public void getAllReviewsByDislikeIsLessThanSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where dislike less than or equals to DEFAULT_DISLIKE
        defaultReviewShouldNotBeFound("dislike.lessThan=" + DEFAULT_DISLIKE);

        // Get all the reviewList where dislike less than or equals to UPDATED_DISLIKE
        defaultReviewShouldBeFound("dislike.lessThan=" + UPDATED_DISLIKE);
    }


    @Test
    @Transactional
    public void getAllReviewsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where date equals to DEFAULT_DATE
        defaultReviewShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the reviewList where date equals to UPDATED_DATE
        defaultReviewShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllReviewsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where date in DEFAULT_DATE or UPDATED_DATE
        defaultReviewShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the reviewList where date equals to UPDATED_DATE
        defaultReviewShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllReviewsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where date is not null
        defaultReviewShouldBeFound("date.specified=true");

        // Get all the reviewList where date is null
        defaultReviewShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllReviewsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where date greater than or equals to DEFAULT_DATE
        defaultReviewShouldBeFound("date.greaterOrEqualThan=" + DEFAULT_DATE);

        // Get all the reviewList where date greater than or equals to UPDATED_DATE
        defaultReviewShouldNotBeFound("date.greaterOrEqualThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllReviewsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where date less than or equals to DEFAULT_DATE
        defaultReviewShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the reviewList where date less than or equals to UPDATED_DATE
        defaultReviewShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllReviewsByImagepathIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where imagepath equals to DEFAULT_IMAGEPATH
        defaultReviewShouldBeFound("imagepath.equals=" + DEFAULT_IMAGEPATH);

        // Get all the reviewList where imagepath equals to UPDATED_IMAGEPATH
        defaultReviewShouldNotBeFound("imagepath.equals=" + UPDATED_IMAGEPATH);
    }

    @Test
    @Transactional
    public void getAllReviewsByImagepathIsInShouldWork() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where imagepath in DEFAULT_IMAGEPATH or UPDATED_IMAGEPATH
        defaultReviewShouldBeFound("imagepath.in=" + DEFAULT_IMAGEPATH + "," + UPDATED_IMAGEPATH);

        // Get all the reviewList where imagepath equals to UPDATED_IMAGEPATH
        defaultReviewShouldNotBeFound("imagepath.in=" + UPDATED_IMAGEPATH);
    }

    @Test
    @Transactional
    public void getAllReviewsByImagepathIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where imagepath is not null
        defaultReviewShouldBeFound("imagepath.specified=true");

        // Get all the reviewList where imagepath is null
        defaultReviewShouldNotBeFound("imagepath.specified=false");
    }

    @Test
    @Transactional
    public void getAllReviewsByVideopathIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where videopath equals to DEFAULT_VIDEOPATH
        defaultReviewShouldBeFound("videopath.equals=" + DEFAULT_VIDEOPATH);

        // Get all the reviewList where videopath equals to UPDATED_VIDEOPATH
        defaultReviewShouldNotBeFound("videopath.equals=" + UPDATED_VIDEOPATH);
    }

    @Test
    @Transactional
    public void getAllReviewsByVideopathIsInShouldWork() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where videopath in DEFAULT_VIDEOPATH or UPDATED_VIDEOPATH
        defaultReviewShouldBeFound("videopath.in=" + DEFAULT_VIDEOPATH + "," + UPDATED_VIDEOPATH);

        // Get all the reviewList where videopath equals to UPDATED_VIDEOPATH
        defaultReviewShouldNotBeFound("videopath.in=" + UPDATED_VIDEOPATH);
    }

    @Test
    @Transactional
    public void getAllReviewsByVideopathIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        // Get all the reviewList where videopath is not null
        defaultReviewShouldBeFound("videopath.specified=true");

        // Get all the reviewList where videopath is null
        defaultReviewShouldNotBeFound("videopath.specified=false");
    }

    @Test
    @Transactional
    public void getAllReviewsByUserProfileIsEqualToSomething() throws Exception {
        // Initialize the database
        UserProfile userProfile = UserProfileResourceIntTest.createEntity(em);
        em.persist(userProfile);
        em.flush();
        review.setUserProfile(userProfile);
        reviewRepository.saveAndFlush(review);
        Long userProfileId = userProfile.getId();

        // Get all the reviewList where userProfile equals to userProfileId
        defaultReviewShouldBeFound("userProfileId.equals=" + userProfileId);

        // Get all the reviewList where userProfile equals to userProfileId + 1
        defaultReviewShouldNotBeFound("userProfileId.equals=" + (userProfileId + 1));
    }


    @Test
    @Transactional
    public void getAllReviewsByBusinessIsEqualToSomething() throws Exception {
        // Initialize the database
        Business business = BusinessResourceIntTest.createEntity(em);
        em.persist(business);
        em.flush();
        review.setBusiness(business);
        reviewRepository.saveAndFlush(review);
        Long businessId = business.getId();

        // Get all the reviewList where business equals to businessId
        defaultReviewShouldBeFound("businessId.equals=" + businessId);

        // Get all the reviewList where business equals to businessId + 1
        defaultReviewShouldNotBeFound("businessId.equals=" + (businessId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultReviewShouldBeFound(String filter) throws Exception {
        restReviewMockMvc.perform(get("/api/reviews?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(review.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].like").value(hasItem(DEFAULT_LIKE)))
            .andExpect(jsonPath("$.[*].dislike").value(hasItem(DEFAULT_DISLIKE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].videoContentType").value(hasItem(DEFAULT_VIDEO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO))))
            .andExpect(jsonPath("$.[*].imagepath").value(hasItem(DEFAULT_IMAGEPATH.toString())))
            .andExpect(jsonPath("$.[*].videopath").value(hasItem(DEFAULT_VIDEOPATH.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultReviewShouldNotBeFound(String filter) throws Exception {
        restReviewMockMvc.perform(get("/api/reviews?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingReview() throws Exception {
        // Get the review
        restReviewMockMvc.perform(get("/api/reviews/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReview() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        int databaseSizeBeforeUpdate = reviewRepository.findAll().size();

        // Update the review
        Review updatedReview = reviewRepository.findById(review.getId()).get();
        // Disconnect from session so that the updates on updatedReview are not directly saved in db
        em.detach(updatedReview);
        updatedReview
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .like(UPDATED_LIKE)
            .dislike(UPDATED_DISLIKE)
            .date(UPDATED_DATE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .video(UPDATED_VIDEO)
            .videoContentType(UPDATED_VIDEO_CONTENT_TYPE)
            .imagepath(UPDATED_IMAGEPATH)
            .videopath(UPDATED_VIDEOPATH);
        ReviewDTO reviewDTO = reviewMapper.toDto(updatedReview);

        restReviewMockMvc.perform(put("/api/reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewDTO)))
            .andExpect(status().isOk());

        // Validate the Review in the database
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(databaseSizeBeforeUpdate);
        Review testReview = reviewList.get(reviewList.size() - 1);
        assertThat(testReview.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testReview.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testReview.getLike()).isEqualTo(UPDATED_LIKE);
        assertThat(testReview.getDislike()).isEqualTo(UPDATED_DISLIKE);
        assertThat(testReview.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testReview.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testReview.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testReview.getVideo()).isEqualTo(UPDATED_VIDEO);
        assertThat(testReview.getVideoContentType()).isEqualTo(UPDATED_VIDEO_CONTENT_TYPE);
        assertThat(testReview.getImagepath()).isEqualTo(UPDATED_IMAGEPATH);
        assertThat(testReview.getVideopath()).isEqualTo(UPDATED_VIDEOPATH);
    }

    @Test
    @Transactional
    public void updateNonExistingReview() throws Exception {
        int databaseSizeBeforeUpdate = reviewRepository.findAll().size();

        // Create the Review
        ReviewDTO reviewDTO = reviewMapper.toDto(review);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReviewMockMvc.perform(put("/api/reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Review in the database
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReview() throws Exception {
        // Initialize the database
        reviewRepository.saveAndFlush(review);

        int databaseSizeBeforeDelete = reviewRepository.findAll().size();

        // Get the review
        restReviewMockMvc.perform(delete("/api/reviews/{id}", review.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Review> reviewList = reviewRepository.findAll();
        assertThat(reviewList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Review.class);
        Review review1 = new Review();
        review1.setId(1L);
        Review review2 = new Review();
        review2.setId(review1.getId());
        assertThat(review1).isEqualTo(review2);
        review2.setId(2L);
        assertThat(review1).isNotEqualTo(review2);
        review1.setId(null);
        assertThat(review1).isNotEqualTo(review2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReviewDTO.class);
        ReviewDTO reviewDTO1 = new ReviewDTO();
        reviewDTO1.setId(1L);
        ReviewDTO reviewDTO2 = new ReviewDTO();
        assertThat(reviewDTO1).isNotEqualTo(reviewDTO2);
        reviewDTO2.setId(reviewDTO1.getId());
        assertThat(reviewDTO1).isEqualTo(reviewDTO2);
        reviewDTO2.setId(2L);
        assertThat(reviewDTO1).isNotEqualTo(reviewDTO2);
        reviewDTO1.setId(null);
        assertThat(reviewDTO1).isNotEqualTo(reviewDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(reviewMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(reviewMapper.fromId(null)).isNull();
    }
}
