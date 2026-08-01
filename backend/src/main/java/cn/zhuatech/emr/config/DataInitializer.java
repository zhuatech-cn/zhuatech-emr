/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.emr.config;

import cn.zhuatech.emr.domain.DomainCatalog;
import cn.zhuatech.emr.model.WorkItem;
import cn.zhuatech.emr.repository.WorkItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seedData(WorkItemRepository repository, DomainCatalog catalog) {
        return args -> {
            if (repository.count() == 0) {
                repository.saveAll(catalog.seedItems().stream()
                    .map(item -> new WorkItem(item.recordNo(), item.title(), item.status(), item.owner(), item.priority()))
                    .toList());
            }
        };
    }
}
