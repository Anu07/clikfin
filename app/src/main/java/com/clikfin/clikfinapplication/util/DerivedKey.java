package com.clikfin.clikfinapplication.util;

public class DerivedKey {
    public final byte[] key;
    public final byte[] iv;

    DerivedKey(byte[] key, byte[] iv) {
        this.key = key;
        this.iv = iv;
    }
}

