package com.praveen.blogpostapp.service;

import com.praveen.blogpostapp.entity.Tags;

import java.util.List;

public interface TagsService {

    public List<Tags> findAll();

    public Tags findById();

    public void save(Tags tag);

    public Tags findByName(String name);

}
