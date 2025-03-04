package romasan.simple2draytracing;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import romasan.simple2draytracing.Engine.Engine;
import romasan.simple2draytracing.Engine.Objects.*;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Consumer;

public class RayTracingApplication extends Application {
    private static final int WIDTH = 1920, HEIGHT = 1080;
    private static AbstractCircle selectedObject = null;
    private static byte moveSpeed = 2, lightDistanceDelta = 2;

    @Override
    public void start(final Stage stage) {
        final Canvas canvas = new Canvas(WIDTH, HEIGHT);
        final GraphicsContext gc = canvas.getGraphicsContext2D();

        final Pane root = new Pane(canvas);
        final Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("Ray Tracing");
        stage.getIcons().add(new Image(Path.of("assets/icon.jpg").toUri().toString()));
        stage.setResizable(false);
        stage.setFullScreen(true);

        final Text hudText = new Text();
        hudText.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
        hudText.setFill(Color.WHITE);
        hudText.setTextAlignment(TextAlignment.LEFT);
        hudText.setX(15);
        hudText.setY(30);
        root.getChildren().add(hudText);

        final Engine engine = new Engine(
                List.of(
                        // Yellow light source
                        new LightSourceCircle(new Point(935.0, 515.0), 20.0, Color.YELLOW, 1.0,
                                Color.WHEAT, 0.024, 2200.0, 0.0, 360.0),
                        // Cyan light source
                        new LightSourceCircle(new Point(1450.0, 115.0), 30.0, Color.CYAN, 1.0,
                                Color.CYAN, 0.024, 2200.0, 0.0, 360.0),
                        // Magenta light source
                        new LightSourceCircle(new Point(315.0, 880.0), 40.0, Color.MAGENTA, 1.0,
                                Color.MAGENTA, 0.02, 2200.0, 0.0, 360.0),
                        // White light source
                        new LightSourceCircle(new Point(1600.0, 600.0), 30.0, Color.WHITE, 0.0,
                                Color.WHITE, 0.02, 2200.0, 0.0, 360.0),
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
        updateHUD(hudText, lightSources.keySet(), (short) defaultObjects.size());

        scene.setOnMouseClicked(event -> {
            switch (event.getButton()) {
                case MouseButton.PRIMARY -> {
                    final Point clicked = new Point(event.getX(), HEIGHT - event.getY());
                    boolean isSelected = false;

                    for (final LightSourceCircle lightSource : lightSources.keySet()) {
                        if (lightSource.contains(clicked)) {
                            selectedObject = lightSource;
                            isSelected = true;
                            break;
                        }
                    }

                    if (!isSelected) {
                        for (final DefaultCircle object : defaultObjects) {
                            if (object.contains(clicked)) {
                                selectedObject = object;
                                isSelected = true;
                                break;
                            }
                        }
                    }

                    if (isSelected) updateHUD(hudText, lightSources.keySet(), (short) defaultObjects.size());
                }

                case MouseButton.SECONDARY -> {
                    selectedObject = null;
                    updateHUD(hudText, lightSources.keySet(), (short) defaultObjects.size());
                }
            }
        });

        scene.setOnKeyPressed(event -> {
            boolean updateHud = true;

            switch (event.getCode()) {
                case KeyCode.F11 -> {
                    stage.setFullScreen(!stage.isFullScreen()); // fullscreen mode
                    updateHud = false;
                }
                case KeyCode.ESCAPE -> stage.close(); // close
                case KeyCode.TAB -> {
                    hudText.setVisible(!hudText.isVisible());
                    updateHud = false;
                }

                case KeyCode.DOWN -> moveSpeed = (byte) Math.max(moveSpeed - 2, 2); // decrease speed (2..16 \w step 2)
                case KeyCode.UP -> moveSpeed = (byte) Math.min(moveSpeed + 2, 16); // increase speed (2..16 \w step 2)
                // decrease light speed delta (2..16 \w step 2)
                case KeyCode.LEFT -> lightDistanceDelta = (byte) Math.max(lightDistanceDelta - 2, 2);
                // increase light speed delta (2..16 \w step 2)
                case KeyCode.RIGHT -> lightDistanceDelta = (byte) Math.min(lightDistanceDelta + 2, 16);

                default -> {
                    if (selectedObject != null) {
                        boolean reDrawScene = true;
                        switch (event.getCode()) {
                            case KeyCode.W -> selectedObject.move(new Point(0.0, moveSpeed)); // move up
                            case KeyCode.A -> selectedObject.move(new Point(-moveSpeed, 0.0)); // move left
                            case KeyCode.S -> selectedObject.move(new Point(0.0, -moveSpeed)); // move down
                            case KeyCode.D -> selectedObject.move(new Point(moveSpeed, 0.0)); // move right

                            case KeyCode.R -> selectedObject.subtractRadius(1.0); // decrease object radius
                            case KeyCode.T -> selectedObject.addRadius(1.0); // increase object radius
                            case KeyCode.O -> selectedObject.subtractOpacity(0.02); // decrease object opacity
                            case KeyCode.P -> selectedObject.addOpacity(0.02); // increase object opacity

                            case KeyCode.Z -> { // decrease light distance
                                if (selectedObject instanceof LightSourceCircle lightSource) {
                                    lightSource.subtractLightDistance(lightDistanceDelta);
                                }
                            }
                            case KeyCode.X -> { // increase light distance
                                if (selectedObject instanceof LightSourceCircle lightSource) {
                                    lightSource.addLightDistance(lightDistanceDelta);
                                }
                            }
                            case KeyCode.Q -> { // decrease light brightness
                                if (selectedObject instanceof LightSourceCircle lightSource) {
                                    lightSource.subtractLightOpacity(0.002);
                                }
                            }
                            case KeyCode.E -> { // increase light brightness
                                if (selectedObject instanceof LightSourceCircle lightSource) {
                                    lightSource.addLightOpacity(0.002);
                                }
                            }

                            case KeyCode.H -> { // rotate light source clockwise
                                if (selectedObject instanceof LightSourceCircle lightSource) {
                                    lightSource.subtractStartAngleDegree(1.0);
                                }
                            }
                            case KeyCode.J -> { // rotate light source counterclockwise
                                if (selectedObject instanceof LightSourceCircle lightSource) {
                                    lightSource.addStartAngleDegree(1.0);
                                }
                            }
                            case KeyCode.K -> { // decrease illumination angle
                                if (selectedObject instanceof LightSourceCircle lightSource) {
                                    lightSource.subtractAngleDegrees(1.0);
                                }
                            }
                            case KeyCode.L -> { // increase illumination angle
                                if (selectedObject instanceof LightSourceCircle lightSource) {
                                    lightSource.addAngleDegrees(1.0);
                                }
                            }

                            case KeyCode.F -> { // toggle light source
                                if (selectedObject instanceof LightSourceCircle lightSource) {
                                    lightSource.toggleActive();
                                }
                            }

                            default -> {
                                reDrawScene = false;
                                updateHud = false;
                            }
                        }

                        if (reDrawScene) {
                            engine.algorithm();
                            drawScene(gc, engine.getLightSources(), defaultObjects);
                        }
                    }
                }
            }

            if (updateHud) updateHUD(hudText, lightSources.keySet(), (short) defaultObjects.size());
        });

        stage.setScene(scene);
        stage.show();
    }

    // draws all objects, light sources & light rays
    private static void drawScene(final GraphicsContext gc, final Map<LightSourceCircle, List<Line>> lightSources, final List<DefaultCircle> defaultObjects) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        // lambda for drawing an object (circle)
        final Consumer<AbstractCircle> drawObject = (final AbstractCircle object) -> {
            final Color objectColor = object.getColor(),
                    objectColorWithOpacity = Color.color(objectColor.getRed(), objectColor.getGreen(), objectColor.getBlue(), object.getOpacity());
            gc.setStroke(objectColorWithOpacity);
            gc.setFill(objectColorWithOpacity);

            final double objectCenterX = object.getCenter().getX(), objectCenterY = object.getCenter().getY(), objectRadius = object.getRadius();
            gc.strokeOval(objectCenterX - objectRadius, HEIGHT - objectCenterY - objectRadius, objectRadius * 2, objectRadius * 2);
            gc.fillOval(objectCenterX - objectRadius, HEIGHT - objectCenterY - objectRadius, objectRadius * 2, objectRadius * 2);
        };

        // draw default objects first
        for (final DefaultCircle defaultObject : defaultObjects) {
            drawObject.accept(defaultObject);
        }

        // draw light rays second
        for (final var lightSourceWihLightRays : lightSources.entrySet()) {
            final LightSourceCircle lightSource = lightSourceWihLightRays.getKey();

            if (lightSource.isActive()) {
                final Color lightColor = lightSource.getLightColor(),
                        lightColorWithOpacity = Color.color(lightColor.getRed(), lightColor.getGreen(), lightColor.getBlue(), lightSource.getLightOpacity());
                gc.setStroke(lightColorWithOpacity);

                for (final Line lightRay : lightSourceWihLightRays.getValue()) {
                    gc.strokeLine(lightRay.getStart().getX(), HEIGHT - lightRay.getStart().getY(), lightRay.getEnd().getX(), HEIGHT - lightRay.getEnd().getY());
                }
            }
        }

        // draw light sources last
        for (final LightSourceCircle lightSource : lightSources.keySet()) {
            drawObject.accept(lightSource);
        }
    }

    // displays info about scene, objects, etc.
    private static void updateHUD(final Text hudText, final Set<LightSourceCircle> lightSources, final short defaultObjectsOnScene) {
        final StringJoiner sj = new StringJoiner("\n");
        sj.add("Objects: " + (defaultObjectsOnScene + lightSources.size() + " (" + defaultObjectsOnScene + " default, "
                + lightSources.stream().filter(LightSourceCircle::isActive).count() + "/" + lightSources.size() + " active light sources)"));
        sj.add("");
        sj.add("[↓/↑] Move speed: " + moveSpeed + " px/step");
        sj.add("[←/→] Light distance delta: " + lightDistanceDelta + " px/step");
        sj.add("");

        if (selectedObject != null) {
            sj.add("Selected object params:");
            sj.add("    [W/A/S/D] Coordinates: " + (short) selectedObject.getCenter().getX() + "px, " + (short) selectedObject.getCenter().getY() + "px");
            sj.add("    [R/T] Radius: " + (short) selectedObject.getRadius() + "px");
            sj.add("    Color: " + selectedObject.getColor());
            sj.add("    [O/P] Opacity: " + (byte) (selectedObject.getOpacity() * 100) + "%");

            if (selectedObject instanceof LightSourceCircle lightSource) {
                sj.add("");
                sj.add("    [F] Status: " + (lightSource.isActive() ? "ACTIVE" : "INACTIVE"));
                sj.add("    Light color: " + lightSource.getLightColor());
                sj.add(String.format("    [Q/E] Light opacity: %.1f%%", lightSource.getLightOpacity() * 100));
                sj.add("    [Z/X] Light distance: " + (short) lightSource.getLightDistance() + "px");
                sj.add("    [H/J] Rotation angle: " + (short) lightSource.getStartAngleDegree() + "°");
                sj.add("    [K/L] Illumination angle: " + (short) lightSource.getAngleDegrees() + "°");
            }
        }

        hudText.setText(sj.toString());
    }

    public static void main(final String[] args) {
        launch();
    }
}
