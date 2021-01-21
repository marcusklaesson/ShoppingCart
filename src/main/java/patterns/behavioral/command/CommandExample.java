package patterns.behavioral.command;

import java.util.Stack;

interface Command {
    void execute();
}

class UndoSecondThing implements Command {

    @Override
    public void execute() {
        System.out.println("Undoing second thing");
    }
}

class UndoFirstThing implements Command {

    @Override
    public void execute() {
        System.out.println("Undoing first thing");
    }
}


public class CommandExample {

    static Stack<Command> undoStack = new Stack<>();

    public static void main(String[] args) {
        doFirstThing();

        System.out.println("Doing second thing");
        undoStack.push(new UndoSecondThing());

        //Ctrl-Z :) Undo
        undoStack.pop().execute();

        System.out.println("Doing a third thing");
        undoStack.push(() -> {
            System.out.println("Undoing third thing");
        });

        undoStack.pop().execute();
        undoStack.pop().execute();

    }

    private static void doFirstThing() {
        System.out.println("Doing first thing");
        undoStack.push(new UndoFirstThing());
    }
}
