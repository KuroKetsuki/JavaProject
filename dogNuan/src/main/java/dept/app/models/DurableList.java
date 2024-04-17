package dept.app.models;

import javafx.scene.image.Image;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class DurableList {
    private ArrayList<Durable> durables;

    public DurableList() {
        durables = new ArrayList<>();
    }


    public Durable findDurableById(String id) {
        for (Durable durable : durables) {
            if (durable.isId(id)) {
                return durable;
            }
        }
        return null;
    }
    public Durable findDurableByName(String name) {
        for (Durable durable : durables) {
            if (durable.isName(name)) {
                return durable;
            }
        }
        return null;
    }

    public void borrowToId(String id, String borrower) {
        Durable exist = findDurableById(id);
        if (exist != null) {
            exist.addBorrower(borrower);
        }
    }

    public ArrayList<Durable> getDurables() {
        return durables;
    }

    public void addNewDurable(Durable durable) {
        durables.add(durable);
    }

    @Override
    public String toString() {
        return "DurableList{" +
                "durables=" + durables +
                '}';
    }

    public Boolean isCategoryByText(String text) {
    int i = 0;
    while (i < durables.size()) {
        if (durables.get(i).isCategory(text)) {
            return true;
        }
        i++;
    }
    return false;
}
    public Boolean isNameByText(String text) {
        int i = 0;
        while (i < durables.size()) {
            if (durables.get(i).isName(text)) {
                return true;
            }
            i++;
        }
        return false;
    }
    public Boolean isBorrowerByText(String text) {
        int i = 0;
        while (i < durables.size()) {
            if (durables.get(i).isBorrower(text)) {
                return true;
            }
            i++;
        }
        return false;
    }
    public Boolean isLocationByText(String text) {
        int i = 0;
        while (i < durables.size()) {
            if (durables.get(i).isLocation(text)) {
                return true;
            }
            i++;
        }
        return false;
    }

}