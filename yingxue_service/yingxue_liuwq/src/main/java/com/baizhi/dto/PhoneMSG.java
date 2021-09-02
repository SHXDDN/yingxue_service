package com.baizhi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneMSG {

    public String RequestId;
    public String Message;
    public String BizId;
    public String Code;
}
