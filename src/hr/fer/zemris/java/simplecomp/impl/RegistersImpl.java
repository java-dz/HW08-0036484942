package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * This class implements the {@linkplain Registers} interface and contains
 * concrete registers. There are several types of registers, which are stored
 * this way:
 * <ul>
 * <li>The general purpose registers, ranging from index 0 to <tt>regsLen</tt>
 * given to the {@linkplain #RegistersImpl(int)} constructor, with an exception
 * of register at index {@linkplain Registers#STACK_REGISTER_INDEX} that can be
 * used both as a stack register and a general purpose register.
 * <li>The stack register which may be used as a stack pointer, whose index is
 * {@linkplain Registers#STACK_REGISTER_INDEX}.
 * <li>The <tt>program counter</tt> register which is stored separately.
 * </ul>
 * <p>
 * The implementation also holds a <tt>flag</tt> which may be used by some
 * {@linkplain Instruction}s.
 *
 * @author Mario Bobic
 */
public class RegistersImpl implements Registers {

    /** Array of general purpose registers plus the stack register. */
    private Object[] registers;
    /** The program counter register. */
    private int pc;
    /** A flag used by some instructions. */
    private boolean flag;

    /**
     * Constructs an instance of {@code RegistersImpl} with number of registers
     * specified by the <tt>regsLen</tt> parameter. The number of registers must
     * not be negative, and additionally it must be greater than the value
     * specified by the {@linkplain Registers#STACK_REGISTER_INDEX} constant as
     * <b>this implementation requires to have a stack register</b>.
     *
     * @param regsLen the number of registers
     * @throws IllegalArgumentException if regsLen &lt; STACK_REGISTER_INDEX+1
     */
    public RegistersImpl(int regsLen) {
        checkLength(regsLen);
        registers = new Object[regsLen];
    }

    /**
     * Checks the registers length passed to the class constructor and throws an
     * {@linkplain IllegalArgumentException} if the <tt>length</tt> is negative or
     * is less than <tt>Registers.STACK_REGISTER_INDEX+1</tt>
     *
     * @param length length to be checked
     * @throws IllegalArgumentException if length &lt; STACK_REGISTER_INDEX+1
     */
    private static void checkLength(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("Registers length must not be negative.");
        }

        int minLen = Registers.STACK_REGISTER_INDEX+1;
        if (length < minLen) {
            throw new IllegalArgumentException(
                "Registers length must be at least " + minLen
                + " as the index of stack register is " + Registers.STACK_REGISTER_INDEX);
        }
    }

    @Override
    public Object getRegisterValue(int index) {
        checkIndex(index);
        return registers[index];
    }

    @Override
    public void setRegisterValue(int index, Object value) {
        checkIndex(index);
        registers[index] = value;
    }

    @Override
    public int getProgramCounter() {
        return pc;
    }

    @Override
    public void setProgramCounter(int value) {
        pc = value;
    }

    @Override
    public void incrementProgramCounter() {
        pc++;
    }

    @Override
    public boolean getFlag() {
        return flag;
    }

    @Override
    public void setFlag(boolean value) {
        flag = value;
    }

    /**
     * Checks if the specified <tt>index</tt> is a valid register index, or
     * throws an {@linkplain IndexOutOfBoundsException} if the index is invalid.
     *
     * @param index register index to be checked
     * @throws IndexOutOfBoundsException if register index is out of range
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= registers.length) {
            throw new IndexOutOfBoundsException(
                    "Size: " + registers.length
                    + ", location: " + index);
        }
    }

}
