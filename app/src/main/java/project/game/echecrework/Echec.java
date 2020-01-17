/*
 * Henry Florian - Antoine Levasseur
 *
* Class Echec
 * Classe métier comprennant les méthodes du jeu.
 *
 *       INDEX
 *
 * R -> Rook   (Tour    )
 * H -> Horse  (Cavalier)
 * B -> Bishop (Fou     )
 * C -> Crown  (Reine   )
 * K -> King   (Roi     )
 * P -> Pawn   (Pion    )
 *
* */

package project.game.echecrework;

class Echec {

    /*CONSTANTES*/

    private final int TAILLEX = 8;
    private final int TAILLEY = 8;

    /* ATTRIBUTS*/

    private boolean isPlaying = true;

    // Variable pour savoir si les deux rois sont encore sur le plateau.
    // Dans le cas contraire la partie se termine.
    boolean kJB = true, kJN = true;
    // 1 -> Joueur Blanc
    // 2 -> Joueur Noir
    private int whoPlay = 1;
    // déclaration du plateau
    private String[][] plateau;

    // Constructeur
    public Echec(){
        // Initialise le plateau
        this.plateau = new String[TAILLEX][TAILLEY];
        this.initPlateau();
    }

    // Initialise le plateau avec les pièces aux bonnes coordonnées.
    // Une case qui n'a pas de pièce est représenté par un "V." qui est une image vide.
    public void initPlateau(){
        // Le jeu commence.
        this.isPlaying = true;

        // Tel que le jeu originel, les blanc commence.
        this.whoPlay = 1;

        // Initialisation du plateau avec des cases vides
        for(int i=0; i < this.TAILLEX; i++){
            for(int k=0; k < this.TAILLEY; k++) {
                this.plateau[i][k] = "V.";
            }
        }

        // On place les pièces indiviuellement.
        /* Pieces noires */
        this.plateau[0][0] = "NR";
        this.plateau[0][1] = "NH";
        this.plateau[0][2] = "NB";
        this.plateau[0][3] = "NC";
        this.plateau[0][4] = "NK";
        this.plateau[0][5] = "NB";
        this.plateau[0][6] = "NH";
        this.plateau[0][7] = "NR";
        for (int i = 0; i < this.TAILLEX; i++) this.plateau[1][i] = "NP";

        /* Pieces blanches */
        this.plateau[7][0] = "BR";
        this.plateau[7][1] = "BH";
        this.plateau[7][2] = "BB";
        this.plateau[7][3] = "BC";
        this.plateau[7][4] = "BK";
        this.plateau[7][5] = "BB";
        this.plateau[7][6] = "BH";
        this.plateau[7][7] = "BR";
        for (int i = 0; i < this.TAILLEX; i++) this.plateau[6][i] = "BP";
    }

