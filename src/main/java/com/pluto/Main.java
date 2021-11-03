package com.pluto;

import com.pluto.service.GoldCodeServiceInstance;

/**
 * @author Kevin.H
 * @version 5.1
 * Created by Kevin.H on 2021/11/1
 */
public class Main {

    public static void main(String[] args) {
        if (!checkArgs(args)){
            throw new IllegalArgumentException("input argument is wrong , need date like : 2021-10-01");
        }
        GoldCodeServiceInstance.INSTANCE.start(args[0]);
    }

    public static boolean checkArgs(String[] args){
        if (args.length == 0){
            return false;
        }
        String date = args[0];
        String rex = "((\\d{2}(([02468][048])|([13579][26]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        return date.matches(rex);
    }

}
