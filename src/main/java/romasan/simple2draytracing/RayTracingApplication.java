package romasan.simple2draytracing;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import romasan.simple2draytracing.Engine.Engine;
import romasan.simple2draytracing.Engine.Objects.*;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class RayTracingApplication extends Application {
    private static final int WIDTH = 1920, HEIGHT = 1080;
    private static AbstractCircle current = null;
    private static byte speed = 2;

    @Override
    public void start(final Stage stage) {
        final Canvas canvas = new Canvas(WIDTH, HEIGHT);
        final GraphicsContext gc = canvas.getGraphicsContext2D();

        final Pane root = new Pane(canvas);
        final Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("Ray Tracing");
        stage.getIcons().add(new Image(Path.of("assets/icon.jpg").toUri().toString()));
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setFullScreen(true);

        final Engine engine = new Engine(
                List.of(
                        // Yellow light source
                        new LightSourceCircle(new Point(935.0, 515.0), 50.0, Color.YELLOW, 1.0, Color.YELLOW, 0.014),
                        // Cyan light source
                        new LightSourceCircle(new Point(1450.0, 115.0), 50.0, Color.CYAN, 1.0, Color.CYAN, 0.016),
                        // Magenta light source
                        new LightSourceCircle(new Point(315.0, 880.0), 50.0, Color.MAGENTA, 1.0, Color.MAGENTA, 0.012),
                        // 8 different-sized different-colored circles
                        new DefaultCircle(new Point(200.0, 200.0), 75.0, Color.RED, 1.0),
                        new DefaultCircle(new Point(1000.0, 300.0), 100.0, Color.GREEN, 1.0),
                        new DefaultCircle(new Point(1800.0, 250.0), 60.0, Color.BLUE, 1.0),
                        new DefaultCircle(new Point(150.0, 850.0), 50.0, Color.ORANGE, 1.0),
                        new DefaultCircle(new Point(450.0, 750.0), 35.0, Color.PURPLE, 1.0),
                        new DefaultCircle(new Point(1120.0, 820.0), 40.0, Color.PINK, 1.0),
                        new DefaultCircle(new Point(1400.0, 700.0), 45.0, Color.DARKBLUE, 1.0),
                        new DefaultCircle(new Point(500.0, 1000.0), 20.0, Color.SEAGREEN, 1.0)
                )
        );

        final List<DefaultCircle> defaultObjects = engine.getDefaultObjects();
        engine.algorithm();
        final Map<LightSourceCircle, List<Line>> lightSources = engine.getLightSources();

        drawScene(gc, lightSources, defaultObjects);

        scene.setOnMouseClicked(event -> {
            final Point clicked = new Point(event.getX(), HEIGHT - event.getY());
            boolean selected = false;

            for (final LightSourceCircle lightSource : lightSources.keySet()) {
                if (lightSource.contains(clicked)) {
                    current = lightSource;
                    selected = true;
                    break;
                }
            }

            if (!selected) {
                for (final DefaultCircle object : defaultObjects) {
                    if (object.contains(clicked)) {
                        current = object;
                        break;
                    }
                }
            }
        });

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case KeyCode.F11 -> stage.setFullScreen(!stage.isFullScreen()); // fullscreen mode
                case KeyCode.DOWN -> speed = (byte) Math.max(speed - 2, 2); // decrease speed (2..16 \w step 2)
                case KeyCode.UP -> speed = (byte) Math.min(speed + 2, 16); // increase speed (2..16 \w step 2)
                default -> {
                    if (current != null) {
                        boolean reDrawScene = true;
                        switch (event.getCode()) {
                            case KeyCode.W -> current.move(new Point(0.0, speed)); // move up
                            case KeyCode.A -> current.move(new Point(-speed, 0.0)); // move left
                            case KeyCode.S -> current.move(new Point(0.0, -speed)); // move down
                            case KeyCode.D -> current.move(new Point(speed, 0.0)); // move right
                            default -> reDrawScene = false;
                        }

                        if (reDrawScene) {
                            engine.algorithm();
                            drawScene(gc, engine.getLightSources(), defaultObjects);
                        }
                    }
                }
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    // drawing all objects, light sources & light rays
    private static void drawScene(final GraphicsContext gc, final Map<LightSourceCircle, List<Line>> lightSources, final List<DefaultCircle> defaultObjects) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        // lambda for drawing an object (circle)
        final Consumer<AbstractCircle> drawObject = (final AbstractCircle object) -> {
            final Color objectColor = object.getColor();
            gc.setStroke(objectColor);
            gc.setFill(objectColor);
            gc.strokeOval(object.getCenter().getX() - object.getRadius(), HEIGHT - object.getCenter().getY() - object.getRadius(), object.getRadius() * 2, object.getRadius() * 2);
            gc.fillOval(object.getCenter().getX() - object.getRadius(), HEIGHT - object.getCenter().getY() - object.getRadius(), object.getRadius() * 2, object.getRadius() * 2);
        };

        for (final DefaultCircle defaultObject : defaultObjects) {
            drawObject.accept(defaultObject);
        }

        for (final var lightSourceWihLightRays : lightSources.entrySet()) {
            final LightSourceCircle lightSource = lightSourceWihLightRays.getKey();
            final Color objectColor = lightSource.getColor();
            final Color lightRaysColor = Color.color(objectColor.getRed(), objectColor.getGreen(), objectColor.getBlue(), lightSource.getLightOpacity());

            gc.setStroke(lightRaysColor);
            for (final Line lightRay : lightSourceWihLightRays.getValue()) {
                gc.strokeLine(lightRay.getStart().getX(), HEIGHT - lightRay.getStart().getY(), lightRay.getEnd().getX(), HEIGHT - lightRay.getEnd().getY());
            }

            drawObject.accept(lightSource);
        }
    }

    public static void main(final String[] args) {
        launch();
    }
}
