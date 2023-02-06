package com.society.service;

import com.society.domain.Profile;
import com.society.domain.User;
import com.society.repository.ProfileRepository;
import com.society.service.dto.ProfileDTO;
import com.society.service.dto.UserProfileDTO;
import com.society.service.mapper.ProfileMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Profile}.
 */
@Service
@Transactional
public class ProfileService {

    private final Logger log = LoggerFactory.getLogger(ProfileService.class);

    private final ProfileRepository profileRepository;

    private final ProfileMapper profileMapper;

    @Autowired
    private FollowService followservice;

    public ProfileService(ProfileRepository profileRepository, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    /**
     * Save a profile.
     *
     * @param profileDTO the entity to save.
     * @return the persisted entity.
     */
    public ProfileDTO save(ProfileDTO profileDTO) {
        log.debug("Request to save Profile : {}", profileDTO);
        Profile profile = profileMapper.toEntity(profileDTO);
        profile = profileRepository.save(profile);
        return profileMapper.toDto(profile);
    }

    /**
     * Update a profile.
     *
     * @param profileDTO the entity to save.
     * @return the persisted entity.
     */
    public ProfileDTO update(ProfileDTO profileDTO) {
        log.debug("Request to update Profile : {}", profileDTO);
        Profile profile = profileMapper.toEntity(profileDTO);
        profile = profileRepository.save(profile);
        return profileMapper.toDto(profile);
    }

    /**
     * Partially update a profile.
     *
     * @param profileDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProfileDTO> partialUpdate(ProfileDTO profileDTO) {
        log.debug("Request to partially update Profile : {}", profileDTO);

        return profileRepository
            .findById(profileDTO.getId())
            .map(existingProfile -> {
                profileMapper.partialUpdate(existingProfile, profileDTO);

                return existingProfile;
            })
            .map(profileRepository::save)
            .map(profileMapper::toDto);
    }

    /**
     * Get all the profiles.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProfileDTO> findAll() {
        log.debug("Request to get all Profiles");
        return profileRepository.findAll().stream().map(profileMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the profiles with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ProfileDTO> findAllWithEagerRelationships(Pageable pageable) {
        return profileRepository.findAllWithEagerRelationships(pageable).map(profileMapper::toDto);
    }

    /**
     * Get one profile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProfileDTO> findOne(Long id) {
        log.debug("Request to get Profile : {}", id);
        return profileRepository.findOneWithEagerRelationships(id).map(profileMapper::toDto);
    }

    /**
     * Delete the profile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Profile : {}", id);
        profileRepository.deleteById(id);
    }

    /**
    Retrieve a list of UserProfileDTOs for all users followed by the current user.
    @return a List of UserProfileDTOs containing the login and societyTag for each followed user.
    */
    @Transactional(readOnly = true)
    public List<UserProfileDTO> findAllForFollowedUsers() {
        List<User> followed = followservice.findAllUsersFollowedByUser();

        log.debug("Request to get all followed user's Profiles");

        return followed.stream().map(this::createUserProfileDTOForUser).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
    Creates a {@link UserProfileDTO} for a given {@link User} object.
    @param user The {@link User} object to create the {@link UserProfileDTO} for.
    @return The created {@link UserProfileDTO} if a {@link Profile} was found for the {@link User}, null otherwise.
    */
    public UserProfileDTO createUserProfileDTOForUser(User user) {
        Optional<Profile> profile = profileRepository.findOneByUserId(user.getId());
        if (profile.isPresent()) return new UserProfileDTO(
            profile.get().getId(),
            user.getLogin(),
            profile.get().getSocietyTag()
        ); else log.debug("No profile found for user {}", user.getLogin());
        return null;
    }
}
