package fr.istic.sit.domain;

import java.time.LocalDateTime;

/**
 * Un ordre reçu par le serveur à envoyer au drone
 */
public class Order {

    Location location;

    boolean takePicture;

    LocalDateTime time;

    public Order(Location location, boolean takePicture, LocalDateTime time) {
        this.location = location;
        this.takePicture = takePicture;
        this.time = time;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isTakePicture() {
        return takePicture;
    }

    public void setTakePicture(boolean takePicture) {
        this.takePicture = takePicture;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Order{" +
            "localisation=" + location +
            ", takePicture=" + takePicture +
            '}';
    }
}
