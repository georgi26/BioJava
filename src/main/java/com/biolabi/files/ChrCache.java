package com.biolabi.files;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ChrCache {

    private static int BUFFER_SIZE=1000000;
    private static int BLOCK_SIZE=17;
    private String chromosome;
    private File file;

    private Map<Long,Long> cache;

    public ChrCache(String chromosome,File file){
        this.chromosome = chromosome;
        this.file = file;
        cache = new HashMap<>();
    }

    public void load() throws IOException {
        cache = new HashMap<>();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(this.file));
        int maxBufferSize = BUFFER_SIZE*BLOCK_SIZE;
        byte [] buffer = new byte[maxBufferSize];
        int readSize = 0;
        int offset = 0;
        while((readSize = bufferedInputStream.read(buffer,offset,buffer.length))>0){
            int currentBlockStart = 0 ;
            while(currentBlockStart < readSize) {
                byte[] firstLongByte =  Arrays.copyOfRange(buffer,currentBlockStart,currentBlockStart+Long.BYTES);
                byte[] seconndLongByte = Arrays.copyOfRange(buffer,currentBlockStart+Long.BYTES,
                        currentBlockStart+Long.BYTES+Long.BYTES);
                long firstLong = readLong(firstLongByte);
                long secondLong = readLong(seconndLongByte);
                cache.put(firstLong,secondLong);
                currentBlockStart = currentBlockStart + BLOCK_SIZE;
            }
        }
    }

    public long readLong(byte [] data){
        data = reverseByteArray(data);
        long result = 0;
        for (int i = 0; i < Long.BYTES; i++) {
            result <<= Byte.SIZE;
            result |= (data[i] & 0xFF);
        }

        return result;
    }

    public byte [] writeLing(long input){
        byte[] result = new byte[Long.BYTES];
        for (int i = Long.BYTES - 1; i >= 0; i--) {
            result[i] = (byte)(input & 0xFF);
            input >>= Byte.SIZE;
        }

        return reverseByteArray(result);
    }

    public byte [] reverseByteArray(byte [] input){
        byte [] result = new byte[input.length];
        int reverseIndex = result.length-1;
        for(int i = 0;i<input.length;i++){
            result[reverseIndex-i]=input[i];
        }
        return result;
    }

    public String getChromosome() {
        return chromosome;
    }

    public Map<Long, Long> getCache() {
        return cache;
    }
}
