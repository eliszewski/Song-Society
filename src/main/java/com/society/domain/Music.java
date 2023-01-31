package com.society.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Music.
 */
@Entity
@Table(name = "music")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Music implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "song_name", nullable = false)
    private String songName;

    @NotNull
    @Column(name = "artist_name", nullable = false)
    private String artistName;

    @Column(name = "album_name")
    private String albumName;

    @ManyToMany
    @JoinTable(name = "rel_music__user", joinColumns = @JoinColumn(name = "music_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<User> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Music id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSongName() {
        return this.songName;
    }

    public Music songName(String songName) {
        this.setSongName(songName);
        return this;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtistName() {
        return this.artistName;
    }

    public Music artistName(String artistName) {
        this.setArtistName(artistName);
        return this;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumName() {
        return this.albumName;
    }

    public Music albumName(String albumName) {
        this.setAlbumName(albumName);
        return this;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Music users(Set<User> users) {
        this.setUsers(users);
        return this;
    }

    public Music addUser(User user) {
        this.users.add(user);
        return this;
    }

    public Music removeUser(User user) {
        this.users.remove(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Music)) {
            return false;
        }
        return id != null && id.equals(((Music) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Music{" +
            "id=" + getId() +
            ", songName='" + getSongName() + "'" +
            ", artistName='" + getArtistName() + "'" +
            ", albumName='" + getAlbumName() + "'" +
            "}";
    }
}
