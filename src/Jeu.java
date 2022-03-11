import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import java.util.ArrayList;

public class Jeu {
    private double width = 640, height = 480;
    private double tempsTotal = 0;
    private double tempsLancer = 0;
    private double groupe = 3; //groupe de bulles
    private double nbrBulle = 5;
    private int sec = 3;
    private double sec3 = 0;
    private double sec5 = 0;
    protected int score = 0;
    protected static int niveau = 1;
    private double tempsNiveau = 0;
    private double tempsNiveau2 = 0;
    private boolean showNiveau = true;
    private int nbVie = 3;
    private boolean ajouterNiveau = false;
    protected boolean gameOver = false;
    private double tempsGameOver = 0;
    protected boolean showMeilleurScore = false;
    private boolean existCrabe = false;
    private boolean existStar = false;

    private PoissonNormal poissonNormal;
    private Crabe crabe;
    private Etoile star;
    private Cible cible;
    private Balle balle = new Balle(0,0,0);
    private ArrayList < Bulles > bulles;

    /**
     * Debute la partie
     */
    public Jeu(){
        poissonNormal = new PoissonNormal();
        cible = new Cible(295, 215);
        ajouterBulles();
    }

    /**
     * ajout des bulles dans l'arriere plam
     */
    public void ajouterBulles() {
        bulles = new ArrayList < > ();
        for (int i = 0; i < groupe; i++) {

            // prend une baseX random pour chaque groupe
            double baseX = (Math.random() * width) + 0;
            for (int j = 0; j < nbrBulle; j++) {
                double x = (Math.random() * baseX + 20) + baseX - 20;
                if (x > 0 || x < 640) { //ajout des bulles si...
                    bulles.add(new Bulles(x)); //
                }
            }
        }
    }

    /**
     * Verifie si contact entre balle et possions
     * @param balle
     * @param poisson
     * @return
     */
    public boolean toucher(Balle balle, Poisson poisson) {
        return !(
                poisson.x + poisson.largeur < balle.x ||
                        balle.x < poisson.x ||
                        poisson.y + poisson.hauteur < balle.y ||
                        balle.y  < poisson.y);
    }

    /**
     * augmente le niveau de la partie
     */
    public void ajouterNiveau(){
        niveau += 1;
        showNiveau = true;
        ajouterNiveau = true;
    }

    /**
     * augmente le score
     */
    public void ajouterScore(){
        score += 1;
    }

    /**
     * augmente le nombre de vie
     */
    public void ajouterNbVie(){
        nbVie += 1;
    }

    /**
     * nombre vie = 0 pour fin de la partie
     */
    public void perdre(){
        nbVie = 0;
    }

