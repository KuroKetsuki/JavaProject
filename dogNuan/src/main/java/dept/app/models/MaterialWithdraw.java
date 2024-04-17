package dept.app.models;

public class MaterialWithdraw {
    String id;
    String name;
    String requisitiondate;
    int amount;

    public MaterialWithdraw(String id, String name, String requisitiondate, int amount){
        this.id = id;
        this.name = name;
        this.requisitiondate = requisitiondate;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getName() {return name;}

    public String getRequisitiondate() {return requisitiondate;}

    public int getAmount() {return amount;}

    public boolean isId(String id) {
        return this.id.equals(id);
    }

    @Override
    public String toString() {
        return "MaterialWithdraw{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", requisitiondate='" + requisitiondate + '\'' +
                ", amount=" + amount +
                '}';
    }
}
