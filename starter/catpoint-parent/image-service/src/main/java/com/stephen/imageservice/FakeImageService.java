package com.stephen.imageservice;

import java.awt.image.BufferedImage;
import java.util.Random;

public class FakeImageService implements ImageService{
    private final Random r = new Random();

    @Override
    public boolean imageContainsCat(BufferedImage image, float confidenceThreshold) {
        return r.nextBoolean();
    }
}
