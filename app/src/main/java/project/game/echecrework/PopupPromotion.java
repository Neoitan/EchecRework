package project.game.echecrework;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class PopupPromotion extends Dialog {

    private String pTitle;

    private int drawableC;
    private int drawableH;
    private int drawableR;
    private int drawableB;

    private ImageButton pIVC;
    private ImageButton pIVH;
    private ImageButton pIVR;
    private ImageButton pIVB;

    private TextView pTitleView;

    public PopupPromotion(Activity activity ){
        super(activity, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.popup_promotion_template);

        this.pTitle = "titre du popup";
        this.pIVC = findViewById(R.id.pIVC);
        this.pIVH = findViewById(R.id.pIVH);
        this.pIVR = findViewById(R.id.pIVR);
        this.pIVB = findViewById(R.id.pIVB);
        this.pTitleView = findViewById(R.id.pTitle);
    }

    public void setTitle(String title){ this.pTitle = title; }

    public void setImagePromo(char j){
        if( j == 'N' ){
            this.drawableC = R.drawable.n_crown;
            this.drawableH = R.drawable.n_horse;
            this.drawableR = R.drawable.n_rook;
            this.drawableB = R.drawable.n_bishop;
        }
        else if( j == 'B' ){
            this.drawableC = R.drawable.b_crown;
            this.drawableH = R.drawable.b_horse;
            this.drawableR = R.drawable.b_rook;
            this.drawableB = R.drawable.b_bishop;
        }
    }

    public ImageButton getpIVC(){ return this.pIVC; }
    public ImageButton getpIVH(){ return this.pIVH; }
    public ImageButton getpIVR(){ return this.pIVR; }
    public ImageButton getpIVB(){ return this.pIVB; }

    public void build(){
        show();
        this.pTitleView.setText(this.pTitle);

        this.pIVC.setImageResource(this.drawableC);
        this.pIVH.setImageResource(this.drawableH);
        this.pIVR.setImageResource(this.drawableR);
        this.pIVB.setImageResource(this.drawableB);

    }
}
