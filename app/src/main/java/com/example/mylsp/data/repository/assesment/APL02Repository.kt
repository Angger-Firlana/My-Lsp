package com.example.mylsp.data.repository.assesment

import android.content.Context
import com.example.mylsp.data.remote.api.APIClient
import com.example.mylsp.data.model.api.assesment.Apl02
import com.example.mylsp.model.api.ResponseSubmission
import com.example.mylsp.model.api.SubmissionGroup
import com.example.mylsp.data.model.api.assesment.Apl02Response
import com.example.mylsp.data.model.api.assesment.GetAPL02Response
import com.example.mylsp.data.model.api.assesment.PostApproveRequest
import com.example.mylsp.data.model.api.assesment.PostApproveResponse

class APL02Repository(context: Context) {
    private val api = APIClient.getClient(context)

    suspend fun getApl02(id: Int): Result<_root_ide_package_.com.example.mylsp.data.model.api.assesment.Apl02> {
        return try {
            val response = api.getAPL02(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response Body Is Null"))
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception(errorBody))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendSubmission(submission: SubmissionGroup): Result<ResponseSubmission> {
        return try {
            val response = api.sendSubmission(submissionRequest = submission)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response Body Is Null"))
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception(errorBody))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getSubmissionByAsesi(asesiId: Int): Result<_root_ide_package_.com.example.mylsp.data.model.api.assesment.GetAPL02Response?> {
        return try {
            val response = api.getApl02ByAsesi(id = asesiId)
            if (response.isSuccessful) {
                val body = response.body()
                // Return success even if body is null (no submission found)
                Result.success(body)
            } else {
                // Handle different HTTP status codes
                when (response.code()) {
                    404 -> {
                        // No submission found - this is normal, return success with null
                        Result.success(null)
                    }
                    else -> {
                        val errorBody = response.errorBody()?.string() ?: "Unknown error"
                        Result.failure(Exception("HTTP ${response.code()}: $errorBody"))
                    }
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun approveApl02(assesiId: Int, postApproveRequest: _root_ide_package_.com.example.mylsp.data.model.api.assesment.PostApproveRequest): Result<_root_ide_package_.com.example.mylsp.data.model.api.assesment.PostApproveResponse> {
        return try {
            val response = api.postApproveApl02(id = assesiId, request = postApproveRequest)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response Body Is Null"))
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception("HTTP ${response.code()}: $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}