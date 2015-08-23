/**
 * @author karthik rao
 * @version 1.0
 * Huffman Coding EC homework
 * Summer 2015
 * CS 1332
 */

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class HuffmanTest {

    private HuffmanEncoder coder;
    private String message, encodedMessage;
    private Map<Character, Integer> freqmap;
    private Map<Character, String> binarymap;

    @Before
    public void setup() {
        coder = new HuffmanEncoder();
        message = "GGCATTTAGGGGCCATX";
        //message = "HELLO MY NAME IS KARTHIK";
    }

    @Test
    public void testEncode() {
        encodedMessage = coder.encode(message);
        System.out.println("Encoded message: " + encodedMessage);
        System.out.println("Encoded bit string length: " + encodedMessage.length());

    }

    @Test
    public void testDecode() {
        binarymap = coder.binaryCodeGen(coder.buildTree(coder.freqMap(message)));
        System.out.println("Decoded String: " + coder.decode(binarymap, coder.encode(message)));
        System.out.println("Origin Message: " + message);
    }
}
