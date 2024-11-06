package file_parser;
import java.io.IOException;

public class TextExtractor {
    public static String extractText(String filePath) throws IOException {
        if (filePath.endsWith(".pdf")) {
            return PDFParser.parsePDF(filePath);
        } else if (filePath.endsWith(".docx")) {
            return WordParser.parseDocx(filePath);
        } else {
            throw new IllegalArgumentException("Unsupported file format");
        }
    }
}
