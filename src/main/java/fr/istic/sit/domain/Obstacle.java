package fr.istic.sit.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Obstacle.
 */
@Document(collection = "obstacle")
public class Obstacle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("pointA")
    private Location pointA;

    @Field("pointB")
    private Location pointB;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Obstacle name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getPointA() {
        return pointA;
    }

    public void setPointA(Location pointA) {
        this.pointA = pointA;
    }

    public Location getPointB() {
        return pointB;
    }

    public void setPointB(Location pointB) {
        this.pointB = pointB;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Obstacle obstacle = (Obstacle) o;
        if (obstacle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), obstacle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Obstacle{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", pointA=" + pointA +
            ", pointB=" + pointB +
            '}';
    }
}
