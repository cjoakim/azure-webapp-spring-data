package com.chrisjoakim.azure.sdweb.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Spring JPA Model for the Airports table.
 * Chris Joakim, Microsoft, 2020/01/07
 */

@Entity
@Table(name="Airports")
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ddl is -> id  INT  IDENTITY  PRIMARY KEY,
    private Integer id;

    private String name;
    private String city;
    private String country;
    private String iataCode;
    private String icaoCode;
    private Double latitude;
    private Double longitude;
    private Double altitude;
    private Double timezoneNum;
    private String dst;
    private String timezoneCode;
    
    @Override
    public String toString() {
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append("Airport id:" + id);
    	sb.append(", iataCode:" + iataCode);
    	sb.append(", name:" + name);
    	sb.append(", country:" + country);
    	return sb.toString();
    }

    // generated getters & setters below
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIataCode() {
		return iataCode;
	}

	public void setIataCode(String iataCode) {
		this.iataCode = iataCode;
	}

	public String getIcaoCode() {
		return icaoCode;
	}

	public void setIcaoCode(String icaoCode) {
		this.icaoCode = icaoCode;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getAltitude() {
		return altitude;
	}

	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}

	public Double getTimezoneNum() {
		return timezoneNum;
	}

	public void setTimezoneNum(Double timezoneNum) {
		this.timezoneNum = timezoneNum;
	}

	public String getDst() {
		return dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	public String getTimezoneCode() {
		return timezoneCode;
	}

	public void setTimezoneCode(String timezoneCode) {
		this.timezoneCode = timezoneCode;
	}
    
}
