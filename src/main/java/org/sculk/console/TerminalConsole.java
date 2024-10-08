package org.sculk.console;

import lombok.Getter;
import net.minecrell.terminalconsole.SimpleTerminalConsole;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.sculk.Server;
import org.sculk.command.CommandSender;
import org.sculk.player.text.RawTextBuilder;

/*
 *   ____             _ _
 *  / ___|  ___ _   _| | | __
 *  \___ \ / __| | | | | |/ /
 *   ___) | (__| |_| | |   <
 *  |____/ \___|\__,_|_|_|\_\
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * @author: SculkTeams
 * @link: http://www.sculkmp.org/
 */
public class TerminalConsole extends SimpleTerminalConsole implements CommandSender {

    @Getter
    private final Server server;
    private final ConsoleThread consoleThread;

    public TerminalConsole(Server server) {
        this.server = server;
        this.consoleThread = new ConsoleThread(this);
    }

    @Override
    protected boolean isRunning() {
        return this.server.isRunning();
    }


    @Override
    public String getName() {
        return "Console";
    }

    @Override
    public void sendMessage(String message) {
        this.server.getLogger().info(message);

    }

    @Override
    public void sendMessage(RawTextBuilder textBuilder) {
        this.server.getLogger().info(textBuilder.toString());
    }

    @Override
    protected void runCommand(String s) {
        this.getServer().dispatchCommand(this, s, true);
    }

    @Override
    protected void shutdown() {
        Server.getInstance().shutdown();
    }

    public ConsoleThread getConsoleThread() {
        return this.consoleThread;
    }

    @Override
    protected LineReader buildReader(LineReaderBuilder builder) {
        builder.appName("Sculk-MP Minecraft: Bedrock Edition");
        builder.option(LineReader.Option.HISTORY_BEEP, false);
        builder.option(LineReader.Option.HISTORY_IGNORE_DUPS, true);
        builder.option(LineReader.Option.HISTORY_IGNORE_SPACE, true);
        return super.buildReader(builder);
    }
}
