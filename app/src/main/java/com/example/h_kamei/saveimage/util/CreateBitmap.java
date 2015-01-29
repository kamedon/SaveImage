package com.example.h_kamei.saveimage.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by h_kamei on 2015/01/29.
 */
public class CreateBitmap {

    public static Bitmap createDebugData(Resources resources, int resourceId) {
        Bitmap sampleImage = BitmapFactory.decodeResource(resources, resourceId);
        Bitmap bitmap = Bitmap.createBitmap(sampleImage.getWidth(), sampleImage.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(sampleImage, 0, 0, new Paint());

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String dateText = sdf1.format(new Date());

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(50);
        textPaint.setColor(Color.WHITE);
        canvas.drawText(dateText, bitmap.getWidth() / 20, bitmap.getHeight() * 19 / 20, textPaint);
        return bitmap;
    }
}
