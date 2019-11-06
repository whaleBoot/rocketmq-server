package com.pansoft.rocketmqserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName StudentDTO
 * @Description TODO
 * @Author whale
 * @Data 2019/11/5 14:09
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
public class StudentDTO implements Serializable {

    private static final long serialVersionUID = 2404086895486462542L;

    private String userName;

    private Integer num;

    public StudentDTO() {
    }
}
