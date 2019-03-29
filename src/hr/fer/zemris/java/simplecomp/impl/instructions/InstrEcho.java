package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class represents a computer instruction that is used for printing
 * contents on screen.
 * <p>
 * Instruction takes a total of <tt>1</tt> {@linkplain InstructionArgument}.
 * The instruction argument must be a register (indirect or non-indirect).
 * <p>
 * If the register is an indirect address to a memory location, the instruction
 * accesses a memory location. If not, only the object at the specified register
 * index is accessed.
 * <p>
 * This instruction takes the contents of the register if it is non-indirect or
 * the memory address to which the specified register points to if it is
 * indirect and writes the contents to the screen.
 *
 * @author Mario Bobic
 */
public class InstrEcho extends AbstractInstruction {

    /** The register specified by the argument. */
    private int registerDescriptor;

    /**
     * Constructs an instance of {@code InstrEcho} with the specified
     * {@link InstructionArgument arguments}.
     * <p>
     * Throws an {@linkplain IllegalArgumentException} if the number of
     * arguments is different from <tt>1</tt> or the argument is not a
     * register (indirect or non-indirect).
     *
     * @param arguments arguments of this instruction
     * @throws IllegalArgumentException if argument size or register is invalid
     */
    public InstrEcho(List<InstructionArgument> arguments) {
        super("Echo");
        checkSize(arguments, 1);

        registerDescriptor = (Integer) checkRegister(arguments.get(0)).getValue();
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

    @Override
    public boolean execute(Computer computer) {
        Object object;

        int registerIndex = RegisterUtil.getRegisterIndex(registerDescriptor);
        if (RegisterUtil.isIndirect(registerDescriptor)) {
            int location = RegisterUtil.getIndirectRegisterLocation(computer, registerDescriptor);
            object = computer.getMemory().getLocation(location);
        } else {
            object = computer.getRegisters().getRegisterValue(registerIndex);
        }

        System.out.print(object);

        return false;
    }

}
