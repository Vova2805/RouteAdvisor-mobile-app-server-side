package com.routeadvisor.app.web.rest;

import com.routeadvisor.app.RouteAdvisorApp;

import com.routeadvisor.app.domain.DayPlace;
import com.routeadvisor.app.repository.DayPlaceRepository;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DayPlaceResource REST controller.
 *
 * @see DayPlaceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RouteAdvisorApp.class)
public class DayPlaceResourceIntTest {

    private static final String DEFAULT_PLACE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PLACE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TRIP_DAY_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRIP_DAY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLATE_DAY_ID = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_DAY_ID = "BBBBBBBBBB";

    @Autowired
    private DayPlaceRepository dayPlaceRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restDayPlaceMockMvc;

    private DayPlace dayPlace;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DayPlaceResource dayPlaceResource = new DayPlaceResource(dayPlaceRepository);
        this.restDayPlaceMockMvc = MockMvcBuilders.standaloneSetup(dayPlaceResource)
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
    public static DayPlace createEntity() {
        DayPlace dayPlace = new DayPlace()
            .placeId(DEFAULT_PLACE_ID)
            .tripDayId(DEFAULT_TRIP_DAY_ID)
            .templateDayId(DEFAULT_TEMPLATE_DAY_ID);
        return dayPlace;
    }

    @Before
    public void initTest() {
        dayPlaceRepository.deleteAll();
        dayPlace = createEntity();
    }

    @Test
    public void createDayPlace() throws Exception {
        int databaseSizeBeforeCreate = dayPlaceRepository.findAll().size();

        // Create the DayPlace
        restDayPlaceMockMvc.perform(post("/api/day-places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayPlace)))
            .andExpect(status().isCreated());

        // Validate the DayPlace in the database
        List<DayPlace> dayPlaceList = dayPlaceRepository.findAll();
        assertThat(dayPlaceList).hasSize(databaseSizeBeforeCreate + 1);
        DayPlace testDayPlace = dayPlaceList.get(dayPlaceList.size() - 1);
        assertThat(testDayPlace.getPlaceId()).isEqualTo(DEFAULT_PLACE_ID);
        assertThat(testDayPlace.getTripDayId()).isEqualTo(DEFAULT_TRIP_DAY_ID);
        assertThat(testDayPlace.getTemplateDayId()).isEqualTo(DEFAULT_TEMPLATE_DAY_ID);
    }

    @Test
    public void createDayPlaceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dayPlaceRepository.findAll().size();

        // Create the DayPlace with an existing ID
        dayPlace.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restDayPlaceMockMvc.perform(post("/api/day-places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayPlace)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DayPlace> dayPlaceList = dayPlaceRepository.findAll();
        assertThat(dayPlaceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkPlaceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = dayPlaceRepository.findAll().size();
        // set the field null
        dayPlace.setPlaceId(null);

        // Create the DayPlace, which fails.

        restDayPlaceMockMvc.perform(post("/api/day-places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayPlace)))
            .andExpect(status().isBadRequest());

        List<DayPlace> dayPlaceList = dayPlaceRepository.findAll();
        assertThat(dayPlaceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllDayPlaces() throws Exception {
        // Initialize the database
        dayPlaceRepository.save(dayPlace);

        // Get all the dayPlaceList
        restDayPlaceMockMvc.perform(get("/api/day-places?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dayPlace.getId())))
            .andExpect(jsonPath("$.[*].placeId").value(hasItem(DEFAULT_PLACE_ID.toString())))
            .andExpect(jsonPath("$.[*].tripDayId").value(hasItem(DEFAULT_TRIP_DAY_ID.toString())))
            .andExpect(jsonPath("$.[*].templateDayId").value(hasItem(DEFAULT_TEMPLATE_DAY_ID.toString())));
    }

    @Test
    public void getDayPlace() throws Exception {
        // Initialize the database
        dayPlaceRepository.save(dayPlace);

        // Get the dayPlace
        restDayPlaceMockMvc.perform(get("/api/day-places/{id}", dayPlace.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dayPlace.getId()))
            .andExpect(jsonPath("$.placeId").value(DEFAULT_PLACE_ID.toString()))
            .andExpect(jsonPath("$.tripDayId").value(DEFAULT_TRIP_DAY_ID.toString()))
            .andExpect(jsonPath("$.templateDayId").value(DEFAULT_TEMPLATE_DAY_ID.toString()));
    }

    @Test
    public void getNonExistingDayPlace() throws Exception {
        // Get the dayPlace
        restDayPlaceMockMvc.perform(get("/api/day-places/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDayPlace() throws Exception {
        // Initialize the database
        dayPlaceRepository.save(dayPlace);
        int databaseSizeBeforeUpdate = dayPlaceRepository.findAll().size();

        // Update the dayPlace
        DayPlace updatedDayPlace = dayPlaceRepository.findOne(dayPlace.getId());
        updatedDayPlace
            .placeId(UPDATED_PLACE_ID)
            .tripDayId(UPDATED_TRIP_DAY_ID)
            .templateDayId(UPDATED_TEMPLATE_DAY_ID);

        restDayPlaceMockMvc.perform(put("/api/day-places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDayPlace)))
            .andExpect(status().isOk());

        // Validate the DayPlace in the database
        List<DayPlace> dayPlaceList = dayPlaceRepository.findAll();
        assertThat(dayPlaceList).hasSize(databaseSizeBeforeUpdate);
        DayPlace testDayPlace = dayPlaceList.get(dayPlaceList.size() - 1);
        assertThat(testDayPlace.getPlaceId()).isEqualTo(UPDATED_PLACE_ID);
        assertThat(testDayPlace.getTripDayId()).isEqualTo(UPDATED_TRIP_DAY_ID);
        assertThat(testDayPlace.getTemplateDayId()).isEqualTo(UPDATED_TEMPLATE_DAY_ID);
    }

    @Test
    public void updateNonExistingDayPlace() throws Exception {
        int databaseSizeBeforeUpdate = dayPlaceRepository.findAll().size();

        // Create the DayPlace

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDayPlaceMockMvc.perform(put("/api/day-places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayPlace)))
            .andExpect(status().isCreated());

        // Validate the DayPlace in the database
        List<DayPlace> dayPlaceList = dayPlaceRepository.findAll();
        assertThat(dayPlaceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteDayPlace() throws Exception {
        // Initialize the database
        dayPlaceRepository.save(dayPlace);
        int databaseSizeBeforeDelete = dayPlaceRepository.findAll().size();

        // Get the dayPlace
        restDayPlaceMockMvc.perform(delete("/api/day-places/{id}", dayPlace.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DayPlace> dayPlaceList = dayPlaceRepository.findAll();
        assertThat(dayPlaceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DayPlace.class);
        DayPlace dayPlace1 = new DayPlace();
        dayPlace1.setId("id1");
        DayPlace dayPlace2 = new DayPlace();
        dayPlace2.setId(dayPlace1.getId());
        assertThat(dayPlace1).isEqualTo(dayPlace2);
        dayPlace2.setId("id2");
        assertThat(dayPlace1).isNotEqualTo(dayPlace2);
        dayPlace1.setId(null);
        assertThat(dayPlace1).isNotEqualTo(dayPlace2);
    }
}
