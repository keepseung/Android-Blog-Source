package co.kr.loadinganimation;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

public class LoadingAnimationListener implements Animation.AnimationListener {
    Context mContext;

    View btn;

    int OPEN = 0;
    int CLOSE = 1;

    int type;

    public LoadingAnimationListener(Context mContext, View btn, int type){
        this.mContext = mContext;
        this.btn = btn;
        this.type = type;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        // 애니메이션 시작할 떄
        if(type == OPEN){
            btn.setVisibility(View.VISIBLE);
            ((MainActivity) mContext).loadingLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(type == CLOSE){
            btn.setVisibility(View.INVISIBLE);
            ((MainActivity) mContext).loadingLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
