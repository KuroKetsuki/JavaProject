package dept.app.models;

public class Object {
    private String category;
    private String id;
    private String name;

    public Object (String category, String name, String id) {
        this.category = category;
        this.name = name;
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCategory(String category) {
        return this.category.equals(category);
    }

    public boolean isId(String id) {
        return this.id.equals(id);
    }

    public boolean isName(String name) {return this.name.equals(name);}

    @Override
    public String toString() {
        return "Object{" +
                "category='" + category + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
