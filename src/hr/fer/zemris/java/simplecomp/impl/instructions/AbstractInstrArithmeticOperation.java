package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class is an abstract representation of the arithmetic operation computer
 * instruction. It contains common methods, constructors, variables and classes
 * used by the concrete arithmetic operations.
 * <p>
 * This class currently supports only the addition and multiplication, but can
 * easily be modified to support more arithmetic operations.
 *
 * @author Mario Bobic
 */
public abstract class AbstractInstrArithmeticOperation extends AbstractInstruction {

	/** The register index to where the operation result is stored. */
	private int registerIndex1;
	/** The register index of the first operand. */
	private int registerIndex2;
	/** The register index of the second operand. */
	private int registerIndex3;
	
	/**
	 * Constructs an instance of {@code AbstractInstrArithmeticOperation} with
	 * the specified {@link InstructionArgument arguments} and the subclass
	 * instruction name.
	 * <p>
	 * Throws an {@linkplain IllegalArgumentException} if the number of
	 * arguments is different from <tt>3</tt> or all of the arguments are not
	 * regular non-indirect registers.
	 * 
	 * @param arguments arguments of this instruction
	 * @param instrName name of the instruction
	 * @throws IllegalArgumentException if argument size or registers are invalid
	 */
	protected AbstractInstrArithmeticOperation(List<InstructionArgument> arguments, String instrName) {
		super(instrName);
		checkSize(arguments, 3);
		
		checkNonIndirectRegisterList(arguments);
		
		this.registerIndex1 = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.registerIndex2 = RegisterUtil.getRegisterIndex((Integer) arguments.get(1).getValue());
		this.registerIndex3 = RegisterUtil.getRegisterIndex((Integer) arguments.get(2).getValue());
	}
	
	/**
	 * Executes the arithmetic operation using the method
	 * {@linkplain #apply(int, int)} and storing the result as:
	 * <blockquote>register1 = register2 op register 3</blockquote>
	 * 
	 * @param computer computer that holds the registers
	 * @return false
	 */
	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(registerIndex2);
		Object value2 = computer.getRegisters().getRegisterValue(registerIndex3);
		
		computer.getRegisters().setRegisterValue(
				registerIndex1,
				apply((Integer) value1, (Integer) value2)
		);
		
		return false;
	}
	
	/**
	 * Applies the arithmetic operation on the two operands <tt>n1</tt> and
	 * <tt>n2</tt> and returns the result of the operation.
	 * 
	 * @param n1 operand 1
	 * @param n2 operand 2
	 * @return the result of the operation
	 */
	protected abstract int apply(int n1, int n2);

}
