package com.routeadvisor.app.web.rest;

import com.routeadvisor.app.RouteAdvisorApp;

import com.routeadvisor.app.domain.Place;
import com.routeadvisor.app.repository.PlaceRepository;
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

import com.routeadvisor.app.domain.enumeration.PlaceLevel;
import com.routeadvisor.app.domain.enumeration.PlaceType;
import com.routeadvisor.app.domain.enumeration.Category;
/**
 * Test class for the PlaceResource REST controller.
 *
 * @see PlaceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RouteAdvisorApp.class)
public class PlaceResourceIntTest {

    private static final String DEFAULT_CITY_ID = "AAAAAAAAAA";
    private static final String UPDATED_CITY_ID = "BBBBBBBBBB";

    private static final PlaceLevel DEFAULT_LEVEL = PlaceLevel.COUNTRY;
    private static final PlaceLevel UPDATED_LEVEL = PlaceLevel.CITY;

    private static final PlaceType DEFAULT_TYPE = PlaceType.CITY;
    private static final PlaceType UPDATED_TYPE = PlaceType.PLACE;

    private static final Float DEFAULT_RATING = 0F;
    private static final Float UPDATED_RATING = 1F;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;

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
    private PlaceRepository placeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restPlaceMockMvc;

    private Place place;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PlaceResource placeResource = new PlaceResource(placeRepository);
        this.restPlaceMockMvc = MockMvcBuilders.standaloneSetup(placeResource)
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
    public static Place createEntity() {
        Place place = new Place()
            .cityId(DEFAULT_CITY_ID)
            .level(DEFAULT_LEVEL)
            .type(DEFAULT_TYPE)
            .rating(DEFAULT_RATING)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .address(DEFAULT_ADDRESS)
            .price(DEFAULT_PRICE)
            .currency(DEFAULT_CURRENCY)
            .photoURL(DEFAULT_PHOTO_URL)
            .lat(DEFAULT_LAT)
            .lng(DEFAULT_LNG)
            .category(DEFAULT_CATEGORY)
            .tags(DEFAULT_TAGS)
            .editedAt(DEFAULT_EDITED_AT)
            .proposedDuration(DEFAULT_PROPOSED_DURATION)
            .favorites(DEFAULT_FAVORITES)
            .officialWebsite(DEFAULT_OFFICIAL_WEBSITE)
            .openingHours(DEFAULT_OPENING_HOURS)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER);
        return place;
    }

    @Before
    public void initTest() {
        placeRepository.deleteAll();
        place = createEntity();
    }

    @Test
    public void createPlace() throws Exception {
        int databaseSizeBeforeCreate = placeRepository.findAll().size();

        // Create the Place
        restPlaceMockMvc.perform(post("/api/places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(place)))
            .andExpect(status().isCreated());

        // Validate the Place in the database
        List<Place> placeList = placeRepository.findAll();
        assertThat(placeList).hasSize(databaseSizeBeforeCreate + 1);
        Place testPlace = placeList.get(placeList.size() - 1);
        assertThat(testPlace.getCityId()).isEqualTo(DEFAULT_CITY_ID);
        assertThat(testPlace.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testPlace.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPlace.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testPlace.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlace.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPlace.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPlace.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPlace.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testPlace.getPhotoURL()).isEqualTo(DEFAULT_PHOTO_URL);
        assertThat(testPlace.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testPlace.getLng()).isEqualTo(DEFAULT_LNG);
        assertThat(testPlace.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testPlace.getTags()).isEqualTo(DEFAULT_TAGS);
        assertThat(testPlace.getEditedAt()).isEqualTo(DEFAULT_EDITED_AT);
        assertThat(testPlace.getProposedDuration()).isEqualTo(DEFAULT_PROPOSED_DURATION);
        assertThat(testPlace.getFavorites()).isEqualTo(DEFAULT_FAVORITES);
        assertThat(testPlace.getOfficialWebsite()).isEqualTo(DEFAULT_OFFICIAL_WEBSITE);
        assertThat(testPlace.getOpeningHours()).isEqualTo(DEFAULT_OPENING_HOURS);
        assertThat(testPlace.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPlace.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    public void createPlaceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = placeRepository.findAll().size();

        // Create the Place with an existing ID
        place.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlaceMockMvc.perform(post("/api/places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(place)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Place> placeList = placeRepository.findAll();
        assertThat(placeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkCityIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = placeRepository.findAll().size();
        // set the field null
        place.setCityId(null);

        // Create the Place, which fails.

        restPlaceMockMvc.perform(post("/api/places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(place)))
            .andExpect(status().isBadRequest());

        List<Place> placeList = placeRepository.findAll();
        assertThat(placeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = placeRepository.findAll().size();
        // set the field null
        place.setLevel(null);

        // Create the Place, which fails.

        restPlaceMockMvc.perform(post("/api/places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(place)))
            .andExpect(status().isBadRequest());

        List<Place> placeList = placeRepository.findAll();
        assertThat(placeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = placeRepository.findAll().size();
        // set the field null
        place.setType(null);

        // Create the Place, which fails.

        restPlaceMockMvc.perform(post("/api/places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(place)))
            .andExpect(status().isBadRequest());

        List<Place> placeList = placeRepository.findAll();
        assertThat(placeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkRatingIsRequired() throws Exception {
        int databaseSizeBeforeTest = placeRepository.findAll().size();
        // set the field null
        place.setRating(null);

        // Create the Place, which fails.

        restPlaceMockMvc.perform(post("/api/places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(place)))
            .andExpect(status().isBadRequest());

        List<Place> placeList = placeRepository.findAll();
        assertThat(placeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = placeRepository.findAll().size();
        // set the field null
        place.setName(null);

        // Create the Place, which fails.

        restPlaceMockMvc.perform(post("/api/places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(place)))
            .andExpect(status().isBadRequest());

        List<Place> placeList = placeRepository.findAll();
        assertThat(placeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = placeRepository.findAll().size();
        // set the field null
        place.setDescription(null);

        // Create the Place, which fails.

        restPlaceMockMvc.perform(post("/api/places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(place)))
            .andExpect(status().isBadRequest());

        List<Place> placeList = placeRepository.findAll();
        assertThat(placeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPhotoURLIsRequired() throws Exception {
        int databaseSizeBeforeTest = placeRepository.findAll().size();
        // set the field null
        place.setPhotoURL(null);

        // Create the Place, which fails.

        restPlaceMockMvc.perform(post("/api/places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(place)))
            .andExpect(status().isBadRequest());

        List<Place> placeList = placeRepository.findAll();
        assertThat(placeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkLatIsRequired() throws Exception {
        int databaseSizeBeforeTest = placeRepository.findAll().size();
        // set the field null
        place.setLat(null);

        // Create the Place, which fails.

        restPlaceMockMvc.perform(post("/api/places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(place)))
            .andExpect(status().isBadRequest());

        List<Place> placeList = placeRepository.findAll();
        assertThat(placeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkLngIsRequired() throws Exception {
        int databaseSizeBeforeTest = placeRepository.findAll().size();
        // set the field null
        place.setLng(null);

        // Create the Place, which fails.

        restPlaceMockMvc.perform(post("/api/places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(place)))
            .andExpect(status().isBadRequest());

        List<Place> placeList = placeRepository.findAll();
        assertThat(placeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = placeRepository.findAll().size();
        // set the field null
        place.setCategory(null);

        // Create the Place, which fails.

        restPlaceMockMvc.perform(post("/api/places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(place)))
            .andExpect(status().isBadRequest());

        List<Place> placeList = placeRepository.findAll();
        assertThat(placeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkProposedDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = placeRepository.findAll().size();
        // set the field null
        place.setProposedDuration(null);

        // Create the Place, which fails.

        restPlaceMockMvc.perform(post("/api/places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(place)))
            .andExpect(status().isBadRequest());

        List<Place> placeList = placeRepository.findAll();
        assertThat(placeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkFavoritesIsRequired() throws Exception {
        int databaseSizeBeforeTest = placeRepository.findAll().size();
        // set the field null
        place.setFavorites(null);

        // Create the Place, which fails.

        restPlaceMockMvc.perform(post("/api/places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(place)))
            .andExpect(status().isBadRequest());

        List<Place> placeList = placeRepository.findAll();
        assertThat(placeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllPlaces() throws Exception {
        // Initialize the database
        placeRepository.save(place);

        // Get all the placeList
        restPlaceMockMvc.perform(get("/api/places?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(place.getId())))
            .andExpect(jsonPath("$.[*].cityId").value(hasItem(DEFAULT_CITY_ID.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].photoURL").value(hasItem(DEFAULT_PHOTO_URL.toString())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.doubleValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS.toString())))
            .andExpect(jsonPath("$.[*].editedAt").value(hasItem(sameInstant(DEFAULT_EDITED_AT))))
            .andExpect(jsonPath("$.[*].proposedDuration").value(hasItem(DEFAULT_PROPOSED_DURATION)))
            .andExpect(jsonPath("$.[*].favorites").value(hasItem(DEFAULT_FAVORITES)))
            .andExpect(jsonPath("$.[*].officialWebsite").value(hasItem(DEFAULT_OFFICIAL_WEBSITE.toString())))
            .andExpect(jsonPath("$.[*].openingHours").value(hasItem(DEFAULT_OPENING_HOURS.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())));
    }

    @Test
    public void getPlace() throws Exception {
        // Initialize the database
        placeRepository.save(place);

        // Get the place
        restPlaceMockMvc.perform(get("/api/places/{id}", place.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(place.getId()))
            .andExpect(jsonPath("$.cityId").value(DEFAULT_CITY_ID.toString()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.doubleValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()))
            .andExpect(jsonPath("$.photoURL").value(DEFAULT_PHOTO_URL.toString()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.lng").value(DEFAULT_LNG.doubleValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.tags").value(DEFAULT_TAGS.toString()))
            .andExpect(jsonPath("$.editedAt").value(sameInstant(DEFAULT_EDITED_AT)))
            .andExpect(jsonPath("$.proposedDuration").value(DEFAULT_PROPOSED_DURATION))
            .andExpect(jsonPath("$.favorites").value(DEFAULT_FAVORITES))
            .andExpect(jsonPath("$.officialWebsite").value(DEFAULT_OFFICIAL_WEBSITE.toString()))
            .andExpect(jsonPath("$.openingHours").value(DEFAULT_OPENING_HOURS.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()));
    }

    @Test
    public void getNonExistingPlace() throws Exception {
        // Get the place
        restPlaceMockMvc.perform(get("/api/places/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePlace() throws Exception {
        // Initialize the database
        placeRepository.save(place);
        int databaseSizeBeforeUpdate = placeRepository.findAll().size();

        // Update the place
        Place updatedPlace = placeRepository.findOne(place.getId());
        updatedPlace
            .cityId(UPDATED_CITY_ID)
            .level(UPDATED_LEVEL)
            .type(UPDATED_TYPE)
            .rating(UPDATED_RATING)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .address(UPDATED_ADDRESS)
            .price(UPDATED_PRICE)
            .currency(UPDATED_CURRENCY)
            .photoURL(UPDATED_PHOTO_URL)
            .lat(UPDATED_LAT)
            .lng(UPDATED_LNG)
            .category(UPDATED_CATEGORY)
            .tags(UPDATED_TAGS)
            .editedAt(UPDATED_EDITED_AT)
            .proposedDuration(UPDATED_PROPOSED_DURATION)
            .favorites(UPDATED_FAVORITES)
            .officialWebsite(UPDATED_OFFICIAL_WEBSITE)
            .openingHours(UPDATED_OPENING_HOURS)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER);

        restPlaceMockMvc.perform(put("/api/places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlace)))
            .andExpect(status().isOk());

        // Validate the Place in the database
        List<Place> placeList = placeRepository.findAll();
        assertThat(placeList).hasSize(databaseSizeBeforeUpdate);
        Place testPlace = placeList.get(placeList.size() - 1);
        assertThat(testPlace.getCityId()).isEqualTo(UPDATED_CITY_ID);
        assertThat(testPlace.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testPlace.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPlace.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testPlace.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlace.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPlace.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPlace.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPlace.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testPlace.getPhotoURL()).isEqualTo(UPDATED_PHOTO_URL);
        assertThat(testPlace.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testPlace.getLng()).isEqualTo(UPDATED_LNG);
        assertThat(testPlace.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testPlace.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testPlace.getEditedAt()).isEqualTo(UPDATED_EDITED_AT);
        assertThat(testPlace.getProposedDuration()).isEqualTo(UPDATED_PROPOSED_DURATION);
        assertThat(testPlace.getFavorites()).isEqualTo(UPDATED_FAVORITES);
        assertThat(testPlace.getOfficialWebsite()).isEqualTo(UPDATED_OFFICIAL_WEBSITE);
        assertThat(testPlace.getOpeningHours()).isEqualTo(UPDATED_OPENING_HOURS);
        assertThat(testPlace.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPlace.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    public void updateNonExistingPlace() throws Exception {
        int databaseSizeBeforeUpdate = placeRepository.findAll().size();

        // Create the Place

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlaceMockMvc.perform(put("/api/places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(place)))
            .andExpect(status().isCreated());

        // Validate the Place in the database
        List<Place> placeList = placeRepository.findAll();
        assertThat(placeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deletePlace() throws Exception {
        // Initialize the database
        placeRepository.save(place);
        int databaseSizeBeforeDelete = placeRepository.findAll().size();

        // Get the place
        restPlaceMockMvc.perform(delete("/api/places/{id}", place.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Place> placeList = placeRepository.findAll();
        assertThat(placeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Place.class);
        Place place1 = new Place();
        place1.setId("id1");
        Place place2 = new Place();
        place2.setId(place1.getId());
        assertThat(place1).isEqualTo(place2);
        place2.setId("id2");
        assertThat(place1).isNotEqualTo(place2);
        place1.setId(null);
        assertThat(place1).isNotEqualTo(place2);
    }
}
