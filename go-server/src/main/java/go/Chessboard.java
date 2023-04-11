package go;

import java.util.*;

/**
 * @since: 2023/4/10.
 * @Author: LiuXinjie
 */
public class Chessboard {

    private final Map<String, Set<String>> qiMap = new HashMap<>();
    //邻居子map
    private final Map<String, List<Chess>> aroundMap = new HashMap<>();

    private final static String CHESSBOARD_SEPARATOR = "  ";

    private final static String XY_SEPARATOR = ",";

    private Integer length;

    private Integer width;

    private Chess[][] chessboard;

    static Chessboard build(Integer length, Integer width) {
        Chessboard chessboard = new Chessboard();
        chessboard.length = length;
        chessboard.width = width;
        initChessboard(chessboard);
        return chessboard;
    }

    private static void initChessboard(Chessboard chessboard) {
        chessboard.chessboard = new Chess[chessboard.length][chessboard.width];
        for (int i = 0; i < chessboard.length; i++) {
            for (int j = 0; j < chessboard.width; j++) {
                chessboard.chessboard[i][j] = NullChess.build(i, j);
            }
        }
    }

    static Chessboard buildQuadrate(Integer length) {
        return build(length, length);
    }

    String put(String assemblyXY, ChessColorEnum chessColorEnum) {
        String[] split = assemblyXY.split(XY_SEPARATOR);
        if (split.length != 2) throw new RuntimeException("wrong axis string");
        return put(split[0], split[1], chessColorEnum);
    }

    //todo 待把这些RuntimeException改为可控制的Exception
    String put(String x, String y, ChessColorEnum color) {
        int xAxis = (char) (x.charAt(0) - 'A');
        int yAxis = Integer.parseInt(y);
        if (!(chessboard[xAxis][yAxis] instanceof NullChess)) {
            throw new RuntimeException("the axis already exists a chess");
        }
        Chess curChess = Chess.build(xAxis, yAxis, color);
        //----------------

        List<Chess>[] aroundSortList = getAroundSortList(curChess);

        Set<String> currentQi = getQi(curChess, aroundSortList);

        if (currentQi.isEmpty() && !haveDeadEnemy(aroundSortList[2], curChess)) {
            //判断对方是否有子可提，若有，则提移除对方无气的棋子，落子
            throw new RuntimeException("you can't put chess at here");
        }

        //没有同色的邻居，新建邻居组存储，同时存储该邻居组的气
        if (aroundSortList[1].size() == 0) {
            curChess.setAroundTag(getAroundTag(curChess));
            List<Chess> aroundList = new ArrayList<>();
            aroundList.add(curChess);
            aroundMap.put(curChess.getAroundTag(), aroundList);
            //新增气
            qiMap.put(curChess.getAroundTag(), currentQi);
        } else {
            //有同色的邻居(多个邻居组合并，邻居组的气改为新落子的气)
            List<Chess> newGroup = null;
            String oldTag = null;
            for (Chess aroundChess : aroundSortList[1]) {
                if (newGroup == null) {
                    newGroup = aroundMap.get(aroundChess.getAroundTag());
                    oldTag = aroundChess.getAroundTag();
                } else if (!oldTag.equals(aroundChess.getAroundTag())) {
                    final String itemTab = aroundChess.getAroundTag();
                    //与初始化的邻居组不同，合并,注意重复删除的问题
                    List<Chess> willDel = aroundMap.get(itemTab);
                    if (willDel == null) {
                        continue;
                    }
                    for (Chess c : willDel) {
                        //变更邻居组标示
                        c.setAroundTag(oldTag);
                    }
                    newGroup.addAll(aroundMap.get(itemTab));
                    //移除被合并的邻居组和他的气
                    aroundMap.remove(itemTab);
                    qiMap.remove(itemTab);
                }
            }
            //加入当前棋子，设置气为当前气集合
            curChess.setAroundTag(oldTag);
            newGroup.add(curChess);
            qiMap.put(oldTag, currentQi);
        }

        boolean haveRemove = false;
        //异色的邻居，移除气，如果异色邻居组的气为空，则移除该邻居组的棋子(邻居可能是相连的，需要额外判断棋子是否已经被移除)
        //触发邻居组移除事件，其他棋子的邻居组获得该位置的气
        for (Chess aroundChess : aroundSortList[2]) {
            if (aroundChess instanceof NullChess) {
                //已经移除的棋子跳过
                continue;
            }
            Set<String> qiSet = qiMap.get(aroundChess.getAroundTag());
            qiSet.remove(curChess.getPointString());
            if (qiSet.isEmpty()) {
                List<Chess> willDel = aroundMap.get(aroundChess.getAroundTag());
                for (Chess d : willDel) {
                    onDeleteUpdate(d);
                }
                //移除邻居组
                aroundMap.remove(aroundChess.getAroundTag());
                //移除qi
                qiMap.remove(aroundChess.getAroundTag());
                haveRemove = true;
            }
        }
        chessboard[xAxis][yAxis] = curChess;
        return this.print();
    }

