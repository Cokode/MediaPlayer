import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;

import static java.lang.System.in;

public class Albums {


    public static ArrayList<Songs> mySongs = new ArrayList<>();
    public static ArrayList<Albums> musicAlbums = new ArrayList<>();
    public static ArrayList<Songs> songPlayList = new ArrayList<>();
    public static ArrayList<Songs> deviceSongs = new ArrayList<>();
    public static String albumName;

    public static ArrayList<Albums> testingAlbum = new ArrayList<>();

    public Albums(ArrayList<Songs> mySongs, String albumName) {
        this.mySongs = mySongs;
        this.albumName = albumName;
    }


    public void newAlbum(String nameOfAlbum, ArrayList<Songs> songsInAlbum){
        testingAlbum.add(createANewAlbum(songsInAlbum, nameOfAlbum));
    }

    public void addAlbum(){
        Scanner newScanner = new Scanner(in);
        System.out.println("Enter name of album");
        String name = newScanner.nextLine();

        System.out.println("Enter song title");
        String songTitle = newScanner.nextLine();

        System.out.println("Enter song duration");
        double songDuration = newScanner.nextDouble();

        Songs freshSongs = new Songs(songTitle, songDuration);
        ArrayList<Songs> newArraySongs = new ArrayList<>();
        newArraySongs.add(freshSongs);
        newAlbum(name, newArraySongs);
    }

    public void showAllAlbumAndSongs() {

        if(testingAlbum.isEmpty()){
            System.out.println("Album is empty. ");
        } else {
            for(int y = 0; y < testingAlbum.size(); y++) {
                String albName = testingAlbum.get(y).getAlbumName();
                ArrayList<Songs> songs = testingAlbum.get(y).getMySongs();
                System.out.println("\n" + (y+1) + " ALBUM --> " + albName.toUpperCase());

                if(songs.isEmpty()) {
                    System.out.println("This album is empty.");
                } else {
                    displaySongList(songs);
                }
            }
        }
    }

    public ArrayList<Songs> getMySongs() {
        return mySongs;
    }

    public String getAlbumName() {
        return albumName;
    }

    public ArrayList<Albums> getMyAlbums() {
        return musicAlbums;
    }

    public ArrayList<Songs> getSongsListIterator() {
        return songPlayList;
    }

    public ArrayList<Songs> getDeviceSongs(){
        return deviceSongs;
    }
    private String checkSongList(String songName) {
        for (int i = 0; i< deviceSongs.size(); i++){
            String name = deviceSongs.get(i).getSongTitle();
            if(name.equals(songName)) {
                return songName;
            }
        }
        return null;
    }

    public Albums createANewAlbum(ArrayList<Songs> newSong, String albumName2) {
        return new Albums(newSong, albumName2);
    }

    public boolean addSongToList(String songName2, double songDuration2) {
        if(checkSongList(songName2) == null) {
            deviceSongs.add(new Songs(songName2, songDuration2));
            return true;
        }
        return false;
    }

    private String checkAlbumList(String albumName) { // returns null if name is not in ArrayList Album.
        for(int j = 0; j < musicAlbums.size(); j++) {
            String name = musicAlbums.get(j).getAlbumName();
            if(name.equals(albumName)) {
                return name;
            }
        }
        return null;
    }

