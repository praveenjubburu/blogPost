package com.praveen.blogpostapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.Proxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="tags")
@Proxy(lazy=false)
public class Tags {

	public Tags(){

	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="created_at")
	private Date createdAt;

	@ManyToMany(mappedBy = "tags", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	List<Posts> postsList=new ArrayList<>();
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Column(name="updated_at")
	private Date updatedAt;


	@JsonIgnore
	public List<Posts> getPosts(){
		return postsList;
	}
}
