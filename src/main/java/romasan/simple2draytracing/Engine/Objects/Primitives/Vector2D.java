package romasan.simple2draytracing.Engine.Objects.Primitives;

import java.util.Objects;

public class Vector2D {
    private Point2D coordinates;
    private double angleDegrees;
    private double distance;

    public Vector2D(final Point2D coordinates, final double angleDegrees, final double distance) {
        this.coordinates = coordinates;
        this.angleDegrees = angleDegrees;
        this.distance = distance;
    }

    public Point2D getCoordinates() {
        return this.coordinates;
    }

    public double getAngleDegrees() {
        return this.angleDegrees;
    }

    public double getDistance() {
        return this.distance;
    }

    public void setDestination(final Point2D coordinates) {
        this.coordinates = coordinates;
    }

    public void setAngleDegrees(final double angleDegrees) {
        this.angleDegrees = angleDegrees;
    }

    public void setDistance(final double distance) {
        this.distance = distance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.coordinates, this.angleDegrees, this.distance);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;

        final Vector2D other = (Vector2D) obj;
        return this.coordinates.equals(other.coordinates) && Double.compare(this.angleDegrees, other.angleDegrees) == 0 &&
                Double.compare(this.distance, other.distance) == 0;
    }
}
