package note.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import note.dao.NoteMapper;
import note.dao.UserMapper;
import note.daoimp.NoteMapperImp;
import note.daoimp.UserMapperImp;
import note.entity.Note;
import note.entity.User;
import note.utils.CommonUtils;
import note.utils.Paging;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	private transient UserMapper userMapper=new UserMapperImp();
	private transient NoteMapper noteMapper= new NoteMapperImp();
	private static final Logger LOGGER=Logger.getAnonymousLogger();
	public static final String ISO8859 ="ISO-8859-1";
	public static final String UTF8 ="UTF-8";
	
	public void updateUser(User user){
		userMapper.updateUser(user);
	}
	
	public void addUser(User user){
		String key = CommonUtils.generateKey();
		user.setUserId(key);
		userMapper.insertUser(user);
	}
	
	public Boolean isPassWordRight(User user){
    	User userInDataBase = userMapper.selectUserByName(user.getUserName());
    	if(userInDataBase==null){ 
    		return false;
    	}
		return user.getUserPassword().equals(userInDataBase.getUserPassword());
	}
	
	public List<Note> getNotes(String userName){
		User userInDataBase=userMapper.selectUserByName(userName);
		return noteMapper.selectNotes(userInDataBase.getUserId());
		
	}
	public User getUser(String userName){
		return userMapper.selectUserByName(userName);
	}
	public Boolean addNote(Note note,String userName){
		User userInDataBase=userMapper.selectUserByName(userName);
		String userId = userInDataBase.getUserId();
		String time = CommonUtils.getTimeString(new Date());
		try {
			note.setNoteTitle(new String(note.getNoteTitle().getBytes(ISO8859),UTF8));
			note.setNoteContent(new String(note.getNoteContent().getBytes(ISO8859),UTF8));
		} catch (UnsupportedEncodingException e) {
			LOGGER.log(Level.FINE, "添加时候转换问题", e);
		}
		note.setNoteId(CommonUtils.generateKey());
		note.setNoteTime(time);
		note.setUserId(userId);
		Integer rs = noteMapper.insertNote(note);
		return (rs==1)?true:false;
	}
	public List<Note> getNotesAtPage(Integer page,String userName){
		String userId=userMapper.selectUserByName(userName).getUserId();
		List<Note> a = new NoteMapperImp().selectNotes(userId);
		Paging paging = new Paging(a.size(),userId);
		if(page<1){
			paging.setCurPage(0);
		}else if(page>paging.getMaxPage()){
			paging.setCurPage(paging.getMaxPage()-1);
		}else{
			paging.setCurPage(page-1);
		}
		return noteMapper.selectNotesBetween(paging);
	}
	public Note getNote(String noteId){
		return noteMapper.selectNote(noteId);
	}
	public Boolean updateNote(Note note){
		note.setNoteTime(CommonUtils.getTimeString(new Date()));
		Integer rs=noteMapper.updateNote(note);
		return (rs==1)?true:false;
	}
	public Boolean deleteNote(String noteId){
		Integer rs = noteMapper.deleteNote(noteId);
		return (rs==1)?true:false;
	}
}
