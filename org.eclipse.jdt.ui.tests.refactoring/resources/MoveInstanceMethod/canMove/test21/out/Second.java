package p;
class Second {
	public void foo(Second s) {
		s.bar();
	}

	public void bar() {
	}
	
	public void go(int i, int j) {
	}

	/**
	 * @param a
	 */
	public void print(A a) {
		foo(this);
		bar();
		go(17, 18);
	
		a.equals(a);
		foo(a.s2);
		a.s2.bar();
	}
}

