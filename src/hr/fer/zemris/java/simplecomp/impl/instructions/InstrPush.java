package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * This class represents a computer instruction that is used for pushing a
 * register value to the stack.
 * <p>
 * Instruction takes a total of <tt>1</tt> {@linkplain InstructionArgument}.
 * The instruction argument must be a regular non-indirect register.
 * <p>
 * Upon instruction execution, the value is extracted from the specified
 * register and is pushed onto the memory location that the stack currently
 * points to. The stack pointer is then raised by <tt>1</tt>, that is, the
 * memory location it points to is decremented by <tt>1</tt>.
 *
 * @author Mario Bobic
 * @see InstrPop
 */
public class InstrPush extends AbstractInstruction {

    /** The index of the register from which the value will be pushed. */
    private int registerIndex;

    /**
     * Constructs an instance of {@code InstrPush} with the specified
     * {@link InstructionArgument arguments}.
     * <p>
     * Throws an {@linkplain IllegalArgumentException} if the number of
     * arguments is different from <tt>1</tt> or the argument is not a
     * regular non-indirect register.
     *
     * @param arguments arguments of this instruction
     * @throws IllegalArgumentException if argument size or register is invalid
     */
    public InstrPush(List<InstructionArgument> arguments) {
        super("Push");
        checkSize(arguments, 1);

        checkNonIndirectRegister(arguments.get(0), 0);
        registerIndex = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
    }

    @Override
    public boolean execute(Computer computer) {
        Object value = computer.getRegisters().getRegisterValue(registerIndex);
        push(computer, value);

        return false;
    }

    /**
     * Does the <i>pushing</i> work of the instruction. Pushes the specified
     * <tt>value</tt> to the stack register of the specified <tt>computer</tt>.
     *
     * @param computer computer that holds the stack register
     * @param value value to be pushed onto the stack
     */
    protected static void push(Computer computer, Object value) {
        int location = (Integer) computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX);
        computer.getMemory().setLocation(location, value);

        // move stack pointer for 1 address down
        computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, location-1);
    }

}
