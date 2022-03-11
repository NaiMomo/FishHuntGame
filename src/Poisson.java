import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * interface pour toutes sortes de possions oublie d'implementer
 * les foncions abstract
 */
public abstract class Poisson {
    protected Color color;
    protected Image image;
    protected double x, y, hauteur, largeur, vx, vy, ax, ay;
    protected boolean mort;


    /**
     * constructeur
     */
    public Poisson(){
        this.hauteur = 80 + Math.random()*40;
        this.largeur = this.hauteur;
        this.mort = false;
        if(Math.random() < 0.5){
            this.x = -largeur;
        }
        else {
            this.x = 640+largeur;
        }

        this.y = 96 + Math.random()*288;
    }

    /**
     *
     * @param dt
     */
    public abstract void update(double dt);

    /**
     *
     * @param context
     */
    public abstract void draw(GraphicsContext context);

    /**
     *
     * @param img
     * @return
     */
    public static Image flop(Image img) {
        int w = (int) img.getWidth();
        WritableImage output = new WritableImage(w, (int) img.getHeight());

        PixelReader reader = img.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color color = reader.getColor(x, y);
                writer.setColor(w - 1 - x, y, color);
            }
        }

        return output;
    }

    /**
     *
     * @param img
     * @param color
     * @return
     */
    public static Image colorize(Image img, Color color) {
        WritableImage output = new WritableImage((int) img.getWidth(),
                (int) img.getHeight());

        PixelReader reader = img.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                if (reader.getColor(x, y).getOpacity() > 0) {
                    writer.setColor(x, y, color);
                }
            }
        }
        return output;
    }

}
