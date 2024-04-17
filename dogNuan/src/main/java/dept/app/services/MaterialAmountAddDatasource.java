package dept.app.services;

import dept.app.models.MaterialLog;
import dept.app.models.MaterialLogList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class MaterialAmountAddDatasource implements Datasource<MaterialLogList> {
    private String directoryName;
    private String fileName;

    public MaterialAmountAddDatasource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
        checkFileIsExisted();
    }

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
    public MaterialLogList readData() {
        MaterialLogList materialLogList = new MaterialLogList();
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
                MaterialLog materialLog = new MaterialLog(data[0].trim(), data[1].trim(), Integer.parseInt(data[2].trim()));
                materialLogList.addNewMaterialLog(materialLog);
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
        return materialLogList;
    }


    @Override
    public void writeData(MaterialLogList data) {
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

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
            for (MaterialLog materialLog : data.getMaterialLogs()) {
                String line = materialLog.getId() + ","
                        + materialLog.getDateadd() + ","
                        + materialLog.getAmount();
                buffer.append(line);
                buffer.append("\n");
            }
        }catch (IOException e) {
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
