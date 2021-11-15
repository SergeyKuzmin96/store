package com.sergeykuzmin.store.repository;

import com.sergeykuzmin.store.models.UserComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCommentRepository extends JpaRepository<UserComment, Long> {
}
