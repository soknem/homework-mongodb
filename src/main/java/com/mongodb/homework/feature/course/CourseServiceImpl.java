package com.mongodb.homework.feature.course;

import com.mongodb.homework.base.BaseFilter;
import com.mongodb.homework.base.BaseParamFilter;
import com.mongodb.homework.domain.Course;
import com.mongodb.homework.domain.Section;
import com.mongodb.homework.domain.Video;
import com.mongodb.homework.feature.course.dto.*;
import com.mongodb.homework.feature.section.dto.SectionDetailResponse;
import com.mongodb.homework.feature.section.dto.SectionRequest;
import com.mongodb.homework.feature.section.dto.SectionResponse;
import com.mongodb.homework.feature.video.dto.VideoRequest;
import com.mongodb.homework.feature.video.dto.VideoResponse;
import com.mongodb.homework.feature.video.dto.VideoUpdateRequest;
import com.mongodb.homework.mapper.CourseMapper;
import com.mongodb.homework.mapper.SectionMapper;
import com.mongodb.homework.mapper.VideoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;

    private final SectionMapper sectionMapper;

    private final VideoMapper videoMapper;

    private final CourseRepository courseRepository;

    private final BaseFilter<Course> courseBaseFilter;

    private final BaseParamFilter<Course> courseBaseParamFilter;

    private final MongoTemplate mongoTemplate;


    @Override
    public void creatCourse(CourseRequest courseRequest) {

        Course course = courseMapper.fromRequest(courseRequest);

        course.setIsDrafted(true);

        course.setIsDeleted(false);

        courseRepository.save(course);
    }

    @Override
    public Page<CourseResponse> getAllCourses(String responseType, int pageNumber, int pageSize) {

        Sort sort = Sort.by(Sort.Direction.ASC, "title");

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page<Course> coursePage = courseRepository.findAll(pageRequest);

        if (responseType.equalsIgnoreCase("content_details")) {

            return coursePage.map(courseMapper::toCourseDetailResponse);

        } else {
            return coursePage.map(courseMapper::toCourseSnippetResponse);
        }

    }

    @Override
    public CourseResponse getCourseById(String responseType, String id) {

        Course course =
                courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("course = %s has not been found", id)));

        if (responseType.equalsIgnoreCase("content_details")) {

            return courseMapper.toCourseDetailResponse(course);

        } else {
            return courseMapper.toCourseSnippetResponse(course);
        }
    }

    @Override
    public CourseResponse updateCourseById(String responseType, String id, CourseUpdateRequest courseUpdateRequest) {

        Course course =
                courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("course = %s has not been found", id)));

        courseMapper.updateCourseFromRequest(course, courseUpdateRequest);

        courseRepository.save(course);

        if (responseType.equalsIgnoreCase("content_details")) {

            return courseMapper.toCourseDetailResponse(course);

        } else {
            return courseMapper.toCourseSnippetResponse(course);
        }
    }

    @Override
    public void updateCourseVisibilityById(String id, CourseVisibilityUpdateRequest courseVisibilityUpdateRequest) {

        Course course =
                courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("course = %s has not been found", id)));

        course.setIsDrafted(courseVisibilityUpdateRequest.isDrafted());

        courseRepository.save(course);

    }

    @Override
    public void updateCourseThumbnailById(String id, CourseThumbnailUpdateRequest courseThumbnailUpdateRequest) {

        Course course =
                courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("course = %s has not been found", id)));

        course.setThumbnail(courseThumbnailUpdateRequest.thumbnail());

        courseRepository.save(course);


    }

    @Override
    public void updateCourseIsPaidById(String id, CoursePaidUpdateRequest coursePaidUpdateRequest) {

        Course course =
                courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("course = %s has not been found", id)));

        course.setIsPaid(coursePaidUpdateRequest.isPaid());

        courseRepository.save(course);
    }

    @Override
    public void enableCourseById(String id) {

        Course course =
                courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("course = %s has not been found", id)));

        course.setIsDeleted(false);

        courseRepository.save(course);
    }

    @Override
    public void disableCourseById(String id) {

        Course course =
                courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("course = %s has not been found", id)));

        course.setIsDeleted(true);

        courseRepository.save(course);

    }

    @Override
    public void deleteCourseById(String id) {

        Course course =
                courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("course = %s has not been found", id)));

        courseRepository.delete(course);
    }

    @Override
    public void crateVideo(String id, VideoRequest videoRequest) {

        Course course =
                courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("course = %s has not been found", id)));

        Section section = course.getSections().stream()
                .filter(section1 -> section1.getOrderNo().equals(videoRequest.orderNo()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("section with " +
                        "orderNo =  %s has not been found", videoRequest.orderNo())));

        Video video = videoMapper.fromRequest(videoRequest);

        video.setLectureNo(UUID.randomUUID().toString());

        section.getVideos().add(video);

        courseRepository.save(course);
    }

    @Override
    public VideoResponse updateVideo(String id, VideoUpdateRequest videoUpdateRequest) {

        Course course =
                courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("course = %s has not been found", id)));

        Section section = course.getSections().stream()
                .filter(section1 -> section1.getOrderNo().equals(videoUpdateRequest.orderNo()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("section with " +
                        "orderNo =  %s has not been found", videoUpdateRequest.orderNo())));

        // Find and update the video in the section
        Video video = section.getVideos().stream()
                .filter(vid -> vid.getOrderNo().equals(videoUpdateRequest.orderNo()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("video with order = %s in section = %s of course =%s has not been found",
                                videoUpdateRequest.orderNo(),section.getOrderNo(), course.getId())));

        videoMapper.updateVideoFromRequest(video, videoUpdateRequest);

        courseRepository.save(course);

        return videoMapper.toResponse(video);
    }

    @Override
    public List<SectionResponse> getAllSections(String id) {

        Course course =
                courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("course =  %s has not been found", id)));

        List<Section> sections = course.getSections();

        if (sections == null) return new ArrayList<>();

        return sections.stream().map(sectionMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public void createSection(String courseId, SectionRequest sectionRequest) {

        Course course =
                courseRepository.findById(courseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("course  = %s has not been found", courseId)));

        Section section = sectionMapper.fromRequest(sectionRequest);


        if (course.getSections() == null) {
            course.setSections(List.of(section));
        } else {
            course.getSections().add(section);
        }

        courseRepository.save(course);
    }

    @Override
    public Page<CourseResponse> filterCourseByBody(String responseType, BaseFilter.FilterDto filterDto, int pageNumber, int pageSize) {

        Query query = courseBaseFilter.buildQuery(filterDto, Course.class);

        Sort sort = Sort.by(Sort.Direction.ASC, "title");

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        query.with(pageRequest);

        Page<Course> coursePage = (Page<Course>) mongoTemplate.find(query, Course.class);

        if (responseType.equalsIgnoreCase("content_details")) {

            return coursePage.map(courseMapper::toCourseDetailResponse);
        } else {
            return coursePage.map(courseMapper::toCourseSnippetResponse);
        }
    }

    @Override
    public Page<CourseResponse> filterCourseByParam(String responseType, WebRequest webRequest, int pageNumber, int pageSize) {

        Query query = courseBaseParamFilter.buildQuery(webRequest, Course.class);

        Sort sort = Sort.by(Sort.Direction.ASC, "title");

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        query.with(pageRequest);

        Page<Course> coursePage = (Page<Course>) mongoTemplate.find(query, Course.class);

        if (responseType.equalsIgnoreCase("content_details")) {

            return coursePage.map(courseMapper::toCourseDetailResponse);
        } else {
            return coursePage.map(courseMapper::toCourseSnippetResponse);
        }
    }

    @Override
    public CourseResponse getCourseBySlug(String responseType, String slug) {

        Course course =
                courseRepository.findBySlug(slug).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("course with slug =  %s has not been found", slug)));

        if (responseType.equalsIgnoreCase("content_details")) {

            return courseMapper.toCourseDetailResponse(course);
        } else {
            return courseMapper.toCourseSnippetResponse(course);
        }
    }

    @Override
    public Page<CourseResponse> getPublicCourses(String responseType, int pageNumber, int pageSize) {

        Sort sort = Sort.by(Sort.Direction.ASC, "title");

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page<Course> coursePage = courseRepository.findAllByIsDraftedFalse(pageRequest);

        if (responseType.equalsIgnoreCase("content_details")) {

            return coursePage.map(courseMapper::toCourseDetailResponse);

        } else {
            return coursePage.map(courseMapper::toCourseSnippetResponse);
        }
    }

    @Override
    public Page<CourseResponse> getPrivateCourses(String responseType, int pageNumber, int pageSize) {

        Sort sort = Sort.by(Sort.Direction.ASC, "title");

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page<Course> coursePage = courseRepository.findAllByIsDraftedTrue(pageRequest);

        if (responseType.equalsIgnoreCase("content_details")) {

            return coursePage.map(courseMapper::toCourseDetailResponse);
        } else {
            return coursePage.map(courseMapper::toCourseSnippetResponse);
        }
    }

    @Override
    public Page<CourseResponse> getCourseByInstructorName(String responseType, String instructorName, int pageNumber, int pageSize) {

        Sort sort = Sort.by(Sort.Direction.ASC, "title");

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page<Course> coursePage = courseRepository.findAllByInstructorName(instructorName, pageRequest);

        if (responseType.equalsIgnoreCase("content_details")) {

            return coursePage.map(courseMapper::toCourseDetailResponse);
        } else {
            return coursePage.map(courseMapper::toCourseSnippetResponse);
        }
    }

    @Override
    public Page<CourseResponse> getFreeCourse(String responseType, int pageNumber, int pageSize) {

        Sort sort = Sort.by(Sort.Direction.ASC, "title");

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page<Course> coursePage = courseRepository.findAllByPriceLessThanEqual(0.0, pageRequest);

        if (responseType.equalsIgnoreCase("content_details")) {

            return coursePage.map(courseMapper::toCourseDetailResponse);
        } else {
            return coursePage.map(courseMapper::toCourseSnippetResponse);
        }
    }
}
