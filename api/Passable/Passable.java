public interface Passable { 

	//Exception shouldn't happen unless one or more of the values are invalid
	public String toJSON() throws Exception;
	public boolean isBeingListened();
}
