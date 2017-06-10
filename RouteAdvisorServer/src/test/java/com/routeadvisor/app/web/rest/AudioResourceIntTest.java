package com.routeadvisor.app.web.rest;

import com.routeadvisor.app.RouteAdvisorApp;

import com.routeadvisor.app.domain.Audio;
import com.routeadvisor.app.repository.AudioRepository;
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
 * Test class for the AudioResource REST controller.
 *
 * @see AudioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RouteAdvisorApp.class)
public class AudioResourceIntTest {

    private static final String DEFAULT_PLACE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PLACE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_ARTIST = "AAAAAAAAAA";
    private static final String UPDATED_ARTIST = "BBBBBBBBBB";

    private static final String DEFAULT_SRC = "AAAAAAAAAA";
    private static final String UPDATED_SRC = "BBBBBBBBBB";

    private static final String DEFAULT_ART = "AAAAAAAAAA";
    private static final String UPDATED_ART = "BBBBBBBBBB";

    @Autowired
    private AudioRepository audioRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restAudioMockMvc;

    private Audio audio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AudioResource audioResource = new AudioResource(audioRepository);
        this.restAudioMockMvc = MockMvcBuilders.standaloneSetup(audioResource)
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
    public static Audio createEntity() {
        Audio audio = new Audio()
            .placeId(DEFAULT_PLACE_ID)
            .title(DEFAULT_TITLE)
            .artist(DEFAULT_ARTIST)
            .src(DEFAULT_SRC)
            .art(DEFAULT_ART);
        return audio;
    }

    @Before
    public void initTest() {
        audioRepository.deleteAll();
        audio = createEntity();
    }

