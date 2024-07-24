package com.example.focus_api.domain.comments;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, String>{

    List<Comments> findByPostId(String postId);
}
