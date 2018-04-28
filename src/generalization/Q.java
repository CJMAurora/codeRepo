package generalization;

import java.util.ArrayList;

import stoStructure.*;

public class Q {
	private Op op;
	private double infoLoss;
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

}
class Op{
	private int locId;
	private int genLoc;
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
	
}