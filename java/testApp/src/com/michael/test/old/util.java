package com.michael.test.old;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

public class util {
    public static void main(String [] args) throws IOException {
        int[] data = { 100, 200, 300, 400 };
        long startTime = System.nanoTime();
        byte[] output = integersToBytes4(data);
        for (byte b:output) {
            System.out.println(b);
        }
        long endTime = System.nanoTime();
        System.out.println("function takes " + (endTime-startTime) + " nanoseconds");
    }

    private static byte[] integersToBytes(int[] data) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(data.length * 4);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(data);

        return byteBuffer.array();
    }

    private static byte[] integersToBytes2(int[] data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        for(int i=0; i < data.length; ++i)
        {
            dos.writeInt(data[i]);
        }

        return baos.toByteArray();
    }

    private static byte[] integersToBytes3(int[] data) {
        final ByteBuffer buf = ByteBuffer.allocate(data.length * 4)
                .order(ByteOrder.LITTLE_ENDIAN);
        buf.asIntBuffer().put(data);
        return buf.array();
    }

    private static byte[] integersToBytes4(int[] data) {
        if (data != null) {
            byte[] outputBytes = new byte[data.length * 4];

            for(int i = 0, k = 0; i < data.length; i++) {
                int integerTemp = data[i];
                for(int j = 0; j < 4; j++, k++) {
                    outputBytes[k] = (byte)((integerTemp >> (8 * j)) & 0xFF);
                }
            }
            return outputBytes;
        } else {
            return null;
        }
    }
}
