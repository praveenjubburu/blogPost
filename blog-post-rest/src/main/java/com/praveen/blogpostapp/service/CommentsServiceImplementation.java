package com.praveen.blogpostapp.service;

import java.util.List;
import java.util.Optional;

import com.praveen.blogpostapp.dao.PostsRepository;
import com.praveen.blogpostapp.entity.Posts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.praveen.blogpostapp.dao.CommentsRepository;
import com.praveen.blogpostapp.entity.Comments;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentsServiceImplementation implements CommentsService {

	public CommentsServiceImplementation(CommentsRepository commentsRepository) {

		this.commentsRepository = commentsRepository;
	}

	CommentsRepository commentsRepository;
	
	@Override
	public List<Comments> findAll() {
	
		return commentsRepository.findAll();
	}

	@Override
	public void save(Comments comment1){
		commentsRepository.save(comment1);
	}

	@Override
	public Comments findById(int id) {
		Optional<Comments> findById = commentsRepository.findById(id);

		Comments comment=null;
		if(findById.isPresent()) {
			comment=findById.get();
		}
		return comment;
	}

	@Override
	public void delete(Comments comment) {
        commentsRepository.delete(comment);
	}

}
