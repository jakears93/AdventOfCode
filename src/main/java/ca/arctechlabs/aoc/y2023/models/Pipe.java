package ca.arctechlabs.aoc.y2023.models;

public enum Pipe {
    NS('|', PipeDirection.NORTH, PipeDirection.SOUTH),
    EW('-', PipeDirection.EAST, PipeDirection.WEST),
    NE('L', PipeDirection.NORTH, PipeDirection.EAST),
    NW('J', PipeDirection.NORTH, PipeDirection.WEST),
    SW('7', PipeDirection.SOUTH, PipeDirection.WEST),
    SE('F', PipeDirection.SOUTH, PipeDirection.EAST),
    INVALID('.', PipeDirection.INVALID, PipeDirection.INVALID),
    START('S', PipeDirection.INVALID, PipeDirection.INVALID);


    private final char value;
    private final PipeDirection entry;
    private final PipeDirection exit;

    Pipe(char value, PipeDirection entry, PipeDirection exit) {
        this.value = value;
        this.entry = entry;
        this.exit = exit;
    }

    public static Pipe fromValue(char value){
        Pipe[] var1 = values();
        int var2 = var1.length;

        for (Pipe b : var1) {
            if (b.value == value) {
                return b;
            }
        }
        return INVALID;
    }

    public PipeDirection getEntry() {
        return entry;
    }

    public PipeDirection getExit() {
        return exit;
    }
}
