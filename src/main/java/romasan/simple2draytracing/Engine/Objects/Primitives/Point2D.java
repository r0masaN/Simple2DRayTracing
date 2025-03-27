package romasan.simple2draytracing.Engine.Objects.Primitives;

import java.util.Objects;

public class Point2D {
    private double x, y;

    public Point2D(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public Point2D(final Point2D other) {
        this.x = other.x;
        this.y = other.y;
    }

    public Point2D add(final double v) {
        this.x += v;
        this.y += v;

        return this;
    }

    public void add(final Point2D other) {
        this.x += other.x;
        this.y += other.y;
    }

    public Point2D subtract(final double v) {
        this.x -= v;
        this.y -= v;

        return this;
    }

    public void subtract(final Point2D other) {
        this.x -= other.x;
        this.y -= other.y;
    }

    public double distance(final Point2D other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(final double x) {
        this.x = x;
    }

    public void setY(final double y) {
        this.y = y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;

        final Point2D other = (Point2D) obj;
        return Double.compare(this.x, other.x) == 0 && Double.compare(this.y, other.y) == 0;
    }
}
