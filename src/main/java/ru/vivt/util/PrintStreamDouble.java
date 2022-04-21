package ru.vivt.util;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class PrintStreamDouble extends PrintStream {
    ByteArrayOutputStream baos;

    public PrintStreamDouble(ByteArrayOutputStream baos, OutputStream old) {
        super(old);
        this.baos = baos;
    }

    @Override
    public void write(byte[] buf, int off, int len) {
        baos.write(buf, off, len);
        super.write(buf, off, len);
    }
}
