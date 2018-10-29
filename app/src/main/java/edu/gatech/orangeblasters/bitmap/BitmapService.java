package edu.gatech.orangeblasters.bitmap;

import android.graphics.Bitmap;

import java.util.Optional;

public interface BitmapService {

    String addBitmap(Bitmap bitmap);
    Optional<Bitmap> getBitmap(String id);
}
