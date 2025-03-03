package romasan.simple2draytracing.Engine.Objects;

import javafx.scene.paint.Color;

public final class LightSourceCircle extends AbstractCircle {
    private Color lightColor;
    private double lightOpacity;
    private boolean isActive;

    public LightSourceCircle(final Point center, final double radius, final Color color, final double opacity, final Color lightColor, final double lightOpacity) {
        super(center, radius, color, opacity);
        this.lightColor = lightColor;
        this.lightOpacity = lightOpacity;
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

    public void toggleActive() {
        this.isActive = !this.isActive;
    }

    public Color getLightColor() {
        return this.lightColor;
    }

    public double getLightOpacity() {
        return this.lightOpacity;
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

    public void setActive(final boolean isActive) {
        this.isActive = isActive;
    }
}
