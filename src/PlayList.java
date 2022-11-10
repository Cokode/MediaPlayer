import java.util.Scanner;

import static java.lang.System.in;

public class PlayList {
    private static Albums myAlbums = new Albums(Albums.mySongs, "T");


    public static void makeNewAlbum() {
        System.out.println("Enter name of new Album.");

        Scanner newScanner = new Scanner(in);
        String albName = newScanner.nextLine();

        if(!myAlbums.createAlbum(albName)) {
            System.out.println("============================");
        }
    }

    public static void addSongsToDevice(){
        System.out.println("Enter 1 to add songs to your device.");
        System.out.println("Enter 0 cancel.");
        Scanner scanner = new Scanner(in);
        boolean flag = true;

        while (flag){
            int input = scanner.nextInt();
            scanner.nextLine();

            switch (input){
                case 1 -> {
                    System.out.println("Enter Song Title: ");
                    String name = scanner.nextLine();
                    System.out.println("Enter Song duration: ");
                    double duration = scanner.nextDouble();
                    scanner.nextLine();

                    if(!myAlbums.addSongToList(name, duration)){
                        System.out.println("Song already exist on Song list.");
                        break;
                    }
                    System.out.println("Song is successfully added to your device.\n");
                    System.out.println("Enter 1 to add more songs.");
                    System.out.println("Enter 0 to stop.");
                }
                case 0 -> {
                    if(myAlbums.getDeviceSongs().isEmpty()){
                        System.out.println("No songs added to list.");
                        return;
                    }
                    flag = false;
                    System.out.println("Finished adding songs.\n");
                }
                default -> System.out.println("Invalid input.");
            }
        }
    }

    public static void myPlaylist(){
        if(myAlbums.checkSongsInDevice()) {
            System.out.println("Your device music list is empty.\n");
            addSongsToDevice();
        }

        myAlbums.display(1);

        do {
            int input = myAlbums.decisionMachine(6);

            switch (input) {
                case 0 -> {
                    System.out.println("Goodbye...");
                    return;
                }
                case 1 -> myAlbums.displaySongList(myAlbums.getDeviceSongs());
                case 2 -> makeNewAlbum();
                case 3 -> myAlbums.myPlayList(myAlbums.getMyAlbums());
                case 4 -> myAlbums.showAllAlbumAndSongs();
                case 5 -> myAlbums.display(1);
                case 6 -> {
                    if(myAlbums.getSongsListIterator().isEmpty()) {
                        System.out.println("Empty Playlist. Nothing to delete.");
                    } else{
                        System.out.println("Enter song name.");
                        Scanner myScanner = new Scanner(in);
                        String nameOfSong = myScanner.nextLine();

                        if(myAlbums.deleteSong(nameOfSong, myAlbums.getSongsListIterator())) {
                            System.out.println(nameOfSong + " was successfully deleted from Playlist");
                            System.out.println("\nEnter 0 to display Playlist.");
                            int input2 = myAlbums.decisionMachine(0);
                            if(input2 == 0) {
                                if(myAlbums.getSongsListIterator().isEmpty()) {
                                    System.out.println("PlayList is empty.");
                                    //return;
                                }
                                myAlbums.displaySongList(myAlbums.getSongsListIterator());
                            }else {
                                System.out.println("Can't display Playlist songs.");
                            }
                        } else {
                            System.out.println("Fails to delete. Try again.");
                        }
                    }

                }
            }
            myAlbums.display(2);

        }while(true);
    }

    public static void main(String[] args) {
        //myPlaylist();

        myAlbums.addAlbum();
        myAlbums.addAlbum();
        myAlbums.addAlbum();
        myAlbums.addAlbum();
        myAlbums.showAllAlbumAndSongs();
    }

}
