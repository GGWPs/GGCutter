package util;

public enum Wood {
	NORMAL("Logs", "Tree"), OAK("Oak logs", "Oak"), WILLOW("Willow logs", "Willow"), MAPLE("Maple logs", "Maple"), YEW("Yew logs", "Yew"), MAGIC("Magic logs", "Magic");
	
	private String logName;
	private String treeName;
	
	Wood(String logname, String treeName) {
		setLogName(logName);
		setTreeName(treeName);
		
	}
	
	public String getLogName() {
		return logName;
	}
	
	public void setLogName(String logName) {
		this.logName = logName;
	}
	
	public String getTreeName() {
		return treeName;
	}
	
	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}
}
