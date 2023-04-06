package com.fundamentosSprinboot.fundamentos.entity;

import jakarta.persistence.*;

@Entity
@Table(name="post")
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_post",nullable = false,unique = true)
  private Long id;

  @Column(name = "description",length = 255)
    private String description;
  //muchos post pertenecen a un usuario
  @ManyToOne
  private Users users;

  public Post() {
  }

  public Post(String description, Users users) {
    this.description = description;
    this.users = users;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Users getUser() {
    return users;
  }

  public void setUser(Users users) {
    this.users = users;
  }

  @Override
  public String toString() {
    return "Post{" +
            "id=" + id +
            ", description='" + description + '\'' +
            ", users=" + users +
            '}';
  }
}
