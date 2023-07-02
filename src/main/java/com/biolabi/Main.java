package com.biolabi;

import com.biolabi.files.ChrCache;

import java.io.File;
import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("/home/georgi/Documents/LabResults/DNA/GCF_000001405.25_biolabi/1.idx");
        ChrCache cache = new ChrCache("1",file);
        long t0 = System.currentTimeMillis();
        cache.load();
        long t1 = System.currentTimeMillis();
        System.out.println("Time: "+((t1-t0)/1000.0));
        System.out.println("Size: "+cache.getCache().size());
    }
}