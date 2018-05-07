package generalization;



public class Q implements Comparable<Q>{
	private Op op;
	private double infoLoss=1;
	private double anonyGain;
	private double score;
	public Op getOp() {
		return op;
	}
	public void setOp(Op op) {
		this.op = op;
	}
	public double getInfoLoss() {
		return infoLoss;
	}
	public void setInfoLoss(double infoLoss) {
		this.infoLoss = infoLoss;
	}
	public double getAnonyGain() {
		return anonyGain;
	}
	public void setAnonyGain(double anonyGain) {
		this.anonyGain = anonyGain;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	@Override
	public int compareTo(Q arg0) {
		// TODO Auto-generated method stub
		if (this.score > arg0.score)
			return -1;
		else if (this.score < arg0.score)
			return 1;
		return 0;
	}

}
class Op{
	private int locId;
	private int genLoc;
	private int trajId;
	public int getLocId() {
		return locId;
	}
	public void setLocId(int locId) {
		this.locId = locId;
	}
	public int getGenLoc() {
		return genLoc;
	}
	public void setGenLoc(int genLoc) {
		this.genLoc = genLoc;
	}
	public int getTrajId() {
		return trajId;
	}
	public void setTrajId(int trajId) {
		this.trajId = trajId;
	}
	public boolean equals(Op op) {
		if(op==null) {
			return false;
		}
		if(this.locId==op.locId&&this.genLoc==op.genLoc&&this.trajId==op.trajId)
			return true;
		else
			return false;
	}
	
}