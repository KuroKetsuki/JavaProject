package dept.app.models;

import java.util.ArrayList;

public class MaterialList {
    private ArrayList<Material> materials;

    public MaterialList() {
        materials = new ArrayList<>();
    }

    public void addAmountToId(String id, int remainder) {
        Material exist = findMaterialById(id);
        if (exist != null) {
            exist.addAmount(remainder);
        }
    }
    public void DecreaseAmountToId(String id, int remainder) {
        Material exist = findMaterialById(id);
        if (exist != null) {
            exist.decreaseAmount(remainder);
        }
    }

    public Material findMaterialById(String id) {
        for (Material material : materials) {
            if (material.isId(id)) {
                return material;
            }
        }
        return null;
    }

//    public void borrowToId(String id, String borrower) {
//        Material exist = findMaterialById(id);
//        if (exist != null) {
//            exist.addBorrower(borrower);
//        }
//    }

    public ArrayList<Material> getMaterials() {
        return materials;
    }

    public void addNewMaterial(Material material) {
        materials.add(material);
    }

    public Material findMaterialByName(String name) {
        for (Material material : materials) {
            if (material.isName(name)) {
                return material;
            }
        }
        return null;
    }


//    public void addNewMaterial(String category, String name, int remainder, String id) {
//        category = category.trim();
//        id = id.trim();
//        name = name.trim();
//
//        if(!name.equals("")&& !id.equals("")&& !category.equals("")){
//            Material exist = findMaterialByName(name);
//            if (exist == null){
//                materials.add(new Material(category, id, remainder, name));
//            }
//        }
//    }
}
