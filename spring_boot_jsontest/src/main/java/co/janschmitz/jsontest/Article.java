package co.janschmitz.jsontest;

public class Article {

    private String name;
    private Ean ean;

    public Article(String name, Ean ean) {
        this.name = name;
        this.ean = ean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ean getEan() {
        return ean;
    }

    public void setEan(Ean ean) {
        this.ean = ean;
    }
}
