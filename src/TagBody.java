import com.restfb.Facebook;

public class TagBody {
	
	@Facebook("tag_text")
	private String name;
	
	@Facebook("tag_uid")
	private String id;
	
	public TagBody(String id, String tagt) {
		this.id = id;
		this.name = tagt;
	}
	

}
