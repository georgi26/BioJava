package com.biolabi.files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AssemblyReport {

    private String filenName = "GRCh37.p13_assembly_report.txt";

    private Map<String, String> chromosomes_map;

    private static AssemblyReport assemblyReport;

    public AssemblyReport() throws IOException{
        chromosomes_map = new HashMap<String, String>();
        parseFile();
    }

    public static synchronized AssemblyReport getInstance() throws IOException {
        if(assemblyReport == null){
            assemblyReport = new AssemblyReport();
        }
        return assemblyReport;
    }

    private void parseFile() throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(filenName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = reader.readLine()) != null) {
            if (!line.startsWith("#")) {
                String[] cols = line.split("\t");
                if (cols.length > 6) {
                    chromosomes_map.put(cols[4].trim(), cols[2].trim());
                    chromosomes_map.put(cols[6].trim(), cols[2].trim());
                }
            }
        }
    }

    public String getChromosome(String alias) {
        return this.chromosomes_map.get(alias);
    }

    public Set<String> getAllChromosomes(){
        Set<String> keys = this.chromosomes_map.keySet();
        Set<String> result = new HashSet<String>();
        for (String key : keys){
            String value = chromosomes_map.get(key);
            if(!result.contains(value)){
                result.add(value);
            }
        }
        return result;
    }
}
