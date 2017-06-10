package com.routeadvisor.app.web.rest;

import com.routeadvisor.app.RouteAdvisorApp;

import com.routeadvisor.app.domain.TripDay;
import com.routeadvisor.app.repository.TripDayRepository;
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

/**
 * Test class for the TripDayResource REST controller.
 *
 * @see TripDayResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RouteAdvisorApp.class)
public class TripDayResourceIntTest {

    private static final String DEFAULT_TRIP_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRIP_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Float DEFAULT_HOURS = 1F;
    private static final Float UPDATED_HOURS = 2F;

    private static final Float DEFAULT_LENGTH = 1F;
    private static final Float UPDATED_LENGTH = 2F;

    private static final String DEFAULT_ORIGIN_ID = "AAAAAAAAAA";
    private static final String UPDATED_ORIGIN_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_PLACES_COUNT = 1;
    private static final Integer UPDATED_PLACES_COUNT = 2;

    private static final String DEFAULT_PHOTO_URL = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_URL = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGES = "AAAAAAAAAA";
    private static final String UPDATED_IMAGES = "BBBBBBBBBB";

    private static final String DEFAULT_AUDIOS = "AAAAAAAAAA";
    private static final String UPDATED_AUDIOS = "BBBBBBBBBB";

    private static final String DEFAULT_VIDEOS = "AAAAAAAAAA";
    private static final String UPDATED_VIDEOS = "BBBBBBBBBB";

    @Autowired
    private TripDayRepository tripDayRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restTripDayMockMvc;

    private TripDay tripDay;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TripDayResource tripDayResource = new TripDayResource(tripDayRepository);
        this.restTripDayMockMvc = MockMvcBuilders.standaloneSetup(tripDayResource)
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
    public static TripDay createEntity() {
        TripDay tripDay = new TripDay()
            .tripId(DEFAULT_TRIP_ID)
            .date(DEFAULT_DATE)
            .hours(DEFAULT_HOURS)
            .length(DEFAULT_LENGTH)
            .originId(DEFAULT_ORIGIN_ID)
            .destinationId(DEFAULT_DESTINATION_ID)
            .placesCount(DEFAULT_PLACES_COUNT)
            .photoURL(DEFAULT_PHOTO_URL)
            .images(DEFAULT_IMAGES)
            .audios(DEFAULT_AUDIOS)
            .videos(DEFAULT_VIDEOS);
        return tripDay;
    }

    @Before
    public void initTest() {
        tripDayRepository.deleteAll();
        tripDay = createEntity();
    }

    @Test
    public void createTripDay() throws Exception {
        int databaseSizeBeforeCreate = tripDayRepository.findAll().size();

        // Create the TripDay
        restTripDayMockMvc.perform(post("/api/trip-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripDay)))
            .andExpect(status().isCreated());

        // Validate the TripDay in the database
        List<TripDay> tripDayList = tripDayRepository.findAll();
        assertThat(tripDayList).hasSize(databaseSizeBeforeCreate + 1);
        TripDay testTripDay = tripDayList.get(tripDayList.size() - 1);
        assertThat(testTripDay.getTripId()).isEqualTo(DEFAULT_TRIP_ID);
        assertThat(testTripDay.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTripDay.getHours()).isEqualTo(DEFAULT_HOURS);
        assertThat(testTripDay.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testTripDay.getOriginId()).isEqualTo(DEFAULT_ORIGIN_ID);
        assertThat(testTripDay.getDestinationId()).isEqualTo(DEFAULT_DESTINATION_ID);
        assertThat(testTripDay.getPlacesCount()).isEqualTo(DEFAULT_PLACES_COUNT);
        assertThat(testTripDay.getPhotoURL()).isEqualTo(DEFAULT_PHOTO_URL);
        assertThat(testTripDay.getImages()).isEqualTo(DEFAULT_IMAGES);
        assertThat(testTripDay.getAudios()).isEqualTo(DEFAULT_AUDIOS);
        assertThat(testTripDay.getVideos()).isEqualTo(DEFAULT_VIDEOS);
    }

    @Test
    public void createTripDayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tripDayRepository.findAll().size();

        // Create the TripDay with an existing ID
        tripDay.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restTripDayMockMvc.perform(post("/api/trip-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripDay)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TripDay> tripDayList = tripDayRepository.findAll();
        assertThat(tripDayList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkTripIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripDayRepository.findAll().size();
        // set the field null
        tripDay.setTripId(null);

        // Create the TripDay, which fails.

        restTripDayMockMvc.perform(post("/api/trip-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripDay)))
            .andExpect(status().isBadRequest());

        List<TripDay> tripDayList = tripDayRepository.findAll();
        assertThat(tripDayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripDayRepository.findAll().size();
        // set the field null
        tripDay.setDate(null);

        // Create the TripDay, which fails.

        restTripDayMockMvc.perform(post("/api/trip-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripDay)))
            .andExpect(status().isBadRequest());

        List<TripDay> tripDayList = tripDayRepository.findAll();
        assertThat(tripDayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkHoursIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripDayRepository.findAll().size();
        // set the field null
        tripDay.setHours(null);

        // Create the TripDay, which fails.

        restTripDayMockMvc.perform(post("/api/trip-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripDay)))
            .andExpect(status().isBadRequest());

        List<TripDay> tripDayList = tripDayRepository.findAll();
        assertThat(tripDayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkOriginIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripDayRepository.findAll().size();
        // set the field null
        tripDay.setOriginId(null);

        // Create the TripDay, which fails.

        restTripDayMockMvc.perform(post("/api/trip-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripDay)))
            .andExpect(status().isBadRequest());

        List<TripDay> tripDayList = tripDayRepository.findAll();
        assertThat(tripDayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPlacesCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripDayRepository.findAll().size();
        // set the field null
        tripDay.setPlacesCount(null);

        // Create the TripDay, which fails.

        restTripDayMockMvc.perform(post("/api/trip-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripDay)))
            .andExpect(status().isBadRequest());

        List<TripDay> tripDayList = tripDayRepository.findAll();
        assertThat(tripDayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPhotoURLIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripDayRepository.findAll().size();
        // set the field null
        tripDay.setPhotoURL(null);

        // Create the TripDay, which fails.

        restTripDayMockMvc.perform(post("/api/trip-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripDay)))
            .andExpect(status().isBadRequest());

        List<TripDay> tripDayList = tripDayRepository.findAll();
        assertThat(tripDayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllTripDays() throws Exception {
        // Initialize the database
        tripDayRepository.save(tripDay);

        // Get all the tripDayList
        restTripDayMockMvc.perform(get("/api/trip-days?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tripDay.getId())))
            .andExpect(jsonPath("$.[*].tripId").value(hasItem(DEFAULT_TRIP_ID.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].hours").value(hasItem(DEFAULT_HOURS.doubleValue())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.doubleValue())))
            .andExpect(jsonPath("$.[*].originId").value(hasItem(DEFAULT_ORIGIN_ID.toString())))
            .andExpect(jsonPath("$.[*].destinationId").value(hasItem(DEFAULT_DESTINATION_ID.toString())))
            .andExpect(jsonPath("$.[*].placesCount").value(hasItem(DEFAULT_PLACES_COUNT)))
            .andExpect(jsonPath("$.[*].photoURL").value(hasItem(DEFAULT_PHOTO_URL.toString())))
            .andExpect(jsonPath("$.[*].images").value(hasItem(DEFAULT_IMAGES.toString())))
            .andExpect(jsonPath("$.[*].audios").value(hasItem(DEFAULT_AUDIOS.toString())))
            .andExpect(jsonPath("$.[*].videos").value(hasItem(DEFAULT_VIDEOS.toString())));
    }

    @Test
    public void getTripDay() throws Exception {
        // Initialize the database
        tripDayRepository.save(tripDay);

        // Get the tripDay
        restTripDayMockMvc.perform(get("/api/trip-days/{id}", tripDay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tripDay.getId()))
            .andExpect(jsonPath("$.tripId").value(DEFAULT_TRIP_ID.toString()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.hours").value(DEFAULT_HOURS.doubleValue()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.doubleValue()))
            .andExpect(jsonPath("$.originId").value(DEFAULT_ORIGIN_ID.toString()))
            .andExpect(jsonPath("$.destinationId").value(DEFAULT_DESTINATION_ID.toString()))
            .andExpect(jsonPath("$.placesCount").value(DEFAULT_PLACES_COUNT))
            .andExpect(jsonPath("$.photoURL").value(DEFAULT_PHOTO_URL.toString()))
            .andExpect(jsonPath("$.images").value(DEFAULT_IMAGES.toString()))
            .andExpect(jsonPath("$.audios").value(DEFAULT_AUDIOS.toString()))
            .andExpect(jsonPath("$.videos").value(DEFAULT_VIDEOS.toString()));
    }

    @Test
    public void getNonExistingTripDay() throws Exception {
        // Get the tripDay
        restTripDayMockMvc.perform(get("/api/trip-days/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTripDay() throws Exception {
        // Initialize the database
        tripDayRepository.save(tripDay);
        int databaseSizeBeforeUpdate = tripDayRepository.findAll().size();

        // Update the tripDay
        TripDay updatedTripDay = tripDayRepository.findOne(tripDay.getId());
        updatedTripDay
            .tripId(UPDATED_TRIP_ID)
            .date(UPDATED_DATE)
            .hours(UPDATED_HOURS)
            .length(UPDATED_LENGTH)
            .originId(UPDATED_ORIGIN_ID)
            .destinationId(UPDATED_DESTINATION_ID)
            .placesCount(UPDATED_PLACES_COUNT)
            .photoURL(UPDATED_PHOTO_URL)
            .images(UPDATED_IMAGES)
            .audios(UPDATED_AUDIOS)
            .videos(UPDATED_VIDEOS);

        restTripDayMockMvc.perform(put("/api/trip-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTripDay)))
            .andExpect(status().isOk());

        // Validate the TripDay in the database
        List<TripDay> tripDayList = tripDayRepository.findAll();
        assertThat(tripDayList).hasSize(databaseSizeBeforeUpdate);
        TripDay testTripDay = tripDayList.get(tripDayList.size() - 1);
        assertThat(testTripDay.getTripId()).isEqualTo(UPDATED_TRIP_ID);
        assertThat(testTripDay.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTripDay.getHours()).isEqualTo(UPDATED_HOURS);
        assertThat(testTripDay.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testTripDay.getOriginId()).isEqualTo(UPDATED_ORIGIN_ID);
        assertThat(testTripDay.getDestinationId()).isEqualTo(UPDATED_DESTINATION_ID);
        assertThat(testTripDay.getPlacesCount()).isEqualTo(UPDATED_PLACES_COUNT);
        assertThat(testTripDay.getPhotoURL()).isEqualTo(UPDATED_PHOTO_URL);
        assertThat(testTripDay.getImages()).isEqualTo(UPDATED_IMAGES);
        assertThat(testTripDay.getAudios()).isEqualTo(UPDATED_AUDIOS);
        assertThat(testTripDay.getVideos()).isEqualTo(UPDATED_VIDEOS);
    }

    @Test
    public void updateNonExistingTripDay() throws Exception {
        int databaseSizeBeforeUpdate = tripDayRepository.findAll().size();

        // Create the TripDay

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTripDayMockMvc.perform(put("/api/trip-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripDay)))
            .andExpect(status().isCreated());

        // Validate the TripDay in the database
        List<TripDay> tripDayList = tripDayRepository.findAll();
        assertThat(tripDayList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteTripDay() throws Exception {
        // Initialize the database
        tripDayRepository.save(tripDay);
        int databaseSizeBeforeDelete = tripDayRepository.findAll().size();

        // Get the tripDay
        restTripDayMockMvc.perform(delete("/api/trip-days/{id}", tripDay.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TripDay> tripDayList = tripDayRepository.findAll();
        assertThat(tripDayList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TripDay.class);
        TripDay tripDay1 = new TripDay();
        tripDay1.setId("id1");
        TripDay tripDay2 = new TripDay();
        tripDay2.setId(tripDay1.getId());
        assertThat(tripDay1).isEqualTo(tripDay2);
        tripDay2.setId("id2");
        assertThat(tripDay1).isNotEqualTo(tripDay2);
        tripDay1.setId(null);
        assertThat(tripDay1).isNotEqualTo(tripDay2);
    }
}
