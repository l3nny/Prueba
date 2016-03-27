package grability.prueba.Reader;

public class Feader {
    String Name;
    String Category;
    String Summary;
    private String URLimage;
    public byte[] image;

    public Feader() {
    }

    public Feader(String name, String summary) {
        Name = name;
        Summary = summary;
    }

    public Feader(String category) {
        Category = category;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public String getURLimage() {
        return URLimage;
    }

    public void setURLimage(String URLimage) {
        this.URLimage = URLimage;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return Category;
    }
}
