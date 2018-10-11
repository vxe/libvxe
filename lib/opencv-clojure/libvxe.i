%module libvxe


%typemap(jstype) cv::Mat "org.opencv.core.Mat" // the C/C++ type cv::Mat corresponds to the JAVA type org.opencv.core.Mat (jstype: C++ type corresponds to JAVA type)

%typemap(javain) cv::Mat "$javainput.getNativeObjAddr()" // javain tells SWIG how to pass the JAVA object to the intermediary JNI class (e.g. swig_exampleJNI.java); see next step also

%typemap(jtype) cv::Mat "long" // the C/C++ type cv::Mat corresponds to the JAVA intermediary type long. JAVA intermediary types are used in the intermediary JNI class (e.g. swig_exampleJNI.java)

%typemap(jni) cv::Mat "jlong" // the C/C++ type cv::Mat corresponds to the C/C++ JNI type long, which is used in the generated C/C++ JNI functions in e.g. swig_example_wrap.cpp 

// the typemap for in specifies how to create the C/C++ object out of the datatype specified in jni
// this is C/C++ code which is injected in the C/C++ JNI function to create the cv::Mat for further processing in the C/C++ code
%typemap(in) cv::Mat {
        $1 = *(cv::Mat **)&$input;
}

%typemap(javaout) cv::Mat {
     return new org.opencv.core.Mat($jnicall);
}


 /* Parse the header file to generate wrappers */
%include "swig_example.h"

