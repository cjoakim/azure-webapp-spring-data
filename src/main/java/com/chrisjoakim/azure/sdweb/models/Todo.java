package com.chrisjoakim.azure.sdweb.models;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name="Todo")
public class Todo {
	
	// constants
	public static final Integer STATUS_ACTIVE    = 1;
	public static final Integer STATUS_COMPLETED = 9;
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ddl is -> id  INT  IDENTITY  PRIMARY KEY,
    private Integer   id;
    
    private Integer   priority;
    private Integer   status;
    private String    name;
    private Timestamp created;
    private Timestamp updated;
    
    @PrePersist
    public void onPrePersist() {
    	
        if (this.created == null) {
        	this.created = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        }
    }
      
    @PreUpdate
    public void onPreUpdate() {
    	
    	this.updated = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
    }
    
    
    @Override
    public String toString() {
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append("Todo id:" + id);
    	sb.append(", priority:" + priority);
    	sb.append(", status:" + status);
    	sb.append(", name:" + name);
    	sb.append(", created:" + created);
    	sb.append(", updated:" + updated);
    	return sb.toString();
    }

    public void setStatusCompleted() {
    
    	this.setStatus(STATUS_COMPLETED);
    }
    
//    public void update() {
//    	
//    	Calendar cal = Calendar.getInstance();
//    	this.updated = new java.sql.Timestamp(cal.getTime().getTime());
//    }
    
    // generated getters & setters below
    

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getPriority() {
		return priority;
	}


	public void setPriority(Integer priority) {
		this.priority = priority;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Timestamp getCreated() {
		return created;
	}


	public void setCreated(Timestamp created) {
		this.created = created;
	}


	public Timestamp getUpdated() {
		return updated;
	}


	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}

}
