package com.baizhi.repository;

import com.baizhi.entity.Video;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface VideoRepository extends ElasticsearchRepository<Video,String> {
}
