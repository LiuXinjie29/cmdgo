package server.command;

import go.Player;

/**
 * @since: 2023/4/11.
 * @Author: LiuXinjie
 */
public abstract class AbstractCommandExecutor implements CommandExecutor {

    protected Player player;

    private final String commandHead;

    protected AbstractCommandExecutor(Player player, String commandHead) {
        this.player = player;
        this.commandHead = commandHead;
    }

    @Override
    public String execute(String command) {
        String[] commandArray = command.split(COMMAND_SEPARATOR);

        if (!commandHead.equals(commandArray[0])) {
            String unknown = "unknown command";
            player.putNewestAnnouncement(unknown);
            return unknown;
        }
        String result = doExecute(commandArray[1]);
        if (player.getChessboard() != null) {
            for (Player curPlayer : player.getChessboard().getPlayers()) {
                curPlayer.putNewestAnnouncement(result);
            }
        } else {
            player.putNewestAnnouncement(result);
        }

        return result;
    }

    protected abstract String doExecute(String commandContent);

}
