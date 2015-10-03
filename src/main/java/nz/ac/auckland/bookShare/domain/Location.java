package nz.ac.auckland.bookShare.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Bean class to represent a Location.
 * 
 * A location is a value type and has a 
 * latitude and longitude.
 * 
 * @author Greggory Tan
 *
 */
@XmlRootElement
@XmlType(name="location")
@XmlAccessorType(XmlAccessType.FIELD)
@Embeddable
public class Location {
	
	@Column(name="LATITUDE")
	@XmlElement(name="latitude")
	private double _latitude;
	
	@Column(name="LONGITUDE")
	@XmlElement(name="longitude")
	private double _longitude;
	
	//Default constructor method for JAXB
	protected Location() {
		this(0, 0);
	}
	
	public Location(double lat, double lng) {
		_latitude = lat;
		_longitude = lng;
	}

	public double getLatitude() {
		return _latitude;
	}
	
	public double getLongitude() {
		return _longitude;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Location))
            return false;
        if (obj == this)
            return true;

        Location rhs = (Location) obj;
        return new EqualsBuilder().
            append(_latitude, rhs._latitude).
            append(_longitude, rhs._longitude).
            isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). 
	            append(_latitude).
	            append(_longitude).
	            toHashCode();
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("(");
		buffer.append(_latitude);
		buffer.append(",");
		buffer.append(_longitude);
		buffer.append(")");
		
		return buffer.toString();
	}
}
