package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class represents a computer instruction that is used for jumping to a
 * memory location specified by the instruction argument.
 * <p>
 * Instruction takes a total of <tt>1</tt> {@linkplain InstructionArgument}.
 * The instruction argument must be a number (a memory location).
 * <p>
 * Upon instruction execution, the program counter is unconditionally set to the
 * specified memory location.
 *
 * @author Mario Bobic
 * @see InstrJumpIfTrue
 */
public class InstrJump extends AbstractInstruction {

    /** The memory location to which the program counter is to be set to. */
    private int memoryLocation;

    /**
     * Constructs an instance of {@code InstrJump} with the specified
     * {@link InstructionArgument arguments}.
     * <p>
     * Throws an {@linkplain IllegalArgumentException} if the number of
     * arguments is different from <tt>1</tt> or the argument is not a
     * number (location).
     *
     * @param arguments arguments of this instruction
     * @throws IllegalArgumentException if argument size or number is invalid
     */
    public InstrJump(List<InstructionArgument> arguments) {
        super("Jump");
        checkSize(arguments, 1);

        checkNumber(arguments.get(0), 0);

        memoryLocation = (Integer) arguments.get(0).getValue();
    }

    @Override
    public boolean execute(Computer computer) {
        computer.getRegisters().setProgramCounter(memoryLocation);

        return false;
    }

}
