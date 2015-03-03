package note.daoimp;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import note.dao.NoteMapper;
import note.entity.Note;
import note.utils.BaseDao;
import note.utils.Paging;

public class NoteMapperImp extends BaseDao implements NoteMapper{

	public Note selectNote(String noteId) {
		NoteMapper sss = getSessionFactory().openSession().getMapper(NoteMapper.class);
		return sss.selectNote(noteId);
	}
	public Integer updateNote(Note note){
		SqlSession session = getSessionFactory().openSession();
		try{
			NoteMapper noteMapper = session.getMapper(NoteMapper.class);
			return 	noteMapper.updateNote(note);
		}finally{
			session.commit();
			session.close();
		}
	}

	public Integer insertNote(Note note) {
		SqlSession session = getSessionFactory().openSession();
		try{
		NoteMapper noteMapper = session.getMapper(NoteMapper.class);
		return noteMapper.insertNote(note);
		}finally{
			session.commit();
			session.close();
		}
	}
	public Integer deleteNote(String noteId) {
		SqlSession session = getSessionFactory().openSession();
		try{
		NoteMapper noteMapper = session.getMapper(NoteMapper.class);
		return noteMapper.deleteNote(noteId);
		}finally{
			session.commit();
			session.close();
		}
	}
	public List<Note> selectNotes(String userId) {
		NoteMapper sss = getSessionFactory().openSession().getMapper(NoteMapper.class);
		return sss.selectNotes(userId);
	}
	public List<Note> selectNotesBetween(Paging paging) {
		NoteMapper sss = getSessionFactory().openSession().getMapper(NoteMapper.class);
		return sss.selectNotesBetween(paging);
	}
	

}
