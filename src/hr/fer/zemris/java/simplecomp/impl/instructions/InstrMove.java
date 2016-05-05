package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class represents a computer instruction that is used for moving a value
 * from the specified register (indirect or non-indirect) or number to the
 * specified register.
 * <p>
 * Instruction takes a total of <tt>2</tt> {@linkplain InstructionArgument}s.
 * The first instruction argument must be a register (indirect or non-indirect),
 * while the second instruction argument must be either a register (indirect or
 * non-indirect) or a number constant.
 * <p>
 * Upon instruction execution, a value is obtained from the specified register
 * or number and that value is set as the register value of the specified
 * register or is stored to a memory location if the storing register is
 * indirect.
 *
 * @author Mario Bobic
 */
public class InstrMove extends AbstractInstruction {

	/** The register specified by the argument. */
	private int registerDescriptor;
	/** The second argument of the instruction. */
	private InstructionArgument argument;
	
	/**
	 * Constructs an instance of {@code InstrLoad} with the specified
	 * {@link InstructionArgument arguments}.
	 * <p>
	 * Throws an {@linkplain IllegalArgumentException} if the number of
	 * arguments is different from <tt>2</tt>, the argument at index <tt>0</tt>
	 * is not a register (indirect or non-indirect) or the argument at index
	 * <tt>1</tt> is not either a register or a number (it can not be a string).
	 * 
	 * @param arguments arguments of this instruction
	 * @throws IllegalArgumentException if argument size or arguments are invalid
	 */
	public InstrMove(List<InstructionArgument> arguments) {
		super("Move");
		checkSize(arguments, 2);
		
		InstructionArgument register = checkRegister(arguments.get(0));
		InstructionArgument argument = checkArgument(arguments.get(1));
		
		registerDescriptor = (Integer) register.getValue();
		this.argument = argument;
	}
	
	/**
	 * Checks if the specified instruction argument is a register and throws an
	 * {@linkplain IllegalArgumentException} if the test returns false.
	 * 
	 * @param argument argument to be checked
	 * @return the specified argument, if the test succeeded
	 * @throws IllegalArgumentException if the argument is not a register
	 */
	private static InstructionArgument checkRegister(InstructionArgument argument) {
		if (!argument.isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0! It must be a register.");
		}
		
		return argument;
	}
	
	/**
	 * Checks if the specified instruction argument is a register or a number
	 * and throws an {@linkplain IllegalArgumentException} if the test returns
	 * false.
	 * 
	 * @param argument argument to be checked
	 * @return the specified argument, if the test succeeded
	 * @throws IllegalArgumentException if argument is not a register or number
	 */
	private static InstructionArgument checkArgument(InstructionArgument argument) {
		if (!argument.isRegister() && !argument.isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 1! "
					+ "It must be either a register or a number.");
		}
		
		return argument;
	}
	
	@Override
	public boolean execute(Computer computer) {
		Object argumentValue = getArgumentValue(computer, argument);
		
		if (RegisterUtil.isIndirect(registerDescriptor)) {
			// it means value has to be stored into a memory location
			int location = RegisterUtil.getIndirectRegisterLocation(computer, registerDescriptor);
			computer.getMemory().setLocation(location, argumentValue);
		} else {
			// the value is stored into this register
			int registerIndex = RegisterUtil.getRegisterIndex(registerDescriptor);
			computer.getRegisters().setRegisterValue(registerIndex, argumentValue);
		}
		
		return false;
	}
	
	/**
	 * Returns the value of the instruction argument by these rules:
	 * <ul>
	 * <li>If the specified <tt>argument</tt> is a register, the
	 * {@linkplain #getRegisterValue(Computer, InstructionArgument)} method is
	 * called to obtain its value,
	 * <li>else simply the value of the specified <tt>argument</tt> is returned.
	 * </ul>
	 * 
	 * @param computer computer from which the register is obtained
	 * @param argument the instruction argument whose value is to be returned
	 * @return the value of the instruction argument
	 */
	private static Object getArgumentValue(Computer computer, InstructionArgument argument) {
		if (argument.isRegister()) {
			// get its value or the value of the address it points to
			return getRegisterValue(computer, argument);
		} else { // it is a value (it does not have to be an integers as it can be a string)
			return argument.getValue();
		}
	}
	
	/**
	 * Returns the value of the register specified by the
	 * <tt>instruction argument</tt> by these rules:
	 * <ul>
	 * <li>If the register is an indirect register, the value is returned from
	 * the memory location it points to (address+offset),
	 * <li>else the value is obtained and returned from the register itself.
	 * </ul>
	 * 
	 * @param computer computer from which the register is obtained
	 * @param argument the register whose value is to be returned
	 * @return the value that the register holds
	 */
	private static Object getRegisterValue(Computer computer, InstructionArgument argument) {
		int argumentDescriptor = (Integer) argument.getValue();
		if (RegisterUtil.isIndirect(argumentDescriptor)) {
			int location = RegisterUtil.getIndirectRegisterLocation(computer, argumentDescriptor);
			return computer.getMemory().getLocation(location);
		} else {
			int registerIndex = RegisterUtil.getRegisterIndex(argumentDescriptor);
			return computer.getRegisters().getRegisterValue(registerIndex);
		}
	}

}
