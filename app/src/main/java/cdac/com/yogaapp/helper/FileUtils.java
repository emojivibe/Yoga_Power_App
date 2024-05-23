package cdac.com.yogaapp.helper;

import static cdac.com.yogaapp.helper.StringValueHelper.DIR_NAME;
import static cdac.com.yogaapp.helper.StringValueHelper.FILE_EXT;
import static cdac.com.yogaapp.helper.StringValueHelper.TEMP_FILE_NAME;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class FileUtils {
    public static void saveFile(byte[] encodedBytes, String path) {
        try {
            File file = new File(path);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(encodedBytes);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static byte[] readFile(String filePath) {
        byte[] contents;
        File file = new File(filePath);
        int size = (int) file.length();
        contents = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(
                    new FileInputStream(file));
            try {
                buf.read(contents);
                buf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return contents;

    }


    @NonNull

    public static File createTempFile(Context context, byte[] decrypted) throws IOException {

        File tempFile = File.createTempFile(TEMP_FILE_NAME, FILE_EXT, context.getCacheDir());

        tempFile.deleteOnExit();

        FileOutputStream fos = new FileOutputStream(tempFile);

        fos.write(decrypted);

        fos.close();

        return tempFile;

    }


    public static String getTempFileDescriptor(Context context, byte[] decrypted) throws IOException {
        Log.d("1234", "getTempFileDescriptor: " + FileUtils.createTempFile(context, decrypted));
        File tempFile = FileUtils.createTempFile(context, decrypted);
        Log.d("1234", "getTempFileDescriptor: " + tempFile.getAbsolutePath());
        return tempFile.getAbsolutePath();

    }


    public static final String getDirPath(Context context) {
        String path = context.getDir(DIR_NAME, Context.MODE_PRIVATE).getAbsolutePath();
        return path;
    }


    public static final String getFilePath(Context context, String fileName) {
        return getDirPath(context) + File.separator + fileName;
    }

    public static final void deleteDownloadedFile(Context context, String fileName) {
        File file = new File(getFilePath(context, fileName));
        if (null != file && file.exists()) {
            if (file.delete()) Log.i("FileUtils", "File Deleted.");
        }
    }

    public static final String getFileName(Context context, String fileName) {
        File file = new File(getFilePath(context, fileName));
        if (null != file && file.exists()) {
            return file.getName();
        } else {
            return null;
        }
    }
}
