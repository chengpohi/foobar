package exercise.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class NumberFormatError extends Exception {
}

class NumberRangeError extends Exception {
}

class CommandFormatError extends Exception {
}

class MazeFormatError extends Throwable {
}

class Points {
    public final Point p1;
    public final Point p2;

    Points(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public String toString() {
        return p1.toString() + " " + p2.toString();
    }
}


class Point {
    public final int x;
    public final int y;

    public Point(String x, String y) throws NumberFormatError {
        try {
            this.x = Integer.parseInt(x);
            this.y = Integer.parseInt(y);
        } catch (Exception e) {
            throw new NumberFormatError();
        }
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}

public class Main {
    public static final String W = "[W]";
    public static final String R = "[R]";
    public static Pattern pattern = Pattern.compile("\\d+,\\d+ \\d+,\\d+");

    public static void main(String[] args) {
        // 输入矩阵的维数
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入矩阵的行:");
        int m = sc.nextInt();
        System.out.println("请输入矩阵的列:");
        int n = sc.nextInt();
        String[][] maze = initMaze(m, n);
        //输入的字符串
        Scanner scc = new Scanner(System.in);
        System.out.println("请输入相应的字符串:");
        String pairStr = scc.nextLine();

        List<Points> points = null;

        try {
            points = getJoinPoints(pairStr);
            for (Points point : points) {
                join(maze, point);
            }

            print(maze);
        } catch (MazeFormatError mazeFormatError) {
            System.out.println("Maze format error");
        } catch (NumberFormatError numberFormatError) {
            System.out.println("Invalid number format");
        } catch (NumberRangeError numberRangeError) {
            System.out.println("Number out of range");
        } catch (CommandFormatError commandFormatError) {
            System.out.println("Incorrect command format");
        }
    }

    private static void join(String[][] maze, Points points) {
        int x1 = 2 * points.p1.x + 1;
        int x2 = 2 * points.p2.x + 1;
        int y1 = 2 * points.p1.y + 1;
        int y2 = 2 * points.p2.y + 1;
        int x = (x1 + x2) / 2;
        int y = (y1 + y2) / 2;
        maze[x][y] = R;
    }

    private static List<Points> getJoinPoints(String pairStr) throws MazeFormatError, NumberFormatError, NumberRangeError, CommandFormatError {
        List<Points> joinList = new ArrayList<>();
        String[] pairs = pairStr.split(";");
        for (String pair : pairs) {
            if (!checkCommandFormat(pair)) {
                throw new CommandFormatError();
            }

            String[] twoPair = pair.split(" ");
            String pair1 = twoPair[0];
            String pair2 = twoPair[1];
            Point p1 = new Point(pair1.split(",")[0], pair1.split(",")[1]);
            Point p2 = new Point(pair2.split(",")[0], pair2.split(",")[1]);

            if (!checkMazeFormat(p1, p2)) {
                throw new MazeFormatError();
            }

            if (!checkNumberFormat(p1, p2)) {
                throw new NumberRangeError();
            }

            joinList.add(new Points(p1, p2));
        }
        return joinList;
    }

    private static boolean checkCommandFormat(String pair) {
        Matcher matcher = pattern.matcher(pair);
        return matcher.matches();
    }

    private static boolean checkNumberFormat(Point p1, Point p2) {
        if (p1.x < 0 || p1.y < 0 || p2.x < 0 || p2.y < 0) {
            return false;
        }
        return true;
    }

    private static boolean checkMazeFormat(Point p1, Point p2) {
        if ((p1.x == p2.x && Math.abs(p1.y - p2.y) == 1)
                || (p1.y == p2.y && Math.abs(p1.x - p2.x) == 1)) {
            return true;
        }
        return false;
    }

    private static String[][] initMaze(int m, int n) {
        String[][] maze = new String[2 * m + 1][2 * n + 1];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                maze[i][j] = (i % 2 == 1 && j % 2 == 1) ? R : W;
            }
        }
        return maze;
    }

    private static void print(String[][] maze) {
        int m = maze.length;
        int n = maze[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(maze[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
