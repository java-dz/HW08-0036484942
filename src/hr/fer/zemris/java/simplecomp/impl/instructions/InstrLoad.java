package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class represents a computer instruction that is used for loading a value
 * from the specified memory location to the register specified by its index.
 * <p>
 * Instruction takes a total of <tt>2</tt> {@linkplain InstructionArgument}s.
 * The first instruction argument must be a regular non-indirect register, while
 * the second instruction argument must be a number (a memory location).
 * <p>
 * Upon instruction execution, a value is obtained from the specified memory
 * location and that value is set as the register value of the specified
 * register.
 *
 * @author Mario Bobic
 */
public class InstrLoad extends AbstractInstruction {

    /** Register whose value is to be set upon loading. */
    private int registerIndex;
    /** Memory location from where to load the value. */
    private int memoryLocation;

    /**
     * Constructs an instance of {@code InstrLoad} with the specified
     * {@link InstructionArgument arguments}.
     * <p>
     * Throws an {@linkplain IllegalArgumentException} if the number of
     * arguments is different from <tt>2</tt>, the argument at index <tt>0</tt>
     * is not a regular non-indirect register or the argument at index
     * <tt>1</tt> is not a number (location).
     *
     * @param arguments arguments of this instruction
     * @throws IllegalArgumentException if argument size or arguments are invalid
     */
    public InstrLoad(List<InstructionArgument> arguments) {
        super("Load");
        checkSize(arguments, 2);

        checkNonIndirectRegister(arguments.get(0), 0);
        checkNumber(arguments.get(1), 1);

        registerIndex = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
        memoryLocation = (Integer) arguments.get(1).getValue();
    }

    @Override
    public boolean execute(Computer computer) {
        Object value = computer.getMemory().getLocation(memoryLocation);
        computer.getRegisters().setRegisterValue(registerIndex, value);

        return false;
    }

}
