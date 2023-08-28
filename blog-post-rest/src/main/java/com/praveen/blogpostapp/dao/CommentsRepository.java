package com.praveen.blogpostapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.praveen.blogpostapp.entity.Comments;

public interface CommentsRepository extends JpaRepository<Comments, Integer> {

}
