package com.alireza.testapp.web.rest;

import com.alireza.testapp.TestappApp;

import com.alireza.testapp.domain.Business;
import com.alireza.testapp.domain.UserProfile;
import com.alireza.testapp.domain.Review;
import com.alireza.testapp.repository.BusinessRepository;
import com.alireza.testapp.service.BusinessService;
import com.alireza.testapp.service.dto.BusinessDTO;
import com.alireza.testapp.service.mapper.BusinessMapper;
import com.alireza.testapp.web.rest.errors.ExceptionTranslator;
import com.alireza.testapp.service.dto.BusinessCriteria;
import com.alireza.testapp.service.BusinessQueryService;

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

import com.alireza.testapp.domain.enumeration.State;
/**
 * Test class for the BusinessResource REST controller.
 *
 * @see BusinessResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestappApp.class)
public class BusinessResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final State DEFAULT_STATE = State.ALBORZ;
    private static final State UPDATED_STATE = State.ARDABIL;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final Integer DEFAULT_RATE = 1;
    private static final Integer UPDATED_RATE = 2;

    private static final LocalDate DEFAULT_SINCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SINCE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final Boolean DEFAULT_RESERVATION = false;
    private static final Boolean UPDATED_RESERVATION = true;

    private static final Boolean DEFAULT_DELIVERY = false;
    private static final Boolean UPDATED_DELIVERY = true;

    private static final Boolean DEFAULT_WIFI = false;
    private static final Boolean UPDATED_WIFI = true;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_VIDEO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_VIDEO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_VIDEO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_VIDEO_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_PAID = false;
    private static final Boolean UPDATED_PAID = true;

    private static final String DEFAULT_IMAGEPATH = "AAAAAAAAAA";
    private static final String UPDATED_IMAGEPATH = "BBBBBBBBBB";

    private static final String DEFAULT_VIDEOPATH = "AAAAAAAAAA";
    private static final String UPDATED_VIDEOPATH = "BBBBBBBBBB";

    @Autowired
    private BusinessRepository businessRepository;


    @Autowired
    private BusinessMapper businessMapper;
    

    @Autowired
    private BusinessService businessService;

    @Autowired
    private BusinessQueryService businessQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBusinessMockMvc;

    private Business business;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BusinessResource businessResource = new BusinessResource(businessService, businessQueryService);
        this.restBusinessMockMvc = MockMvcBuilders.standaloneSetup(businessResource)
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
    public static Business createEntity(EntityManager em) {
        Business business = new Business()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .state(DEFAULT_STATE)
            .address(DEFAULT_ADDRESS)
            .phone(DEFAULT_PHONE)
            .rate(DEFAULT_RATE)
            .since(DEFAULT_SINCE)
            .link(DEFAULT_LINK)
            .reservation(DEFAULT_RESERVATION)
            .delivery(DEFAULT_DELIVERY)
            .wifi(DEFAULT_WIFI)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .video(DEFAULT_VIDEO)
            .videoContentType(DEFAULT_VIDEO_CONTENT_TYPE)
            .paid(DEFAULT_PAID)
            .imagepath(DEFAULT_IMAGEPATH)
            .videopath(DEFAULT_VIDEOPATH);
        return business;
    }

    @Before
    public void initTest() {
        business = createEntity(em);
    }

    @Test
    @Transactional
    public void createBusiness() throws Exception {
        int databaseSizeBeforeCreate = businessRepository.findAll().size();

        // Create the Business
        BusinessDTO businessDTO = businessMapper.toDto(business);
        restBusinessMockMvc.perform(post("/api/businesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessDTO)))
            .andExpect(status().isCreated());

        // Validate the Business in the database
        List<Business> businessList = businessRepository.findAll();
        assertThat(businessList).hasSize(databaseSizeBeforeCreate + 1);
        Business testBusiness = businessList.get(businessList.size() - 1);
        assertThat(testBusiness.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBusiness.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBusiness.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testBusiness.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testBusiness.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testBusiness.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testBusiness.getSince()).isEqualTo(DEFAULT_SINCE);
        assertThat(testBusiness.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testBusiness.isReservation()).isEqualTo(DEFAULT_RESERVATION);
        assertThat(testBusiness.isDelivery()).isEqualTo(DEFAULT_DELIVERY);
        assertThat(testBusiness.isWifi()).isEqualTo(DEFAULT_WIFI);
        assertThat(testBusiness.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testBusiness.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testBusiness.getVideo()).isEqualTo(DEFAULT_VIDEO);
        assertThat(testBusiness.getVideoContentType()).isEqualTo(DEFAULT_VIDEO_CONTENT_TYPE);
        assertThat(testBusiness.isPaid()).isEqualTo(DEFAULT_PAID);
        assertThat(testBusiness.getImagepath()).isEqualTo(DEFAULT_IMAGEPATH);
        assertThat(testBusiness.getVideopath()).isEqualTo(DEFAULT_VIDEOPATH);
    }

    @Test
    @Transactional
    public void createBusinessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = businessRepository.findAll().size();

        // Create the Business with an existing ID
        business.setId(1L);
        BusinessDTO businessDTO = businessMapper.toDto(business);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessMockMvc.perform(post("/api/businesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Business in the database
        List<Business> businessList = businessRepository.findAll();
        assertThat(businessList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessRepository.findAll().size();
        // set the field null
        business.setTitle(null);

        // Create the Business, which fails.
        BusinessDTO businessDTO = businessMapper.toDto(business);

        restBusinessMockMvc.perform(post("/api/businesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessDTO)))
            .andExpect(status().isBadRequest());

        List<Business> businessList = businessRepository.findAll();
        assertThat(businessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBusinesses() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList
        restBusinessMockMvc.perform(get("/api/businesses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(business.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE)))
            .andExpect(jsonPath("$.[*].since").value(hasItem(DEFAULT_SINCE.toString())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())))
            .andExpect(jsonPath("$.[*].reservation").value(hasItem(DEFAULT_RESERVATION.booleanValue())))
            .andExpect(jsonPath("$.[*].delivery").value(hasItem(DEFAULT_DELIVERY.booleanValue())))
            .andExpect(jsonPath("$.[*].wifi").value(hasItem(DEFAULT_WIFI.booleanValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].videoContentType").value(hasItem(DEFAULT_VIDEO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO))))
            .andExpect(jsonPath("$.[*].paid").value(hasItem(DEFAULT_PAID.booleanValue())))
            .andExpect(jsonPath("$.[*].imagepath").value(hasItem(DEFAULT_IMAGEPATH.toString())))
            .andExpect(jsonPath("$.[*].videopath").value(hasItem(DEFAULT_VIDEOPATH.toString())));
    }
    

    @Test
    @Transactional
    public void getBusiness() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get the business
        restBusinessMockMvc.perform(get("/api/businesses/{id}", business.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(business.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE))
            .andExpect(jsonPath("$.since").value(DEFAULT_SINCE.toString()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK.toString()))
            .andExpect(jsonPath("$.reservation").value(DEFAULT_RESERVATION.booleanValue()))
            .andExpect(jsonPath("$.delivery").value(DEFAULT_DELIVERY.booleanValue()))
            .andExpect(jsonPath("$.wifi").value(DEFAULT_WIFI.booleanValue()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.videoContentType").value(DEFAULT_VIDEO_CONTENT_TYPE))
            .andExpect(jsonPath("$.video").value(Base64Utils.encodeToString(DEFAULT_VIDEO)))
            .andExpect(jsonPath("$.paid").value(DEFAULT_PAID.booleanValue()))
            .andExpect(jsonPath("$.imagepath").value(DEFAULT_IMAGEPATH.toString()))
            .andExpect(jsonPath("$.videopath").value(DEFAULT_VIDEOPATH.toString()));
    }

    @Test
    @Transactional
    public void getAllBusinessesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where title equals to DEFAULT_TITLE
        defaultBusinessShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the businessList where title equals to UPDATED_TITLE
        defaultBusinessShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllBusinessesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultBusinessShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the businessList where title equals to UPDATED_TITLE
        defaultBusinessShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllBusinessesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where title is not null
        defaultBusinessShouldBeFound("title.specified=true");

        // Get all the businessList where title is null
        defaultBusinessShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    public void getAllBusinessesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where description equals to DEFAULT_DESCRIPTION
        defaultBusinessShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the businessList where description equals to UPDATED_DESCRIPTION
        defaultBusinessShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllBusinessesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultBusinessShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the businessList where description equals to UPDATED_DESCRIPTION
        defaultBusinessShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllBusinessesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where description is not null
        defaultBusinessShouldBeFound("description.specified=true");

        // Get all the businessList where description is null
        defaultBusinessShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllBusinessesByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where state equals to DEFAULT_STATE
        defaultBusinessShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the businessList where state equals to UPDATED_STATE
        defaultBusinessShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllBusinessesByStateIsInShouldWork() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where state in DEFAULT_STATE or UPDATED_STATE
        defaultBusinessShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the businessList where state equals to UPDATED_STATE
        defaultBusinessShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllBusinessesByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where state is not null
        defaultBusinessShouldBeFound("state.specified=true");

        // Get all the businessList where state is null
        defaultBusinessShouldNotBeFound("state.specified=false");
    }

    @Test
    @Transactional
    public void getAllBusinessesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where address equals to DEFAULT_ADDRESS
        defaultBusinessShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the businessList where address equals to UPDATED_ADDRESS
        defaultBusinessShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllBusinessesByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultBusinessShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the businessList where address equals to UPDATED_ADDRESS
        defaultBusinessShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllBusinessesByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where address is not null
        defaultBusinessShouldBeFound("address.specified=true");

        // Get all the businessList where address is null
        defaultBusinessShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    public void getAllBusinessesByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where phone equals to DEFAULT_PHONE
        defaultBusinessShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the businessList where phone equals to UPDATED_PHONE
        defaultBusinessShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllBusinessesByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultBusinessShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the businessList where phone equals to UPDATED_PHONE
        defaultBusinessShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllBusinessesByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where phone is not null
        defaultBusinessShouldBeFound("phone.specified=true");

        // Get all the businessList where phone is null
        defaultBusinessShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    public void getAllBusinessesByRateIsEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where rate equals to DEFAULT_RATE
        defaultBusinessShouldBeFound("rate.equals=" + DEFAULT_RATE);

        // Get all the businessList where rate equals to UPDATED_RATE
        defaultBusinessShouldNotBeFound("rate.equals=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllBusinessesByRateIsInShouldWork() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where rate in DEFAULT_RATE or UPDATED_RATE
        defaultBusinessShouldBeFound("rate.in=" + DEFAULT_RATE + "," + UPDATED_RATE);

        // Get all the businessList where rate equals to UPDATED_RATE
        defaultBusinessShouldNotBeFound("rate.in=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllBusinessesByRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where rate is not null
        defaultBusinessShouldBeFound("rate.specified=true");

        // Get all the businessList where rate is null
        defaultBusinessShouldNotBeFound("rate.specified=false");
    }

    @Test
    @Transactional
    public void getAllBusinessesByRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where rate greater than or equals to DEFAULT_RATE
        defaultBusinessShouldBeFound("rate.greaterOrEqualThan=" + DEFAULT_RATE);

        // Get all the businessList where rate greater than or equals to (DEFAULT_RATE + 1)
        defaultBusinessShouldNotBeFound("rate.greaterOrEqualThan=" + (DEFAULT_RATE + 1));
    }

    @Test
    @Transactional
    public void getAllBusinessesByRateIsLessThanSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where rate less than or equals to DEFAULT_RATE
        defaultBusinessShouldNotBeFound("rate.lessThan=" + DEFAULT_RATE);

        // Get all the businessList where rate less than or equals to (DEFAULT_RATE + 1)
        defaultBusinessShouldBeFound("rate.lessThan=" + (DEFAULT_RATE + 1));
    }


    @Test
    @Transactional
    public void getAllBusinessesBySinceIsEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where since equals to DEFAULT_SINCE
        defaultBusinessShouldBeFound("since.equals=" + DEFAULT_SINCE);

        // Get all the businessList where since equals to UPDATED_SINCE
        defaultBusinessShouldNotBeFound("since.equals=" + UPDATED_SINCE);
    }

    @Test
    @Transactional
    public void getAllBusinessesBySinceIsInShouldWork() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where since in DEFAULT_SINCE or UPDATED_SINCE
        defaultBusinessShouldBeFound("since.in=" + DEFAULT_SINCE + "," + UPDATED_SINCE);

        // Get all the businessList where since equals to UPDATED_SINCE
        defaultBusinessShouldNotBeFound("since.in=" + UPDATED_SINCE);
    }

    @Test
    @Transactional
    public void getAllBusinessesBySinceIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where since is not null
        defaultBusinessShouldBeFound("since.specified=true");

        // Get all the businessList where since is null
        defaultBusinessShouldNotBeFound("since.specified=false");
    }

    @Test
    @Transactional
    public void getAllBusinessesBySinceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where since greater than or equals to DEFAULT_SINCE
        defaultBusinessShouldBeFound("since.greaterOrEqualThan=" + DEFAULT_SINCE);

        // Get all the businessList where since greater than or equals to UPDATED_SINCE
        defaultBusinessShouldNotBeFound("since.greaterOrEqualThan=" + UPDATED_SINCE);
    }

    @Test
    @Transactional
    public void getAllBusinessesBySinceIsLessThanSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where since less than or equals to DEFAULT_SINCE
        defaultBusinessShouldNotBeFound("since.lessThan=" + DEFAULT_SINCE);

        // Get all the businessList where since less than or equals to UPDATED_SINCE
        defaultBusinessShouldBeFound("since.lessThan=" + UPDATED_SINCE);
    }


    @Test
    @Transactional
    public void getAllBusinessesByLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where link equals to DEFAULT_LINK
        defaultBusinessShouldBeFound("link.equals=" + DEFAULT_LINK);

        // Get all the businessList where link equals to UPDATED_LINK
        defaultBusinessShouldNotBeFound("link.equals=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    public void getAllBusinessesByLinkIsInShouldWork() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where link in DEFAULT_LINK or UPDATED_LINK
        defaultBusinessShouldBeFound("link.in=" + DEFAULT_LINK + "," + UPDATED_LINK);

        // Get all the businessList where link equals to UPDATED_LINK
        defaultBusinessShouldNotBeFound("link.in=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    public void getAllBusinessesByLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where link is not null
        defaultBusinessShouldBeFound("link.specified=true");

        // Get all the businessList where link is null
        defaultBusinessShouldNotBeFound("link.specified=false");
    }

    @Test
    @Transactional
    public void getAllBusinessesByReservationIsEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where reservation equals to DEFAULT_RESERVATION
        defaultBusinessShouldBeFound("reservation.equals=" + DEFAULT_RESERVATION);

        // Get all the businessList where reservation equals to UPDATED_RESERVATION
        defaultBusinessShouldNotBeFound("reservation.equals=" + UPDATED_RESERVATION);
    }

    @Test
    @Transactional
    public void getAllBusinessesByReservationIsInShouldWork() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where reservation in DEFAULT_RESERVATION or UPDATED_RESERVATION
        defaultBusinessShouldBeFound("reservation.in=" + DEFAULT_RESERVATION + "," + UPDATED_RESERVATION);

        // Get all the businessList where reservation equals to UPDATED_RESERVATION
        defaultBusinessShouldNotBeFound("reservation.in=" + UPDATED_RESERVATION);
    }

    @Test
    @Transactional
    public void getAllBusinessesByReservationIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where reservation is not null
        defaultBusinessShouldBeFound("reservation.specified=true");

        // Get all the businessList where reservation is null
        defaultBusinessShouldNotBeFound("reservation.specified=false");
    }

    @Test
    @Transactional
    public void getAllBusinessesByDeliveryIsEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where delivery equals to DEFAULT_DELIVERY
        defaultBusinessShouldBeFound("delivery.equals=" + DEFAULT_DELIVERY);

        // Get all the businessList where delivery equals to UPDATED_DELIVERY
        defaultBusinessShouldNotBeFound("delivery.equals=" + UPDATED_DELIVERY);
    }

    @Test
    @Transactional
    public void getAllBusinessesByDeliveryIsInShouldWork() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where delivery in DEFAULT_DELIVERY or UPDATED_DELIVERY
        defaultBusinessShouldBeFound("delivery.in=" + DEFAULT_DELIVERY + "," + UPDATED_DELIVERY);

        // Get all the businessList where delivery equals to UPDATED_DELIVERY
        defaultBusinessShouldNotBeFound("delivery.in=" + UPDATED_DELIVERY);
    }

    @Test
    @Transactional
    public void getAllBusinessesByDeliveryIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where delivery is not null
        defaultBusinessShouldBeFound("delivery.specified=true");

        // Get all the businessList where delivery is null
        defaultBusinessShouldNotBeFound("delivery.specified=false");
    }

    @Test
    @Transactional
    public void getAllBusinessesByWifiIsEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where wifi equals to DEFAULT_WIFI
        defaultBusinessShouldBeFound("wifi.equals=" + DEFAULT_WIFI);

        // Get all the businessList where wifi equals to UPDATED_WIFI
        defaultBusinessShouldNotBeFound("wifi.equals=" + UPDATED_WIFI);
    }

    @Test
    @Transactional
    public void getAllBusinessesByWifiIsInShouldWork() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where wifi in DEFAULT_WIFI or UPDATED_WIFI
        defaultBusinessShouldBeFound("wifi.in=" + DEFAULT_WIFI + "," + UPDATED_WIFI);

        // Get all the businessList where wifi equals to UPDATED_WIFI
        defaultBusinessShouldNotBeFound("wifi.in=" + UPDATED_WIFI);
    }

    @Test
    @Transactional
    public void getAllBusinessesByWifiIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where wifi is not null
        defaultBusinessShouldBeFound("wifi.specified=true");

        // Get all the businessList where wifi is null
        defaultBusinessShouldNotBeFound("wifi.specified=false");
    }

    @Test
    @Transactional
    public void getAllBusinessesByPaidIsEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where paid equals to DEFAULT_PAID
        defaultBusinessShouldBeFound("paid.equals=" + DEFAULT_PAID);

        // Get all the businessList where paid equals to UPDATED_PAID
        defaultBusinessShouldNotBeFound("paid.equals=" + UPDATED_PAID);
    }

    @Test
    @Transactional
    public void getAllBusinessesByPaidIsInShouldWork() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where paid in DEFAULT_PAID or UPDATED_PAID
        defaultBusinessShouldBeFound("paid.in=" + DEFAULT_PAID + "," + UPDATED_PAID);

        // Get all the businessList where paid equals to UPDATED_PAID
        defaultBusinessShouldNotBeFound("paid.in=" + UPDATED_PAID);
    }

    @Test
    @Transactional
    public void getAllBusinessesByPaidIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where paid is not null
        defaultBusinessShouldBeFound("paid.specified=true");

        // Get all the businessList where paid is null
        defaultBusinessShouldNotBeFound("paid.specified=false");
    }

    @Test
    @Transactional
    public void getAllBusinessesByImagepathIsEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where imagepath equals to DEFAULT_IMAGEPATH
        defaultBusinessShouldBeFound("imagepath.equals=" + DEFAULT_IMAGEPATH);

        // Get all the businessList where imagepath equals to UPDATED_IMAGEPATH
        defaultBusinessShouldNotBeFound("imagepath.equals=" + UPDATED_IMAGEPATH);
    }

    @Test
    @Transactional
    public void getAllBusinessesByImagepathIsInShouldWork() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where imagepath in DEFAULT_IMAGEPATH or UPDATED_IMAGEPATH
        defaultBusinessShouldBeFound("imagepath.in=" + DEFAULT_IMAGEPATH + "," + UPDATED_IMAGEPATH);

        // Get all the businessList where imagepath equals to UPDATED_IMAGEPATH
        defaultBusinessShouldNotBeFound("imagepath.in=" + UPDATED_IMAGEPATH);
    }

    @Test
    @Transactional
    public void getAllBusinessesByImagepathIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where imagepath is not null
        defaultBusinessShouldBeFound("imagepath.specified=true");

        // Get all the businessList where imagepath is null
        defaultBusinessShouldNotBeFound("imagepath.specified=false");
    }

    @Test
    @Transactional
    public void getAllBusinessesByVideopathIsEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where videopath equals to DEFAULT_VIDEOPATH
        defaultBusinessShouldBeFound("videopath.equals=" + DEFAULT_VIDEOPATH);

        // Get all the businessList where videopath equals to UPDATED_VIDEOPATH
        defaultBusinessShouldNotBeFound("videopath.equals=" + UPDATED_VIDEOPATH);
    }

    @Test
    @Transactional
    public void getAllBusinessesByVideopathIsInShouldWork() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where videopath in DEFAULT_VIDEOPATH or UPDATED_VIDEOPATH
        defaultBusinessShouldBeFound("videopath.in=" + DEFAULT_VIDEOPATH + "," + UPDATED_VIDEOPATH);

        // Get all the businessList where videopath equals to UPDATED_VIDEOPATH
        defaultBusinessShouldNotBeFound("videopath.in=" + UPDATED_VIDEOPATH);
    }

    @Test
    @Transactional
    public void getAllBusinessesByVideopathIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where videopath is not null
        defaultBusinessShouldBeFound("videopath.specified=true");

        // Get all the businessList where videopath is null
        defaultBusinessShouldNotBeFound("videopath.specified=false");
    }

    @Test
    @Transactional
    public void getAllBusinessesByUserProfileIsEqualToSomething() throws Exception {
        // Initialize the database
        UserProfile userProfile = UserProfileResourceIntTest.createEntity(em);
        em.persist(userProfile);
        em.flush();
        business.setUserProfile(userProfile);
        businessRepository.saveAndFlush(business);
        Long userProfileId = userProfile.getId();

        // Get all the businessList where userProfile equals to userProfileId
        defaultBusinessShouldBeFound("userProfileId.equals=" + userProfileId);

        // Get all the businessList where userProfile equals to userProfileId + 1
        defaultBusinessShouldNotBeFound("userProfileId.equals=" + (userProfileId + 1));
    }


    @Test
    @Transactional
    public void getAllBusinessesByReviewIsEqualToSomething() throws Exception {
        // Initialize the database
        Review review = ReviewResourceIntTest.createEntity(em);
        em.persist(review);
        em.flush();
        business.addReview(review);
        businessRepository.saveAndFlush(business);
        Long reviewId = review.getId();

        // Get all the businessList where review equals to reviewId
        defaultBusinessShouldBeFound("reviewId.equals=" + reviewId);

        // Get all the businessList where review equals to reviewId + 1
        defaultBusinessShouldNotBeFound("reviewId.equals=" + (reviewId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultBusinessShouldBeFound(String filter) throws Exception {
        restBusinessMockMvc.perform(get("/api/businesses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(business.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE)))
            .andExpect(jsonPath("$.[*].since").value(hasItem(DEFAULT_SINCE.toString())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())))
            .andExpect(jsonPath("$.[*].reservation").value(hasItem(DEFAULT_RESERVATION.booleanValue())))
            .andExpect(jsonPath("$.[*].delivery").value(hasItem(DEFAULT_DELIVERY.booleanValue())))
            .andExpect(jsonPath("$.[*].wifi").value(hasItem(DEFAULT_WIFI.booleanValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].videoContentType").value(hasItem(DEFAULT_VIDEO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO))))
            .andExpect(jsonPath("$.[*].paid").value(hasItem(DEFAULT_PAID.booleanValue())))
            .andExpect(jsonPath("$.[*].imagepath").value(hasItem(DEFAULT_IMAGEPATH.toString())))
            .andExpect(jsonPath("$.[*].videopath").value(hasItem(DEFAULT_VIDEOPATH.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultBusinessShouldNotBeFound(String filter) throws Exception {
        restBusinessMockMvc.perform(get("/api/businesses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingBusiness() throws Exception {
        // Get the business
        restBusinessMockMvc.perform(get("/api/businesses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusiness() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        int databaseSizeBeforeUpdate = businessRepository.findAll().size();

        // Update the business
        Business updatedBusiness = businessRepository.findById(business.getId()).get();
        // Disconnect from session so that the updates on updatedBusiness are not directly saved in db
        em.detach(updatedBusiness);
        updatedBusiness
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .state(UPDATED_STATE)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .rate(UPDATED_RATE)
            .since(UPDATED_SINCE)
            .link(UPDATED_LINK)
            .reservation(UPDATED_RESERVATION)
            .delivery(UPDATED_DELIVERY)
            .wifi(UPDATED_WIFI)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .video(UPDATED_VIDEO)
            .videoContentType(UPDATED_VIDEO_CONTENT_TYPE)
            .paid(UPDATED_PAID)
            .imagepath(UPDATED_IMAGEPATH)
            .videopath(UPDATED_VIDEOPATH);
        BusinessDTO businessDTO = businessMapper.toDto(updatedBusiness);

        restBusinessMockMvc.perform(put("/api/businesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessDTO)))
            .andExpect(status().isOk());

        // Validate the Business in the database
        List<Business> businessList = businessRepository.findAll();
        assertThat(businessList).hasSize(databaseSizeBeforeUpdate);
        Business testBusiness = businessList.get(businessList.size() - 1);
        assertThat(testBusiness.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBusiness.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBusiness.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testBusiness.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testBusiness.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testBusiness.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testBusiness.getSince()).isEqualTo(UPDATED_SINCE);
        assertThat(testBusiness.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testBusiness.isReservation()).isEqualTo(UPDATED_RESERVATION);
        assertThat(testBusiness.isDelivery()).isEqualTo(UPDATED_DELIVERY);
        assertThat(testBusiness.isWifi()).isEqualTo(UPDATED_WIFI);
        assertThat(testBusiness.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testBusiness.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testBusiness.getVideo()).isEqualTo(UPDATED_VIDEO);
        assertThat(testBusiness.getVideoContentType()).isEqualTo(UPDATED_VIDEO_CONTENT_TYPE);
        assertThat(testBusiness.isPaid()).isEqualTo(UPDATED_PAID);
        assertThat(testBusiness.getImagepath()).isEqualTo(UPDATED_IMAGEPATH);
        assertThat(testBusiness.getVideopath()).isEqualTo(UPDATED_VIDEOPATH);
    }

    @Test
    @Transactional
    public void updateNonExistingBusiness() throws Exception {
        int databaseSizeBeforeUpdate = businessRepository.findAll().size();

        // Create the Business
        BusinessDTO businessDTO = businessMapper.toDto(business);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBusinessMockMvc.perform(put("/api/businesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Business in the database
        List<Business> businessList = businessRepository.findAll();
        assertThat(businessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBusiness() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        int databaseSizeBeforeDelete = businessRepository.findAll().size();

        // Get the business
        restBusinessMockMvc.perform(delete("/api/businesses/{id}", business.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Business> businessList = businessRepository.findAll();
        assertThat(businessList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Business.class);
        Business business1 = new Business();
        business1.setId(1L);
        Business business2 = new Business();
        business2.setId(business1.getId());
        assertThat(business1).isEqualTo(business2);
        business2.setId(2L);
        assertThat(business1).isNotEqualTo(business2);
        business1.setId(null);
        assertThat(business1).isNotEqualTo(business2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessDTO.class);
        BusinessDTO businessDTO1 = new BusinessDTO();
        businessDTO1.setId(1L);
        BusinessDTO businessDTO2 = new BusinessDTO();
        assertThat(businessDTO1).isNotEqualTo(businessDTO2);
        businessDTO2.setId(businessDTO1.getId());
        assertThat(businessDTO1).isEqualTo(businessDTO2);
        businessDTO2.setId(2L);
        assertThat(businessDTO1).isNotEqualTo(businessDTO2);
        businessDTO1.setId(null);
        assertThat(businessDTO1).isNotEqualTo(businessDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(businessMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(businessMapper.fromId(null)).isNull();
    }
}
