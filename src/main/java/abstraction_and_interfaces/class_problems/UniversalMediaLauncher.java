package abstraction_and_interfaces.class_problems;

public class UniversalMediaLauncher {

    interface Playable {
        String play();

        String play(int fromSecond);

        String pause();
    }

    abstract static class MediaFile {

        private static final int FILE_ID_BASE = 1000;
        private static int filesCreated = 0;

        private final String fileId;

        public MediaFile() {
            filesCreated++;
            this.fileId = "MF-" + (FILE_ID_BASE + filesCreated);
        }

        public abstract String getFormatInfo();

        public String getFileId() {
            return fileId;
        }
    }

    static class AudioFile extends MediaFile implements Playable {

        private final String title;

        public AudioFile(String title) {
            this.title = title;
        }

        @Override
        public String play() {
            return "Playing audio: " + title;
        }

        @Override
        public String play(int fromSecond) {
            int minutes = fromSecond / 60;
            int seconds = fromSecond % 60;
            return "Playing audio: " + title + " from " + minutes + ":" + String.format("%02d", seconds);
        }

        @Override
        public String pause() {
            return "Paused audio: " + title;
        }

        @Override
        public String getFormatInfo() {
            return "Audio file, ID: " + getFileId();
        }
    }

    static class Podcast implements Playable {

        private final String showName;
        private final int episodeNumber;

        public Podcast(String showName, int episodeNumber) {
            this.showName = showName;
            this.episodeNumber = episodeNumber;
        }

        @Override
        public String play() {
            return "Streaming episode " + episodeNumber + " of " + showName;
        }

        @Override
        public String play(int fromSecond) {
            int minutes = fromSecond / 60;
            int seconds = fromSecond % 60;
            return "Streaming episode " + episodeNumber + " of " + showName
                    + " from " + minutes + ":" + String.format("%02d", seconds);
        }

        @Override
        public String pause() {
            return "Paused episode " + episodeNumber + " of " + showName;
        }
    }

    static void launchAll(Playable[] items) {
        if (items == null) {
            return;
        }
        for (Playable item : items) {
            if (item != null) {
                System.out.println(item.play());
            }
        }
    }

    public static void main(String[] args) {
        AudioFile audioFile = new AudioFile("Morning Jazz");
        System.out.println(audioFile.play());
        System.out.println(audioFile.play(30));
        System.out.println(audioFile.getFormatInfo());

        System.out.println();
        Podcast podcast = new Podcast("Tech Talk", 12);
        System.out.println(podcast.play());

        System.out.println();
        // Upcasting: an AudioFile object stored in a Playable interface-typed variable.
        Playable reference = audioFile;
        launchAll(new Playable[] { reference, podcast });

        System.out.println();
        System.out.println(audioFile.pause());
        System.out.println(podcast.pause());

        System.out.println();
        // IS-A versus CAN-DO: AudioFile extends MediaFile because an audio file genuinely is a
        // stored media file - it has a real fileId and a real format to report, so it inherits
        // that shared state and the getFormatInfo() contract. Podcast only implements Playable
        // because it is streamed live: it owns no file and has no format to describe, so forcing
        // it under MediaFile would hand it a fileId and a getFormatInfo() it would have to fake.
        // Both can be played, but only one of them is a file - so only one of them extends.
        System.out.println("AudioFile IS-A MediaFile; Podcast only CAN-DO Playable.");
    }
}
