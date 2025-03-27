package romasan.simple2draytracing.Engine;

import romasan.simple2draytracing.Engine.Objects.Primitives.Point2D;
import romasan.simple2draytracing.Engine.Objects.Primitives.Line2D;
import romasan.simple2draytracing.Engine.Objects.Figures.Circle;
import romasan.simple2draytracing.Engine.Objects.ColoredObjects.ColoredCircle;
import romasan.simple2draytracing.Engine.Objects.LightSources.CircleLightSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Engine class (ray tracing magic here)
public final class Engine {
    private final Map<CircleLightSource, List<Line2D>> lightSources;
    private final List<ColoredCircle> defaultObjects;

    private final static double DEGREES_STEP = 0.03;

    public Engine(final List<Circle> objects) {
        this.lightSources = new HashMap<>();
        this.defaultObjects = new ArrayList<>();

        objects.forEach(object -> {
            switch (object) {
                case CircleLightSource lightSource -> lightSources.put(lightSource, null);
                case ColoredCircle defaultObject -> this.defaultObjects.add(defaultObject);
                default -> throw new IllegalStateException("Unexpected value: " + object);
            }
        });
    }

    // main method \w ray tracing algorithm
    public void algorithm() {
        for (final CircleLightSource lightSource : lightSources.keySet()) {
            final List<Line2D> lightRays = new ArrayList<>((int) Math.ceil(360 / DEGREES_STEP));

            final double startAngleDegree = lightSource.getLightSource().getStartAngleDegree(), endAngleDegree = startAngleDegree + lightSource.getLightSource().getAngleDegrees();
            // spawns (360 / step) light rays in all directions from each light source
            for (double i = startAngleDegree; i < endAngleDegree; i += DEGREES_STEP) {
                final double angleDegree = Math.round(i * 1000.0) / 1000.0;

                if (angleDegree < endAngleDegree) {
                    final Line2D lightRay = new Line2D(new Point2D(lightSource.getCenter()),
                            new Point2D(lightSource.getCenter().getX() + lightSource.getLightSource().getLightDistance() * Math.cos(Math.toRadians(angleDegree)),
                                    lightSource.getCenter().getY() + lightSource.getLightSource().getLightDistance() * Math.sin(Math.toRadians(angleDegree))));

                    for (final ColoredCircle defaultObject : this.defaultObjects) {
                        final Point2D intersectionPoint;
                        // cuts off light ray when intersects \w any object (except light sources)
                        if ((intersectionPoint = lightRay.intersectionPoint(defaultObject)) != null) {
                            lightRay.setEnd(intersectionPoint);
                        }
                    }

                    lightRays.add(lightRay);
                }
            }

            this.lightSources.put(lightSource, lightRays);
        }
    }

    public Map<CircleLightSource, List<Line2D>> getLightSources() {
        return this.lightSources;
    }

    public List<ColoredCircle> getDefaultObjects() {
        return this.defaultObjects;
    }
}
