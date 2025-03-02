package romasan.simple2draytracing.Engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Engine {
    private final Map<Circle, List<Line>> lightSources;
    private final List<Circle> objects;

    public Engine(final List<Circle> objects) {
        this.lightSources = new HashMap<>();
        this.objects = new ArrayList<>();

        objects.parallelStream().forEach(object -> {
            if (object.isLightSource()) this.lightSources.put(object, null);
            else this.objects.add(object);
        });
    }

    public void algorithm() {
        final double step = 0.05, scale = 200.0;

        for (final Circle lightSource : lightSources.keySet()) {
            final List<Line> lightRays = new ArrayList<>((int) Math.ceil(360 / step));

            for (double i = 0; i < 360; i += step) {
                final Line lightRay = new Line(new Point(lightSource.getCenter()), new Point((1 - scale) * lightSource.getCenter().getX() + scale * (lightSource.getCenter().getX() + lightSource.getRadius() * Math.cos(Math.toRadians(i))), (1 - scale) * lightSource.getCenter().getY() + scale * (lightSource.getCenter().getY() + lightSource.getRadius() * Math.sin(Math.toRadians(i)))));

                for (final Circle object : this.objects) {
                    final Point intersectionPoint;
                    if ((intersectionPoint = lightRay.intersectionPoint(object)) != null) {
                        lightRay.setEnd(intersectionPoint);
                    }
                }

                lightRays.add(lightRay);
            }

            this.lightSources.put(lightSource, lightRays);
        }
    }

    public Map<Circle, List<Line>> getLightSources() {
        return this.lightSources;
    }

    public List<Circle> getObjects() {
        return this.objects;
    }
}
