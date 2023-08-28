package com.praveen.blogpostapp.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name="posts")
public class Posts {
	public List<Tags> getTags() {
		return tags;
	}

	public Posts(){

	}
	@JsonManagedReference
	public List<Comments> getComment() {
		return comment;
	}

	public void setComment(List<Comments> comment) {
		this.comment = comment;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getExcerpt() {
		return excerpt;
	}

	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(Date publishedAt) {
		this.publishedAt = publishedAt;
	}

	public String getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(String isPublished) {
		this.isPublished = isPublished;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
    
	@OneToMany(mappedBy="posts",cascade = CascadeType.ALL)
	private List<Comments> comment=new ArrayList<>();

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name="post_tag",joinColumns = {
			@JoinColumn(name="post_id",referencedColumnName = "id")
	},inverseJoinColumns = {
			@JoinColumn(name="tag_id",referencedColumnName = "id")
	}
	)
	private List<Tags> tags=new ArrayList<>();
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="excerpt")
	private String excerpt;
	
	@Column(name="content")
	private String content;
	
	@Column(name="author")
	private String author;
	
	@Column(name="published_at")
	private Date publishedAt;
	
	@Column(name="is_published")
	private String isPublished;
	
	@Column(name="create_at")
	private Date createdAt;
	
	@Column(name="updated_at")
	private Date updatedAt;

	public void setTags(List<Tags> tags) {
		this.tags = tags;
	}

	public void addTag(Tags tag) {
		tags.add(tag);
	}
	public void addComment(Comments comments){
		comment.add(comments);
	}

}