    //获取棋子的邻居并分类在长度为3的数组中，0 空白邻居，1，同色邻居，2异色邻居
    private List<Chess>[] getAroundSortList(Chess chess) {
        List<Chess>[] aroundChessList = new List[3];
        for (int i = 0; i < aroundChessList.length; i++) {
            aroundChessList[i] = new ArrayList<>(4);
        }
        Chess[] aroundChess = getAroundList(chess);
        for (Chess item : aroundChess) {
            if (item == null) continue;
            String chessColor = item.getChessColor();
            if (ChessColorEnum.NULL.getCharacter().equals(chessColor)) {
                aroundChessList[0].add(item);
            } else if (chess.getChessColor().equals(chessColor)) {
                aroundChessList[1].add(item);
            } else {
                aroundChessList[2].add(item);
            }
        }
        return aroundChessList;
    }

    //获取相邻棋子
    private Chess[] getAroundList(Chess chess) {
        Chess up = getChess(chess.getxAxis(), chess.getyAxis() + 1);
        Chess down = getChess(chess.getxAxis(), chess.getyAxis() - 1);
        Chess left = getChess(chess.getxAxis() - 1, chess.getyAxis());
        Chess right = getChess(chess.getxAxis() + 1, chess.getyAxis());
        Chess[] around = {up, down, left, right};
        return around;
    }

    //获取指定点的棋子
    private Chess getChess(int x, int y) {
        //out range
        if (x < 0 || x > 18 || y < 0 || y > 18) {
            return null;
        }
        return chessboard[x][y];
    }

    //获取当前落子的气
    private Set<String> getQi(Chess chess, List<Chess>[] aroundList) {
        Set<String> qiSet = new HashSet<>();
        for (Chess item : aroundList[0]) {
            qiSet.add(item.getPointString());
        }
        for (Chess item : aroundList[1]) {
            //相同颜色的棋子，取气的并集
            Set<String> linjuQi = qiMap.get(item.getAroundTag());
            if (linjuQi != null) {
                qiSet.addAll(linjuQi);
            }
        }
        //移除当前棋子位置的气
        qiSet.remove(chess.getPointString());

        return qiSet;
    }

    //判断落子后是否有敌方子可提，如果有，则继续
    private boolean haveDeadEnemy(List<Chess> enemyList, Chess chess) {
        for (Chess item : enemyList) {
            Set<String> qiSet = new HashSet<>();
            qiSet.addAll(qiMap.get(item.getAroundTag()));
            qiSet.remove(chess.getPointString());
            if (qiSet.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    //棋子被移除调用次方法
    private void onDeleteUpdate(Chess qizi) {
        Chess[] arounds = getAroundList(qizi);
        //颜色不同的棋子获得气
        Chess finalQizi = qizi;
        Arrays.stream(arounds).filter(item -> item != null && !ChessColorEnum.NULL.getCharacter().equals(item.getChessColor()) && !item.getChessColor().equals(finalQizi.getChessColor())).forEach(
                item -> {
                    Set<String> qiSet = qiMap.get(item.getAroundTag());
                    qiSet.add(finalQizi.getPointString());
                }
        );
        chessboard[qizi.getxAxis()][qizi.getyAxis()] = NullChess.build(qizi.getxAxis(), qizi.getyAxis());
        qizi = null;
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
                lengthStr.add(chessboard[j][i].getChessColor());
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

    //生成邻居标记
    private String getAroundTag(Chess chess) {
        return chess.getChessColor() + "_" + UUID.randomUUID();
    }

}
