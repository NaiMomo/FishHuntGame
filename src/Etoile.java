import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Pour créer les etoiles de mer.
 */
public class Etoile extends Poisson{
    protected double tempsTotal;
    private boolean turnUp = false;
    Image image = new Image("images/star.png"); //image de l'etoile

    /**
     * constructeur
     * accede au constructeur super (poisson)
     * mis a jour de vitesse en x
     */
    public Etoile(){
        super();
        ay = 0; // acceleration en y
        vy = 200; //vitesse comme les poissons normaux

        if(x>320){
            this.vx = -(100*Math.cbrt(Jeu.niveau) + 200);
        }
        else{
            this.vx = 100*Math.cbrt(Jeu.niveau) + 200;
        }
        if(this.vx < 0){
            image = flop(image);
        }

    }

    /**
     * Update les propriétés de l'etole (oscille vertical)
     * @param dt
     */
    @Override
    public void update(double dt) {
        tempsTotal += dt;
        if(tempsTotal > 0.5 && tempsTotal <=1.0 && !turnUp){
            vy = -vy;
            turnUp = true;
        }

        if (tempsTotal > 1.0 && tempsTotal <= 1.5 && turnUp){
            vy = -vy;
            turnUp = false;
        }
        if(tempsTotal > 1.5 && tempsTotal <= 2.0 && !turnUp){
            vy = -vy;
            turnUp = true;
        }
        if (tempsTotal > 2.0 && tempsTotal <= 2.5 && turnUp){
            vy = -vy;
            turnUp = false;
        }
        if(tempsTotal > 2.5 && tempsTotal <= 3.0 && !turnUp){
            vy = -vy;
            turnUp = true;
        }
        if (tempsTotal > 3.0 && tempsTotal < 3.5 && turnUp){
            vy = -vy;
            turnUp = false;
        }

        x += dt * vx;
        y += dt * vy;
    }

    /**
     * desinne l'imade de l'etoile sur le canvas dans les positions a jour
     * @param context
     */
    @Override
    public void draw(GraphicsContext context) {
        if(!mort) {
            context.drawImage(image, x, y, largeur, hauteur);
        }
    }
}
