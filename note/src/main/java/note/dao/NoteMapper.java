package note.dao;

import java.util.List;

import note.entity.Note;
import note.utils.Paging;

public interface NoteMapper {
	List<Note> selectNotes(String userId);
	Note selectNote(String noteId);
	Integer updateNote(Note note);
	Integer insertNote(Note note);
	Integer deleteNote(String noteId);
	List<Note> selectNotesBetween(Paging paging);
}
