package io.github.konohiroaki.deepinitializer;

public class CharacterTypeInitializer extends BaseTypeInitializer<Character> {

    private static char DEFAULT_CHARACTER;

    @Override
    public Character init(Class<Character> clazz) {
        return DEFAULT_CHARACTER;
    }
}
