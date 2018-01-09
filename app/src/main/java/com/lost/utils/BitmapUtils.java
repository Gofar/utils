package com.lost.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.support.annotation.ColorInt;

/**
 * @author lcf
 * @date 2017/11/30 10:04
 * @since 1.0
 */
public class BitmapUtils {

    /**
     * 创建倒影Bitmap
     *
     * @param source 源Bitmap
     * @param scale  源Bitmap与倒影的比例
     * @return target
     */
    public static Bitmap createInvertedBitmap(Bitmap source, int scale) {
        int width = source.getWidth();
        int height = source.getHeight();
        int targetHeight = height / scale;
        Matrix matrix = new Matrix();
        matrix.setScale(1, -1);

        Bitmap target = Bitmap.createBitmap(source, 0, height - targetHeight, width, targetHeight, matrix, false);
        Canvas canvas = new Canvas(target);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        LinearGradient shader = new LinearGradient(0, 0, 0, targetHeight, 0x99ffffff, 0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(0, 0, width, targetHeight, paint);
        return target;
    }


    /**
     * 根据宽、高、颜色创建Bitmap
     *
     * @param width  Bitmap的宽
     * @param height Bitmap的高
     * @param color  Bitmap的颜色
     * @return Bitmap
     */
    public static Bitmap createColorBitmap(int width, int height, @ColorInt int color) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(color);
        return bitmap;
    }
}
