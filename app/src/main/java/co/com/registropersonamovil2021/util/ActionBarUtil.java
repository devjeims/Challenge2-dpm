package co.com.registropersonamovil2021.util;

import androidx.appcompat.app.AppCompatActivity;

public class ActionBarUtil {
    AppCompatActivity appCompatActivity;
    boolean arrowEnable;

    public static  ActionBarUtil getInstance(AppCompatActivity appCompatActivity,  boolean arrowEnable){
           ActionBarUtil actionBarUtil = new ActionBarUtil();
           actionBarUtil.appCompatActivity = appCompatActivity;
           actionBarUtil.arrowEnable = arrowEnable;
           return actionBarUtil;
    }

    public void setToolBar(String titulo) {
        if (appCompatActivity.getSupportActionBar() != null) {
            setTitutulo2ActionBar(titulo);
        }
    }

    public void setToolBar(String titilo, String subtitle) {
        subtitle = subtitle == null?"":subtitle;
        if (appCompatActivity.getSupportActionBar() != null) {
            setTitutulo2ActionBar(titilo);
            appCompatActivity.getSupportActionBar().setSubtitle(subtitle);
        }
    }

    private void setTitutulo2ActionBar(String titulo) {
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(arrowEnable);
        appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(arrowEnable);
        appCompatActivity.getSupportActionBar().setTitle(titulo);

    }


}
