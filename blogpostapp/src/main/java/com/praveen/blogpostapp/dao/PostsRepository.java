package com.praveen.blogpostapp.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.praveen.blogpostapp.entity.Posts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Integer> {
    @Query(value = "select p from Posts p Join fetch p.tags t where t.name=:word")
    public List<Posts> findBySearchWord(@Param("word") String word);


    @Query(value = "select p from Posts p Join fetch p.tags t where t.id=:tagId")
    public List<Posts> findByTagId(int tagId);
    public List<Posts> findByContentContaining(String word);

    @Query(value = "select p from Posts p where p.author like ?1 OR p.content like ?1 OR p.title like ?1")
    public List<Posts> findByFullText(String word);

    Page<Posts> findAll(Pageable pageable);

    @Query("select p from Posts p where p.publishedAt BETWEEN ?1 AND ?2")
    public List<Posts> findByDate(Date fromDate, Date toDate);

    @Query(value = "select p from Posts p Join fetch p.tags t where p.author in (?1) AND t.name in (?2)")
    public List<Posts> findByAuthorNameAndTagName(List<String> A,List<String> B);
    @Query(value = "select p from Posts p where p.author in (?1)")
    public List<Posts> findByAuthorName(List<String> A);
    @Query(value = "select p from Posts p Join fetch p.tags t where t.name in (?1)")
    public List<Posts> findByTagName(List<String> A);

}
