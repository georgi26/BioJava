package com.biolabi.files;

import java.io.*;

public class VCFFile {

    private File file;

    private ChrCache chrCache;

    public VCFFile(File file){
        this.file = file;
    }

    public void forEachRow(VCFRowHandler vcfRowHandler) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)),10000);
        String line;
        boolean started = false ;
        while((line = reader.readLine()) != null){
            if(!line.startsWith("#") && started){
                vcfRowHandler.onRow(new VCFRow(line));
            }else if(line.startsWith("#CHROM")){
                started = true;
            }
        }
        reader.close();
    }


    public VCFRow findARow(String chromosome,long position) throws IOException {
        ChrCache chrCache1 = getChrCacheFor(chromosome);
        Long filePosition = chrCache1.getCache().get(Long.valueOf(position));
        if(filePosition != null){
            return readRowAt(filePosition);
        }else {
            return null;
        }
    }

    public VCFRow readRowAt(long position) throws IOException {
        FileInputStream in = new FileInputStream(file);
        in.skip(position);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = reader.readLine();
        VCFRow result = new VCFRow(line);
        reader.close();
        return result;
    }

    public String getIndexDirName(){
        return ""+this.file.getParent()+"/"+this.file.getName()+"_biolabi";
    }

    public File getIndexFileFor(String chromosome){
        return new File(getIndexDirName()+"/"+chromosome+".idx");
    }

    public ChrCache getChrCacheFor(String chromosome) throws IOException {
        if(this.chrCache == null || !this.chrCache.getChromosome().equals(chromosome)){
            this.chrCache = new ChrCache(chromosome,getIndexFileFor(chromosome));
            System.out.println("Load "+this.chrCache.getFile().getName());
            this.chrCache.load();
            System.out.println("Loaded "+chromosome);
        }
        return  this.chrCache;
    }
}
