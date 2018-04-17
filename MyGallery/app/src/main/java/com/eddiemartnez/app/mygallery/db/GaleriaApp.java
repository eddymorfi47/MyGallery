package com.eddiemartnez.app.mygallery.db;

/**
 * Created by Eddie Martínez on 9/4/2018.
 */
import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by Brian on 3/26/18.
 */

public class GaleriaApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }
}