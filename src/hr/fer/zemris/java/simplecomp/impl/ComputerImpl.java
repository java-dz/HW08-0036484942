package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * This class implements the {@linkplain Computer} interface, containing an
 * obtainable {@linkplain Memory} and {@linkplain Registers}.
 *
 * @author Mario Bobic
 */
public class ComputerImpl implements Computer {

    /** The computer memory. */
    private Memory memory;
    /** The computer registers. */
    private Registers registers;

    /**
     * Constructs an instance of {@code ComputerImpl} with the specified memory
     * length and registers length. The number of registers must be greater than
     * the value specified by the {@linkplain Registers#STACK_REGISTER_INDEX}
     * constant.
     *
     * @param memoryLength the length of computer memory (number of locations)
     * @param registersLength the number of registers
     * @throws IllegalArgumentException if registersLength &lt; STACK_REGISTER_INDEX+1
     */
    public ComputerImpl(int memoryLength, int registersLength) {
        memory = new MemoryImpl(memoryLength);
        registers = new RegistersImpl(registersLength);
    }

    @Override
    public Registers getRegisters() {
        return registers;
    }

    @Override
    public Memory getMemory() {
        return memory;
    }

}
