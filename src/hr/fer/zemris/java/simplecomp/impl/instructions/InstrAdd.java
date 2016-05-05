package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class implements the {@linkplain AbstractInstrArithmeticOperation} and
 * represents a computer instruction that is used for adding two register number
 * values together.
 * <p>
 * Instruction takes a total of <tt>3</tt> {@linkplain InstructionArgument}s.
 * All 3 instruction arguments must be regular non-indirect registers.
 * <p>
 * Upon instruction execution it adds the first operand specified by the
 * argument at index <tt>1</tt> to the second operand specified by the argument
 * at index <tt>2</tt>, storing the result to the argument specified at index
 * <tt>3</tt>.
 *
 * @author Mario Bobic
 * @see InstrMul
 */
public class InstrAdd extends AbstractInstrArithmeticOperation {

	/**
	 * Constructs an instance of {@code InstrAdd} with the specified
	 * {@link InstructionArgument arguments}.
	 * <p>
	 * Throws an {@linkplain IllegalArgumentException} if the number of
	 * arguments is different from <tt>3</tt> or all of the arguments are not
	 * regular non-indirect registers.
	 * 
	 * @param arguments arguments of this instruction
	 * @throws IllegalArgumentException if argument size or registers are invalid
	 */
	public InstrAdd(List<InstructionArgument> arguments) {
		super(arguments, "Add");
	}

	@Override
	protected int apply(int n1, int n2) {
		return n1 + n2;
	}

}
