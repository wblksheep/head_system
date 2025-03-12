package com.haiyin.service.interfaces.impl;

import com.haiyin.enums.SprinklerStatus;
import com.haiyin.service.interfaces.StatusOperator;
import org.springframework.stereotype.Component;

@Component("inventoryStatusOperator")
public class InventoryStatusOperator implements StatusOperator {
    @Override
    public void changeStatus(String sprinklerNo, SprinklerStatus from, SprinklerStatus to) {

    }
}
