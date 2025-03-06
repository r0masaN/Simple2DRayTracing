package romasan.simple2draytracing.Engine.Objects;

import javafx.scene.paint.Color;

import java.util.Objects;

// light source Circle class (for light sources)
public final class LightSourceCircle extends AbstractCircle {
    private Color lightColor;
    private double lightOpacity, lightDistance, startAngleDegree, angleDegrees;
    private boolean isActive;

    public LightSourceCircle(final Point center, final double radius, final Color color, final double opacity,
                             final Color lightColor, final double lightOpacity, final double lightDistance,
                             final double startAngleDegree, final double angleDegrees) {
        super(center, radius, color, opacity);
        this.lightColor = lightColor;
        this.lightOpacity = lightOpacity;
        this.lightDistance = lightDistance;
        this.startAngleDegree = startAngleDegree;
        this.angleDegrees = angleDegrees;
        this.isActive = true;
    }

    public void addLightOpacity(final double deltaLightOpacity) {
        if (deltaLightOpacity > 0.0 && deltaLightOpacity <= 1.0 && this.lightOpacity + deltaLightOpacity <= 1.0 + 10e-6)
            this.lightOpacity = Math.round(Math.min(this.lightOpacity + deltaLightOpacity, 1.0) * 10e3) / 10e3;
    }

    public void subtractLightOpacity(final double deltaLightOpacity) {
        if (deltaLightOpacity > 0.0 && deltaLightOpacity <= 1.0 && this.lightOpacity - deltaLightOpacity > -10e-6)
            this.lightOpacity = Math.round(Math.max(this.lightOpacity - deltaLightOpacity, 0.0) * 10e3) / 10e3;
    }

    public void addLightDistance(final double deltaLightDistance) {
        if (deltaLightDistance > 0.0 && this.lightDistance + deltaLightDistance <= 2500.0 + 10e-6)
            this.lightDistance = Math.round(Math.min(this.lightDistance + deltaLightDistance, 2500.0) * 10e3) / 10e3;
    }

    public void subtractLightDistance(final double deltaLightDistance) {
        if (deltaLightDistance > 0.0 && this.lightDistance - deltaLightDistance >= -10e-6)
            this.lightDistance = Math.round(Math.max(this.lightDistance - deltaLightDistance, 0.0) * 10e3) / 10e3;
    }

    public void addStartAngleDegree(final double deltaStartAngleDegree) {
        if (deltaStartAngleDegree > 0.0)
            this.startAngleDegree = (this.startAngleDegree + deltaStartAngleDegree) % 360.0;
    }

    public void subtractStartAngleDegree(final double deltaStartAngleDegree) {
        if (deltaStartAngleDegree > 0.0) {
            this.startAngleDegree = (this.startAngleDegree - deltaStartAngleDegree) % 360.0;
            if (this.startAngleDegree < 0.0) this.startAngleDegree += 360.0;
        }
    }

    public void addAngleDegrees(final double deltaAngleDegrees) {
        if (deltaAngleDegrees > 0.0) this.angleDegrees = (this.angleDegrees + deltaAngleDegrees) % 360.0;
    }

    public void subtractAngleDegrees(final double deltaAngleDegree) {
        if (deltaAngleDegree > 0.0) {
            this.angleDegrees = (this.angleDegrees - deltaAngleDegree) % 360.0;
            if (this.angleDegrees < 0.0) this.angleDegrees += 360.0;
        }
    }

    public void toggleActive() {
        this.isActive = !this.isActive;
    }

    public Color getLightColor() {
        return this.lightColor;
    }

    public double getLightOpacity() {
        return this.lightOpacity;
    }

    public double getLightDistance() {
        return this.lightDistance;
    }

    public double getStartAngleDegree() {
        return this.startAngleDegree;
    }

    public double getAngleDegrees() {
        return this.angleDegrees;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setLightColor(final Color lightColor) {
        this.lightColor = lightColor;
    }

    public void setLightOpacity(final double lightOpacity) {
        this.lightOpacity = lightOpacity;
    }

    public void setLightDistance(final double lightDistance) {
        this.lightDistance = lightDistance;
    }

    public void setStartAngleDegree(final double startAngleDegree) {
        this.startAngleDegree = startAngleDegree;
    }

    public void setAngleDegrees(final double angleDegrees) {
        this.angleDegrees = angleDegrees;
    }

    public void setActive(final boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.lightColor, this.lightOpacity, this.lightDistance,
                this.startAngleDegree, this.angleDegrees, this.isActive);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass() || !super.equals(obj)) return false;

        final LightSourceCircle other = (LightSourceCircle) obj;
        return this.lightColor.equals(other.lightColor) && Double.compare(this.lightOpacity, other.lightOpacity) == 0 &&
                Double.compare(this.lightDistance, other.lightDistance) == 0 && Double.compare(this.startAngleDegree, other.startAngleDegree) == 0
                && Double.compare(this.angleDegrees, other.angleDegrees) == 0 && this.isActive == other.isActive;
    }
}
