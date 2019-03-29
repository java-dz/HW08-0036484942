package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class represents a computer instruction that is used for reading the
 * user input from keyboard and interpreting it as an integer.
 * <p>
 * Instruction takes a total of <tt>1</tt> {@linkplain InstructionArgument}.
 * The instruction argument must be a number (a memory location).
 * <p>
 * Upon instruction execution, the program gets blocked due to trying to read a
 * line.
 * <p>
 * If the entered value can not be interpreted as an integer, the computer
 * register flag is set to false to indicate that another reading is needed.
 * <p>
 * If the {@linkplain BufferedReader#readLine() reader.readLine()} method
 * returns <tt>null</tt>, an {@linkplain IllegalStateException} is thrown as the
 * input stream reached the end or is closed.
 *
 * @author Mario Bobic
 */
public class InstrIinput extends AbstractInstruction {

    /** The memory location to which integer is stored. */
    private int memoryLocation;

    /**
     * Constructs an instance of {@code InstrIinput} with the specified
     * {@link InstructionArgument arguments}.
     * <p>
     * Throws an {@linkplain IllegalArgumentException} if the number of
     * arguments is different from <tt>1</tt> or the argument is not a
     * number (location).
     *
     * @param arguments arguments of this instruction
     * @throws IllegalArgumentException if argument size or number is invalid
     */
    public InstrIinput(List<InstructionArgument> arguments) {
        super("Iinput");
        checkSize(arguments, 1);

        checkNumber(arguments.get(0), 0);
        memoryLocation = (Integer) arguments.get(0).getValue();
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalStateException if the input stream is closed
     */
    @Override
    public boolean execute(Computer computer) {
        try {
            // The input stream must NOT be closed.
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            String line = reader.readLine();
            if (line == null) {
                throw new IllegalStateException("Input stream closed.");
            }

            int num = Integer.parseInt(line);

            computer.getMemory().setLocation(memoryLocation, num);
            computer.getRegisters().setFlag(true);
        } catch (IOException | NumberFormatException e) {
            computer.getRegisters().setFlag(false);
        }

        return false;
    }

}
