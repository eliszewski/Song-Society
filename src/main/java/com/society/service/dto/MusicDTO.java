package com.society.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.society.domain.Music} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MusicDTO implements Serializable {

    private Long id;

    @NotNull
    private String songName;

    @NotNull
    private String artistName;

    private String albumName;

    private Set<UserDTO> users = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MusicDTO)) {
            return false;
        }

        MusicDTO musicDTO = (MusicDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, musicDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MusicDTO{" +
            "id=" + getId() +
            ", songName='" + getSongName() + "'" +
            ", artistName='" + getArtistName() + "'" +
            ", albumName='" + getAlbumName() + "'" +
            ", users=" + getUsers() +
            "}";
    }
}
