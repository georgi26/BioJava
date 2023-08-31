import com.biolabi.files.AssemblyReport;
import com.biolabi.files.ChrCache;
import com.biolabi.files.VCFFile;
import com.biolabi.files.VCFRow;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VCFRowTest {

    @Test
    public void testRowParse() throws IOException {
        VCFRow vcfRow = new VCFRow("NC_000001.10    10001   rs1570391677    T       A,C     .       .       RS=1570391677;dbSNPBuildID=154;SSR=0;PSEUDOGENEINFO=DDX11L1:100287102;VC=SNV;R5;GNO;\n" +
                "FREQ=KOREAN:0.9891,0.0109,.|SGDP_PRJ:0,1,.|dbGaP_PopFreq:1,.,0;COMMON\n");

        assertEquals("1",vcfRow.getChrom());
        assertEquals(10001, vcfRow.getPosition());

    }

    @Test
    public void testAssemblyReport() throws IOException {
        AssemblyReport assemblyReport = new AssemblyReport();
        assertEquals("1",assemblyReport.getChromosome("NC_000001.10"));
        assertEquals("11",assemblyReport.getChromosome("GL000202.1"));
        assertEquals("1",assemblyReport.getChromosome("NW_004070863.1"));
        assertEquals("MT",assemblyReport.getChromosome("NC_012920.1"));
    }

    @Test
    public void testFileParse() throws IOException, URISyntaxException {
        File file = new File("./test_data/test.vcf");
        VCFFile vcfFile = new VCFFile(file);
        List<VCFRow> rows = new ArrayList<VCFRow>(30);
        vcfFile.forEachRow( row -> rows.add(row));
        assertEquals(30,rows.size());
        VCFRow row = vcfFile.findARow("1",10001);
        assertNotNull(row);
    }

    @Test
    public void testWriteLong(){
        ChrCache cache = new ChrCache("1",new File(""));
        byte[] result = cache.writeLong(10001);
        assertEquals(17,result[0]);
    }

    @Test
    public void testReadLong() throws URISyntaxException{

        ChrCache cache = new ChrCache("1",new File(""));
        byte [] longBytes = {17,39,0,0,0,0,0,0};
        long result = cache.readLong(longBytes);
        assertEquals(10001,result);
    }
    @Test
    public void testChrCache() throws IOException, URISyntaxException {
        File file = new File(getClass().getClassLoader().getResource("1.idx").toURI());
        ChrCache cache = new ChrCache("1",file);
        cache.load();
        assertEquals(5826,cache.getCache().get(Long.valueOf(10001)));
    }

}
