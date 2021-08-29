package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "yx_log")
public class Log {

    @Id
    private String id;

    @Column(name = "admin_name")
    private String adminName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "option_time")
    private Date optionTime;

    @Column(name = "method_name")
    private String methodName;

    private String status;


}