/*
 * Henry Florian - Antoine Levasseur
 *
 * Class MainActivity
 * Classe Affichage du métier
 *
 * */

package project.game.echecrework;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    int[] tokens = {-1, -1, -1, -1};
    // Lien avec la classe métier
    private Echec board;
    private int idButton;
    // Image boutton du tableau affiché.
    private ImageButton[][] IDBoard;
    private TextView tvPlayer;


    /*SAVE PLAY*/
    // IHM en dessous du l'échiquier.
    private Button bReset;
    // Variable définissant le début d'un déplacement
    private boolean firstTouch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Lien avec la classe métier
        this.board = new Echec();

        // lien de l'IHM
        this.bReset = findViewById(R.id.bReset);
        this.bReset.setOnClickListener(this);
        this.tvPlayer = findViewById(R.id.tvPlayer);

        // Initialisation de l'échiquier affiché en fonction de la classe métier.
        this.initBoard();
    }

    // Initialisation de l'échiquier
    private void initBoard() {
        this.IDBoard = new ImageButton[8][8];
        this.idButton = 0;
        /*CREATION DU LAYOUT*/

        final TableLayout tableLayout = findViewById(R.id.TLBoard);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

        /* CREATION DE LA ROW */
        for(int i = 0; i < 8; i++){
            final TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

            /* CREATION DU IMAGEBUTTON */
            for (int k = 0; k < 8; k++) {
                // Creation  button
                final ImageButton button = new ImageButton(this);

                button.setLayoutParams(new TableRow.LayoutParams(115, 115));
                button.setId(this.idButton++);

                button.setOnClickListener(this);

                // SAVE ID
                this.IDBoard[i][k] = button;
                // ADD IN ROW
                tableRow.addView(button);
            }
            // ADD IN LAYOUT
            tableLayout.addView(tableRow);
        }
        majGame();
    }

    // Mise à jour du plateau selon les pièce récupérées du plateau de la classe métier.
    private void majButton() {
        for(int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                ImageButton button = this.IDBoard[i][k];
                switch (board.getCase(i, k)) {
                    case "V.":
                        button.setImageResource(R.drawable.vide);
                        break;

                    /* PION BLANC */
                    case "BR":
                        button.setImageResource(R.drawable.b_rook);
                        break;
                    case "BH":
                        button.setImageResource(R.drawable.b_horse);
                        break;
                    case "BB":
                        button.setImageResource(R.drawable.b_bishop);
                        break;
                    case "BC":
                        button.setImageResource(R.drawable.b_crown);
                        break;
                    case "BK":
                        button.setImageResource(R.drawable.b_king);
                        break;
                    case "BP":
                        button.setImageResource(R.drawable.b_pawn);
                        break;

                    /* PION NOIR */
                    case "NR":
                        button.setImageResource(R.drawable.n_rook);
                        break;
                    case "NH":
                        button.setImageResource(R.drawable.n_horse);
                        break;
                    case "NB":
                        button.setImageResource(R.drawable.n_bishop);
                        break;
                    case "NC":
                        button.setImageResource(R.drawable.n_crown);
                        break;
                    case "NK":
                        button.setImageResource(R.drawable.n_king);
                        break;
                    case "NP":
                        button.setImageResource(R.drawable.n_pawn);
                        break;
                }
            }
        }
    }

    /* POUR RECUPERER LES COORD X ET Y DEPUIS L'ID DE LA LISTE*/
    private int[] searchIndex(int id){
        for(int i = 0; i < 8; i++){
            for(int k = 0; k < 8; k++) {
                if (this.IDBoard[i][k].getId() == id){
                    int[] tabId = {i, k};
                    return tabId;
                }
            }
        }
        return null;
    }

    // Fonction d'évènement de click
    @Override
    public void onClick(View v) {

        // La variable tolen correcponds au coordonnées des deux cases du tableaux étant
        // sélectionnées par le joueur.

        // On la réinitialise à chaque fois avec des valeurs "-1".

        if(v == this.bReset) reset();
        else {
            if(this.firstTouch && !this.board.getCase(searchIndex(v.getId())[0], searchIndex(v.getId())[1]).equals("V.")) {

                this.tokens[0] = searchIndex(v.getId())[0];
                this.tokens[1] = searchIndex(v.getId())[1];
                this.firstTouch = false;

            } else if(!this.firstTouch){
                this.tokens[2] = searchIndex(v.getId())[0];
                this.tokens[3] = searchIndex(v.getId())[1];
                this.firstTouch = true;

                if( board.move(tokens[0], tokens[1], tokens[2], tokens[3]) ) {
                    this.board.changePlayer();
                }
                this.tokens[0] = -1;
                this.tokens[1] = -1;
                this.tokens[2] = -1;
                this.tokens[3] = -1;

            }
        }

        // Mise à jour de l'affichage
        majGame();
    }

    // Fonction de mise à jour principale
    public void majGame(){
        majButton();
        majIHM();
        verif();
    }

    // Mise à jour de l'IHM donnant le nom du joueur actuel
    public void majIHM(){
        this.tvPlayer.setText("C'est au joueur " + this.board.getPlayer());
    }

    // Fonction de remise à zero du plateau
    public void reset(){
        this.board.reset();
        this.majGame();
    }

    // Envoie à la classe métier quelle pièce doit être promue.
    public void makePromotion(int[] idCase, String idPromo){
        this.board.makePromotion(idCase, idPromo);
    }

    // Vérification de la situation de victoire grâce à la fonction de la classe Echec
    public void verif(){
        if(this.board.complete()){
            final PopupVictoire popupVictoire = new PopupVictoire(this);
            popupVictoire.setTitle("Fin de partie !");

            // Vérifie la victoire et lance un popup dans le cas échéant.
            if ( this.board.getWinner() == 'N' ) {
                popupVictoire.setImageWin(R.drawable.n_pawn);
                popupVictoire.setSubTitle("Le joueur NOIR à gagné !");
            } else {
                popupVictoire.setImageWin(R.drawable.b_pawn);

                popupVictoire.setSubTitle("Le joueur BLANC à gagné !");
            }

            popupVictoire.getResetButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Nouvelle partie ...", Toast.LENGTH_LONG);
                    popupVictoire.dismiss();
                    reset();
                }
            });
            popupVictoire.build();
        }

        // Vérifie la promotion d'une pièce blanche et lance un popup dans le cas échéant.
        else if( this.board.checkPromotion()[0] == 1 ){
            final int[] temp = this.board.checkPromotion();

            final PopupPromotion popupPromotion = new PopupPromotion(this);
            popupPromotion.setTitle("Choisissez la promotion de votre pion");
            popupPromotion.setImagePromo('B');

            popupPromotion.getpIVC().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makePromotion(temp, "BC");
                    popupPromotion.dismiss();
                    majGame();
                }
            });

            popupPromotion.getpIVH().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makePromotion(temp, "BH");
                    popupPromotion.dismiss();
                    majGame();
                }
            });

            popupPromotion.getpIVR().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makePromotion(temp, "BR");
                    popupPromotion.dismiss();
                    majGame();
                }
            });

            popupPromotion.getpIVB().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makePromotion(temp, "BB");
                    popupPromotion.dismiss();
                    majGame();
                }
            });
            popupPromotion.build();
        }

        // Vérifie la promotion d'une pièce noire et lance un popup dans le cas échéant.
        else if( this.board.checkPromotion()[0] == 2 ){
            final int[] temp = this.board.checkPromotion();

            final PopupPromotion popupPromotion = new PopupPromotion(this);
            popupPromotion.setTitle("Choisissez la promotion de votre pion");
            popupPromotion.setImagePromo('N');

            popupPromotion.getpIVC().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makePromotion(temp, "NC");
                    popupPromotion.dismiss();
                    majGame();
                }
            });

            popupPromotion.getpIVH().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makePromotion(temp, "NH");
                    popupPromotion.dismiss();
                    majGame();
                }
            });

            popupPromotion.getpIVR().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makePromotion(temp, "NR");
                    popupPromotion.dismiss();
                    majGame();
                }
            });

            popupPromotion.getpIVB().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makePromotion(temp, "NB");
                    popupPromotion.dismiss();
                    majGame();
                }
            });
            popupPromotion.build();
        }
    }
}