    public boolean createAlbum(String albumName3) {
        if(checkAlbumList(albumName3) != null){
            return false;
        }

        System.out.println("""
                Press 1 to create new empty Album.
                Press 2 to to add songs to New Album.
                Press 3 to add all songs to new Album.
                Press 0 to cancel.
                \n""");

        int check = decisionMachine(3);
        switch (check) {
            case 1 -> {
                //ArrayList<Songs> emptySongs = new ArrayList<>();
                //Albums makeNew = createANewAlbum(emptySongs, albumName3);
                musicAlbums.add(new Albums(mySongs, albumName3));
                System.out.println("New Empty Album : " + albumName3 + " successfully created.");
                return true;
            }
            case 2 -> {
                if(deviceSongs.isEmpty()) {
                    System.out.println("Can't create new Album. Music list is empty.");
                } else {
                    System.out.println("Select Songs to add to new Album.\n");
                    displaySongList(deviceSongs);

                    ArrayList<Songs> mySongs2 = new ArrayList<>();
                    int addedSongs = 0;

                    System.out.println("\nEnter the song number to add to new Album.");
                    System.out.println("Enter 0 to stop.");
                    while (true) {
                        int index = decisionMachine(deviceSongs.size()); // takes the number of items in the array as a range.
                        if(index != 0) {
                            addedSongs += 1;
                            Songs songClassIndex = returnClass(deviceSongs, index); // Song Class returned from returnClass().
                            mySongs2.add(songClassIndex); // adding songClassIndex to new mySongs2 Array of Songs Class.
                            System.out.println("Song number (" + index +") successfully added.\n");

                            System.out.println("""
                                    Enter music number to add more song.
                                    Enter 0 to stop.
                                    """);
                        } else {
                            break;
                        }
                    }

                    System.out.println("Values in array.");
                    displaySongList(mySongs2);
                    Albums fullAlbum = createANewAlbum(mySongs2, albumName3);
                    musicAlbums.add(fullAlbum);
                    System.out.println(albumName3 + " Album have been created. " + addedSongs + " songs added.");
                    return true;
                }
            }
            case 3 -> {
                if(deviceSongs.isEmpty()) {
                    System.out.println("No song in device.");
                    return false;
                }
                int songsNumber = deviceSongs.size();
                ArrayList<Songs> myNewSongs = new ArrayList<>();
                myNewSongs = deviceSongs;
                Albums newAlbum = createANewAlbum(myNewSongs, albumName3);
                musicAlbums.add(newAlbum);
                System.out.println("New Album {" + albumName3.toUpperCase() +
                        "} created.");
                System.out.println("["+songsNumber + " songs added]\n");
                return true;
            }
            case 0 -> {
                System.out.println("Ok...");
                return false;
            }
            default -> System.out.println("Not sure of the entry");
        }
        return false;
    }

    public int decisionMachine(int range){ // range starts from 0.
        Scanner myScanner = new Scanner(in);

        while(true) {
            int input = myScanner.nextInt();
            myScanner.nextLine();

            for(int x = 0; x <= range; x++) {
                if(input == x) {
                    return x;
                }
            }
            System.out.println("Invalid selection. Enter number again.");
        }
    }

    public void displaySongList(ArrayList<Songs> listSongs) {
        if(listSongs.isEmpty()){
            System.out.println("Empty List.");
        } else {
            System.out.println("SONG LIST:\n");
            for (int k = 0; k < listSongs.size(); k++) {
                String name = listSongs.get(k).getSongTitle();
                double duration = listSongs.get(k).getSongDuration();
                System.out.println( "[" +(k+1)+"]" + " Title: "
                        + name + " --> Duration: " + duration);
            }
        }
    }

//    public void showAllAlbumAndSongs() {
//
//        if(getMyAlbums().isEmpty()){
//            System.out.println("Album is empty. ");
//        } else {
//            for(int y = 0; y < musicAlbums.size(); y++) {
//                String albName = musicAlbums.get(y).getAlbumName();
//                ArrayList<Songs> songs = musicAlbums.get(y).getMySongs();
//                System.out.println("\n" + (y+1) + " ALBUM --> " + albName.toUpperCase());
//
//                if(songs.isEmpty()) {
//                    System.out.println("This album is empty.");
//                } else {
//                    displaySongList(songs);
//                }
//            }
//        }
//
//    }

    public Songs returnClass(ArrayList<Songs> listSongs, int indexChoice) {
        indexChoice -= 1;
        for (int k = 0; k < listSongs.size(); k++) {
            Songs mySongs = listSongs.get(k);
            if(k == indexChoice) {
                return mySongs;
            }
        }
        return null;
    }

    public boolean checkSongsInDevice(){
        return deviceSongs.isEmpty();
    }

    public void display(int choice){

        if(choice == 1) {
            System.out.println("""
                Enter 1 To display list of songs on device.
                Enter 2 To create a new album of songs.
                Enter 3 To create a PlayList.
                Enter 4 to list all albums and songs.
                Enter 5 to display Menu options.
                Enter 6 to delete song from playlist.
                Enter 0 to quit application.
                """);
        }

        if (choice == 2){
            System.out.println("""
                    \nEnter 0 quit the application.
                    Enter 5 to display Meno options.
                    """);
        } else {

        }

    }

