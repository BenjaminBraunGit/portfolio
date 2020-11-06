package GameBitBoard;

public class BitB {

    static final int HEIGHT = 6;
    static final int WIDTH = 7;

    int current_position;
    int mask;
    int moves;

    public BitB(int cp, int m, int mn) {
        current_position = cp;
        mask = m;
        moves = mn;
    }

    public boolean canPlay(int col) {
        return (mask & top_mask(col)) == 0;
    }

    public void play(int col) {
        current_position ^= mask;
        mask |= mask + bottom_mask(col);
        moves++;
    }

    public boolean isWinningMove(int col) {
        int pos = current_position;
        pos |= (mask + bottom_mask(col)) & column_mask(col);
        return alignment(pos);
    }

    public int numMoves() {
        return moves;
    }

    public int key() {
        return current_position + mask;
    }

    public BitB copy() {
        return new BitB(current_position, mask, moves);
    }

    private static boolean alignment(int pos) {
        //horizontal
        int m = pos & (pos >>> (HEIGHT + 1));
        if ((m & (m >>> (2*(HEIGHT + 1)))) != 0) return true;

        m = pos & (pos >>> HEIGHT);
        if ((m & (m >>> (2*HEIGHT))) != 0) return true;

        m = pos & (pos >>> (HEIGHT + 2));
        if ((m & (m >>> (2*(HEIGHT + 2)))) != 0) return true;

        //vertical
        m = pos & (pos >>> 1);
        if ((m & (m >>> 2)) != 0) return true;

        return false;
    }

    private static int top_mask(int col) {
        return (1 << (HEIGHT - 1)) << (col*(HEIGHT + 1));
    }

    private static int bottom_mask(int col) {
        return (1 << (HEIGHT + 1));
    }

    private static int column_mask(int col) {
        return ((1 << HEIGHT) - 1) << (col*(HEIGHT + 1));
    }
}
