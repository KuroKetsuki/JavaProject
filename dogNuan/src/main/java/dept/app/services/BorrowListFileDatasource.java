package dept.app.services;


import dept.app.models.RequestBorrow;
import dept.app.models.RequestBorrowList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class BorrowListFileDatasource implements Datasource<RequestBorrowList>{
    private String directoryName;
    private String fileName;

    public BorrowListFileDatasource(String directoryName, String fileName) {
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
    public RequestBorrowList readData() {
        RequestBorrowList requestBorrowList = new RequestBorrowList();
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
                RequestBorrow requestBorrow = new RequestBorrow(data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(), data[4].trim());
//                LocalDateTime.parse(data[2]);
                requestBorrowList.addNewBorrow(requestBorrow);
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


        return requestBorrowList;
    }

    @Override
    public void writeData(RequestBorrowList data) {
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
            for (RequestBorrow requestBorrow : data.getRequestBorrows()) {
                String line = requestBorrow.getNameBorrower() + ","
                        + requestBorrow.getNameDurable() + ","
                        + requestBorrow.getLocation() + ","
                        + requestBorrow.getTimeRequest() + ","
                        + requestBorrow.getPermission();
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
