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
 * Upon instruction execution, the computer register flag is first checked. If
 * the flag is <tt>true</tt> (if it is set to <tt>1</tt>), the program counter
 * is set to the specified memory location.
 *
 * @author Mario Bobic
 * @see InstrJumpIfTrue
 */
public class InstrJumpIfTrue extends InstrJump {

	/**
	 * Constructs an instance of {@code InstrJumpIfTrue} with the specified
	 * {@link InstructionArgument arguments}.
	 * <p>
	 * Throws an {@linkplain IllegalArgumentException} if the number of
	 * arguments is different from <tt>1</tt> or the argument is not a
	 * number (location).
	 * 
	 * @param arguments arguments of this instruction
	 * @throws IllegalArgumentException if argument size or number is invalid
	 */
	public InstrJumpIfTrue(List<InstructionArgument> arguments) {
		super(arguments);
	}

	@Override
	public boolean execute(Computer computer) {
		boolean flag = computer.getRegisters().getFlag();
		if (flag) {
			super.execute(computer);
		}
		
		return false;
	}

}
