package hr.fer.zemris.java.simplecomp;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class SimulatorTests {

    /* ------------------------------ Simulator program tests ------------------------------ */
    /*
     * The purpose of these tests is to check if execution of all codes
     * (asmProgram1-5 and prim1-3) ended regularly.
     */

    @Test
    public void testAsmProgram1() {
        executeMainAndAssertEndedRegularly("examples/asmProgram1.txt");
    }

    @Test
    public void testAsmProgram2() {
        executeMainAndAssertEndedRegularly("examples/asmProgram2.txt");
    }

    @Test
    public void testAsmProgram3() {
        executeMainAndAssertEndedRegularly("examples/asmProgram3.txt");
    }

    @Test
    public void testAsmProgram4() {
        executeMainAndAssertEndedRegularly("examples/asmProgram4.txt");
    }

    @Test
    public void testAsmProgram5() {
        executeMainAndAssertEndedRegularly("examples/asmProgram5.txt");
    }

    @Test
    public void testPrim1() {
        executeMainAndAssertEndedRegularly("examples/prim1.txt");
    }

    @Test
    public void testPrim2() {
        setIn("-23\n");
        executeMainAndAssertEndedRegularly("examples/prim2.txt");
    }

    @Test
    public void testPrim3() {
        setIn("6\n");
        executeMainAndAssertEndedRegularly("examples/prim3.txt");
    }


    /* ------------------------------ Utility methods ------------------------------ */

    /**
     * Executes the {@linkplain Simulator#main(String[])} program and asserts
     * that the program ended regularly.
     *
     * @param path path to assembly code file
     */
    private static void executeMainAndAssertEndedRegularly(String path) {
        Simulator.main(new String[] {path});
        Assert.assertTrue(Simulator.endedRegularly);
    }

    /**
     * Sets the standard input to the byte array input stream of the specified
     * <tt>input</tt> string.
     *
     * @param input the input string
     */
    private static void setIn(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }

}
