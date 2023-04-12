package server.command;

import go.Player;

/**
 * @since: 2023/4/11.
 * @Author: LiuXinjie
 */
public class DropCommandExecutor extends AbstractCommandExecutor {

    protected DropCommandExecutor(Player player) {
        super(player, DROP);
    }

    @Override
    protected String doExecute(String commandContent) {
        return this.player.drop(commandContent);
    }

}
