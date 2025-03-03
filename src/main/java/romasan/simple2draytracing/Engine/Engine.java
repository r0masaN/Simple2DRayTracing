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
        final double step = 0.05, scale = 200.0;

        for (final LightSourceCircle lightSource : lightSources.keySet()) {
            final List<Line> lightRays = new ArrayList<>((int) Math.ceil(360 / step));

            // spawns (360 / step) light rays in all directions from each light source
            for (double i = 0; i < 360; i += step) {
                final Line lightRay = new Line(new Point(lightSource.getCenter()), new Point((1 - scale) * lightSource.getCenter().getX() + scale * (lightSource.getCenter().getX() + lightSource.getRadius() * Math.cos(Math.toRadians(i))), (1 - scale) * lightSource.getCenter().getY() + scale * (lightSource.getCenter().getY() + lightSource.getRadius() * Math.sin(Math.toRadians(i)))));

                for (final DefaultCircle defaultObject : this.defaultObjects) {
                    final Point intersectionPoint;
                    // cuts off light ray when intersects \w any object (except light sources)
                    if ((intersectionPoint = lightRay.intersectionPoint(defaultObject)) != null) {
                        lightRay.setEnd(intersectionPoint);
                    }
                }

                lightRays.add(lightRay);
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
