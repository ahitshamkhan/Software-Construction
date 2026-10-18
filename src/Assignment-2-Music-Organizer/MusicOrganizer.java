import java.util.ArrayList;
import java.util.Iterator;

public class MusicOrganizer {

    private ArrayList<String> files;
    private ArrayList<Track> tracks;
    private MusicPlayer player;

    public MusicOrganizer() {
        files = new ArrayList<>();
        tracks = new ArrayList<>();
        player = new MusicPlayer();
    }

    // Exercises 4.1–4.3: file collection

    public void addFile(String filename) {
        files.add(filename);
    }

    public int numberOfFiles() {
        return files.size();
    }

    public void listFile(int index) {
        if (validIndex(index)) {
            System.out.println(files.get(index));
        } else {
            checkIndex(index);
        }
    }

    public void removeFile(int index) {
        if (validIndex(index)) {
            files.remove(index);
        } else {
            checkIndex(index);
        }
    }

    // Exercises 4.14–4.16: index validation

    public void checkIndex(int index) {
        if (!validIndex(index)) {
            if (files.isEmpty()) {
                System.out.println(
                        "The list is empty. No valid indexes.");
            } else {
                System.out.println("Valid index range: 0 to "
                        + (files.size() - 1));
            }
        }
    }

    public boolean validIndex(int index) {
        return index >= 0 && index < files.size();
    }

    // Exercises 4.20–4.24: listing files

    public void listAllFiles() {
        for (String filename : files) {
            System.out.println(filename);
        }
    }

    public void listAllFilesWithIndex() {
        int index = 0;

        for (String filename : files) {
            System.out.println(index + ": " + filename);
            index++;
        }
    }

    // Exercises 4.25–4.26: search filenames

    public void listMatching(String searchString) {
        boolean found = false;

        for (String filename : files) {
            if (filename.contains(searchString)) {
                System.out.println(filename);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching files found.");
        }
    }

    // Exercises 4.27–4.28: tracks and artist playback

    public void addTrack(Track track) {
        tracks.add(track);
    }

    public void playTracksByArtist(String artist) {
        for (Track track : tracks) {
            if (track.getArtist().equalsIgnoreCase(artist)) {
                player.playAndWait(track.getFilename());
                track.incrementPlayCount();
            }
        }
    }

    // Exercise 4.38: start a track and stop any currently playing track

    public void playTrack(Track track) {
        player.startPlaying(track.getFilename());
        track.incrementPlayCount();
    }

    public void stopPlaying() {
        player.stop();
    }

    // Exercise 4.39: remove tracks whose titles contain the search text

    public void removeTracks(String searchString) {
        Iterator<Track> iterator = tracks.iterator();
        int removedCount = 0;

        while (iterator.hasNext()) {
            Track track = iterator.next();

            if (track.getTitle().contains(searchString)) {
                iterator.remove();
                removedCount++;
            }
        }

        System.out.println("Tracks removed: " + removedCount);
    }

    // Display tracks and play counts for testing

    public void listAllTracks() {
        for (Track track : tracks) {
            System.out.println(
                    track.getTitle()
                            + " | Artist: " + track.getArtist()
                            + " | Plays: " + track.getPlayCount()
                            + " | Rating: " + track.getRating()
            );
        }
    }

    public int numberOfTracks() {
        return tracks.size();
    }
}