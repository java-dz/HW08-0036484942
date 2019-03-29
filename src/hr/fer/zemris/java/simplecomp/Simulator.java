package hr.fer.zemris.java.simplecomp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.ExecutionUnitImpl;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.InstructionCreator;
import hr.fer.zemris.java.simplecomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.simplecomp.parser.ProgramParser;

/**
 * A demonstration class that simulates the processor by executing the code
 * written in <i>assembly</i> language. The program expects <tt>zero</tt> or
 * <tt>one</tt> command line arguments.
 * <ul>
 * <li>If there are zero command line arguments specified, the program asks for
 * a keyboard input to obtain the <b>path</b> of the document containing
 * assembly code.
 * <li>If there is one command line argument specified, it is expected to be a
 * path to of the document containing assembly code.
 * </ul>
 * <p>
 * If the specified path does not exist, an error message is written to the
 * standard error and the program terminates.
 * <p>
 * If the specified path exists, but the document can not be parsed (i.e. an
 * invalid instruction is written, or there is invalid text in code), an error
 * message is again written to the standard error and the program terminates.
 * <p>
 * If the program is parsed normally, but an exception occurs while executing
 * (i.e. if the stack is overflown, if the program was not halted, if a
 * subroutine did not return, an indirect register contains a string instead of
 * number), a regular ending flag turns false and the stack trace is written
 * onto the standard error.
 *
 * @author Mario Bobic
 */
public class Simulator {

    /** Path to the file containing assembly code. */
    private static String path;

    /** The regular ending flag.
     * Is false if the program did not finish regularly. */
    static boolean endedRegularly;

    /**
     * Program entry point.
     *
     * @param args command line argument
     */
    public static void main(String[] args) {
        endedRegularly = false;
        readPath(args);

        // Create a computer with 256 memory locations and 16 registers.
        Computer comp = new ComputerImpl(256, 16);

        // Create an object that knows how to create instruction instances.
        InstructionCreator creator = new InstructionCreatorImpl(
                "hr.fer.zemris.java.simplecomp.impl.instructions"
        );


        /* Load the computer memory from file; create instructions
         * using the specified object for creating instructions. */
        try {
            ProgramParser.parse(path, comp, creator);
        } catch (Exception e) {
            System.err.println("Parsing error: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Create the execution unit
        ExecutionUnit exec = new ExecutionUnitImpl();

        // Print additional error message if the program did not end regularly.
        endedRegularly = exec.go(comp);
        if (!endedRegularly) {
            System.err.println("Exception occured while executing code from file " + path);
        }
    }

    /**
     * Reads the path to file (either as the command line argument or the
     * standard input stream). Terminates the program if an I/O exception occurs
     * (in this case, an error message is printed out onto the standard error).
     * <p>
     * Even though the parser would generate an exception if the file specified
     * by the path does not exist, this method prematurely checks if the file
     * exists and terminates the program (with an error message) if it does not
     * exist.
     *
     * @param args the command line argument
     */
    private static void readPath(String[] args) {
        if (args.length == 1) {
            path = args[0];
        } else {
            try {
                // The input stream must NOT be closed. (Because of InstrIinput)
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Path to file: ");

                String line = reader.readLine();
                if (line == null) {
                    throw new IOException("Reached end of input stream.");
                }

                path = line;

            } catch (IOException e) {
                System.err.println("I/O exception: " + e.getMessage());
                System.exit(1);
            }
        }

        // This method could throw an InvalidPathException on Windows. I'll let it be.
        if (!Files.exists(Paths.get(path))) {
            System.err.println("File " + path + " does not exist.");
            System.exit(2);
        }
    }

}
