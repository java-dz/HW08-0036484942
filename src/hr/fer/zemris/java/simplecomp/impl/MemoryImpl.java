package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.Memory;

/**
 * This class implements the {@linkplain Memory} interface and contains concrete
 * locations used for storing {@linkplain Instruction}s and <tt>constants</tt>.
 *
 * @author Mario Bobic
 */
public class MemoryImpl implements Memory {
	
	/** Array of memory locations. */
	private Object[] locations;
	
	/**
	 * Constructs an instance of {@code MemoryImpl} with memory length of the
	 * specified <tt>size</tt>. The specified size must not be negative.
	 * 
	 * @param size memory size
	 * @throws IllegalArgumentException if the specified size is negative
	 */
	public MemoryImpl(int size) {
		if (size < 0) {
			throw new IllegalArgumentException("Memory size must not be negative.");
		}
		locations = new Object[size];
	}

	@Override
	public void setLocation(int location, Object value) {
		checkLocation(location);
		locations[location] = value;
	}

	@Override
	public Object getLocation(int location) {
		checkLocation(location);
		return locations[location];
	}
	
	/**
	 * Checks if the specified <tt>location</tt> is in range of the memory, or
	 * throws an {@linkplain IndexOutOfBoundsException} if the location is
	 * invalid.
	 * 
	 * @param location location to be checked
	 * @throws IndexOutOfBoundsException if location is out of range
	 */
	private void checkLocation(int location) {
		if (location < 0 || location >= locations.length) {
			throw new IndexOutOfBoundsException(
					"Size: " + locations.length
					+ ", location: " + location);
		}
	}

}
