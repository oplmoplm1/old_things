package note.entity;


public class Note {
	private String noteId ;
	private String noteTitle ;
	private String noteTime;
	private String noteContent;
	private String userId;
	public String getNoteId() {
		return noteId;
	}
	 
	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}
	public String getNoteTitle() {
		return noteTitle;
	}
	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}
	
	public String getNoteContent() {
		return noteContent;
	}
	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Note(String noteId, String noteTitle, String time,
			String noteContent, String userId) {
		super();
		this.noteId = noteId;
		this.noteTitle = noteTitle;
		this.noteTime = time;
		this.noteContent = noteContent;
		this.userId = userId;
	}
	
	public String getNoteTime() {
		return noteTime;
	}
	public void setNoteTime(String noteTime) {
		this.noteTime = noteTime;
	}
	public Note() {
		super();
	}
	@Override
	public String toString() {
		return "Note [noteContent=" + noteContent + ", noteId=" + noteId
				+ ", noteTime=" + noteTime + ", noteTitle=" + noteTitle
				+ ", userId=" + userId + "]";
	}

	
}
