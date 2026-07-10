package com.disia.game.minigames.labyrinth;

import java.util.Random;

/**
 * Labyrinthe généré par recursive backtracker (DFS).
 *
 * Représentation interne : grille de taille (2*cols-1) x (2*rows-1).
 *   - grid[2c][2r]   = true  → cellule (c, r) est praticable
 *   - grid[2c+dx][2r+dy] = true → passage ouvert vers le voisin (dx, dy)
 */
public class Maze {

    public final int cols;
    public final int rows;
    private final boolean[][] grid;

    public Maze(int cols, int rows, long seed) {
        this.cols = cols;
        this.rows = rows;
        this.grid = new boolean[2 * cols - 1][2 * rows - 1];
        generate(new Random(seed));
    }

    private void generate(Random rng) {
        for (int c = 0; c < cols; c++)
            for (int r = 0; r < rows; r++)
                grid[2 * c][2 * r] = true;

        carve(0, 0, new boolean[cols][rows], rng);
    }

    private void carve(int c, int r, boolean[][] visited, Random rng) {
        visited[c][r] = true;
        int[][] dirs = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        shuffle(dirs, rng);

        for (int[] d : dirs) {
            int nc = c + d[0], nr = r + d[1];
            if (nc >= 0 && nc < cols && nr >= 0 && nr < rows && !visited[nc][nr]) {
                grid[c + nc][r + nr] = true; // ouvre le mur entre (c,r) et (nc,nr)
                carve(nc, nr, visited, rng);
            }
        }
    }

    private void shuffle(int[][] a, Random rng) {
        for (int i = a.length - 1; i > 0; i--) {
            int j = rng.nextInt(i + 1);
            int[] tmp = a[i]; a[i] = a[j]; a[j] = tmp;
        }
    }

    /** True si le déplacement (dx, dy) depuis la cellule (cellX, cellY) est possible. */
    public boolean canMove(int cellX, int cellY, int dx, int dy) {
        int gx = 2 * cellX + dx;
        int gy = 2 * cellY + dy;
        if (gx < 0 || gx >= 2 * cols - 1 || gy < 0 || gy >= 2 * rows - 1) return false;
        return grid[gx][gy];
    }
}
