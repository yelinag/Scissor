package Product;

public class RatingClass {

	private int uid;
	private int pid;
	private String comment;
	private int ratingVal1;
	private int ratingVal2;
	private int ratingVal3;
	private int ratingVal4;
	private int ratingVal5;



public RatingClass(int uid, int pid, int ratingVal1, int ratingVal2, int ratingVal3,
		int ratingVal4, int ratingVal5, String comment) {
	super();
	this.uid = uid;
	this.pid = pid;
	this.ratingVal1 = ratingVal1;
	this.ratingVal2 = ratingVal2;
	this.ratingVal3 = ratingVal3;
	this.ratingVal4 = ratingVal4;
	this.ratingVal5 = ratingVal5;
	this.comment = comment;
}



public String getComment() {
	return comment;
}



public void setComment(String comment) {
	this.comment = comment;
}



public RatingClass(int uid, int pid, int ratingVal1, int ratingVal2,
			int ratingVal3, int ratingVal4, int ratingVal5) {
		super();
		this.uid = uid;
		this.pid = pid;
		this.ratingVal1 = ratingVal1;
		this.ratingVal2 = ratingVal2;
		this.ratingVal3 = ratingVal3;
		this.ratingVal4 = ratingVal4;
		this.ratingVal5 = ratingVal5;
	}



public int getUid() {
	return uid;
}



public void setUid(int uid) {
	this.uid = uid;
}



public int getPid() {
	return pid;
}



public void setPid(int pid) {
	this.pid = pid;
}



public int getRatingVal1() {
	return ratingVal1;
}



public void setRatingVal1(int ratingVal1) {
	this.ratingVal1 = ratingVal1;
}



public int getRatingVal2() {
	return ratingVal2;
}



public void setRatingVal2(int ratingVal2) {
	this.ratingVal2 = ratingVal2;
}



public int getRatingVal3() {
	return ratingVal3;
}



public void setRatingVal3(int ratingVal3) {
	this.ratingVal3 = ratingVal3;
}



public int getRatingVal4() {
	return ratingVal4;
}



public void setRatingVal4(int ratingVal4) {
	this.ratingVal4 = ratingVal4;
}



public int getRatingVal5() {
	return ratingVal5;
}



public void setRatingVal5(int ratingVal5) {
	this.ratingVal5 = ratingVal5;
}

}
