package note.controller;
import java.util.List;

import note.entity.Note;
import note.entity.User;
import note.service.UserService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@EnableAutoConfiguration
@SessionAttributes({"userName","page"})
public class UserAction {
	public static final String NOTELIST ="redirect:noteList";
	public static final String USERNAME ="userName";
	public static final String INDEX ="redirect:index";
	public static final String PAGE ="page";
	
	private transient UserService userService = new UserService();
	
	@RequestMapping("update_user_page")
	public String updateUserPage(Model model){
		String userName =(String)model.asMap().get(USERNAME);
		User u =userService.getUser(userName);
		model.addAttribute("user", u);
		return "user/update";
	}
	@RequestMapping("update_user")
	public String updateUser(@RequestParam(value="rePassword", required=true)String repass, User user, Model model){
		if(repass.equals(user.getUserPassword())){
			userService.updateUser(user);
			return NOTELIST;
		}else {
			return "redirect:update_user_page";
		}
	}
	@RequestMapping("signup")
	public String signUp(){
		return "user/signup";
	}
	@RequestMapping("sign_up")
	public String sign(@RequestParam(value="rePassword", required=true)String repass,User user){
		if(repass.equals(user.getUserPassword())){
			userService.addUser(user);
			return INDEX;
		}else {
			return "redirect:signup";
		}
	}
    @RequestMapping("index")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "index12345";
    }
    @RequestMapping("loginPage")
    public String loginPage(Model model){
    	if(model.asMap().get(USERNAME)==null){
    		return "user/login";
    	}else{
    		return NOTELIST;
    	}
    }

	@RequestMapping("login")
	public String login(User user, Model model) {
		Boolean rs = userService.isPassWordRight(user);
		if (rs) {
			String userName = user.getUserName();
			model.addAttribute(USERNAME, userName);
			model.addAttribute(PAGE,Integer.valueOf(1));
			return NOTELIST;
		} else {
			return INDEX;
		}
	}

	@RequestMapping("noteList")
	public String noteList(@RequestParam(PAGE) Integer page,Model model) {
		String userName = (String)model.asMap().get(USERNAME);
		if (userName != null) {
			List<Note> notes=userService.getNotesAtPage(page, userName);
			model.addAttribute("notes",notes);
			if(page<1){
				model.addAttribute(PAGE, 1);
			}else{
				model.addAttribute(PAGE, page);
			}
			return "note/notelist";
		} else {
			return INDEX;
		}
	}
	@RequestMapping("add_note")
	public String addNote(){
		return "note/addnote";
	}
	@RequestMapping("add_a_note")
	public String addNote(Note note,Model model){
		
		userService.addNote(note, (String)model.asMap().get(USERNAME));
		return NOTELIST;
	}
	@RequestMapping("get_one_note")
	public String getNote(@RequestParam("note_id") String noteId, Model model){
		Note note = userService.getNote(noteId);
		if(note==null){ 
			return NOTELIST;
		}
		model.addAttribute("note",note);
		return "note/note";
	}
	@RequestMapping("update")
	public String updateNote(Note note,Model model){
		userService.updateNote(note);
		return NOTELIST;
	}
	@RequestMapping("delete_note")
	public String deleteNote(@RequestParam("note_id") String noteId){
		userService.deleteNote(noteId);
		return NOTELIST;
	}
    public static void main(String[] args) {
        SpringApplication.run(UserAction.class, args);
    }

}