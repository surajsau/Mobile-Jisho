package com.halfplatepoha.jisho;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by surjo on 19/07/17.
 */

public class ProgressInputStream extends InputStream {

    private InputStream stream;
    private ProgressListener listener;

    private long offset = 0;

    public ProgressInputStream(InputStream stream, ProgressListener listener) {
        this.stream = stream;
        this.listener = listener;
    }

    @Override
    public int read() throws IOException {
        int res = stream.read();
        listener.onProgressChanged(++offset);

        return res;
    }

    @Override
    public int read(@NonNull byte[] b, int off, int len) throws IOException {
        int res = stream.read(b, off, len);
        offset += res;
        listener.onProgressChanged(offset);

        return res;
    }

    public interface ProgressListener {
        void onProgressChanged(long bytes);
    }
}
