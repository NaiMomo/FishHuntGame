import javafx.scene.canvas.GraphicsContext;


/**
 * Demarre controleur et accede au méthodes de jeu
 */
public class Controleur {
    Jeu jeu;

    /**
     * nouvelle partie
     */
    public Controleur(){
        jeu = new Jeu();
    }

    /**
     * appel methodes draw de jeu
     * @param context
     */
    void draw(GraphicsContext context){
        jeu.draw(context);
    }

    /**
     * envoie le delta temps et position de cible
     * @param dt
     * @param cibleX
     * @param cibleY
     */
    void update(double dt, double cibleX, double cibleY){
        jeu.update(dt, cibleX, cibleY);
    }

    /**
     * postion pour lancer la balle
     * @param x
     * @param y
     */
    void lancer(double x, double y){
        jeu.lancer(x,y);
    }

    /**
     * augmenter niveu
     */
    void ajouterNiveau(){
        jeu.ajouterNiveau();
    }

    /**
     * augmenter le score
     */
    void ajouterScore(){
        jeu.ajouterScore();
    }

    /**
     * aumenter vie
     */
    void ajouterNbVie(){
        jeu.ajouterNbVie();
    }

    /**
     * finir la partie
     */
    void perdre(){
        jeu.perdre();
    }

    int score(){
        return jeu.score;
    }

    /**
     * pour déterminer la fin du partie
     * @return boolen une fois la partie fini
     */
    boolean showMeilleurScore(){
        return jeu.showMeilleurScore;
    }

}
