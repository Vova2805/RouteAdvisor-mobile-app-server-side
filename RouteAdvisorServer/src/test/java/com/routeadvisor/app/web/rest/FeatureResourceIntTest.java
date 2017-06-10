package com.routeadvisor.app.web.rest;

import com.routeadvisor.app.RouteAdvisorApp;

import com.routeadvisor.app.domain.Feature;
import com.routeadvisor.app.repository.FeatureRepository;
import com.routeadvisor.app.web.rest.errors.ExceptionTranslator;

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

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.routeadvisor.app.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.routeadvisor.app.domain.enumeration.FeatureLevel;
import com.routeadvisor.app.domain.enumeration.FeatureType;
import com.routeadvisor.app.domain.enumeration.Category;
/**
 * Test class for the FeatureResource REST controller.
 *
 * @see FeatureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RouteAdvisorApp.class)
public class FeatureResourceIntTest {

    private static final FeatureLevel DEFAULT_LEVEL = FeatureLevel.CITY;
    private static final FeatureLevel UPDATED_LEVEL = FeatureLevel.DESTINATION;

    private static final FeatureType DEFAULT_TYPE = FeatureType.CITY;
    private static final FeatureType UPDATED_TYPE = FeatureType.DESTINATION;

    private static final Double DEFAULT_RATING = 0D;
    private static final Double UPDATED_RATING = 1D;

    private static final String DEFAULT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_GUID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_SUFFIX = "AAAAAAAAAA";
    private static final String UPDATED_NAME_SUFFIX = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_URL = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_URL = "BBBBBBBBBB";

    private static final Double DEFAULT_LAT = 1D;
    private static final Double UPDATED_LAT = 2D;

    private static final Double DEFAULT_LNG = 1D;
    private static final Double UPDATED_LNG = 2D;

    private static final Category DEFAULT_CATEGORY = Category.NONE;
    private static final Category UPDATED_CATEGORY = Category.FAMILY;

    private static final String DEFAULT_TAGS = "AAAAAAAAAA";
    private static final String UPDATED_TAGS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_EDITED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EDITED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_TIER = 1;
    private static final Integer UPDATED_TIER = 2;

    private static final Integer DEFAULT_PROPOSED_DURATION = 1;
    private static final Integer UPDATED_PROPOSED_DURATION = 2;

    private static final Integer DEFAULT_FAVORITES = 0;
    private static final Integer UPDATED_FAVORITES = 1;

    private static final String DEFAULT_OFFICIAL_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_OFFICIAL_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_OPENING_HOURS = "AAAAAAAAAA";
    private static final String UPDATED_OPENING_HOURS = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restFeatureMockMvc;

    private Feature feature;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FeatureResource featureResource = new FeatureResource(featureRepository);
        this.restFeatureMockMvc = MockMvcBuilders.standaloneSetup(featureResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feature createEntity() {
        Feature feature = new Feature()
            .level(DEFAULT_LEVEL)
            .type(DEFAULT_TYPE)
            .rating(DEFAULT_RATING)
            .guid(DEFAULT_GUID)
            .name(DEFAULT_NAME)
            .nameSuffix(DEFAULT_NAME_SUFFIX)
            .originalName(DEFAULT_ORIGINAL_NAME)
            .description(DEFAULT_DESCRIPTION)
            .url(DEFAULT_URL)
            .address(DEFAULT_ADDRESS)
            .price(DEFAULT_PRICE)
            .currency(DEFAULT_CURRENCY)
            .photoURL(DEFAULT_PHOTO_URL)
            .lat(DEFAULT_LAT)
            .lng(DEFAULT_LNG)
            .category(DEFAULT_CATEGORY)
            .tags(DEFAULT_TAGS)
            .editedAt(DEFAULT_EDITED_AT)
            .tier(DEFAULT_TIER)
            .proposedDuration(DEFAULT_PROPOSED_DURATION)
            .favorites(DEFAULT_FAVORITES)
            .officialWebsite(DEFAULT_OFFICIAL_WEBSITE)
            .openingHours(DEFAULT_OPENING_HOURS)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER);
        return feature;
    }

    @Before
    public void initTest() {
        featureRepository.deleteAll();
        feature = createEntity();
    }

    @Test
    public void createFeature() throws Exception {
        int databaseSizeBeforeCreate = featureRepository.findAll().size();

        // Create the Feature
        restFeatureMockMvc.perform(post("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isCreated());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeCreate + 1);
        Feature testFeature = featureList.get(featureList.size() - 1);
        assertThat(testFeature.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testFeature.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testFeature.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testFeature.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testFeature.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFeature.getNameSuffix()).isEqualTo(DEFAULT_NAME_SUFFIX);
        assertThat(testFeature.getOriginalName()).isEqualTo(DEFAULT_ORIGINAL_NAME);
        assertThat(testFeature.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFeature.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testFeature.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testFeature.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testFeature.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testFeature.getPhotoURL()).isEqualTo(DEFAULT_PHOTO_URL);
        assertThat(testFeature.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testFeature.getLng()).isEqualTo(DEFAULT_LNG);
        assertThat(testFeature.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testFeature.getTags()).isEqualTo(DEFAULT_TAGS);
        assertThat(testFeature.getEditedAt()).isEqualTo(DEFAULT_EDITED_AT);
        assertThat(testFeature.getTier()).isEqualTo(DEFAULT_TIER);
        assertThat(testFeature.getProposedDuration()).isEqualTo(DEFAULT_PROPOSED_DURATION);
        assertThat(testFeature.getFavorites()).isEqualTo(DEFAULT_FAVORITES);
        assertThat(testFeature.getOfficialWebsite()).isEqualTo(DEFAULT_OFFICIAL_WEBSITE);
        assertThat(testFeature.getOpeningHours()).isEqualTo(DEFAULT_OPENING_HOURS);
        assertThat(testFeature.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testFeature.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    public void createFeatureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = featureRepository.findAll().size();

        // Create the Feature with an existing ID
        feature.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeatureMockMvc.perform(post("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = featureRepository.findAll().size();
        // set the field null
        feature.setLevel(null);

        // Create the Feature, which fails.

        restFeatureMockMvc.perform(post("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isBadRequest());

        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = featureRepository.findAll().size();
        // set the field null
        feature.setType(null);

        // Create the Feature, which fails.

        restFeatureMockMvc.perform(post("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isBadRequest());

        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkRatingIsRequired() throws Exception {
        int databaseSizeBeforeTest = featureRepository.findAll().size();
        // set the field null
        feature.setRating(null);

        // Create the Feature, which fails.

        restFeatureMockMvc.perform(post("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isBadRequest());

        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = featureRepository.findAll().size();
        // set the field null
        feature.setName(null);

        // Create the Feature, which fails.

        restFeatureMockMvc.perform(post("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isBadRequest());

        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = featureRepository.findAll().size();
        // set the field null
        feature.setDescription(null);

        // Create the Feature, which fails.

        restFeatureMockMvc.perform(post("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isBadRequest());

        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPhotoURLIsRequired() throws Exception {
        int databaseSizeBeforeTest = featureRepository.findAll().size();
        // set the field null
        feature.setPhotoURL(null);

        // Create the Feature, which fails.

        restFeatureMockMvc.perform(post("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isBadRequest());

        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkLatIsRequired() throws Exception {
        int databaseSizeBeforeTest = featureRepository.findAll().size();
        // set the field null
        feature.setLat(null);

        // Create the Feature, which fails.

        restFeatureMockMvc.perform(post("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isBadRequest());

        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkLngIsRequired() throws Exception {
        int databaseSizeBeforeTest = featureRepository.findAll().size();
        // set the field null
        feature.setLng(null);

        // Create the Feature, which fails.

        restFeatureMockMvc.perform(post("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isBadRequest());

        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = featureRepository.findAll().size();
        // set the field null
        feature.setCategory(null);

        // Create the Feature, which fails.

        restFeatureMockMvc.perform(post("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isBadRequest());

        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkProposedDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = featureRepository.findAll().size();
        // set the field null
        feature.setProposedDuration(null);

        // Create the Feature, which fails.

        restFeatureMockMvc.perform(post("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isBadRequest());

        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkFavoritesIsRequired() throws Exception {
        int databaseSizeBeforeTest = featureRepository.findAll().size();
        // set the field null
        feature.setFavorites(null);

        // Create the Feature, which fails.

        restFeatureMockMvc.perform(post("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isBadRequest());

        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllFeatures() throws Exception {
        // Initialize the database
        featureRepository.save(feature);

        // Get all the featureList
        restFeatureMockMvc.perform(get("/api/features?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feature.getId())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].nameSuffix").value(hasItem(DEFAULT_NAME_SUFFIX.toString())))
            .andExpect(jsonPath("$.[*].originalName").value(hasItem(DEFAULT_ORIGINAL_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].photoURL").value(hasItem(DEFAULT_PHOTO_URL.toString())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.doubleValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS.toString())))
            .andExpect(jsonPath("$.[*].editedAt").value(hasItem(sameInstant(DEFAULT_EDITED_AT))))
            .andExpect(jsonPath("$.[*].tier").value(hasItem(DEFAULT_TIER)))
            .andExpect(jsonPath("$.[*].proposedDuration").value(hasItem(DEFAULT_PROPOSED_DURATION)))
            .andExpect(jsonPath("$.[*].favorites").value(hasItem(DEFAULT_FAVORITES)))
            .andExpect(jsonPath("$.[*].officialWebsite").value(hasItem(DEFAULT_OFFICIAL_WEBSITE.toString())))
            .andExpect(jsonPath("$.[*].openingHours").value(hasItem(DEFAULT_OPENING_HOURS.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())));
    }

    @Test
    public void getFeature() throws Exception {
        // Initialize the database
        featureRepository.save(feature);

        // Get the feature
        restFeatureMockMvc.perform(get("/api/features/{id}", feature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(feature.getId()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.doubleValue()))
            .andExpect(jsonPath("$.guid").value(DEFAULT_GUID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.nameSuffix").value(DEFAULT_NAME_SUFFIX.toString()))
            .andExpect(jsonPath("$.originalName").value(DEFAULT_ORIGINAL_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()))
            .andExpect(jsonPath("$.photoURL").value(DEFAULT_PHOTO_URL.toString()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.lng").value(DEFAULT_LNG.doubleValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.tags").value(DEFAULT_TAGS.toString()))
            .andExpect(jsonPath("$.editedAt").value(sameInstant(DEFAULT_EDITED_AT)))
            .andExpect(jsonPath("$.tier").value(DEFAULT_TIER))
            .andExpect(jsonPath("$.proposedDuration").value(DEFAULT_PROPOSED_DURATION))
            .andExpect(jsonPath("$.favorites").value(DEFAULT_FAVORITES))
            .andExpect(jsonPath("$.officialWebsite").value(DEFAULT_OFFICIAL_WEBSITE.toString()))
            .andExpect(jsonPath("$.openingHours").value(DEFAULT_OPENING_HOURS.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()));
    }

    @Test
    public void getNonExistingFeature() throws Exception {
        // Get the feature
        restFeatureMockMvc.perform(get("/api/features/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFeature() throws Exception {
        // Initialize the database
        featureRepository.save(feature);
        int databaseSizeBeforeUpdate = featureRepository.findAll().size();

        // Update the feature
        Feature updatedFeature = featureRepository.findOne(feature.getId());
        updatedFeature
            .level(UPDATED_LEVEL)
            .type(UPDATED_TYPE)
            .rating(UPDATED_RATING)
            .guid(UPDATED_GUID)
            .name(UPDATED_NAME)
            .nameSuffix(UPDATED_NAME_SUFFIX)
            .originalName(UPDATED_ORIGINAL_NAME)
            .description(UPDATED_DESCRIPTION)
            .url(UPDATED_URL)
            .address(UPDATED_ADDRESS)
            .price(UPDATED_PRICE)
            .currency(UPDATED_CURRENCY)
            .photoURL(UPDATED_PHOTO_URL)
            .lat(UPDATED_LAT)
            .lng(UPDATED_LNG)
            .category(UPDATED_CATEGORY)
            .tags(UPDATED_TAGS)
            .editedAt(UPDATED_EDITED_AT)
            .tier(UPDATED_TIER)
            .proposedDuration(UPDATED_PROPOSED_DURATION)
            .favorites(UPDATED_FAVORITES)
            .officialWebsite(UPDATED_OFFICIAL_WEBSITE)
            .openingHours(UPDATED_OPENING_HOURS)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER);

        restFeatureMockMvc.perform(put("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFeature)))
            .andExpect(status().isOk());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeUpdate);
        Feature testFeature = featureList.get(featureList.size() - 1);
        assertThat(testFeature.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testFeature.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFeature.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testFeature.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testFeature.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFeature.getNameSuffix()).isEqualTo(UPDATED_NAME_SUFFIX);
        assertThat(testFeature.getOriginalName()).isEqualTo(UPDATED_ORIGINAL_NAME);
        assertThat(testFeature.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFeature.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testFeature.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testFeature.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testFeature.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testFeature.getPhotoURL()).isEqualTo(UPDATED_PHOTO_URL);
        assertThat(testFeature.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testFeature.getLng()).isEqualTo(UPDATED_LNG);
        assertThat(testFeature.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testFeature.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testFeature.getEditedAt()).isEqualTo(UPDATED_EDITED_AT);
        assertThat(testFeature.getTier()).isEqualTo(UPDATED_TIER);
        assertThat(testFeature.getProposedDuration()).isEqualTo(UPDATED_PROPOSED_DURATION);
        assertThat(testFeature.getFavorites()).isEqualTo(UPDATED_FAVORITES);
        assertThat(testFeature.getOfficialWebsite()).isEqualTo(UPDATED_OFFICIAL_WEBSITE);
        assertThat(testFeature.getOpeningHours()).isEqualTo(UPDATED_OPENING_HOURS);
        assertThat(testFeature.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testFeature.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    public void updateNonExistingFeature() throws Exception {
        int databaseSizeBeforeUpdate = featureRepository.findAll().size();

        // Create the Feature

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFeatureMockMvc.perform(put("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feature)))
            .andExpect(status().isCreated());

        // Validate the Feature in the database
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteFeature() throws Exception {
        // Initialize the database
        featureRepository.save(feature);
        int databaseSizeBeforeDelete = featureRepository.findAll().size();

        // Get the feature
        restFeatureMockMvc.perform(delete("/api/features/{id}", feature.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Feature> featureList = featureRepository.findAll();
        assertThat(featureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Feature.class);
        Feature feature1 = new Feature();
        feature1.setId("id1");
        Feature feature2 = new Feature();
        feature2.setId(feature1.getId());
        assertThat(feature1).isEqualTo(feature2);
        feature2.setId("id2");
        assertThat(feature1).isNotEqualTo(feature2);
        feature1.setId(null);
        assertThat(feature1).isNotEqualTo(feature2);
    }
}
