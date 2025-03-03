package romasan.simple2draytracing.Engine.Objects;

import javafx.scene.paint.Color;

public final class LightSourceCircle extends AbstractCircle {
    private Color lightColor;
    private double lightOpacity;
    private double lightDistance;
    private boolean isActive;

    public LightSourceCircle(final Point center, final double radius, final Color color, final double opacity,
                             final Color lightColor, final double lightOpacity, final double lightDistance) {
        super(center, radius, color, opacity);
        this.lightColor = lightColor;
        this.lightOpacity = lightOpacity;
        this.lightDistance = lightDistance;
        this.isActive = true;
    }

    public void addLightOpacity(final double deltaLightOpacity) {
        if (deltaLightOpacity > 0.0 && deltaLightOpacity <= 1.0 && this.lightOpacity < 1.0) {
            final double newLightOpacity = this.lightOpacity + deltaLightOpacity;
            if (newLightOpacity <= 1.0) this.lightOpacity = Math.min(newLightOpacity, 1.0);
        }
    }

    public void subtractLightOpacity(final double deltaLightOpacity) {
        if (deltaLightOpacity > 0.0 && deltaLightOpacity <= 1.0 && this.lightOpacity > 0.0) {
            final double newLightOpacity = this.lightOpacity - deltaLightOpacity;
            if (newLightOpacity >= 0.0) this.lightOpacity = Math.max(newLightOpacity, 0.0);
        }
    }

    public void addLightDistance(final double deltaLightDistance) {
        if (deltaLightDistance > 0.0 && this.lightDistance + deltaLightDistance <= 2500.0)
            this.lightDistance = Math.min(this.lightDistance + deltaLightDistance, 2500.0);
    }

    public void subtractLightDistance(final double deltaLightDistance) {
        if (deltaLightDistance > 0.0 && this.lightDistance - deltaLightDistance >= 0)
            this.lightDistance = Math.max(this.lightDistance - deltaLightDistance, 0.0);
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

    public void setActive(final boolean isActive) {
        this.isActive = isActive;
    }
}
