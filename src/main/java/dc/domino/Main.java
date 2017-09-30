package dc.domino;

import dc.domino.error.ParserException;
import dc.domino.parser.Parser;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ParserException {
        System.out.println(
                new Parser(new File(Main.class.getClassLoader().getResource("test.csv").getFile()),
                        true).parseFile());
    }
}
