package com.haiyin.service.interfaces;

import com.haiyin.enums.SprinklerStatus;

public interface StatusOperator {
    void changeStatus(String sprinklerNo, SprinklerStatus from, SprinklerStatus to);
}
