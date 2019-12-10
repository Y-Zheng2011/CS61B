

public class BubbleGrid {
    int[][] bubbles;
    UnionFind uF;
    int width, height, size;
    int[][] index;

    public BubbleGrid(int[][] input) {
        bubbles = input;
        height = input.length;
        width = input[0].length;
        int k = 0;
        index = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                index[i][j] = k;
                k++;
            }
        }
        size = k;
    }

    private void init() {
        uF = new UnionFind(size);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width-1; j++) {
                if (bubbles[i][j] == 1 && bubbles[i][j + 1] == 1) {
                    uF.union(index[i][j],index[i][j+1]);
                }
            }
        }
        for (int j = 0; j < width; j++) {
            for (int i = 0; i < height-1; i++) {
                if (bubbles[i][j] == 1 && bubbles[i+1][j] == 1) {
                    uF.union(index[i][j],index[i+1][j]);
                }
            }
        }
    }

    private int pop(int row, int col) {
        if (bubbles[row][col]==0) {
            return 0;
        }
        bubbles[row][col] = 0;
        init();
        int count = 0;
        for (int i = 1; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (bubbles[i][j] == 1) {
                    boolean flag = false;
                    for (int k = 0; k < width; k++) {
                        flag = flag || (uF.connected(index[i][j],index[0][j]));
                    }
                    if (!flag) count++;
                }
            }
        }
        return count;
    }

    public int[] popBubbles(int[][] darts) {
        int[] ret = new int[darts.length];
        for (int i = 0; i < darts.length; i++) {
            ret[i] = pop(darts[i][0],darts[i][1]);
        }
        return ret;
    }

    public static void main(String[] args) {
        int[][] input = {{1, 1, 0}, {1, 0, 0}, {1, 1, 0}, {1, 1, 1}},
                darts = {{2, 2}, {2, 0}};
        BubbleGrid bg = new BubbleGrid(input);
        int[] t = bg.popBubbles(darts);
        for (int i : t) {
            System.out.print(i+" ");
        }
    }
}
