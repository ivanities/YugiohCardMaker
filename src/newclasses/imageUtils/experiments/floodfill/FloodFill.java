package newclasses.imageUtils.experiments.floodfill;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

public class FloodFill extends Application {
    private Scene scene;
    private double mouseXOld = 0;
    private double mouseYOld = 0;
    private Path path;
    private Group root;
    private Canvas canvas;
    private PathRegion pathRegion;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.root = new Group();
        this.canvas = new Canvas(800, 600);
        root.getChildren().add(canvas);
        this.scene = new Scene(root);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setResizable(true);
        primaryStage.setTitle(getClass().getName());
        primaryStage.setScene(scene);
        setMouseEvents(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void setMouseEvents(Scene scene) {
        EventHandler mouseEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double mouseXNew = event.getSceneX();
                double mouseYNew = event.getSceneY();

                if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                    onMousePressed(event);
                } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
                    onMouseReleased(event);
                } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                    System.out.println("x,y : " + mouseXNew + ", " + mouseYNew );
                    onMouseDrag(event, mouseXOld, mouseYOld, mouseXNew, mouseYNew);
                }

                mouseXOld = mouseXNew;
                mouseYOld = mouseYNew;
                event.consume();
            }
        };


        scene.addEventHandler(MouseEvent.ANY, mouseEventHandler);
    }

    private void onMousePressed(MouseEvent event) {
        path = new Path();
        path.setStroke(Color.DARKBLUE);
        path.setStrokeWidth(1);
        //path.setFill(new Color(Color.BLUE.getRed(), Color.BLUE.getGreen(), Color.BLUE.getBlue(), 0.5));
        path.getElements().add(new MoveTo(event.getSceneX(), event.getSceneY()));
        root.getChildren().add(path);
        pathRegion = new PathRegion((int) scene.getWidth(), (int) scene.getHeight(), (int) event.getSceneX(), (int) event.getSceneY());
    }

    private void onMouseReleased(MouseEvent event) {
        path.getElements().clear();
        root.getChildren().remove(path);
    }

    private void onMouseDrag(MouseEvent event, double mouseXOld, double mouseYOld, double mouseXNew, double mouseYNew) {
        LineTo endTo = null;
        if (path.getElements().size() > 1) {
            endTo = (LineTo) path.getElements().get(path.getElements().size() - 1);
            path.getElements().remove(endTo);
        } else {
            MoveTo start = (MoveTo) path.getElements().get(0);
            endTo = new LineTo(start.getX(), start.getY());
        }

        path.getElements().addAll(new LineTo(event.getSceneX(), event.getSceneY()), endTo);
        pathRegion.updateRegion((int) event.getSceneX(), (int) event.getSceneY());
        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (Point2D point : pathRegion.getRegion()) {
            gc.getPixelWriter().setColor((int) point.getX(), (int) point.getY(), Color.RED);
        }
    }
}