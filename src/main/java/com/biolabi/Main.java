package com.biolabi;

import com.biolabi.files.ChrCache;
import com.biolabi.files.VCFFile;
import com.biolabi.files.VCFRow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Started....");
        VCFFile referenceFile = new VCFFile(
                new File("/home/georgi/Documents/LabResults/DNA/GCF_000001405.25"));
        VCFFile myFile = new VCFFile(
                new File("/home/georgi/Documents/LabResults/DNA/TSAB6967.filtered.snp.vcf"));
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-mm-dd");
        PrintStream out = new PrintStream(
                new FileOutputStream(
                        new File("results_j_"+sdFormat.format(new Date()))));
        long t0 = System.currentTimeMillis();
            myFile.forEachRow(row -> {
                VCFRow refRow = referenceFile.findARow(row.getChrom(),row.getPosition());
                if(refRow != null){
                    out.println("=================");
                    out.println(row.getRaw());
                    out.println("------------------");
                    out.println(refRow.getRaw());
                    out.println("=================");
                }
            });
        long t1 = System.currentTimeMillis();
        long t = t1-t0;
        System.out.println("Time: "+t);
        out.println("Time: "+t);
        out.close();

    }
}