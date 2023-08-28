package com.praveen.blogpostapp.service;

import java.util.List;

import com.praveen.blogpostapp.entity.Comments;

public interface CommentsService {

    public List<Comments> findAll();
	
	public void save(Comments comment1);

    public Comments findById(int id);

    public void delete(Comments comment);
}
