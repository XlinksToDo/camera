package com.hosshan.android.camerawallpaper;

import android.content.ContextWrapper;
import android.hardware.Camera;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

public class CameraWallpaper extends WallpaperService {
    public static final String TAG = CameraWallpaper.class.getSimpleName();

    public CameraEngine.CameraCallback mCameraCallback;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Engine onCreateEngine() {
        return new CameraEngine();
    }

    // Live Wallpaperの描画などを個なうクラス
    class CameraEngine extends Engine {

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            mCameraCallback = new CameraCallback(CameraWallpaper.this);
            holder.addCallback(mCameraCallback);

        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
        }

        private class CameraCallback implements SurfaceHolder.Callback {
            ContextWrapper mContext;
            private Camera mCamera;

            private CameraCallback(ContextWrapper context) {
                mContext = context;
            }

            // 画面が開いた時に呼ばれるメソッド
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                openCamera(holder);
            }

            // 画面が破棄されるときに呼ばれるメソッド
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                closeCamera();
            }

            // 画面が回転した時などに呼ばれるメソッド
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                changeCameraState(width, height);
            }

            private void openCamera(SurfaceHolder holder) {
                // カメラインスタンスを取得
                mCamera = Camera.open();
                try {
                    mCamera.setPreviewDisplay(holder);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void closeCamera() {
                // カメラインスタンス開放
                mCamera.release();
                mCamera = null;
            }

            private void changeCameraState(int width, int height) {
                Log.d(TAG, "surfaceChanged width:" + width + " height:" + height);

                Camera.Parameters parameters = mCamera.getParameters();

                // デバッグ用表示
                Camera.Size size = parameters.getPictureSize();
                Log.d(TAG, "getPictureSize width:" + size.width + " size.height:" + size.height);
                size = parameters.getPreviewSize();
                Log.d(TAG, "getPreviewSize width:" + size.width + " size.height:" + size.height);

                // プレビューのサイズを変更
                parameters.setPreviewSize(size.width, size.height);


                // 縦画面の場合回転させる
                if (width < height) {
                    mCamera.setDisplayOrientation(90);
                }else{
                    mCamera.setDisplayOrientation(0);
                }

                // パラメーターセット
                mCamera.setParameters(parameters);
                // プレビュー開始
                mCamera.startPreview();


            }

        }
    }
}
