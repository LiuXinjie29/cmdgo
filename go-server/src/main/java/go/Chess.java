package go;

import java.util.UUID;

/**
 * @since: 2023/4/10.
 * @Author: LiuXinjie
 */
public class Chess {

    int xAxis;

    int yAxis;

    ChessColorEnum color;

    /**
     * 用于标记当前属于的相邻组
     */
    private String aroundTag;

    static Chess build(int x, int y, ChessColorEnum color) {
        Chess chess = new Chess();
        chess.xAxis = x;
        chess.yAxis = y;
        chess.color = color;
        return chess;
    }

    String getChessColor() {
        return this.color.getCharacter();
    }

    public int getxAxis() {
        return xAxis;
    }

    public int getyAxis() {
        return yAxis;
    }

    public String getPointString() {
        return xAxis + "," + yAxis;
    }

    public void setAroundTag(String aroundTag) {
        this.aroundTag = aroundTag;
    }

    public String getAroundTag() {
        return aroundTag;
    }

}
