package com.example.webappinitializer.util;

public class PrettierConfiguration extends ModuleConfiguration {
    private boolean semi;
    private boolean singleQuote;
    private boolean bracketSpacing;
    private String trailingComma;
    private int tabWidth;
    private int printWidth;
    private String endOfLine;

    public void printPreview() {
        String preview = "{\n" +
                "  semi: " + semi + ",\n" +
                "  singleQuote: " + singleQuote + ",\n" +
                "  trailingComma: \"" + trailingComma + "\",\n" +
                "  tabWidth: " + tabWidth + ",\n" +
                "  printWidth: " + printWidth + ",\n" +
                "  bracketSpacing: " + bracketSpacing + ",\n" +
                "  endOfLine: \"" + endOfLine + "\",\n" +
                "};";
        System.out.println(preview);
    }
}
