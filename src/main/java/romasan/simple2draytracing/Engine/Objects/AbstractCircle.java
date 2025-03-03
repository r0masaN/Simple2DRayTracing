package romasan.simple2draytracing.Engine.Objects;

import javafx.scene.paint.Color;

public sealed class AbstractCircle permits DefaultCircle, LightSourceCircle {
    private Point center;
    private double radius;
    private Color color;
    private double opacity;

    AbstractCircle(final Point center, final double radius, final Color color, final double opacity) {
        this.center = center;
        this.radius = radius;
        this.color = color;
        this.opacity = opacity;
    }

    public boolean contains(final Point point) {
        return Math.pow((point.getX() - this.center.getX()), 2) + Math.pow((point.getY() - this.center.getY()), 2) <= Math.pow(this.radius, 2);
    }

    public void move(final Point point) {
        this.center.add(point);
    }

    public void addRadius(final double deltaRadius) {
        if (deltaRadius > 0.0 && this.radius + deltaRadius <= 1000.0)
            this.radius = Math.min(this.radius + deltaRadius, 1000.0);
    }

    public void subtractRadius(final double deltaRadius) {
        if (deltaRadius > 0.0 && this.radius - deltaRadius >= 0.0)
            this.radius = Math.max(this.radius - deltaRadius, 0.0);
    }

    public void addOpacity(final double deltaOpacity) {
        if (deltaOpacity > 0.0 && deltaOpacity <= 1.0 && this.opacity + deltaOpacity <= 1.0)
            this.opacity = Math.min(this.opacity + deltaOpacity, 1.0);
    }

    public void subtractOpacity(final double deltaOpacity) {
        if (deltaOpacity > 0.0 && deltaOpacity <= 1.0 && this.opacity - deltaOpacity >= 0.0)
            this.opacity = Math.max(this.opacity - deltaOpacity, 0.0);
    }

    public Point getCenter() {
        return this.center;
    }

    public double getRadius() {
        return this.radius;
    }

    public Color getColor() {
        return this.color;
    }

    public double getOpacity() {
        return this.opacity;
    }

    public void setCenter(final Point center) {
        this.center = center;
    }

    public void setRadius(final double radius) {
        this.radius = radius;
    }

    public void setColor(final Color color) {
        this.color = color;
    }

    public void setOpacity(final double opacity) {
        this.opacity = opacity;
    }
}
