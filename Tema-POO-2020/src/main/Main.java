package main;

import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */


    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();
        System.out.println(input.getMovies().toString());
        System.out.println(input.getSerials().toString());
        System.out.println(input.getCommands().toString());
        //TODO add here the entry point to your implementation
        for(int i=0;i<input.getCommands().size();i++){

            if(input.getCommands().get(i).getActionType().equals("command") ){
                if(input.getCommands().get(i).getType().equals("favorite")){
                    for(int j=0;j<input.getUsers().size();j++){
                        if(input.getCommands().get(i).getUsername().equals(input.getUsers().get(j).getUsername())){
                            arrayResult.add(input.getUsers().get(j).getFavorite(input.getCommands().get(i),fileWriter));
                        }
                    }
                }
                if(input.getCommands().get(i).getType().equals("view")){
                    for(int j=0;j<input.getUsers().size();j++){
                        if(input.getCommands().get(i).getUsername().equals(input.getUsers().get(j).getUsername())){
                            arrayResult.add(input.getUsers().get(j).getView(input.getCommands().get(i),fileWriter));
                        }
                    }
                }
                if(input.getCommands().get(i).getType().equals("rating")){
                    for(int j=0;j<input.getUsers().size();j++){
                        if(input.getCommands().get(i).getUsername().equals(input.getUsers().get(j).getUsername())){
                            arrayResult.add(input.getUsers().get(j).getRating(input.getCommands().get(i),input.getSerials(),fileWriter));
                        }
                    }
                }
            }
           else {
                if (input.getCommands().get(i).getActionType().equals("query")) {
                    if (input.getCommands().get(i).getCriteria().equals("average")) {
                        ArrayList<ActorRating> actors = new ArrayList<ActorRating>();
                        for (int j = 0; j < input.getActors().size(); j++) {
                            ActorRating actor = new ActorRating(input.getActors().get(j).getName(), input.getActors().get(j).getActorRating(input.getUsers(),
                                    input.getMovies(), input.getSerials()));
                            actors.add(actor);
                        }
                        if (input.getCommands().get(i).getSortType().equals("asc")) {
                            for (int k = 0; k < actors.size(); k++) {
                                for (int l = k + 1; l < actors.size(); l++) {
                                    if (actors.get(k).getActor_rating() > actors.get(l).getActor_rating()) {
                                        Collections.swap(actors, k, l);


                                    }
                                }
                            }
                            for (int k = 0; k < actors.size(); k++) {
                                for (int l = k + 1; l < actors.size(); l++) {
                                    if (actors.get(k).getActor_rating() == actors.get(l).getActor_rating()) {
                                        if(actors.get(k).getActorFirstLetter() > actors.get(l).getActorFirstLetter())
                                            Collections.swap(actors,k,l);


                                    }
                                }
                            }
                        } else {
                            for (int k = 0; k < actors.size(); k++) {
                                for (int l = k + 1; l < actors.size(); l++) {
                                    if (actors.get(k).getActor_rating() < actors.get(l).getActor_rating()) {
                                        Collections.swap(actors, k, l);

                                    }
                                }
                            }
                            for (int k = 0; k < actors.size(); k++) {
                                for (int l = k + 1; l < actors.size(); l++) {
                                    if (actors.get(k).getActor_rating() == actors.get(l).getActor_rating()) {
                                        if(actors.get(k).getActorFirstLetter() < actors.get(l).getActorFirstLetter())
                                            Collections.swap(actors,k,l);


                                    }
                                }
                            }
                        }
                        StringBuilder builder = new StringBuilder();
                        builder.append("Query result: [");
                        int counter=0;
                        for (int i1 = 0; i1 < actors.size(); i1++) {
                            if(counter == input.getCommands().get(i).getNumber())
                                break;
                            if(actors.get(i1).getActor_rating() != 0) {
                                builder.append(actors.get(i1).getActor_name());
                                if(counter < input.getCommands().get(i).getNumber()-1) {
                                    builder.append(",");
                                    builder.append(' ');
                                }

                                counter++;
                            }
                        }
                        builder.append("]");
                        arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, builder.toString()));
                    }
                    if(input.getCommands().get(i).getCriteria().equals("awards")) {
                        ArrayList<Actor_Award> actors = new ArrayList<Actor_Award>();
                        for (int j = 0; j < input.getActors().size(); j++) {
                            Actor_Award actor = new Actor_Award(input.getActors().get(j).getAwardsNumber(input.getCommands().get(i))
                                    , input.getActors().get(j).getName());
                            if (actor.getNo_of_awards() != 0) {
                                actors.add(actor);
                            }
                        }
                        if (input.getCommands().get(i).getSortType().equals("asc")) {
                            for (int k = 0; k < actors.size(); k++) {
                                for (int l = k + 1; l < actors.size(); l++) {
                                    if (actors.get(k).getNo_of_awards() > actors.get(l).getNo_of_awards()) {
                                        Collections.swap(actors, k, l);


                                    }
                                }
                            }
                            for (int k = 0; k < actors.size(); k++) {
                                for (int l = k + 1; l < actors.size(); l++) {
                                    if (actors.get(k).getNo_of_awards() == actors.get(l).getNo_of_awards()) {
                                        if (actors.get(k).getActorFirstLetter() > actors.get(l).getActorFirstLetter())
                                            Collections.swap(actors, k, l);
                                        if (actors.get(k).getActorFirstLetter() == actors.get(l).getActorFirstLetter())
                                            if (actors.get(k).getActorSecondLetter() > actors.get(l).getActorSecondLetter())
                                                Collections.swap(actors, k, l);

                                    }
                                }
                            }
                        } else {
                            for (int k = 0; k < actors.size(); k++) {
                                for (int l = k + 1; l < actors.size(); l++) {
                                    if (actors.get(k).getNo_of_awards() < actors.get(l).getNo_of_awards()) {
                                        Collections.swap(actors, k, l);


                                    }
                                }
                            }
                            for (int k = 0; k < actors.size(); k++) {
                                for (int l = k + 1; l < actors.size(); l++) {
                                    if (actors.get(k).getNo_of_awards() == actors.get(l).getNo_of_awards()) {
                                        if (actors.get(k).getActorFirstLetter() < actors.get(l).getActorFirstLetter())
                                            Collections.swap(actors, k, l);
                                        if (actors.get(k).getActorFirstLetter() == actors.get(l).getActorFirstLetter())
                                            if (actors.get(k).getActorSecondLetter() < actors.get(l).getActorSecondLetter())
                                                Collections.swap(actors, k, l);


                                    }
                                }
                            }

                        }
                        if (actors.size() == 0) {
                            arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, "Query result: []"));

                        } else {
                            StringBuilder builder = new StringBuilder();
                            builder.append("Query result: [");
                            int counter = 0;
                            for (int i1 = 0; i1 < actors.size(); i1++) {
                                if (counter == input.getCommands().get(i).getNumber())
                                    break;
                                if (actors.get(i1).getNo_of_awards() != 0) {
                                    builder.append(actors.get(i1).getActor_name());
                                    if (counter < actors.size() - 1) {
                                        builder.append(",");
                                        builder.append(' ');
                                    }

                                    counter++;
                                }
                            }
                            builder.append("]");
                            arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, builder.toString()));

                        }
                    }
                    if(input.getCommands().get(i).getCriteria().equals("filter_description")){
                        ArrayList<ActorInputData> actors=new ArrayList<ActorInputData>();
                        for(int j=0;j<input.getActors().size();j++){
                            if(input.getActors().get(j).checkActorKeyWords(input.getCommands().get(i))){
                                actors.add(input.getActors().get(j));
                            }
                        }
                        if (input.getCommands().get(i).getSortType().equals("asc")) {
                            for (int k = 0; k < actors.size(); k++) {
                                for (int l = k + 1; l < actors.size(); l++) {
                                    {
                                        if (actors.get(k).getFirstLetter() > actors.get(l).getFirstLetter())
                                            Collections.swap(actors, k, l);
                                        if (actors.get(k).getFirstLetter() == actors.get(l).getFirstLetter()){
                                            if (actors.get(k).getSecondLetter() < actors.get(l).getSecondLetter())
                                                Collections.swap(actors, k, l);
                                        }


                                    }
                                }
                            }
                        }
                        else{ for (int k = 0; k < actors.size(); k++) {
                            for (int l = k + 1; l < actors.size(); l++) {
                                {
                                    if (actors.get(k).getFirstLetter() < actors.get(l).getFirstLetter())
                                        Collections.swap(actors, k, l);
                                    if (actors.get(k).getFirstLetter() == actors.get(l).getFirstLetter()){
                                        if (actors.get(k).getSecondLetter() < actors.get(l).getSecondLetter())
                                            Collections.swap(actors, k, l);
                                    }



                                }
                            }
                        }

                        }
                        if (actors.size() == 0) {
                            arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, "Query result: []"));

                        } else {
                            StringBuilder builder = new StringBuilder();
                            builder.append("Query result: [");
                            int counter=0;
                            for (int i1 = 0; i1 < actors.size(); i1++) {
                                if(counter == input.getCommands().get(i).getNumber())
                                    break;

                                    builder.append(actors.get(i1).getName());
                                    if(counter < actors.size() - 1) {
                                        builder.append(",");
                                        builder.append(' ');
                                    }

                                    counter++;

                            }
                            builder.append("]");
                            arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, builder.toString()));

                        }

                    }
                    if(input.getCommands().get(i).getCriteria().equals("ratings")){
                        if(input.getCommands().get(i).getObjectType().equals("movies")) {
                            ArrayList<VideoRating> videos = new ArrayList<VideoRating>();
                            for (int j = 0; j < input.getMovies().size(); j++) {
                                if (input.getMovies().get(j).checkFilters(input.getCommands().get(i)) == true) {
                                    if (input.getMovies().get(j).getMovieRating(input.getUsers()) != 0) {
                                        VideoRating video = new VideoRating(input.getMovies().get(j).getTitle(), input.getMovies().get(j).getMovieRating(input.getUsers()));
                                        videos.add(video);
                                    }

                                }
                            }
                            if (input.getCommands().get(i).getSortType().equals("asc")) {
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getVideo_rating() > videos.get(l).getVideo_rating()) {
                                            Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getVideo_rating() == videos.get(l).getVideo_rating()) {
                                            if (videos.get(k).getFirstLetter() > videos.get(l).getFirstLetter())
                                                Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                            } else {
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getVideo_rating() < videos.get(l).getVideo_rating()) {
                                            Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getVideo_rating() == videos.get(l).getVideo_rating()) {
                                            if (videos.get(k).getFirstLetter() < videos.get(l).getFirstLetter())
                                                Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                            }
                            if (videos.size() == 0) {
                                arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, "Query result: []"));

                            } else {
                                StringBuilder builder = new StringBuilder();
                                builder.append("Query result: [");
                                int counter = 0;
                                for (int i1 = 0; i1 < videos.size(); i1++) {
                                    if (counter == input.getCommands().get(i).getNumber())
                                        break;

                                    builder.append(videos.get(i1).getVideo_name());
                                    if (counter < videos.size() - 1) {
                                        builder.append(",");
                                        builder.append(' ');
                                    }

                                    counter++;

                                }
                                builder.append("]");
                                arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, builder.toString()));

                            }
                        }
                        else{ArrayList<VideoRating> videos = new ArrayList<VideoRating>();
                            for (int j = 0; j < input.getSerials().size(); j++) {
                                if (input.getSerials().get(j).checkFilters(input.getCommands().get(i)) == true) {
                                    if (input.getSerials().get(j).getShowRating(input.getUsers()) != 0) {
                                        VideoRating video = new VideoRating(input.getSerials().get(j).getTitle(), input.getSerials().get(j).getShowRating(input.getUsers()));
                                        videos.add(video);
                                    }

                                }
                            }
                            if (input.getCommands().get(i).getSortType().equals("asc")) {
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getVideo_rating() > videos.get(l).getVideo_rating()) {
                                            Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getVideo_rating() == videos.get(l).getVideo_rating()) {
                                            if (videos.get(k).getFirstLetter() > videos.get(l).getFirstLetter())
                                                Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                            } else {
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getVideo_rating() < videos.get(l).getVideo_rating()) {
                                            Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getVideo_rating() == videos.get(l).getVideo_rating()) {
                                            if (videos.get(k).getFirstLetter() < videos.get(l).getFirstLetter())
                                                Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                            }
                            if (videos.size() == 0) {
                                arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, "Query result: []"));

                            } else {
                                StringBuilder builder = new StringBuilder();
                                builder.append("Query result: [");
                                int counter = 0;
                                for (int i1 = 0; i1 < videos.size(); i1++) {
                                    if (counter == input.getCommands().get(i).getNumber())
                                        break;

                                    builder.append(videos.get(i1).getVideo_name());
                                    if (counter < videos.size() - 1) {
                                        builder.append(",");
                                        builder.append(' ');
                                    }

                                    counter++;

                                }
                                builder.append("]");
                                arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, builder.toString()));

                            }

                        }
                    }
                    if(input.getCommands().get(i).getCriteria().equals("favorite")) {
                        if(input.getCommands().get(i).getObjectType().equals("movies")) {
                            ArrayList<VideoFavorite> videos = new ArrayList<VideoFavorite>();
                            for (int j = 0; j < input.getMovies().size(); j++) {
                                if (input.getMovies().get(j).checkFilters(input.getCommands().get(i)) == true) {
                                    if (input.getMovies().get(j).getFavourites(input.getUsers()) != 0) {
                                        VideoFavorite video = new VideoFavorite(input.getMovies().get(j).getTitle(),
                                                input.getMovies().get(j).getFavourites(input.getUsers()));
                                        videos.add(video);
                                    }
                                }
                            }
                            if (input.getCommands().get(i).getSortType().equals("asc")) {
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getNo_of_favourites() > videos.get(l).getNo_of_favourites()) {
                                            Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getNo_of_favourites() == videos.get(l).getNo_of_favourites()) {
                                            if (videos.get(k).getFirstLetter() > videos.get(l).getFirstLetter())
                                                Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                            } else {
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getNo_of_favourites() < videos.get(l).getNo_of_favourites()) {
                                            Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getNo_of_favourites() == videos.get(l).getNo_of_favourites()) {
                                            if (videos.get(k).getFirstLetter() < videos.get(l).getFirstLetter())
                                                Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                                if (videos.size() == 0) {
                                    arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, "Query result: []"));

                                } else {
                                    StringBuilder builder = new StringBuilder();
                                    builder.append("Query result: [");
                                    int counter = 0;
                                    for (int i1 = 0; i1 < videos.size(); i1++) {
                                        if (counter == input.getCommands().get(i).getNumber())
                                            break;

                                        builder.append(videos.get(i1).getVideo_name());
                                        if (counter < videos.size() - 1) {
                                            builder.append(",");
                                            builder.append(' ');
                                        }

                                        counter++;

                                    }
                                    builder.append("]");
                                    arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, builder.toString()));

                                }
                            }
                        }
                        else
                        {
                            ArrayList<VideoFavorite> videos = new ArrayList<VideoFavorite>();
                            for (int j = 0; j < input.getSerials().size(); j++) {
                                if (input.getSerials().get(j).checkFilters(input.getCommands().get(i)) == true) {
                                    if (input.getSerials().get(j).getFavourites(input.getUsers()) != 0) {
                                        VideoFavorite video = new VideoFavorite(input.getSerials().get(j).getTitle(),
                                                input.getSerials().get(j).getFavourites(input.getUsers()));
                                        videos.add(video);
                                    }
                                }
                            }
                            if (input.getCommands().get(i).getSortType().equals("asc")) {
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getNo_of_favourites() > videos.get(l).getNo_of_favourites()) {
                                            Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getNo_of_favourites() == videos.get(l).getNo_of_favourites()) {
                                            if (videos.get(k).getFirstLetter() > videos.get(l).getFirstLetter())
                                                Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                            } else {
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getNo_of_favourites() < videos.get(l).getNo_of_favourites()) {
                                            Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getNo_of_favourites() == videos.get(l).getNo_of_favourites()) {
                                            if (videos.get(k).getFirstLetter() < videos.get(l).getFirstLetter())
                                                Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                                if (videos.size() == 0) {
                                    arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, "Query result: []"));

                                } else {
                                    StringBuilder builder = new StringBuilder();
                                    builder.append("Query result: [");
                                    int counter = 0;
                                    for (int i1 = 0; i1 < videos.size(); i1++) {
                                        if (counter == input.getCommands().get(i).getNumber())
                                            break;

                                        builder.append(videos.get(i1).getVideo_name());
                                        if (counter < videos.size() - 1) {
                                            builder.append(",");
                                            builder.append(' ');
                                        }

                                        counter++;

                                    }
                                    builder.append("]");
                                    arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, builder.toString()));

                                }
                            }
                        }
                    }
                    if(input.getCommands().get(i).getCriteria().equals("longest")){
                        if(input.getCommands().get(i).getObjectType().equals("movies")) {
                            ArrayList<VideoLongest> videos = new ArrayList<VideoLongest>();
                            for (int j = 0; j < input.getMovies().size(); j++) {
                                if (input.getMovies().get(j).checkFilters(input.getCommands().get(i)) == true) {
                                    if (input.getMovies().get(j).getDuration() != 0) {
                                        VideoLongest video = new VideoLongest(input.getMovies().get(j).getTitle(),
                                                input.getMovies().get(j).getDuration());
                                        videos.add(video);
                                    }
                                }
                            }
                            if (input.getCommands().get(i).getSortType().equals("asc")) {
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getDuration() > videos.get(l).getDuration()) {
                                            Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getDuration() == videos.get(l).getDuration()) {
                                            if (videos.get(k).getFirstLetter() > videos.get(l).getFirstLetter())
                                                Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                            } else {
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getDuration() < videos.get(l).getDuration()) {
                                            Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getDuration() == videos.get(l).getDuration()) {
                                            if (videos.get(k).getFirstLetter() < videos.get(l).getFirstLetter())
                                                Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                            }
                            if (videos.size() == 0) {
                                arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, "Query result: []"));

                            } else {
                                StringBuilder builder = new StringBuilder();
                                builder.append("Query result: [");
                                int counter = 0;
                                for (int i1 = 0; i1 < videos.size(); i1++) {
                                    if (counter == input.getCommands().get(i).getNumber())
                                        break;

                                    builder.append(videos.get(i1).getVideo_name());
                                    if (counter < videos.size() - 1) {
                                        builder.append(",");
                                        builder.append(' ');
                                    }

                                    counter++;

                                }
                                builder.append("]");
                                arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, builder.toString()));

                            }
                        }
                        else
                        {
                            ArrayList<VideoLongest> videos = new ArrayList<VideoLongest>();
                            for (int j = 0; j < input.getSerials().size(); j++) {
                                if (input.getSerials().get(j).checkFilters(input.getCommands().get(i)) == true) {
                                    if (input.getSerials().get(j).getShowDuration() != 0) {
                                        VideoLongest video = new VideoLongest(input.getSerials().get(j).getTitle(),
                                                input.getSerials().get(j).getShowDuration());
                                        videos.add(video);
                                    }
                                }
                            }
                            if (input.getCommands().get(i).getSortType().equals("asc")) {
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getDuration() > videos.get(l).getDuration()) {
                                            Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getDuration() == videos.get(l).getDuration()) {
                                            if (videos.get(k).getFirstLetter() > videos.get(l).getFirstLetter())
                                                Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                            } else {
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getDuration() < videos.get(l).getDuration()) {
                                            Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getDuration() == videos.get(l).getDuration()) {
                                            if (videos.get(k).getFirstLetter() < videos.get(l).getFirstLetter())
                                                Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                            }
                            if (videos.size() == 0) {
                                arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, "Query result: []"));

                            } else {
                                StringBuilder builder = new StringBuilder();
                                builder.append("Query result: [");
                                int counter = 0;
                                for (int i1 = 0; i1 < videos.size(); i1++) {
                                    if (counter == input.getCommands().get(i).getNumber())
                                        break;

                                    builder.append(videos.get(i1).getVideo_name());
                                    if (counter < videos.size() - 1) {
                                        builder.append(",");
                                        builder.append(' ');
                                    }

                                    counter++;

                                }
                                builder.append("]");
                                arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, builder.toString()));

                            }
                        }

                    }
                    if(input.getCommands().get(i).getCriteria().equals("most_viewed")){
                        if(input.getCommands().get(i).getObjectType().equals("movies")) {
                            ArrayList<VideoViews> videos = new ArrayList<VideoViews>();
                            for (int j = 0; j < input.getMovies().size(); j++) {
                                if (input.getMovies().get(j).checkFilters(input.getCommands().get(i)) == true) {
                                    if (input.getMovies().get(j).getViews(input.getUsers()) != 0) {
                                        VideoViews video = new VideoViews(input.getMovies().get(j).getTitle(),
                                                input.getMovies().get(j).getViews(input.getUsers()));
                                        videos.add(video);
                                    }
                                }
                            }

                            if (input.getCommands().get(i).getSortType().equals("asc")) {
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getViews() > videos.get(l).getViews()) {
                                            Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getViews() == videos.get(l).getViews()) {
                                            if (videos.get(k).getFirstLetter() > videos.get(l).getFirstLetter())
                                                Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                            } else {
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getViews() < videos.get(l).getViews()) {
                                            Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getViews() == videos.get(l).getViews()) {
                                            if (videos.get(k).getFirstLetter() < videos.get(l).getFirstLetter())
                                                Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                            }
                            if (videos.size() == 0) {
                                arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, "Query result: []"));

                            } else {
                                StringBuilder builder = new StringBuilder();
                                builder.append("Query result: [");
                                int counter = 0;
                                for (int i1 = 0; i1 < videos.size(); i1++) {
                                    if (counter == input.getCommands().get(i).getNumber())
                                        break;

                                    builder.append(videos.get(i1).getVideo_name());
                                    if (counter < videos.size() - 1) {
                                        builder.append(",");
                                        builder.append(' ');
                                    }

                                    counter++;

                                }
                                builder.append("]");
                                arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, builder.toString()));

                            }
                        }
                        else{
                            ArrayList<VideoViews> videos = new ArrayList<VideoViews>();
                            for (int j = 0; j < input.getSerials().size(); j++) {
                                if (input.getSerials().get(j).checkFilters(input.getCommands().get(i)) == true) {
                                    if (input.getSerials().get(j).getViews(input.getUsers()) != 0) {
                                        VideoViews video = new VideoViews(input.getSerials().get(j).getTitle(),
                                                input.getSerials().get(j).getViews(input.getUsers()));
                                        videos.add(video);
                                    }
                                }
                            }

                            if (input.getCommands().get(i).getSortType().equals("asc")) {
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getViews() > videos.get(l).getViews()) {
                                            Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getViews() == videos.get(l).getViews()) {
                                            if (videos.get(k).getFirstLetter() > videos.get(l).getFirstLetter())
                                                Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                            } else {
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getViews() < videos.get(l).getViews()) {
                                            Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getViews() == videos.get(l).getViews()) {
                                            if (videos.get(k).getFirstLetter() < videos.get(l).getFirstLetter())
                                                Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                            }
                            if (videos.size() == 0) {
                                arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, "Query result: []"));

                            } else {
                                StringBuilder builder = new StringBuilder();
                                builder.append("Query result: [");
                                int counter = 0;
                                for (int i1 = 0; i1 < videos.size(); i1++) {
                                    if (counter == input.getCommands().get(i).getNumber())
                                        break;

                                    builder.append(videos.get(i1).getVideo_name());
                                    if (counter < videos.size() - 1) {
                                        builder.append(",");
                                        builder.append(' ');
                                    }

                                    counter++;

                                }
                                builder.append("]");
                                arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, builder.toString()));

                            }
                        }
                    }
                    if(input.getCommands().get(i).getCriteria().equals("num_ratings")){

                            ArrayList<UserRating> users=new ArrayList<UserRating>();
                            for(int j=0;j<input.getUsers().size();j++)
                            {
                                if(input.getUsers().get(j).getNoRatings() != 0){
                                    UserRating user=new UserRating(input.getUsers().get(j).getUsername(),
                                            input.getUsers().get(j).getNoRatings());
                                    users.add(user);
                                }
                            }
                        if (input.getCommands().get(i).getSortType().equals("asc")) {
                            for (int k = 0; k < users.size(); k++) {
                                for (int l = k + 1; l < users.size(); l++) {
                                    if (users.get(k).getNo_of_ratings() > users.get(l).getNo_of_ratings()) {
                                        Collections.swap(users, k, l);


                                    }
                                }
                            }
                            for (int k = 0; k < users.size(); k++) {
                                for (int l = k + 1; l < users.size(); l++) {
                                    if (users.get(k).getNo_of_ratings() == users.get(l).getNo_of_ratings()) {
                                        if (users.get(k).getFirstLetter() > users.get(l).getFirstLetter())
                                            Collections.swap(users, k, l);


                                    }
                                }
                            }
                        }
                        else{for (int k = 0; k < users.size(); k++) {
                            for (int l = k + 1; l < users.size(); l++) {
                                if (users.get(k).getNo_of_ratings() < users.get(l).getNo_of_ratings()) {
                                    Collections.swap(users, k, l);


                                }
                            }
                        }
                            for (int k = 0; k < users.size(); k++) {
                                for (int l = k + 1; l < users.size(); l++) {
                                    if (users.get(k).getNo_of_ratings() == users.get(l).getNo_of_ratings()) {
                                        if (users.get(k).getFirstLetter() < users.get(l).getFirstLetter())
                                            Collections.swap(users, k, l);


                                    }
                                }
                            }

                        }
                        if (users.size() == 0) {
                            arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, "Query result: []"));

                        }
                        else
                        {   StringBuilder builder = new StringBuilder();
                            builder.append("Query result: [");
                            int counter=0;
                            for (int i1 = 0; i1 < users.size(); i1++) {
                                if(counter == input.getCommands().get(i).getNumber())
                                    break;

                                builder.append(users.get(i1).getUser_name());
                                if(counter < users.size() - 1) {
                                    builder.append(",");
                                    builder.append(' ');
                                }

                                counter++;

                            }
                            builder.append("]");
                            arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(), null, builder.toString()));

                        }
                    }





                }
                if (input.getCommands().get(i).getActionType().equals("recommendation")){

                }
            }


        }


        fileWriter.closeJSON(arrayResult);
    }

}
