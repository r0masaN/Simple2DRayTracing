package romasan.simple2draytracing.Engine.Objects.Figures;

import romasan.simple2draytracing.Engine.Objects.Primitives.Point2D;

import java.util.Objects;

public class Circle {
    private Point2D coordinates, center;
    private double radius;

    protected Circle(final Point2D coordinates, final double radius) {
        this.coordinates = new Point2D(coordinates);
        this.center = new Point2D(coordinates).add(radius);
        this.radius = radius;
    }

    public boolean contains(final Point2D point) {
        return Math.pow((point.getX() - this.center.getX()), 2) + Math.pow((point.getY() - this.center.getY()), 2) <= Math.pow(this.radius, 2);
    }

    public void move(final Point2D point) {
        this.coordinates.add(point);
        this.center.add(point);
    }

    public void addRadius(final double deltaRadius) {
        if (deltaRadius > 0.0 && this.radius + deltaRadius <= 1000.0 + 10e-9)
            this.radius = Math.min(this.radius + deltaRadius, 1000.0);
    }

    public void subtractRadius(final double deltaRadius) {
        if (deltaRadius > 0.0 && this.radius - deltaRadius >= -10e-9)
            this.radius = Math.max(this.radius - deltaRadius, 0.0);
    }

    public Point2D getCoordinates() {
        return this.coordinates;
    }

    public Point2D getCenter() {
        return this.center;
    }

    public double getRadius() {
        return this.radius;
    }

    public void setCoordinates(final Point2D coordinates) {
        this.coordinates = coordinates;
        this.center = new Point2D(coordinates).add(this.radius);
    }

    public void setCenter(final Point2D center) {
        this.center = center;
        this.coordinates = new Point2D(center).subtract(this.radius);
    }

    public void setRadius(final double radius) {
        this.radius = radius;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.coordinates, this.radius);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;

        final Circle other = (Circle) obj;
        return this.center.equals(other.center) && Double.compare(this.radius, other.radius) == 0;
    }
}
