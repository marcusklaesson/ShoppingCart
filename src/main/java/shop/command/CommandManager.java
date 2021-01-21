package shop.command;

import java.util.Stack;

public class CommandManager {

    private final Stack<Command> undoList = new Stack<>();
    private final Stack<Command> redoList = new Stack<>();

    public void addToUndo(Command command) {
        undoList.push(command);
    }

    public Stack<Command> getUndoList() {
        return undoList;
    }

    public Stack<Command> getRedoList() {
        return redoList;
    }
}