    /**
     *
     * @param dt
     * @param cibleX
     * @param cibleY
     */
    public void update(double dt, double cibleX, double cibleY){

        if (tempsTotal > sec) { // ajout des bulles chaque 3 sec
            this.sec += 3;
            ajouterBulles();
        }
        for (Bulles b: bulles) { // update dt pour les bulles
            b.update(dt);
        }
        if(nbVie > 3){
            nbVie = 3;
        }
        if(nbVie <= 0 && !gameOver){  //parite perdu
            gameOver = true;
        }
        if(!showNiveau && !ajouterNiveau) {
            if(niveau >= 2){
                tempsNiveau2 += dt;
            }
            tempsTotal += dt;
            poissonNormal.update(dt);
            if (tempsTotal > sec3) { //ajout de poisson normal chque 3 sec
                poissonNormal = new PoissonNormal();
                sec3 += 3;
            }
            //ajout crabe a niveau>=2 et chaque 5sec
            if(niveau >= 2 && tempsNiveau2 > sec5){
                if(Math.random() < 0.5) {
                    crabe = new Crabe();
                    existCrabe = true;
                    sec5 += 5;
                }
                //sinon ajout d'une étoile de mer
                else {
                    star = new Etoile();
                    existStar = true;
                    sec5 += 5;
                }
            }
            if (!poissonNormal.mort && tempsTotal - tempsLancer >= 0.15
                    && toucher(balle, poissonNormal)) {
                poissonNormal.mort = true;
                score += 1;
                if(score % 5 == 0){
                    showNiveau = true;
                    niveau += 1;
                }
            }
            if(!poissonNormal.mort && poissonNormal.x <-poissonNormal.largeur
                    -10 && poissonNormal.vx < 0){
                poissonNormal.mort = true;
                nbVie -= 1;
            }
            if(!poissonNormal.mort && poissonNormal.x > 640 +
                    poissonNormal.largeur+10 && poissonNormal.vx > 0){
                poissonNormal.mort = true;
                nbVie -= 1;
            }
            if(existCrabe) {
                crabe.update(dt);
                if (!crabe.mort && tempsTotal - tempsLancer >= 0.15 &&
                        toucher(balle, crabe)) {
                    crabe.mort = true;
                    score += 1;
                    if (score % 5 == 0) {
                        showNiveau = true;
                        niveau += 1;
                    }
                    existCrabe = false;
                }
                if (!crabe.mort && crabe.x < -crabe.largeur - 10 &&
                        crabe.vx < 0) {
                    crabe.mort = true;
                    nbVie -= 1;
                    existCrabe = false;
                }
                if (!crabe.mort && crabe.x > 640 + crabe.largeur + 10 &&
                        crabe.vx > 0) {
                    crabe.mort = true;
                    nbVie -= 1;
                    existCrabe = false;
                }
            }
            if(existStar) {
                star.update(dt);
                if (!star.mort && tempsTotal - tempsLancer >= 0.15 &&
                        toucher(balle, star)) {
                    star.mort = true;
                    score += 1;
                    if (score % 5 == 0) {
                        showNiveau = true;
                        niveau += 1;
                    }
                    existStar = false;
                }
                if (!star.mort && star.x < -star.largeur - 10 && star.vx < 0) {
                    star.mort = true;
                    nbVie -= 1;
                    existStar = false;
                }
                if (!star.mort && star.x > 640 + star.largeur + 10
                        && star.vx > 0) {
                    star.mort = true;
                    nbVie -= 1;
                    existStar = false;
                }
            }
        }
        else if(showNiveau && !gameOver){
            tempsNiveau2 = 0;
            sec5 = 0;
            tempsNiveau += dt;
            poissonNormal.x = -200;
            if(existCrabe) {
                crabe.x = -200;
            }
            if(existStar) {
                star.x = -200;
            }

            if (tempsNiveau >= 3){
                showNiveau = false;
                tempsNiveau = 0;
                if(ajouterNiveau){
                    poissonNormal = new PoissonNormal();
                    if(existCrabe) {
                        crabe = new Crabe();
                    }
                    if(existStar) {
                        star = new Etoile();
                    }
                    ajouterNiveau = false;
                }
            }
        }
        if(gameOver){
            tempsGameOver += dt;
        }
        if(tempsGameOver >= 3){
            showMeilleurScore = true;
            gameOver = false;
            tempsGameOver = 0;
        }
        cible.update(cibleX, cibleY);
        balle.update(dt);
    }

    /**
     * dessine, ecris , affiche toutes les donnés de la partie.
     * @param context
     */
    public void draw(GraphicsContext context){
        poissonNormal.draw(context);
        if(existCrabe) {
            crabe.draw(context);
        }
        if(existStar) {
            star.draw(context);
        }
        balle.draw(context);
        cible.draw(context);
        for (Bulles r: bulles) {
            r.draw(context);
        }

        context.fillText("" + score, 310, 50);//affiche le score sur l'ecran

        Image image = new Image("images/fish/00.png"); //images=nmbr de vies
        if(nbVie >= 1) {
            context.drawImage(image, 250, 100, 30, 30);
            if(nbVie >= 2){
                context.drawImage(image, 300, 100, 30, 30);
                if(nbVie == 3){
                    context.drawImage(image, 350, 100, 30, 30);
                }
            }
        }
        if(showNiveau && !gameOver){  //affiche le niveu a chaque fois
            context.setFont(Font.font(60));
            context.fillText("Level "+ niveau, 230, 240);
            context.setFont(Font.font(30));
        }
        if(gameOver){ //affichage si game over
            context.setFont(Font.font(80));
            context.setFill(Color.RED);
            context.fillText("GAME OVER", 100, 240);
            context.setFont(Font.font(30));
            context.setFill(Color.WHITE);
        }
    }

    /**
     * Pour lancer les balles
     * @param x
     * @param y
     */
    public void lancer(double x, double y){
        balle = new Balle(x,y,50);
        tempsLancer = tempsTotal;
    }

}
