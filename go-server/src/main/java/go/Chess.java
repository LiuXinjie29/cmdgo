package go;

/**
 * @since: 2023/4/10.
 * @Author: LiuXinjie
 */
public enum Chess {

    WHITE("*"),
    BLACK("#")
    ;

    private final String character;

    Chess(String character) {
        this.character = character;
    }

    public String getCharacter() {
        return character;
    }
}
