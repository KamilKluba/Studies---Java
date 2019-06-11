#include <stdio.h>
#include <jni.h>

JNIEXPORT jint JNICALL Java_Main_moveAIC(JNIEnv *env, jobject jniobject, jcharArray jnicharArray, jchar jnichar){
    jchar* board = (*env)->GetCharArrayElements(env, jnicharArray, null);

    int random;

    srand(time(0));

    do{
        int random = rand() % 16;
    }while(board[i] != ' ')

    return random;
}