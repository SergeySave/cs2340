package edu.gatech.orangeblasters.bitmap;

import android.graphics.Bitmap;

import java.util.Optional;

@FunctionalInterface
public interface BitmapCallback {
    void onComplete(Optional<Bitmap> result);
}
