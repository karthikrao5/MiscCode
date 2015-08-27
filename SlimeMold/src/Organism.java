import java.util.Random;

public class Organism {

    private Gene[] genome;
    private Random rand;
    byte temp1, temp2;

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

//        for(int i = 0; i < genome.length; i++) {
//            System.out.println(genome[i].toString());
//        }
    }

}
