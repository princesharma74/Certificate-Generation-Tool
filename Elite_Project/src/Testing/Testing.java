package Testing;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Testing implements Initializable {
	@FXML private ImageView imageview;
	private static final int MIN_PIXELS = 10;
    private ObjectProperty<Point2D> mouseDown = new SimpleObjectProperty<>();
	
	//because viewport must not be supplied with null value of ImageView and that will create an nullpointer exception
    private void reset(ImageView imageView, double width, double height) {
        imageView.setViewport(new Rectangle2D(0, 0, width, height));
    }
    /*
     shift the viewport of the imageView by the specified delta, clamping so
     the viewport does not move off the actual image:
     */
    private void shift(ImageView imageView, Point2D delta) {
        Rectangle2D viewport = imageView.getViewport();

        double width = imageView.getImage().getWidth() ;
        double height = imageView.getImage().getHeight() ;

        double maxX = width - viewport.getWidth();
        double maxY = height - viewport.getHeight();
        
        double minX = clamp(viewport.getMinX() - delta.getX(), 0, maxX);
        double minY = clamp(viewport.getMinY() - delta.getY(), 0, maxY);

        imageView.setViewport(new Rectangle2D(minX, minY, viewport.getWidth(), viewport.getHeight()));
    }

    private double clamp(double value, double min, double max) {

        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    // convert mouse coordinates in the imageView to coordinates in the actual image:
    private Point2D imageViewToImage(ImageView imageView, Point2D imageViewCoordinates) {
        double xProportion = imageViewCoordinates.getX() / imageView.getBoundsInLocal().getWidth();
        double yProportion = imageViewCoordinates.getY() / imageView.getBoundsInLocal().getHeight();

        Rectangle2D viewport = imageView.getViewport();
        return new Point2D(
        viewport.getMinX() + xProportion * viewport.getWidth(), 
        viewport.getMinY() + yProportion * viewport.getHeight());
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image image = new Image("file:"+"C:/Users/Prince Sharma/Desktop/Extras/Videoder/Videos Recorded By ME/Sanjana/sanjana.PNG");
		imageview.setImage(image);
        double width = image.getWidth();
        double height = image.getHeight();
        imageview.setPreserveRatio(true);
        //next statement is very important as by default viewport has null value this causes this not to be null. 
        reset(imageview, width / 2, height / 2);
        imageview.setOnMousePressed(e -> {
            
            Point2D mousePress = imageViewToImage(imageview, new Point2D(e.getX(), e.getY()));
            System.out.println("getX of Image: "+mousePress.getX());
            System.out.println("getY of Image: "+mousePress.getY());
            mouseDown.set(mousePress);
        });

        imageview.setOnMouseDragged(e -> {
            Point2D dragPoint = imageViewToImage(imageview, new Point2D(e.getX(), e.getY()));
            shift(imageview, dragPoint.subtract(mouseDown.get()));
            mouseDown.set(imageViewToImage(imageview, new Point2D(e.getX(), e.getY())));
        });

        imageview.setOnScroll(e -> {
            double delta = e.getDeltaY();
            Rectangle2D viewport = imageview.getViewport();

            double scale = clamp(Math.pow(1.01, delta),

                // don't scale so we're zoomed in to fewer than MIN_PIXELS in any direction:
                Math.min(MIN_PIXELS / viewport.getWidth(), MIN_PIXELS / viewport.getHeight()),

                // don't scale so that we're bigger than image dimensions:
                Math.max(width / viewport.getWidth(), height / viewport.getHeight())

            );

            Point2D mouse = imageViewToImage(imageview, new Point2D(e.getX(), e.getY()));

            double newWidth = viewport.getWidth() * scale;
            double newHeight = viewport.getHeight() * scale;

           /* To keep the visual point under the mouse from moving, we need
             (x - newViewportMinX) / (x - currentViewportMinX) = scale
             where x is the mouse X coordinate in the image

             solving this for newViewportMinX gives

             newViewportMinX = x - (x - currentViewportMinX) * scale 

             we then clamp this value so the image never scrolls out
             of the imageview:
            */
            double newMinX = clamp(mouse.getX() - (mouse.getX() - viewport.getMinX()) * scale, 
                    0, width - newWidth);
            double newMinY = clamp(mouse.getY() - (mouse.getY() - viewport.getMinY()) * scale, 
                    0, height - newHeight);

            imageview.setViewport(new Rectangle2D(newMinX, newMinY, newWidth, newHeight));
        });

            imageview.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2) {
                reset(imageview, width, height);
            }
        });
            

	}
}