package edu.gatech.orangeblasters.bitmap.impl;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import edu.gatech.orangeblasters.bitmap.BitmapService;

public class BitmapInMemoryService implements BitmapService {

    private Map<String, Bitmap> bitmaps = new HashMap<>();
    private Random random = new Random();

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
    public Optional<Bitmap> getBitmap(String id) {
        return Optional.ofNullable(bitmaps.get(id));
    }
}
