import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.io.File;
import java.util.concurrent.CountDownLatch;

public class MusicPlayer {

    private Clip currentClip;

    public void playAndWait(String filename) {
        stop();

        File audioFile = new File(filename);
        Clip clip = null;

        try (AudioInputStream audioStream =
                     AudioSystem.getAudioInputStream(audioFile)) {

            clip = AudioSystem.getClip();
            CountDownLatch finished = new CountDownLatch(1);
            Clip playingClip = clip;

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP
                        && playingClip.getFramePosition()
                        >= playingClip.getFrameLength()) {
                    finished.countDown();
                }
            });

            clip.open(audioStream);
            currentClip = clip;

            System.out.println("Playing: " + audioFile.getName());
            clip.start();

            finished.await();
            System.out.println("Playback finished.");

        } catch (Exception e) {
            System.out.println("Could not play file: " + filename);
            System.out.println("Reason: " + e.getMessage());

        } finally {
            if (clip != null) {
                clip.close();

                if (currentClip == clip) {
                    currentClip = null;
                }
            }
        }
    }

    public void startPlaying(String filename) {
        stop();

        File audioFile = new File(filename);

        try (AudioInputStream audioStream =
                     AudioSystem.getAudioInputStream(audioFile)) {

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            currentClip = clip;

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP
                        && clip.getFramePosition() >= clip.getFrameLength()) {
                    clip.close();

                    if (currentClip == clip) {
                        currentClip = null;
                    }
                }
            });

            System.out.println("Playing: " + audioFile.getName());
            clip.start();

        } catch (Exception e) {
            System.out.println("Could not play file: " + filename);
            System.out.println("Reason: " + e.getMessage());
        }
    }

    public void stop() {
        if (currentClip != null) {
            Clip clipToStop = currentClip;
            currentClip = null;

            if (clipToStop.isOpen()) {
                clipToStop.stop();
                clipToStop.close();
            }
        }
    }
}