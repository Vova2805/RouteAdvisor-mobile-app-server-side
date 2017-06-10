package com.routeadvisor.app.web.rest;

import com.routeadvisor.app.RouteAdvisorApp;

import com.routeadvisor.app.domain.TemplateDay;
import com.routeadvisor.app.repository.TemplateDayRepository;
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
 * Test class for the TemplateDayResource REST controller.
 *
 * @see TemplateDayResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RouteAdvisorApp.class)
public class TemplateDayResourceIntTest {

    private static final String DEFAULT_TEMPLATE_ID = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Float DEFAULT_HOURS = 1F;
    private static final Float UPDATED_HOURS = 2F;

    private static final Float DEFAULT_LENGTH = 1F;
    private static final Float UPDATED_LENGTH = 2F;

    private static final Integer DEFAULT_PLACES_COUNT = 1;
    private static final Integer UPDATED_PLACES_COUNT = 2;

    private static final String DEFAULT_ORIGIN_ID = "AAAAAAAAAA";
    private static final String UPDATED_ORIGIN_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION_ID = "BBBBBBBBBB";

    @Autowired
    private TemplateDayRepository templateDayRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restTemplateDayMockMvc;

    private TemplateDay templateDay;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TemplateDayResource templateDayResource = new TemplateDayResource(templateDayRepository);
        this.restTemplateDayMockMvc = MockMvcBuilders.standaloneSetup(templateDayResource)
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
    public static TemplateDay createEntity() {
        TemplateDay templateDay = new TemplateDay()
            .templateId(DEFAULT_TEMPLATE_ID)
            .date(DEFAULT_DATE)
            .hours(DEFAULT_HOURS)
            .length(DEFAULT_LENGTH)
            .placesCount(DEFAULT_PLACES_COUNT)
            .originId(DEFAULT_ORIGIN_ID)
            .destinationId(DEFAULT_DESTINATION_ID);
        return templateDay;
    }

    @Before
    public void initTest() {
        templateDayRepository.deleteAll();
        templateDay = createEntity();
    }

