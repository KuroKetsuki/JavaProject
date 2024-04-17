package dept.app.models;

import java.util.ArrayList;

public class RequestReturnList {
    public ArrayList<RequestReturn> requestReturns;

    public RequestReturnList() {
        requestReturns = new ArrayList<>();
    }

    public void addNewReturn(RequestReturn requestReturn) {
        requestReturns.add(requestReturn);
    }

    public void addReturn(String name, String nameDurable, String location, String timeRequest, String permission) {
        name = name.trim();
        nameDurable = nameDurable.trim();
        location = location.trim();
        permission = permission.trim();
        if (!name.equals("") && !nameDurable.equals("")) {
            RequestReturn exist = findReturnByName(name);
            if (exist == null) {
                requestReturns.add(new RequestReturn(name, nameDurable, location, timeRequest, permission));
            }
        }
    }

    public RequestReturn findReturnByName(String name) {
        for (RequestReturn requestReturn : requestReturns) {
            if (requestReturn.isName(name)) {
                return requestReturn;
            }
        }
        return null;
    }

    public ArrayList<RequestReturn> getReturns() {
        return requestReturns;
    }
}