    @Test
    public void createAudio() throws Exception {
        int databaseSizeBeforeCreate = audioRepository.findAll().size();

        // Create the Audio
        restAudioMockMvc.perform(post("/api/audios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audio)))
            .andExpect(status().isCreated());

        // Validate the Audio in the database
        List<Audio> audioList = audioRepository.findAll();
        assertThat(audioList).hasSize(databaseSizeBeforeCreate + 1);
        Audio testAudio = audioList.get(audioList.size() - 1);
        assertThat(testAudio.getPlaceId()).isEqualTo(DEFAULT_PLACE_ID);
        assertThat(testAudio.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testAudio.getArtist()).isEqualTo(DEFAULT_ARTIST);
        assertThat(testAudio.getSrc()).isEqualTo(DEFAULT_SRC);
        assertThat(testAudio.getArt()).isEqualTo(DEFAULT_ART);
    }

    @Test
    public void createAudioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = audioRepository.findAll().size();

        // Create the Audio with an existing ID
        audio.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restAudioMockMvc.perform(post("/api/audios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audio)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Audio> audioList = audioRepository.findAll();
        assertThat(audioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkPlaceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = audioRepository.findAll().size();
        // set the field null
        audio.setPlaceId(null);

        // Create the Audio, which fails.

        restAudioMockMvc.perform(post("/api/audios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audio)))
            .andExpect(status().isBadRequest());

        List<Audio> audioList = audioRepository.findAll();
        assertThat(audioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = audioRepository.findAll().size();
        // set the field null
        audio.setTitle(null);

        // Create the Audio, which fails.

        restAudioMockMvc.perform(post("/api/audios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audio)))
            .andExpect(status().isBadRequest());

        List<Audio> audioList = audioRepository.findAll();
        assertThat(audioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkArtistIsRequired() throws Exception {
        int databaseSizeBeforeTest = audioRepository.findAll().size();
        // set the field null
        audio.setArtist(null);

        // Create the Audio, which fails.

        restAudioMockMvc.perform(post("/api/audios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audio)))
            .andExpect(status().isBadRequest());

        List<Audio> audioList = audioRepository.findAll();
        assertThat(audioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkSrcIsRequired() throws Exception {
        int databaseSizeBeforeTest = audioRepository.findAll().size();
        // set the field null
        audio.setSrc(null);

        // Create the Audio, which fails.

        restAudioMockMvc.perform(post("/api/audios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audio)))
            .andExpect(status().isBadRequest());

        List<Audio> audioList = audioRepository.findAll();
        assertThat(audioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllAudios() throws Exception {
        // Initialize the database
        audioRepository.save(audio);

        // Get all the audioList
        restAudioMockMvc.perform(get("/api/audios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(audio.getId())))
            .andExpect(jsonPath("$.[*].placeId").value(hasItem(DEFAULT_PLACE_ID.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].artist").value(hasItem(DEFAULT_ARTIST.toString())))
            .andExpect(jsonPath("$.[*].src").value(hasItem(DEFAULT_SRC.toString())))
            .andExpect(jsonPath("$.[*].art").value(hasItem(DEFAULT_ART.toString())));
    }

    @Test
    public void getAudio() throws Exception {
        // Initialize the database
        audioRepository.save(audio);

        // Get the audio
        restAudioMockMvc.perform(get("/api/audios/{id}", audio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(audio.getId()))
            .andExpect(jsonPath("$.placeId").value(DEFAULT_PLACE_ID.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.artist").value(DEFAULT_ARTIST.toString()))
            .andExpect(jsonPath("$.src").value(DEFAULT_SRC.toString()))
            .andExpect(jsonPath("$.art").value(DEFAULT_ART.toString()));
    }

    @Test
    public void getNonExistingAudio() throws Exception {
        // Get the audio
        restAudioMockMvc.perform(get("/api/audios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAudio() throws Exception {
        // Initialize the database
        audioRepository.save(audio);
        int databaseSizeBeforeUpdate = audioRepository.findAll().size();

        // Update the audio
        Audio updatedAudio = audioRepository.findOne(audio.getId());
        updatedAudio
            .placeId(UPDATED_PLACE_ID)
            .title(UPDATED_TITLE)
            .artist(UPDATED_ARTIST)
            .src(UPDATED_SRC)
            .art(UPDATED_ART);

        restAudioMockMvc.perform(put("/api/audios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAudio)))
            .andExpect(status().isOk());

        // Validate the Audio in the database
        List<Audio> audioList = audioRepository.findAll();
        assertThat(audioList).hasSize(databaseSizeBeforeUpdate);
        Audio testAudio = audioList.get(audioList.size() - 1);
        assertThat(testAudio.getPlaceId()).isEqualTo(UPDATED_PLACE_ID);
        assertThat(testAudio.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAudio.getArtist()).isEqualTo(UPDATED_ARTIST);
        assertThat(testAudio.getSrc()).isEqualTo(UPDATED_SRC);
        assertThat(testAudio.getArt()).isEqualTo(UPDATED_ART);
    }

    @Test
    public void updateNonExistingAudio() throws Exception {
        int databaseSizeBeforeUpdate = audioRepository.findAll().size();

        // Create the Audio

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAudioMockMvc.perform(put("/api/audios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audio)))
            .andExpect(status().isCreated());

        // Validate the Audio in the database
        List<Audio> audioList = audioRepository.findAll();
        assertThat(audioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteAudio() throws Exception {
        // Initialize the database
        audioRepository.save(audio);
        int databaseSizeBeforeDelete = audioRepository.findAll().size();

        // Get the audio
        restAudioMockMvc.perform(delete("/api/audios/{id}", audio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Audio> audioList = audioRepository.findAll();
        assertThat(audioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Audio.class);
        Audio audio1 = new Audio();
        audio1.setId("id1");
        Audio audio2 = new Audio();
        audio2.setId(audio1.getId());
        assertThat(audio1).isEqualTo(audio2);
        audio2.setId("id2");
        assertThat(audio1).isNotEqualTo(audio2);
        audio1.setId(null);
        assertThat(audio1).isNotEqualTo(audio2);
    }
}
