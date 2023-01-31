package com.society.repository;

import com.society.domain.Music;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface MusicRepositoryWithBagRelationships {
    Optional<Music> fetchBagRelationships(Optional<Music> music);

    List<Music> fetchBagRelationships(List<Music> music);

    Page<Music> fetchBagRelationships(Page<Music> music);
}
