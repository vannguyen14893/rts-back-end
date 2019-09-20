/**
 * 
 */
package com.cmc.recruitment.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
@Table(name = "foreign_language")
public class ForeignLanguage implements Serializable {

  /**
   * @description: .
   * @author: Lcnguyen
   * @created_date: Mar 6, 2018
   * @modifier: User
   * @modifier_date: Mar 6, 2018
   */
  private static final long serialVersionUID = 2710841404788747440L;
  
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
  
  @ManyToMany(mappedBy = "foreignLanguageCollection")
  @JsonIgnore
  private List<Request> requestCollection;
  
  @Column(name = "is_delete")
	private Boolean isDelete;
  
  public ForeignLanguage(Long id) {
    this.id = id;
  }

 
  @Override
  public String toString() {
    return "demo.ForeignLanguage[ id=" + id + " ]";
  }

  /**
   * @param title
   * @param description
   */
  public ForeignLanguage(String title, String description) {
	super();
	this.title = title;
	this.description = description;
  }
}
