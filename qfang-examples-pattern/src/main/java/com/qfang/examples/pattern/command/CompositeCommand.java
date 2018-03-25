package com.qfang.examples.pattern.command;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-03-10
 * @since: 1.0
 */
public abstract class CompositeCommand implements TvCommand {

    private final List<TvCommand> commands = new LinkedList<>();

    public void addCommand(TvCommand command) {
        commands.add(command);
    }

    public void removeCommand(TvCommand command) {
        Iterator<TvCommand> it = commands.iterator();
        while (it.hasNext()) {
            TvCommand c = it.next();
            if(c.equals(command)) {
                it.remove();
                return;
            }
        }
    }

    @Override
    public void execute(final Tv tv) {
        commands.forEach(tvCommand -> tvCommand.execute(tv));
    }
}
