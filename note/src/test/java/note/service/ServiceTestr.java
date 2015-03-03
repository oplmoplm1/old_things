package note.service;

import java.util.List;

import note.entity.Note;

import org.junit.Test;

public class ServiceTestr {
	@Test
	public void testPaging(){
		List<Note> rs=new UserService().getNotesAtPage(1, "001");
		for(Note t :rs){
			System.out.println(t);
		}
	}
}
