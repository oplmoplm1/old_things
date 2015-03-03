package note.daoimp;

import java.util.Date;
import java.util.List;

import note.entity.Note;
import note.utils.CommonUtils;

import org.junit.After;
import org.junit.Test;

public class NoteDaoTest {
	String id;
	@After
	public void testDaoImpSelect(){
		Note no=new NoteMapperImp().selectNote("1399173798452436");
		System.out.println(no.toString());
		System.out.println("Select method has run.");
		System.out.println("-------------------------------------");
	}
	@Test
	public void testUpdate(){
		String d = CommonUtils.getTimeString(new Date());
		Note note = new Note("12344", "changeTitle",d , "This is new content and test.", "001");
		new NoteMapperImp().updateNote(note);
		System.out.println("Update method has run.");
		System.out.println("-------------------------------------");
	}
	@Test
	public void testInsert(){
		id = CommonUtils.generateKey();
		String time = CommonUtils.getTimeString(new Date());
		Note note = new Note(id, "newTitle", time, "This is new content and test.", "001");
		new NoteMapperImp().insertNote(note);
		Note no=new NoteMapperImp().selectNote(id);
		System.out.println(no);
		System.out.println("Insert method has run.");
		System.out.println("-------------------------------------");
	}
	@Test
	public void testSelectNotes(){
		List<Note> no=new NoteMapperImp().selectNotes("001");
		for(Note note: no){
			System.out.println("中文"+note);
			
		}
	}
	@After
	public void testDelete(){
		new NoteMapperImp().deleteNote(id);
		System.out.println("Delete method has run.");
		System.out.println("-------------------------------------");
	}
}
