package com.routeadvisor.app.web.rest;

import com.routeadvisor.app.RouteAdvisorApp;

import com.routeadvisor.app.domain.Trip;
import com.routeadvisor.app.repository.TripRepository;
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
 * Test class for the TripResource REST controller.
 *
 * @see TripResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RouteAdvisorApp.class)
public class TripResourceIntTest {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_ID = "AAAAAAAAAA";
    private static final String UPDATED_CITY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_URL = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_URL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Float DEFAULT_HOURS = 1F;
    private static final Float UPDATED_HOURS = 2F;

    private static final Float DEFAULT_COST = 1F;
    private static final Float UPDATED_COST = 2F;

    private static final Float DEFAULT_LENGTH = 1F;
    private static final Float UPDATED_LENGTH = 2F;

    private static final Integer DEFAULT_NOTES_COUNT = 0;
    private static final Integer UPDATED_NOTES_COUNT = 1;

    private static final String DEFAULT_ARRIVAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_ARRIVAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOMMODATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACCOMMODATION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_AUDIOS_IDS = "AAAAAAAAAA";
    private static final String UPDATED_AUDIOS_IDS = "BBBBBBBBBB";

    private static final String DEFAULT_VIDEOS_IDS = "AAAAAAAAAA";
    private static final String UPDATED_VIDEOS_IDS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DATES_KNOWN = false;
    private static final Boolean UPDATED_DATES_KNOWN = true;

    private static final Boolean DEFAULT_ACCESSIBLE = false;
    private static final Boolean UPDATED_ACCESSIBLE = true;

    private static final Boolean DEFAULT_OFFLINE = false;
    private static final Boolean UPDATED_OFFLINE = true;

    private static final Boolean DEFAULT_TRASH = false;
    private static final Boolean UPDATED_TRASH = true;

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restTripMockMvc;

    private Trip trip;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TripResource tripResource = new TripResource(tripRepository);
        this.restTripMockMvc = MockMvcBuilders.standaloneSetup(tripResource)
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
    public static Trip createEntity() {
        Trip trip = new Trip()
            .userId(DEFAULT_USER_ID)
            .cityId(DEFAULT_CITY_ID)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .photoURL(DEFAULT_PHOTO_URL)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .hours(DEFAULT_HOURS)
            .cost(DEFAULT_COST)
            .length(DEFAULT_LENGTH)
            .notesCount(DEFAULT_NOTES_COUNT)
            .arrivalId(DEFAULT_ARRIVAL_ID)
            .accommodationId(DEFAULT_ACCOMMODATION_ID)
            .audiosIds(DEFAULT_AUDIOS_IDS)
            .videosIds(DEFAULT_VIDEOS_IDS)
            .datesKnown(DEFAULT_DATES_KNOWN)
            .accessible(DEFAULT_ACCESSIBLE)
            .offline(DEFAULT_OFFLINE)
            .trash(DEFAULT_TRASH)
            .token(DEFAULT_TOKEN);
        return trip;
    }

    @Before
    public void initTest() {
        tripRepository.deleteAll();
        trip = createEntity();
    }

