package com.gitonway.lee.niftymodaldialogeffects;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import library.module.dialog.Effectstype;
import library.module.dialog.DialogBuilder;
import library.module.dialog.ListBuilder;


public class MainActivity extends Activity{

    private Effectstype effect;
    DialogBuilder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MainActivity","onCreate");
        DialogBuilder.setDefaultThemeData("#FF0000","#FF00FF","#FFFFFF","#000000","#FF0000","#FF0000","#99000000","#00FF00");
    }

    public void dialogShow(View v){

      //  DialogBuilder dialogBuilder= DialogBuilder.getInstance(this);

        switch (v.getId()){
            case R.id.fadein:effect=Effectstype.Fadein;break;
            case R.id.slideright:effect=Effectstype.Slideright;break;
            case R.id.slideleft:effect=Effectstype.Slideleft;break;
            case R.id.slidetop:effect=Effectstype.Slidetop;break;
            case R.id.slideBottom:effect=Effectstype.SlideBottom;break;
            case R.id.newspager:effect=Effectstype.Newspager;break;
            case R.id.fall:effect=Effectstype.Fall;break;
            case R.id.sidefall:effect=Effectstype.Sidefill;break;
            case R.id.fliph:effect=Effectstype.Fliph;break;
            case R.id.flipv:effect=Effectstype.Flipv;break;
            case R.id.rotatebottom:effect=Effectstype.RotateBottom;break;
            case R.id.rotateleft:effect=Effectstype.RotateLeft;break;
            case R.id.slit:effect=Effectstype.Slit;break;
            case R.id.shake:effect=Effectstype.Shake;break;
            case R.id.light:effect=Effectstype.Fadein;break;
            case R.id.custom:effect=Effectstype.Slidetop;break;
            case R.id.list_dialog:effect=Effectstype.SlideBottom;break;
        }

        //DialogBuilder.setThemeCustom("#FF0000","#FF00FF","#FFFFFF","#000000","#FF0000","#FF0000","#99000000","#00FF00");
        //DialogBuilder.setDefaultCustomDialog(this,"This is a default msg",false,"Alert").setThemeDark();
        if(v.getId()==R.id.list_dialog) {
            ListBuilder listBuilder = new ListBuilder(this,0);
            listBuilder.addItem("Title1","SubTitle1",R.drawable.icon);
            listBuilder.addItem("Title2","",R.drawable.ic_launcher);
            listBuilder.addItem("Title3","SubTitle3",0);
            listBuilder.addItem("","SubTitle4",R.drawable.icon);
            builder = listBuilder.build("title",true,effect,700,true,DialogBuilder.ThemeType.DARK);
            //builder.setDefaultCustomDialogList(builder,"Title",true,effect,Effectstype.Fadein,700,false);
            // builder.show();
            builder.setListItemClick(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(MainActivity.this,""+position,Toast.LENGTH_SHORT).show();
                    builder.dismiss();
                }
            });
        }else {
            builder = DialogBuilder.setDefaultCustomDialog(this, "This is a default msg", "Alert", true, effect, Effectstype.Fadein, 700, true, DialogBuilder.ThemeType.DARK);
            if (v.getId() == R.id.custom)
                builder.setThemeCustom("#FFFFFF", "#FF8954", "#FFFFFF", "#DA5F6A", "#FF0000", "#FF0000", "#99000000", "#00FF00");
            else if (v.getId() == R.id.light) { //DA5F6A
                builder.setThemeLight();
            }
        }

        //You can customize everything by this commented method.

/*
        dialogBuilder
                //.withTitle("Modal Dialog")                                  //.withTitle(null)  no title
                //.withTitleColor("#FFFFFF")                                  //def
                //.withDividerColor("#11000000")                              //def
                //.setThemeCustom("#FF0000","#000000","#000000","#00FFFF")
                //.setThemeLight()
                //.setThemeDark()
                .withMessage("This is a modal Dialog.")                     //.withMessage(null)  no Msg

                //.withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                //.withDialogColor("#FFE74C3C")                               //def  | withDialogColor(int resid)                               //def
                .withIcon(getResources().getDrawable(R.drawable.icon))
                .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                .withDuration(700)                                          //def
                .with Effect(effect)                                         //def Effectstype.Slidetop
                .withButton1Text("OK")                                      //def gone
                .withButton2Text("Cancel")                                  //def gone
                //.setCustomView(R.layout.custom_view,v.getContext())         //.setCustomView(View or ResId,context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "i'm btn1", Toast.LENGTH_SHORT).show();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "i'm btn2", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();*/

    }



    }
