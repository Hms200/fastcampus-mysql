package com.example.fastcampusmysql.domain.post;

import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostBulkInsertTest {

    @Autowired
    private PostRepository postRepository;

//    @Test
//    public void bulkInsert() {
//        var easyRandom = PostFixtureFactory.get(
//                2L,
//                LocalDate.of(2022, 1, 1),
//                LocalDate.of(2022, 2, 1));
//
//        var stopWatch = new StopWatch();
//        stopWatch.start();
//
//        var posts = IntStream.range(0, 10000 * 100)
//                .parallel()
//                .mapToObj(i -> easyRandom.nextObject(Post.class))
//                .toList();
//
//        stopWatch.stop();
//        System.out.println("elapsed time: " + stopWatch.getTotalTimeSeconds());
//        postRepository.bulkInsert(posts);
//    }

}
