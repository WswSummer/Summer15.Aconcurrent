package com.wsw.concurrent.pdf;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author WangSongWen
 * @Date: Created in 13:59 2021/4/25
 * @Description:
 */
@Component
public class PdfFileConvert {
    public String load() {
        PDDocument pdDocument = null;
        PDFTextStripper stripper;
        String text = null;
        try {
            //pdDocument = PDDocument.load(new File("C:\\Users\\wangsongwen\\Desktop\\广东松炀再生资源股份有限公司2019年年度报告（更正版）.pdf"));
            pdDocument = PDDocument.load(new File("C:\\Users\\wangsongwen\\Desktop\\中加心享灵活配置混合型证券投资基金托管协议(更新).pdf"));
            //pdDocument = PDDocument.load(new File("C:\\Users\\wangsongwen\\Desktop\\贵广网络2020年第一季度报告（更新后）.pdf"));
            stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);
            text = stripper.getText(pdDocument).trim();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pdDocument != null) {
                    pdDocument.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*System.out.println(text);
        System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------------------------------------------");*/
        return format(text);
    }

    private String format(String content) {
        if (content == null || content.isEmpty()) {
            return null;
        }
        String spilter = System.getProperty("line.separator");
        String blank = "    ";
        StringBuilder textStrBuffer = new StringBuilder();
        content = StringUtils.replaceEach(content, new String[]{"[　 ]", "\\?", ","}, new String[]{"", "", ""});
        content = StringReplaceUtil.replaceUTFSpace(content);
        content = StringReplaceUtil.replaceSpecialCharacters(content);
        String[] strelines = content.trim().split(spilter);
        String buffer = blank;
        String tempStr = "";
        int pageUp = 1;// 页面个数
        int pageDown = 1;// 页脚个数
        for (int i = 0; i < strelines.length; i++) {
            String temp = strelines[i].trim().replaceAll("[ ]+", " ");
            if (temp.length() <= 0) {
                continue;
            }
            // 目录中省略号与横线过多导致文本解析不美观
            // 现在限制目录的长度为55，后面可按需调整
            if ((temp.contains("..........") || temp.contains("----------")) && temp.length() >= 55) {
                temp = temp.substring(0, 50) + temp.substring(temp.length() - 5);
            }
            // 页眉统计
            Pattern p1 = Pattern.compile("[123456789][\\d]{0,2}");
            Matcher m1 = p1.matcher(temp);
            Pattern p6 = Pattern.compile("[一二三四五六七八九十]+[、]+");
            Matcher m6 = p6.matcher(temp);
            if (!"".equals(tempStr) && temp.equals(tempStr)) {
                pageUp++;
            } else if ("".equals(tempStr) && (isPageNumber(temp) || m1.find())) {
                tempStr = temp.trim();
            }
            // 去除页号
            if (isPageNumber(temp) || (m1.find() && String.valueOf(pageDown).equals(temp))) {
                pageDown++;
                continue;// 存在一定风险
            }
            int one = temp.indexOf("，");// 逗号
            String t4 = buffer.trim();
            if (i > 4 && !(StringUtils.endsWithAny(t4, new String[]{"。", "：", "；", "！"}))) {
                // 判断上一行是否结束
                Pattern p2 = Pattern.compile("^[\u4e00-\u9fa5。；0-9]+$");// 针对标题、短句被硬性换行符分隔的情况
                Matcher m2 = p2.matcher(temp);
                // 日期被分隔判断
                Pattern p3 = Pattern.compile("^[年月日]");
                Matcher m3 = p3.matcher(temp);
                Pattern p4 = Pattern.compile("\\d$");
                Matcher m4 = p4.matcher(t4);

                if (temp.indexOf("》") < temp.indexOf("《")
                        || (temp.indexOf("》") > 0 && temp.indexOf("《") < 0)
                        || temp.indexOf("”") < temp.indexOf("“")
                        || (temp.indexOf("”") > 0 && temp.indexOf("“") < 0)
                        || temp.indexOf("）", 3) < temp.indexOf("（", 3)
                        || (temp.indexOf("）", 3) > 0 && temp.indexOf("（", 3) < 0)
                        || (m3.find() && m4.find()) || m2.find()
                        || (temp.indexOf("。") > 0 && temp.indexOf("。") < 7)
                        || (one > 0 && one < 5 && !temp.substring(one - 1, one).matches("[\\d]"))
                        || one == 0 || temp.startsWith(".")
                        || (temp.indexOf("；") >= 0 && temp.indexOf("；") < 3)) {
                    buffer = buffer.split(spilter)[0];
                }
                // 本行是标题，上行一定要有换行符
                if (isKeywords(temp) || temp.equals("目 录") || temp.equals("目录")) {
                    if (!buffer.endsWith(spilter + blank)) {
                        buffer = buffer + spilter + blank;
                    }
                }
            }
            textStrBuffer.append(buffer);
            // 该行为标题行
            if (isKeywords(temp)) {
                if (temp.indexOf("）", 3) < temp.indexOf("（", 3)
                        || temp.indexOf("》") < temp.indexOf("《")
                        || temp.indexOf("”") < temp.indexOf("“")
                        || temp.endsWith("、")) {
                    buffer = temp;
                } else {
                    buffer = temp + spilter + blank;
                }
            } else {
                if (temp.indexOf("》") < temp.indexOf("《")
                        || temp.indexOf("”") < temp.indexOf("“")
                        || temp.indexOf("）") < temp.indexOf("（")) {
                    buffer = temp;
                } else if (temp.endsWith("。") || temp.endsWith("：")
                        || temp.endsWith("；") || t4.endsWith("！")
                        || m6.find()) {
                    buffer = temp + spilter + blank;
                } else if ((temp.indexOf("。") >= 0 && !temp.endsWith("。"))
                        || (one > 0 && !temp.substring(one - 1, one).matches("[\\d]"))
                        || (temp.indexOf("、") >= 0 && !(temp.contains("........") || temp.contains("--------")))
                        || temp.endsWith(".")
                        || (temp.contains("：") && !temp.endsWith("。"))) {
                    buffer = temp;
                } else {
                    buffer = temp + spilter + blank;
                }
            }
        }
        textStrBuffer.append(buffer);
        if (pageUp > 4) {
            // 去除页眉页脚
            // 匹配符号* ( ) 转义
            tempStr = StringUtils.replaceEach(tempStr, new String[]{"\\*", "\\(", "(", "\\)", ")", "\\[", "[", "\\]", "]", "\\{", "{", "\\}", "}"}, new String[]{"\\\\*", "\\\\(", "\\(", "\\\\)", "\\)", "\\\\[", "\\[", "\\\\]", "\\]", "\\\\{", "\\{", "\\\\}", "\\}"});
            return textStrBuffer.toString().replaceAll(tempStr + "[\\s]*(\\r\\n)?", "").replaceAll(spilter + spilter, spilter).replaceAll("    " + spilter, "").replaceAll("        ", "    ");
        } else {
            return textStrBuffer.toString();
        }
    }

