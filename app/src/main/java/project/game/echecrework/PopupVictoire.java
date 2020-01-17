package project.game.echecrework;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PopupVictoire extends Dialog {

    private String pTitle;
    private String pSubTitle;
    private Button pReset;
    private int drawable;
    private ImageView pIVWinView;
    private TextView pTitleView, pSubTitleView;

    public PopupVictoire(Activity activity ){
        super(activity, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.popup_victoire_template);

        this.pTitle = "titre du popup";
        this.pSubTitle = "Sous-titre de popup";
        this.pReset = findViewById(R.id.pReset);
        this.pIVWinView = findViewById(R.id.pIVWin);
        this.pTitleView = findViewById(R.id.pTitle);
        this.pSubTitleView = findViewById(R.id.pSubTitle);
    }

    public void setTitle(String title){ this.pTitle = title; }
    public void setSubTitle(String subTitle){ this.pSubTitle = subTitle; }
    public void setImageWin(int resId){ this.drawable = resId; }

    public Button getResetButton(){ return this.pReset; }

    public void build(){
        show();
        this.pTitleView.setText(this.pTitle);
        this.pSubTitleView.setText(this.pSubTitle);
        this.pIVWinView.setImageResource(this.drawable);
    }
}
