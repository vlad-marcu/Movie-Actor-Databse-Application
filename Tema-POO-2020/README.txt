Am inceput prin a itera prin toate comenzile, executandu-le in funtie de tipul 
lor.

Commands:

Favorite - folosesc functia getFavorite din UserInputData pentru a verifica daca
un video este vazut, si in acest caz daca este deja in lista de favorite a
userului.Daca nu este, se adauga.

View - folosesc functia getView din UserInputData pentru a vedea daca videoul
este vazut, caz in care incrementez numarul de viewuri, altfel setez numarul
de viewuri ca si 1.

Rating - folosesc functia getRating din UserInputData pentru a adauga ratingul
unui video, daca acesta este vazut.Pentru a stoca ratingurile filmelor am
folosit un HashMap numit movieRatings.Pentru seriale am creat clasele TvShow
si Series pentru a stoca ratingurile.

Querys:

Average - am creat functia getMovieRating in MovieInputData care calculeaza
ratingul unui film, respectiv functia getShowRating care calculeaza ratingul
unui serial.Apoi cu ajutorul functiei getActorRating am calculat ratingul unui
actor.Apoi am creat clasa ActorRating care retine numele unui actor si ratingul
sau.Am adaugat toti actorii si ratingul lor intr-un array de ActorRating si l-am
sortat conform criteriilor din cerinta.Apoi am returnat primii N actori.

Awards - cu ajutorul functiei getAwardsNumber calculez numarul de awards date in 
filters ale actorului.Apoi am creat clasa ActorAwards care retine numele unui actor 
si numarul sau de awards.Am adaugat toti actorii si numarul lor de awards lor intr-un
array de ActorAwards si l-am sortat conform criteriilor din cerinta.Apoi am returnat
primii N actori.

Filter description - cu ajutorul functiei checkActorKeyWords din ActorInputData am
verificat daca actorul contine in descrierea sa cuvintele din filters.Apoi am adaugat
actorii care le contineau intr-un array, i-am sortat alfabetic si am returnat primii
N actori.

Rating - intai am verificat filterele din filters la filme si la seriale cu ajutorul
functiei checkFilters din ShowInput.Apoi cu ajutorul clasei VideoRating am salvat
titlul videourilor si ratingul lor.Apoi am creat un array de VideoRating si l-am
sortat conform criteriilor din enunt.Apoi am returnat primele N videouri.

Favorite - intai am verificat filterele din filters la filme si la seriale cu ajutorul
functiei checkFilters din ShowInput.Apoi cu ajutorul functiei getFavourites din 
ShowInput am calculat numarul de aparitii al fiecarui video in listele de videouri
favorite ale userilor.Apoi cu ajutorul clasei VideoFavorite am salvat numele videoului
si numarul sau de aparitii.Apoi am creat un array de VideoFavorite si l-am
sortat conform criteriilor din enunt.Apoi am returnat primele N videouri.

Longest -  intai am verificat filterele din filters la filme si la seriale cu ajutorul
functiei checkFilters din ShowInput.Apoi cu ajutorul functiilor getDuration si
getShowDuration din MovieInputData,respectiv SerialInputData  am calculat durata
fiecarui video.Apoi cu ajutorul clasei VideoLongest am salvat numele videoului
si durata sa.Apoi am creat un array de VideoLongest si l-am sortat conform
criteriilor din enunt.Apoi am returnat primele N videouri.

Most Viewed - intai am verificat filterele din filters la filme si la seriale cu ajutorul
functiei checkFilters din ShowInput.Apoi cu ajutorul functiei getViews din ShowInput am 
calculat numarul de views.Apoi cu ajutorul clasei VideoViews am salvat numele videoului
si numarul sau de views.Apoi am creat un array de VideoViews si l-am sortat conform
criteriilor din enunt.Apoi am returnat primele N videouri.

Number of ratings - intai am verificat filterele din filters la filme si la seriale cu ajutorul
functiei checkFilters din ShowInput.Cu functia getNoRatings din UserInputData am calculat
cate ratinguri a dat userul respectiv.Apoi cu ajutorul clasei UserRating am salvat numele 
userului si cate ratinguri a dat.Apoi am creat un array de UserRating si l-am sortat conform
criteriilor din enunt.Apoi am returnat primele N videouri.

Recommendations:

Standard - cu ajutorul functiei getStandard din UserInputData gasim primul video nevazut
din baza de date.

Best unseen - am creat clasa VideoRatingId unde stocam numele videoului si ratingul sau.
Am creat un array de VideoRatingId, unde am pus toate videourile nevazute de user.Apoi
l-am sortat conform criteriilor si am returnat primul video.

Popular - am creat clasa GenrePopularity unde am stocat genul si popularitatea sa.Apoi am
creat un array de GenrePopulariy si l-am sortat pentru a afla cel mai popular gen.Apoi am
returnat primul video nevazut de user din acel gen.

Favorite - cu ajutorul clasei ShowRecommandationFavorite am stocat numele videoului si
numarul de aparitii in lista de favourite ale userilor.Am salvat toate videourile intr-un
array de ShowRecommandationFavorite si l-am sortat conform criteriilor, apoi am returnat primul
video nevazut de user din acel array.

Search - am creat clasa ShowSearch unde am stocat numele videoului si ratingul sau.Am
creat un array de ShowSearch cu toate videoclipurile dintr-un anumit gen, nevazute
de user si l-am sortat conform criteriilor.Apoi am returnat toate videourile.






