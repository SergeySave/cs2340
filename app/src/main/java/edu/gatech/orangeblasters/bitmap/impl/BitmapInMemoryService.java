package edu.gatech.orangeblasters.bitmap.impl;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import edu.gatech.orangeblasters.bitmap.BitmapCallback;
import edu.gatech.orangeblasters.bitmap.BitmapService;

@Deprecated
public class BitmapInMemoryService implements BitmapService {

    private final Map<String, Bitmap> bitmaps = new HashMap<>();
    private final Random random = new Random();

    /**
     * Creates the id
     *
     * @return a string of the id
     */
    private String createId() {
        return random.ints(4).mapToObj(Integer::toHexString).collect(Collectors.joining());
    }

    @Override
    public String addBitmap(Bitmap bitmap) {
        String id = createId();
        bitmaps.put(id, bitmap);
        return id;
    }

    @Override
    public void getBitmap(String id, BitmapCallback callback) {
        callback.onComplete(Optional.ofNullable(bitmaps.get(id)));
    }
}
