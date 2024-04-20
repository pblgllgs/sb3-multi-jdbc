package com.pblgllgs.multijdbc.enums;
/*
 *
 * @author pblgl
 * Created on 20-04-2024
 *
 */

import lombok.Getter;

@Getter
public enum Tier {

    DEBUG("DEBUG"),
    INFO("INFO"),
    ERROR("ERROR"),
    SYSTEM("SYSTEM");

    private final String value;

    Tier(String value) {
        this.value = value;
    }
}
