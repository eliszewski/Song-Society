package com.society.web.rest;

import com.society.repository.MusicRepository;
import com.society.service.MusicService;
import com.society.service.dto.MusicDTO;
import com.society.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.society.domain.Music}.
 */
@RestController
@RequestMapping("/api")
public class MusicResource {

    private final Logger log = LoggerFactory.getLogger(MusicResource.class);

    private static final String ENTITY_NAME = "music";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MusicService musicService;

    private final MusicRepository musicRepository;

    public MusicResource(MusicService musicService, MusicRepository musicRepository) {
        this.musicService = musicService;
        this.musicRepository = musicRepository;
    }

    /**
     * {@code POST  /music} : Create a new music.
     *
     * @param musicDTO the musicDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new musicDTO, or with status {@code 400 (Bad Request)} if the music has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/music")
    public ResponseEntity<MusicDTO> createMusic(@Valid @RequestBody MusicDTO musicDTO) throws URISyntaxException {
        log.debug("REST request to save Music : {}", musicDTO);
        if (musicDTO.getId() != null) {
            throw new BadRequestAlertException("A new music cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MusicDTO result = musicService.save(musicDTO);
        return ResponseEntity
            .created(new URI("/api/music/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /music/:id} : Updates an existing music.
     *
     * @param id the id of the musicDTO to save.
     * @param musicDTO the musicDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated musicDTO,
     * or with status {@code 400 (Bad Request)} if the musicDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the musicDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/music/{id}")
    public ResponseEntity<MusicDTO> updateMusic(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MusicDTO musicDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Music : {}, {}", id, musicDTO);
        if (musicDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, musicDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!musicRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MusicDTO result = musicService.update(musicDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, musicDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /music/:id} : Partial updates given fields of an existing music, field will ignore if it is null
     *
     * @param id the id of the musicDTO to save.
     * @param musicDTO the musicDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated musicDTO,
     * or with status {@code 400 (Bad Request)} if the musicDTO is not valid,
     * or with status {@code 404 (Not Found)} if the musicDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the musicDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/music/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MusicDTO> partialUpdateMusic(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MusicDTO musicDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Music partially : {}, {}", id, musicDTO);
        if (musicDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, musicDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!musicRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MusicDTO> result = musicService.partialUpdate(musicDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, musicDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /music} : get all the music.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of music in body.
     */
    @GetMapping("/music")
    public List<MusicDTO> getAllMusic(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Music");
        return musicService.findAll();
    }

    /**
     * {@code GET  /music/:id} : get the "id" music.
     *
     * @param id the id of the musicDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the musicDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/music/{id}")
    public ResponseEntity<MusicDTO> getMusic(@PathVariable Long id) {
        log.debug("REST request to get Music : {}", id);
        Optional<MusicDTO> musicDTO = musicService.findOne(id);
        return ResponseUtil.wrapOrNotFound(musicDTO);
    }

    /**
     * {@code DELETE  /music/:id} : delete the "id" music.
     *
     * @param id the id of the musicDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/music/{id}")
    public ResponseEntity<Void> deleteMusic(@PathVariable Long id) {
        log.debug("REST request to delete Music : {}", id);
        musicService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
