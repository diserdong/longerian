package me.longerian.pinyin;


public class People implements Comparable<People> {
		
	private String name;
	private String namePinyin;

	public People() {
		super();
	}

	public People(String name) {
		super();
		this.name = name;
		this.namePinyin = HanziHelper.words2Pinyin(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.namePinyin = HanziHelper.words2Pinyin(name);
	}

	public String getNamePinyin() {
		return namePinyin;
	}

	public void setNamePinyin(String namePinyin) {
		this.namePinyin = namePinyin;
	}

	@Override
	public int compareTo(People another) {
		return namePinyin.compareToIgnoreCase(another.namePinyin);
	}

	@Override
	public String toString() {
		return name;
	}

}
