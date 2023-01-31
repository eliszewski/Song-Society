package com.society.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.society.IntegrationTest;
import com.society.domain.Music;
import com.society.repository.MusicRepository;
import com.society.service.MusicService;
import com.society.service.dto.MusicDTO;
import com.society.service.mapper.MusicMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MusicResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MusicResourceIT {

    private static final String DEFAULT_SONG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SONG_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ARTIST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ARTIST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ALBUM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ALBUM_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/music";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MusicRepository musicRepository;

    @Mock
    private MusicRepository musicRepositoryMock;

    @Autowired
    private MusicMapper musicMapper;

    @Mock
    private MusicService musicServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMusicMockMvc;

    private Music music;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Music createEntity(EntityManager em) {
        Music music = new Music().songName(DEFAULT_SONG_NAME).artistName(DEFAULT_ARTIST_NAME).albumName(DEFAULT_ALBUM_NAME);
        return music;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Music createUpdatedEntity(EntityManager em) {
        Music music = new Music().songName(UPDATED_SONG_NAME).artistName(UPDATED_ARTIST_NAME).albumName(UPDATED_ALBUM_NAME);
        return music;
    }

    @BeforeEach
    public void initTest() {
        music = createEntity(em);
    }

    @Test
    @Transactional
    void createMusic() throws Exception {
        int databaseSizeBeforeCreate = musicRepository.findAll().size();
        // Create the Music
        MusicDTO musicDTO = musicMapper.toDto(music);
        restMusicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(musicDTO)))
            .andExpect(status().isCreated());

        // Validate the Music in the database
        List<Music> musicList = musicRepository.findAll();
        assertThat(musicList).hasSize(databaseSizeBeforeCreate + 1);
        Music testMusic = musicList.get(musicList.size() - 1);
        assertThat(testMusic.getSongName()).isEqualTo(DEFAULT_SONG_NAME);
        assertThat(testMusic.getArtistName()).isEqualTo(DEFAULT_ARTIST_NAME);
        assertThat(testMusic.getAlbumName()).isEqualTo(DEFAULT_ALBUM_NAME);
    }

    @Test
    @Transactional
    void createMusicWithExistingId() throws Exception {
        // Create the Music with an existing ID
        music.setId(1L);
        MusicDTO musicDTO = musicMapper.toDto(music);

        int databaseSizeBeforeCreate = musicRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMusicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(musicDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Music in the database
        List<Music> musicList = musicRepository.findAll();
        assertThat(musicList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSongNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = musicRepository.findAll().size();
        // set the field null
        music.setSongName(null);

        // Create the Music, which fails.
        MusicDTO musicDTO = musicMapper.toDto(music);

        restMusicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(musicDTO)))
            .andExpect(status().isBadRequest());

        List<Music> musicList = musicRepository.findAll();
        assertThat(musicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkArtistNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = musicRepository.findAll().size();
        // set the field null
        music.setArtistName(null);

        // Create the Music, which fails.
        MusicDTO musicDTO = musicMapper.toDto(music);

        restMusicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(musicDTO)))
            .andExpect(status().isBadRequest());

        List<Music> musicList = musicRepository.findAll();
        assertThat(musicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMusic() throws Exception {
        // Initialize the database
        musicRepository.saveAndFlush(music);

        // Get all the musicList
        restMusicMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(music.getId().intValue())))
            .andExpect(jsonPath("$.[*].songName").value(hasItem(DEFAULT_SONG_NAME)))
            .andExpect(jsonPath("$.[*].artistName").value(hasItem(DEFAULT_ARTIST_NAME)))
            .andExpect(jsonPath("$.[*].albumName").value(hasItem(DEFAULT_ALBUM_NAME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMusicWithEagerRelationshipsIsEnabled() throws Exception {
        when(musicServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMusicMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(musicServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMusicWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(musicServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMusicMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(musicRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getMusic() throws Exception {
        // Initialize the database
        musicRepository.saveAndFlush(music);

        // Get the music
        restMusicMockMvc
            .perform(get(ENTITY_API_URL_ID, music.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(music.getId().intValue()))
            .andExpect(jsonPath("$.songName").value(DEFAULT_SONG_NAME))
            .andExpect(jsonPath("$.artistName").value(DEFAULT_ARTIST_NAME))
            .andExpect(jsonPath("$.albumName").value(DEFAULT_ALBUM_NAME));
    }

    @Test
    @Transactional
    void getNonExistingMusic() throws Exception {
        // Get the music
        restMusicMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMusic() throws Exception {
        // Initialize the database
        musicRepository.saveAndFlush(music);

        int databaseSizeBeforeUpdate = musicRepository.findAll().size();

        // Update the music
        Music updatedMusic = musicRepository.findById(music.getId()).get();
        // Disconnect from session so that the updates on updatedMusic are not directly saved in db
        em.detach(updatedMusic);
        updatedMusic.songName(UPDATED_SONG_NAME).artistName(UPDATED_ARTIST_NAME).albumName(UPDATED_ALBUM_NAME);
        MusicDTO musicDTO = musicMapper.toDto(updatedMusic);

        restMusicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, musicDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(musicDTO))
            )
            .andExpect(status().isOk());

        // Validate the Music in the database
        List<Music> musicList = musicRepository.findAll();
        assertThat(musicList).hasSize(databaseSizeBeforeUpdate);
        Music testMusic = musicList.get(musicList.size() - 1);
        assertThat(testMusic.getSongName()).isEqualTo(UPDATED_SONG_NAME);
        assertThat(testMusic.getArtistName()).isEqualTo(UPDATED_ARTIST_NAME);
        assertThat(testMusic.getAlbumName()).isEqualTo(UPDATED_ALBUM_NAME);
    }

    @Test
    @Transactional
    void putNonExistingMusic() throws Exception {
        int databaseSizeBeforeUpdate = musicRepository.findAll().size();
        music.setId(count.incrementAndGet());

        // Create the Music
        MusicDTO musicDTO = musicMapper.toDto(music);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMusicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, musicDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(musicDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Music in the database
        List<Music> musicList = musicRepository.findAll();
        assertThat(musicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMusic() throws Exception {
        int databaseSizeBeforeUpdate = musicRepository.findAll().size();
        music.setId(count.incrementAndGet());

        // Create the Music
        MusicDTO musicDTO = musicMapper.toDto(music);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMusicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(musicDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Music in the database
        List<Music> musicList = musicRepository.findAll();
        assertThat(musicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMusic() throws Exception {
        int databaseSizeBeforeUpdate = musicRepository.findAll().size();
        music.setId(count.incrementAndGet());

        // Create the Music
        MusicDTO musicDTO = musicMapper.toDto(music);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMusicMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(musicDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Music in the database
        List<Music> musicList = musicRepository.findAll();
        assertThat(musicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMusicWithPatch() throws Exception {
        // Initialize the database
        musicRepository.saveAndFlush(music);

        int databaseSizeBeforeUpdate = musicRepository.findAll().size();

        // Update the music using partial update
        Music partialUpdatedMusic = new Music();
        partialUpdatedMusic.setId(music.getId());

        partialUpdatedMusic.songName(UPDATED_SONG_NAME).artistName(UPDATED_ARTIST_NAME);

        restMusicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMusic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMusic))
            )
            .andExpect(status().isOk());

        // Validate the Music in the database
        List<Music> musicList = musicRepository.findAll();
        assertThat(musicList).hasSize(databaseSizeBeforeUpdate);
        Music testMusic = musicList.get(musicList.size() - 1);
        assertThat(testMusic.getSongName()).isEqualTo(UPDATED_SONG_NAME);
        assertThat(testMusic.getArtistName()).isEqualTo(UPDATED_ARTIST_NAME);
        assertThat(testMusic.getAlbumName()).isEqualTo(DEFAULT_ALBUM_NAME);
    }

    @Test
    @Transactional
    void fullUpdateMusicWithPatch() throws Exception {
        // Initialize the database
        musicRepository.saveAndFlush(music);

        int databaseSizeBeforeUpdate = musicRepository.findAll().size();

        // Update the music using partial update
        Music partialUpdatedMusic = new Music();
        partialUpdatedMusic.setId(music.getId());

        partialUpdatedMusic.songName(UPDATED_SONG_NAME).artistName(UPDATED_ARTIST_NAME).albumName(UPDATED_ALBUM_NAME);

        restMusicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMusic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMusic))
            )
            .andExpect(status().isOk());

        // Validate the Music in the database
        List<Music> musicList = musicRepository.findAll();
        assertThat(musicList).hasSize(databaseSizeBeforeUpdate);
        Music testMusic = musicList.get(musicList.size() - 1);
        assertThat(testMusic.getSongName()).isEqualTo(UPDATED_SONG_NAME);
        assertThat(testMusic.getArtistName()).isEqualTo(UPDATED_ARTIST_NAME);
        assertThat(testMusic.getAlbumName()).isEqualTo(UPDATED_ALBUM_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingMusic() throws Exception {
        int databaseSizeBeforeUpdate = musicRepository.findAll().size();
        music.setId(count.incrementAndGet());

        // Create the Music
        MusicDTO musicDTO = musicMapper.toDto(music);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMusicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, musicDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(musicDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Music in the database
        List<Music> musicList = musicRepository.findAll();
        assertThat(musicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMusic() throws Exception {
        int databaseSizeBeforeUpdate = musicRepository.findAll().size();
        music.setId(count.incrementAndGet());

        // Create the Music
        MusicDTO musicDTO = musicMapper.toDto(music);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMusicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(musicDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Music in the database
        List<Music> musicList = musicRepository.findAll();
        assertThat(musicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMusic() throws Exception {
        int databaseSizeBeforeUpdate = musicRepository.findAll().size();
        music.setId(count.incrementAndGet());

        // Create the Music
        MusicDTO musicDTO = musicMapper.toDto(music);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMusicMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(musicDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Music in the database
        List<Music> musicList = musicRepository.findAll();
        assertThat(musicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMusic() throws Exception {
        // Initialize the database
        musicRepository.saveAndFlush(music);

        int databaseSizeBeforeDelete = musicRepository.findAll().size();

        // Delete the music
        restMusicMockMvc
            .perform(delete(ENTITY_API_URL_ID, music.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Music> musicList = musicRepository.findAll();
        assertThat(musicList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
