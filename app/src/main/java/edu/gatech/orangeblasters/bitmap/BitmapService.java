package edu.gatech.orangeblasters.bitmap;

import android.graphics.Bitmap;

/**
 * Abstracts a storage method for bitmaps
 */
public interface BitmapService {

    /**
     * Add a new bitmap
     *
     * @param bitmap the bitmap to add
     * @return the id of the bitmap
     */
    String addBitmap(Bitmap bitmap);

    /**
     * Asynchronously get a bitmap
     *
     * @param id the bitmap's id
     * @param callback a callback for the returned bitmap
     */
    void getBitmap(String id, BitmapCallback callback);
}
