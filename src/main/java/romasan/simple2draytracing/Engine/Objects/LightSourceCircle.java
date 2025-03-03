package romasan.simple2draytracing.Engine.Objects;

import javafx.scene.paint.Color;
import romasan.simple2draytracing.Engine.Point;

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
