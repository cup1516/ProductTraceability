package com.pt.ptdealerorder.constant;

/**
 * @author wl
 */
public interface CheckFlagConstants {
    String CheckFlag_0 = "0";

    String CheckFlag_1 = "1";

    String CheckFlag_2 = "2";

    String CheckFlag_3 = "3";

    String Exception_CheckFlag_1_Delete = "订单已送审，无法删除!";

    String Exception_CheckFlag_2_Delete = "订单已通过审核，无法删除!";

    String Exception_CheckFlag_3_Delete = "订单已被驳回，无法删除，请进行修改!";
}
