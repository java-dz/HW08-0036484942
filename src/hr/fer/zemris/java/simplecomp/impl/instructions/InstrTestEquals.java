package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class represents a computer instruction that is used for testing the
 * equality of contents of two non-indirect registers.
 * <p>
 * Instruction takes a total of <tt>2</tt> {@linkplain InstructionArgument}s.
 * Both instruction arguments must be regular non-indirect registers.
 * <p>
 * Upon instruction execution, the value is extracted from both registers and is
 * compared to one another. If the equality test determined by the
 * {@linkplain Object#equals(Object)} method returns <tt>true</tt>, the flag is
 * set to <tt>true</tt>. Else the flag is set to <tt>false</tt>.
 *
 * @author Mario Bobic
 */
public class InstrTestEquals extends AbstractInstruction {

	/** The index of the first register. */
	private int registerIndex1;
	/** The index of the second register. */
	private int registerIndex2;
	
	/**
	 * Constructs an instance of {@code InstrTestEquals} with the specified
	 * {@link InstructionArgument arguments}.
	 * <p>
	 * Throws an {@linkplain IllegalArgumentException} if the number of
	 * arguments is different from <tt>2</tt> or both registers are not
	 * regular non-indirect registers.
	 * 
	 * @param arguments arguments of this instruction
	 * @throws IllegalArgumentException if argument size or registers are invalid
	 */
	public InstrTestEquals(List<InstructionArgument> arguments) {
		super("TestEquals");
		checkSize(arguments, 2);
		
		checkNonIndirectRegisterList(arguments);
		
		this.registerIndex1 = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.registerIndex2 = RegisterUtil.getRegisterIndex((Integer) arguments.get(1).getValue());
	}
	
	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(registerIndex1);
		Object value2 = computer.getRegisters().getRegisterValue(registerIndex2);
		
		computer.getRegisters().setFlag(value1.equals(value2));
		
		return false;
	}

}
