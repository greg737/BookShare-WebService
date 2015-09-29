package nz.ac.auckland.bookShare.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@XmlRootElement
@XmlType(name="location")
@XmlAccessorType(XmlAccessType.FIELD)
public class Location {
	
	@XmlElement(name="latitude")
	private double _latitude;
	
	@XmlElement(name="longitude")
	private double _longitude;
	
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
