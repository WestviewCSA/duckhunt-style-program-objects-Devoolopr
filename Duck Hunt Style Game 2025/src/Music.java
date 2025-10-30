import javax.sound.sampled.*;
import java.io.InputStream;

public class Music {
    private Clip audioClip;
    private boolean loops;

    public Music(String fileName, boolean loops) {
        this.loops = loops;

        try {
            InputStream is = getClass().getResourceAsStream("/" + fileName);
            if (is == null) {
                throw new RuntimeException("Could not find audio file: " + fileName);
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(is);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);

            if (loops) {
                audioClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (audioClip != null) {
            audioClip.stop();
            audioClip.setFramePosition(0); // rewind to start
            if (loops) {
                audioClip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                audioClip.start();
            }
        }
    }

    public void stop() {
        if (audioClip != null && audioClip.isRunning()) {
            audioClip.stop();
        }
    }
}
