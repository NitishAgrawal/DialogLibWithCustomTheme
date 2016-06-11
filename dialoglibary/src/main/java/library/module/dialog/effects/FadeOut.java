package library.module.dialog.effects;

import android.view.View;

import com.nineoldandroids.animation.ObjectAnimator;

public class FadeOut extends BaseEffects{

    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view,"alpha",1,0).setDuration(mDuration)

        );
    }
}
