package pt.up.fe.comp.fsa;

public class NoSuchNodeException extends FSAException {
    public NoSuchNodeException(String message) {
        super(message);
    }

    private static final long serialVersionUID = -4798645704148787131L;
}
