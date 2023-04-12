package go;

/**
 * @since: 2023/4/10.
 * @Author: LiuXinjie
 */
public class Player {

    private String name;

    private Chessboard chessboard;

    private ChessColorEnum chessColorEnum;

    public static Player build(String name) {
        Player player = new Player();
        player.name = name;
        return player;
    }

    public String join(String chessboardNumber) {
        Chessboard chessboard = Chessboard.ChessboardMap().get(chessboardNumber);
        if (chessboard == null) {
            return "unknown chessboard";
        }
        if (this.chessboard == chessboard) return "you already in this chessboard";
        quit();
        this.chessboard = chessboard;
        this.chessColorEnum = this.chessboard.pickUpColor();
        return chessboard.print();
    }

    public String quit() {
        if (this.chessboard == null) {
            return "quit success";
        }
        chessboard.putDownColor(this.chessColorEnum);
        chessboard = null;
        return "quit success";
    }

    public String drop(String assemblyXY) {
        if (this.chessboard == null) return "please create/join a chessboard firstly";
        return this.chessboard.put(assemblyXY, this.chessColorEnum);
    }

}
