package com.praveen.blogpostapp.service;

import com.praveen.blogpostapp.dao.TagsRepository;
import com.praveen.blogpostapp.entity.Tags;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagsServiceImplementation implements TagsService{

    TagsRepository tagsRepository;

    public TagsServiceImplementation(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    @Override
    public List<Tags> findAll() {
        return tagsRepository.findAll();
    }

    @Override
    public Tags findById() {
        return null;
    }

    @Override
    public void save(Tags tag) {

    }

    @Override
    public Tags findByName(String name) {
        return tagsRepository.findByName(name);
    }

}
