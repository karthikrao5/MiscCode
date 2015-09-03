
public class Traversal {

    private Maze maze;

    public Traversal() {
        maze = new Maze();
    }

    public void traverse() {

        if(Math.random() > 0.5) {
            maze.addRight();
        } else {
            maze.addLeft();
        }
    }
}
