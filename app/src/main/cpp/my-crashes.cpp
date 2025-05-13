#include <jni.h>
#include <string>
#include <stdlib.h>

#include <pthread.h>
#include <signal.h>
#include <unistd.h>
#include <android/log.h>
#include <sys/syscall.h>
#include <unistd.h>

void* killer_thread(void*) {
    sleep(1); // Даем основному потоку время на запуск
    pthread_kill(pthread_self(), SIGABRT); // Краш текущего потока
    return nullptr;
}


void crash() {
    volatile int* ptr = nullptr;
    *ptr = 42; // Краш из-за SIGSEGV
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_ironmeddie_myndkapplication_NativeHelper_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT void JNICALL
Java_com_ironmeddie_myndkapplication_NativeHelper_crashAppAbrt(JNIEnv* env, jobject thiz) {
    abort(); // SIGABRT
}

extern "C" JNIEXPORT void JNICALL
Java_com_ironmeddie_myndkapplication_NativeHelper_crashAppSegv(JNIEnv* env, jobject thiz) {
    crash();  // SIGSEGV
}

extern "C" JNIEXPORT void JNICALL
Java_com_ironmeddie_myndkapplication_NativeHelper_startCrash(JNIEnv*, jobject) {
    pthread_t thread;
    pthread_create(&thread, nullptr, killer_thread, nullptr);
    pthread_detach(thread);
    while (true) {} // Бесконечный цикл (чтобы поток не завершился раньше краша)
}

extern "C" JNIEXPORT void JNICALL
Java_com_ironmeddie_myndkapplication_NativeHelper_circleCrash(JNIEnv*, jobject) {
    volatile int buf[1];
    for (int i = 0; ; i++) {
        buf[i] = 0xDEADBEEF; // Записываем данные за границы массива
    }
}

extern "C" JNIEXPORT void JNICALL
Java_com_ironmeddie_myndkapplication_NativeHelper_tgkillCrash(JNIEnv*, jobject) {
    syscall(__NR_tgkill, getpid(), gettid(), SIGABRT);
}
