package server.command;

import go.Player;

/**
 * @since: 2023/4/12.
 * @Author: LiuXinjie
 */
public class UnknownCommandExecutor extends AbstractCommandExecutor {

    protected UnknownCommandExecutor(Player player) {
        super(player, UNKNOWN);
    }

    @Override
    protected String doExecute(String commandContent) {
        return "unknown command";
    }

}
