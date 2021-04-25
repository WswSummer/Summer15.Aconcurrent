package com.wsw.concurrent.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @Author WangSongWen
 * @Date: Created in 13:59 2021/4/25
 * @Description:
 */
@Component
public class PdfFileConvert {
    public String load() {
        PDDocument pdDocument;
        PDFTextStripper stripper;
        String text = null;
        try {
            pdDocument = PDDocument.load(new File("C:\\Users\\wangsongwen\\Desktop\\分众传媒：2020年年度报告.pdf"));
            stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);
            text = stripper.getText(pdDocument);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }
}
