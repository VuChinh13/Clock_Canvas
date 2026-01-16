package com.example.firstapp.testflow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.resume
import kotlinx.coroutines.*
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class TestFlowRepository {
    fun fetchUser(): Flow<String> {
        return flow {
            emit("Loading")
            delay(2000)
            emit("Vũ Văn Chính")
        }
    }

    fun fetchData(callback: (String) -> Unit) {
        Thread.sleep(1000)
        callback("Data successfully processed")
    }

    suspend fun fetchDataAsync(): String = suspendCoroutine { continuation ->
        fetchData { continuation.resume(it) }
    }

    fun fakeNetworkRequest(onSuccess: (String) -> Unit, onError: (Throwable) -> Unit) {
        Thread {
            try {
                Thread.sleep(2000)
                onSuccess("Hello from server")
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    suspend fun loadData(): String = suspendCancellableCoroutine { continuation ->
        fakeNetworkRequest(onSuccess = { result ->
            if (continuation.isActive) {
                continuation.resume(result)
            }
        }, onError = { error ->
            if (continuation.isActive) {
                continuation.resumeWithException(error)
            }
        })

        // Coroutine cancel
        continuation.invokeOnCancellation {
            // Dọn dẹp tài nguyên
            // Làm gì đó ....
        }
    }
}