package com.dzkjdx.mall.form;

import lombok.Data;

@Data
public class ShippingAddUpdateForm {
    private String receiverName;

    private String receiverPhone;

    private String receiverMobile;

    private String receiverProvince;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverAddress;

    private String receiverZip;
}
