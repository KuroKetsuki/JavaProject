package dept.app.models;

import java.util.ArrayList;

public class MaterialWithdrawList {

    private ArrayList<MaterialWithdraw> materialWithdraws;

    public MaterialWithdrawList() {
        materialWithdraws = new ArrayList<>();
    }

    public MaterialWithdraw findMaterialWithdrawById(String id) {
        for (MaterialWithdraw materialWithdraws : materialWithdraws) {
            if (materialWithdraws.isId(id)) {
                return materialWithdraws;
            }
        }
        return null;
    }

    public ArrayList<MaterialWithdraw> getMaterialWithdraws() {
        return materialWithdraws;
    }

    public void addNewWithdraw(MaterialWithdraw materialWithdraw) {
        materialWithdraws.add(materialWithdraw);
    }

}
