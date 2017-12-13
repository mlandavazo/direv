package com.direv.direv.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by khinthein on 12/12/17.
 */

public class ImageManager {
    private static final String TAG = "ImageManager";

    public static Bitmap getBitMap(String imgUrl){
        File imageFile = new File(imgUrl);
        FileInputStream fis = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = null;
        try{
            fis = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(fis, null,options);
        }
        catch (FileNotFoundException e){
            Log.e(TAG, "getBitmap: FileNotFoundException" + e.getMessage());
        }
        finally {
            try {
                fis.close();
            } catch (IOException e) {
                Log.e(TAG, "getBitmap: FileNotFoundException" + e.getMessage());
            }
        }
        return bitmap;
    }

    /**
     *
     * return byte array from bitmap
     * 0<= quality <= 100
     * @param bm
     * @param quality
     * @return
     */
    public static byte[] getBytesFromBitmap(Bitmap bm, int quality){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        return stream.toByteArray();

    }
}
