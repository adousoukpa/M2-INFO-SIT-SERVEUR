package fr.istic.sit.domain;

import java.time.LocalDateTime;

public class LocationDrone extends Localisation{

    LocalDateTime time;

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
