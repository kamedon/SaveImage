package com.example.h_kamei.saveimage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.example.h_kamei.saveimage.util.BitmapUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by h_kamei on 2015/01/29.
 */
public class SaveTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private final int count = 100;
    private Activity mActivity;

    public SaveTest() {
        super(MainActivity.class);
    }

    public SaveTest(Class<MainActivity> activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
    }


    //デバック画像の作成
    public void testCreateBitmap() {
        Bitmap bitmap = BitmapUtil.createDebugData(mActivity.getResources(), R.drawable.sample);
        assertTrue(bitmap != null);
        assertTrue(bitmap.getWidth() == 1000);
        assertTrue(bitmap.getHeight() == 800);
        bitmap.recycle();
    }

    public void testContentResolver() {
        createSaveTestCase(count, "ContentResolver", new OnSaveListener() {
            @Override
            public void onPre() {
            }

            @Override
            public void onSave(File file) {
                Log.v("kkk", "+++++ContentResolver" + file.getPath());
                BitmapUtil.contentResolver(mActivity.getContentResolver(), file);
            }

            @Override
            public void onFail(Exception e) {
            }

            @Override
            public void onFinally() {
            }
        });

    }

    public void testMediaScannerConnection() {
        createSaveTestCase(count, "MediaScannerConnection", new OnSaveListener() {
            @Override
            public void onPre() {

            }

            @Override
            public void onSave(File file) {
                Log.v("kkk", "----MediaScannerConnection" + file.getPath());
                BitmapUtil.mediaScannerConnection(mActivity, file);
            }

            @Override
            public void onFail(Exception e) {

            }

            @Override
            public void onFinally() {

            }
        });

    }
    //デバック画像の保存
//    public void testSendBroadcast() {
//        createSaveTestCase(1, "SendBroadcast", new OnSaveListener() {
//            @Override
//            public void onPre() {
//
//            }
//
//            @Override
//            public void onSave(File file) {
//                Log.v("kkk","----sendBroadcast"+file.getPath());
//                BitmapUtil.sendBroadcast(mActivity, file);
//            }
//
//            @Override
//            public void onFail(Exception e) {
//
//            }
//
//            @Override
//            public void onFinally() {
//
//            }
//        });
//    }

    public interface OnSaveListener {
        public void onPre();

        public void onSave(File file);

        public void onFail(Exception e);

        public void onFinally();
    }

    public void createSaveTestCase(int count, String text, OnSaveListener listener) {
        for (int i = 0; i < count; i++) {
            listener.onPre();
            Bitmap bitmap = BitmapUtil.createDebugData(mActivity.getResources(), R.drawable.sample, text + "[" + (i + 1) + "]");
            try {
                File file = BitmapUtil.save(bitmap);
                listener.onSave(file);
            } catch (IOException e) {
                e.printStackTrace();
                listener.onFail(e);
                fail();
            } finally {
                bitmap.recycle();
                listener.onFinally();
            }
        }
    }


}
