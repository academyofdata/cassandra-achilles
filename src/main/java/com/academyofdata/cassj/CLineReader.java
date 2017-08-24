package com.academyofdata.cassj;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;


public class CLineReader implements Iterable<String> {

    private BufferedReader r;

    public CLineReader(java.io.BufferedReader r) {
        this.r = r;
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {

            @Override
            public boolean hasNext() {
                try {
                    r.mark(1);
                    if (r.read() < 0) {
                        return false;
                    }
                    r.reset();
                    return true;
                } catch (IOException e) {
                    return false;
                }
            }

            @Override
            public String next() {
                try {
                    return r.readLine();
                } catch (IOException e) {
                    return null;
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

        };
    }

}