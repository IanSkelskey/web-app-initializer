package com.example.webappinitializer.util;

public class PrettierConfiguration extends ModuleConfiguration {
    private boolean semi;
    private boolean singleQuote;
    private boolean bracketSpacing;
    private String trailingComma;
    private int tabWidth;
    private int printWidth;
    private String endOfLine;

    public boolean isSemi() {
        return semi;
    }

    public void setSemi(boolean semi) {
        this.semi = semi;
    }

    public boolean isSingleQuote() {
        return singleQuote;
    }

    public void setSingleQuote(boolean singleQuote) {
        this.singleQuote = singleQuote;
    }

    public boolean isBracketSpacing() {
        return bracketSpacing;
    }

    public void setBracketSpacing(boolean bracketSpacing) {
        this.bracketSpacing = bracketSpacing;
    }

    public String getTrailingComma() {
        return trailingComma;
    }

    public void setTrailingComma(String trailingComma) {
        this.trailingComma = trailingComma;
    }

    public int getTabWidth() {
        return tabWidth;
    }

    public void setTabWidth(int tabWidth) {
        this.tabWidth = tabWidth;
    }

    public int getPrintWidth() {
        return printWidth;
    }

    public void setPrintWidth(int printWidth) {
        this.printWidth = printWidth;
    }

    public String getEndOfLine() {
        return endOfLine;
    }

    public void setEndOfLine(String endOfLine) {
        this.endOfLine = endOfLine;
    }

    @Override
    public String toString() {
        return "PrettierConfiguration{" +
                "semi=" + semi +
                ", singleQuote=" + singleQuote +
                ", bracketSpacing=" + bracketSpacing +
                ", trailingComma='" + trailingComma + '\'' +
                ", tabWidth=" + tabWidth +
                ", printWidth=" + printWidth +
                ", endOfLine='" + endOfLine + '\'' +
                '}';
    }
}