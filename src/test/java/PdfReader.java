import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;

/**
 * @author zhou.peng
 * @desc todo
 * @date 2021 11 03 12:04
 */
public class PdfReader {
    public static String getContentFromPdf(String pdfFilePath){
        String result = "";
        PDDocument document = null;
        RandomAccessRead randomAccessRead = null;
        try {
            randomAccessRead = new RandomAccessBufferedFileInputStream(pdfFilePath);
            PDFParser pdfParser = new PDFParser(randomAccessRead);
            pdfParser.parse();
            document = pdfParser.getPDDocument();
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            result = pdfTextStripper.getText(document);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                randomAccessRead.close();
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public static void main(String args[]) {
        String content = PdfReader.getContentFromPdf("D:\\TOO下机文件\\21.pdf");
        System.out.println("content:"+content);
    }

}
