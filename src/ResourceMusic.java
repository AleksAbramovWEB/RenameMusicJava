import java.io.File;
public class ResourceMusic {

    static final short DIR_CHUNK = 500;

    static final String FORMAT_NUMBER = "%04d";

    static final String DIR_NAME = "dir_";

    static final String[] REPLACES = {
        "(www.lightaudio.ru)", "(www.hotplayer.ru)", "(zaycev.net)", "(Audio-VK4.ru)", "(muzofon.com)", "(muzofon.com)", "(NaitiMP3.ru)", "(musmore.com)", "(BiffHard.click)",
        "(music4love.net)", "(boxradio.ru)", "(iPleer.fm)", "(5music.ru)", "(musportal.org)", "(Cool.DJ)", "(www.petamusic.ru)", "(soundbass.net)", "(music7s.com)", "(www.petamusic.ru)",
        "(audiopoisk.com)", "(mp3-party.ru)", "(zf.fm)", "(New-Music24.ru)", "(megapesni.com)", "(musicuz.com)", "myzuka.ru_", "muzlome_"
    };

    private String folderName = null;

    private String newFileName = null;

    private File resourceFile;

    private File newFile = null;

    private int index;

    public File getResourceFile() {
        return resourceFile;
    }

    public ResourceMusic setIndex(int index) {
        this.index = index;
        return this;
    }

    public ResourceMusic setResourceFile(File resourceFile) {
        this.resourceFile = resourceFile;
        return this;
    }

    public File getNewFile() {
        if (newFile == null) {
            String fileName = RenameMusic.pathTo + getFolderName() + "/" + getNewFileName();

            newFile = new File(fileName);
        }

        return newFile;
    }

    private String getNewFileName() {
        if (newFileName == null) {
            String number = String.format(FORMAT_NUMBER, index);
            newFileName = number + " - " + resourceFile.getName();

            for (String replace : REPLACES) {
                newFileName = newFileName.replace(replace, "");
            }
        }
        return newFileName;
    }

    private String getFolderName() {
        if (folderName == null) {
            byte dirNumber = 1;
            for (int i = 1; i <= index; i++) {
                if ((i % DIR_CHUNK) == 0) {
                    dirNumber++;
                }
            }
            folderName = DIR_NAME + dirNumber;
        }

        return folderName;
    }
}