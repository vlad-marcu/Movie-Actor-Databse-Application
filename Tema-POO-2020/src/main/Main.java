package main;

import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import entertainment.Genre;
import fileio.*;
import org.json.simple.JSONArray;


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
     *
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

        //TODO add here the entry point to your implementation
        System.out.println(input.getUsers().toString());
        for (int i = 0; i < input.getCommands().size(); i++) {

            if (input.getCommands().get(i).getActionType().equals("command")) {
                if (input.getCommands().get(i).getType().equals("favorite")) {
                    for (int j = 0; j < input.getUsers().size(); j++) {
                        if (input.getCommands().get(i).getUsername().
                                equals(input.getUsers().get(j).getUsername())) {
                            arrayResult.add(input.getUsers().
                                    get(j).getFavorite(input.getCommands().get(i), fileWriter));
                        }
                    }
                }
                if (input.getCommands().get(i).getType().equals("view")) {
                    for (int j = 0; j < input.getUsers().size(); j++) {
                        if (input.getCommands().get(i).getUsername()
                                .equals(input.getUsers().get(j).getUsername())) {
                            arrayResult.add(input.getUsers().
                                    get(j).getView(input.getCommands().
                                    get(i), fileWriter));
                        }
                    }
                }
                if (input.getCommands().get(i).getType().equals("rating")) {
                    for (int j = 0; j < input.getUsers().size(); j++) {
                        if (input.getCommands().get(i).getUsername().
                                equals(input.getUsers().get(j).getUsername())) {
                            arrayResult.add(input.getUsers().get(j).
                                    getRating(input.getCommands().get(i),
                                            input.getSerials(), fileWriter));
                        }
                    }
                }
            } else {
                if (input.getCommands().get(i).getActionType().equals("query")) {
                    if (input.getCommands().get(i).getCriteria().equals("average")) {
                        ArrayList<ActorRating> actors = new ArrayList<ActorRating>();
                        for (int j = 0; j < input.getActors().size(); j++) {
                            ActorRating actor = new ActorRating(input.getActors().get(j).
                                    getName(), input.getActors().get(j).
                                    getActorRating(input.getUsers(),
                                            input.getMovies(), input.getSerials()));
                            actors.add(actor);
                        }
                        if (input.getCommands().get(i).getSortType().equals("asc")) {
                            for (int k = 0; k < actors.size(); k++) {
                                for (int l = k + 1; l < actors.size(); l++) {
                                    if (actors.get(k).getActorRating()
                                            > actors.get(l).getActorRating()) {
                                        Collections.swap(actors, k, l);


                                    }
                                }
                            }
                            for (int k = 0; k < actors.size(); k++) {
                                for (int l = k + 1; l < actors.size(); l++) {
                                    if (actors.get(k).getActorRating()
                                            == actors.get(l).getActorRating()) {
                                        if (actors.get(k).getActorName()
                                                .compareTo(actors.get(l).getActorName()) > 0) {
                                            Collections.swap(actors, k, l);
                                        }


                                    }
                                }
                            }
                        } else {
                            for (int k = 0; k < actors.size(); k++) {
                                for (int l = k + 1; l < actors.size(); l++) {
                             if (actors.get(k).getActorRating() < actors.get(l).getActorRating()) {
                                        Collections.swap(actors, k, l);

                                    }
                                }
                            }
                            for (int k = 0; k < actors.size(); k++) {
                                for (int l = k + 1; l < actors.size(); l++) {
                                    if (actors.get(k).getActorRating()
                                            == actors.get(l).getActorRating()) {

                        if (actors.get(k).getActorName().compareTo(actors.get(l).getActorName())
                                < 0) {
                                            Collections.swap(actors, k, l);
                                        }

                                    }
                                }
                            }
                        }
                        StringBuilder builder = new StringBuilder();
                        builder.append("Query result: [");
                        int counter = 0;
                        for (int i1 = 0; i1 < actors.size(); i1++) {
                            if (counter == input.getCommands().get(i).getNumber()) {
                                break;
                            }
                            if (actors.get(i1).getActorRating() != 0) {
                                builder.append(actors.get(i1).getActorName());

                                builder.append(",");
                                builder.append(' ');


                                counter++;
                            }
                        }
                        builder.deleteCharAt(builder.length() - 1);
                        builder.deleteCharAt(builder.length() - 1);
                        builder.append("]");
                   arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                null, builder.toString()));
                    }
                    if (input.getCommands().get(i).getCriteria().equals("awards")) {
                        ArrayList<ActorAward> actors = new ArrayList<ActorAward>();
                        for (int j = 0; j < input.getActors().size(); j++) {
                            ActorAward actor = new ActorAward(input.getActors().get(j).
                                    getAwardsNumber(input.getCommands().get(i)),
                                    input.getActors().get(j).getName());
                            if (actor.getNoofawards() != 0) {
                                actors.add(actor);
                            }
                        }
                        if (input.getCommands().get(i).getSortType().equals("asc")) {
                            for (int k = 0; k < actors.size(); k++) {
                                for (int l = k + 1; l < actors.size(); l++) {
                               if (actors.get(k).getNoofawards() > actors.get(l).getNoofawards()) {
                                        Collections.swap(actors, k, l);


                                    }
                                }
                            }
                            for (int k = 0; k < actors.size(); k++) {
                                for (int l = k + 1; l < actors.size(); l++) {
                          if (actors.get(k).getNoofawards() == actors.get(l).getNoofawards()) {
                                if (actors.get(k).getActorname().compareTo(actors.get(l).
                                        getActorname()) > 0) {
                                            Collections.swap(actors, k, l);
                                        }

                                    }
                                }
                            }
                        } else {
                            for (int k = 0; k < actors.size(); k++) {
                                for (int l = k + 1; l < actors.size(); l++) {
                       if (actors.get(k).getNoofawards() < actors.get(l).getNoofawards()) {
                                        Collections.swap(actors, k, l);


                                    }
                                }
                            }
                            for (int k = 0; k < actors.size(); k++) {
                                for (int l = k + 1; l < actors.size(); l++) {
                             if (actors.get(k).getNoofawards() == actors.get(l).getNoofawards()) {
                           if (actors.get(k).getActorFirstLetter() < actors.get(l).
                                   getActorFirstLetter()) {
                                            Collections.swap(actors, k, l);
                                        }
              if (actors.get(k).getActorFirstLetter() == actors.get(l).getActorFirstLetter()) {
                  if (actors.get(k).getActorname().compareTo(actors.get(l).getActorname()) < 0) {
                      Collections.swap(actors, k, l);
                  }
              }


                                    }
                                }
                            }

                        }
                        if (actors.size() == 0) {
                            arrayResult.add(fileWriter.writeFile(input.getCommands().get(i)
                                    .getActionId(), null, "Query result: []"));

                        } else {
                            StringBuilder builder = new StringBuilder();
                            builder.append("Query result: [");
                            int counter = 0;
                            for (int i1 = 0; i1 < actors.size(); i1++) {
                                if (counter == input.getCommands().get(i).getNumber()) {
                                    break;
                                }
                                if (actors.get(i1).getNoofawards() != 0) {
                                    builder.append(actors.get(i1).getActorname());

                                    builder.append(",");
                                    builder.append(' ');


                                    counter++;
                                }
                            }
                            builder.deleteCharAt(builder.length() - 1);
                            builder.deleteCharAt(builder.length() - 1);
                            builder.append("]");
                            arrayResult.add(fileWriter.writeFile(input.getCommands().get(i)
                                    .getActionId(), null, builder.toString()));

                        }
                    }
                    if (input.getCommands().get(i).getCriteria().equals("filter_description")) {
                        ArrayList<ActorInputData> actors = new ArrayList<ActorInputData>();
                        for (int j = 0; j < input.getActors().size(); j++) {
                   if (input.getActors().get(j).checkActorKeyWords(input.getCommands().get(i))) {
                                actors.add(input.getActors().get(j));
                            }
                        }
                        if (input.getCommands().get(i).getSortType().equals("asc")) {
                            for (int k = 0; k < actors.size(); k++) {
                                for (int l = k + 1; l < actors.size(); l++) {
                                    {
                                        if (actors.get(k).getName().compareTo(actors.get(l).
                                                getName()) > 0)
                                            Collections.swap(actors, k, l);


                                    }
                                }
                            }
                        } else {
                            for (int k = 0; k < actors.size(); k++) {
                                for (int l = k + 1; l < actors.size(); l++) {
                                    {
                                        if (actors.get(k).getName().compareTo(actors.get(l)
                                                .getName()) < 0) {
                                            Collections.swap(actors, k, l);
                                        }


                                    }
                                }
                            }

                        }
                        if (actors.size() == 0) {
                            arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).
                                    getActionId(), null, "Query result: []"));

                        } else {
                            StringBuilder builder = new StringBuilder();
                            builder.append("Query result: [");
                            int counter = 0;
                            for (int i1 = 0; i1 < actors.size(); i1++) {
                                if (counter == input.getCommands().get(i).getNumber()) {
                                    break;
                                }

                                builder.append(actors.get(i1).getName());

                                builder.append(",");
                                builder.append(' ');


                                counter++;

                            }
                            builder.deleteCharAt(builder.length() - 1);
                            builder.deleteCharAt(builder.length() - 1);
                            builder.append("]");
                            arrayResult.add(fileWriter.writeFile(input.getCommands().
                                    get(i).getActionId(), null, builder.toString()));

                        }

                    }
                    if (input.getCommands().get(i).getCriteria().equals("ratings")) {
                        if (input.getCommands().get(i).getObjectType().equals("movies")) {
                            ArrayList<VideoRating> videos = new ArrayList<VideoRating>();
                            for (int j = 0; j < input.getMovies().size(); j++) {
                                if (input.getMovies().get(j).checkFilters(input.getCommands()
                                        .get(i))) {
                                    if (input.getMovies().get(j).getMovieRating(input.
                                            getUsers()) != 0) {
                                      VideoRating video = new VideoRating(input.getMovies().get(j).
                                                getTitle(), input.getMovies().get(j).
                                                getMovieRating(input.getUsers()));
                                        videos.add(video);
                                    }

                                }
                            }
                            if (input.getCommands().get(i).getSortType().equals("asc")) {
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getVideorating() > videos.
                                                get(l).getVideorating()) {
                                            Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getVideoname().compareTo(videos.
                                                get(l).getVideoname()) > 0) {
                                            Collections.swap(videos, k, l);
                                        }
                                    }
                                }
                            } else {
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getVideorating() < videos.get(l).
                                                getVideorating()) {
                                            Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getVideorating() == videos.get(l).
                                                getVideorating()) {
                                            if (videos.get(k).getVideoname().
                                                    compareTo(videos.get(l).getVideoname()) < 0) {
                                                Collections.swap(videos, k, l);
                                            }

                                        }
                                    }
                                }
                            }

                            if (videos.size() == 0) {
                                arrayResult.add(fileWriter.writeFile(input.getCommands()
                                        .get(i).getActionId(),
                                        null, "Query result: []"));

                            } else {

                                StringBuilder builder = new StringBuilder();
                                builder.append("Query result: [");
                                int counter = 0;
                                for (int i1 = 0; i1 < videos.size(); i1++) {
                                    if (counter == input.getCommands().get(i).getNumber()) {
                                        break;
                                    }

                                    builder.append(videos.get(i1).getVideoname());

                                    builder.append(",");
                                    builder.append(' ');


                                    counter++;

                                }
                                builder.deleteCharAt(builder.length() - 1);
                                builder.deleteCharAt(builder.length() - 1);
                                builder.append("]");
                                arrayResult.add(fileWriter.writeFile(input.getCommands().
                                        get(i).getActionId(), null, builder.toString()));

                            }
                        } else {
                            ArrayList<VideoRating> videos = new ArrayList<VideoRating>();
                            for (int j = 0; j < input.getSerials().size(); j++) {
                          if (input.getSerials().get(j).checkFilters(input.getCommands().get(i))) {
                            if (input.getSerials().get(j).getShowRating(input.getUsers()) != 0) {
                                        VideoRating video = new VideoRating(input.getSerials().
                                                get(j).getTitle(), input.getSerials().get(j).
                                                getShowRating(input.getUsers()));
                                        videos.add(video);
                                    }

                                }
                            }
                            if (input.getCommands().get(i).getSortType().equals("asc")) {
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getVideorating() > videos.get(l).
                                                getVideorating()) {
                                            Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getVideorating() == videos.get(l)
                                                .getVideorating()) {
                                            if (videos.get(k).getVideoname().compareTo(videos.
                                                    get(l).getVideoname()) > 0) {
                                                Collections.swap(videos, k, l);
                                            }


                                        }
                                    }
                                }
                            } else {
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getVideorating() < videos.get(l).
                                                getVideorating()) {
                                            Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getVideorating() == videos.get(l).
                                                getVideorating()) {
                                          if (videos.get(k).getVideoname().compareTo(videos.get(l).
                                                    getVideoname()) < 0) {
                                                Collections.swap(videos, k, l);
                                            }


                                        }
                                    }
                                }
                            }
                            if (videos.size() == 0) {
                                arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).
                                        getActionId(), null, "Query result: []"));

                            } else {
                                StringBuilder builder = new StringBuilder();
                                builder.append("Query result: [");
                                int counter = 0;
                                for (int i1 = 0; i1 < videos.size(); i1++) {
                                    if (counter == input.getCommands().get(i).getNumber()) {
                                        break;
                                    }

                                    builder.append(videos.get(i1).getVideoname());

                                    builder.append(",");
                                    builder.append(' ');


                                    counter++;

                                }
                                builder.deleteCharAt(builder.length() - 1);
                                builder.deleteCharAt(builder.length() - 1);
                                builder.append("]");
                                arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).
                                        getActionId(), null, builder.toString()));

                            }

                        }
                    }
                    if (input.getCommands().get(i).getCriteria().equals("favorite")) {
                        if (input.getCommands().get(i).getObjectType().equals("movies")) {
                            ArrayList<VideoFavorite> videos = new ArrayList<VideoFavorite>();
                            for (int j = 0; j < input.getMovies().size(); j++) {
                                if (input.getMovies().get(j).checkFilters(input.getCommands().
                                        get(i))) {
                            if (input.getMovies().get(j).getFavourites(input.getUsers()) != 0) {
                                        VideoFavorite video = new VideoFavorite(input.getMovies().
                                                get(j).getTitle(),
                                        input.getMovies().get(j).getFavourites(input.getUsers()));
                                        videos.add(video);
                                    }
                                }
                            }
                            if (input.getCommands().get(i).getSortType().equals("asc")) {
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getNooffavourites() > videos.get(l).
                                                getNooffavourites()) {
                                            Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getNooffavourites() == videos.get(l)
                                                .getNooffavourites()) {
                                            if (videos.get(k).getVideoname().compareTo(videos.
                                                    get(l).getVideoname()) > 0) {
                                                Collections.swap(videos, k, l);
                                            }


                                        }
                                    }
                                }
                            } else {
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getNooffavourites() < videos.get(l)
                                                .getNooffavourites()) {
                                            Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getNooffavourites() == videos.get(l).
                                                getNooffavourites()) {
                                            if (videos.get(k).getVideoname().compareTo(videos.
                                                    get(l).getVideoname()) < 0) {
                                                Collections.swap(videos, k, l);
                                            }


                                        }
                                    }
                                }
                            }
                            if (videos.size() == 0) {
                                arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).
                                        getActionId(), null, "Query result: []"));

                            } else {
                                StringBuilder builder = new StringBuilder();
                                builder.append("Query result: [");
                                int counter = 0;
                                for (int i1 = 0; i1 < videos.size(); i1++) {
                                    if (counter == input.getCommands().get(i).getNumber()) {
                                        break;
                                    }

                                    builder.append(videos.get(i1).getVideoname());

                                    builder.append(",");
                                    builder.append(' ');


                                    counter++;

                                }
                                builder.deleteCharAt(builder.length() - 1);
                                builder.deleteCharAt(builder.length() - 1);
                                builder.append("]");
                                arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).
                                        getActionId(), null, builder.toString()));

                            }

                        } else if (input.getCommands().get(i).getObjectType().equals("shows")) {
                            ArrayList<VideoFavorite> videos = new ArrayList<VideoFavorite>();
                            for (int j = 0; j < input.getSerials().size(); j++) {
                                if (input.getSerials().get(j).checkFilters(input.getCommands().
                                        get(i)) == true) {
                                    if (input.getSerials().get(j).getFavourites(input
                                            .getUsers()) != 0) {
                                        VideoFavorite video = new VideoFavorite(input.getSerials().
                                                get(j).getTitle(),
                                                input.getSerials().get(j).getFavourites(input.
                                                        getUsers()));
                                        videos.add(video);
                                    }
                                }
                            }
                            if (input.getCommands().get(i).getSortType().equals("asc")) {
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getNooffavourites() > videos.get(l).
                                                getNooffavourites()) {
                                            Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getNooffavourites() == videos.get(l).
                                                getNooffavourites()) {
                                            if (videos.get(k).getVideoname().compareTo(videos.
                                                    get(l).getVideoname()) > 0) {
                                                Collections.swap(videos, k, l);
                                            }


                                        }
                                    }
                                }
                            } else {
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getNooffavourites() < videos.get(l)
                                                .getNooffavourites()) {
                                            Collections.swap(videos, k, l);


                                        }
                                    }
                                }
                                for (int k = 0; k < videos.size(); k++) {
                                    for (int l = k + 1; l < videos.size(); l++) {
                                        if (videos.get(k).getNooffavourites() == videos.get(l).
                                                getNooffavourites()) {
                                            if (videos.get(k).getVideoname().compareTo(videos.
                                                    get(l).getVideoname()) < 0) {
                                                Collections.swap(videos, k, l);
                                            }


                                        }
                                    }
                                }
                            }
                            if (videos.size() == 0) {
                                arrayResult.add(fileWriter.writeFile(input.getCommands().
                                    get(i).getActionId(), null, "Query result: []"));

                            } else {
                                StringBuilder builder = new StringBuilder();
                                builder.append("Query result: [");
                                int counter = 0;
                                for (int i1 = 0; i1 < videos.size(); i1++) {
                                    if (counter == input.getCommands().get(i).getNumber()) {
                                        break;
                                    }

                                    builder.append(videos.get(i1).getVideoname());

                                    builder.append(",");
                                    builder.append(' ');


                                    counter++;

                                }
                                builder.deleteCharAt(builder.length() - 1);
                                builder.deleteCharAt(builder.length() - 1);
                                builder.append("]");
                                arrayResult.add(fileWriter.writeFile(input.getCommands().
                                        get(i).getActionId(), null, builder.toString()));

                            }
                        }

                    }
                    if (input.getCommands().get(i).getCriteria().equals("longest")) {
                        if (input.getCommands().get(i).getObjectType().equals("movies")) {
                            ArrayList<VideoLongest> videos = new ArrayList<VideoLongest>();
                            for (int j = 0; j < input.getMovies().size(); j++) {
                          if (input.getMovies().get(j).checkFilters(input.getCommands().get(i))) {
                                    if (input.getMovies().get(j).getDuration() != 0) {
                                        VideoLongest video = new VideoLongest(input.getMovies()
                                                .get(j).getTitle(),
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
                                            if (videos.get(k).getVideoname().compareTo(videos.
                                                    get(l).getVideoname()) > 0) {
                                                Collections.swap(videos, k, l);
                                            }


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
                                            if (videos.get(k).getVideoname().compareTo(videos.
                                                    get(l).getVideoname()) < 0) {
                                                Collections.swap(videos, k, l);
                                            }


                                        }
                                    }
                                }
                            }
                            if (videos.size() == 0) {
                                arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).
                                        getActionId(), null, "Query result: []"));

                            } else {
                                StringBuilder builder = new StringBuilder();
                                builder.append("Query result: [");
                                int counter = 0;
                                for (int i1 = 0; i1 < videos.size(); i1++) {
                                    if (counter == input.getCommands().get(i).getNumber()) {
                                        break;
                                    }

                                    builder.append(videos.get(i1).getVideoname());

                                    builder.append(",");
                                    builder.append(' ');


                                    counter++;

                                }
                                builder.deleteCharAt(builder.length() - 1);
                                builder.deleteCharAt(builder.length() - 1);
                                builder.append("]");
                                arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).
                                        getActionId(), null, builder.toString()));

                            }
                        } else {
                            ArrayList<VideoLongest> videos = new ArrayList<VideoLongest>();
                            for (int j = 0; j < input.getSerials().size(); j++) {
                         if (input.getSerials().get(j).checkFilters(input.getCommands().get(i))) {
                                    if (input.getSerials().get(j).getShowDuration() != 0) {
                                        VideoLongest video = new VideoLongest(input.getSerials().
                                                get(j).getTitle(),
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
                                        if (videos.get(k).getDuration() == videos.get(l).
                                                getDuration()) {
                                            if (videos.get(k).getVideoname().compareTo(videos.
                                                    get(l).getVideoname()) > 0)
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
                                     if (videos.get(k).getVideoname().compareTo(videos.get(l).
                                             getVideoname()) < 0) {
                                         Collections.swap(videos, k, l);
                                     }


                                        }
                                    }
                                }
                            }
                            if (videos.size() == 0) {
                                arrayResult.add(fileWriter.writeFile(input.getCommands().
                                     get(i).getActionId(), null, "Query result: []"));

                            } else {
                                StringBuilder builder = new StringBuilder();
                                builder.append("Query result: [");
                                int counter = 0;
                                for (int i1 = 0; i1 < videos.size(); i1++) {
                                    if (counter == input.getCommands().get(i).getNumber()) {
                                        break;
                                    }

                                    builder.append(videos.get(i1).getVideoname());

                                    builder.append(",");
                                    builder.append(' ');


                                    counter++;

                                }
                                builder.deleteCharAt(builder.length() - 1);
                                builder.deleteCharAt(builder.length() - 1);
                                builder.append("]");
                                arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).
                                        getActionId(), null, builder.toString()));

                            }
                        }

                    }
                    if (input.getCommands().get(i).getCriteria().equals("most_viewed")) {
                        if (input.getCommands().get(i).getObjectType().equals("movies")) {
                            ArrayList<VideoViews> videos = new ArrayList<VideoViews>();
                            for (int j = 0; j < input.getMovies().size(); j++) {
                           if (input.getMovies().get(j).checkFilters(input.getCommands().get(i))) {
                                    if (input.getMovies().get(j).getViews(input.getUsers()) != 0) {
                                        VideoViews video = new VideoViews(input.getMovies().
                                                get(j).getTitle(),
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
                                            if (videos.get(k).getVideoname().compareTo(videos.
                                                    get(l).getVideoname()) > 0) {
                                                Collections.swap(videos, k, l);
                                            }


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
                                            if (videos.get(k).getVideoname().compareTo(videos.
                                                    get(l).getVideoname()) < 0) {
                                                Collections.swap(videos, k, l);
                                            }


                                        }
                                    }
                                }
                            }
                            if (videos.size() == 0) {
                                arrayResult.add(fileWriter.writeFile(input.getCommands().
                                     get(i).getActionId(), null, "Query result: []"));

                            } else {
                                StringBuilder builder = new StringBuilder();
                                builder.append("Query result: [");
                                int counter = 0;
                                for (int i1 = 0; i1 < videos.size(); i1++) {
                                    if (counter == input.getCommands().get(i).getNumber()) {
                                        break;
                                    }

                                    builder.append(videos.get(i1).getVideoname());

                                    builder.append(",");
                                    builder.append(' ');


                                    counter++;

                                }
                                builder.deleteCharAt(builder.length() - 1);
                                builder.deleteCharAt(builder.length() - 1);
                                builder.append("]");
                                arrayResult.add(fileWriter.writeFile(input.getCommands().get(i)
                                        .getActionId(), null, builder.toString()));

                            }
                        } else {
                            ArrayList<VideoViews> videos = new ArrayList<VideoViews>();
                            for (int j = 0; j < input.getSerials().size(); j++) {
                         if (input.getSerials().get(j).checkFilters(input.getCommands().get(i))) {
                                    if (input.getSerials().get(j).getViews(input.getUsers()) != 0) {
                                        VideoViews video = new VideoViews(input.getSerials().get(j).
                                                getTitle(),
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
                                            if (videos.get(k).getVideoname().compareTo(videos.
                                                    get(l).getVideoname()) > 0) {
                                                Collections.swap(videos, k, l);
                                            }


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
                                            if (videos.get(k).getVideoname().compareTo(videos.
                                                    get(l).getVideoname()) < 0) {
                                                Collections.swap(videos, k, l);
                                            }


                                        }
                                    }
                                }
                            }
                            if (videos.size() == 0) {
                                arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).
                                        getActionId(), null, "Query result: []"));

                            } else {
                                StringBuilder builder = new StringBuilder();
                                builder.append("Query result: [");
                                int counter = 0;
                                for (int i1 = 0; i1 < videos.size(); i1++) {
                                    if (counter == input.getCommands().get(i).getNumber()) {
                                        break;
                                    }

                                    builder.append(videos.get(i1).getVideoname());

                                    builder.append(",");
                                    builder.append(' ');


                                    counter++;

                                }
                                builder.deleteCharAt(builder.length() - 1);
                                builder.deleteCharAt(builder.length() - 1);
                                builder.append("]");
                                arrayResult.add(fileWriter.writeFile(input.getCommands().get(i)
                                        .getActionId(), null, builder.toString()));

                            }
                        }
                    }
                    if (input.getCommands().get(i).getCriteria().equals("num_ratings")) {

                        ArrayList<UserRating> users = new ArrayList<UserRating>();
                        for (int j = 0; j < input.getUsers().size(); j++) {
                            if (input.getUsers().get(j).getNoRatings() != 0) {
                             UserRating user = new UserRating(input.getUsers().get(j).getUsername(),
                                        input.getUsers().get(j).getNoRatings());
                                users.add(user);
                            }
                        }

                        if (input.getCommands().get(i).getSortType().equals("asc")) {
                            for (int k = 0; k < users.size(); k++) {
                                for (int l = k + 1; l < users.size(); l++) {
                             if (users.get(k).getNoofratings() > users.get(l).getNoofratings()) {
                                        Collections.swap(users, k, l);


                                    }
                                }
                            }
                            for (int k = 0; k < users.size(); k++) {
                                for (int l = k + 1; l < users.size(); l++) {
                            if (users.get(k).getNoofratings() == users.get(l).getNoofratings()) {
                                        if (users.get(k).getUsername().compareTo(users.get(l)
                                                .getUsername()) > 0) {
                                            Collections.swap(users, k, l);
                                        }

                                    }
                                }
                            }
                        } else {
                            for (int k = 0; k < users.size(); k++) {
                                for (int l = k + 1; l < users.size(); l++) {
                               if (users.get(k).getNoofratings() < users.get(l).getNoofratings()) {
                                        Collections.swap(users, k, l);


                                    }
                                }
                            }
                            for (int k = 0; k < users.size(); k++) {
                                for (int l = k + 1; l < users.size(); l++) {
                                    if (users.get(k).getNoofratings() == users.get(l).
                                            getNoofratings()) {
                                        if (users.get(k).getUsername().compareTo(users.get(l).
                                                getUsername()) < 0) {
                                            Collections.swap(users, k, l);
                                        };


                                    }
                                }
                            }

                        }
                        if (users.size() == 0) {
                            arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).
                                    getActionId(), null, "Query result: []"));

                        } else {
                            StringBuilder builder = new StringBuilder();
                            builder.append("Query result: [");
                            int counter = 0;
                            for (int i1 = 0; i1 < users.size(); i1++) {
                                if (counter == input.getCommands().get(i).getNumber()) {
                                    break;
                                }

                                builder.append(users.get(i1).getUsername());
                                builder.append(",");
                                builder.append(' ');


                                counter++;

                            }
                            builder.deleteCharAt(builder.length() - 1);
                            builder.deleteCharAt(builder.length() - 1);
                            builder.append("]");
                            arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).
                                    getActionId(), null, builder.toString()));

                        }
                    }


                }
                if (input.getCommands().get(i).getActionType().equals("recommendation")) {
                    if (input.getCommands().get(i).getType().equals("standard")) {
                        for (int j = 0; j < input.getUsers().size(); j++) {
                            if (input.getUsers().get(j).getUsername().equals(input.getCommands().
                                    get(i).getUsername())) {
                                if (input.getUsers().get(j).getStandard(input.getMovies(),
                                        input.getSerials(), input.getCommands().get(i),
                                        fileWriter) == null) {
                                    arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                                            null, "StandardRecommendation cannot be applied!"));
                                }
                                else {
                                    arrayResult.add(input.getUsers().get(j).getStandard(input.getMovies(),
                                            input.getSerials(), input.getCommands().get(i),
                                            fileWriter));
                                }
                            }
                        }
                    }
                    if (input.getCommands().get(i).getType().equals("best_unseen")) {
                        ArrayList<VideoRatingId> videos = new ArrayList<VideoRatingId>();
                        for (int j = 0; j < input.getUsers().size(); j++) {
                            if (input.getUsers().get(j).getUsername().equals(input.getCommands().
                                    get(i).getUsername())) {
                                for (int k = 0; k < input.getMovies().size(); k++) {
                                    if (!input.getUsers().get(j).getHistory().containsKey(input.
                                            getMovies().get(k).getTitle())) {
                                        VideoRatingId video = new VideoRatingId(input.getMovies().
                                                get(k).getTitle(),
                                                input.getMovies().get(k).getMovieRating(input.
                                                        getUsers()), k);
                                        videos.add(video);
                                    }
                                }
                                for (int k = 0; k < input.getSerials().size(); k++) {
                                    if (!input.getUsers().get(j).getHistory().containsKey(input.
                                            getSerials().get(k).getTitle())) {
                                        VideoRatingId video = new VideoRatingId(input.getSerials()
                                                .get(k).getTitle(),
                                                input.getSerials().get(k).getShowRating(input.
                                                        getUsers()), k);
                                        videos.add(video);
                                    }
                                }
                                for (int i1 = 0; i1 < videos.size(); i1++) {
                                    for (int i2 = i1 + 1; i2 < videos.size(); i2++) {
                          if (videos.get(i1).getVideorating() < videos.get(i2).getVideorating()) {
                              Collections.swap(videos, i1, i2);
                          }
                                    }
                                }
                                for (int i1 = 0; i1 < videos.size(); i1++) {
                                    for (int i2 = i1 + 1; i2 < videos.size(); i2++) {
                       if (videos.get(i1).getVideorating() == videos.get(i2).getVideorating())
                                       if (videos.get(i1).getIndex() > videos.get(i2).getIndex()) {
                                           Collections.swap(videos, i1, i2);
                                       }
                                    }
                                }
                                if (videos.size() != 0)
                                    arrayResult.add(fileWriter.writeFile(input.getCommands().get(i)
                      .getActionId(), null, "BestRatedUnseenRecommendation result:"
                                            + ' ' + videos.get(0).getVideoname()));
                                else
                                    arrayResult.add(fileWriter.writeFile(input.getCommands().
                                                    get(i).getActionId(),
                       null, "BestRatedUnseenRecommendation cannot be applied!"));
                            }

                        }
                    }
                    if (input.getCommands().get(i).getType().equals("popular")) {

                        ArrayList<GenrePopularity> genres = new ArrayList<GenrePopularity>();
                        for (Genre genre : Genre.values()) {
                            int popularity = 0;
                            for (int j = 0; j < input.getMovies().size(); j++) {
                            if (input.getMovies().get(j).getGenres().contains(genre.toString())) {
                                  popularity += input.getMovies().get(j).getViews(input.getUsers());
                                }
                            }
                            for (int j = 0; j < input.getSerials().size(); j++) {
                           if (input.getSerials().get(j).getGenres().contains(genre.toString())) {
                                 popularity += input.getSerials().get(j).getViews(input.getUsers());
                                }
                            }
                            GenrePopularity genre1 = new GenrePopularity(genre, popularity);
                            genres.add(genre1);
                        }
                        for (int i1 = 0; i1 < genres.size(); i1++) {
                            for (int i2 = i1 + 1; i2 < genres.size(); i2++) {
                                if (genres.get(i1).getPopularity() < genres.get(i2).getPopularity())
                                    Collections.swap(genres, i1, i2);
                            }
                        }
                        for (int j = 0; j < input.getUsers().size(); j++) {
                            if (input.getUsers().get(j).getUsername().equals(input.getCommands().
                                    get(i).getUsername())) {
                                if (input.getUsers().get(j).getSubscriptionType().equals("BASIC")) {
                                    arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).
                                                    getActionId(),
                                 null, "PopularRecommendation cannot be applied!"));
                                } else {
                                    int idx = 1;
                                    int genre_idx = 0;
                                    while (idx == 1) {
                          for (int i1 = 0; i1 < input.getMovies().size(); i1++) {
                       if (!input.getUsers().get(j).getHistory().containsKey(input.getMovies().
                               get(i1).getTitle())) {
                         for (int l = 0; l < input.getMovies().get(i1).getGenres().size(); l++) {
                                     if (input.getMovies().get(i1).getGenres().get(l).toUpperCase().
                            equals(genres.get(genre_idx).getGenre().toString().toUpperCase())) {
                                     arrayResult.add(fileWriter.writeFile(input.getCommands().
                                                             get(i).getActionId(), null,
                                              "PopularRecommendation result:" + ' ' +
                                                      input.getMovies().get(i1).getTitle()));
                                                   idx = 0;
                                                        i1 = input.getMovies().size();
                                                        break;

                                                    }
                                                }
                                            }
                                        }
                             if (idx == 1) {
                             for (int i2 = 0; i2 < input.getSerials().size(); i2++) {
                                      if (!input.getUsers().get(j).getHistory().
                                              containsKey(input.getSerials().get(i2).getTitle())) {
                           for (int l = 0; l < input.getSerials().get(i2).getGenres().size(); l++) {
                              if (input.getSerials().get(i2).getGenres().get(l).toUpperCase().
                         equals(genres.get(genre_idx).getGenre().toString().toUpperCase())) {
                                    arrayResult.add(fileWriter.writeFile(input.getCommands().
                                                    get(i).getActionId(), null,
                                          "PopularRecommendation result:" + ' ' +
                                                  input.getSerials().get(i2).getTitle()));
                                                            idx = 0;
                                                            i2 = input.getSerials().size();
                                                            break;

                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        genre_idx++;
                                        if (genre_idx == genres.size()) {
                                            if (idx == 1) {
                                                System.out.println("Da");
                              arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).
                                              getActionId(), null,
                                     "PopularRecommendation cannot be applied!"));
                                                break;
                                            }
                                        }

                                    }
                                }
                            }
                        }


                    }
                    if (input.getCommands().get(i).getType().equals("favorite")) {
                        for (int j = 0; j < input.getUsers().size(); j++) {
                            if (input.getUsers().get(j).getUsername().equals(input.
                                    getCommands().get(i).getUsername())) {
                                if (input.getUsers().get(j).getSubscriptionType().equals("BASIC")) {
                                    arrayResult.add(fileWriter.writeFile(input.getCommands().
                                                    get(i).getActionId(),
                                  null, "FavoriteRecommendation cannot be applied!"));
                                } else {
                                    ArrayList<ShowRecommandationFavorite> shows = new ArrayList<ShowRecommandationFavorite>();
                                    for (int i1 = 0; i1 < input.getMovies().size(); i1++) {
                                        if (!input.getUsers().get(j).getHistory().containsKey(input.
                                                getMovies().get(i1).getTitle())) {
                                            if (input.getMovies().get(i1).getFavoriteRating(input.
                                                    getUsers(), input.getUsers().get(j)) != 0) {
            ShowRecommandationFavorite show = new ShowRecommandationFavorite(input.getMovies()
                                                        .get(i1).getTitle(),
                              input.getMovies().get(i1).getFavoriteRating(input.getUsers(), input.
                                                       getUsers().get(j)), i1);
                                                shows.add(show);
                                            }
                                        }
                                    }
                                    for (int i2 = 0; i2 < input.getSerials().size(); i2++) {
                           if (!input.getUsers().get(j).getHistory().containsKey(input.getSerials().
                                                get(i2).getTitle())) {
                              if (input.getSerials().get(i2).getFavoriteRating(input.getUsers(),
                                                    input.getUsers().get(j)) != 0) {
                            ShowRecommandationFavorite show = new ShowRecommandationFavorite(input.
                                        getSerials().get(i2).getTitle(),
                                      input.getSerials().get(i2).getFavoriteRating(input.getUsers(),
                                                                input.getUsers().get(j)), i2);
                                                shows.add(show);
                                            }
                                        }
                                    }
                                    for (int l = 0; l < shows.size(); l++) {
                                        for (int k = l + 1; k < shows.size(); k++) {
                          if (shows.get(l).getFavoriteappereances() < shows.get(k).
                                  getFavoriteappereances()) {
                                                Collections.swap(shows, k, l);
                                            }
                                        }

                                    }

                                    if (shows.size() == 0) {

                                        arrayResult.add(fileWriter.writeFile(input.getCommands().
                                                        get(i).getActionId(), null,
                                            "FavoriteRecommendation cannot be applied!"));
                                    } else {
                                        arrayResult.add(fileWriter.writeFile(input.getCommands().
                                                        get(i).getActionId(), null,
               "FavoriteRecommendation result:" + ' ' + shows.get(0).getShowtitle()));
                                    }


                                }
                            }

                        }

                    }
                    if (input.getCommands().get(i).getType().equals("search")) {
                        for (int j = 0; j < input.getUsers().size(); j++) {
                            if (input.getUsers().get(j).getUsername().equals(input.getCommands().
                                    get(i).getUsername())) {
                                if (input.getUsers().get(j).getSubscriptionType().equals("BASIC")) {
                                    arrayResult.add(fileWriter.writeFile(input.getCommands().get(i).
                                                    getActionId(),
                                    null, "SearchRecommendation cannot be applied!"));
                                } else {
                                    ArrayList<ShowSearch> shows = input.getUsers().get(j).getSearch
                                            (input.getCommands().get(i).getGenre(),
                                           input.getMovies(), input.getSerials(), input.getUsers());
                                    if (shows.size() == 0) {
                                        arrayResult.add(fileWriter.writeFile(input.getCommands().
                                                        get(i).getActionId(), null,
                                                "SearchRecommendation cannot be applied!"));
                                    } else {
                                        for (int i1 = 0; i1 < shows.size(); i1++) {
                                            for (int i2 = i1 + 1; i2 < shows.size(); i2++) {
                                       if (shows.get(i1).getRating() > shows.get(i2).getRating())
                                                    Collections.swap(shows, i1, i2);
                                            }

                                        }
                                        for (int i1 = 0; i1 < shows.size(); i1++) {
                                            for (int i2 = i1 + 1; i2 < shows.size(); i2++) {
                                       if (shows.get(i1).getRating() == shows.get(i2).getRating()) {
                                                   if (shows.get(i1).getShowtitle().compareTo(shows.
                                                            get(i2).getShowtitle()) > 0)
                                                        Collections.swap(shows, i1, i2);
                                                }
                                            }
                                        }

                                        StringBuilder stringBuilder = new StringBuilder();
                                        stringBuilder.append("SearchRecommendation result: [");
                                        for (int k = 0; k < shows.size(); k++) {
                                            stringBuilder.append(shows.get(k).getShowtitle());
                                            if (k < shows.size() - 1) {
                                                stringBuilder.append(",");
                                                stringBuilder.append(" ");
                                            }

                                        }
                                        stringBuilder.append("]");
                                        arrayResult.add(fileWriter.writeFile(input.getCommands().
                                    get(i).getActionId(), null, stringBuilder.toString()));
                                    }

                                }
                            }

                        }


                    }

                }


            }
        }


        fileWriter.closeJSON(arrayResult);
    }

}
