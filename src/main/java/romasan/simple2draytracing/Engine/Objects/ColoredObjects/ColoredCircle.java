package romasan.simple2draytracing.Engine.Objects.ColoredObjects;

import javafx.scene.paint.Color;
import romasan.simple2draytracing.Engine.Objects.Figures.Circle;
import romasan.simple2draytracing.Engine.Objects.Primitives.Point2D;

import java.util.Objects;

public class ColoredCircle extends Circle {
    private Color color;
    private double opacity;

    public ColoredCircle(final Point2D coordinates, final double radius, final Color color, final double opacity) {
        super(coordinates, radius);
        this.color = color;
        this.opacity = opacity;
    }

    public void addOpacity(final double deltaOpacity) {
        if (deltaOpacity > 0.0 && deltaOpacity <= 1.0 && this.opacity + deltaOpacity <= 1.0 + 10e-9)
            this.opacity = Math.min(this.opacity + deltaOpacity, 1.0);
    }

    public void subtractOpacity(final double deltaOpacity) {
        if (deltaOpacity > 0.0 && deltaOpacity <= 1.0 && this.opacity - deltaOpacity >= -10e-9)
            this.opacity = Math.max(this.opacity - deltaOpacity, 0.0);
    }

    public Color getColor() {
        return this.color;
    }

    public double getOpacity() {
        return this.opacity;
    }

    public void setOpacity(final double opacity) {
        this.opacity = opacity;
    }

    public void setColor(final Color color) {
        this.color = color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(((Circle) this), this.color, this.opacity);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;

        final ColoredCircle other = (ColoredCircle) obj;
        return ((Circle) this).equals(other) && this.color.equals(other.color) && Double.compare(this.opacity, other.opacity) == 0;
    }
}
