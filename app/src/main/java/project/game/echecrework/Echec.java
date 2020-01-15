package project.game.echecrework;

import android.util.Log;

class Echec {

    /*CONSTANTES*/

    private final int TAILLEX = 8;
    private final int TAILLEY = 8;

    /* ATTRIBUTS*/

    private boolean isPlaying = true;

    // 1 -> B
    // 2 -> N
    private int whoPlay = 1;

    // déclaration du plateau
    private String[][] plateau;

    // Sauvegarder la position des rois
    int xKB, xKN, yKB, yKN;

    // Constructeur
    public Echec(){
        this.plateau = new String[TAILLEX][TAILLEY];
        initPlateau();
        Log.i("Affichage_board", toString());
    }

    // Initialise le plateau avec les pièces au bonne coordonnées.
    // Une case qui n'a pas de pièce est représenté par un "V." qui est une image vide.
    public void initPlateau(){
        this.isPlaying = true;
        this.whoPlay = 1;

        for(int i=0; i < this.TAILLEX; i++){
            for(int k=0; k < this.TAILLEY; k++) {
                this.plateau[i][k] = "V.";
            }
        }

        this.xKN = 0;
        this.yKN = 4;

        this.xKB = 7;
        this.yKB = 4;

        /* Pieces noires */
        this.plateau[0][0] = "NR";
        this.plateau[0][1] = "NH";
        this.plateau[0][2] = "NB";
        this.plateau[0][3] = "NC";
        this.plateau[0][4] = "NK";
        this.plateau[0][5] = "NB";
        this.plateau[0][6] = "NH";
        this.plateau[0][7] = "NR";

        this.plateau[1][0] = "NP";
        this.plateau[1][1] = "NP";
        this.plateau[1][2] = "NP";
        this.plateau[1][3] = "NP";
        this.plateau[1][4] = "NP";
        this.plateau[1][5] = "NP";
        this.plateau[1][6] = "NP";
        this.plateau[1][7] = "NP";

        /* Pieces blanches */
        this.plateau[7][0] = "BR";
        this.plateau[7][1] = "BH";
        this.plateau[7][2] = "BB";
        this.plateau[7][3] = "BC";
        this.plateau[7][4] = "BK";
        this.plateau[7][5] = "BB";
        this.plateau[7][6] = "BH";
        this.plateau[7][7] = "BR";

        this.plateau[6][0] = "BP";
        this.plateau[6][1] = "BP";
        this.plateau[6][2] = "BP";
        this.plateau[6][3] = "BP";
        this.plateau[6][4] = "BP";
        this.plateau[6][5] = "BP";
        this.plateau[6][6] = "BP";
        this.plateau[6][7] = "BP";
    }

