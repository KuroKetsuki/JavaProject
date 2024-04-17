package dept.app.models;

import java.util.ArrayList;

public class MaterialLogList {
    private ArrayList<MaterialLog> materialLogs;

    public MaterialLogList() {
        materialLogs = new ArrayList<>();
    }


    public MaterialLog findMaterialLogById(String id) {
        for (MaterialLog materialLogs : materialLogs) {
            if (materialLogs.isId(id)) {
                return materialLogs;
            }
        }
        return null;
    }

    public ArrayList<MaterialLog> getMaterialLogs() {
        return materialLogs;
    }

    public void addNewMaterialLog(MaterialLog materialLog) {materialLogs.add(materialLog);}

//    public void addNewMaterialLog(String id, String dateadd, int amount){
//        id = id.trim();
//        dateadd = dateadd.trim();
//        amount = amount;
//
//        if((amount > 0)){
//            MaterialLog exist = findMaterialLogById(id);
//            if(exist == null)
//                materialLogs.add(new MaterialLog(id, dateadd, amount));
//        }
//
//    }

//    public void addAmount(String id, String dateadd, int amount){
//        id = id.trim();
//        dateadd = dateadd.trim();
//
//        if(!id.equals("")&&!dateadd.equals("")){
//            MaterialLog exist = findMaterialLogById(id);
//            if (exist == null){
//                materialLogs.add(new MaterialLog(id, dateadd, amount));
//            }
//        }
//    }
}
