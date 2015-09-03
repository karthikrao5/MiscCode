import java.util.Random;

public class Organism {

    private Gene[] genome;
    private Random rand;
    private StringBuilder sb;

    private class Gene {
        private byte one;
        private byte two;

        public Gene(byte one, byte two) {
            this.one = one;
            this.two = two;
        }

        @Override
        public String toString() {
            return "Gene: " + one + " " + two;
        }
    }

    public Organism() {
        rand = new Random();
        genome = new Gene[15];

        for(int i = 0; i < genome.length; i++) {
            genome[i] = new Gene((byte) Math.round(Math.random()),
                    (byte) Math.round(Math.random()));
        }
    }

    @Override
    public String toString() {
        sb = new StringBuilder();
        for(int i = 0; i < genome.length; i++) {
            sb.append(genome[i].toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public Gene getGenes(int index) {
        return genome[index];
    }
}
