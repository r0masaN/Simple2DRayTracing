package romasan.simple2draytracing.Engine.Objects.LightSources;

import javafx.scene.paint.Color;
import romasan.simple2draytracing.Engine.Objects.ColoredObjects.ColoredCircle;
import romasan.simple2draytracing.Engine.Objects.Primitives.Point2D;

public class CircleLightSource extends ColoredCircle {
    private LightSource lightSource;

    public CircleLightSource(final Point2D coordinates, final double radius, final Color color, final double opacity, final LightSource lightSource) {
        super(coordinates, radius, color, opacity);
        this.lightSource = lightSource;
    }

    public LightSource getLightSource() {
        return this.lightSource;
    }

    public void setLightSource(final LightSource lightSource) {
        this.lightSource = lightSource;
    }

    @Override
    public int hashCode() {
        return this.lightSource.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;

        final CircleLightSource other = (CircleLightSource) obj;
        return ((ColoredCircle) this).equals(other) && this.lightSource.equals(other.lightSource);
    }
}
