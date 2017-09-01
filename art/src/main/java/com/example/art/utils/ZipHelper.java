package com.example.art.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;

/**
 * 版权：公司 版权所有
 * <p>
 * 作者：Administrator
 * <p>
 * 版本：
 * <p>
 * 创建日期：2017/8/10 0010
 * <p>
 * 描述：大傻出品避暑精品
 */

public class ZipHelper {


    private ZipHelper() {

    }

    public static String decompressToStringForZlib(byte[] byteToDecompress) {
        return decompressToStringForZlib(byteToDecompress, "UTF-8");
    }


    public static String decompressToStringForZlib(byte[] byteToDecompress, String charsetName) {

        byte[] bytesDecompressed = decompressForZlib
                (
                        byteToDecompress
                );

        String returnValue = null;

        try {
            returnValue = new String
                    (
                            bytesDecompressed,
                            0,
                            bytesDecompressed.length,
                            charsetName
                    );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return returnValue;
    }


    /**
     * zlib decompress 2 byte
     *
     * @param bytesToDecompress
     * @return
     */
    public static byte[] decompressForZlib(byte[] bytesToDecompress) {
        byte[] returnValues = null;

        Inflater inflater = new Inflater();

        int numberOfBytesToDecompress = bytesToDecompress.length;

        inflater.setInput
                (
                        bytesToDecompress,
                        0,
                        numberOfBytesToDecompress
                );

        int bufferSizeInBytes = numberOfBytesToDecompress;

        int numberOfBytesDecompressSoFar = 0;
        List<Byte> bytesDecompressedSoFar = new ArrayList<>();


        try {

            while (inflater.needsInput() == false) {
                byte[] bytesDecompressBuffer = new byte[bufferSizeInBytes];

                int numberOfBytesDecompressedThisTime = inflater.inflate
                        (
                                bytesDecompressBuffer
                        );

                numberOfBytesDecompressSoFar += numberOfBytesDecompressedThisTime;

                for (int b = 0; b < numberOfBytesDecompressedThisTime; b++) {
                    bytesDecompressedSoFar.add(bytesDecompressBuffer[b]);
                }
            }

            returnValues = new byte[bytesDecompressedSoFar.size()];
            for (int b = 0; b < returnValues.length; b++) {
                returnValues[b] = (byte) bytesDecompressedSoFar.get(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        inflater.end();

        return returnValues;

    }


    /**
     * gzip compress 2 byte
     */
    public static byte[] compressForZlib(byte[] byteToCompress) {
        Deflater deflater = new Deflater();
        deflater.setInput(byteToCompress);
        deflater.finish();

        byte[] bytesCompressed = new byte[Short.MAX_VALUE];

        int numberOfBytesAfterCompression = deflater.deflate(bytesCompressed);

        byte[] returnValues = new byte[numberOfBytesAfterCompression];
        System.arraycopy
                (
                        bytesCompressed,
                        0,
                        returnValues,
                        0,
                        numberOfBytesAfterCompression
                );

        return returnValues;

    }


    /**
     *
     * @param stringToCompress
     * @return
     */
    public static byte[] compressForZlib(String stringToCompress) {
        byte[] returnValues = null;

        try {
            returnValues = compressForZlib
                    (
                            stringToCompress.getBytes("UTF-8")
                    );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return returnValues;

    }


    /**
     * gzip decompress 2 string
     *
     * @param compressed 一压缩的 byte 数组
     *                   把tyte 解压成 gzip 类型的 string 字符串
     *                   默认使用utf-8格式，支持中文
     */

    /**
     * gzip decompress 2 string
     *
     * @param compressed
     * @return
     * @throws IOException
     */

    public static String decompressForGzip(byte[] compressed) {
        return decompressForGzip(compressed, "UTF-8");
    }

    public static byte[] compressForGzip(String string) {
        ByteArrayOutputStream os = null;
        GZIPOutputStream gos = null;
        try {
            os = new ByteArrayOutputStream(string.length());
            gos = new GZIPOutputStream(os);
            gos.write(string.getBytes("UTF-8"));
            byte[] compressed = os.toByteArray();
            return compressed;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuitely(gos);
            closeQuitely(os);
        }
        return null;
    }


    /**
     * gzip decompress 2 string
     *
     * @param compressed 一压缩的 byte 数组
     *                   把tyte 解压成 gzip 类型的 string 字符串
     */

    public static String decompressForGzip(byte[] compressed, String charsetName) {
        final int BUFFER_SIZE = compressed.length; // 可变数组的最终长度
        GZIPInputStream gis = null;//gzip 流
        ByteArrayInputStream is = null;// byte 输入 流
        try {
            is = new ByteArrayInputStream(compressed);
            gis = new GZIPInputStream(is, BUFFER_SIZE);
            StringBuffer string = new StringBuffer();
            byte[] data = new byte[BUFFER_SIZE];//穿件一个byte 数组。长度为  已经压缩的byte 的长度
            int bytesRead;
            while ((bytesRead = gis.read(data)) != -1) {
                string.append(new String(data, 0, bytesRead, charsetName));
            }
            return string.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuitely(gis);
            closeQuitely(is);
        }
        return null;
    }


    public static void closeQuitely(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();

            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception ignored) {

            }
        }
    }


}
