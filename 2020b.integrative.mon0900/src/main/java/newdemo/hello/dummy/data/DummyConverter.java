package newdemo.hello.dummy.data;

import org.springframework.stereotype.Component;

import newdemo.hello.dummy.rest.boudanries.DummyBoundary;
import newdemo.hello.dummy.rest.boudanries.NameBoundary;
import newdemo.hello.dummy.rest.boudanries.Type;

@Component
public class DummyConverter {
	public DummyBoundary fromEntity (DummyEntity entity) {
		DummyBoundary rv = new DummyBoundary(entity.getId(), entity.getMessage());
		rv.setDetails(entity.getDetails());
		rv.setIsDeleted(entity.getDeleted());
		rv.setTimestamp(entity.getTimestamp());
		if (entity.getType() != null) {
			rv.setType(this.fromEntity(entity.getType()));
		}else {
			rv.setType(null);
		}
		rv.setName(this.fromEntity(entity.getName()));
		return rv;
	}
	
	public DummyEntity toEntity (DummyBoundary boundary) {
		DummyEntity rv = new DummyEntity(boundary.getId(), boundary.getMessage());
		if (boundary.getIsDeleted() != null) {
			rv.setDeleted(boundary.getIsDeleted());
		}else {
			rv.setDeleted(false);
		}
		rv.setDetails(boundary.getDetails());
		rv.setTimestamp(boundary.getTimestamp());
		if (boundary.getType() != null) {
			rv.setType(toEntity(boundary.getType()));
		}else {
			rv.setType(null);
		}
		rv.setName(this.toEntity(boundary.getName()));
		return rv;
	}

	public TypeEntityEnum toEntity(Type type) {
		if (type != null) {
			return TypeEntityEnum.valueOf(type.name().toLowerCase());
		}else {
			return null;
		}
	}
	
	public Type fromEntity(TypeEntityEnum type) {
		if (type != null) {
			return Type.valueOf(type.name().toUpperCase());
		}else {
			return null;
		}
	}
	
	public NameBoundary fromEntity(NameEntity name) {
		if(name != null) {
			return new NameBoundary(name.getFirst(), name.getLast());
		}else {
			return null;
		}
	}

	public NameEntity toEntity(NameBoundary name) {
		if (name != null) {
			return new NameEntity(name.getFirst(), name.getLast());
		}else {
			return null;
		}
	}


}
