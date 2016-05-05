package hr.fer.zemris.java.simplecomp;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * A register utility class, providing necessary methods for register
 * operations, such as retrieving the register index, checking if it is indirect
 * and getting the offset contained in the register.
 * <p>
 * These methods work with an integer value called <b>register descriptor</b>.
 * To read more about the descriptor, check the {@linkplain InstructionArgument}
 * class.
 *
 * @author Mario Bobic
 */
public abstract class RegisterUtil {

	/** Mask for extracting the register index. */
	private static final int INDEX_MASK = 0xFF;
	/** Mask for checking if the register is indirect. */
	private static final int INDIRECT_MASK = 0x1000000;
	/** Mask for extracting the register offset. */
	private static final int OFFSET_MASK = 0xFFFF00;

	/**
	 * Returns the 8-bit integer contained in the specified
	 * <tt>registerDescriptor</tt> that represents an index of the register.
	 * Note that since the register index is 8 bit, the index may only range
	 * from <tt>0</tt> to <tt>255</tt>.
	 * 
	 * @param registerDescriptor the register descriptor
	 * @return the index of the register
	 */
	public static int getRegisterIndex(int registerDescriptor) {
		return INDEX_MASK & registerDescriptor;
	}

	/**
	 * Returns true if the register is indirect, which is determined by checking
	 * if bit 24 contained in the specified <tt>registerDescriptor</tt> is set
	 * to <tt>1</tt>.
	 * 
	 * @param registerDescriptor the register descriptor
	 * @return true if the register is indirect, false if not
	 */
	public static boolean isIndirect(int registerDescriptor) {
		return (INDIRECT_MASK & registerDescriptor) != 0;
	}

	/**
	 * Returns the 16-bit integer contained in the specified
	 * <tt>registerDescriptor</tt> that represents the register offset.
	 * 
	 * @param registerDescriptor the register descriptor
	 * @return the register offset
	 */
	public static int getRegisterOffset(int registerDescriptor) {
		return (short) ((OFFSET_MASK & registerDescriptor) >> 8);
	}
	
	/**
	 * Returns the location from memory of the specified <tt>computer</tt> by
	 * summing the <tt>address</tt> (which is represented by the integer value
	 * of the specified <tt>registerDescriptor</tt>) and its <tt>offset</tt>.
	 * <p>
	 * If the register specified by the <tt>registerDescriptor</tt> is not
	 * indirect, an {@linkplain IllegalArgumentException} is thrown.
	 * <p>
	 * If the value of the register specified by the <tt>registerDescriptor</tt>
	 * can not be interpreted as an integer, an {@linkplain IllegalStateException}
	 * is thrown.
	 * 
	 * @param computer computer which holds the registers
	 * @param registerDescriptor the register descriptor
	 * @return the memory location obtained as <tt>address+offset</tt>
	 * @throws IllegalArgumentException if the specified register is not indirect
	 * @throws IllegalStateException if the register does not contain an integer
	 */
	public static int getIndirectRegisterLocation(Computer computer, int registerDescriptor) {
		if (!isIndirect(registerDescriptor)) {
			throw new IllegalArgumentException("The specified register must be indirect.");
		}
		
		int registerIndex = getRegisterIndex(registerDescriptor);
		
		Object registerValue = computer.getRegisters().getRegisterValue(registerIndex);
		if (!(registerValue instanceof Integer)) {
			throw new IllegalStateException("Indirect register must contain an integer. "
					+ "Instead it contained: [" + registerValue + "]");
		}
		
		int address = (Integer) registerValue;
		int offset = getRegisterOffset(registerDescriptor);
		
		return address+offset;
	}

}
