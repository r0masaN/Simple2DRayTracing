package romasan.simple2draytracing.Engine;

import javafx.scene.paint.Color;

// Circle class (also for light sources)
public final class Circle {
    private Point center;
    private double radius;
    private boolean isLightSource;
    private Color color;
    private double lightOpacity;

    public Circle(final Point center, final double radius, final boolean isLightSource, final Color color, final double lightOpacity) {
        this.center = center;
        this.radius = radius;
        this.isLightSource = isLightSource;
        this.color = color;
        this.lightOpacity = lightOpacity;
    }

    public boolean contains(final Point point) {
        return Math.pow((point.getX() - this.center.getX()), 2) + Math.pow((point.getY() - this.center.getY()), 2) <= Math.pow(this.radius, 2);
    }

    public void move(final Point point) {
        this.center.add(point);
    }

    public Point getCenter() {
        return this.center;
    }

    public double getRadius() {
        return this.radius;
    }

    public boolean isLightSource() {
        return this.isLightSource;
    }

    public Color getColor() {
        return this.color;
    }

    public double getLightOpacity() {
        return this.lightOpacity;
    }

    public void setCenter(final Point center) {
        this.center = center;
    }

    public void setRadius(final double radius) {
        this.radius = radius;
    }

    public void setLightSource(final boolean isLightSource) {
        this.isLightSource = isLightSource;
    }

    public void setColor(final Color color) {
        this.color = color;
    }

    public void setLightOpacity(final double lightOpacity) {
        this.lightOpacity = lightOpacity;
    }
}
