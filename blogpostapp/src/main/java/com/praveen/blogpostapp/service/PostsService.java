package com.praveen.blogpostapp.service;

import java.util.Date;
import java.util.List;

import com.praveen.blogpostapp.entity.Comments;
import com.praveen.blogpostapp.entity.Posts;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
@Transactional
public interface PostsService {

    public List<Posts> findAll();
	
	public void save(Posts post);
	
	public Posts findById(int id);

	public void deletePost(Posts post);
	public Posts referenceById(int id);

	public List<Posts> findBySearchWord(String word);

	public List<Posts> findContentLike(String word);

	public List<Posts> findByFullText(String word);

	public List<Posts> findByTagId(int id);

	public Page<Posts> getAllPosts(int offset, int pageSize);

	public List<Posts> findByDate(Date fromDate, Date toDate);

	List<Posts> findByAuthorNameAndTagName(List<String> authorName,List<String> tagName);

	List<Posts> findByAuthorName(List<String> authorName);

	List<Posts> findByTagName(List<String> tagName);

}
