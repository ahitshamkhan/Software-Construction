import java.util.concurrent.atomic.AtomicInteger;

public class Track {

    private String title;
    private String artist;
    private String filename;
    private int playCount;
    private int rating;

    public Track(String title, String artist, String filename) {
        this.title = title;
        this.artist = artist;
        this.filename = filename;
        this.playCount = 0;
        this.rating = 0;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getFilename() {
        return filename;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void resetPlayCount() {
        playCount = 0;
    }

    public void incrementPlayCount() {
        playCount++;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}