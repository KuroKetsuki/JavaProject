package dept.app.services;

public interface Datasource<T> {
    T readData();
    void writeData(T data);
}
