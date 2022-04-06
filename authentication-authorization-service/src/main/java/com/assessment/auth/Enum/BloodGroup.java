package com.assessment.auth.Enum;

import java.io.Serializable;

public enum BloodGroup implements Serializable {


    O_POS("O+"), O_NEG("O-"), A_POS("A+"), A_NEG("A-"), B_POS("B+"), B_NEG("B-");

    private String group;

    private BloodGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return this.group;
    }

//    public static BloodGroup getBloodGroup(String group) {
//        for (BloodGroup bloodGrp : BloodGroup.values()) {
//            if (bloodGrp.getGroup().equalsIgnoreCase(group)) {
//                return bloodGrp;
//            }
//        }
//        throw new RuntimeException();
//
//    }




}
