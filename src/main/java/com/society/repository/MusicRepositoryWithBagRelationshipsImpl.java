package com.society.repository;

import com.society.domain.Music;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class MusicRepositoryWithBagRelationshipsImpl implements MusicRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Music> fetchBagRelationships(Optional<Music> music) {
        return music.map(this::fetchUsers);
    }

    @Override
    public Page<Music> fetchBagRelationships(Page<Music> music) {
        return new PageImpl<>(fetchBagRelationships(music.getContent()), music.getPageable(), music.getTotalElements());
    }

    @Override
    public List<Music> fetchBagRelationships(List<Music> music) {
        return Optional.of(music).map(this::fetchUsers).orElse(Collections.emptyList());
    }

    Music fetchUsers(Music result) {
        return entityManager
            .createQuery("select music from Music music left join fetch music.users where music is :music", Music.class)
            .setParameter("music", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Music> fetchUsers(List<Music> music) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, music.size()).forEach(index -> order.put(music.get(index).getId(), index));
        List<Music> result = entityManager
            .createQuery("select distinct music from Music music left join fetch music.users where music in :music", Music.class)
            .setParameter("music", music)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
