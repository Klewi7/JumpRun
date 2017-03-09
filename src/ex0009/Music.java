
package ex0009;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Music implements Runnable
{
private void playSound()
{
  try
  {
    AudioInputStream audioInputStream =
       AudioSystem.getAudioInputStream(
         new File(
           "F:/JumpRun/src/music/Mariachi.wav").getAbsoluteFile());

    Clip clip = AudioSystem.getClip();
    clip.open(audioInputStream);
    clip.start();
  }
  catch(Exception ex)
  {
    System.out.println("Error with playing sound.");
    ex.printStackTrace();
  }
}
 @Override
  public void run()
  {
   playSound();
  }
}
 

