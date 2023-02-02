package com.society.service;

import com.society.domain.Music;
import com.society.repository.MusicRepository;
import com.society.service.dto.MusicDTO;
import com.society.service.mapper.MusicMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Music}.
 */
@Service
@Transactional
public class MusicService {

    private final Logger log = LoggerFactory.getLogger(MusicService.class);

    private final MusicRepository musicRepository;

    private final MusicMapper musicMapper;

    public MusicService(MusicRepository musicRepository, MusicMapper musicMapper) {
        this.musicRepository = musicRepository;
        this.musicMapper = musicMapper;
    }

    /**
     * Save a music.
     *
     * @param musicDTO the entity to save.
     * @return the persisted entity.
     */
    public MusicDTO save(MusicDTO musicDTO) {
        log.debug("Request to save Music : {}", musicDTO);
        Music music = musicMapper.toEntity(musicDTO);
        music = musicRepository.save(music);
        return musicMapper.toDto(music);
    }

    /**
     * Update a music.
     *
     * @param musicDTO the entity to save.
     * @return the persisted entity.
     */
    public MusicDTO update(MusicDTO musicDTO) {
        log.debug("Request to update Music : {}", musicDTO);
        Music music = musicMapper.toEntity(musicDTO);
        music = musicRepository.save(music);
        return musicMapper.toDto(music);
    }

    /**
     * Partially update a music.
     *
     * @param musicDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MusicDTO> partialUpdate(MusicDTO musicDTO) {
        log.debug("Request to partially update Music : {}", musicDTO);

        return musicRepository
            .findById(musicDTO.getId())
            .map(existingMusic -> {
                musicMapper.partialUpdate(existingMusic, musicDTO);

                return existingMusic;
            })
            .map(musicRepository::save)
            .map(musicMapper::toDto);
    }

    /**
     * Get all the music.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MusicDTO> findAll() {
        log.debug("Request to get all Music");
        return musicRepository.findAll().stream().map(musicMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the music with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<MusicDTO> findAllWithEagerRelationships(Pageable pageable) {
        return musicRepository.findAllWithEagerRelationships(pageable).map(musicMapper::toDto);
    }

    /**
     * Get one music by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MusicDTO> findOne(Long id) {
        log.debug("Request to get Music : {}", id);
        return musicRepository.findOneWithEagerRelationships(id).map(musicMapper::toDto);
    }

    /**
     * Delete the music by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Music : {}", id);
        musicRepository.deleteById(id);
    }
}
