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

    //デバック画像の作成
    public void testSaveBitmap() {
        Bitmap bitmap = BitmapUtil.createDebugData(mActivity.getResources(), R.drawable.sample);
        try {
            File file = BitmapUtil.save(bitmap);
            assertTrue(file.exists());
            Log.e("testSaveBitmap", file.getPath());
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }
}
