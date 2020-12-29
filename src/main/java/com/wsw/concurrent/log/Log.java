package com.wsw.concurrent.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author WangSongWen
 * @Date: Created in 10:05 2020/12/29
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log implements Serializable {
    private Long id;
    private String type;
    private String name;
    private String message;
}
