package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * This class serves as a super class for {@linkplain Instruction}s, offering
 * most common methods, mostly for checking the validity of arguments.
 *
 * @author Mario Bobic
 */
public abstract class AbstractInstruction implements Instruction {

    /** Name of the instruction.*/
    private final String instrName;

    /**
     * Constructs an instance of {@code AbstractInstruction} with the specified
     * <tt>name</tt> of the instruction.
     *
     * @param name name of the instruction
     */
    protected AbstractInstruction(String name) {
        instrName = name;
    }

    /**
     * Checks if the size of the specified list is as expected.
     * <p>
     * This method throws an {@linkplain IllegalArgumentException} if
     * <tt>arguments.size() != n</tt>
     *
     * @param arguments the list whose size is to be checked
     * @param n the expected size of the list
     * @throws IllegalArgumentException if <tt>arguments.size() != n</tt>
     */
    protected void checkSize(List<InstructionArgument> arguments, int n) {
        if (arguments.size() != n) {
            throw new IllegalArgumentException(instrName + ": expected " + n + " arguments!");
        }
    }

    /**
     * Checks if the specified list contains all <tt>registers</tt>, and that
     * all are non-indirect, both which are obtained by the
     * {@linkplain #checkNonIndirectRegister(InstructionArgument, int)} method.
     * <p>
     * This method throws an {@linkplain IllegalArgumentException} if any of the
     * criteria specified from the method above is not met.
     *
     * @param arguments list of instruction arguments
     * @throws IllegalArgumentException if any argument is not non-indirect register
     */
    protected static void checkNonIndirectRegisterList(List<InstructionArgument> arguments) {
        int size = arguments.size();
        for (int i = 0; i < size; i++) {
            checkNonIndirectRegister(arguments.get(i), i);
        }
    }

    /**
     * Checks if the specified argument is a <tt>register</tt>, obtained by the
     * {@link InstructionArgument#isRegister() argument.isRegister()} method,
     * and that it is a non-indirect register, which is obtained by the
     * {@link RegisterUtil#isIndirect(int) RegisterUtil.isIndirect((Integer)
     * argument.getValue())} method.
     * <p>
     * This method throws an {@linkplain IllegalArgumentException} if any of the
     * criteria as specified above is not met.
     *
     * @param argument the instruction argument to be checked
     * @param index the index of the instruction argument
     * @throws IllegalArgumentException if the argument is not non-indirect register
     */
    protected static void checkNonIndirectRegister(InstructionArgument argument, int index) {
        if (!argument.isRegister() || RegisterUtil.isIndirect((Integer) argument.getValue())) {
            throw new IllegalArgumentException("Type mismatch for argument " + index + "!");
        }
    }

    /**
     * Checks if the specified argument is a <tt>number</tt>, obtained by the
     * {@link InstructionArgument#isNumber() argument.isNumber()} method.
     * <p>
     * This method throws an {@linkplain IllegalArgumentException} the specified
     * <tt>argument</tt> is not a number.
     *
     * @param argument the instruction argument to be checked
     * @param index the index of the instruction argument
     */
    protected static void checkNumber(InstructionArgument argument, int index) {
        if (!argument.isNumber()) {
            throw new IllegalArgumentException("Type mismatch for argument "
                    + index + "! It must be a location.");
        }
    }

    /**
     * Returns the name of this instruction.
     */
    @Override
    public String toString() {
        return instrName;
    }

}
