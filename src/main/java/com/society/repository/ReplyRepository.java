package com.society.repository;

import com.society.domain.Reply;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Reply entity.
 */
@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Query("select reply from Reply reply where reply.user.login = ?#{principal.username}")
    List<Reply> findByUserIsCurrentUser();

    default Optional<Reply> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Reply> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Reply> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct reply from Reply reply left join fetch reply.user",
        countQuery = "select count(distinct reply) from Reply reply"
    )
    Page<Reply> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct reply from Reply reply left join fetch reply.user")
    List<Reply> findAllWithToOneRelationships();

    @Query("select reply from Reply reply left join fetch reply.user where reply.id =:id")
    Optional<Reply> findOneWithToOneRelationships(@Param("id") Long id);
}
