package server.command;

import go.Player;

import static server.command.CommandExecutor.*;

/**
 * @since: 2023/4/11.
 * @Author: LiuXinjie
 */
public class CommandRunner {

    public static String runCommand(Player player, String command) {
        String[] split = command.split(COMMAND_SEPARATOR);
        String commandType = split[0];
        if (commandType.equals(JOIN)) {
            return new JoinCommandExecutor(player).execute(command);
        } else if (commandType.equals(DROP)) {
            return new DropCommandExecutor(player).execute(command);
        } else if (commandType.equals(CREATE)) {
            return new CreateCommandExecutor(player).execute(command);
        } else {
            return "unknown command";
        }
    }
}
