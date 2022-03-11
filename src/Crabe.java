import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


/**
 * Pour gerer le crabe.
 */

public class Crabe extends Poisson{

    protected double tempsTotal;
    private boolean turnBack = false; //pour gerer l'avance et recul du crabe
    Image image = new Image("images/crabe.png");

    /**
     * constructeur
     * appel le constreucteur super (poisson)
     * mis a jour de vitesse
     */
    public Crabe(){
        super();
        this.vy = 0;
        this.ay = 0;

        if(x>320){
            this.vx = -1.3*(100*Math.cbrt(Jeu.niveau) + 200);
        }
        else{
            this.vx = 1.3*(100*Math.cbrt(Jeu.niveau) + 200);
        }
        if(this.vx < 0){
            image = flop(image);
        }

    }


    /**
     * Update les propriétés du crabe (recul,avance,oscille)
     * @param dt
     */
    @Override
    public void update(double dt) {
        tempsTotal += dt;
        if(tempsTotal>0.5 && tempsTotal <=0.75 && !turnBack){
            vx = -vx;
            turnBack = true;
            image = flop(image);
        }

        if (tempsTotal>0.75 && tempsTotal < 1.25 && turnBack){
            vx = -vx;
            turnBack = false;
            image = flop(image);
        }
        if(tempsTotal>1.25 && tempsTotal <=1.5 && !turnBack){
            vx = -vx;
            turnBack = true;
            image = flop(image);
        }
        if (tempsTotal>1.5 && tempsTotal <= 2.0 && turnBack){
            vx = -vx;
            turnBack = false;
            image = flop(image);
        }
        if(tempsTotal>2.0 && tempsTotal <=2.25 && !turnBack){
            vx = -vx;
            turnBack = true;
            image = flop(image);
        }
        if (tempsTotal>2.25 && tempsTotal < 2.75 && turnBack){
            vx = -vx;
            turnBack = false;
            image = flop(image);
        }
        if(tempsTotal>2.75 && tempsTotal <=3.0 && !turnBack){
            vx = -vx;
            turnBack = true;
            image = flop(image);
        }
        if (tempsTotal>3.0 && tempsTotal < 3.5 && turnBack){
            vx = -vx;
            turnBack = false;
            image = flop(image);
        }
        x += dt * vx;
    }

    /**
     * desinne l'imade du crabe sur le canvas dans les positions a jour
     * @param context
     */
    @Override
    public void draw(GraphicsContext context) {
        if(!mort) {
            context.drawImage(image, x, y, largeur, hauteur);
        }
    }
}
