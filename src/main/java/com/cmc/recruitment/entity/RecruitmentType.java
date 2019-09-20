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
@Table(name = "recruitment_type")
public class RecruitmentType implements Serializable {

  /**
   * @description: .
   * @author: VDHoan
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
  
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "recruitmentTypeId")
  @JsonIgnore
  private List<Request> requestCollection;
  
  @Column(name = "is_delete")
	private Boolean isDelete;

  public RecruitmentType(Long id) {
    this.id = id;
  }



  @Override
  public String toString() {
    return "demo.RecruitmentType[ id=" + id + " ]";
  }

  /**
   * 
   * @param title
   * @param description
   */
  public RecruitmentType(String title, String description) {
	super();
	this.title = title;
	this.description = description;
  }
}