    @Test
    public void createTrip() throws Exception {
        int databaseSizeBeforeCreate = tripRepository.findAll().size();

        // Create the Trip
        restTripMockMvc.perform(post("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trip)))
            .andExpect(status().isCreated());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeCreate + 1);
        Trip testTrip = tripList.get(tripList.size() - 1);
        assertThat(testTrip.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testTrip.getCityId()).isEqualTo(DEFAULT_CITY_ID);
        assertThat(testTrip.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTrip.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTrip.getPhotoURL()).isEqualTo(DEFAULT_PHOTO_URL);
        assertThat(testTrip.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTrip.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTrip.getHours()).isEqualTo(DEFAULT_HOURS);
        assertThat(testTrip.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testTrip.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testTrip.getNotesCount()).isEqualTo(DEFAULT_NOTES_COUNT);
        assertThat(testTrip.getArrivalId()).isEqualTo(DEFAULT_ARRIVAL_ID);
        assertThat(testTrip.getAccommodationId()).isEqualTo(DEFAULT_ACCOMMODATION_ID);
        assertThat(testTrip.getAudiosIds()).isEqualTo(DEFAULT_AUDIOS_IDS);
        assertThat(testTrip.getVideosIds()).isEqualTo(DEFAULT_VIDEOS_IDS);
        assertThat(testTrip.isDatesKnown()).isEqualTo(DEFAULT_DATES_KNOWN);
        assertThat(testTrip.isAccessible()).isEqualTo(DEFAULT_ACCESSIBLE);
        assertThat(testTrip.isOffline()).isEqualTo(DEFAULT_OFFLINE);
        assertThat(testTrip.isTrash()).isEqualTo(DEFAULT_TRASH);
        assertThat(testTrip.getToken()).isEqualTo(DEFAULT_TOKEN);
    }

    @Test
    public void createTripWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tripRepository.findAll().size();

        // Create the Trip with an existing ID
        trip.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restTripMockMvc.perform(post("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trip)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setUserId(null);

        // Create the Trip, which fails.

        restTripMockMvc.perform(post("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trip)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCityIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setCityId(null);

        // Create the Trip, which fails.

        restTripMockMvc.perform(post("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trip)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setName(null);

        // Create the Trip, which fails.

        restTripMockMvc.perform(post("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trip)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPhotoURLIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setPhotoURL(null);

        // Create the Trip, which fails.

        restTripMockMvc.perform(post("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trip)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkHoursIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setHours(null);

        // Create the Trip, which fails.

        restTripMockMvc.perform(post("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trip)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkLengthIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setLength(null);

        // Create the Trip, which fails.

        restTripMockMvc.perform(post("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trip)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAccessibleIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setAccessible(null);

        // Create the Trip, which fails.

        restTripMockMvc.perform(post("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trip)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkOfflineIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setOffline(null);

        // Create the Trip, which fails.

        restTripMockMvc.perform(post("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trip)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllTrips() throws Exception {
        // Initialize the database
        tripRepository.save(trip);

        // Get all the tripList
        restTripMockMvc.perform(get("/api/trips?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trip.getId())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].cityId").value(hasItem(DEFAULT_CITY_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].photoURL").value(hasItem(DEFAULT_PHOTO_URL.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].hours").value(hasItem(DEFAULT_HOURS.doubleValue())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.doubleValue())))
            .andExpect(jsonPath("$.[*].notesCount").value(hasItem(DEFAULT_NOTES_COUNT)))
            .andExpect(jsonPath("$.[*].arrivalId").value(hasItem(DEFAULT_ARRIVAL_ID.toString())))
            .andExpect(jsonPath("$.[*].accommodationId").value(hasItem(DEFAULT_ACCOMMODATION_ID.toString())))
            .andExpect(jsonPath("$.[*].audiosIds").value(hasItem(DEFAULT_AUDIOS_IDS.toString())))
            .andExpect(jsonPath("$.[*].videosIds").value(hasItem(DEFAULT_VIDEOS_IDS.toString())))
            .andExpect(jsonPath("$.[*].datesKnown").value(hasItem(DEFAULT_DATES_KNOWN.booleanValue())))
            .andExpect(jsonPath("$.[*].accessible").value(hasItem(DEFAULT_ACCESSIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].offline").value(hasItem(DEFAULT_OFFLINE.booleanValue())))
            .andExpect(jsonPath("$.[*].trash").value(hasItem(DEFAULT_TRASH.booleanValue())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())));
    }

    @Test
    public void getTrip() throws Exception {
        // Initialize the database
        tripRepository.save(trip);

        // Get the trip
        restTripMockMvc.perform(get("/api/trips/{id}", trip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trip.getId()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.cityId").value(DEFAULT_CITY_ID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.photoURL").value(DEFAULT_PHOTO_URL.toString()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.hours").value(DEFAULT_HOURS.doubleValue()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.doubleValue()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.doubleValue()))
            .andExpect(jsonPath("$.notesCount").value(DEFAULT_NOTES_COUNT))
            .andExpect(jsonPath("$.arrivalId").value(DEFAULT_ARRIVAL_ID.toString()))
            .andExpect(jsonPath("$.accommodationId").value(DEFAULT_ACCOMMODATION_ID.toString()))
            .andExpect(jsonPath("$.audiosIds").value(DEFAULT_AUDIOS_IDS.toString()))
            .andExpect(jsonPath("$.videosIds").value(DEFAULT_VIDEOS_IDS.toString()))
            .andExpect(jsonPath("$.datesKnown").value(DEFAULT_DATES_KNOWN.booleanValue()))
            .andExpect(jsonPath("$.accessible").value(DEFAULT_ACCESSIBLE.booleanValue()))
            .andExpect(jsonPath("$.offline").value(DEFAULT_OFFLINE.booleanValue()))
            .andExpect(jsonPath("$.trash").value(DEFAULT_TRASH.booleanValue()))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN.toString()));
    }

    @Test
    public void getNonExistingTrip() throws Exception {
        // Get the trip
        restTripMockMvc.perform(get("/api/trips/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTrip() throws Exception {
        // Initialize the database
        tripRepository.save(trip);
        int databaseSizeBeforeUpdate = tripRepository.findAll().size();

        // Update the trip
        Trip updatedTrip = tripRepository.findOne(trip.getId());
        updatedTrip
            .userId(UPDATED_USER_ID)
            .cityId(UPDATED_CITY_ID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .photoURL(UPDATED_PHOTO_URL)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .hours(UPDATED_HOURS)
            .cost(UPDATED_COST)
            .length(UPDATED_LENGTH)
            .notesCount(UPDATED_NOTES_COUNT)
            .arrivalId(UPDATED_ARRIVAL_ID)
            .accommodationId(UPDATED_ACCOMMODATION_ID)
            .audiosIds(UPDATED_AUDIOS_IDS)
            .videosIds(UPDATED_VIDEOS_IDS)
            .datesKnown(UPDATED_DATES_KNOWN)
            .accessible(UPDATED_ACCESSIBLE)
            .offline(UPDATED_OFFLINE)
            .trash(UPDATED_TRASH)
            .token(UPDATED_TOKEN);

        restTripMockMvc.perform(put("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrip)))
            .andExpect(status().isOk());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeUpdate);
        Trip testTrip = tripList.get(tripList.size() - 1);
        assertThat(testTrip.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testTrip.getCityId()).isEqualTo(UPDATED_CITY_ID);
        assertThat(testTrip.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTrip.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTrip.getPhotoURL()).isEqualTo(UPDATED_PHOTO_URL);
        assertThat(testTrip.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTrip.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTrip.getHours()).isEqualTo(UPDATED_HOURS);
        assertThat(testTrip.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testTrip.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testTrip.getNotesCount()).isEqualTo(UPDATED_NOTES_COUNT);
        assertThat(testTrip.getArrivalId()).isEqualTo(UPDATED_ARRIVAL_ID);
        assertThat(testTrip.getAccommodationId()).isEqualTo(UPDATED_ACCOMMODATION_ID);
        assertThat(testTrip.getAudiosIds()).isEqualTo(UPDATED_AUDIOS_IDS);
        assertThat(testTrip.getVideosIds()).isEqualTo(UPDATED_VIDEOS_IDS);
        assertThat(testTrip.isDatesKnown()).isEqualTo(UPDATED_DATES_KNOWN);
        assertThat(testTrip.isAccessible()).isEqualTo(UPDATED_ACCESSIBLE);
        assertThat(testTrip.isOffline()).isEqualTo(UPDATED_OFFLINE);
        assertThat(testTrip.isTrash()).isEqualTo(UPDATED_TRASH);
        assertThat(testTrip.getToken()).isEqualTo(UPDATED_TOKEN);
    }

    @Test
    public void updateNonExistingTrip() throws Exception {
        int databaseSizeBeforeUpdate = tripRepository.findAll().size();

        // Create the Trip

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTripMockMvc.perform(put("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trip)))
            .andExpect(status().isCreated());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteTrip() throws Exception {
        // Initialize the database
        tripRepository.save(trip);
        int databaseSizeBeforeDelete = tripRepository.findAll().size();

        // Get the trip
        restTripMockMvc.perform(delete("/api/trips/{id}", trip.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trip.class);
        Trip trip1 = new Trip();
        trip1.setId("id1");
        Trip trip2 = new Trip();
        trip2.setId(trip1.getId());
        assertThat(trip1).isEqualTo(trip2);
        trip2.setId("id2");
        assertThat(trip1).isNotEqualTo(trip2);
        trip1.setId(null);
        assertThat(trip1).isNotEqualTo(trip2);
    }
}
