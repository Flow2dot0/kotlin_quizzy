//package com.example.quizzy.services
//import android.util.Log
//import com.example.quizzy.models.ApiQuestion
//import retrofit2.Call
//import retrofit2.http.GET
//import retrofit2.Retrofit
//import retrofit2.converter.moshi.MoshiConverterFactory
//import retrofit2.Callback
//import retrofit2.Response
//
//class APIService {
//
//    val TAG = "API Service"
//
//    private val url = ""
//    private var service = null
//
//    fun init(){
//        val retrofit = Retrofit.Builder()
//            .baseUrl(url)
//            .addConverterFactory(MoshiConverterFactory.create())
//            .build()
//
//        service = retrofit.create(QuestionsService::class.java)
//    }
//
//    fun getData(){
//
//        val questionRequest = service.listQuestions()
//
//        questionRequest.enqueue(object : Callback<List<ApiQuestion>> {
//
//            override fun onResponse(call: Call<List<ApiQuestion>>, response: Response<List<ApiQuestion>>) {
//
//                val allQuestions = response.body()
//                if (allQuestions != null) {
//                    Log.i(TAG, "sucessfully get the data")
//                    for (q in allQuestions)
//                        Log.i(TAG, "question : ${q}")
//                    // TODO : handle datas
//                }
//            }
//            override fun onFailure(call: Call<List<ApiQuestion>>, t: Throwable) {
//                Log.e(TAG, "error during GET protocol")
//            }
//        })
//
//    }
//
//}
//
//interface QuestionsService{
//    @GET("/")
//    fun listQuestions() : Call<List<ApiQuestion>>
//
//    // TODO : to complete 14 BONUS
//}
//
//
////https://www.chillcoding.com/blog/2017/03/14/requete-http-get-retrofit-android/
////https://mobile-courses-server.herokuapp.com/courses
////https://www.openquizzdb.org/api_config.php