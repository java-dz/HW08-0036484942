package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.*;

/**
 * This class implements the {@linkplain ExecutionUnit} interface and represents
 * an execution unit. It executes the program stored in the computer memory.
 *
 * @author Mario Bobic
 */
public class ExecutionUnitImpl implements ExecutionUnit {

	@Override
	public boolean go(Computer computer) {
		Registers registers = computer.getRegisters();
		Memory memory = computer.getMemory();
		
		registers.setProgramCounter(0);
		
		while (true) {
			int pc = registers.getProgramCounter();
			registers.incrementProgramCounter();
			
			try {
				Instruction instruction = getInstruction(memory, pc);
				
				boolean halt = instruction.execute(computer);
				if (halt) break;
			} catch (Throwable t) {
				t.printStackTrace();
				System.err.println("\t at instruction on address " + pc);
				return false;
			}
		}
		
		return true;
	}

	/**
	 * Returns the instruction from the specified <tt>memory</tt> at the
	 * specified program counter <tt>pc</tt>.
	 * <p>
	 * Throws an {@linkplain IllegalStateException} if the instruction is in
	 * illegal state. This could happen if stack is overflown, if the program
	 * was not halted or if a subroutine did not return.
	 * </p>
	 * 
	 * @param memory the computer memory
	 * @param pc program counter
	 * @return the instruction from memory at the program counter address
	 */
	private static Instruction getInstruction(Memory memory, int pc) {
		Object instruction = memory.getLocation(pc);
		if (!(instruction instanceof Instruction)) {
			throw new IllegalStateException("Reading constants instead of instructions.");
		}
		
		return (Instruction) instruction;
	}

}
