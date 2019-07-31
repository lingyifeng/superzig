package com.android.hz.czc.utils;

import com.android.hz.czc.enums.PhotoEnum;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by czc-pual on 2019/1/8.
 */
@Slf4j
public class ImageInfoUtil {

    /**
     * 获取单张图片的全量Info信息
     *
     * @param imagePath 图片的物理路径
     */
    public static Map<String, Integer> getImageWidthAndHeight(File file) throws IOException {
        Map<String, Integer> imageInfo = new HashMap<>();
        FileInputStream fis = new FileInputStream(file);
        BufferedImage bufferedImage = ImageIO.read(fis);
        imageInfo.put(PhotoEnum.HPOTO.getWidth(), bufferedImage.getWidth());
        imageInfo.put(PhotoEnum.HPOTO.getHeight(), bufferedImage.getHeight());
        fis.close();
        return imageInfo;
    }

}
