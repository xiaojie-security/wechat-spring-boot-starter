package com.wechat.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 配置字符串加载工具，支持 classpath/file/普通文件路径/直接文本内容。
 */
@Slf4j
public final class ConfigStringLoader {

    private ConfigStringLoader() {
    }

    /**
     * 解析配置来源并返回文本内容。
     *
     * @param source 配置来源
     * @param sourceType 来源类型文案
     * @return 文本内容
     */
    public static String load(String source, String sourceType) {
        if (!StringUtils.hasText(source)) {
            throw new IllegalArgumentException(sourceType + "不能为空");
        }
        String trimmedSource = source.trim();
        if (trimmedSource.startsWith(ResourceUtils.CLASSPATH_URL_PREFIX)
                || trimmedSource.startsWith(ResourceUtils.FILE_URL_PREFIX)) {
            return loadFromResource(trimmedSource, sourceType);
        }
        if (Files.exists(Paths.get(trimmedSource))) {
            return loadFromFilePath(trimmedSource, sourceType);
        }
        return trimmedSource;
    }

    private static String loadFromResource(String location, String sourceType) {
        try {
            Resource resource = new DefaultResourceLoader().getResource(location);
            if (!resource.exists()) {
                throw new IllegalArgumentException(sourceType + "资源不存在：" + location);
            }
            try (InputStream inputStream = resource.getInputStream()) {
                return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            log.error("ConfigStringLoader.loadFromResource 读取资源失败，sourceType={}, location={}", sourceType, location, e);
            throw new UncheckedIOException("读取" + sourceType + "资源失败：" + location, e);
        }
    }

    private static String loadFromFilePath(String path, String sourceType) {
        try {
            return Files.readString(Paths.get(path));
        } catch (IOException e) {
            log.error("ConfigStringLoader.loadFromFilePath 读取文件失败，sourceType={}, path={}", sourceType, path, e);
            throw new UncheckedIOException("读取" + sourceType + "文件失败：" + path, e);
        }
    }
}
