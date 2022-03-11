import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Pour creer les balles.
 */
public class Balle {
    protected double x, y, r; // position et rayon
    protected double ar = -300;//pour diminuer la vitesse du rayon

    /**
     *constructeur
     * @param x position
     * @param y postion
     * @param r rayon
     */
    public Balle(double x, double y, double r){
        this.x = x;
        this.y = y;
        this.r = r;
    }

    /**
     *
     * @param dt update
     */
    public void update(double dt){
        r += ar*dt;
        if(r < 0){
            r = 0;
            x = -200;
            y = -200;
        }
    }

    /**
     * Dessine les propriétes de la balle
     * @param context
     */
    public void draw(GraphicsContext context){
        context.setFill(Color.BLACK);
        context.fillOval(x-r,y-r,2*r,2*r);
        context.setFill(Color.WHITE);
    }
}
