package framework.mvp1.base.util;

import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class StrZipUtil {
    /**
     * 使用zip进行压缩
     *
     * @param str 压缩前的文本
     * @return 返回压缩后的文本
     */
    public static final String zip(String str) {
        if (str == null)
            return null;
        byte[] compressed;
//
        String compressedStr = null;
        int strl = getLength(str);
        int m = strl / 1024;
        try {
            byte[] output = new byte[1024 * (m + 1)];
            Deflater compresser = new Deflater();
            compresser.setInput(str.getBytes("utf-8"));
            compresser.finish();
            int compressedDataLength = compresser.deflate(output);
            compressed = Arrays.copyOf(output, compressedDataLength);
            compressedStr = new String(Base64.encode(compressed, Base64.NO_WRAP), "utf-8");
        } catch (Exception e) {
        } finally {
            compressed = null;
        }
        return compressedStr;
    }

    public static int getLength(String s) {
        int length = 0;
        for (int i = 0; i < s.length(); i++) {
            int ascii = Character.codePointAt(s, i);
            if (ascii >= 0 && ascii <= 255) {
                length++;
            } else {
                length += 2;
            }
        }
        return length;
    }


    /**
     * 使用zip进行解压缩
     *
     * @param compressedStr 压缩后的文本
     * @return 解压后的字符串
     */
    public static final String unzip(String compressedStr) {
        if (ToolUtils.isNull(compressedStr)) {
            return "";
        }
        byte[] output = new byte[0];
        byte[] data = Base64.decode(compressedStr, Base64.NO_WRAP);
        Inflater decompresser = new Inflater();
        decompresser.reset();
        //设置当前输入解压
        decompresser.setInput(data, 0, data.length);
        Log.i("BaseRXNetApi", "data.length:" + data.length);
        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!decompresser.finished()) {
                int i = decompresser.inflate(buf);
//                if (i == 0) {
//                    break;
//                }
                o.write(buf, 0, i);
            }
            output = o.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                o.close();
            } catch (IOException e) {
            }
        }
        decompresser.end();
        return new String(output);
    }
}