package dept.app.models;

import java.util.ArrayList;

public class RequestBorrowList {
    public ArrayList<RequestBorrow> requestBorrows;

    public RequestBorrowList() {
        requestBorrows = new ArrayList<>();
    }

    public void addNewBorrow(RequestBorrow requestBorrow) {
        requestBorrows.add(requestBorrow);
    }

    public RequestBorrow findBorrowByName(String name) {
        for (RequestBorrow requestBorrow : requestBorrows) {
            if (requestBorrow.isName(name)) {
                return requestBorrow;
            }
        }
        return null;
    }
    public RequestBorrow findBorrowByDurable(String nameDurable) {
        for (RequestBorrow requestBorrow : requestBorrows) {
            if (requestBorrow.isNameDurable(nameDurable)) {
                return requestBorrow;
            }
        }
        return null;
    }
    public RequestBorrow findBorrowByTime(String timeRequest) {
        for (RequestBorrow requestBorrow : requestBorrows) {
            if (requestBorrow.isTime(timeRequest)) {
                return requestBorrow;
            }
        }
        return null;
    }

    public ArrayList<RequestBorrow> getRequestBorrows() {
        return requestBorrows;
    }
}
