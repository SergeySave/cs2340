package edu.gatech.orangeblasters.bitmap;

import android.graphics.Bitmap;

public interface BitmapService {

    String addBitmap(Bitmap bitmap);
    void getBitmap(String id, BitmapCallback callback);
}
