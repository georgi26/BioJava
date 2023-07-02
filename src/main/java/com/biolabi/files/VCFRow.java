package com.biolabi.files;


import java.io.IOException;

public class VCFRow {

    private static final String NOT_NUMERIC_CHROM_NAME = "NC_";

    private String raw;
    private String[] rawTockens;

    private String chrom;


    private long position;

    private String id;

    public VCFRow(String raw) throws IOException {
        this.raw = raw;
        this.parse();
    }

    private void parse() throws IOException {
        this.rawTockens = raw.split("\\s+");
        this.chrom = this.parseChrom(this.rawTockens[0]);
        this.position = Long.parseLong(this.rawTockens[1]);
        this.id = this.rawTockens[2];
    }

    private String parseChrom(String input) throws IOException {
        return AssemblyReport.getInstance().getChromosome(input);
    }

    public String getChrom() {
        return chrom;
    }

    public long getPosition() {
        return position;
    }

}
