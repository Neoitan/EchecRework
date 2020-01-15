package project.game.echecrework;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    private Echec board;

    private ImageButton IDBoard[][];
    private int idButton;

    private Button bReset;
    private TextView tvPlayer;

    private int nbTurn = 1;

    /*SAVE PLAY*/

    int tokens[] = {-1, -1, -1, -1};

    private boolean firstTouch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        this.board = new Echec();
        this.bReset = findViewById(R.id.bReset);
        this.bReset.setOnClickListener(this);

        this.tvPlayer = findViewById(R.id.tvPlayer);

        initBoard();
    }

    private void initBoard() {
        this.IDBoard = new ImageButton[8][8];
        this.idButton = 0;
        /*CREATION DU LAYOUT*/

        final TableLayout tableLayout = (TableLayout) findViewById(R.id.TLBoard);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

        /* CREATION DE LA ROW */
        for(int i=0; i < 8; i++){
            final TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

            /* CREATION DU IMAGEBUTTON */
            for (int k = 0; k < 8; k++) {
                // Creation  button
                final ImageButton button = new ImageButton(this);

                Log.i("CHECK", "Check image" + this.idButton);

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

    // Mise à jour du plateau
    private void majButton() {
        for(int i=0; i < 8; i++) {
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
        for( int i = 0; i < 8; i++){
            for( int k = 0; k < 8; k++) {
                if (this.IDBoard[i][k].getId() == id){
                    int tabId[] = {i, k};
                    return tabId;
                }
            }
        }
        return null;
    }

    // Fonction d'évènement de click
    @Override
    public void onClick(View v) {

        if(v == this.bReset) reset();
        else {
            Log.i("COORD", v.getId()+"\n"+searchIndex(v.getId())[0]+"-"+searchIndex(v.getId())[1] + "\n" + this.board.getCase(searchIndex(v.getId())[0], searchIndex(v.getId())[1]));
            if(this.firstTouch && !this.board.getCase(searchIndex(v.getId())[0], searchIndex(v.getId())[1]).equals("V.")) {

                    this.tokens[0] = searchIndex(v.getId())[0];
                    this.tokens[1] = searchIndex(v.getId())[1];
                    this.firstTouch = false;

            }
            else if(!this.firstTouch){
                this.tokens[2] = searchIndex(v.getId())[0];
                this.tokens[3] = searchIndex(v.getId())[1];
                this.firstTouch = true;

                if( board.move(tokens[0], tokens[1], tokens[2], tokens[3]) ) {
                    this.board.changePlayer();
                    this.nbTurn++;
                }
                this.tokens[0] = -1;
                this.tokens[1] = -1;
                this.tokens[2] = -1;
                this.tokens[3] = -1;

            }
        }

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
        this.nbTurn = 1;
        this.board.reset();
        this.majGame();
    }

    // Vérification de la situation d'échec et échec et mat grâce à la fonction de la classe Echec
    public void verif(){
        if(this.board.complete()){
            final CustomPopup customPopup = new CustomPopup(this);
            customPopup.setTitle("Fin de partie !");

            if ( this.board.getWinner() == 'N' )
                customPopup.setImageWin(R.drawable.n_pawn);
            else
                customPopup.setImageWin(R.drawable.b_pawn);

            customPopup.setSubTitle("Le joueur " + this.board.getWinner() + " à gagné !");
            customPopup.getResetButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Nouvelle partie ...", Toast.LENGTH_LONG);
                    customPopup.dismiss();
                    reset();
                }
            });
            customPopup.build();
        }
    }
}
