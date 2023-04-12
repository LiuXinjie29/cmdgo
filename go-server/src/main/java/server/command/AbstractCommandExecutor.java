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
            return "unknown command";
        }
        return doExecute(commandArray[1]);
    }

    protected abstract String doExecute(String commandContent);

}
