package com.biolabi.files;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VCFFile {

    private File file;

    private Map<String, Map<Long,VCFRow>> cache;
    public VCFFile(File file){
        this.file = file;
    }

    public void forEachRow(VCFRowHandler vcfRowHandler) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
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

    public void loadCache() throws IOException {
        cache = new HashMap<String, Map<Long,VCFRow>>();
        Set<String> chromosomes = AssemblyReport.getInstance().getAllChromosomes();
        for (String chrom : chromosomes){
            cache.put(chrom,new HashMap<Long,VCFRow>());
        }
        forEachRow(row -> cache.get(row.getChrom()).put(row.getPosition(),row));
    }

}
