import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import static javafx.scene.paint.Color.*;

/**
 * Classe fournissant des méthodes statiques pour manipuler des Images avec
 * JavaFX.
 */
public class FishHunt extends Application {

    public static final double largeur = 640, hauteur = 480;
    private long lastTime = 0;
    private boolean writeScore = false;
    private int score;
    private TextField nom = new TextField();

    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;
        primaryStage.setTitle("Fish Hunt");
        primaryStage.setScene(creerSceneAccueil()); //appel SceneAccueil
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    /**
     * Scene menu
     * @return scene avec les ajouts des propriétés
     */
    private Scene creerSceneAccueil() {

        VBox root = new VBox();

        //Propriétés de background
        BackgroundFill darkBlue = new BackgroundFill(
                Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY);

        Background background = new Background(darkBlue);
        root.setBackground(background); //ajour de background dans le root

        //Creation de scene
        Scene sceneAccueil = new Scene(root, largeur, hauteur);

        //Propriétes du logo de la scene
        Image image = new Image("/images/logo.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(largeur/2);
        imageView.setFitHeight(hauteur/2);

        //Ajout de Vbox et propriétés pour les bouttons de la scene
        VBox groupeBouton = new VBox();
        Button boutonPartie = new Button("Nouvelle partie!");
        Button boutonScore = new Button("Meilleurs scores");
        groupeBouton.setAlignment(Pos.CENTER);
        groupeBouton.setSpacing(10);

        //ajout de logo et les bouttons dans le root
        groupeBouton.getChildren().addAll(boutonPartie,boutonScore);
        root.getChildren().addAll(imageView,groupeBouton);

        root.setAlignment(Pos.CENTER);
        root.setSpacing(30);

        //Boutton Partie = change primaryStage
        boutonPartie.setOnAction((e) -> {
            primaryStage.setScene(creerSceneJeu());
        });

        //Boutton Score = change primaryStage
        boutonScore.setOnAction((e) -> {
            primaryStage.setScene(creerSceneScore());
        });

        return sceneAccueil;
    }

    /**
     * scene de jeu
     * inclus aussi les propriétés de mode debug
     * @return avec toutes les propriétés des parties de jeu
     */
    private Scene creerSceneJeu() {

        //propriétés de la scene
        Pane root = new Pane();
        root.setStyle("-fx-background-color: darkblue;");
        Scene sceneJeu = new Scene(root, largeur, hauteur);
        Canvas canvas = new Canvas(largeur, hauteur);
        root.getChildren().add(canvas);
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(WHITE);
        context.setFont(Font.font(30));

        //appel controleur pour demarer le jeu
        Controleur controleur = new Controleur();

        //Trouve l'emplacement du souris
        AtomicReference<Double> cibleX = new AtomicReference<>((double) 0);
        AtomicReference<Double> cibleY = new AtomicReference<>((double) 0);
        sceneJeu.addEventHandler(MouseEvent.ANY,(e) -> {
            cibleX.set(e.getSceneX());
            cibleY.set(e.getSceneY());
        });

        //pour lancer la balle
        sceneJeu.setOnMouseClicked((e) -> {
            if(e.getButton() == MouseButton.PRIMARY){
                controleur.lancer(e.getSceneX(), e.getSceneY());
            }
        });

        //Pour le mode debug
        sceneJeu.setOnKeyPressed((e) -> {
            switch (e.getCode()) {
                case H:
                    controleur.ajouterNiveau();
                    break;
                case J:
                    controleur.ajouterScore();
                    break;
                case K:
                    controleur.ajouterNbVie();
                    break;
                case L:
                    controleur.perdre();
            }
        });

        //Update delta temps et position de souris.
        controleur.update(0, 295, 215);
        controleur.draw(context);

        /**
         *update les positions du cible, delta temps,et draw du controleur
         */
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }
                context.clearRect(0, 0, largeur, hauteur);
                double dt = (now - lastTime) * 1e-9;
                controleur.update(dt, cibleX.get() - 25, cibleY.get() - 25);
                controleur.draw(context);;

                //Envoie vers scene de scores une fois le jeu est terminé
                if(controleur.showMeilleurScore()){
                    writeScore = true;
                    score = controleur.score();
                    primaryStage.setScene(creerSceneScore());
                    dt = 0;
                    now = 0;
                    lastTime = 0;
                    stop();
                }
                lastTime = now;
            }
        };
        timer.start();

        return sceneJeu;

    }

    /**
     * scene de score
     * @return scene avec la liste des meilleurs scores selon l'ordre
     */
    private Scene creerSceneScore() {

        ArrayList<String> scoreList = new ArrayList<String>();
        File file = new File("score.txt");

        //créer le fichier contenant les scores, s'il n'existe pas.
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //lire le ficher une fois qu'il existe
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            String ligne;
            while((ligne = br.readLine()) != null) {
                scoreList.add(ligne.substring(2));
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //layout de la scène
        VBox root = new VBox();
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);
        Scene sceneScore = new Scene(root, largeur, hauteur);
        Text titre = new Text("Meilleurs scores");
        titre.setFont(Font.font(40));

        //Proprietes de Listview + ajout de scoreList dans listview
        ListView<String> list = new ListView<>();
        list.setMaxWidth(560);
        list.setMaxHeight(270);
        if(!scoreList.isEmpty()){
            for(int i = 0; i < scoreList.size(); i++){
                if(i < 9) {
                    list.getItems().add("#" + (i + 1) + scoreList.get(i));
                }
                else {
                    list.getItems().add("#" + 1 + scoreList.get(i));
                }
            }
        }

        HBox hBox = new HBox();
        if(scoreList.size() == 10) {
            for (int i = 0; i < scoreList.size(); i++) {

                //verifie si besoin d'ajouter un score
                if (score > Integer.parseInt(
                        scoreList.get(i).substring(
                                scoreList.get(i).lastIndexOf(" ") + 1,
                                scoreList.get(i).length()))) {
                    writeScore = true;
                    break;
                } else {
                    writeScore = false;
                }
            }
        }

        //Permet d'ecrire un nouveau score si necessaire
        if(writeScore) {
            hBox = new HBox();
            Label label1 = new Label("Votre nom : ");
            nom = new TextField();
            Label label2 = new Label(" a fait " + score + " points!  ");
            Button ajouter = new Button("Ajouter!");
            hBox.getChildren().addAll(label1, nom, label2, ajouter);
            hBox.setAlignment(Pos.CENTER);
            ajouter.setOnAction(new EventHandler<ActionEvent>() {


                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        FileWriter fw = new FileWriter(file);
                        if(writeScore){
                            if (scoreList.isEmpty()) {
                                scoreList.add(" - " + nom.getText() +
                                        " - " + score);
                            } else {
                                if (scoreList.size() == 10) {
                                    for (int i = 0; i < scoreList.size(); i++){
                                        if (score > Integer.parseInt(
                                                scoreList.get(i).substring(
                                                scoreList.get(i).lastIndexOf
                                                        (" ")+1,
                                                scoreList.get(i).length()))) {
                                            scoreList.add(i, " - " +
                                                    nom.getText() +
                                                    " - " + score);
                                            scoreList.remove(10);
                                            break;
                                        }
                                    }
                                } else {
                                    int sizeInitial = scoreList.size();
                                    for (int i = 0; i < scoreList.size(); i++){
                                        if (score > Integer.parseInt
                                                (scoreList.get(i).substring(
                                                scoreList.get(i).lastIndexOf
                                                        (" ")+1,
                                                scoreList.get(i).length()))) {
                                            scoreList.add(i, " - " +
                                                    nom.getText() +
                                                    " - " + score);
                                            break;
                                        }
                                    }
                                    if (scoreList.size() == sizeInitial) {
                                        scoreList.add(" - " + nom.getText() +
                                                " - " + score);
                                    }
                                }
                            }
                            for (int i = 0; i < scoreList.size(); i++) {
                                fw.write("#" + (i +1) + scoreList.get(i)+"\n");
                            }
                            fw.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    primaryStage.setScene(creerSceneAccueil());
                    writeScore = false;
                }
            });
        }

        Button menu = new Button("Menu");

        menu.setOnAction((e) -> {
            primaryStage.setScene(creerSceneAccueil());
        });
        root.getChildren().addAll(titre,list,hBox,menu);

        return sceneScore;
    }
};