    // Fonction de déplacement
    // Les vérifications y sont comprisent.
    public boolean move(int x, int y, int x1, int y1){

        String token = getCase(x, y);
        boolean deplacement = false;

        if((x == x1 && y == y1) || ( getCase(x, y).charAt(0) == getCase(x1, y1).charAt(0) ) || getCase(x, y).charAt(0) != this.getPlayer().charAt(0) ) {
        }else
        /* REGLE DEPLACEMENT */
            switch(token.charAt(1)){
                /* PAWN */
                case 'P':
                    if(token.charAt(0) == 'N' ){
                        /*
                        * déplacement de 2 si position = 1 / 6
                        * déplacement de 1 sinon
                        * déplacement de diagonal si pion à attraper
                        *
                        * */
                        if(
                                x<7 &&
                                        ( (y == y1) && x == (x1 - 1) && getCase(x1, y1).charAt(0)=='V' ) ||
                                        ( (x == 1) && (y == y1) && ( x == (x1 - 2) ) ) ||
                                        ( (x == (x1 - 1)) && (Math.abs(y - y1) == 1) && getCase(x1, y1).charAt(0)!='V' )


                        ) deplacement = true;
                    }
                    else {
                        if(
                                x>0 &&
                                        ( (y == y1) && x == (x1 + 1) && getCase(x1, y1).charAt(0)=='V' ) ||
                                        ( (x == 6) && (y == y1) && ( x == (x1 + 2) ) ) ||
                                        ( (x == (x1 + 1)) && (Math.abs(y - y1) == 1) && getCase(x1, y1).charAt(0)!='V' )


                        ) deplacement = true;
                    }
                    break;
                /* ROOK */
                case 'R':
                    if( !((x != x1) && (y != y1)) ){
                        deplacement = true;
                    }
                    break;
                /* HORSE */
                case 'H':
                    if( ( Math.abs( x - x1 ) == 2 && Math.abs( y - y1 ) == 1 ) || ( Math.abs( x - x1 ) == 1  && Math.abs( y - y1 ) == 2 ) ){
                        deplacement = true;
                    }
                    break;
                /* BISHOP */
                case 'B':
                    if( Math.abs(x - x1) == Math.abs(y - y1) ){
                        deplacement = true;
                    }
                    break;
                /* CROWN */
                case 'C':
                    if( !((x != x1) && (y != y1)) || ( Math.abs(x - x1) == Math.abs(y - y1) ) ){
                        deplacement = true;
                    }
                    break;
                /* KING */
                case 'K':
                    if( (Math.abs(x - x1) <= 1 && Math.abs(y - y1) <= 1) &&
                        (((x!=x1) && (Math.abs(x - x1))<=1) ||((y!=y1) && (Math.abs(y - y1))<=1))
                    ){
                        deplacement = true;
                    }
                    break;
            }

            boolean temp = true;



            if( deplacement ){
                //Verification que la pièce déplacée de saute pas d'autres pièces
                //Verticaux et Horizontaux
                if(y==y1){
                    if(x > x1)
                        for(int i=x-1; i>x1; i--) {
                            if (!getCase(i, y).equals("V.")) deplacement = false;
                        }
                    else
                        for( int i=x+1; i<x1; i++) {
                            if (!getCase(i, y).equals("V.")) deplacement = false;
                        }
                }
                else if(x == x1){
                    if(y > y1)
                        for(int i=y-1; i>y1; i--) {
                            if (!getCase(x, i).equals("V.")) deplacement = false;
                        }
                    else
                        for( int i=y+1; i<y1; i++) {
                            if (!getCase(x, i).equals("V.")) deplacement = false;
                        }
                }
                //Diagonales
                if( x<x1 && y<y1 ){
                    for( int i=x+1, j=y+1; i<x1; i++, j++){
                        if (!getCase(i, j).equals("V.")) deplacement = false; Log.i("Test", "1");
                    }
                }
                else if( x<x1 && y>y1 ){
                    for( int i=x+1, j=y-1; i<x1; i++, j--){
                        if (!getCase(i, j).equals("V.")) deplacement = false; Log.i("Test", "2");
                    }
                }
                else if( x>x1 && y<y1 ){
                    for( int i=x-1, j=y+1; i>x1; i--, j++){
                        if (!getCase(i, j).equals("V.")) deplacement = false; Log.i("Test", "3");
                    }
                }
                else if( x>x1 && y>y1 ){
                    for( int i=x-1, j=y-1; i>x1; i--, j--){
                        if (!getCase(i, j).equals("V.")) deplacement = false; Log.i("Test", "4");
                    }
                }

                // Si le déplacement est correct on l'effectue.
                if(deplacement) {
                    if(getCase(x, y).equals("NK")){
                        this.xKN = x1;
                        this.yKN = y1;
                    }
                    else if(getCase(x, y).equals("BK")){
                        this.xKB = x1;
                        this.yKB = y1;
                    }
                    this.plateau[x1][y1] = this.plateau[x][y];
                    this.plateau[x][y] = "V.";
                }
            }
            return deplacement;
    }

    //Affichage en mode console.
    public String toString() {
        String s = "      1  2  3  4  5  6  7  8";


        s += "\n     --------------------\n1   |";

        for(int i=0; i < this.TAILLEX; i++){
            for(int k=0; k < this.TAILLEY; k++){
                s += this.plateau[i][k] + "|";
            }
            if(i<7) s += "\n     --------------------\n" + (i+2) + "   |";
            else    s += "\n     --------------------\n";
        }

        return s;
    }

    // Passe le tour d'un joueur à l'autre.
    public void changePlayer(){
        if (this.whoPlay == 1) this.whoPlay += 1; else this.whoPlay -= 1;
    }

    // Récupère le joueur qui joue actuellement.
    public String getPlayer(){
        if(this.whoPlay == 1) return "Blanc"; else return "Noir";
    }

    // Récupère la valeur d'une case.
    // La valeur étant la pièce (ou non) qui l'occupe.
    public String getCase(int x, int y) {
        return this.plateau[x][y];
    }

    // Vérification de la situation d'échec et échec et mat
    public boolean complete() {

        /*for(int i = 0; i < this.TAILLEX; i++){
            for(int j = 0; j < this.TAILLEX; j++) {
                switch(getCase(i, j)){
                    case "BP":

                        break;

                }
            }
        }*/

        if( getCase( this.xKB-1, this.yKB-1 ).equals("NB") ){
            isPlaying = !isPlaying;
        }

        //if( this.plateau[5][0].equals("BP") ) isPlaying = false;
        return !isPlaying;
    }
}