import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    private HashMap<String, ArrayList<PageEntry>> keys;

    BooleanSearchEngine() {
        keys = new HashMap<>();
    }

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        File[] files = pdfsDir.listFiles();
        keys = new HashMap<>();
        int n = files.length;
        for (int i = 0; i < n; ++i) {
            PdfDocument doc = new PdfDocument(new PdfReader(files[i]));
            for (int j = 1; j < doc.getNumberOfPages() + 1; ++j) {
                String text = PdfTextExtractor.getTextFromPage(doc.getPage(j));
                String words[] = text.split("\\P{IsAlphabetic}+");

                Map<String, Integer> freqs = new HashMap<>(); // мапа, где ключом будет слово, а значением - частота
                for (String word : words) { // перебираем слова
                    if (word.equals("")) {
                        continue;
                    }
                    word = word.toLowerCase();
                    freqs.put(word, freqs.getOrDefault(word, 0) + 1);
                }

                for (String word : words) {
                    word = word.toLowerCase();

                    if (word.equals("")) continue;
                    if (keys.containsKey(word)) {
                        boolean isFirst = true;
                        for (int l = 0; l < keys.get(word).size(); ++l) {
                            if (keys.get(word).get(l).getPdfName().equals(files[i].getName()) && keys.get(word).get(l).getPage() == j) {
                                isFirst = false;
                            }
                        }
                        if (isFirst) {
                            keys.get(word).add(new PageEntry(files[i].getName(), j, freqs.get(word)));
                        }
                    } else {
                        ArrayList<PageEntry> values = new ArrayList<>();
                        values.add(new PageEntry(files[i].getName(), j, freqs.get(word)));
                        keys.put(word, values);
                    }
                }
            }
        }
    }

    public List<PageEntry> search(String word) {
        return keys.get(word);
    }
}