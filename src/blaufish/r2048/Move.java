package blaufish.r2048;

enum Move {
	LEFT, RIGHT, UP, DOWN;
	
	static Move nextMove(Move m) {
		Move[] values = values();
		for (int i = values.length - 1; i >= 0; i--)
			if (m == values[i])
				m = values[i + 1];
		return m;
	}


	static boolean isLastMove(Move m) {
		Move[] values = values();
		return m == values[values.length - 1];
	}
	
}