    @Test
    public void createTemplateDay() throws Exception {
        int databaseSizeBeforeCreate = templateDayRepository.findAll().size();

        // Create the TemplateDay
        restTemplateDayMockMvc.perform(post("/api/template-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateDay)))
            .andExpect(status().isCreated());

        // Validate the TemplateDay in the database
        List<TemplateDay> templateDayList = templateDayRepository.findAll();
        assertThat(templateDayList).hasSize(databaseSizeBeforeCreate + 1);
        TemplateDay testTemplateDay = templateDayList.get(templateDayList.size() - 1);
        assertThat(testTemplateDay.getTemplateId()).isEqualTo(DEFAULT_TEMPLATE_ID);
        assertThat(testTemplateDay.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTemplateDay.getHours()).isEqualTo(DEFAULT_HOURS);
        assertThat(testTemplateDay.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testTemplateDay.getPlacesCount()).isEqualTo(DEFAULT_PLACES_COUNT);
        assertThat(testTemplateDay.getOriginId()).isEqualTo(DEFAULT_ORIGIN_ID);
        assertThat(testTemplateDay.getDestinationId()).isEqualTo(DEFAULT_DESTINATION_ID);
    }

    @Test
    public void createTemplateDayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = templateDayRepository.findAll().size();

        // Create the TemplateDay with an existing ID
        templateDay.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemplateDayMockMvc.perform(post("/api/template-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateDay)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TemplateDay> templateDayList = templateDayRepository.findAll();
        assertThat(templateDayList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkTemplateIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateDayRepository.findAll().size();
        // set the field null
        templateDay.setTemplateId(null);

        // Create the TemplateDay, which fails.

        restTemplateDayMockMvc.perform(post("/api/template-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateDay)))
            .andExpect(status().isBadRequest());

        List<TemplateDay> templateDayList = templateDayRepository.findAll();
        assertThat(templateDayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateDayRepository.findAll().size();
        // set the field null
        templateDay.setDate(null);

        // Create the TemplateDay, which fails.

        restTemplateDayMockMvc.perform(post("/api/template-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateDay)))
            .andExpect(status().isBadRequest());

        List<TemplateDay> templateDayList = templateDayRepository.findAll();
        assertThat(templateDayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkHoursIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateDayRepository.findAll().size();
        // set the field null
        templateDay.setHours(null);

        // Create the TemplateDay, which fails.

        restTemplateDayMockMvc.perform(post("/api/template-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateDay)))
            .andExpect(status().isBadRequest());

        List<TemplateDay> templateDayList = templateDayRepository.findAll();
        assertThat(templateDayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkLengthIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateDayRepository.findAll().size();
        // set the field null
        templateDay.setLength(null);

        // Create the TemplateDay, which fails.

        restTemplateDayMockMvc.perform(post("/api/template-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateDay)))
            .andExpect(status().isBadRequest());

        List<TemplateDay> templateDayList = templateDayRepository.findAll();
        assertThat(templateDayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPlacesCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateDayRepository.findAll().size();
        // set the field null
        templateDay.setPlacesCount(null);

        // Create the TemplateDay, which fails.

        restTemplateDayMockMvc.perform(post("/api/template-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateDay)))
            .andExpect(status().isBadRequest());

        List<TemplateDay> templateDayList = templateDayRepository.findAll();
        assertThat(templateDayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkOriginIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateDayRepository.findAll().size();
        // set the field null
        templateDay.setOriginId(null);

        // Create the TemplateDay, which fails.

        restTemplateDayMockMvc.perform(post("/api/template-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateDay)))
            .andExpect(status().isBadRequest());

        List<TemplateDay> templateDayList = templateDayRepository.findAll();
        assertThat(templateDayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDestinationIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateDayRepository.findAll().size();
        // set the field null
        templateDay.setDestinationId(null);

        // Create the TemplateDay, which fails.

        restTemplateDayMockMvc.perform(post("/api/template-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateDay)))
            .andExpect(status().isBadRequest());

        List<TemplateDay> templateDayList = templateDayRepository.findAll();
        assertThat(templateDayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllTemplateDays() throws Exception {
        // Initialize the database
        templateDayRepository.save(templateDay);

        // Get all the templateDayList
        restTemplateDayMockMvc.perform(get("/api/template-days?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(templateDay.getId())))
            .andExpect(jsonPath("$.[*].templateId").value(hasItem(DEFAULT_TEMPLATE_ID.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].hours").value(hasItem(DEFAULT_HOURS.doubleValue())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.doubleValue())))
            .andExpect(jsonPath("$.[*].placesCount").value(hasItem(DEFAULT_PLACES_COUNT)))
            .andExpect(jsonPath("$.[*].originId").value(hasItem(DEFAULT_ORIGIN_ID.toString())))
            .andExpect(jsonPath("$.[*].destinationId").value(hasItem(DEFAULT_DESTINATION_ID.toString())));
    }

    @Test
    public void getTemplateDay() throws Exception {
        // Initialize the database
        templateDayRepository.save(templateDay);

        // Get the templateDay
        restTemplateDayMockMvc.perform(get("/api/template-days/{id}", templateDay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(templateDay.getId()))
            .andExpect(jsonPath("$.templateId").value(DEFAULT_TEMPLATE_ID.toString()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.hours").value(DEFAULT_HOURS.doubleValue()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.doubleValue()))
            .andExpect(jsonPath("$.placesCount").value(DEFAULT_PLACES_COUNT))
            .andExpect(jsonPath("$.originId").value(DEFAULT_ORIGIN_ID.toString()))
            .andExpect(jsonPath("$.destinationId").value(DEFAULT_DESTINATION_ID.toString()));
    }

    @Test
    public void getNonExistingTemplateDay() throws Exception {
        // Get the templateDay
        restTemplateDayMockMvc.perform(get("/api/template-days/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTemplateDay() throws Exception {
        // Initialize the database
        templateDayRepository.save(templateDay);
        int databaseSizeBeforeUpdate = templateDayRepository.findAll().size();

        // Update the templateDay
        TemplateDay updatedTemplateDay = templateDayRepository.findOne(templateDay.getId());
        updatedTemplateDay
            .templateId(UPDATED_TEMPLATE_ID)
            .date(UPDATED_DATE)
            .hours(UPDATED_HOURS)
            .length(UPDATED_LENGTH)
            .placesCount(UPDATED_PLACES_COUNT)
            .originId(UPDATED_ORIGIN_ID)
            .destinationId(UPDATED_DESTINATION_ID);

        restTemplateDayMockMvc.perform(put("/api/template-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTemplateDay)))
            .andExpect(status().isOk());

        // Validate the TemplateDay in the database
        List<TemplateDay> templateDayList = templateDayRepository.findAll();
        assertThat(templateDayList).hasSize(databaseSizeBeforeUpdate);
        TemplateDay testTemplateDay = templateDayList.get(templateDayList.size() - 1);
        assertThat(testTemplateDay.getTemplateId()).isEqualTo(UPDATED_TEMPLATE_ID);
        assertThat(testTemplateDay.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTemplateDay.getHours()).isEqualTo(UPDATED_HOURS);
        assertThat(testTemplateDay.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testTemplateDay.getPlacesCount()).isEqualTo(UPDATED_PLACES_COUNT);
        assertThat(testTemplateDay.getOriginId()).isEqualTo(UPDATED_ORIGIN_ID);
        assertThat(testTemplateDay.getDestinationId()).isEqualTo(UPDATED_DESTINATION_ID);
    }

    @Test
    public void updateNonExistingTemplateDay() throws Exception {
        int databaseSizeBeforeUpdate = templateDayRepository.findAll().size();

        // Create the TemplateDay

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTemplateDayMockMvc.perform(put("/api/template-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateDay)))
            .andExpect(status().isCreated());

        // Validate the TemplateDay in the database
        List<TemplateDay> templateDayList = templateDayRepository.findAll();
        assertThat(templateDayList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteTemplateDay() throws Exception {
        // Initialize the database
        templateDayRepository.save(templateDay);
        int databaseSizeBeforeDelete = templateDayRepository.findAll().size();

        // Get the templateDay
        restTemplateDayMockMvc.perform(delete("/api/template-days/{id}", templateDay.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TemplateDay> templateDayList = templateDayRepository.findAll();
        assertThat(templateDayList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemplateDay.class);
        TemplateDay templateDay1 = new TemplateDay();
        templateDay1.setId("id1");
        TemplateDay templateDay2 = new TemplateDay();
        templateDay2.setId(templateDay1.getId());
        assertThat(templateDay1).isEqualTo(templateDay2);
        templateDay2.setId("id2");
        assertThat(templateDay1).isNotEqualTo(templateDay2);
        templateDay1.setId(null);
        assertThat(templateDay1).isNotEqualTo(templateDay2);
    }
}