    private boolean isKeywords(String link) {
        // 以下：存在下列情况时，忽略关键字匹配
        if (link.indexOf("）") < link.indexOf("（")) {
            return false;
        }
        if (link.indexOf("》") < link.indexOf("《")) {
            return false;
        }
        if (link.endsWith("，")) {
            return false;
        }
        Pattern p2 = Pattern.compile("[<(（第]+[1234567890一二三四五六七八九十ABCDEF]{1,3}[>)、）.节章条]?");
        if (link.length() > 4) {
            link = link.substring(0, 4);
        }
        Matcher m2 = p2.matcher(link);
        return m2.find();
    }

    public String getTextFromByte(byte[] fileContent) {
        String content = null;

        try {
            content = new String(fileContent, Charset.forName("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (content == null || content.length() <= 0) {
            return null;
        }
        // 格式排版
        StringBuffer sb = new StringBuffer();
        content = content.replaceAll("[ ]{1,}", " ");
        content = content.replaceAll("[\\?]{1,}", "");
        String spliter = System.getProperty("line.separator");
        String[] lines = content.split(spliter);
        if (lines.length <= 1) {
            spliter = "\n";
            lines = content.split(spliter);
        }
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.length() == 0 || isPageNumber(line)) {
                continue;
            }
            line = "    " + line + spliter;
            sb.append(line);
        }

        return sb.toString();
    }

    private boolean isPageNumber(String line) {
        boolean flag = false;
        line = line.replaceAll("[\\s]+", "");
        if (line.length() < 11) {
            if (!flag) {
                Pattern p2 = Pattern.compile("-[0-9\\s]{1,}-");
                Matcher m2 = p2.matcher(line);
                flag = m2.matches();
            }
            if (!flag) {
                Pattern p3 = Pattern.compile("第[\\s0-9]{1,}页");
                Matcher m3 = p3.matcher(line);
                flag = m3.matches();
            }
            if (!flag) {
                Pattern p4 = Pattern.compile("[0-9\\s]{1,}-[0-9\\s]{1,}");
                Matcher m4 = p4.matcher(line);
                flag = m4.matches();
            }
            if (!flag) {
                Pattern p4 = Pattern.compile("[0-9\\s]{1,}/[0-9\\s]{1,}");
                Matcher m4 = p4.matcher(line);
                flag = m4.matches();
            }
            if (!flag) {
                Pattern p5 = Pattern.compile("第[\\s0-9]{1,}页\\s{0,}共[0-9\\s]{1,}页");
                Matcher m5 = p5.matcher(line);
                flag = m5.matches();
            }
            if (!flag) {
                if (StringUtils.equals(line, "第页"))
                    flag = true;
            }
        }
        return flag;
    }
}
