package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class is an abstract representation of the unary operation instruction.
 * It contains common methods, constructors, variables and classes used by the
 * concrete unary operations.
 * <p>
 * This class supports incrementing and decrementing as an unary operation.
 *
 * @author Mario Bobic
 */
public abstract class AbstractInstrUnaryOperation extends AbstractInstruction {

    /** The register index to which the unary operation is performed. */
    private int registerIndex;

    /**
     * Constructs an instance of {@code AbstractInstrUnaryOperation} with the
     * specified {@link InstructionArgument arguments} and the subclass
     * instruction name.
     * <p>
     * Throws an {@linkplain IllegalArgumentException} if the number of
     * arguments is different from <tt>1</tt> or the argument is not a regular
     * non-indirect register.
     *
     * @param arguments arguments of this instruction
     * @param instrName name of the instruction
     * @throws IllegalArgumentException if argument size or register is invalid
     */
    protected AbstractInstrUnaryOperation(List<InstructionArgument> arguments, String instrName) {
        super(instrName);
        checkSize(arguments, 1);

        InstructionArgument register = arguments.get(0);
        checkNonIndirectRegister(register, 0);

        registerIndex = RegisterUtil.getRegisterIndex((Integer) register.getValue());
    }

    /**
     * Executes the unary operation using the method {@linkplain #apply(int)}
     * and storing the result as:
     * <blockquote>register = (unary_op)register</blockquote>
     *
     * @param computer computer that holds the registers
     * @return false
     */
    @Override
    public boolean execute(Computer computer) {
        Integer num = (Integer) computer.getRegisters().getRegisterValue(registerIndex);

        computer.getRegisters().setRegisterValue(
                registerIndex,
                apply(num)
        );

        return false;
    }

    /**
     * Applies the unary operation to the specified value <tt>n</tt>.
     *
     * @param n the value to which the operation is to be applied
     * @return the result of the operation
     */
    protected abstract int apply(int n);

}
