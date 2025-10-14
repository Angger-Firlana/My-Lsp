package com.example.mylsp.data.repository.assesment

import android.content.Context
import androidx.compose.runtime.saveable.Saver
import com.example.mylsp.data.api.assesment.QuestionResponse
import com.example.mylsp.data.remote.api.APIClient
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream

class QuestionRepository(context:Context) {
    private val api = APIClient.getClient(context)

    suspend fun getQuestionBySkema(id:Int):Result<QuestionResponse>{
        val response = api.getQuestionsBySkema(id)

        return try{
            if(response.isSuccessful){
                val body = response.body()
                if (body == null){
                    Result.failure(Exception("body is null"))
                }else{
                    Result.success(body)
                }
            }else{
                Result.failure(Exception(response.errorBody().toString()))
            }
        }catch (e:Exception){
            Result.failure(e)
        }

    }
    suspend fun downloadQuestion(id:Int, savePath:String):Result<File>{
        return try {
            val response = api.downloadQuestion(id)

            if (response.isSuccessful){
                val body = response.body()
                if (body != null){
                    val file = File(savePath)
                    body.byteStream().use { input ->
                        FileOutputStream(file).use { output ->
                            val buffer = ByteArray(8 * 1024)
                            var bytesRead: Int
                            while (input.read(buffer).also { bytesRead = it } != -1) {
                                output.write(buffer, 0, bytesRead)
                            }
                        }
                    }
                    Result.success(file)
                }else{
                    Result.failure(Exception("body is null"))
                }
            }else{
                Result.failure(Exception(response.errorBody()?.string() ?: "Download failed"))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }
}