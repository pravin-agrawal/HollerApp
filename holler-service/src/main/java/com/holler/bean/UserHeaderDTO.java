package com.holler.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by pravina on 14/10/17.
 */
@Getter @Setter
public class UserHeaderDTO {

    private Integer unSeenNotificationCount;

    private Integer unSeenMessageCount;
}
