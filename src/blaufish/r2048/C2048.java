package blaufish.r2048;

import java.util.Arrays;
import java.util.Random;

class C2048 {
	int p = 0;
	int[] a;
	long seed;

	C2048() {
		p = 0;
		a = new int[9];
		seed = 0; // new Random().nextLong();
	}

	public C2048(C2048 original) {
		super();
		this.p = original.p;
		this.a = Arrays.copyOf(original.a, original.a.length);
		this.seed = new Random(original.seed).nextLong();
	}

	void random() {
		int counter = 0;
		for (int i = 0; i < a.length; i++)
			if (a[i] == 0)
				counter++;
		if (counter <= 0)
			throw new IllegalMoveException();
		Random r = new Random(seed);
		counter = r.nextInt(counter);
		for (int i = 0; i < a.length; i++) {
			if (a[i] == 0) {
				if (counter == 0) {
					a[i] = 2; /* todo: more values =) */
					break;
				}
				counter--;
			}
		}
	}

	C2048 move(Move m) {
		C2048 c2048 = new C2048(this);
		System.out.println("BEFORE move:");
		System.out.println(c2048);
		boolean valid;
		switch (m) {
		case LEFT:
			valid = left(c2048);
			break;
		case RIGHT:
			valid = right(c2048);
			break;
		case UP:
			valid = up(c2048);
			break;
		case DOWN:
			valid = down(c2048);
			break;
		default:
			throw new RuntimeException("Lazy boy did not implement???");
		}
		System.out.println("AFTER " + m + ":");
		System.out.println(c2048);
		c2048.random();
		if (!valid)
			throw new IllegalMoveException();
		System.out.println("AFTER random:");
		System.out.println(c2048);
		return c2048;
	}

	private boolean right(C2048 c2048) {
		boolean valid = false;
		for (int row = 0; row < 3; row++) {
			skip: for (int col = 2; col >= 0; col--) {
				int current = c2048.a[row * 3 + col];
				for (int c = col - 1; c >= 0; c--) {
					int next = c2048.a[row * 3 + c];
					if (next == 0) {
						continue;
					}
					if (0 == current) {
						current = next;
						c2048.a[row * 3 + col] = current;
						c2048.a[row * 3 + c] = 0;
						valid = true;
						continue;
					}
					if (next == current) {
						int value = next + current;
						c2048.p += value;
						c2048.a[row * 3 + col] = value;
						c2048.a[row * 3 + c] = 0;
						valid = true;
						continue skip;
					}

				}
			}
		}
		return valid;
	}

	private boolean left(C2048 c2048) {
		boolean valid = false;
		for (int row = 0; row < 3; row++) {
			skip: for (int col = 0; col < 3; col++) {
				int current = c2048.a[row * 3 + col];
				for (int c = col + 1; c < 3; c++) {
					int next = c2048.a[row * 3 + c];
					if (next == 0) {
						continue;
					}
					if (0 == current) {
						current = next;
						c2048.a[row * 3 + col] = current;
						c2048.a[row * 3 + c] = 0;
						valid = true;
						continue;
					}
					if (next == current) {
						int value = next + current;
						c2048.p += value;
						c2048.a[row * 3 + col] = value;
						c2048.a[row * 3 + c] = 0;
						valid = true;
						continue skip;
					}

				}
			}
		}
		return valid;
	}

	private boolean up(C2048 c2048) {
		boolean valid = false;
		for (int col = 0; col < 3; col++) {
			skip: 
				for (int row = 0; row < 3; row++) {
				int current = c2048.a[row * 3 + col];
				for (int r = row + 1; r < 3; r++) {
					int next = c2048.a[r * 3 + col];
					if (next == 0) {
						continue;
					}
					if (0 == current) {
						current = next;
						c2048.a[row * 3 + col] = current;
						c2048.a[r * 3 + col] = 0;
						valid = true;
						continue;
					}
					if (next == current) {
						int value = next + current;
						c2048.p += value;
						c2048.a[row * 3 + col] = value;
						c2048.a[r * 3 + col] = 0;
						valid = true;
						continue skip;
					}

				}
			}
		}
		return valid;
	}

	private boolean down(C2048 c2048) {
		boolean valid = false;
		for (int col = 0; col < 3; col++) {
			skip: 
				for (int row = 2; row >= 0; row--) {
				int current = c2048.a[row * 3 + col];
				for (int r = row - 1; r >= 0; r--) {
					int next = c2048.a[r * 3 + col];
					if (next == 0) {
						continue;
					}
					if (0 == current) {
						current = next;
						c2048.a[row * 3 + col] = current;
						c2048.a[r * 3 + col] = 0;
						valid = true;
						continue;
					}
					if (next == current) {
						int value = next + current;
						c2048.p += value;
						c2048.a[row * 3 + col] = value;
						c2048.a[r * 3 + col] = 0;
						valid = true;
						continue skip;
					}

				}
			}
		}
		return valid;
	}	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int row = 0; row < 3; row++) {
			sb.append(" | ");
			for (int col = 0; col < 3; col++) {
				int current = a[row * 3 + col];
				sb.append(current == 0 ? "#" : current).append(" | ");
			}
			sb.append(System.lineSeparator());
		}
		return sb.toString();

	}
}
