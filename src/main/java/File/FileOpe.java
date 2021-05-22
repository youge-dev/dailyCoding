package File;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * File api
 *
 * @author yqzhou
 * @Date 20210522
 */
public class FileOpe {
    /**
     * 场景：内存2G的机器对4G的大文件（每行都是IP）去重并输出到文件
     *
     * @param filePath   文件路径（不包括格式后缀）
     * @param fileCount  分割文件数量
     * @param outputPath 对文件IP去重后的结果文件
     * @throws IOException
     */

    public static void splitAndMergeFile(String filePath, int fileCount, String outputPath) {

        try {
            FileInputStream fis = new FileInputStream(filePath);
            FileChannel inputChannel = fis.getChannel();
            final long fileSize = inputChannel.size();
            long average = fileSize / fileCount;//平均值
            long bufferSize = 200; //缓存块大小，自行调整
            ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.parseInt(bufferSize + "")); // 申请一个缓存区
            long startPosition = 0; //子文件开始位置
            long endPosition = average < bufferSize ? 0 : average - bufferSize;//子文件结束位置
            for (int i = 0; i < fileCount; i++) {
                if (i + 1 != fileCount) {
                    int read = inputChannel.read(byteBuffer, endPosition);// 读取数据
                    readW:
                    while (read != -1) {
                        byteBuffer.flip();//切换读模式
                        byte[] array = byteBuffer.array();
                        for (int j = 0; j < array.length; j++) {
                            byte b = array[j];
                            if (b == 10 || b == 13) { //判断\n\r
                                endPosition += j;
                                break readW;
                            }
                        }
                        endPosition += bufferSize;
                        byteBuffer.clear(); //重置缓存块指针
                        read = inputChannel.read(byteBuffer, endPosition);
                    }
                } else {
                    endPosition = fileSize; //最后一个文件直接指向文件末尾
                }

                int m = i + 1;
                String subFile = filePath + m;
                while (new File(filePath + m).exists()) {
                    m++;
                }
                FileOutputStream fos = new FileOutputStream(filePath + m);
                FileChannel outputChannel = fos.getChannel();
                inputChannel.transferTo(startPosition, endPosition - startPosition, outputChannel);//通道传输文件数据
                outputChannel.close();
                fos.close();
                startPosition = endPosition + 1;
                endPosition += average;
                distinctFileIp(subFile, outputPath);
            }
            inputChannel.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void distinctFileIp(String inputPath, String outputPath) {
        BufferedReader reader, reader_output;
        List<String> list = new ArrayList<>();
        // read
        try {
            reader = new BufferedReader(new FileReader(new File(inputPath)));
            reader_output = new BufferedReader(new FileReader(new File(outputPath)));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                list.add(tempString);
            }
            reader.close();
            while ((tempString = reader_output.readLine()) != null) {
                list.add(tempString);
            }
            reader_output.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // write
        try {
            FileWriter fw = new FileWriter(outputPath, true);
            PrintWriter out = new PrintWriter(fw);
            list.stream().distinct().forEach(s -> {
                out.write(s);
                out.println();
            });
            fw.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
