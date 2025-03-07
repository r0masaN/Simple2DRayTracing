package romasan.simple2draytracing.Engine.Objects;

import java.util.Objects;

// simple Line class
public final class Line {
    // const id for correct hashCode() working
    private final long id;
    private Point start, end;
    private double k, b;

    public Line(final Point start, final Point end) {
        this.updatePoints(start, end);

        this.id = this.generateId();
    }

    private long generateId() {
        return Objects.hash(this.start, this.end);
    }

    private void updatePoints(final Point start, final Point end) {
        boolean recalculate = false;

        if (start != null) {
            this.start = start;
            recalculate = true;
        }
        if (end != null) {
            this.end = end;
            recalculate = true;
        }

        if (recalculate) {
            this.k = (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
            this.b = (this.start.getY() * this.end.getX() - this.end.getY() * this.start.getX()) / (this.end.getX() - this.start.getX());
        }
    }

    public Point intersectionPoint(final AbstractCircle circle) {
        final double cx = circle.getCenter().getX(), cy = circle.getCenter().getY(), r = circle.getRadius();

        if (Math.abs(this.start.getX() - this.end.getX()) < 10e-6) {
            final double x = this.start.getX(), D = Math.pow(r, 2) - Math.pow(x - cx, 2);
            if (D < 0) return null;

            final double y1 = cy + Math.sqrt(D), y2 = cy - Math.sqrt(D);
            final Point p1 = new Point(x, y1), p2 = new Point(x, y2);

            return chooseClosestPoint(p1, p2);
        }

        if (Math.abs(this.start.getY() - this.end.getY()) < 10e-6) {
            final double y = this.start.getY(), D = Math.pow(r, 2) - Math.pow(y - cy, 2);
            if (D < 0) return null;

            final double x1 = cx + Math.sqrt(D), x2 = cx - Math.sqrt(D);
            final Point p1 = new Point(x1, y), p2 = new Point(x2, y);

            return chooseClosestPoint(p1, p2);
        }

        final double k = this.getK(), b = this.getB();
        final double A = 1 + k * k, B = 2 * (k * (b - cy) - cx), C = cx * cx + (b - cy) * (b - cy) - r * r;
        final double D = B * B - 4 * A * C;
        if (D < 0) return null;

        final double x1 = (-B - Math.sqrt(D)) / (2 * A), x2 = (-B + Math.sqrt(D)) / (2 * A),
                y1 = k * x1 + b, y2 = k * x2 + b;
        final Point p1 = new Point(x1, y1), p2 = new Point(x2, y2);

        return chooseClosestPoint(p1, p2);
    }

    private Point chooseClosestPoint(final Point p1, final Point p2) {
        final boolean p1Valid = isPointOnSegment(p1), p2Valid = isPointOnSegment(p2);

        if (p1Valid && p2Valid) return this.start.distance(p1) <= this.start.distance(p2) ? p1 : p2;
        else if (p1Valid) return p1;
        else if (p2Valid) return p2;
        else return null;
    }

    private boolean isPointOnSegment(final Point p) {
        final double startX = this.start.getX(), endX = this.end.getX(),
                startY = this.start.getY(), endY = this.end.getY(),
                pX = p.getX(), pY = p.getY();
        return Math.min(startX, endX) <= pX && pX <= Math.max(startX, endX) &&
                Math.min(startY, endY) <= pY && pY <= Math.max(startY, endY);
    }

    public Point getStart() {
        return this.start;
    }

    public Point getEnd() {
        return this.end;
    }

    public double getK() {
        return this.k;
    }

    public double getB() {
        return this.b;
    }

    public void setStart(final Point start) {
        this.updatePoints(start, null);
    }

    public void setEnd(final Point end) {
        this.updatePoints(null, end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;

        final Line other = (Line) obj;
        return this.start.equals(other.start) && this.end.equals(other.end);
    }
}
