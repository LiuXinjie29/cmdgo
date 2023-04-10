package go;

/**
 * @since: 2023/4/10.
 * @Author: LiuXinjie
 */
public enum ChessColorEnum {
    NULL("+"),
    WHITE("*"),
    BLACK("#")
    ;

    private final String character;

    ChessColorEnum(String character) {
        this.character = character;
    }

    public String getCharacter() {
        return character;
    }
}
