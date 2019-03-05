package org.duohuo.paper.utils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.FileHeader;
import org.duohuo.paper.exceptions.ZipFileException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

public final class FileUtil {

    private FileUtil() {

    }

    public static byte[] createZipWithOutPutStreams(Map<String, byte[]> fileMap) {
        ZipOutputStream zipOutputStream;
        for (Map.Entry<String, byte[]> file : fileMap.entrySet()) {
            InputStream inputStream = new ByteArrayInputStream(file.getValue());

        }

        return null;
    }

    //删除临时文件
    public static void fileDelete(List<String> filePaths, String targetPath) {
        for (String path : filePaths) {
            File file = new File(targetPath + path);
            if (file.isDirectory()) {
                fileDelete(file);
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void fileDelete(File directFile) {
        File[] files = directFile.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    fileDelete(file);
                } else {
                    file.delete();
                }
            }
        }
        directFile.delete();
    }

    /*
    需要zip存储路径，解压之后的文件路径，以及文件名称
     */
    public static List<String> decompressZipFile(byte[] data, String targetPath, String decompressPath, String fileName) {
        String originalZipFilePath = targetPath + File.separator + fileName;
        //这是压缩文件
        File file = saveFile(data, originalZipFilePath);
        ZipFile zipFile;
        List<String> resultPathList = new ArrayList<>();
        try {
            zipFile = new ZipFile(file);
            zipFile.extractAll(decompressPath);
            List fileHeaderList = zipFile.getFileHeaders();
            if (fileHeaderList == null) {
                file.deleteOnExit();
                throw new ZipFileException("上传zip为空:" + fileName);
            }
            for (Object o : fileHeaderList) {
                resultPathList.add(((FileHeader) o).getFileName());
            }
        } catch (Exception e) {
            throw new ZipFileException("Zip处理出错:" + fileName);
        } finally {
            //最后删除压缩文件
            file.deleteOnExit();
        }
        return resultPathList;
    }

    private static File saveFile(byte[] data, String zipFilePath) {
        File file = new File(zipFilePath);
        OutputStream outputStream = null;
        try {
            if (!file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            outputStream.write(data);
            outputStream.flush();
        } catch (Exception e) {
            file.deleteOnExit();
            throw new ZipFileException(e.getMessage());
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception ignore) {
                }
            }
        }
        return file;
    }
}