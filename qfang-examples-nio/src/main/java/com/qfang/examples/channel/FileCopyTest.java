package com.qfang.examples.channel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * @author: liaozhicheng
 * @date: 2018-04-11
 * @since: 1.0
 */
public class FileCopyTest {

    public static void main(String[] args) throws IOException {
//        doCopyUseByte();
//        doCopyUseChar();
        doCopyUseFileChannel();
//        doCopyUseTransferTo();
    }

    private static void doCopyUseByte() throws IOException {
        try (
                InputStream is = FileCopyTest.class.getResourceAsStream("/test.txt");
                OutputStream os = new FileOutputStream(new File("d:/temp/test.txt"))
        ) {
            byte[] bytes = new byte[2];
            while (is.read(bytes) != -1) {
                System.out.println(bytes[0]);
                os.write(bytes);
            }
        }
    }

    private static void doCopyUseChar() throws IOException {
        try (
                LineNumberReader reader = new LineNumberReader(new InputStreamReader(FileCopyTest.class.getResourceAsStream("/test.txt")));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("d:/temp/test-char.txt")), Charset.forName("UTF-8")))
        ) {
            reader.lines().forEach(line -> {
                try {
                    writer.write(line);
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void doCopyUseFileChannel() throws IOException {
        try (
                FileInputStream fis = new FileInputStream(new File("d:/temp/test-char.txt"));
                FileChannel inChannel = fis.getChannel();
                FileOutputStream fos = new FileOutputStream(new File("d:/temp/test-char-2.txt"));
                FileChannel outChannel = fos.getChannel()
        ) {
            ByteBuffer buffer = ByteBuffer.allocate(4);
            while (inChannel.read(buffer) != -1) {
                buffer.flip();

                outChannel.write(buffer);
                buffer.clear();
            }
        }
    }

    private static void doCopyUseTransferTo() throws IOException {
        try (
                FileInputStream fis = new FileInputStream(new File("d:/temp/test-char.txt"));
                FileChannel inChannel = fis.getChannel();
                FileOutputStream fos = new FileOutputStream(new File("d:/temp/test-char-3.txt"));
                FileChannel outChannel = fos.getChannel()
        ) {
            // FileChannel#transferTo  zero copy 技术
            inChannel.transferTo(0, inChannel.size(), outChannel);
        }
    }

}
