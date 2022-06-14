package com.group12.syDocbackend.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class PakoGzipUtils {

    public static String compress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes());
        gzip.close();
        return out.toString("ISO-8859-1");
    }


    public static String uncompress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        // toString()使用平台默认编码，也可以显式的指定如toString("GBK")
        return out.toString();
    }


    public static String  unCompressURI(String jsUriStr) throws IOException {
        String gzipCompress=uncompress(jsUriStr);
        String result=URLDecoder.decode(gzipCompress,"UTF-8");

        return result;
    }


    public static String  compress2URI(String strData) throws IOException {
        String encodeGzip=compress(strData);
        String jsUriStr=URLEncoder.encode(encodeGzip,"UTF-8");
        return jsUriStr;
    }

}
