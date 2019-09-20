/**
 * 
 */
package com.cmc.recruitment.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity()
@Getter
@Setter
@NoArgsConstructor
@Table(name = "groups")
public class Group implements Serializable {

  private static final long serialVersionUID = 2726906038615693206L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @Size(max = 255) 
  @Column(name = "title")
  private String title;
  @Size(max = 1000)
  @Column(name = "description")
  private String description;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
  @JsonIgnore
  private List<Request> requestCollection;

  @ManyToMany(mappedBy = "groupCollection")
  @JsonIgnore
  private Set<User> userCollection;
  
  public Group(Long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "demo.Request[ id=" + id + " ]";
  }

  /**
   * @param Title
   * @param description
   */
  public Group(String title, String description) {
    super();
    this.title = title;
    this.description = description;
  }
}
