package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class represents a computer instruction that is used for halting the
 * processor, that is stopping the computer program.
 * <p>
 * Instruction takes a total of <tt>0</tt> {@linkplain InstructionArgument}s.
 * <p>
 * Upon instruction execution, a simple <tt>true</tt> boolean value is returned
 * to indicate that the processor needs to be stopped.
 *
 * @author Mario Bobic
 */
public class InstrHalt extends AbstractInstruction {

	/**
	 * Constructs an instance of {@code InstrHalt} with the specified
	 * {@link InstructionArgument arguments}.
	 * <p>
	 * Throws an {@linkplain IllegalArgumentException} if the number of
	 * arguments is different from <tt>0</tt>.
	 * 
	 * @param arguments arguments of this instruction
	 * @throws IllegalArgumentException if argument size is invalid
	 */
	public InstrHalt(List<InstructionArgument> arguments) {
		super("Halt");
		checkSize(arguments, 0);
	}
	
	@Override
	public boolean execute(Computer computer) {
		return true;
	}

}
