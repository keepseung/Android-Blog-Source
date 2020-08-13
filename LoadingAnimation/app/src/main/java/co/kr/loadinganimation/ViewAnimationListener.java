package kr.onelive.listener;

import android.view.View;
import android.view.animation.Animation;

public class ViewAnimationListener implements Animation.AnimationListener {
    View btn;

    int OPEN = 0;
    int CLOSE = 1;

    int type = 0;

    public ViewAnimationListener(View btn, int type){
        this.btn = btn;
        this.type = type;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if(type == OPEN){
            btn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(type == CLOSE){
            btn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
