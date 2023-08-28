package com.praveen.blogpostapp.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name="comments")
public class Comments {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int comment_id;
	
	private String name;
	
	private String email;
	
	private String comment;


	public Comments(int comment_id, String name, String email, String comment, Date createdAt, Date updatedAt) {
		super();
		this.comment_id = comment_id;
		this.name = name;
		this.email = email;
		this.comment = comment;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="post_id")
	private Posts posts;
	private Date createdAt;
	
	private Date updatedAt;
	
	public Comments() {
		
	}
	public int getId() {
		return comment_id;
	}

	public void setId(int id) {
		this.comment_id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
	@JsonBackReference
     public Posts getPost() {
		return posts;
	}

	public void setPost(Posts post) {
		this.posts = post;
	}
}
