package parsers;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileInputStream;
import java.io.IOException;

public class WordParser {
    public static String parseDocx(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             XWPFDocument docx = new XWPFDocument(fis)) {
            StringBuilder text = new StringBuilder();
            docx.getParagraphs().forEach(p -> text.append(p.getText()).append("\n"));
            return text.toString();
        }
    }
}
