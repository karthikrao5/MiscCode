
public class Organism {

    private Gene[] genome;

    private class Gene {
        private byte one;
        private byte two;

        public Gene(byte one, byte two) {
            this.one = one;
            this.two = two;
        }
    }

    public Organism() {
        genome = new Gene[30];
    }

}
