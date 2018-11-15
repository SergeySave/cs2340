package edu.gatech.orangeblasters.bitmap;

import android.graphics.Bitmap;

import java.util.Optional;

/**
 * Represents a callback for bitmap
 */
@FunctionalInterface
public interface BitmapCallback {
    /**
     * Called when an bitmap is returned
     *
     * @param result the returned bitmap
     */
    void onComplete(Optional<Bitmap> result); //Optional makes it explicit when we return if
    // we have a negative result
}
