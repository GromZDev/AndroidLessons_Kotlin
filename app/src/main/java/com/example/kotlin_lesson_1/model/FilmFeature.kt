package com.example.kotlin_lesson_1.model

import android.os.Parcelable
import com.example.kotlin_lesson_1.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmFeature(
    val film: Film = getDefaultFilm(),
    val description: String,
    val actors: String
) : Parcelable

fun getDefaultFilm() = Film("Мстители: Война бесконечности", 1, 8.8, 2019, 222)

// Создал методы, возвращающие массивы фильмов:
fun getAllFilms(): List<FilmFeature> {
    return mutableListOf(
        FilmFeature(Film("Железный человек", R.drawable.iron_man, 7.9, 2008, 121), FilmDadaDescription().getIronManDesc(), "Robert Дауни Jr"),
        FilmFeature(Film("Тор: царство тьмы", R.drawable.thor_2_dark_world, 7.1, 2013, 108), FilmDadaDescription().getThor2DarkWorldDesc(), "Крис Хемсворт"),
        FilmFeature(Film("Мстители: Война бесконечности", R.drawable.film_avengers_inf, 8.5, 2018, 149), FilmDadaDescription().getAvengersInfWarDesc(), "Крис Хемсворт"),
        FilmFeature(Film("Железный человек 2", R.drawable.iron_man_2, 7.0, 2010, 125), FilmDadaDescription().getIronMan2Desc(), "Крис Хемсворт"),
        FilmFeature(Film("Тор", R.drawable.thor_1, 7.3, 2011, 115), FilmDadaDescription().getThorDesc(), "Крис Хемсворт"),
        FilmFeature(Film("Мстители", R.drawable.avengers, 8.0, 2012, 137), FilmDadaDescription().getAvengersDesc(), "Крис Хемсворт"),
        FilmFeature(Film("Железный Человек 3", R.drawable.iron_man_3, 7.2, 2013, 125), FilmDadaDescription().getIronMan3Desc(), "Robert Дауни Jr"),
        FilmFeature(Film("Тор: рагнарёк", R.drawable.thor_3_ragnarok, 7.9, 2017, 130), FilmDadaDescription().getThor3RagnarokDesc(), "Крис Хемсворт"),
        FilmFeature(Film("Мстители: эра альтрона", R.drawable.avengers_2_age_of_ulthron, 7.4, 2015, 141), FilmDadaDescription().getAvengers2UltDesc(), "Крис Хемсворт"),
        FilmFeature(Film("Мстители. Финал", R.drawable.film_avengers_end_game, 8.4, 2019, 181), FilmDadaDescription().getAvengersEndGameDesc(), "Крис Хемсворт")
    )
}

fun getPopularFilms(): List<FilmFeature> {
    return mutableListOf(
        FilmFeature(Film("Star Wars: Episode IV: Новая надежда", R.drawable.sw_episode_4, 8.6, 1977, 121), FilmDadaDescription().getSW4(), "Марк Хэммил"),
        FilmFeature(Film("Star Wars: Episode V: Империя наносит ответный удар", R.drawable.sw_episode_5, 8.7, 1980, 124), FilmDadaDescription().getSW5(), "Харрисон Форд"),
        FilmFeature(Film("Star Wars: Episode VI: Возвращение Джедая", R.drawable.sw_episode_6, 8.3, 1983, 131), FilmDadaDescription().getSW6(), "Кэрри Фишер"),
        FilmFeature(Film("Star Wars: Episode I: Скрытая угроза", R.drawable.sw_episode_1, 6.5, 1999, 136), FilmDadaDescription().getSW1(), "Лайам Нисон"),
        FilmFeature(Film("Star Wars: Episode II: Атака клонов", R.drawable.sw_episode_2, 6.6, 2002, 144), FilmDadaDescription().getSW2(), "Юэн Макгрегор"),
        FilmFeature(Film("Star Wars: Episode III: Месть ситхов", R.drawable.sw_episode_3, 7.5, 2005, 140), FilmDadaDescription().getSW3(), "Сэмюэл Л. Джексон"),
        FilmFeature(Film("Star Wars: Episode VII: Пробуждение силы", R.drawable.sw_episode_7, 7.9, 2015, 138), FilmDadaDescription().getSW7(), "Дэйзи Ридли"),
        FilmFeature(Film("Star Wars: Episode VIII: Последние Джедаи", R.drawable.sw_episode_8, 7.1, 2017, 151), FilmDadaDescription().getSW8(), "Марк Хэмилл"),
        FilmFeature(Film("Star Wars: Episode IX: Скайуокер. Восход", R.drawable.sw_episode_9, 8.2, 2019, 141), FilmDadaDescription().getSW9(), "Дэйзи Ридли")
    )
}
