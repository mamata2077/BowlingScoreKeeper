package com.agile.BowlingApplication;


import java.util.HashMap;
import java.util.Map;

public class BowlingGameFrame {

	static int sequence = 0;
	Map<Integer,int[]> frameSet = new HashMap<Integer, int[]>();
	Map<Integer,Integer> totalScoreOfFrames = new HashMap<Integer,Integer>();
	
	public String createBowlingFrame(int firstThrowScore,int secondThrowScore) {
		int totalScoreOfBothThrows = firstThrowScore + secondThrowScore;

		//increment the sequence of frame
		sequence = sequence + 1  ;		

		 return addNewFrameIfTotalCountOfFramesIsLessThanTen(firstThrowScore,secondThrowScore,0,totalScoreOfBothThrows,sequence);
			
	}

	public String createBowlingFrame(int firstThrowScore,int secondThrowScore,int thirdThrowScore) {
		int totalScoreOfBothThrows = firstThrowScore + secondThrowScore ;

		//increment the sequence of frame
		sequence = sequence + 1  ;		

		 return addNewFrameIfTotalCountOfFramesIsLessThanTen(firstThrowScore,secondThrowScore,thirdThrowScore,totalScoreOfBothThrows,sequence);
			
	}


	private String addNewFrameIfTotalCountOfFramesIsLessThanTen(int firstThrowScore,int secondThrowScore, int thirdThrowScore, int totalScoreOfBothThrows,int currentSequence){
		if(currentSequence > 10){
			sequence = sequence - 1;
			return "Can not add more than 10 frames";
		}
	
			int[] scores ={firstThrowScore,secondThrowScore};
			frameSet.put(sequence, scores);
			calculateAndStoreCumulativeScoreOfCurrentFrame(totalScoreOfBothThrows,firstThrowScore, secondThrowScore,   thirdThrowScore);
			return "Frame created successfully with cumulative score as: "+ (totalScoreOfFrames.get(sequence));
		
	}

	
	private void calculateAndStoreCumulativeScoreOfCurrentFrame(int totalScoreOfBothThrowsOfCurrentFrame, int firstThrowScoreOfCurrentFrame,int secondThrowScore, int  thirdThrowScore) {
		//Get total score of previous frame
		if(sequence > 1){		
			
			int[] scoresOfPreviousFrame = frameSet.get(sequence -1);
			int totalScoreOfPreviousFrame = totalScoreOfFrames.get((sequence - 1));

			totalScoreOfPreviousFrame = addBonusOfSpareToPreviousFrameScore(scoresOfPreviousFrame, totalScoreOfPreviousFrame, firstThrowScoreOfCurrentFrame);
			
			totalScoreOfPreviousFrame = addBonusOfStrikeToPreviousFrameScore(scoresOfPreviousFrame, totalScoreOfPreviousFrame, totalScoreOfBothThrowsOfCurrentFrame);
				
			int cumulativeScore;
			
			if(sequence == 10){
				cumulativeScore= calculateCumulativeScoreOfTenthFrame( firstThrowScoreOfCurrentFrame, secondThrowScore, thirdThrowScore,  totalScoreOfPreviousFrame);
			}
			else{
			 	cumulativeScore = totalScoreOfBothThrowsOfCurrentFrame + totalScoreOfPreviousFrame;
			}

			totalScoreOfFrames.put(sequence,cumulativeScore);			
		}	
		else{	
			totalScoreOfFrames.put(sequence,totalScoreOfBothThrowsOfCurrentFrame);
			}

	}

	public int getTotalScoreOfCurrentFrame(){
		return totalScoreOfFrames.get(sequence);
	}

	public boolean isSpare(int scoreOfFirstThrow, int scoreOfSecondThrow) {
		if(scoreOfFirstThrow == 10){
			return false;
		}
	   if((scoreOfFirstThrow + scoreOfSecondThrow) == 10){
			return true ;
		}
		return false;
		
	}

	public boolean isStrike(int scoreOfFirstThrow) {
		if(scoreOfFirstThrow == 10){
		return true;
		}
		return false;
	}
	
	private int addBonusOfSpareToPreviousFrameScore(int[] scoresOfPreviousFrame,int totalScoreOfPreviousFrame,int firstThrowScoreOfCurrentFrame){
		if (isSpare(scoresOfPreviousFrame[0], scoresOfPreviousFrame[1])){
			//check if previous frame was spare then include next frame firstthrow into total score of previous frame
			totalScoreOfPreviousFrame = totalScoreOfPreviousFrame + firstThrowScoreOfCurrentFrame;
			totalScoreOfFrames.put(sequence - 1, totalScoreOfPreviousFrame );
		}
		return totalScoreOfPreviousFrame;
	}

	private int addBonusOfStrikeToPreviousFrameScore(int[] scoresOfPreviousFrame, int totalScoreOfPreviousFrame, int totalScoreOfBothThrowsOfCurrentFrame){
		if (isStrike(scoresOfPreviousFrame[0])){
			//check if previous frame was strike then include next frame both throw score into total score of previous frame
			totalScoreOfPreviousFrame = totalScoreOfPreviousFrame + totalScoreOfBothThrowsOfCurrentFrame;
			totalScoreOfFrames.put(sequence - 1, totalScoreOfPreviousFrame );
		}
		return totalScoreOfPreviousFrame;
	}

	private int calculateCumulativeScoreOfTenthFrame(int firstThrowScore,int secondThrowScore,int thirdThrowScore, int totalScoreOfPreviousFrame){
		if(isStrike(firstThrowScore) || isSpare(firstThrowScore,secondThrowScore) ){
			int totalOfThreeKnocks = firstThrowScore + secondThrowScore + thirdThrowScore;			
			return  totalScoreOfPreviousFrame + totalOfThreeKnocks;
		}
		else{
			return  totalScoreOfPreviousFrame + (firstThrowScore + secondThrowScore);
		}
	}

}
