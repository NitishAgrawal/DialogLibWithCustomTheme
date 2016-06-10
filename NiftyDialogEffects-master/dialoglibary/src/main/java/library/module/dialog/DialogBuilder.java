package library.module.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import library.module.dialog.effects.BaseEffects;

import java.util.ArrayList;
import java.util.HashMap;

public class DialogBuilder extends Dialog implements DialogInterface {

    private static boolean isNotDefault = false;
    private static String titleColor = "#000000";
    private static String dividerColor = "#9E9E9E";
    private static String messageColor = "#000000";
    private static String dialogBackground = "#FFFFFF";
    private static String rowTitle = "#212121";
    private static String rowSummery = "#212121";
    private static String rowImgBg = "#00000000";
    private static String rowBg = "#FFFFFF";

    private static Context tmpContext;

    public enum ThemeType {
        DARK,
        LIGHT,
        DEFAULT,
        CUSTOM
    }

    private static ThemeType defaultTheme = ThemeType.LIGHT;

    static HashMap<String, Integer> rowcolorMap = new HashMap<String, Integer>();

    private Effectstype startType = null;

    private Effectstype endType = null;

    private LinearLayout mLinearLayoutView;

    private RelativeLayout mRelativeLayoutView;

    private LinearLayout mLinearLayoutMsgView;

    private LinearLayout mLinearLayoutTopView;

    private FrameLayout mFrameLayoutCustomView;

    private View mDialogView;

    private View mDivider;

    private TextView mTitle;

    private TextView mMessage;

    private ImageView mIcon;

    private Button mButton1;

    private Button mButton2;

    private ListView listView;

    private ArrayList<HashMap<String, Object>> list;

    private int mDuration = -1;

    private static int mOrientation = 1;

    private boolean isCancelable = true;

    private static DialogBuilder instance;

    private static String defaultTitle = "Alert";

    public DialogBuilder(Context context) {
        super(context);
        init(context);
    }


    public DialogBuilder(Context context, String title, int resource, ArrayList<HashMap<String, Object>> list
            , int theme, boolean isCancelable, Effectstype effect, int time, boolean isShowBtn1, ThemeType themeType) {
        super(context, theme);
        this.list = list;
        init(context, title, resource, list, isCancelable, effect, time, isShowBtn1, themeType);
    }

