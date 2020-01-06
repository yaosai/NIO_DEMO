package com.yaosai.niodemo.filechannel;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 文件管道测试类
 *
 * @author YaoS
 * @date 19/3/11 17:56
 */
public class FileChannelTest {

    @Test
    public void write() throws Exception {
        // 获取文件通道。基于某种流（比如文件输出流或输入流来创建)
        FileChannel fc = new FileOutputStream(new File("1.txt")).getChannel();
        ByteBuffer buffer = ByteBuffer.wrap("helloWorld".getBytes());
        fc.write(buffer);
        fc.close();
    }

    @Test
    public void read() throws Exception {
        FileChannel fc = new FileInputStream(new File("1.txt")).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(10);
        // 文件通道可以通过位置灵活的操作数据
        // 文件通道底层可以使用zero copy零拷贝技术
        fc.position(4);
        fc.read(buffer);
        System.out.println(new String(buffer.array()));
        fc.close();
    }

    /**
     * 功能描述: 使用直接缓冲区完成文件的复制
     *
     * @author YaoS
     * @date 20/1/6 20:27
     */
    @Test
    public void fastReadAndWrite() throws Exception {
        //打开通道，赋予读权限
        FileChannel inChannel = FileChannel.open(Paths.get("1.txt"), StandardOpenOption.READ);
        //打开通道，赋予读写创建权限，可以看到FileChannel.open后面的参数是可变参数
        FileChannel outChannel = FileChannel.open(Paths.get("2.txt"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);
        //通过map方法获取两个通道的buffer对象
        MappedByteBuffer in = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer out = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());
        //定义数据的数组，并从in中读取数据到dst，再将数据写入out
        byte[] dst = new byte[in.limit()];
        in.get(dst);
        out.put(dst);
        //关闭通道
        inChannel.close();
        outChannel.close();
    }
}
