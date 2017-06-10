package com.routeadvisor.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.routeadvisor.app.domain.Audio;

import com.routeadvisor.app.repository.AudioRepository;
import com.routeadvisor.app.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Audio.
 */
@RestController
@RequestMapping("/api")
public class AudioResource {

    private final Logger log = LoggerFactory.getLogger(AudioResource.class);

    private static final String ENTITY_NAME = "audio";
        
    private final AudioRepository audioRepository;

    public AudioResource(AudioRepository audioRepository) {
        this.audioRepository = audioRepository;
    }

    /**
     * POST  /audios : Create a new audio.
     *
     * @param audio the audio to create
     * @return the ResponseEntity with status 201 (Created) and with body the new audio, or with status 400 (Bad Request) if the audio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/audios")
    @Timed
    public ResponseEntity<Audio> createAudio(@Valid @RequestBody Audio audio) throws URISyntaxException {
        log.debug("REST request to save Audio : {}", audio);
        if (audio.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new audio cannot already have an ID")).body(null);
        }
        Audio result = audioRepository.save(audio);
        return ResponseEntity.created(new URI("/api/audios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /audios : Updates an existing audio.
     *
     * @param audio the audio to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated audio,
     * or with status 400 (Bad Request) if the audio is not valid,
     * or with status 500 (Internal Server Error) if the audio couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/audios")
    @Timed
    public ResponseEntity<Audio> updateAudio(@Valid @RequestBody Audio audio) throws URISyntaxException {
        log.debug("REST request to update Audio : {}", audio);
        if (audio.getId() == null) {
            return createAudio(audio);
        }
        Audio result = audioRepository.save(audio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, audio.getId().toString()))
            .body(result);
    }

    /**
     * GET  /audios : get all the audios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of audios in body
     */
    @GetMapping("/audios")
    @Timed
    public List<Audio> getAllAudios() {
        log.debug("REST request to get all Audios");
        List<Audio> audios = audioRepository.findAll();
        return audios;
    }

    /**
     * GET  /audios/:id : get the "id" audio.
     *
     * @param id the id of the audio to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the audio, or with status 404 (Not Found)
     */
    @GetMapping("/audios/{id}")
    @Timed
    public ResponseEntity<Audio> getAudio(@PathVariable String id) {
        log.debug("REST request to get Audio : {}", id);
        Audio audio = audioRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(audio));
    }

    /**
     * DELETE  /audios/:id : delete the "id" audio.
     *
     * @param id the id of the audio to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/audios/{id}")
    @Timed
    public ResponseEntity<Void> deleteAudio(@PathVariable String id) {
        log.debug("REST request to delete Audio : {}", id);
        audioRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

}
