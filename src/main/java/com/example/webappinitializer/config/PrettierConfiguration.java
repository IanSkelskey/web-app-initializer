package com.example.webappinitializer.config;

import com.google.gson.JsonObject;

/**
 * This class represents the Prettier configuration.
 */
public class PrettierConfiguration extends ModuleConfiguration {
    private boolean semi;
    private boolean singleQuote;
    private boolean bracketSpacing;
    private String trailingComma;
    private int tabWidth;
    private int printWidth;
    private String endOfLine;

    /**
     * This is the default constructor.
     */
    public PrettierConfiguration() {
        this.semi = true;
        this.singleQuote = true;
        this.bracketSpacing = true;
        this.trailingComma = "es5";
        this.tabWidth = 4;
        this.printWidth = 120;
        this.endOfLine = "lf";
    }

    /**
     * This is the constructor for the Prettier configuration.
     *
     * @param semi            The semi flag.
     * @param singleQuote     The single quote flag.
     * @param bracketSpacing  The bracket spacing flag.
     * @param trailingComma   The trailing comma flag.
     * @param tabWidth        The tab width.
     * @param printWidth      The print width.
     * @param endOfLine       The end of line.
     */
    public PrettierConfiguration(boolean semi, boolean singleQuote, boolean bracketSpacing, String trailingComma, int tabWidth, int printWidth, String endOfLine) {
        this.semi = semi;
        this.singleQuote = singleQuote;
        this.bracketSpacing = bracketSpacing;
        this.trailingComma = trailingComma;
        this.tabWidth = tabWidth;
        this.printWidth = printWidth;
        this.endOfLine = endOfLine;
    }

    /**
     * @return The semi flag.
     */
    public boolean isSemi() {
        return semi;
    }

    /**
     * @param semi The semi flag.
     */
    public void setSemi(boolean semi) {
        this.semi = semi;
    }

    /**
     * @return The single quote flag.
     */
    public boolean isSingleQuote() {
        return singleQuote;
    }

    /**
     * @param singleQuote The single quote flag.
     */
    public void setSingleQuote(boolean singleQuote) {
        this.singleQuote = singleQuote;
    }

    /**
     * @return The bracket spacing flag.
     */
    public boolean isBracketSpacing() {
        return bracketSpacing;
    }

    /**
     * @param bracketSpacing The bracket spacing flag.
     */
    public void setBracketSpacing(boolean bracketSpacing) {
        this.bracketSpacing = bracketSpacing;
    }

    /**
     * @return The trailing comma flag.
     */
    public String getTrailingComma() {
        return trailingComma;
    }

    /**
     * @param trailingComma The trailing comma flag.
     */
    public void setTrailingComma(String trailingComma) {
        this.trailingComma = trailingComma;
    }

    /**
     * @return The tab width.
     */
    public int getTabWidth() {
        return tabWidth;
    }

    /**
     * @param tabWidth The tab width.
     */
    public void setTabWidth(int tabWidth) {
        this.tabWidth = tabWidth;
    }

    /**
     * @return The print width.
     */
    public int getPrintWidth() {
        return printWidth;
    }

    /**
     * @param printWidth The print width.
     */
    public void setPrintWidth(int printWidth) {
        this.printWidth = printWidth;
    }

    /**
     * @return The end of line.
     */
    public String getEndOfLine() {
        return endOfLine;
    }

    /**
     * @param endOfLine The end of line.
     */
    public void setEndOfLine(String endOfLine) {
        this.endOfLine = endOfLine;
    }

    @Override
    public String toString() {
        return toJson().toString();
    }

    /**
     * @return The JSON representation of the Prettier configuration.
     */
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("semi", semi);
        jsonObject.addProperty("singleQuote", singleQuote);
        jsonObject.addProperty("bracketSpacing", bracketSpacing);
        jsonObject.addProperty("trailingComma", trailingComma);
        jsonObject.addProperty("tabWidth", tabWidth);
        jsonObject.addProperty("printWidth", printWidth);
        jsonObject.addProperty("endOfLine", endOfLine);
        return jsonObject;
    }
}
