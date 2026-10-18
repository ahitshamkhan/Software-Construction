import java.io.File;

public class Main {

    public static void main(String[] args) {

        String audioPath =
                "Software-Construction/src/Assignment-2-Music-Organizer/audio/sample.wav";

        File audioFile = new File(audioPath);

        // Check audio file
        System.out.println("========== AUDIO FILE TEST ==========");
        System.out.println("Working directory: "
                + System.getProperty("user.dir"));
        System.out.println("File exists: " + audioFile.exists());
        System.out.println("File path: " + audioFile.getAbsolutePath());

        if (!audioFile.exists() || !audioFile.isFile()) {
            System.out.println("ERROR: Audio file not found!");
            return;
        }

        MusicOrganizer organizer = new MusicOrganizer();

        // Test file collection
        System.out.println("\n========== FILE COLLECTION TEST ==========");

        organizer.addFile("song1.wav");
        organizer.addFile("song2.wav");
        organizer.addFile("rock-song.wav");

        System.out.println("Number of files: "
                + organizer.numberOfFiles());

        System.out.println("\nAll files with indexes:");
        organizer.listAllFilesWithIndex();

        System.out.println("\nSearch for 'rock':");
        organizer.listMatching("rock");

        System.out.println("\nTest invalid index:");
        organizer.listFile(10);

        // Create tracks
        System.out.println("\n========== TRACK COLLECTION TEST ==========");

        Track track1 = new Track(
                "Sample Song 1",
                "Artist One",
                audioPath
        );

        Track track2 = new Track(
                "Sample Song 2",
                "Artist Two",
                audioPath
        );

        Track track3 = new Track(
                "Sample Song 3",
                "Artist One",
                audioPath
        );

        organizer.addTrack(track1);
        organizer.addTrack(track2);
        organizer.addTrack(track3);

        // Test the extra Track field
        track1.setRating(5);

        System.out.println("All tracks:");
        organizer.listAllTracks();

        // Exercise 4.27: play tracks by artist sequentially
        System.out.println("\n========== PLAY TRACKS BY ARTIST ==========");

        organizer.playTracksByArtist("Artist One");

        System.out.println("\nTracks after playback:");
        organizer.listAllTracks();

        // Exercise 4.39: remove tracks by title
        System.out.println("\n========== REMOVE TRACKS BY TITLE ==========");

        organizer.removeTracks("3");

        System.out.println("\nRemaining tracks:");
        organizer.listAllTracks();

        System.out.println("\n========== PROGRAM FINISHED ==========");
    }
}