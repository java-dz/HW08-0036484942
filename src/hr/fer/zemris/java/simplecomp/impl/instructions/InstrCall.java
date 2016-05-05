package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class represents a computer instruction that is used for calling a
 * program subroutine.
 * <p>
 * Instruction takes a total of <tt>1</tt> {@linkplain InstructionArgument}.
 * The instruction argument must be a number (a memory location).
 * <p>
 * Upon instruction execution, the subroutine is called by pushing the current
 * <tt>program counter</tt> to stack and then setting the program counter to the
 * new memory location that being is called.
 *
 * @author Mario Bobic
 * @see InstrRet
 */
public class InstrCall extends AbstractInstruction {

	/** The memory location which is being called. */
	private int memoryLocation;
	
	/**
	 * Constructs an instance of {@code InstrCall} with the specified
	 * {@link InstructionArgument arguments}.
	 * <p>
	 * Throws an {@linkplain IllegalArgumentException} if the number of
	 * arguments is different from <tt>1</tt> or the argument is not a
	 * number (location).
	 * 
	 * @param arguments arguments of this instruction
	 * @throws IllegalArgumentException if argument size or argument is invalid
	 */
	public InstrCall(List<InstructionArgument> arguments) {
		super("Call");
		checkSize(arguments, 1);
		
		checkNumber(arguments.get(0), 0);
		
		memoryLocation = (Integer) arguments.get(0).getValue();
	}

	@Override
	public boolean execute(Computer computer) {
		Object pc = computer.getRegisters().getProgramCounter();
		InstrPush.push(computer, pc);
		
		computer.getRegisters().setProgramCounter(memoryLocation);
		
		return false;
	}

}
