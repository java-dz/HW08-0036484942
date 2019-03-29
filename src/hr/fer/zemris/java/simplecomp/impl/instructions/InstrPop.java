package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * This class represents a computer instruction that is used for popping a value
 * from the stack to the register.
 * <p>
 * Instruction takes a total of <tt>1</tt> {@linkplain InstructionArgument}.
 * The instruction argument must be a regular non-indirect register.
 * <p>
 * Upon instruction execution, the stack pointer is first lowered by <tt>1</tt>,
 * that is, the memory location it points to is raised by <tt>1</tt>. Then the
 * value is extracted from the stack and is pushed into the specified register.
 *
 * @author Mario Bobic
 * @see InstrPush
 */
public class InstrPop extends AbstractInstruction {

    /** The index of the register to which the value will be popped. */
    private int registerIndex;

    /**
     * Constructs an instance of {@code InstrPop} with the specified
     * {@link InstructionArgument arguments}.
     * <p>
     * Throws an {@linkplain IllegalArgumentException} if the number of
     * arguments is different from <tt>1</tt> or the argument is not a
     * regular non-indirect register.
     *
     * @param arguments arguments of this instruction
     * @throws IllegalArgumentException if argument size or register is invalid
     */
    public InstrPop(List<InstructionArgument> arguments) {
        super("Pop");
        checkSize(arguments, 1);

        checkNonIndirectRegister(arguments.get(0), 0);
        registerIndex = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
    }

    @Override
    public boolean execute(Computer computer) {
        Object value = pop(computer);
        computer.getRegisters().setRegisterValue(registerIndex, value);

        return false;
    }

    /**
     * Does the <i>popping</i> work of the instruction. Pops and returns the
     * value from the stack register of the specified <tt>computer</tt>.
     *
     * @param computer computer that holds the stack register
     * @return the value that is popped from the stack
     */
    protected static Object pop(Computer computer) {
        int location = (Integer) computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX);
        // move stack pointer for 1 address up
        computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, ++location);

        return computer.getMemory().getLocation(location);
    }

}
