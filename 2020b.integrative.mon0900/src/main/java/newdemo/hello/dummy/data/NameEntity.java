package newdemo.hello.dummy.data;

import javax.persistence.Embeddable;

@Embeddable
public class NameEntity {
	private String first;
	private String last;

	public NameEntity() {
	}

	public NameEntity(String first, String last) {
		super();
		this.first = first;
		this.last = last;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	@Override
	public String toString() {
		return "NameBoundary [first=" + first + ", last=" + last + "]";
	}

	
}
