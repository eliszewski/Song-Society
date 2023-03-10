package com.society.web.rest;

import com.society.domain.User;
import com.society.repository.PostRepository;
import com.society.repository.ProfileRepository;
import com.society.service.ProfileService;
import com.society.service.dto.ProfileDTO;
import com.society.service.dto.UserProfileDTO;
import com.society.service.mapper.ProfileMapper;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.society.domain.Profile}.
 */
@RestController
@RequestMapping("/api")
public class ProfileResource {

    private final Logger log = LoggerFactory.getLogger(ProfileResource.class);

    private static final String ENTITY_NAME = "profile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfileService profileService;

    private final ProfileRepository profileRepository;

    // @Autowired
    // private ProfileMapper profileMapper;

    @Autowired
    private PostRepository postRepository;

    public ProfileResource(ProfileService profileService, ProfileRepository profileRepository) {
        this.profileService = profileService;
        this.profileRepository = profileRepository;
    }

    /**
     * {@code POST  /profiles} : Create a new profile.
     *
     * @param profileDTO the profileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new profileDTO, or with status {@code 400 (Bad Request)} if the profile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/profiles")
    public ResponseEntity<ProfileDTO> createProfile(@Valid @RequestBody ProfileDTO profileDTO) throws URISyntaxException {
        log.debug("REST request to save Profile : {}", profileDTO);
        if (profileDTO.getId() != null) {
            throw new BadRequestAlertException("A new profile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfileDTO result = profileService.save(profileDTO);
        return ResponseEntity
            .created(new URI("/api/profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /profiles/:id} : Updates an existing profile.
     *
     * @param id the id of the profileDTO to save.
     * @param profileDTO the profileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profileDTO,
     * or with status {@code 400 (Bad Request)} if the profileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the profileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/profiles/{id}")
    public ResponseEntity<ProfileDTO> updateProfile(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProfileDTO profileDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Profile : {}, {}", id, profileDTO);
        if (profileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, profileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!profileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProfileDTO result = profileService.update(profileDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, profileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /profiles/:id} : Partial updates given fields of an existing profile, field will ignore if it is null
     *
     * @param id the id of the profileDTO to save.
     * @param profileDTO the profileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profileDTO,
     * or with status {@code 400 (Bad Request)} if the profileDTO is not valid,
     * or with status {@code 404 (Not Found)} if the profileDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the profileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/profiles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProfileDTO> partialUpdateProfile(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProfileDTO profileDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Profile partially : {}, {}", id, profileDTO);
        if (profileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, profileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!profileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProfileDTO> result = profileService.partialUpdate(profileDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, profileDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /profiles} : get all the profiles.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profiles in body.
     */
    @GetMapping("/profiles")
    public List<ProfileDTO> getAllProfiles(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Profiles");
        return profileService.findAll();
    }

    /**
        REST endpoint to get the society tags for all the profiles of the users that the current user is following.
        @return A list of {@link UserProfileDTO} objects representing the profiles of the followed users. Each DTO
        object will contain the login of the user and their society tag.
    */
    @GetMapping("/profiles/followed-tags")
    public List<UserProfileDTO> getAllFollowedUsersTags() {
        log.debug("REST request to get all Profiles");
        return profileService.findAllForFollowedUsers();
    }

    /**
     * {@code GET  /profiles/:id} : get the "id" profile.
     *
     * @param id the id of the profileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the profileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/profiles/{id}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable Long id) {
        log.debug("REST request to get Profile : {}", id);
        Optional<ProfileDTO> profileDTO = profileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(profileDTO);
    }

    /**
     * Get the profile of the author of a post.
     *
     * @param id the id of the post
     * @return the ResponseEntity with status 200 (OK) and the profile of the author, or with status 404 (Not Found)
     */
    @GetMapping("/profiles/post/{id}")
    public ResponseEntity<ProfileDTO> getProfileByPostId(Long id) {
        log.debug("REST request to get author Profile for post : {}", id);
        Long authorId = postRepository.findOneWithEagerRelationships(id).get().getUser().getId();
        Optional<ProfileDTO> profileDTO = profileService.findOneByUserId(authorId);
        return ResponseUtil.wrapOrNotFound(profileDTO);
    }

    /**
     * GET  /profiles/current-user : get the profile for the current user.
     *
     * @return the ResponseEntity with status 200 (OK) and the profile in body, or with status 404 (Not Found) if the profile is not available for the current user.
     */
    @GetMapping("/profiles/current-user")
    public ResponseEntity<ProfileDTO> getProfileForCurrentUser() {
        log.debug("REST request to get Profile for current user");
        Optional<ProfileDTO> profileDTO = profileService.findOneForCurrentUser();
        return ResponseUtil.wrapOrNotFound(profileDTO);
    }

    /**
     * {@code DELETE  /profiles/:id} : delete the "id" profile.
     *
     * @param id the id of the profileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/profiles/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        log.debug("REST request to delete Profile : {}", id);
        profileService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
