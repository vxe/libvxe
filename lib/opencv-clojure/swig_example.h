#include "opencv.hpp"
class MySwigExample{
public:
MySwigExample();
        void setUp();
        void run(const cv::Mat inputImage, MySwigResult &result);
};
class MySwigResult{
public:
MySwigResult();
MySwigResult(const cv::Mat &thresholdedImage, const cv::Rect &regionOfInterest);
        cv::Mat getThresholdedImage();
        cv::Rect getRegionOfInterest();
        void setThresholdedImage(const cv::Mat &thresholdedImage);
        void setRegionOfInterest(const cv::Rect &regionOfInterest);
private:
        cv::Mat thresholdedImage;
        cv::Rect regionOfInterest;
};
