package dept.app.models;

public class Material extends Object{
    private int remainder;

    public Material(String category, String name, int remainder, String id) {
        super(category, name, id);
        this.remainder = remainder;
    }

    public int getRemainder() {
        return remainder;
    }

    @Override
    public String toString() {
        return "Material{" +
                "remainder=" + remainder +
                '}';
    }

    public void addAmount(int remainder) {
        if (remainder > 0) {
            this.remainder += remainder;
        }
    }
    public void decreaseAmount(int remainder) {
        if (remainder > 0) {
            this.remainder -= remainder;
        }
    }
}
