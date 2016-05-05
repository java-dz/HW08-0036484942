package hr.fer.zemris.java.simplecomp;

import org.junit.Test;

import hr.fer.zemris.java.simplecomp.impl.instructions.*;
import hr.fer.zemris.java.simplecomp.models.*;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("javadoc")
public class InstructionTests {

	/* ------------------------------ Load tests ------------------------------ */
	
	// Gets integer value 10 from memory location 20 and stores into register r0
	// (load r0, 20)
	@Test
	public void testLoad1() {
		// Initialize variables which must be the same across method calls
		int location = 20;
		int regIndex = 0;
		Integer value = 10;
		
		// Get mock objects
		Computer c = getComputer();
		Memory m = c.getMemory();
		Registers r = c.getRegisters();
		
		// Set mock return values
		when(m.getLocation(location)).thenReturn(value);
		
		// Fill in the arguments list
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(false, true, false, new Register(false, regIndex, 0)));
		arguments.add(new InstructionArgumentImpl(false, false, true, location));
		
		// Execute the instruction
		Instruction instr = new InstrLoad(arguments);
		instr.execute(c);
		
		
		// Verify mock method calls
		verify(m, times(1)).getLocation(location);
		verify(r, times(1)).setRegisterValue(regIndex, value);
	}
	
	// Constructor must not accept indirect register as first argument
	@Test(expected=IllegalArgumentException.class)
	public void testLoad2() {
		// Fill in the arguments list
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(false, true, false, new Register(true, 0, 10)));
		arguments.add(new InstructionArgumentImpl(false, false, true, 20));
		
		// The constructor must throw an exception
		new InstrLoad(arguments);
	}
	
	
	/* ------------------------------ Move tests ------------------------------ */
	
	// Moves (copies) value from memory location 20 to memory location 30 using indirect registers
	// (move [r2+25], [r1+20]), where r2 = 5 and r1 = 0
	@Test
	public void testMove1() {
		// Initialize source variables
		int regIndexSrc = 1;
		int regOffsetSrc = 20;
		Integer regValueSrc = 0; // total offset: 20
		Integer locationValueSrc = -50;
		
		// Initialize destination variables
		int regIndexDst = 2;
		int regOffsetDst = 5;
		Integer regValueDst = 25; // total offset: 30
		
		// Get mock objects
		Computer c = getComputer();
		Memory m = c.getMemory();
		Registers r = c.getRegisters();
		
		// Set mock return values
		when(r.getRegisterValue(regIndexSrc)).thenReturn(regValueSrc);
		when(r.getRegisterValue(regIndexDst)).thenReturn(regValueDst);
		when(m.getLocation(regValueSrc+regOffsetSrc)).thenReturn(locationValueSrc);
		
		// Fill in the arguments list
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(false, true, false, new Register(true, regIndexDst, regOffsetDst)));
		arguments.add(new InstructionArgumentImpl(false, true, false, new Register(true, regIndexSrc, regOffsetSrc)));
		
		// Execute the instruction
		Instruction instr = new InstrMove(arguments);
		instr.execute(c);
		
		
		// Verify mock method calls
		verify(r, times(1)).getRegisterValue(regIndexSrc);
		verify(r, times(1)).getRegisterValue(regIndexDst);
		
		verify(m, times(1)).getLocation(regValueSrc+regOffsetSrc);
		verify(m, times(1)).setLocation(regValueDst+regOffsetDst, locationValueSrc);
	}
	
	// Moves (copies) value from register 1 to register 2
	// (move r2, r1), where r1 = "A string in r1."
	@Test
	public void testMove2() {
		// Initialize source variables
		int regIndexSrc = 1;
		String regValueSrc = "A string in r1.";
		
		// Initialize destination variables
		int regIndexDst = 2;
		
		// Get mock objects
		Computer c = getComputer();
		Registers r = c.getRegisters();
		
		// Set mock return values
		when(r.getRegisterValue(regIndexSrc)).thenReturn(regValueSrc);
		
		// Fill in the arguments list
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(false, true, false, new Register(false, regIndexDst, 0)));
		arguments.add(new InstructionArgumentImpl(false, true, false, new Register(false, regIndexSrc, 0)));
		
		// Execute the instruction
		Instruction instr = new InstrMove(arguments);
		instr.execute(c);
		
		
		// Verify mock method calls
		verify(r, times(1)).getRegisterValue(regIndexSrc);
		verify(r, times(1)).setRegisterValue(regIndexDst, regValueSrc);
	}
	
	// Constructor must not accept string as second argument
	@Test(expected=IllegalArgumentException.class)
	public void testMove3() {
		// Fill in the arguments list
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(false, true, false, new Register(true, 0, 10)));
		arguments.add(new InstructionArgumentImpl(true, false, false, "A string."));
		
		// The constructor must throw an exception
		new InstrMove(arguments);
	}
	
	
	/* ------------------------------ Push tests ------------------------------ */
	
	// Pushes a string value from register r14 to stack
	// (push r14), where r14 = "A string value."
	@Test
	public void testPush1() {
		// Initialize register variables
		int regIndex = 14;
		String value = "A string value.";
		
		// Initialize stack starting location
		int stackTop = 50;
		
		// Get mock objects
		Computer c = getComputer();
		Memory m = c.getMemory();
		Registers r = c.getRegisters();
		
		// Set mock return values
		when(r.getRegisterValue(Registers.STACK_REGISTER_INDEX)).thenReturn(stackTop);
		when(r.getRegisterValue(regIndex)).thenReturn(value);
		
		// Fill in the arguments list
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(false, true, false, new Register(false, regIndex, 0)));
		
		// Execute the instruction
		Instruction instr = new InstrPush(arguments);
		instr.execute(c);
		
		
		// Verify mock method calls
		verify(r, times(1)).getRegisterValue(Registers.STACK_REGISTER_INDEX);
		verify(r, times(1)).getRegisterValue(regIndex);
		
		verify(m, times(1)).setLocation(stackTop, value);
		
		verify(r, times(1)).setRegisterValue(Registers.STACK_REGISTER_INDEX, stackTop-1);
	}
	
	// Constructor must not accept an indirect register
	@Test(expected=IllegalArgumentException.class)
	public void testPush2() {
		// Put an indirect register as the only argument
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(false, true, false, new Register(true, 0, 10)));
		
		// The constructor must throw an exception
		new InstrPush(arguments);
	}
	
	
	/* ------------------------------ Pop tests ------------------------------ */
	
	// Pops a value from memory location 51 (obtained from stack top) to r14
	// (pop r14), where stackTop = 50, and memory location 51 = "A string value"
	@Test
	public void testPop1() {
		// Initialize register variables
		int regIndex = 14;
		
		// Initialize stack starting location
		int stackTop = 50;
		String value = "A string value";
		
		// Get mock objects
		Computer c = getComputer();
		Memory m = c.getMemory();
		Registers r = c.getRegisters();
		
		// Set mock return values
		when(r.getRegisterValue(Registers.STACK_REGISTER_INDEX)).thenReturn(stackTop);
		when(m.getLocation(stackTop+1)).thenReturn(value);
		
		// Fill in the arguments list
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(false, true, false, new Register(false, regIndex, 0)));
		
		// Execute the instruction
		Instruction instr = new InstrPop(arguments);
		instr.execute(c);
		
		
		// Verify mock method calls
		verify(r, times(1)).setRegisterValue(Registers.STACK_REGISTER_INDEX, stackTop+1);
		
		verify(r, times(1)).getRegisterValue(Registers.STACK_REGISTER_INDEX);
		verify(r, times(1)).setRegisterValue(regIndex, value);
		
		verify(m, times(1)).getLocation(stackTop+1);
	}
	
	// Constructor must not accept an indirect register
	@Test(expected=IllegalArgumentException.class)
	public void testPop2() {
		// Put an indirect register as the only argument
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(false, true, false, new Register(true, 0, 10)));
		
		// The constructor must throw an exception
		new InstrPop(arguments);
	}
	
	
	/* ------------------------------ Call tests ------------------------------ */
	
	// Calls the memory location 20 from pc=5 and sets the pc to 20
	// (call 20)
	@Test
	public void testCall1() {
		// Initialize the value variable and current pc
		Integer value = 20;
		int pc = 5;
		
		// Initialize stack starting location
		int stackTop = 50;
		
		// Get mock objects
		Computer c = getComputer();
		Memory m = c.getMemory();
		Registers r = c.getRegisters();
		
		// Set mock return values
		when(r.getRegisterValue(Registers.STACK_REGISTER_INDEX)).thenReturn(stackTop);
		when(r.getProgramCounter()).thenReturn(pc);
		
		// Fill in the arguments list
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(false, false, true, value));
		
		// Execute the instruction
		Instruction instr = new InstrCall(arguments);
		instr.execute(c);
		
		
		// Verify mock method calls
		verify(r, times(1)).getRegisterValue(Registers.STACK_REGISTER_INDEX);
		verify(m, times(1)).setLocation(stackTop, pc);
		
		verify(r, times(1)).setRegisterValue(Registers.STACK_REGISTER_INDEX, stackTop-1);
		
		verify(r, times(1)).setProgramCounter(value);
	}
	
	// Constructor must not accept an indirect register
	@Test(expected=IllegalArgumentException.class)
	public void testCall2() {
		// Put an indirect register as the only argument
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(false, true, false, new Register(true, 0, 10)));
		
		// The constructor must throw an exception
		new InstrCall(arguments);
	}
	
	
	/* ------------------------------ Ret tests ------------------------------ */
	
	// Returns from some location to memory location 5 (sets the pc to 5)
	// (ret)
	@Test
	public void testRet1() {
		// Initialize the return location
		int returnLocation = 5;
		
		// Initialize stack starting location
		int stackTop = 50;
		
		// Get mock objects
		Computer c = getComputer();
		Memory m = c.getMemory();
		Registers r = c.getRegisters();
		
		// Set mock return values
		when(m.getLocation(stackTop+1)).thenReturn(returnLocation);
		when(r.getRegisterValue(Registers.STACK_REGISTER_INDEX)).thenReturn(stackTop);
		
		// Create an empty list of arguments
		@SuppressWarnings("unchecked")
		List<InstructionArgument> arguments = (List<InstructionArgument>) mock(List.class);
		when(arguments.size()).thenReturn(0);
		
		// Execute the instruction
		Instruction instr = new InstrRet(arguments);
		instr.execute(c);
		
		
		// Verify mock method calls
		verify(arguments, times(1)).size();
		
		verify(r, times(1)).getRegisterValue(Registers.STACK_REGISTER_INDEX);
		verify(r, times(1)).setRegisterValue(Registers.STACK_REGISTER_INDEX, stackTop+1);
		
		verify(m, times(1)).getLocation(stackTop+1);
		verify(r, times(1)).setProgramCounter(returnLocation);
	}
	
	// Constructor must not accept any arguments
	@Test(expected=IllegalArgumentException.class)
	public void testRet2() {
		// Add one argument that represents a location
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(false, false, true, 10));
		
		// The constructor must throw an exception
		new InstrRet(arguments);
	}
	
	
	/* ------------------------------ Mul tests ------------------------------ */
	
	@Test
	public void testMul() {
		// Initialize variables which must be the same across method calls
		Integer value1 = 10;
		Integer value2 = 20;
		
		// Get mock objects
		Computer c = getComputer();
		Registers r = c.getRegisters();
		
		// Set mock return values
		when(r.getRegisterValue(1)).thenReturn(value1);
		when(r.getRegisterValue(2)).thenReturn(value2);
		
		// Fill in the arguments list
		List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(false, true, false, new Register(false, 0, 0)));
		arguments.add(new InstructionArgumentImpl(false, true, false, new Register(false, 1, 0)));
		arguments.add(new InstructionArgumentImpl(false, true, false, new Register(false, 2, 0)));
		
		// Execute the instruction
		Instruction instr = new InstrMul(arguments);
		instr.execute(c);
		
		
		// Verify mock method calls
		verify(r, times(0)).getRegisterValue(0);
		verify(r, times(1)).getRegisterValue(1);
		verify(r, times(1)).getRegisterValue(2);
		
		verify(r, times(1)).setRegisterValue(0, value1*value2);
	}
	
	
	
	/* ------------------------------ Utility methods and classes ------------------------------ */
	
	/**
	 * Returns a mock object of a computer with mocked registers and memory.
	 * 
	 * @return a mock object of a computer with mocked registers and memory
	 */
	private static Computer getComputer() {
		Computer c = (Computer) mock(Computer.class);
		Registers r = (Registers) mock(Registers.class);
		Memory m = (Memory) mock(Memory.class);
		
		when(c.getRegisters()).thenReturn(r);
		when(c.getMemory()).thenReturn(m);
		
		return c;
	}
	
	/**
	 * This class represents a register with an obtainable register descriptor.
	 * This is helpful for getting a descriptor of an indirect register, with
	 * its offset shifted 8 bits from the beginning.
	 *
	 * @author Mario Bobic
	 */
	private static class Register {
		
		/** Indicates if the register is an indirect register. */
		private boolean isIndirect;
		/** The index of the register. */
		private int index;
		/** The offset of the register, if it is indirect. */
		private int offset;
		
		/**
		 * Constructs a Register with the specified values.
		 * 
		 * @param isIndirect indicates if the register is indirect
		 * @param index the index of the register
		 * @param offset the offset of the register, if it is indirect
		 */
		public Register(boolean isIndirect, int index, int offset) {
			this.isIndirect = isIndirect;
			this.index = index;
			this.offset = offset;
		}

		/**
		 * Returns the register descriptor.
		 * 
		 * @return the register descriptor
		 */
		public int getRegisterDescriptor() {
			if (isIndirect) {
				return 0x1000000 | (offset << 8) | index;
			} else {
				return index;
			}
		}
	}
	
	/**
	 * This class is an implementation of the {@linkplain InstructionArgument}.
	 *
	 * @author Mario Bobic
	 */
	private static class InstructionArgumentImpl implements InstructionArgument {
		
		/** Is string. */
		private boolean s;
		/** Is register. */
		private boolean r;
		/** Is number. */
		private boolean n;
		/** The value. */
		private Object value;
		
		/**
		 * Creates an instruction argument with the specified parameters.
		 * 
		 * @param s true if the argument is a string
		 * @param r true if the argument is a register
		 * @param n true if the argument is a number
		 * @param value the value of the argument
		 * @return an instruction argument
		 */
		public InstructionArgumentImpl(boolean s, boolean r, boolean n, Object value) {
			this.s = s;
			this.r = r;
			this.n = n;
			this.value = value;
		}

		@Override
		public boolean isString() {
			return s;
		}
		
		@Override
		public boolean isRegister() {
			return r;
		}
		
		@Override
		public boolean isNumber() {
			return n;
		}
		
		@Override
		public Object getValue() {
			if (r) {
				return ((Register) value).getRegisterDescriptor();
			} else {
				return value;
			}
		}
	}

}
