package com.hosshan.android.camerawallpaper;

import android.Manifest;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.service.wallpaper.WallpaperService;
import android.view.View;
import android.widget.Button;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by oshiro_000 on 2018/02/25.
 */
@RuntimePermissions
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.sub);

//        final Button button = (Button) findViewById (R.id.b_start);
//        if (button != null) {
//            button.setOnClickListener (new View.OnClickListener () {
//
//                public void onClick(View paramView) {
//                    Intent i = new Intent (MainActivity.this, CameraWallpaper.class);
//                    startService (i);
//                }
//            });
//        }

    }
    @NeedsPermission (Manifest.permission.CAMERA)
    public void b_start(View v){
        Intent intent = new Intent (MainActivity.this,CameraWallpaper.class);
        startService (intent);
//        final Button button = (Button) findViewById (R.id.b_start);

    }
}
