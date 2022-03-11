import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;


/**
 * Pour gerer le crabe.
 */

public class PoissonNormal extends Poisson{

    /**
     * constructeur
     * appel le constreucteur super (poisson)
     * mis a jour de vitesse et les differents images de poisson
     */
    public PoissonNormal(){
        super();
        this.vy = -100 - Math.random()*100;
        this.ay = 100;

        if(x>320){
            this.vx = -(100*Math.cbrt(Jeu.niveau) + 200);
        }
        else{
            this.vx = 100*Math.cbrt(Jeu.niveau) + 200;
        }

        double random = Math.random();
        if(random < 0.125){
            image = new Image("images/fish/00.png");
        }
        else if(random < 0.25){
            image = new Image("images/fish/01.png");
        }
        else if(random < 0.375){
            image = new Image("images/fish/02.png");
        }
        else if(random < 0.5){
            image = new Image("images/fish/03.png");
        }
        else if(random < 0.625){
            image = new Image("images/fish/04.png");
        }
        else if(random < 0.75){
            image = new Image("images/fish/05.png");
        }
        else if(random < 0.875){
            image = new Image("images/fish/06.png");
        }
        else{
            image = new Image("images/fish/07.png");
        }

        if(this.vx < 0){
            image = flop(image);
        }

        this.color = new Color(Math.random()/2+0.5, Math.random()/2+0.5, Math.random()/2+0.5,1);

        image = colorize(image, this.color);

    }

    /**
     * update les vitesses et positions du poisson
     * @param dt
     */
    @Override
    public void update(double dt){

        vx += dt * ax;
        vy += dt * ay;
        x += dt * vx;
        y += dt * vy;
    }

    /**
     * dessine le poisson sur le canvas
     * @param context
     */
    @Override
    public void draw(GraphicsContext context){
        if(!mort) {
            context.drawImage(image, x, y, largeur, hauteur);
        }
    }


}
