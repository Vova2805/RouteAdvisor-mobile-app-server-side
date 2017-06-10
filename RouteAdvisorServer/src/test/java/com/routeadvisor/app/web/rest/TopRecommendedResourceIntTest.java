package com.routeadvisor.app.web.rest;

import com.routeadvisor.app.RouteAdvisorApp;

import com.routeadvisor.app.domain.TopRecommended;
import com.routeadvisor.app.repository.TopRecommendedRepository;
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

import com.routeadvisor.app.domain.enumeration.Category;
/**
 * Test class for the TopRecommendedResource REST controller.
 *
 * @see TopRecommendedResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RouteAdvisorApp.class)
public class TopRecommendedResourceIntTest {

    private static final Float DEFAULT_RATING = 1F;
    private static final Float UPDATED_RATING = 2F;

    private static final Category DEFAULT_CATEGORY = Category.NONE;
    private static final Category UPDATED_CATEGORY = Category.FAMILY;

    private static final String DEFAULT_PLACE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PLACE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLATE_ID = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_ID = "BBBBBBBBBB";

    @Autowired
    private TopRecommendedRepository topRecommendedRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restTopRecommendedMockMvc;

    private TopRecommended topRecommended;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TopRecommendedResource topRecommendedResource = new TopRecommendedResource(topRecommendedRepository);
        this.restTopRecommendedMockMvc = MockMvcBuilders.standaloneSetup(topRecommendedResource)
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
    public static TopRecommended createEntity() {
        TopRecommended topRecommended = new TopRecommended()
            .rating(DEFAULT_RATING)
            .category(DEFAULT_CATEGORY)
            .placeId(DEFAULT_PLACE_ID)
            .templateId(DEFAULT_TEMPLATE_ID);
        return topRecommended;
    }

    @Before
    public void initTest() {
        topRecommendedRepository.deleteAll();
        topRecommended = createEntity();
    }

    @Test
    public void createTopRecommended() throws Exception {
        int databaseSizeBeforeCreate = topRecommendedRepository.findAll().size();

        // Create the TopRecommended
        restTopRecommendedMockMvc.perform(post("/api/top-recommendeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topRecommended)))
            .andExpect(status().isCreated());

        // Validate the TopRecommended in the database
        List<TopRecommended> topRecommendedList = topRecommendedRepository.findAll();
        assertThat(topRecommendedList).hasSize(databaseSizeBeforeCreate + 1);
        TopRecommended testTopRecommended = topRecommendedList.get(topRecommendedList.size() - 1);
        assertThat(testTopRecommended.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testTopRecommended.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testTopRecommended.getPlaceId()).isEqualTo(DEFAULT_PLACE_ID);
        assertThat(testTopRecommended.getTemplateId()).isEqualTo(DEFAULT_TEMPLATE_ID);
    }

    @Test
    public void createTopRecommendedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = topRecommendedRepository.findAll().size();

        // Create the TopRecommended with an existing ID
        topRecommended.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopRecommendedMockMvc.perform(post("/api/top-recommendeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topRecommended)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TopRecommended> topRecommendedList = topRecommendedRepository.findAll();
        assertThat(topRecommendedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkRatingIsRequired() throws Exception {
        int databaseSizeBeforeTest = topRecommendedRepository.findAll().size();
        // set the field null
        topRecommended.setRating(null);

        // Create the TopRecommended, which fails.

        restTopRecommendedMockMvc.perform(post("/api/top-recommendeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topRecommended)))
            .andExpect(status().isBadRequest());

        List<TopRecommended> topRecommendedList = topRecommendedRepository.findAll();
        assertThat(topRecommendedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = topRecommendedRepository.findAll().size();
        // set the field null
        topRecommended.setCategory(null);

        // Create the TopRecommended, which fails.

        restTopRecommendedMockMvc.perform(post("/api/top-recommendeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topRecommended)))
            .andExpect(status().isBadRequest());

        List<TopRecommended> topRecommendedList = topRecommendedRepository.findAll();
        assertThat(topRecommendedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllTopRecommendeds() throws Exception {
        // Initialize the database
        topRecommendedRepository.save(topRecommended);

        // Get all the topRecommendedList
        restTopRecommendedMockMvc.perform(get("/api/top-recommendeds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topRecommended.getId())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].placeId").value(hasItem(DEFAULT_PLACE_ID.toString())))
            .andExpect(jsonPath("$.[*].templateId").value(hasItem(DEFAULT_TEMPLATE_ID.toString())));
    }

    @Test
    public void getTopRecommended() throws Exception {
        // Initialize the database
        topRecommendedRepository.save(topRecommended);

        // Get the topRecommended
        restTopRecommendedMockMvc.perform(get("/api/top-recommendeds/{id}", topRecommended.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(topRecommended.getId()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.doubleValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.placeId").value(DEFAULT_PLACE_ID.toString()))
            .andExpect(jsonPath("$.templateId").value(DEFAULT_TEMPLATE_ID.toString()));
    }

    @Test
    public void getNonExistingTopRecommended() throws Exception {
        // Get the topRecommended
        restTopRecommendedMockMvc.perform(get("/api/top-recommendeds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTopRecommended() throws Exception {
        // Initialize the database
        topRecommendedRepository.save(topRecommended);
        int databaseSizeBeforeUpdate = topRecommendedRepository.findAll().size();

        // Update the topRecommended
        TopRecommended updatedTopRecommended = topRecommendedRepository.findOne(topRecommended.getId());
        updatedTopRecommended
            .rating(UPDATED_RATING)
            .category(UPDATED_CATEGORY)
            .placeId(UPDATED_PLACE_ID)
            .templateId(UPDATED_TEMPLATE_ID);

        restTopRecommendedMockMvc.perform(put("/api/top-recommendeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTopRecommended)))
            .andExpect(status().isOk());

        // Validate the TopRecommended in the database
        List<TopRecommended> topRecommendedList = topRecommendedRepository.findAll();
        assertThat(topRecommendedList).hasSize(databaseSizeBeforeUpdate);
        TopRecommended testTopRecommended = topRecommendedList.get(topRecommendedList.size() - 1);
        assertThat(testTopRecommended.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testTopRecommended.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testTopRecommended.getPlaceId()).isEqualTo(UPDATED_PLACE_ID);
        assertThat(testTopRecommended.getTemplateId()).isEqualTo(UPDATED_TEMPLATE_ID);
    }

    @Test
    public void updateNonExistingTopRecommended() throws Exception {
        int databaseSizeBeforeUpdate = topRecommendedRepository.findAll().size();

        // Create the TopRecommended

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTopRecommendedMockMvc.perform(put("/api/top-recommendeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topRecommended)))
            .andExpect(status().isCreated());

        // Validate the TopRecommended in the database
        List<TopRecommended> topRecommendedList = topRecommendedRepository.findAll();
        assertThat(topRecommendedList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteTopRecommended() throws Exception {
        // Initialize the database
        topRecommendedRepository.save(topRecommended);
        int databaseSizeBeforeDelete = topRecommendedRepository.findAll().size();

        // Get the topRecommended
        restTopRecommendedMockMvc.perform(delete("/api/top-recommendeds/{id}", topRecommended.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TopRecommended> topRecommendedList = topRecommendedRepository.findAll();
        assertThat(topRecommendedList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopRecommended.class);
        TopRecommended topRecommended1 = new TopRecommended();
        topRecommended1.setId("id1");
        TopRecommended topRecommended2 = new TopRecommended();
        topRecommended2.setId(topRecommended1.getId());
        assertThat(topRecommended1).isEqualTo(topRecommended2);
        topRecommended2.setId("id2");
        assertThat(topRecommended1).isNotEqualTo(topRecommended2);
        topRecommended1.setId(null);
        assertThat(topRecommended1).isNotEqualTo(topRecommended2);
    }
}
