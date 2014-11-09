import java.util.Iterator;


public class SymbolTable {
	
	class Node	{	
		String key;	
		Object data;	
		Node next;	
		Node(String	k,	Object	d,	Node	x)	{	
			key	=	k;	
			data	=	d;	
			next	=	x;	
		}	
	}	
	Node table[];	
	public	SymbolTable(int	s)	{
		table = new Node[s];
	}

	private	int	hash (String	k)	{	
		//return	the	hash	funcBon	value	for	k
		int hash = 0;
		int len = k.length();
		for (int i = 0; i < len; i++) {
			hash += k.charAt(i);
		}
		return hash%table.length;	
	}	

	public	boolean	insert (String k)	{	
		//if	k	is	not	in	the	table	create	a	new	entry	with	null	data	and	return	true	
		//if	k	is	in	the	table	return	false
		int i = hash(k);
		Node n = table[i];
		while(n != null) {
			if(n.key.equals(k))
				return false;
			n = n.next;
		}
		Node t = table[i];
		table[i] = new Node(k, null, t);
		return true;
	}	

	public	boolean	insert(String	k,	Object	d)	{	
		//if	k	is	not	in	the	table	create	a	new	entry	with	data	value	d	and	return	true	
		//if	k	is	in	the	table	return	false	
		int i = hash(k);
		Node n = table[i];
		while(n != null) {
			if(n.key.equals(k))
				return false;
			n = n.next;
		}
		Node t = table[i];
		table[i] = new Node(k, d, t);
		return true;
	}	
	
	public boolean find(String k) {
		//returns true if k is in the table otherwise return false
		int i = hash(k);
		Node n = table[i];
		while(n != null) {
			if(n.key.equals(k))
				return true;
			n = n.next;
		}
		return false;
	}

	public Object getData (String k) {
		//if	k	is	in	the	table	return	the	data	associated	with	k	
		//if	k	is	not	in	the	table	return	null
		int i = hash(k);
		Node n = table[i];
		while(n != null) {
			if(n.key.equals(k))
				return n.data;
			n = n.next;
		}
		return null;
	}
	
	public void setValue (String k, Object d) {
		//PRE:	k	is	in	the	table	
		//make	d	the	data	value	associated	with	k	
		int i = hash(k);
		Node n = table[i];
		while(!n.key.equals(k)) {
			n = n.next;
		}
		n.data = d;
	}
	
	public boolean delete (String k) {
		//if	k	is	in	the	table,	delete	the	entry	for	k	and	return	true	
		//if	k	is	not	in	the	table,	return	false	
		int i = hash(k);
		Node n = table[i];
		if (n.key.equals(k)) {
			table[i] = n.next;
			return true;
		}
		Node t = n;
		n = n.next;
		while(n != null) {
			if(n.key.equals(k)) {
				t.next = n.next;
				return true;
			}
			t = n;
			n = n.next;
		}
		return false;
	}
	
	public Iterator<String> iterator() {
		//returns a new iterator object
		return new STIterator();
	}
	
	public class STIterator implements Iterator<String> {
		
		Node n;
		int i;
		
		public STIterator() {
			i = 0;
			n = null;
		}
		
		@Override
		public boolean hasNext() {
			int t = i;
			if(n != null){
				if(n.next != null)
					return true;
				t++;
			}
			int k = table.length;
			if (t == k)
				return false;
			while(table[t] == null) {
				if (++t == k)
					return false;
			}
			return true;
		}

		@Override
		public String next() {
			if(n != null){
				if(n.next != null) {
					n = n.next;
					return n.key;
				}
				i++;
			}
			while(table[i] == null) {
				if (i++ == table.length)
					return null;
			}
			n = table[i];
			return n.key;
		}

		@Override
		public void remove() {
			// optional
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SymbolTable st = new SymbolTable(10);
		System.out.println(st.insert("hello"));
		int i = 5009;
		System.out.println(st.insert("Jesse", i));
		System.out.println(st.insert("Jesse"));
		char c = 'l';
		System.out.println(st.insert("string", c));
		double d = 56.8;
		System.out.println(st.insert("My", d));
		System.out.println(st.insert("hoKKo"));
		System.out.println(st.insert("hoKKe"));
		System.out.println(st.insert("dddd"));
		Iterator<String> it = st.iterator();
		while(it.hasNext()) 
			System.out.println(it.next());
		int k = (int)st.getData("Jesse");
		k++;
		System.out.println(st.delete("Jesse"));
		System.out.println(st.delete("fail"));
		System.out.println(st.find("Jesse"));
		System.out.println(st.find("hoKKo"));
		st.setValue("hoKKo", k);
		System.out.println(st.getData("hoKKo"));
	}

	

}
