package blaufish.r2048;

import java.util.LinkedList;
import java.util.List;

public class R2048 {

	private static final Move[] MOVE_VALUES = Move.values();

	static void simulate() {
		C2048 c = new C2048();
		c.random();
		c.random();
		LinkedList<T> list = new LinkedList<>();
		list.add(new T(c,null));
		simulate(list);
	}
	

	static void simulate(List<T> list) {
		recurse: while (true) {
			T tuple = list.get(list.size() - 1);
			C2048 c = tuple.board;
			Move m = tuple.m;
			if (m == null) {
				m = firstMove();
				tuple.m = m;
			} else {
				if (isLastMove(m)) {
					if (list.size() == 1)
						return; //no more recursion
					list.remove(list.size() - 1);
					continue recurse;
				}
				m = nextMove(m);
				tuple.m = m;
			}
			C2048 cc;
			try {
				cc = c.move(m);
				list.add(new T(cc, null));
				continue recurse;
			} catch (IllegalMoveException e) {
				System.out.println("Points: " + c.p + " Illegal Move: " + m);
				continue;
			}
		}
	}


	private static Move firstMove() {
		return MOVE_VALUES[0];
	}

	private static Move nextMove(Move m) {
		for (int i = MOVE_VALUES.length - 1; i >= 0; i--)
			if (m == MOVE_VALUES[i])
				m = MOVE_VALUES[i + 1];
		return m;
	}


	private static boolean isLastMove(Move m) {
		return m == MOVE_VALUES[MOVE_VALUES.length - 1];
	}
	
	static class T {
		public T(C2048 board, Move m) {
			super();
			this.board = board;
			this.m = m;
		}
		C2048 board;
		Move m;
	}

	public static void main(String[] args) {
		simulate();
	}

}
