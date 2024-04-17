package dept.app.services;

import dept.app.models.Durable;
import dept.app.models.DurableList;
import javafx.scene.image.Image;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class DurableListFileDatasource implements Datasource<DurableList> {
    private String directoryName;
    private String fileName;

    public DurableListFileDatasource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
        checkFileIsExisted();
    }

    // ตรวจสอบว่ามีไฟล์ให้อ่านหรือไม่ ถ้าไม่มีให้สร้างไฟล์เปล่า
    private void checkFileIsExisted() {
        File file = new File(directoryName);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath = directoryName + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public DurableList readData() {
        DurableList durableList = new DurableList();
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);
        FileReader reader = null;
        BufferedReader buffer = null;


        try {
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);
            String line = "";
            while ((line = buffer.readLine()) != null) {
                String[] data = line.split(",");
                Durable durable = new Durable(
                        data[0].trim(),
                        data[1].trim(),
                        data[2].trim(),
                        data[3].trim(),
                        data[4].trim(),
                        data[5].trim(),
                        data[6].trim(),
                        data[7].trim(),
                        data[8].trim());
                durable.setImagePath(data[4].trim());
                durableList.addNewDurable(durable);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return durableList;
    }
//        DurableList durables = new DurableList();
//        String filePath = directoryName + File.separator + fileName;
//        File file = new File(filePath);
//
//
//        // เตรียม object ที่ใช้ในการอ่านไฟล์
//        FileInputStream fileInputStream = null;
//
//        try {
//            fileInputStream = new FileInputStream(file);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        InputStreamReader inputStreamReader = new InputStreamReader(
//                fileInputStream,
//                StandardCharsets.UTF_8
//        );
//        BufferedReader buffer = new BufferedReader(inputStreamReader);
//
//        String line = "";
//        try {
//            // ใช้ while loop เพื่ออ่านข้อมูลรอบละบรรทัด
//            while ( (line = buffer.readLine()) != null ){
//                // ถ้าเป็นบรรทัดว่าง ให้ข้าม
//                if (line.equals("")) continue;
//
//                // แยกสตริงด้วย ,
//                String[] data = line.split(",");
//                Durable durable = new Durable(data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(), data[5].trim(), data[6].trim(), data[7].trim(), data[8].trim(), data[9].trim(), data[10].trim());
//
//                // อ่านข้อมูลตาม index แล้วจัดการประเภทของข้อมูลให้เหมาะสม
////                String category = data[0].trim();
////                String id = data[1].trim();
////                String name = data[2].trim();
////                String datepurchase = data[6].trim();
////                String timepurchase = data[7].trim();
////                String status = data[3].trim();
////                String location = data[5].trim();
////                String borrower = data[8].trim();
////                String borrowdate = data[9].trim();
////                String borrowtime = data[10].trim();
//
//
//                // เพิ่มข้อมูลลงใน list
////                durables.addNewBorrower(category, id, name, datepurchase, timepurchase, status, location, borrower, borrowdate, borrowtime, imagePath);
//                durables.addNewBorrower(durable);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        return durables;

    @Override
    public void writeData(DurableList data) {
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        // เตรียม object ที่ใช้ในการเขียนไฟล์
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                fileOutputStream,
                StandardCharsets.UTF_8
        );
        BufferedWriter buffer = new BufferedWriter(outputStreamWriter);

        try {
            // สร้าง csv ของ User และเขียนลงในไฟล์ทีละบรรทัด
            for (Durable durable : data.getDurables()) {
                String line = durable.getCategory() + ","
                        + durable.getId() + ","
                        + durable.getName() + ","
                        + durable.getStatus() + ","
                        + durable.getImagePath() + ","
                        + durable.getLocation() + ","
                        + durable.getDatepurchase() + ","
                        + durable.getBorrower() + ","
                        + durable.getBorrowdate();
                buffer.append(line);
                buffer.append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                buffer.flush();
                buffer.close();
            }
            catch (IOException e){
                throw new RuntimeException(e);
            }
        }
    }

}
