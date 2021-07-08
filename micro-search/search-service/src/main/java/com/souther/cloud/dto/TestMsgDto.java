package com.souther.cloud.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description:
 * @Author souther
 * @Date: 2021/6/29 16:12
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestMsgDto {

    private String title; //模糊查询

    private Integer type; //精准查询

    private Boolean dataDeleted;

    private LocalDateTime createTime;

    private List<Integer> ids;
}
