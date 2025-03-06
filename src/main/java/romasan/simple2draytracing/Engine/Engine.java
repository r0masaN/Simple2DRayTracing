package romasan.simple2draytracing.Engine;

import romasan.simple2draytracing.Engine.Objects.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Engine class (ray tracing magic here)
public final class Engine {
    private final Map<LightSourceCircle, List<Line>> lightSources;
    private final List<DefaultCircle> defaultObjects;

    private final static double DEGREES_STEP = 0.03;

    public Engine(final List<AbstractCircle> objects) {
        this.lightSources = new HashMap<>();
        this.defaultObjects = new ArrayList<>();

        objects.forEach(object -> {
            switch (object) {
                case DefaultCircle defaultObject -> this.defaultObjects.add(defaultObject);
                case LightSourceCircle lightSource -> lightSources.put(lightSource, null);
                default -> throw new IllegalStateException("Unexpected value: " + object);
            }
        });
    }

    // main method \w ray tracing algorithm
    public void algorithm() {
        for (final LightSourceCircle lightSource : lightSources.keySet()) {
            final List<Line> lightRays = new ArrayList<>((int) Math.ceil(360 / DEGREES_STEP));

            final double startAngleDegree = lightSource.getStartAngleDegree(), endAngleDegree = startAngleDegree + lightSource.getAngleDegrees();
            // spawns (360 / step) light rays in all directions from each light source
            for (double i = startAngleDegree; i < endAngleDegree; i += DEGREES_STEP) {
                final double angleDegree = Math.round(i * 1000.0) / 1000.0;

                if (angleDegree < endAngleDegree) {
                    final Line lightRay = new Line(new Point(lightSource.getCenter()),
                            new Point(lightSource.getCenter().getX() + lightSource.getLightDistance() * Math.cos(Math.toRadians(angleDegree)),
                                    lightSource.getCenter().getY() + lightSource.getLightDistance() * Math.sin(Math.toRadians(angleDegree))));

                    for (final DefaultCircle defaultObject : this.defaultObjects) {
                        final Point intersectionPoint;
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

    public Map<LightSourceCircle, List<Line>> getLightSources() {
        return this.lightSources;
    }

    public List<DefaultCircle> getDefaultObjects() {
        return this.defaultObjects;
    }
}