    public DialogBuilder(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    public static void setDefaultTitle(String title) {
        DialogBuilder.defaultTitle = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

    }

    public static DialogBuilder getInstance(Context context, String dialogTitle, int resource
            , ArrayList<HashMap<String, Object>> list, boolean isCancelable, Effectstype effect,
                                            int time, boolean isShowBtn1, ThemeType themeType) {
        //if (instance == null || !tmpContext.equals(context)) {
        synchronized (DialogBuilder.class) {
            // if (instance == null || !tmpContext.equals(context)) {
            tmpContext = context;
            if (list != null) {
                instance = new DialogBuilder(context, dialogTitle, resource, list, R.style.dialog_untran, isCancelable, effect, time, isShowBtn1, themeType);
            } else {
                instance = new DialogBuilder(context, R.style.dialog_untran);
            }
            //  }
        }
        //}

       /* if(notNull(dialogTitle))
        instance.setTitle(dialogTitle);

        instance.isCancelableOnTouchOutside(isCancelable).
        withStartEffect(effect).
        withDuration(time)
       // setButton1DefaultClick(isShowBtn1?instance:null).
        ;*/
        return instance;
    }

    public static boolean notNull(String string) {
        if (string != null && string.length() > 0) {
            return true;
        }
        return false;
    }

    public static DialogBuilder getInstance(Context context) {
       /* if (instance == null || !tmpContext.equals(context)) {
            synchronized (DialogBuilder.class) {
                if (instance == null || !tmpContext.equals(context)) {
                    instance = new DialogBuilder(context,R.style.dialog_untran);
                }
            }
        }
        tmpContext = context;
        return instance;*/
        return getInstance(context, null, 0, null, true, null, 0, true, ThemeType.DEFAULT);
    }

    private void init(Context context, String dialogTitle, int resource, ArrayList<HashMap<String, Object>> list
            , boolean isCancelable, Effectstype effect, int duration, boolean isShowBtn1, ThemeType themeType) {
        mDialogView = View.inflate(context, R.layout.dialog_layout_list, null);
        commonInit(context);
        listView = (ListView) mDialogView.findViewById(R.id.list);
        ListDialogAdapter listDialogAdapter = null;
        //if(resource == 0) {
        listDialogAdapter = new ListDialogAdapter(context, R.layout.list_dialog_two_line_row, list, getRowColors(themeType));
        //}
        listView.setAdapter(listDialogAdapter);

        mLinearLayoutMsgView.setVisibility(View.VISIBLE);
        //this.setDefaultTitle(dialogTitle);
        withTitle(dialogTitle)
                .isCancelableOnTouchOutside(isCancelable)
                .withDuration(duration)
                .withStartEffect(effect)
                .withEndEffect(null)
                .setButton1DefaultClick(this)
                .show();
    }

    public HashMap<String, Integer> getRowColors(ThemeType themeType) {
        setTheme(themeType, true);
        return rowcolorMap;
    }

    public static void setRowColors(String colorTitle, String colorSummary, String colorImageBg,
                                    String colorRowBG) {
        rowcolorMap.put(ListDialogAdapter.TITLE_COLOR, Color.parseColor(colorTitle));
        rowcolorMap.put(ListDialogAdapter.SUMMARY_COLOR, Color.parseColor(colorSummary));
        rowcolorMap.put(ListDialogAdapter.IMAGE_BG_COLOR, Color.parseColor(colorImageBg));
        rowcolorMap.put(ListDialogAdapter.ROW_BG_COLOR, Color.parseColor(colorRowBG));
    }

    private void init(Context context) {

        mDialogView = View.inflate(context, R.layout.dialog_layout, null);
        mFrameLayoutCustomView = (FrameLayout) mDialogView.findViewById(R.id.customPanel);
        commonInit(context);
        mMessage = (TextView) mDialogView.findViewById(R.id.message);
    }

    private void commonInit(Context context) {

        mLinearLayoutView = (LinearLayout) mDialogView.findViewById(R.id.parentPanel);
        mRelativeLayoutView = (RelativeLayout) mDialogView.findViewById(R.id.main);
        mLinearLayoutTopView = (LinearLayout) mDialogView.findViewById(R.id.topPanel);
        mLinearLayoutMsgView = (LinearLayout) mDialogView.findViewById(R.id.contentPanel);

        mTitle = (TextView) mDialogView.findViewById(R.id.alertTitle);
        mIcon = (ImageView) mDialogView.findViewById(R.id.icon);
        mDivider = mDialogView.findViewById(R.id.titleDivider);
        mButton1 = (Button) mDialogView.findViewById(R.id.button1);
        mButton1.setText("working");
        mButton2 = (Button) mDialogView.findViewById(R.id.button2);

        setContentView(mDialogView);

        this.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                mLinearLayoutView.setVisibility(View.VISIBLE);
                if (startType != null) {
                    //startType =Effectstype.Slidetop;
                    start(startType);
                }
            }
        });

      /*  this.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if(endType !=null){
                    //endType =Effectstype.Slidetop;
                    end(endType);
                }
            }
        });*/

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (endType != null) {
                    //endType =Effectstype.Slidetop;
                    end(endType);
                }
            }
        });

        mRelativeLayoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCancelable) dismiss();
            }
        });

    }

    public static DialogBuilder setDefaultCustomDialog(Context mContext, String mainPanalMsg) {
        return setDefaultCustomDialog(mContext, mainPanalMsg, null);
    }

    public static DialogBuilder setDefaultCustomDialog(Context mContext, String mainPanalMsg, String titleText) {
        return setDefaultCustomDialog(mContext, mainPanalMsg, titleText, true);
    }

    public static DialogBuilder setDefaultCustomDialog(Context mContext, String mainPanalMsg, boolean isShowBtn, String titleText) {
        return setDefaultCustomDialog(mContext, mainPanalMsg, titleText, true, null, null, 1, isShowBtn, getDefatulTheme());
    }

    public static DialogBuilder setDefaultCustomDialog(Context mContext, String mainPanalMsg, String titleText, boolean isCancelable) {
        return setDefaultCustomDialog(mContext, mainPanalMsg, titleText, true, null, null);
    }

    public static DialogBuilder setDefaultCustomDialog(Context mContext, String mainPanalMsg, String titleText, boolean isCancelable, Effectstype startTypeEf, Effectstype endTypeEf) {
        return setDefaultCustomDialog(mContext, mainPanalMsg, titleText, true, null, null, 1);
    }

    public static DialogBuilder setDefaultCustomDialog(Context mContext, String mainPanalMsg, String titleText, boolean isCancelable, Effectstype startTypeEf, Effectstype endTypeEf, int time) {
        return setDefaultCustomDialog(mContext, mainPanalMsg, titleText, true, null, null, 1, false, getDefatulTheme());
    }

    public static DialogBuilder setDefaultCustomDialog(Context mContext, String titleText, boolean isCancelable, Effectstype startTypeEf, Effectstype endTypeEf, int time, boolean isShowBtn1) {
        return setDefaultCustomDialog(mContext, null, titleText, true, null, null, 1, false, getDefatulTheme());
    }

    public static DialogBuilder setDefaultCustomDialog(Context mContext, String mainPanalMsg, String titleText, boolean isCancelable, Effectstype startTypeEf, Effectstype endTypeEf, int time, boolean isShowBtn1, ThemeType themeType) {

        DialogBuilder dialogBuilder = DialogBuilder.getInstance(mContext);
        dialogBuilder.withTitle(titleText)
                .withMessage(mainPanalMsg)
                .setThemeDark()
                .isCancelableOnTouchOutside(isCancelable)
                .withDuration(time)
                .withStartEffect(startTypeEf)
                .withEndEffect(endTypeEf)
                .setButton1DefaultClick(isShowBtn1 ? dialogBuilder : null)
                .setTheme(themeType)
                .show();
        return dialogBuilder;
    }

    public static ThemeType getDefatulTheme() {
        if (isNotDefault) {
            return ThemeType.DEFAULT;
        } else {
            return ThemeType.LIGHT;
        }
    }

    public static DialogBuilder setDefaultCustomDialogList(DialogBuilder dialogBuilder, String titleText, boolean isCancelable, Effectstype startTypeEf, Effectstype endTypeEf, int time, boolean isShowBtn1) {

        dialogBuilder.withTitle(titleText)
                .setThemeDefault()
                .isCancelableOnTouchOutside(isCancelable)
                .withDuration(time)
                .withStartEffect(startTypeEf)
                .withEndEffect(endTypeEf)
                .show();

        return dialogBuilder;
    }


    /**
     * Add a drowable image to set in dialog list divider
     *
     * @param image to set in divider.
     */

    public DialogBuilder setListDividerDrawable(Drawable image) {
        try {
            listView.setDivider(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Add a HashColor to set in dialog list divider
     *
     * @param colorHashCode pass a hash color string.
     */

    public DialogBuilder setListDividerColor(String colorHashCode) {
        try {
            colorHashCode = colorHashCode.replace("#", "0x");
            int col = Integer.parseInt(colorHashCode);
            listView.setDivider(new ColorDrawable(col));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Add height to set in dialog list divider
     *
     * @param height pass a int value to set height.
     */

    public DialogBuilder setListDividerHeight(int height) {
        listView.setDividerHeight(height);
        return this;
    }

    /**
     * Set a custom adapter in the dialog listview
     *
     * @param adapter The ListAdapter which is responsible for maintaining the
     *                data in the customize view.
     */

    public DialogBuilder setListAdapter(BaseAdapter adapter) {
        listView.setAdapter(adapter);
        return this;
    }


    /**
     * Set a default theme for simple dialog for the hole app
     *
     * @param titleColor       The Dialog title color.
     * @param dividerColor     The dividerColor of simple dialog (No list view).
     * @param messageColor     The Dialog middle summery message color.
     * @param dialogBackground The Dialog background color.
     */

    public static void setDefaultThemeData(String titleColor, String dividerColor, String messageColor,
                                           String dialogBackground) {
        setDefaultThemeData(titleColor, dividerColor, messageColor, dialogBackground, "#FFFFFF", "#FFFFFF", "#00000000", "#212121");
    }

    /**
     * Set a default theme for dialog with list for the hole app
     *
     * @param titleColor       The Dialog title color.
     * @param dividerColor     The dividerColor of simple dialog (No list view).
     * @param messageColor     The Dialog middle summery message color.
     * @param dialogBackground The Dialog background color.
     * @param rowTitle         The Dialog listview row title color.
     * @param rowSummery       The Dialog listview row summery color.
     * @param rowImgBg         The Dialog listview image layout background color.
     * @param rowBg            The Dialog listview background color.
     */

    public static void setDefaultThemeData(String titleColor, String dividerColor, String messageColor,
                                           String dialogBackground, String rowTitle, String rowSummery,
                                           String rowImgBg, String rowBg) {
        DialogBuilder.isNotDefault = true;
        DialogBuilder.titleColor = titleColor;
        DialogBuilder.dividerColor = dividerColor;
        DialogBuilder.messageColor = messageColor;
        DialogBuilder.dialogBackground = dialogBackground;
        DialogBuilder.rowTitle = rowTitle;
        DialogBuilder.rowSummery = rowSummery;
        DialogBuilder.rowImgBg = rowImgBg;
        DialogBuilder.rowBg = rowBg;
    }

    /**
     * Set a default theme get data from already save data of colors
     */

    public DialogBuilder setDefaultTheme() {
        return setThemeCustom(DialogBuilder.titleColor, DialogBuilder.dividerColor, DialogBuilder.messageColor,
                DialogBuilder.dialogBackground, DialogBuilder.rowTitle, DialogBuilder.rowSummery,
                DialogBuilder.rowImgBg, DialogBuilder.rowBg);
    }

    /**
     * Set a custom theme for a simple dialog.
     *
     * @param titleColor       The Dialog title color.
     * @param dividerColor     The dividerColor of simple dialog (No list view).
     * @param messageColor     The Dialog middle summery message color.
     * @param dialogBackground The Dialog background color.
     */

    public DialogBuilder setThemeCustom(String titleColor, String dividerColor, String messageColor,
                                        String dialogBackground) {
        return setThemeCustom(titleColor, dividerColor, messageColor, dialogBackground, null, null, null, null);
    }

    /**
     * Set a default theme for a dialog with list .
     *
     * @param titleColor       The Dialog title color.
     * @param dividerColor     The dividerColor of simple dialog (No list view).
     * @param messageColor     The Dialog middle summery message color.
     * @param dialogBackground The Dialog background color.
     * @param rowTitle         The Dialog listview row title color.
     * @param rowSummery       The Dialog listview row summery color.
     * @param rowImgBg         The Dialog listview image layout background color.
     * @param rowBg            The Dialog listview background color.
     */

    public DialogBuilder setThemeCustom(String titleColor, String dividerColor, String messageColor,
                                        String dialogBackground, String rowTitle, String rowSummery,
                                        String rowImgBg, String rowBg) {
        withTitleColor(titleColor);                                          //Def Title text color
        withDividerColor(dividerColor);                                      //Def Divider color
        withMessageColor(messageColor);                                      //Def Simple Dialog message color
        withDialogColor(dialogBackground);                                   //Def Dialog background color
        mButton1.setTextColor(Color.parseColor(titleColor));                 //Def Dialog Button1 background color
        mButton2.setTextColor(Color.parseColor(titleColor));                 //Def Dialog Button2 background color
        if (notNull(rowTitle) && notNull(rowSummery) && notNull(rowImgBg) && notNull(rowBg))
            setRowColors(rowTitle, rowSummery, rowImgBg, rowBg);//Def List Dialog items colors(RowTitle,RowSummeryMsg,ImageBackgroundColor,RowBackgroundColor)
        return this;
    }

    /**
     * Set a default theme get data from already save data of colors
     */

    public DialogBuilder setThemeDefault() {
        return setThemeDefault(false);
    }

    /**
     * Set a default theme get data from already save data of colors
     *
     * @param isListDialog pass true if dialog hava a listview
     */

    public DialogBuilder setThemeDefault(boolean isListDialog) {
        return setTheme(defaultTheme, isListDialog);
    }

    /**
     * Set a dark theme by defalut colors.
     */

    public DialogBuilder setThemeDark() {
        return setThemeDark(false);
    }

    /**
     * Set a dark theme by defalut colors.
     *
     * @param isListDialog pass true if dialog hava a listview
     */

    public DialogBuilder setThemeDark(boolean isListDialog) {
        withTitleColor("#FFFFFF");                                  //Def Title text color
        withDividerColor("#000000");                                //Def Divider color
        withDialogColor("#212121");                                 //Def Dialog background color
        mButton1.setTextColor(Color.parseColor("#FFFFFF"));         //Def Dialog Button1 background color
        mButton2.setTextColor(Color.parseColor("#FFFFFF"));         //Def Dialog Button2 background color
        if (isListDialog) {
            setRowColors("#FFFFFF", "#FFFFFF", "#00000000", "#212121");//Def List Dialog items colors(RowTitle,RowSummeryMsg,ImageBackgroundColor,RowBackgroundColor)
        } else {
            withMessageColor("#FFFFFF");                            //Def Simple Dialog message color
        }
        return this;
    }

    /**
     * Set a light theme by defalut colors.
     */

    public DialogBuilder setThemeLight() {
        return setThemeLight(false);
    }

    /**
     * Set a light theme by defalut colors.
     *
     * @param isListDialog pass true if dialog hava a listview
     */

    public DialogBuilder setThemeLight(boolean isListDialog) {
        withTitleColor("#000000");                                  //Def Title text color
        withDividerColor("#9E9E9E");                                //Def Divider color
        withDialogColor("#FFFFFF");                                 //Def Dialog background color
        mButton1.setTextColor(Color.parseColor("#000000"));         //Def Dialog Button1 text color
        mButton1.setBackgroundColor(Color.parseColor("#9E9E9E"));         //Def Dialog Button1 background color
        mButton2.setTextColor(Color.parseColor("#000000"));         //Def Dialog Button2 text color
        mButton2.setBackgroundColor(Color.parseColor("#9E9E9E"));         //Def Dialog Button1 background color
        if (isListDialog) {
            setRowColors("#212121", "#212121", "#00000000", "#FFFFFF");//Def List Dialog items colors(RowTitle,RowSummeryMsg,ImageBackgroundColor,RowBackgroundColor)
        } else {
            withMessageColor("#000000");                            //Def Simple Dialog message color
        }
        return this;
    }

    /**
     * Set theems by this method by passing default type in it.
     *
     * @param themeType pass already defined types.
     */


    public DialogBuilder setTheme(ThemeType themeType) {
        return setTheme(themeType, false);
    }

    public DialogBuilder setTheme(ThemeType themeType, boolean isListDialog) {
        if (themeType.equals(ThemeType.LIGHT)) {
            setThemeLight(isListDialog);
        } else if (themeType.equals(ThemeType.DARK)) {
            setThemeDark(isListDialog);
        } else if (themeType.equals(ThemeType.DEFAULT)) {
            setDefaultTheme();
        } else if (themeType.equals(ThemeType.CUSTOM)) {
            //setThemeCustom();
        } else {
            setDefaultTheme();
        }
        return this;
    }

    private void resetTheme() {
        setThemeDefault(true);
    }

    public DialogBuilder withDividerColor(String colorString) {
        mDivider.setBackgroundColor(Color.parseColor(colorString));
        return this;
    }

    public DialogBuilder withDividerColor(int color) {
        mDivider.setBackgroundColor(color);
        return this;
    }


    public DialogBuilder withTitle(CharSequence title) {
        toggleView(mLinearLayoutTopView, title);
        mTitle.setText(title);
        return this;
    }

    public DialogBuilder withTitleColor(String colorString) {
        mTitle.setTextColor(Color.parseColor(colorString));
        return this;
    }

    public DialogBuilder withTitleColor(int color) {
        mTitle.setTextColor(color);
        return this;
    }

    public DialogBuilder withMessage(int textResId) {
        toggleView(mLinearLayoutMsgView, textResId);
        mMessage.setText(textResId);
        return this;
    }

    public DialogBuilder withMessage(CharSequence msg) {
        toggleView(mLinearLayoutMsgView, msg);
        mMessage.setText(msg);
        return this;
    }

    public DialogBuilder withMessageColor(String colorString) {
        if (mMessage != null)
            mMessage.setTextColor(Color.parseColor(colorString));
        return this;
    }

    public DialogBuilder withMessageColor(int color) {
        mMessage.setTextColor(color);
        return this;
    }

    public DialogBuilder withDialogColor(String colorString) {
        mLinearLayoutView.getBackground().setColorFilter(ColorUtils.getColorFilter(Color.parseColor(colorString)));
        return this;
    }

    public DialogBuilder withDialogColor(int color) {
        mLinearLayoutView.getBackground().setColorFilter(ColorUtils.getColorFilter(color));
        return this;
    }

    public DialogBuilder withIcon(int drawableResId) {
        mIcon.setImageResource(drawableResId);
        return this;
    }

    public DialogBuilder withIcon(Drawable icon) {
        mIcon.setImageDrawable(icon);
        return this;
    }

    public DialogBuilder withDuration(int duration) {
        this.mDuration = duration;
        return this;
    }

    public DialogBuilder withStartEffect(Effectstype type) {
        this.startType = type;
        return this;
    }

    public DialogBuilder withEndEffect(Effectstype type) {
        this.endType = type;
        return this;
    }

    public DialogBuilder withButtonDrawable(int resid) {
        mButton1.setBackgroundResource(resid);
        mButton2.setBackgroundResource(resid);
        return this;
    }

    public DialogBuilder withButton1Text(CharSequence text, String textColor) {
        mButton1.setTextColor(Color.parseColor(textColor));
        withButton1Text(text);
        return this;
    }

    public DialogBuilder withButton1Text(CharSequence text) {
        mButton1.setVisibility(View.VISIBLE);
        mButton1.setText(text);

        return this;
    }

    public DialogBuilder withButton2Text(CharSequence text, String textColor) {
        mButton2.setTextColor(Color.parseColor(textColor));
        withButton2Text(text);
        return this;
    }

    public DialogBuilder withButton2Text(CharSequence text) {
        mButton2.setVisibility(View.VISIBLE);
        mButton2.setText(text);
        return this;
    }

    public DialogBuilder setListItemClick(AdapterView.OnItemClickListener click) {
        listView.setOnItemClickListener(click);
        return this;
    }

    public DialogBuilder setButton1DefaultClick(final DialogBuilder dBuild) {
        if (dBuild != null) {
            mButton1.setVisibility(View.VISIBLE);
            mButton1.setText(tmpContext.getResources().getString(android.R.string.ok));
            mButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dBuild.dismiss();
                }
            });
        }
        return this;
    }

    public DialogBuilder setButton1Click(View.OnClickListener click) {
        mButton1.setOnClickListener(click);
        return this;
    }

    public DialogBuilder setButton2DefaultClick(final DialogBuilder dBuild) {
        if (dBuild != null) {
            mButton2.setVisibility(View.VISIBLE);
            mButton2.setText(tmpContext.getResources().getString(android.R.string.cancel));
            mButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dBuild.dismiss();
                }
            });
        }
        return this;
    }

    public DialogBuilder setButton2Click(View.OnClickListener click) {
        mButton2.setOnClickListener(click);
        return this;
    }


    public DialogBuilder setCustomView(int resId, Context context) {
        View customView = View.inflate(context, resId, null);
        if (mFrameLayoutCustomView.getChildCount() > 0) {
            mFrameLayoutCustomView.removeAllViews();
        }
        mFrameLayoutCustomView.addView(customView);
        return this;
    }

    public DialogBuilder setCustomView(View view, Context context) {
        if (mFrameLayoutCustomView.getChildCount() > 0) {
            mFrameLayoutCustomView.removeAllViews();
        }
        mFrameLayoutCustomView.addView(view);

        return this;
    }

    public DialogBuilder setCancelableOnTouchOutside(boolean cancelable) {
        return isCancelableOnTouchOutside(cancelable);
    }

    public DialogBuilder isCancelableOnTouchOutside(boolean cancelable) {
        this.isCancelable = cancelable;
        this.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    public DialogBuilder isCancelable(boolean cancelable) {
        this.isCancelable = cancelable;
        this.setCancelable(cancelable);
        return this;
    }

    private void toggleView(View view, Object obj) {
        if (obj == null) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void show() {
        super.show();
    }

    private void end(Effectstype type) {
      /*  BaseEffects animator = Effectstype.FadeOut.getAnimator();
                //type.getAnimator();
        if(mDuration != -1){
            animator.setDuration(Math.abs(mDuration));
        }
        animator.end(mRelativeLayoutView);*/
    }

    private void start(Effectstype type) {
        BaseEffects animator = type.getAnimator();
        if (mDuration != -1) {
            animator.setDuration(Math.abs(mDuration));
        }
        animator.start(mRelativeLayoutView);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mButton1.setVisibility(View.GONE);
        mButton2.setVisibility(View.GONE);
        //  end(Effectstype.Newspager);
        this.resetTheme();
    }

}
