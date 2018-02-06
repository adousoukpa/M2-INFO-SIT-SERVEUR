package fr.istic.sit.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Une localisation définie avec une latitude, longitude et une altitude
 */
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double latitude;

    private Double longitude;

    private Double altitude;

    public Double getLatitude() {
        return latitude;
    }

    public Location latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Location longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public Location altitude(Double altitude) {
        this.altitude = altitude;
        return this;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        if (location == null) {
            return false;
        }

        boolean latEquals = getLatitude() == location.getLatitude();
        boolean longEquals= getLongitude() == location.getLongitude();
        boolean altEquals = getAltitude() == location.getAltitude();

        return latEquals && longEquals && altEquals;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getLatitude()+getLongitude()+getAltitude());
    }

    @Override
    public String toString() {
        return "Localisation{" +
            " latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", altitude=" + getAltitude() +
            "}";
    }
}
