package ru.job4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * String service.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 26.12.2018
 */
public class StringService {

    /**
     * Delete all abuse word from InputStream.
     * @param in input stream.
     * @param out output stream.
     * @param abuse abuse words.
     * @throws IOException if input or output streams throws IOException.
     */
    void dropAbuses(final InputStream in, final OutputStream out,
                    final String[] abuse) throws IOException {
        BufferedFilter transfer = new TransferToOutputStream(out);
        for (final String word : abuse) {
            transfer = new WithoutAbuseWord(transfer, word);
        }
        int b = in.read();
        while (b != -1) {
            transfer.transfer(b);
            b = in.read();
        }
        transfer.flush();
    }

    /**
     * Buffered filter.
     *
     * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
     * @since 26.12.2018
     */
    interface BufferedFilter {

        /**
         * Filter and then transfer next byte.
         * @param nextByte source.
         * @throws IOException IOException.
         */
        void transfer(final int nextByte) throws IOException;

        /**
         * Flush buffer.
         * @throws IOException IOException.
         */
        void flush() throws IOException;
    }

    /**
     * Without abuse word.
     *
     * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
     * @since 26.12.2018
     */
    public static class WithoutAbuseWord implements BufferedFilter {
        private final BufferedFilter filter;
        private final char[] abuseWord;
        private final char[] buffer;
        private int cursor = 0;

        public WithoutAbuseWord(final BufferedFilter filter, final String abuse) {
            this.filter = filter;
            this.abuseWord = abuse.toCharArray();
            this.buffer = new char[this.abuseWord.length];
        }

        /**
         * Filter and then transfer next byte.
         * @param nextByte source.
         * @throws IOException IOException.
         */
        @Override
        public void transfer(final int nextByte) throws IOException {
            final char nextChar = (char) nextByte;
            if (nextChar == this.abuseWord[this.cursor]) {
                this.buffer[this.cursor++] = nextChar;
                if (this.cursor == this.abuseWord.length) {
                    this.cursor = 0;
                }
            } else if (this.cursor != 0) {
                this.flushBuffer();
                this.transfer(nextByte);
            } else {
                this.filter.transfer(nextByte);
            }
        }

        /**
         * Flush buffer.
         * @throws IOException IOException.
         */
        @Override
        public void flush() throws IOException {
            while (this.cursor != 0) {
                this.flushBuffer();
            }
            this.filter.flush();
        }

        private void flushBuffer() throws IOException {
            int tempCursor = 0;
            while (tempCursor <= this.cursor) {
                this.filter.transfer(this.buffer[tempCursor++]);
                final int cmpResult = Arrays.compare(
                        this.abuseWord, 0, this.cursor - tempCursor,
                        this.buffer, tempCursor, this.cursor
                );
                if (cmpResult == 0) {
                    final char[] tempBuffer = Arrays.copyOfRange(
                            this.buffer, tempCursor, this.cursor
                    );
                    System.arraycopy(tempBuffer, 0, this.buffer, 0, tempBuffer.length);
                    this.cursor = tempBuffer.length;
                    break;
                }
            }
        }
    }

    /**
     * Transfer to output stream.
     *
     * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
     * @since 26.12.2018
     */
    public static class TransferToOutputStream implements BufferedFilter {
        private final OutputStream outputStream;

        public TransferToOutputStream(final OutputStream outputStream) {
            this.outputStream = outputStream;
        }

        /**
         * Transfer to output stream.
         * @param nextByte source.
         * @throws IOException IOException.
         */
        @Override
        public void transfer(final int nextByte) throws IOException {
            this.outputStream.write(nextByte);
        }

        /**
         * Flush output stream.
         * @throws IOException IOException.
         */
        @Override
        public void flush() throws IOException {
            this.outputStream.flush();
        }
    }
}
