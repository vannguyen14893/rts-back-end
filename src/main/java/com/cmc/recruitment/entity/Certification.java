package com.cmc.recruitment.entity;

import java.io.Serializable;
import java.util.Set;

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

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "certification")
public class Certification implements Serializable {
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

	@ManyToMany(mappedBy = "certificationCollection")
	@JsonIgnore
	private Set<Request> requestCollection;

	@JsonIgnore
	@ManyToMany(mappedBy = "certificationCollection")
	private Set<Cv> cvCollection;

	@Column(name = "is_delete")
	private Boolean isDelete;

	@Override
	public String toString() {
		return "demo.Certification[ id=" + id + " ]";
	}

	public Certification(Long id) {
		this.id = id;
	}

	public Certification(String title, String description) {
		super();
		this.title = title;
		this.description = description;
	}
}
