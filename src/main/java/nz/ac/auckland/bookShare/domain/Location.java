package nz.ac.auckland.bookShare.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Location {
	
	@Column(name="LATITUDE")
	private double _latitude;
	
	@Column(name="LONGITUDE")
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
}
