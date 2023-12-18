package com.fianlandroidassignments.xuancuongstationery.Common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class Common {
    public static Bitmap getBitmapFromByteArray(byte[] bytes){
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,
                0,bytes.length);
        return bitmap;
    }

    public static byte[] convertImageViewToByteArray(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, outputStream);
        return outputStream.toByteArray();
    }
}
