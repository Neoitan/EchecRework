/*package project.game.echecrework;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PopupPromotion extends Dialog {

    private String pTitle;
    private int drawable;
    private ImageView pIVQ;
    private ImageView pIVK;
    private ImageView pIVR;
    private ImageView pIVB;
    private TextView pTitleView, pSubTitleView;

    public PopupPromotion(Activity activity ){
        super(activity, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.popup_template);

        this.pTitle = "titre du popup";
        this.pIVQ = findViewById(R.drawable.b_crown);
        this.pIVQ = findViewById(R.drawable.b_horse);
        this.pIVQ = findViewById(R.drawable.b_rook);
        this.pIVQ = findViewById(R.drawable.b_bishop);
        this.pTitleView = findViewById(R.id.pTitle);
        this.pSubTitleView = findViewById(R.id.pSubTitle);
    }

    public void setTitle(String title){ this.pTitle = title; }
    public void setImageWin(int resId){ this.drawable = resId; }

    public Button getResetButton(){ return this.pReset; }

    public void build(){
        show();
        this.pTitleView.setText(this.pTitle);
        this.pSubTitleView.setText(this.pSubTitle);
        this.pIVWinView.setImageResource(this.drawable);
    }

}
*/