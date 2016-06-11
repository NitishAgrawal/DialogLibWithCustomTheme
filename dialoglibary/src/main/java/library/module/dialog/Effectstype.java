package library.module.dialog;

import library.module.dialog.effects.BaseEffects;
import library.module.dialog.effects.FadeIn;
import library.module.dialog.effects.Fall;
import library.module.dialog.effects.FlipH;
import library.module.dialog.effects.FlipV;
import library.module.dialog.effects.NewsPaper;
import library.module.dialog.effects.RotateLeft;
import library.module.dialog.effects.SideFall;
import library.module.dialog.effects.RotateBottom;
import library.module.dialog.effects.SlideBottom;
import library.module.dialog.effects.SlideLeft;
import library.module.dialog.effects.SlideRight;
import library.module.dialog.effects.SlideTop;
import library.module.dialog.effects.Slit;

public enum  Effectstype {

    Fadein(FadeIn.class),
   // FadeOut(FadeIn.class),
    Slideleft(SlideLeft.class),
    Slidetop(SlideTop.class),
    SlideBottom(SlideBottom.class),
    Slideright(SlideRight.class),
    Fall(Fall.class),
    Newspager(NewsPaper.class),
    Fliph(FlipH.class),
    Flipv(FlipV.class),
    RotateBottom(RotateBottom.class),
    RotateLeft(RotateLeft.class),
    Slit(Slit.class),
    Shake(library.module.dialog.effects.Shake.class),
    Sidefill(SideFall.class);
    private Class<? extends BaseEffects> effectsClazz;

    private Effectstype(Class<? extends BaseEffects> mclass) {
        effectsClazz = mclass;
    }

    public BaseEffects getAnimator() {
        BaseEffects bEffects=null;
	try {
		bEffects = effectsClazz.newInstance();
	} catch (ClassCastException e) {
		throw new Error("Can not init animatorClazz instance");
	} catch (InstantiationException e) {
		// TODO Auto-generated catch block
		throw new Error("Can not init animatorClazz instance");
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		throw new Error("Can not init animatorClazz instance");
	}
	return bEffects;
    }
}
