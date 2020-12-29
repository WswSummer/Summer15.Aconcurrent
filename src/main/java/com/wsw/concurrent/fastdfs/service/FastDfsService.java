package com.wsw.concurrent.fastdfs.service;

import com.wsw.concurrent.fastdfs.domain.FastFile;

/**
 * @Author WangSongWen
 * @Date: Created in 17:02 2020/12/28
 * @Description:
 */
public interface FastDfsService {
    FastFile uploadFile(FastFile fastFile) throws Exception;

    byte[] downloadFile(String groupName, String filePath);
}
