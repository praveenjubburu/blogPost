package com.praveen.blogpostapp.dao;

import com.praveen.blogpostapp.entity.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsRepository extends JpaRepository<Tags, Integer> {

    public Tags findByName(String name);

}
