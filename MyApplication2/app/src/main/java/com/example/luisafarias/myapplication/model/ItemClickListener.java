package com.example.luisafarias.myapplication.model;

import android.view.View;

/**
 * Created by luisafarias on 16/10/17.
 */

public interface ItemClickListener {
    void onClick(View view, int position,boolean isLongClick);
}
