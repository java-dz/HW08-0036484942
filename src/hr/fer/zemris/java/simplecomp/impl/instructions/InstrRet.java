package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class represents a computer instruction that is used for returning from
 * a called program subroutine.
 * <p>
 * Instruction takes a total of <tt>0</tt> {@linkplain InstructionArgument}s.
 * <p>
 * Upon instruction execution, the program is being returned from the subroutine
 * by popping the value from the stack pointer, expecting to pop a number which
 * is taken as a <tt>program counter</tt>. The program counter is then set to
 * the saved program counter which was just popped.
 *
 * @author Mario Bobic
 * @see InstrCall
 */
public class InstrRet extends AbstractInstruction {
	
	/**
	 * Constructs an instance of {@code InstrRet} with the specified
	 * {@link InstructionArgument arguments}.
	 * <p>
	 * Throws an {@linkplain IllegalArgumentException} if the number of
	 * arguments is different from <tt>0</tt>.
	 * 
	 * @param arguments arguments of this instruction
	 * @throws IllegalArgumentException if argument size is invalid
	 */
	public InstrRet(List<InstructionArgument> arguments) {
		super("Ret");
		checkSize(arguments, 0);
	}

	@Override
	public boolean execute(Computer computer) {
		Object pc = InstrPop.pop(computer);
		if (!(pc instanceof Integer)) {
			throw new IllegalStateException("Return value must be a number!");
		}
		
		computer.getRegisters().setProgramCounter((Integer) pc);
		
		return false;
	}

}
