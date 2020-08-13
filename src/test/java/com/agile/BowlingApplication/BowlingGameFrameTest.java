package com.agile.BowlingApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class BowlingGameFrameTest {
    
    BowlingGameFrame frame = new BowlingGameFrame();

    @Before
    public void setup(){
    BowlingGameFrame.sequence = 0;
    }

    @Test
    public void shouldCreateAnEmptyBowlingFrame(){
                     
       String message =  frame.createBowlingFrame(0,0);
       
       assertEquals("Frame created successfully with cumulative score as: "+ 0,message);

    }

    @Test
    public void shouldCreateBowlingFrameWithScoreOfFirstThrow(){
        String message = frame.createBowlingFrame(3,0);
        assertEquals("Frame created successfully with cumulative score as: "+ 3,message);

    }

    @Test
    public void shouldCreateBowlingFrameWithScoreOfFirstAndSecondThrow(){
        String message = frame.createBowlingFrame(3,4);
        assertEquals("Frame created successfully with cumulative score as: "+ 7,message);

    }

    
    @Test
    public void shouldNotAddNewFrameAfterTenthFrame(){
        frame.createBowlingFrame(2,3);
        frame.createBowlingFrame(2,3);
        frame.createBowlingFrame(2,3);
        frame.createBowlingFrame(2,3);
        frame.createBowlingFrame(2,3);
        frame.createBowlingFrame(2,3);
        frame.createBowlingFrame(2,3);
        frame.createBowlingFrame(2,3);
        frame.createBowlingFrame(2,3);
        frame.createBowlingFrame(2,3);
       String message = frame.createBowlingFrame(2,3);
        assertEquals("Can not add more than 10 frames", message);
    }

    @Test
    public void shouldCalculateTotalScoreOfFrameAsSumOfBothThrowCountOnlyWhenItsFirstFrame(){
    
       //create first frame
       frame.createBowlingFrame(3,4);
       int score = frame.getTotalScoreOfCurrentFrame();
       assertEquals(1,BowlingGameFrame.sequence);
       assertEquals(7,score );
    }

    @Test
    public void shouldCalculateCumulativeTotalScoreOfFrameWhenFrameCountIsMoreThanOne(){
        //create two frames
        frame.createBowlingFrame(3,4);
        frame.createBowlingFrame(0,3);
        frame.createBowlingFrame(5,3);
        frame.createBowlingFrame(3,3);
        int score = frame.getTotalScoreOfCurrentFrame();
        assertEquals(4,BowlingGameFrame.sequence);
        assertEquals(24, score);
    }

    @Test
    public void shouldReturnTrueIfItsSpare(){
        assertTrue(frame.isSpare(5,5));
    }

    @Test
    public void shouldReturnFalseIfItsNotSpare(){
        assertFalse(frame.isSpare(4,5));
    }

    @Test
    public void shouldReturnFalseIfScoreOfFirstThrowIsTen(){
        assertFalse(frame.isSpare(10,0));
    }

    @Test
    public void shouldCalculateCumulativeScoreOfPreviousFrameAddingScoreOfFirstThrowOfCurrentFrameWhenPreviousFrameIsSpare(){
        //create frames
        frame.createBowlingFrame(7,3);
        frame.createBowlingFrame(4,3);
        //frame.createBowlingFrame(5,3);
        int scoreOfSecondFrame = frame.totalScoreOfFrames.get(1);
        assertEquals(14,scoreOfSecondFrame );      

    }

    @Test
    public void shouldCalculateCumulativeScoreOfCurrentFrameCorrectlyIfPreviousFrameIsSpare(){
        //create frames
        frame.createBowlingFrame(4,3);
        frame.createBowlingFrame(7,3);
        frame.createBowlingFrame(5,3);
        int scoreOfSecondFrame = frame.totalScoreOfFrames.get(3);
        assertEquals(30,scoreOfSecondFrame );      

    }


    @Test
    public void shouldReturnTrueIfItsStrike(){
        assertTrue(frame.isStrike(10));
    }

    @Test
    public void shouldReturnFalseIfItsNotStrike(){
        assertFalse(frame.isStrike(3));
    }

    @Test
    public void shouldCalculateCumulativeScoreOfPreviousFrameAddingTotalKnocksOfCurrentFrameWhenPreviousFrameIsStrike(){
        //create frames
        frame.createBowlingFrame(10,0);
        frame.createBowlingFrame(4,3);
       
        int scoreOfFirstFrame = frame.totalScoreOfFrames.get(1);
        assertEquals(17,scoreOfFirstFrame );      

    }

    @Test
    public void shouldCalculateCumulativeScoreOfCurrentFrameCorrectlyIfPreviousFrameIsStrike(){
        //create frames
        frame.createBowlingFrame(4,3);
        frame.createBowlingFrame(10,0);
        frame.createBowlingFrame(5,3);
        int scoreOfSecondFrame = frame.totalScoreOfFrames.get(3);
        assertEquals(33,scoreOfSecondFrame );      
    }

    @Test
    public void shouldNotGetFailureMessageWhenTotalScoreOfBothThrowsExceedsTenForTenthFrameWhenItsStrike(){
        frame.createBowlingFrame(2,3);
        frame.createBowlingFrame(2,3);
        frame.createBowlingFrame(2,3);
        frame.createBowlingFrame(2,3);
        frame.createBowlingFrame(2,3);
        frame.createBowlingFrame(2,3);
        frame.createBowlingFrame(2,3);
        frame.createBowlingFrame(2,3);
        frame.createBowlingFrame(2,3);
        String message= frame.createBowlingFrame(10,10,3);
    
        assertEquals("Frame created successfully with cumulative score as: "+ 68 ,message);
    }

    
    @Test
    public void shouldNotGetFailureMessageWhenTotalScoreOfBothThrowsExceedsTenForTenthFrameWhenItsSpare(){
        frame.createBowlingFrame(1,4);
        frame.createBowlingFrame(4,5);
        frame.createBowlingFrame(6,4);
        frame.createBowlingFrame(5,5);
        frame.createBowlingFrame(10,0);
        frame.createBowlingFrame(0,1);
        frame.createBowlingFrame(7,3);
        frame.createBowlingFrame(6,4);
        frame.createBowlingFrame(10,0);
        String message= frame.createBowlingFrame(2,8,6);
    
        assertEquals("Frame created successfully with cumulative score as: "+133 ,message);
    }

   

}