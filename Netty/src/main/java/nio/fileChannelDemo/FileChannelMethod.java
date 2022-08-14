package nio.fileChannelDemo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @Description : 文件操作
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/8/10 23:18:18
 * @Version : 1.0
 */

public class FileChannelMethod {
    public static void main(String[] args) throws IOException {
        //fileTransferTo();
        fileWalkFileTree();
    }


    /**
     * 大小不能超过2GB
     * 大于2G可以分段获取
     */
    public static void fileTransferTo() {
        try (
                FileChannel from = new FileInputStream("E:\\ProjectData\\Study_Material\\StudyTestDemo\\JavaProject\\UtilsHub\\Netty\\src\\main\\java\\nio\\channelDemo\\from.txt").getChannel();
                FileChannel to = new FileOutputStream("E:\\ProjectData\\Study_Material\\StudyTestDemo\\JavaProject\\UtilsHub\\Netty\\src\\main\\java\\nio\\channelDemo\\to.txt").getChannel();
        ) {
            from.transferTo(0, from.size(), to);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 遍历文件夹操作（walkFile）
     * 可以用于遍历，删除，拷贝文件
     */
    public static void fileWalkFileTree() throws IOException {

        Path resource = Paths.get("E:\\ProjectData\\Study_Material\\StudyTestDemo\\JavaProject\\UtilsHub");

        /*
        使用原子类型作为计数器
         */
        AtomicInteger dirCount = new AtomicInteger();
        AtomicInteger fileCount = new AtomicInteger();


        /*
        使用walkFileTree遍历执行操作
         */
        Files.walkFileTree(resource, new SimpleFileVisitor<Path>() {
            //自行重写方法

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

                System.out.println("====>" + dir);
                dirCount.incrementAndGet();
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file);
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }
        });

        System.out.println("dir count:" + dirCount);
        System.out.println("file count:" + fileCount);

        System.out.println("===================");



        /*
        使用walk返回值为流，可以对其进行流操作
         */
        Stream<Path> walk = Files.walk(resource);


    }
}