    public void myPlayList(ArrayList<Albums> displayAlbum) {

        if(displayAlbum.isEmpty()) {
            System.out.println("You do not have songs in Album");
            return;
        }

        System.out.println("\nAdd songs from Albums to create a PlayList.");
        showAllAlbumAndSongs();

        if(deviceSongs.isEmpty()){
            System.out.println("No song in device.");
            return;
        }
        System.out.println("\nTo add songs to Playlist.");
        boolean flag = true;
        int songsAdded = 0;

        while(flag){
            System.out.println("Enter Album number:");

            int albumNumber = decisionMachine(musicAlbums.size());

            for(int a = 0; a < musicAlbums.size(); a++) {
                Albums chosenAlb = musicAlbums.get(a);
                ArrayList<Songs> isNewSongs = chosenAlb.getMySongs();
                if(a == (albumNumber-1)) {
                    System.out.println("Enter Song number:");
                    int songNumber = decisionMachine(isNewSongs.size());
                    Songs returnedSong = returnClass(isNewSongs, songNumber);
                    String songName = returnedSong.getSongTitle();

                    if(checkSongListInPlayList(songName, songPlayList)){
                        songPlayList.add(returnedSong);
                        songsAdded += 1;
                        System.out.println(songName + " from "+ chosenAlb.getAlbumName().toUpperCase() + " added to Playlist.");
                    } else{
                        System.out.println("This song already exist in Playlist.");
                    }
                }
            }

            System.out.println("""
                    \nEnter 0 to stop.
                    Enter 1 to add another song to Playlist.
                    """);
            int input = decisionMachine(1);

            if(input == 0){
                flag = false;
            }
        }

        if(songPlayList.isEmpty()) {
            System.out.println("Playlist cannot be empty.");
            return;
        }

        System.out.println( "\n" + songsAdded +
                " songs have been added to PlayList.\n");


        while(true) {
            System.out.println("""
                \nEnter [1] to list songs on Playlist.
                Enter [2] to play songs on Playlist.
                Enter [0] to quit application.
                """);

            int choice = decisionMachine(2);
            if(choice == 0){
                return;
            }else if (choice == 1) {
                displaySongList(songPlayList);
                System.out.println();
            } else if(choice == 2){
                break;
            }
        }

        ListIterator<Songs> songsListIterator1 = songPlayList.listIterator();
        boolean goingForward = true;

        System.out.println("Now playing --- > " +
                songsListIterator1.next().getSongTitle());
        System.out.println();

        while(!flag) {
            System.out.println("""
                \nEnter
                0 --> to quit.
                1 --> to skip to next songs.
                2 --> to skip to previous song.
                3 --> to replay current song.
                """);

            int input = decisionMachine(3);

            switch (input) {
                case 1 -> {
                    if(goingForward) {
                        if(songsListIterator1.hasNext()) {
                            System.out.println("\nNow Playing --> " +
                                    songsListIterator1.next().getSongTitle());
                            if(!songsListIterator1.hasNext()){
                                songsListIterator1.previous();
                                goingForward = false;
                            }
                        }
                    } else {
                        System.out.println("\nYou've reached the end of the Playlist.");
                    }
                }
                case 2 -> {
                    if(!goingForward){
                        if (songsListIterator1.hasPrevious()) {
                            System.out.println("\nNow Playing --> " +
                                    songsListIterator1.previous().getSongTitle());
                            if(!songsListIterator1.hasPrevious()){
                                songsListIterator1.next();
                                goingForward = true;
                            }
                        }
                    } else {
                        System.out.println("\nYou've reached the beginning of the Playlist.");
                    }
                }

                case 3 -> {
                    if(!goingForward) {
                        songsListIterator1.next();
                        System.out.println("Now re-playing --> " +
                                songsListIterator1.previous().getSongTitle());
                    } else {
                        songsListIterator1.previous();
                        System.out.println("Now re-playing --> " +
                                songsListIterator1.next().getSongTitle());
                    }
                }
                case 0 -> flag = true;
            }
        }
    }

    private boolean checkSongListInPlayList(String songName, ArrayList<Songs> newList) {
        for(int c = 0; c < newList.size(); c++) {
            String checkName = newList.get(c).getSongTitle();
            if(checkName.equals(songName)) {
                return false;
            }
        }
        return true;
    }

    public boolean deleteSong(String songName, ArrayList<Songs> newList) {
        for(int c = 0; c < newList.size(); c++) {
            Songs songClass = newList.get(c);
            String checkName = songClass.getSongTitle();

            int indexOfClass = newList.indexOf(songClass);
            if(checkName.equals(songName)) {
                newList.remove(indexOfClass);
                return true;
            }
        }
        return false;
    }

}
