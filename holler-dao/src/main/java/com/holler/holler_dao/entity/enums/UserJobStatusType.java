package com.holler.holler_dao.entity.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pravina on 04/10/15.
 */
public enum UserJobStatusType {
    ACCEPTED,
    REJECTED,
    GRANTED,
    UNACCEPT,
    UNGRANT;

    public static List<String> getAcceptedAndGrantedStatus() {
        List<String> list = new ArrayList<String>();
        list.add(ACCEPTED.toString());
        list.add(GRANTED.toString());
        return list;
    }
}
