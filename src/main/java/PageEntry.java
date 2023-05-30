public class PageEntry implements Comparable<PageEntry> {
    private final String pdfName;
    private final int page;
    private final int count;

    PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public int getPage() {
        return page;
    }

    public String getPdfName() {
        return pdfName;
    }

    public int compareTo(PageEntry o) {
        return count - o.getCount();
    }

    @Override
    public String toString() {
        return ' ' + "{" + '\n' +
                "  " + "pdfName=" + pdfName + ", " + '\n' +
                "  " + "page=" + String.valueOf(page) + ", " + '\n' +
                "  " + "count=" + count + '\n' +
                ' ' + "}," + '\n';
    }

}