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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author User
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "candidate_status")
public class CandidateStatus implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6150518159135711151L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
	
    @Column(name = "title")
    private String title;
    
    @Column(name = "description")
    private String description;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "statusId")
    @JsonIgnore
    private List<Candidate> candidateCollection;

    public CandidateStatus(Long id) {
        this.id = id;
    }

    public CandidateStatus(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    /**
	 * @param title
	 * @param description
	 */
	public CandidateStatus(String title, String description) {
		super();
		this.title = title;
		this.description = description;
	}
	
    @Override
    public String toString() {
        return "demo.CandidateStatus[ id=" + id + " ]";
    }

    
}
