package note.utils;

public final class Paging {
	
	public static final int MAXSIZE=5;
	private String userId;
	public static int getMaxsize() {
		return MAXSIZE;
	}

	private int recordSize;
	private int maxPage;
	private int curPage;
	private int curStart;
	
	public Paging(int recordSize,String userId) {
		super();
		this.userId =userId;
		this.recordSize = recordSize;
		if(recordSize%MAXSIZE==0){
			this.maxPage=recordSize/MAXSIZE ;
			if(maxPage==0){
				maxPage=1;
			}
		}else{
			this.maxPage=recordSize/MAXSIZE+1;
		}
		this.curPage=0;
		this.setCurStart(0);
	}
	
	public int getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
		this.setCurStart(curPage * MAXSIZE);
	}
	public void setRecordSize(int recordSize) {
		this.recordSize = recordSize;
	}
	public int getRecordSize() {
		return recordSize;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setCurStart(int curStart) {
		this.curStart = curStart;
	}

	public int getCurStart() {
		return curStart;
	}
	
}
