/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.recruitment.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

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
@Table(name = "department")
public class Department implements Serializable, Comparable<Department> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2415793262886770786L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
	
    @Column(name = "title", unique = true, nullable = false)
    private String title;
    
    @Column(name = "description")
    private String description;
    
    @OneToMany(mappedBy = "departmentId")
    @JsonIgnore
    private Collection<User> userCollection;
    @OneToMany(mappedBy = "departmentId")
    @JsonIgnore
    private Collection<Request> requestCollection;
    public Department(Long id) {
        this.id = id;
    }
    
    @Column(name = "is_delete")
	private Boolean isDelete;
    
    public Department(String title, String description) {
		super();
		this.title = title;
		this.description = description;
	}

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Department)) {
            return false;
        }
        Department other = (Department) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "demo.Department[ id=" + id + " ]";
    }
    @Override
	public int compareTo(Department o) {
		return this.title.compareTo(o.title);
	}

    
}
