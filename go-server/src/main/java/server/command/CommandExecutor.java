package server.command;

/**
 * @since: 2023/4/11.
 * @Author: LiuXinjie
 */
public interface CommandExecutor {

    String JOIN = "join";

    String DROP = "drop";

    String CREATE = "create";

    String UNKNOWN = "UNKNOWN";


    String COMMAND_SEPARATOR = ":";

    String execute(String command);

}
