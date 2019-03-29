package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class implements the {@linkplain AbstractInstrUnaryOperation} and
 * represents a computer instruction that is used for incrementing the register
 * number value by one.
 * <p>
 * Instruction takes a total of <tt>1</tt> {@linkplain InstructionArgument}.
 * The instruction argument must be a regular non-indirect register.
 * <p>
 * Upon instruction execution it increments the operand specified by the
 * argument at index <tt>0</tt>, storing the result to the same operand.
 *
 * @author Mario Bobic
 * @see InstrDecrement
 */
public class InstrIncrement extends AbstractInstrUnaryOperation {

    /**
     * Constructs an instance of {@code InstrIncrement} with the specified
     * {@link InstructionArgument arguments}.
     * <p>
     * Throws an {@linkplain IllegalArgumentException} if the number of
     * arguments is different from <tt>1</tt> or the argument is not a regular
     * non-indirect register.
     *
     * @param arguments arguments of this instruction
     * @throws IllegalArgumentException if argument size or register is invalid
     */
    public InstrIncrement(List<InstructionArgument> arguments) {
        super(arguments, "Increment");
    }

    @Override
    protected int apply(int n) {
        return n+1;
    }

}
