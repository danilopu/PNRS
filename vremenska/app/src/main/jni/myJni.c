//
// Created by student on 29.5.2019.
//

#include "myJni.h"

JNIEXPORT jint JNICALL Java_com_example_vremenska_MyNDK_conversion
  (JNIEnv *env, jobject obj, jint t, jint c){

        if(c == 0) {
            return t;
        } else {
            t = t * (9/5) + 32;
        }

        return t;
  }
