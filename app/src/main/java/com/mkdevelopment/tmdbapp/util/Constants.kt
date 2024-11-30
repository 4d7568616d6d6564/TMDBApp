package com.mkdevelopment.tmdbapp.util

class Constants {
    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/movie/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w600"
        const val TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxM2Y3YjRmZTcyNzFiMDBkZTNhMmJkYjRlZWIwZDQ1NiIsIm5iZiI6MTczMjg5NzU5Ni4xODUyMzc0LCJzdWIiOiI2NzQ5ZWEyYzA5MzZiNmU0ZmI5ZmE2OGUiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.lc2YZTuQDkDEgQc2Xc59pHz3B6tuiJ946mH3cAw4mjI"
        const val OKHTTP_TIMEOUT = 10L
        const val OKHTTP_MAX_RETRIES = 3
    }
}