package dept.app.models;

public class MaterialLog {
    private String id;
    private String dateadd;
    private int amount;

    public MaterialLog(String id, String dateadd, int amount){
        this.id = id;
        this.dateadd = dateadd;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getDateadd() {return dateadd;}

    public int getAmount() {return amount;}

    public boolean isId(String id) {
        return this.id.equals(id);
    }

    @Override
    public String toString() {
        return "MaterialLog{" +
                "id='" + id + '\'' +
                ", dateadd='" + dateadd + '\'' +
                ", amount=" + amount +
                '}';
    }
}
