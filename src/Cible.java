import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Pour creer les cibles
 */

public class Cible {
    private double x, y;
    private double hauteur = 50;
    private double largeur =50;
    private Image image; //image du cible

    /**
     * constructeur
     * @param x position
     * @param y position
     */
    public Cible(double x, double y){
        this.x = x;
        this.y = y;
        this.image = new Image("images/cible.png");
    }

    /**
     *
     * @param x position update
     * @param y position update
     */
    public void update(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * dessine l'image du sur le canvas
     * @param context
     */
    public void draw(GraphicsContext context){
        context.drawImage(image, x, y, largeur, hauteur);
    }
}
