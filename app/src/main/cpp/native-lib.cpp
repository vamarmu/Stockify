//
// Created by Marce Cz on 17/04/2021.
//

#include <jni.h>
#include <string>

extern "C"

JNIEXPORT jstring JNICALL
Java_ar_team_stockify_network_Keys_apiKey(JNIEnv *env, jobject object) {
    std::string api_key = "5ZH64HR26H20SJ7T";
    return env->NewStringUTF(api_key.c_str());
}