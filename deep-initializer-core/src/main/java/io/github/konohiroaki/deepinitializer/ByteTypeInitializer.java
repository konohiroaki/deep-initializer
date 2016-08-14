package io.github.konohiroaki.deepinitializer;

public class ByteTypeInitializer extends BaseTypeInitializer<Byte> {

    private static byte DEFAULT_BYTE;

    @Override
    public Byte init(Class<Byte> clazz) {
        return DEFAULT_BYTE;
    }
}
