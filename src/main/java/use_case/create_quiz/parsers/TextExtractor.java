package use_case.create_quiz.parsers;
import java.io.IOException;

public class TextExtractor {
    public static String extractText(String filePath) throws IOException {
        if (filePath.endsWith(".pdf")) {
            return PDFParser.parsePDF(filePath);
        } else {
            throw new IllegalArgumentException("Unsupported file format");
        }
    }
}
