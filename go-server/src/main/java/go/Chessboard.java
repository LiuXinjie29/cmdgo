package go;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;

/**
 * @since: 2023/4/10.
 * @Author: LiuXinjie
 */
public class Chessboard {

    private final static String NULL_CONTENT = "+";

    private final static String CHESSBOARD_SEPARATOR = "  ";

    private final static String XY_SEPARATOR = ",";



    private Integer length;

    private Integer width;

    private String[][] chessboard;

    static Chessboard build(Integer length, Integer width) {
        Chessboard chessboard = new Chessboard();
        chessboard.length = length;
        chessboard.width = width;
        initChessboard(chessboard);
        return chessboard;
    }

    private static void initChessboard(Chessboard chessboard) {
        chessboard.chessboard = new String[chessboard.length][chessboard.width];
        for (int i = 0; i < chessboard.chessboard.length; i++) {
            Arrays.fill(chessboard.chessboard[i], NULL_CONTENT);
        }
    }

    static Chessboard buildQuadrate(Integer length) {
        return build(length, length);
    }

    String put(String assemblyXY, Chess chess) {
        String[] split = assemblyXY.split(XY_SEPARATOR);
        if (split.length != 2) throw new RuntimeException("wrong axis string");
        return put(split[0], split[1], chess);
    }

    String put(String x, String y, Chess chess) {
        int xAxis = (char)(x.charAt(0) - 'A');
        int yAxis = Integer.parseInt(y);
        if (!chessboard[xAxis][yAxis].equals(NULL_CONTENT)) throw new RuntimeException("the axis already exists a chess");
        chessboard[xAxis][yAxis] = chess.getCharacter();
        return this.print();
    }

    /**
     * 展示棋盘当前状态
     *
     * @return
     */
    public String print() {
        StringJoiner content = new StringJoiner("\n");
        StringJoiner xAxis = new StringJoiner(CHESSBOARD_SEPARATOR);
        //创建X轴坐标和内容
        xAxis.add(CHESSBOARD_SEPARATOR);
        for (int i = 0; i < this.length; i++) {
            xAxis.add(Character.toString((char) ('A' + i)));
        }
        xAxis.add(CHESSBOARD_SEPARATOR);

        StringJoiner lengthStr;
        ArrayList<String> lengthStrList = new ArrayList<>(width);
        for (int i = 0; i < this.width; i++) {
            lengthStr = new StringJoiner(CHESSBOARD_SEPARATOR);
            for (int j = 0; j < this.length; j++) {
                lengthStr.add(chessboard[j][i]);
            }
            lengthStrList.add(lengthStr.toString());
        }

        //创建Y轴坐标 && 将内容拼接
        content.add(xAxis.toString());
        StringJoiner temp;
        for (int i = 0; i < this.width; i++) {
            temp = new StringJoiner(CHESSBOARD_SEPARATOR);
            temp.add(beautyNumber(i));
            temp.add(lengthStrList.get(i));
            temp.add(beautyNumber(i));
            content.add(temp.toString());
            temp = null;
        }
        content.add(xAxis.toString());
        return content.toString();
    }

    private String beautyNumber(int i) {
        if (i < 10) {
            return "0" + String.valueOf(i);
        }
        return String.valueOf(i);
    }


}
