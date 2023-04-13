package go;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @since: 2023/4/10.
 * @Author: LiuXinjie
 */
public class Player {

    private String name;

    private Chessboard chessboard;

    private ChessColorEnum chessColorEnum;

    private BlockingQueue<String> announcementQueue = new LinkedBlockingDeque<>();

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
        //todo 写的很烂 有时间可以整理下这部分逻辑
        chessboard.getPlayers().add(this);
        return chessboard.print();
    }

    public String quit() {
        if (this.chessboard == null) {
            return "quit success";
        }
        chessboard.putDownColor(this.chessColorEnum);
        chessboard.getPlayers().remove(this);
        chessboard = null;
        return "quit success";
    }

    public String drop(String assemblyXY) {
        if (this.chessboard == null) return "please create/join a chessboard firstly";
        return this.chessboard.put(assemblyXY, this.chessColorEnum);
    }

    public String getNewestAnnouncement() {
        return announcementQueue.poll();
    }

    public boolean putNewestAnnouncement(String content) {
        return announcementQueue.offer(content);
    }

    public Chessboard getChessboard() {
        return chessboard;
    }
}
