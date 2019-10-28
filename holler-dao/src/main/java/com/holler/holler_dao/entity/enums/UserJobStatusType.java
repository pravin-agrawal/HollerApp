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
    COMPLETED,
    UNGRANT,
    ACCEPTER,
    PROVIDER,
    CANCELLED;

    public static List<String> getUsersAcceptedJobStatus() {
        List<String> list = new ArrayList<String>();
        list.add(ACCEPTED.name());
        list.add(GRANTED.name());
        list.add(REJECTED.name());
        list.add(COMPLETED.name());
        return list;
    }
    
    public static List<String> getAcceptedAndGrantedJobStatus() {
        List<String> list = new ArrayList<String>();
        list.add(ACCEPTED.name());
        list.add(GRANTED.name());
        return list;
    }
}
