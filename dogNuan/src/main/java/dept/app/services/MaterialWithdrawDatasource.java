package dept.app.services;

import dept.app.models.MaterialWithdraw;
import dept.app.models.MaterialWithdrawList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class MaterialWithdrawDatasource implements Datasource<MaterialWithdrawList> {
    private String directoryName;
    private String fileName;

    public MaterialWithdrawDatasource(String directoryName, String fileName) {
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
    public MaterialWithdrawList readData() {
        MaterialWithdrawList materialWithdrawList = new MaterialWithdrawList();
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
                MaterialWithdraw materialWithdraw = new MaterialWithdraw(data[0].trim(), data[1].trim(), data[2].trim(), Integer.parseInt(data[3].trim()));
                materialWithdrawList.addNewWithdraw(materialWithdraw);
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
        return materialWithdrawList;
    }

    @Override
    public void writeData(MaterialWithdrawList data) {
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
            for (MaterialWithdraw materialWithdraw : data.getMaterialWithdraws()) {
                String line = materialWithdraw.getId() + ","
                        + materialWithdraw.getName() + ","
                        + materialWithdraw.getRequisitiondate() + ","
                        + materialWithdraw.getAmount();
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
