package romasan.simple2draytracing.Engine.Objects;

import javafx.scene.paint.Color;

// default Circle class (for default object)
public final class DefaultCircle extends AbstractCircle {

    public DefaultCircle(final Point center, final double radius, final Color color, final double opacity) {
        super(center, radius, color, opacity);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        return super.equals(obj);
    }
}
