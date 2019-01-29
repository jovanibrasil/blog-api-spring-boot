package blog.enums;

public enum ListOrderType {
	// ascending and descending
	ASC("ASC"), DESC("DESC");
	
	private String value;
	
	private ListOrderType(String value) {
		this.value = value;
	}
	
	public static ListOrderType forNameIgnoreCase(String value) {
		for (ListOrderType category :values()) {
			if(category.value.equalsIgnoreCase(value)) {
				return category;
			}
		}
		return null;
	}
	
	public static ListOrderType fromValue(String value) {
		
		for (ListOrderType category :values()) {
			if(category.value.equalsIgnoreCase(value)) {
				return category;
			}
		}
		throw new IllegalArgumentException(value + " is an unknown enum type.");
	}
	
	@Override
	public String toString() {
		return value;
	}
	
}
