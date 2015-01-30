package com.example.h_kamei.saveimage.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by h_kamei on 2015/01/29.
 */
public class BitmapUtil {

    public static Bitmap createDebugData(Resources resources, int resourceId) {
        return createDebugData(resources, resourceId, "");
    }


    public static Bitmap createDebugData(Resources resources, int resourceId, String text) {
        Bitmap sampleImage = BitmapFactory.decodeResource(resources, resourceId);
        Bitmap bitmap = Bitmap.createBitmap(sampleImage.getWidth(), sampleImage.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(sampleImage, 0, 0, new Paint());

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String dateText = sdf1.format(new Date());

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(50);
        textPaint.setColor(Color.WHITE);

        canvas.drawText(text, bitmap.getWidth() / 20, bitmap.getHeight()  / 2, textPaint);

        canvas.drawText(dateText, bitmap.getWidth() / 20, bitmap.getHeight() * 19 / 20, textPaint);
        return bitmap;
    }

    public static File save(Bitmap bitmap) throws IOException {
        try {
            String dirPath = Environment.getExternalStorageDirectory().toString() + "/test/";
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 日付でファイル名を作成　
            Date mDate = new Date();
            SimpleDateFormat fileName = new SimpleDateFormat("yyyyMMdd_HHmmss");

            // 保存処理開始
            FileOutputStream fos = null;
            File file = new File(dir, fileName.format(mDate) + ".jpg");
            fos = new FileOutputStream(file);

            // jpegで保存
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            // 保存処理終了
            fos.close();
            return file;
        } catch (Exception e) {
            Log.e("Error", "" + e.toString());
            throw e;
        }

    }

    public static void sendBroadcast(Activity activity, File file) {
        Uri uri = Uri.fromFile(file);
        activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, uri));
    }

    public static void mediaScannerConnection(Activity activity, File file) {
        MediaScannerConnection.scanFile( // API Level 8
                activity, // Context
                new String[]{file.getPath()},
                new String[]{"image/jpeg"},
                null);


    }

    public static void contentResolver(ContentResolver contentResolver, File file) {
        if (file.exists()) {
            Uri uri = Uri.fromFile(file);

            ContentValues values = new ContentValues(7);
            values.put(MediaStore.Images.Media.TITLE, file.getName());
            values.put(MediaStore.Images.Media.DISPLAY_NAME, file.getPath());
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.ORIENTATION, 0);
            values.put(MediaStore.Images.Media.DATA, file.getPath());
            values.put(MediaStore.Images.Media.SIZE, file.length());
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
    }

}