    // Fonction de déplacement
    // Les vérifications y sont comprisent.
    public boolean move(int x, int y, int x1, int y1){

        // On sauvegarde la case du pion à déplacer.
        String token = getCase(x, y);

        ///Avant les vérification le déplacement n'est pas autorisé.
        boolean deplacement = false;

        // Le déplacement est faux si le joueur veut déplacer une pièce :
        //      sur elle même.
        //      Si les deux pièces sont de la même couleur
        //      Si la case à déplacer n'appartient pas au joueur actuel;
        if((x == x1 && y == y1) || ( getCase(x, y).charAt(0) == getCase(x1, y1).charAt(0) ) || getCase(x, y).charAt(0) != this.getPlayer().charAt(0) ) {
        } else {
            /* REGLE DEPLACEMENT */
            // Selon la pièce à déplacer on limite les mouvements possibles.
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
                                        ( (x == 1) && (y == y1) && ( x == (x1 - 2) ) && getCase(x1, y1).charAt(0)=='V') ||
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
        }

        // Malgré un déplacement correct on dooit vérifier que le joueur ne passe pas au dessus
        // d'une autre pièce.
        if( deplacement ){
            if( getCase(x, y).charAt(1) != 'H') {
                //Verticaux et Horizontaux
                if (y == y1) {
                    if (x > x1)
                        for (int i = x - 1; i > x1; i--) {
                            if (!getCase(i, y).equals("V.")) deplacement = false;
                        }
                    else
                        for (int i = x + 1; i < x1; i++) {
                            if (!getCase(i, y).equals("V.")) deplacement = false;
                        }
                } else if (x == x1) {
                    if (y > y1)
                        for (int i = y - 1; i > y1; i--) {
                            if (!getCase(x, i).equals("V.")) deplacement = false;
                        }
                    else
                        for (int i = y + 1; i < y1; i++) {
                            if (!getCase(x, i).equals("V.")) deplacement = false;
                        }
                }
                //Diagonales
                if (x < x1 && y < y1) {
                    for (int i = x + 1, j = y + 1; i < x1; i++, j++) {
                        if (!getCase(i, j).equals("V.")) deplacement = false;
                    }
                } else if (x < x1 && y > y1) {
                    for (int i = x + 1, j = y - 1; i < x1; i++, j--) {
                        if (!getCase(i, j).equals("V.")) deplacement = false;
                    }
                } else if (x > x1 && y < y1) {
                    for (int i = x - 1, j = y + 1; i > x1; i--, j++) {
                        if (!getCase(i, j).equals("V.")) deplacement = false;
                    }
                } else if (x > x1 && y > y1) {
                    for (int i = x - 1, j = y - 1; i > x1; i--, j--) {
                        if (!getCase(i, j).equals("V.")) deplacement = false;
                    }
                }
            }

            // Si le déplacement est correct on l'effectue.
            if(deplacement) {

                // On vérifie si l'un des deux rois n'as pas été sauté.
                if(getCase(x1, y1).equals("NK")){
                    this.kJN = false;
                }
                else if(getCase(x, y).equals("BK")){
                    this.kJB = false;
                }

                // On échange les places
                // Dans tous les cas, la place qu'occupait une pièce devient vide.
                this.plateau[x1][y1] = this.plateau[x][y];
                this.plateau[x][y] = "V.";

                // On vérifie si un pion peux être promu.
                this.checkPromotion();
            }
        }
        return deplacement;
    }

    // Vérification des promotion d'un pion.
    public int[] checkPromotion() {
        int[] tabTemp = {'0', '0', '0'};
        for(int i = 0; i<this.TAILLEX; i++){
            if( this.plateau[0][i].equals("BP") ) {
                tabTemp[0] = 1;
                tabTemp[1] = 0;
                tabTemp[2] = i;
            }
            if( this.plateau[this.TAILLEY-1][i].equals("NP") ) {
                tabTemp[0] = 2;
                tabTemp[1] = this.TAILLEY-1;
                tabTemp[2] = i;
            }
        }
        return tabTemp;
    }

    // Méthode de promition d'une pièce via la popup
    public void makePromotion(int[] idCase, String idPromo) {
        this.plateau[idCase[1]][idCase[2]] = idPromo;
    }

    // Passe le tour d'un joueur Ã  l'autre.
    public void changePlayer(){
        if (this.whoPlay == 1) this.whoPlay += 1; else this.whoPlay -= 1;
    }

    // RécupÃ¨re le joueur qui joue actuellement.
    public String getPlayer(){
        if(this.whoPlay == 1) return "Blanc"; else return "Noir";
    }

    // RécupÃ¨re la valeur d'une case.
    // La valeur étant la piÃ¨ce (ou non) qui l'occupe.
    public String getCase(int x, int y) {
        return this.plateau[x][y];
    }

    //renvoie la couleur du houeur qui à encore son roi sur le plateau.
    public char getWinner(){
        if( !this.kJB ){
            return 'N';
        } else return 'B';
    }

    // Vérification de la situation d'échec et échec et mat
    public boolean complete() {

        this.kJN = false;
        this.kJB = false;

        for(int i=0; i < this.TAILLEX; i++){
            for(int k=0; k < this.TAILLEY; k++) {
                if( this.plateau[i][k] == "NK" ) this.kJN = true;
                if( this.plateau[i][k] == "BK" ) this.kJB = true;
            }
        }

        if( !this.kJB || !this.kJN ) this.isPlaying = false;

        return !isPlaying;
    }

    // Méthode de remise à zéro du plateau et du boolean disant si le jeu est en cour.
    public void reset(){
        this.initPlateau();
        this.isPlaying = true;
    }
}
