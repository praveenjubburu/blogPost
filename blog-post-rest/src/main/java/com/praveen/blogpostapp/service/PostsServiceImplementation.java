package com.praveen.blogpostapp.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.praveen.blogpostapp.dao.PostsRepository;
import com.praveen.blogpostapp.entity.Posts;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostsServiceImplementation implements PostsService {
	
	private PostsRepository postsRepository;
	
	public PostsServiceImplementation(PostsRepository postsRepository) {
		
		this.postsRepository = postsRepository;
	}

	@Override
	public List<Posts> findAll() {
		
		return postsRepository.findAll();
	}

	@Override
	public void save(Posts post) {
		
		postsRepository.save(post);
		
	}
	
	public Posts findById(int id) {
		
     Optional<Posts> findById = postsRepository.findById(id);
		
		 Posts post=null;
		if(findById.isPresent()) {
			post=findById.get();
		}
		return post;
	}

	@Override
	public void deletePost(Posts post) {

        postsRepository.delete(post);

	}

	@Override
	public Posts referenceById(int id) {
        return postsRepository.getReferenceById(id);
	}

	@Override
	public List<Posts> findBySearchWord(String word) {
		return postsRepository.findBySearchWord(word);
	}

	@Override
	public List<Posts> findContentLike(String word) {

		return postsRepository.findByContentContaining(word);

	}

	@Override
	@Transactional(readOnly = true)
	public List<Posts> findByFullText(String word) {
		return postsRepository.findByFullText(word);
	}

	@Override
	public List<Posts> findByTagId(int id) {

		return postsRepository.findByTagId(id);

	}
	public Page<Posts> getAllPosts(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Posts> posts = postsRepository.findAll(pageable);
		return posts;
	}

	@Override
	public List<Posts> findByDate(Date fromDate, Date toDate) {
		return postsRepository.findByDate(fromDate,toDate);
	}

	@Override
	public List<Posts> findByAuthorNameAndTagName(List<String> authorName, List<String> tagName) {

		return postsRepository.findByAuthorNameAndTagName(authorName,tagName);
	}

	@Override
	public List<Posts> findByAuthorName(List<String> authorName) {

		return postsRepository.findByAuthorName(authorName);
	}

	@Override
	public List<Posts> findByTagName(List<String> tagName) {

		return postsRepository.findByTagName(tagName);
	}

}
