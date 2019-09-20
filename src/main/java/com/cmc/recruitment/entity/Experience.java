/**
 * 
 */
package com.cmc.recruitment.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description: .
 * @author: VDHoan
 * @created_date: Mar 6, 2018
 * @modifier: User
 * @modifier_date: Mar 6, 2018
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "experience")
public class Experience implements Serializable{

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Mar 6, 2018
   * @modifier: User
   * @modifier_date: Mar 6, 2018
   */
  private static final long serialVersionUID = 4197942078910386097L;

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
  
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "experienceId")
  @JsonIgnore
  private List<Request> requestCollection;
  
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "experienceId")
  @JsonIgnore
  private List<Cv> cvCollection;
  
  @Column(name = "is_delete")
	private Boolean isDelete;

  public Experience(Long id) {
    this.id = id;
  }

 
  @Override
  public String toString() {
    return "demo.Experience[ id=" + id + " ]";
  }

	/**
	 * @param title
	 * @param description
	 */
  public Experience(String title, String description) {
	super();
	this.title = title;
	this.description = description;
  }
}
